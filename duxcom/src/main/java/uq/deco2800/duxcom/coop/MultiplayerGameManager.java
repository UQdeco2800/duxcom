package uq.deco2800.duxcom.coop;

import javafx.application.Platform;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;
import uq.deco2800.duxcom.coop.listeners.BlockStateListener;
import uq.deco2800.duxcom.coop.listeners.CountdownListener;
import uq.deco2800.duxcom.coop.listeners.TurnTimeoutListener;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroType;
import uq.deco2800.duxcom.interfaces.LobbyInterface;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.singularity.clients.duxcom.GameplayEventListener;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.*;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.herospecific.HeroSpecifier;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.herospecific.SquadSpecifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static uq.deco2800.duxcom.coop.NiceError.niceError;

/**
 * Manages multiplayer games.
 * <p>
 * Created by liamdm on 18/10/2016.
 */
public class MultiplayerGameManager {

    /**
     * The flashbang currently being displayed
     */
    private static String flashbang = "";

    /**
     * The time the flashbang was added
     */
    private static long flashbangAdded = 0;

    /**
     * Returns the flashbang
     */
    public static String getFlashbang(){
        if((System.currentTimeMillis() - flashbangAdded) > 1000){
            if(!flashbang.isEmpty()){
                flashbang = "";
            }
            return null;
        }
        return  flashbang;
    }

    /**
     * Show a flashbang for 1 second
     */
    public static void showFlashbang(String message){
        flashbangAdded = System.currentTimeMillis();
        flashbang = message;
    }

    /**
     * The temporary block boolean indicating if this should block
     */
    private boolean temporaryBlock = false;

    /**
     * Enables a temporary block
     */
    public void enableTemporaryBlock(){
        temporaryBlock = true;
        notifyBlockStateListeners();
    }

    /**
     * Disables the temporary block
     */
    public void disableTemporaryBlock(){
        temporaryBlock = false;
        notifyBlockStateListeners();
    }

    /**
     * The data transfer point for early receipt of player order
     */
    private static GameUpdate playerOrderSneakyTransferPoint;

    /**
     * If the sneaky transfer point has been used
     */
    private static boolean isSneakyTransferPoint = false;

    /**
     * Sneaky transfer the player order, it arrived early
     * @param playerOrder the players ordering
     */
    public static void sneakyTransferPlayerOrder(GameUpdate playerOrder){
        playerOrderSneakyTransferPoint = playerOrder;
        isSneakyTransferPoint = true;
    }

