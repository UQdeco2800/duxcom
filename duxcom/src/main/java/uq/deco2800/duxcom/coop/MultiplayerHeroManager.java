package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.auth.LoginManager;
import uq.deco2800.duxcom.coop.exceptions.UnexpectedIncorrectGameState;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;
import uq.deco2800.duxcom.entities.heros.Cavalier;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.herospecific.HeroSpecifier;
import uq.deco2800.singularity.common.representations.duxcom.gamestate.herospecific.SquadSpecifier;

import java.util.*;

/**
 * Generates a hero on the given map.
 * <p>
 * Created by liamdm on 18/10/2016.
 */
public class MultiplayerHeroManager {

    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameManager.class);

    /**
     * The order of players
     */
    private List<String> playerOrder = new LinkedList<>();

    /**
     * The list of dead players
     */
    private List<String> deadPlayers = new LinkedList<>();

    /**
     * Kill a player. returns true if game over
     * @param player the player to kill
     */
    public boolean killPlayer(String player){
        deadPlayers.add(player);
        return isGameOver();
    }

    /**
     * If the game is over
     */
    private boolean isGameOver(){
        return (playerOrder.size() - deadPlayers.size()) == 1;
    }

    /**
     * The current player
     */
    private String currentPlayer;

    /**
     * The local players username
     */
    private String localUsername = LoginManager.getUsername();

    /**
     * Returns true if it is the current players turn
     */
    protected boolean isCurrentTurn() {
        if (currentPlayer == null) {
            return false;
        }
        return currentPlayer.equals(localUsername);
    }

    /**
     * The map of squads to players
     */
    private HashMap<String, Squad> squadMap = new HashMap<>();

    /**
     * Get the current player
     */
    public String getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Move to the next player
     */
    public String nextPlayer(){
        if(isGameOver()){
            throw new UnexpectedIncorrectGameState("The game was over but next player was called!");
        }

        do {
            int cpi = playerOrder.indexOf(currentPlayer) + 1;

            logger.info("Next player index is [{}]", cpi);

            if (cpi >= playerOrder.size()) {
                logger.info("Resetting overflowing player index...");
                cpi = 0;
            }

            currentPlayer = playerOrder.get(cpi);
        } while(deadPlayers.contains(currentPlayer));

        logger.info("Targeting current player [{}] and resetting their squad.", currentPlayer);

        squadMap.get(currentPlayer).seekStart();

        return currentPlayer;
    }

    /**
     * Gets the squad for the given player
     * @param player the players squad
     */
    public Squad getSquad(String player){
        return squadMap.get(player);
    }

    /**
     * Initialise the hero manager to target the first player
     */
    public String firstPlayer(){
        currentPlayer = playerOrder.get(0);
        squadMap.get(currentPlayer).seekStart();
        return currentPlayer;
    }

    /**
     * The map assembly to generate heroes on
     */
    private final MapAssembly map;

    /**
     * The size of player squads
     */
    private int squadSize = 4;


    /**
     * Uses the given map assembly for hero generation.
     */
    public MultiplayerHeroManager(MapAssembly mapAssembly) {
        this.map = mapAssembly;
    }

    /**
     * A list of used Y coordinates for spawn
     */
    LinkedList<Integer> usedYSpawns = new LinkedList<>();

    /**
     * Generates a random in bounds squad spawn
     */
    public int[] generateRandomSquadSpawn() {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

        int effectiveWidth = mapWidth - 2 * squadSize;
        int effectiveHeight = mapHeight - 2 * squadSize;

        Random random = new Random();

        int xSpawn;
        int ySpawn;
        for (; ; ) {
            do {
                ySpawn = squadSize + random.nextInt(effectiveHeight);
            } while (usedYSpawns.contains(ySpawn));

            xSpawn = squadSize + random.nextInt(effectiveWidth);

            spawnOccupied:
            {
                for (int x = xSpawn; x < xSpawn + squadSize; ++x) {
                    if (map.getEntities(x, ySpawn).size() != 0) {
                        break spawnOccupied;
                    }
                }
                // spawn was empty we're done
                break;
            }

        }

        return new int[]{xSpawn, ySpawn};
    }

    /**
     * Generate heroes for the given players
     *
     * @param players the players in the game
     */
    public Map<String, Squad> generateHeroes(List<String> players) {
        HashMap<String, Squad> newSquadMap = new HashMap<>();

        for (String player : players) {
            int[] spawn = generateRandomSquadSpawn();
            Squad squad = SquadGenerator.generateSquad(player, squadSize, spawn[0], spawn[1]);

            // add the heroes to the map
            squad.getHeroesToAdd().forEach(map::addHero);

            logger.info("Populated squad map for player [{}] with squad [{}].", player, squad);
            newSquadMap.put(player, squad);
        }

        this.squadMap = newSquadMap;
        return squadMap;
    }

    /**
     * Load the heroes from a given squad specifier map
     *
     * @param load
     */
    public void loadHeroes(List<SquadSpecifier> load) {
        for (String player : playerOrder) {
            SquadSpecifier target = null;
            for (SquadSpecifier specifier : load) {
                 if(specifier.getOwner().equals(player)){
                     target = specifier;
                     break;
                 }
            }
            logger.info("Got squad specifier for player [{}] with value [{}]", player, target);

            Squad squad = new Squad(player);

            if (target == null) {
                continue;
            }
            for (HeroSpecifier heroSpecifier : target.getHeroes()) {
                int x = heroSpecifier.getSpawn()[0];
                int y = heroSpecifier.getSpawn()[1];

                AbstractHero hero;
                if (heroSpecifier.getType() == HeroSpecifier.HeroType.KNIGHT) {
                    hero = new Knight(x, y);
                } else if (heroSpecifier.getType() == HeroSpecifier.HeroType.CAVALIER) {
                    hero = new Cavalier(x, y);
                } else {
                    hero = new Archer(x, y);
                }

                hero.setName(heroSpecifier.getHeroUUID());
                squad.addHero(heroSpecifier.getHeroUUID(), hero);

                map.addHero(hero);

            }

        squadMap.put(player, squad);
        }
    }

    /**
     * Get the player squad size
     */
    public int getSquadSize() {
        return squadSize;
    }

    /**
     * Set the player squad size
     *
     * @param squadSize the new size > 1
     */
    public void setSquadSize(int squadSize) {
        this.squadSize = squadSize;
    }

    /**
     * Sets the player order to use
     *
     * @param playerOrder the player order
     */
    public void setPlayerOrder(List<String> playerOrder) {
        this.playerOrder = playerOrder;
    }

    /**
     * Sets the current player
     *
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        squadMap.get(currentPlayer).seekStart();
    }

    /**
     * Get the current squad
     */
    public Squad getCurrentSquad(){
        return getSquad(getCurrentPlayer());
    }

    /**
     * Kills a hero from a squad. Returns true if the whole squad is dead.
     *
     * @param targetUUID the target hero UID
     */
    public boolean killHero(String targetUUID) {
        for(Squad sqaud : squadMap.values()){
            if(sqaud.isInSquad(targetUUID)){
                sqaud.killHero(targetUUID);

                return sqaud.allHeroKill();
            }
        }
        return false;
    }

    /**
     * Gets the owner of the given hero
     * @param hero the hero ID
     * @return the owner of the hero
     */
    public String getHeroOwner(String hero) {
        for(String owner : squadMap.keySet()){
            if(squadMap.get(owner).isInSquad(hero)){
                return owner;
            }
        }
        return "";
    }
}