    private org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GameManager.class);

    /**
     * If the clients are in sync
     */
    private boolean clientsInSync = false;

    /**
     * Returns true if the clients are in sync
     */
    public boolean areClientsSynced(){
        return clientsInSync;
    }

    /**
     * The game manager being used
     */
    private final GameManager gameManager;

    /**
     * If multiplayer is enabled
     */
    private boolean multiplayerEnabled;

    /**
     * The hero generator
     */
    private MultiplayerHeroManager multiplayerHeroManager;

    /**
     * The game countdown manager
     */
    private GameCountdown gameCountdown;

    /**
     * The multiplayer client
     */
    private MultiplayerClient multiplayerClient = MultiplayerClient.getMultiplayerClient();

    /**
     * The gameplay client
     */
    private GameplayClient gameplayClient = MultiplayerClient.getGameplayClient();

    /**
     * True if the players on the map have been loaded
     */
    private LocalGameState localGameState = LocalGameState.WAITING_PLAYER_ORDER;

    /**
     * List of listeners on blocked state changes
     */
    private List<BlockStateListener> blockStateListeners = new ArrayList<>();

    /**
     * Add a new block state listener
     */
    public void addBlockStateListener(BlockStateListener listener){
        blockStateListeners.add(listener);
    }

    /**
     * Remove the block state listener
     */
    public void removeBlockStateListener(BlockStateListener listener){
        blockStateListeners.remove(listener);
    }

    /**
     * Hook the current player moving
     * @param x the X coordinate moving to
     * @param y the Y coordinate moving to
     */
    public boolean hookPlayerMove(int x, int y){
        if(!isMultiplayer()){
            LOGGER.info("Ignoring multiplayer move hook, multiplayer state is false.");
            return true;
        }

        gameplayClient.sendMessage(PlayerAction.move(x, y));
        return true;
    }

    /**
     * Hooks the attack inputs
     */
    public boolean hookAttack(AbstractCharacter owner, AbstractAbility ability, int x, int y){
        if(!isMultiplayer()){
            LOGGER.info("Ignoring attack hook, multiplayer state is false.");
            return false;
        }

        Targetable targetable = (Targetable) gameManager.getMap().getMovableEntity(x, y);
        if(!(targetable instanceof AbstractHero)){
            return false;
        }
        if(!(owner instanceof AbstractHero)){
            return false;
        }

        AbstractHero sender = (AbstractHero)owner;
        AbstractHero target = (AbstractHero)targetable;

        String targetUUID = target.getName();
        String senderUUID = sender.getName();
        double damage = ability.getDamage();
        target.changeHealth(damage);

        double health = target.getHealth();

        gameplayClient.sendMessage(SquadState.attack(senderUUID, targetUUID, health, ability.getName()));

        if(target.getHealth() <= 0){
            gameplayClient.sendMessage(SquadState.heroDeath(targetUUID));
            multiplayerHeroManager.killHero(targetUUID);
        }

        return true;
    }

    /**
     * Hook into the game managers next turn and override it to provide custom functionality
     */
    public boolean hookNextTurn(){
        if(!isMultiplayer()){
            LOGGER.info("Ignoring multiplayer next turn hook, multiplayer state is false.");
            return false;
        }

        MultiplayerGameManager.showFlashbang("Changed turns...");

        String nextHero = multiplayerHeroManager.getCurrentSquad().nextHero();


        if(nextHero == null){
            // move to the nextplayer
            String nextPlayer = multiplayerHeroManager.nextPlayer();
            LOGGER.info("Squad has no next hero, switching to next player [{}]", nextPlayer);

            gameplayClient.sendMessage(GameUpdate.notifyPlayerChange(nextPlayer));
            gameManager.getHeroManager().setHero(multiplayerHeroManager.getCurrentSquad().getCurrentHero());
            gameManager.getHeroManager().getCurrentHero().onTurn();

            gameManager.fullRenderRefresh();
            return true;
        }

        LOGGER.info("Trying to switch to hero [{}]", nextHero);

        gameManager.getHeroManager().setHero(nextHero);
        gameManager.getHeroManager().getCurrentHero().onTurn();

        gameplayClient.sendMessage(SquadState.nextHero(nextHero));
        gameManager.fullRenderRefresh();
        return true;
    }

    /**
     * Get the current hero manager
     * @return the current hero manager
     */
    public MultiplayerHeroManager getHeroManager(){
        return multiplayerHeroManager;
    }

    /**
     * Returns true if this is the current thurn
     * @return the current turn
     */
    public boolean isCurrentTurn(){
        return multiplayerHeroManager.isCurrentTurn();
    }

    /**
     * Used to listen for turn end events
     */
    private TurnTimeoutListener turnTimeoutListener = new TurnTimeoutListener() {
        @Override
        public void onTurnTimeout(String user) {
            // called when a turn timeout occurs
            LOGGER.info("Turn timed out for " + user + "...");

        }
    };

    /**
     * Recieves the gameplay events
     */
    private GameplayEventListener gameplayEventListener = new GameplayEventListener() {
        @Override
        public void recievedGameMetadataQuery(GameMetadata gameMetadata) {
            /* necessary override */

        }

        @Override
        public void recievedGameRegistrationMessage(GameRegistration gameRegistration) {
            /* necessary override */

        }

        @Override
        public void recievedControlMessage(ControlMessage controlMessage) {
            if(controlMessage.getInnerMessageType() == ControlMessage.MessageType.JIT_FLOW_PERMIT){
                notifyBlockStateListeners();
                LOGGER.info("Recieved admin just in time acknowledgment, game is ready to commence...");
                clientsInSync = true;
                gameCountdown.start();

                // ensure the interface is blocked
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 100; ++i){
                            notifyBlockStateListeners();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            }

            if(multiplayerClient.isAdmin()){
                // send the player change
                gameplayClient.sendMessage(GameUpdate.notifyPlayerChange(multiplayerHeroManager.firstPlayer()));
            }
        }

        @Override
        public void recievedStateMessage(StateChange stateChange) {
            if(stateChange.shouldDitch()){
                // ditch the server
                LOGGER.warn("Got server ditch!");
            }
        }

        @Override
        public void recievedUpdateMessage(GameUpdate gameUpdate) {
            // recieved the definitive player ordering
            switch(gameUpdate.getInnerMessageType()){
                case PLAYER_ORDER:
                    // the player order has been updated
                    multiplayerHeroManager.setPlayerOrder(gameUpdate.getPlayerOrder());

                    if(!multiplayerClient.isAdmin()) {
                        // if you are an admin do nothing yet... waiting for admin
                        setLocalGameState(LocalGameState.WAITING_HERO_STATE);
                        LOGGER.info("Waiting for admin to distribute game state...");
                        niceError("Waiting for admin", "Waiting for admin to distribute game state...", "The server is currently waiting for the admin to distribute the game state, please wait... The game will automatically load when this is done!", false);
                        return;
                    }

                    setLocalGameState(LocalGameState.DISTRIBUTING_HERO_STATE);

                    // add the players to the map
                    List<SquadSpecifier> specifiers = spawnHeroes(gameUpdate.getPlayerOrder());

                    LOGGER.info("Distributing squad specifiers to players...");
                    gameplayClient.sendMessage(SquadState.broadcastSquadState(specifiers));

                    break;

                case PLAYER_CHANGE:
                    // next player command
                    multiplayerHeroManager.setCurrentPlayer(gameUpdate.getCurrentPlayer());

                    Platform.runLater(()-> {
                                gameManager.getHeroManager().setHero(multiplayerHeroManager.getCurrentSquad().getCurrentHero());
                                gameManager.fullRenderRefresh();
                                gameManager.centerMapOnCurrentHero();
                            });

                    // if not current turn do nothing
                    if(!multiplayerHeroManager.isCurrentTurn()){
                        setLocalGameState(LocalGameState.WAITING_OTHER_PLAYER);
                        notifyBlockStateListeners();
                        return;
                    }

                    // current turn
                    setLocalGameState(LocalGameState.WAITING_CURRENT_TURN);
                default:
                    /* placeholder */
                    break;
            }
        }

        @Override
        public void recievedPlayerActionMessage(PlayerAction playerAction) {
            switch(playerAction.getInnerMessageType()){
                case END_TURN:
                    // this is deprecated
                    LOGGER.info("The end turn functionality is deprecated... Please use SquadState!");
                    return;

                case PLAYER_MOVE:
                    // hook the player move
                    if(playerAction.getUsername().equals(multiplayerClient.getUsername())){
                        // current username
                        LOGGER.info("Ignoring player move as was broadcast from local system!");
                        return;
                    }

                    LOGGER.info("Got player move to point ({}, {}).", playerAction.getX(), playerAction.getY());

                    // move to the point
                    Platform.runLater(()-> {
                        gameManager.moveToPoint(playerAction.getX(), playerAction.getY());
                        gameManager.fullRenderRefresh();
                    });
                default:
                    /* placeholder */
                    break;

            }
        }

        @Override
        public void recievedSquadStateMessage(SquadState squadState) {
            switch(squadState.getInnerMessageType()){
                case SQUAD_INIT:
                    // got a broadcast from an admin with the new squad state

                    // no need to recieve as an admin
                    if(multiplayerClient.isAdmin()){
                        gameplayClient.sendMessage(ControlMessage.gameManagerReady());
                        setLocalGameState(LocalGameState.WAITING_CLIENT_SYNC);
                        return;
                    }

                    // state check
                    if(localGameState != LocalGameState.WAITING_HERO_STATE){
                        throw new UnexpectedIncorrectGameState("Recieved a squad state broadcast while not waiting for distribution. Aborting...");
                    }

                    // load the map state
                    multiplayerHeroManager.loadHeroes(squadState.getSquadSpecifiers());

                    // set JIT to ready
                    gameplayClient.sendMessage(ControlMessage.gameManagerReady());
                    setLocalGameState(LocalGameState.WAITING_CLIENT_SYNC);

                    break;

                case NEXT_HERO:
                    // moving to the next hero
                    if(squadState.getUsername().equals(multiplayerClient.getUsername())){
                        // ignore this was the other player
                        LOGGER.info("Ignoring next hero, command originated from local!");
                        return;
                    }

                    LOGGER.info("Switching from existing hero to next hero.");

                    // move to the next squad hero
                    Platform.runLater(()-> {
                        gameManager.getHeroManager().setHero(multiplayerHeroManager.getCurrentSquad().nextHero());
                        gameManager.fullRenderRefresh();
                    });
                    break;

                case HERO_ATTACK:
                    // hero attacked another
                    String attack_source = squadState.getUsername();
                    String attack_destination = multiplayerHeroManager.getHeroOwner(squadState.getTargetData().getReceiver());

                    AbstractHero hero = gameManager.getHeroManager().getHero(attack_destination);

                    double newHealth = squadState.getDamage();
                    double delta = newHealth - hero.getHealth();
                    String damage = String.valueOf((int)Math.abs(delta));

                    // change the health by
                    hero.changeHealth(delta);

                    if(hero.getHealth() < 0) {
                        multiplayerHeroManager.killHero(squadState.getTargetData().getReceiver());
                        showFlashbang(String.format("%s killed a hero of %s", attack_source, attack_destination));

                        // notify other players of the death
                        if(attack_destination.equals(multiplayerClient.getUsername())) {
                            gameplayClient.sendMessage(SquadState.heroDeath(hero.getName()));
                        }
                    } else {
                        showFlashbang(String.format("%s attacked %s for %s damage", attack_source, attack_destination, damage));
                    }

                    Platform.runLater(gameManager::fullRenderRefresh);
                    break;

                case HERO_DEATH:
                    // hero killed another
                    String death_destination = multiplayerHeroManager.getHeroOwner(squadState.getTargetData().getReceiver());

                    if(multiplayerHeroManager.getSquad(death_destination).allHeroKill()){
                        showFlashbang(String.format("%s died", death_destination));
                        multiplayerHeroManager.killPlayer(death_destination);
                    }

                    break;

                case HERO_HEALTH_CHANGE:
                    break;
                case WORLD_KILL:
                    break;
                default:
                    /* placeholder */
                    break;
            }
        }

        @Override
        public String toString() {
            return "MultiplayerGameManagerEventListener"+ Thread.currentThread().getId();
        }
    };

    /**
     * Notify the block state listeners
     */
    private void notifyBlockStateListeners() {
        LOGGER.info("Notifying block state listeners of game state [{}].", isBlocking());
        for(BlockStateListener listener : blockStateListeners){
            listener.onRequireUpdate();
        }
    }

    /**
     * Converts local to remote hero type
     * @param heroType the type of the hero
     */
    private HeroSpecifier.HeroType convert(HeroType heroType){
        switch(heroType){
            case KNIGHT:
                return HeroSpecifier.HeroType.KNIGHT;
            case ARCHER:
                return HeroSpecifier.HeroType.ARCHER;
            case CAVALIER:
                return HeroSpecifier.HeroType.CAVALIER;
            default:
                return null;
        }
    }

    /**
     * Load the map
     * @param map the map to use
     */
    public void loadMap(MapAssembly map){
        multiplayerHeroManager = new MultiplayerHeroManager(map);

        /**
         * Pass through the second hand event if not loaded
         */
        if(isSneakyTransferPoint){
            LOGGER.info("Detected event transfer scheduled for trigger.");
            gameplayEventListener.recievedUpdateMessage(playerOrderSneakyTransferPoint);
        }
    }

    /**
     * Spawns the heroes on the given map
     * @param playerOrder the order of the players
     */
    private List<SquadSpecifier> spawnHeroes(List<String> playerOrder) {
        Map<String, Squad> squadMap = multiplayerHeroManager.generateHeroes(playerOrder);
        List<SquadSpecifier> specifiers = new LinkedList<>();

        for(String player : playerOrder){
            Squad target = squadMap.get(player);
            SquadSpecifier ss = new SquadSpecifier(player);

            for(AbstractHero hero : target.getHeroesToAdd()){
                ss.add(
                        new HeroSpecifier(
                                convert(hero.getHeroType()),
                                hero.getName(),
                                new int[]{hero.getX(), hero.getY()}
                                )
                );
            }

            specifiers.add(ss);
            LOGGER.info("Generated squad specifier for [{}]...", player);
        }

        return specifiers;
    }

    /**
     * Used to listen for countdown ends
     */
    private CountdownListener countdownEndedListener = new CountdownListener() {
        @Override
        public void onCountdownEnded() {
            LOGGER.info("Game countdown ended, notifying block state listeners.");

            // call the game manager update blocking
            for(BlockStateListener blockStateListener : blockStateListeners){
                blockStateListener.onRequireUpdate();
            }
        }
    };

    /**
     * Gets the game countdown
     */
    public GameCountdown getGameCountdown(){
        return gameCountdown;
    }

    public MultiplayerGameManager(GameManager gameManager, boolean multiplayerEnabled) {
        setLocalGameState(LocalGameState.WAITING_PLAYER_ORDER);
        this.multiplayerEnabled = multiplayerEnabled;
        this.gameManager = gameManager;

        if (multiplayerEnabled) {
            gameCountdown = new GameCountdown(countdownEndedListener);
            multiplayerClient = MultiplayerClient.getMultiplayerClient();
            gameplayClient = MultiplayerClient.getGameplayClient();

            LOGGER.info("Linking primary gameplay listener...");
            gameplayClient.addListener(gameplayEventListener);

            LOGGER.info("Deregistering lobby listener...");
            gameplayClient.removeListener(LobbyInterface.getDeregistrationReference());
        }
    }

    /**
     * If the game is multiplayer
     */
    public boolean isMultiplayer() {
        return multiplayerEnabled;
    }

    /**
     * If the user input should be blocked
     */
    public boolean isBlocking() {
        return multiplayerEnabled && (gameCountdown.isBlocking() || !isCurrentTurn() || temporaryBlock);
    }

    /**
     * Sets the local game state
     * @param localGameState the local state
     */
    public void setLocalGameState(LocalGameState localGameState) {
        LOGGER.info(String.format("Local game state changed to: %s", localGameState));
        this.localGameState = localGameState;
    }


}
