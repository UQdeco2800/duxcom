package uq.deco2800.duxcom;

import javafx.application.Platform;
import org.slf4j.Logger;
import uq.deco2800.duxcom.abilities.*;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.controllers.DuxComController;
import uq.deco2800.duxcom.controllers.UserInterfaceController;
import uq.deco2800.duxcom.coop.MultiplayerGameManager;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.TileDataRegister;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.dynamics.DynamicEntity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.listeners.CurrentHeroChangeListener;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.InterfaceSegmentType;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectives.ProtectionObjective;
import uq.deco2800.duxcom.objectivesystem.GameState;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.objectivesystem.ObjectiveTracker;
import uq.deco2800.duxcom.overworld.LevelRegister;
import uq.deco2800.duxcom.savegame.SaveGame;
import uq.deco2800.duxcom.savegame.SaveOriginator;
import uq.deco2800.duxcom.savegame.SavePlayerStats;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import uq.deco2800.duxcom.shop.PlayerWallet;
import uq.deco2800.duxcom.shop.ShopManager;
import uq.deco2800.duxcom.sound.SoundPlayer;
import uq.deco2800.duxcom.tiles.LiveTile;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.time.DayNightClock;
import uq.deco2800.singularity.common.representations.duxcom.PlayerStats;

import java.util.*;

import static java.awt.geom.Point2D.distance;

import java.io.File;
import java.io.IOException;

/**
 * Manages the interaction between the user and the game.
 *
 * @author Leggy
 * @author Woody
 */
public class GameManager extends Observable implements Observer {

    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GameManager.class);

    private int selectionX = -1;
    private int selectionY = -1;
    private int lastSelectionX = -1;
    private int lastSelectionY = -1;

    private double viewPortWidth = 0;
    private double viewPortHeight = 0;

    private double draggedX = -1;
    private double draggedY = -1;

    private double pressedX = -1;
    private double pressedY = -1;

    private double scale = 0.3;

    private double currentXOffset = 0;
    private double currentYOffset = 0;
    private double xOffset = 0;
    private double yOffset = 0;

    private DuxComController controller;

    private MapAssembly map;

    private boolean playerTurn = false;

    private int hoverX = -1;
    private int hoverY = -1;

    private int targetXOffset = 0;
    private int targetYOffset = 0;

    boolean doPan = false;

    private boolean playerPerformingMove = false;
    private int enemyMoveIntentX = -1;
    private int enemyMoveIntentY = -1;
    private int currentAbilityOwnerX = -1;
    private int currentAbilityOwnerY = -1;
    private int currentAbilityTargetX = -1;
    private int currentAbilityTargetY = -1;
    private boolean currentlyAbilityHit = false;
    private AbstractAbility currentAbilityCast;

    private ObjectiveBuilder builder;
    private ObjectiveTracker tracker;
    private GameState gameState;
    private ScoreSystem scoreCounter = new ScoreSystem();


    private ShopManager shopManager = new ShopManager(this);
    private PlayerWallet gameWallet = new PlayerWallet(500);

    private List<int[]> tempVisiblePoints = new ArrayList<>();

    private boolean gameChanged = true;
    private boolean turnGraphicsChanged = true;
    private boolean backgroundChanged = true;
    private boolean minimapChanged = true;
    private boolean selectionChanged = true;
    private boolean visionChanged = true;
    private boolean movementChanged = true;
    private boolean abilitySelectedChanged = true;
    private boolean inventoryChanged = true;

    private boolean isPlayingMusic = false;

    private boolean miniMapVisible = false;
    private boolean chatVisible = false;
    private boolean miniHealthBarVisible = true;
    private boolean muted = false;

    private boolean blocking = true;

    private boolean displayDebug = true;

    private AbilitySelected abilitySelected = AbilitySelected.MOVE;

    private MultiplayerGameManager multiplayerGameManager = new MultiplayerGameManager(null, false);
    
    private SaveGame saveGame = new SaveGame();
    private SaveOriginator saveOriginator = new SaveOriginator();
    private PlayerStats playerStats = new PlayerStats();
    private int killCount = 0;

    private List<CurrentHeroChangeListener> heroChangeListeners = new ArrayList<CurrentHeroChangeListener>();
    
    /**
     * Gets the multiplayer game manager for this
     */
    public MultiplayerGameManager getMultiplayerGameManager() {
        return this.multiplayerGameManager;
    }

    /**
     * Performs any needed auto-panning
     */
    public void panView() {
        double xDistance = currentXOffset - targetXOffset;
        double yDistance = currentYOffset - targetYOffset;

        xDistance *= -0.5;
        yDistance *= -0.5;

        boolean clearX = Math.abs(xDistance) < 2;
        if (clearX) {
            currentXOffset = targetXOffset;
        } else {
            currentXOffset += xDistance;
        }

        boolean clearY = Math.abs(xDistance) < 2;
        if (clearY) {
            currentYOffset = targetYOffset;
        } else {
            currentYOffset += yDistance;
        }

        doPan = !clearX || !clearY;

        gameChanged = true;

    }

    /**
     * Moves the view gradually to the new point
     */
    public void moveViewTo(int newX, int newY) {
        int scaledWidth = (int) (TileDataRegister.TILE_WIDTH * scale);
        int scaledHeight = (int) (TileDataRegister.TILE_HEIGHT * scale);
        int baseX = (int) (map.getWidth() * TileDataRegister.TILE_WIDTH * scale) / 2;

        int x = baseX + (newY - newX) * scaledWidth / 2;
        x -= viewPortWidth * 0.5;

        int y = (newY + newX) * scaledHeight / 2;
        y -= viewPortHeight * 0.5;

        targetXOffset = -1 * x;
        targetYOffset = -1 * y;

        doPan = true;

        gameChanged = true;
    }

    /**
     * Moves the view relative to the current view center.
     */
    public void moveViewRelative(int newX, int newY) {

        targetXOffset = (int) (currentXOffset - newX);
        targetYOffset = (int) (currentYOffset - newY);

        doPan = true;

        gameChanged = true;
    }

    private DayNightClock dayNightClock = new DayNightClock(0.5);

    /**
     * Gets the x coordinate of the point over which one is hovering
     *
     * @return x coordinate
     */
    public int getHoverX() {
        return hoverX;
    }

    /**
     * Gets the y coordinate of the point over which one is hovering
     *
     * @return y coordinate
     */
    public int getHoverY() {
        return hoverY;
    }

    /**
     * Sets the pressed coordinates upon right mouse button press
     *
     * @param x the x coordinate of the press
     * @param y the y coordinate of the press
     */
    public void setRightPressed(double x, double y) {
        pressedX = x;
        pressedY = y;
    }

    /**
     * attempt to move hero to position if it is players turn
     *
     * @param x the x coordinate of the destination
     * @param y the y coordinate of the destination
     */
    public void setLeftPressed(int x, int y) {
        if (!map.canSelectPoint(x, y)) {
            return;
        }

        selectionX = x;
        selectionY = y;
        if (lastSelectionX != -1 && lastSelectionX == selectionX
                && lastSelectionY == selectionY) {
            useSelectedAbility();
        }
        lastSelectionX = selectionX;
        lastSelectionY = selectionY;
        selectionChanged = true;
    }

    /**
     * Uses the currently selected ability on the currently selected point.
     */
    public void useSelectedAbility() {
        if (abilitySelected == AbilitySelected.MOVE
                && validMoveTile(selectionX, selectionY)) {
            // Move the hero
            AbstractHero hero = map.getCurrentTurnHero();
            moveToSelected();
            updateLiveTileEffectsOnCharacter(hero);
            gameChanged = true;
            minimapChanged = true;
            visionChanged = true;
            movementChanged = true;

            // Only next turn if it's not the beta tester
            if (hero.getActionPoints() == 0) {
                nextTurn();
            }
        } else if (abilitySelected == AbilitySelected.ABILITY1) {
            abilityOne();
        } else if (abilitySelected == AbilitySelected.ABILITY2) {
            abilityTwo();
        } else if (abilitySelected == AbilitySelected.WEAPON) {
            weaponAbility();
        } else if (abilitySelected == AbilitySelected.UTILITY) {
            utilityAbility();
        }
    }

    private boolean validMoveTile(int x, int y) {
        return !map.getTile(x, y).isOccupied();
    }

    public void updateLiveTileEffectsOnCharacter(AbstractCharacter character) {
        int x = character.getX();
        int y = character.getY();
        LiveTile liveTile = map.getLiveTile(x, y);

        if (liveTile != null && (liveTile.hasEffect() || liveTile.hasAppliedEffect())) {
            for (AbstractBuff tileEffect
                    : map.getLiveTile(x, y).getAllEffects()) {
                logger.info("LIVETILE: character.addBuff(" + tileEffect.getName() + ")");
                character.addBuff(AbstractBuff.getNewBuff(tileEffect.getType(), tileEffect.getBuffStrength(),
                        tileEffect.getOriginalDuration()));
            }
        }
        if (character instanceof AbstractHero) {
            updateBuffIcons();
        }
        character.turnTick();
    }

    /**
     * Attempts to zoom in by changing the scale
     */
    public void zoomIn() {
        if (scale < 0.8) {
            double scaleFactor = (scale + 0.03) / scale;
            currentXOffset = scaleFactor * (currentXOffset - viewPortWidth / 2) + viewPortWidth / 2;
            currentYOffset = scaleFactor * (currentYOffset - viewPortHeight / 2) + viewPortHeight / 2;
            scale += 0.03;

            selectionChanged = true;
            gameChanged = true;
        }

    }

    /**
     * Attempts to zoom out by changing the scale
     */
    public void zoomOut() {
        if (scale > 0.05) {
            double scaleFactor = (scale - 0.03) / scale;
            currentXOffset = scaleFactor * (currentXOffset - viewPortWidth / 2) + viewPortWidth / 2;
            currentYOffset = scaleFactor * (currentYOffset - viewPortHeight / 2) + viewPortHeight / 2;
            scale -= 0.03;

            selectionChanged = true;
            gameChanged = true;
        }
    }

    /**
     * Gets the current scale of the game
     *
     * @return the game's scale
     */
    public double getScale() {
        return this.scale;
    }

    /**
     * Method to return the clock keeping track of time.
     *
     * @return Day night clock.
     */
    public DayNightClock getDayNightClock() {
        return this.dayNightClock;
    }

    /**
     * Sets the drag parameters of the manager
     *
     * @param x the x distance dragged
     * @param y the y distance dragged
     */
    public void setDragged(double x, double y) {
        if (Math.abs(draggedX - x) >= 0.00001d || Math.abs(draggedY - y) >= 0.00001d) {
            draggedX = x < 0 ? 0 : x;
            draggedY = y < 0 ? 0 : y;
            xOffset = draggedX - pressedX;
            yOffset = draggedY - pressedY;

            gameChanged = true;
        }
    }

    /**
     * Centers the map on the given point
     */
    public void centerMapOnPoint(int targetX, int targetY) {

        int scaledWidth = (int) (TileDataRegister.TILE_WIDTH * scale);
        int scaledHeight = (int) (TileDataRegister.TILE_HEIGHT * scale);
        int baseX = (int) (map.getWidth() * TileDataRegister.TILE_WIDTH * scale) / 2;

        int x = baseX + (targetY - targetX) * scaledWidth / 2;
        x -= viewPortWidth * 0.5;
        int y = (targetY + targetX) * scaledHeight / 2;
        y -= viewPortHeight * 0.5;

        currentXOffset = -x;
        currentYOffset = -y;

        gameChanged = true;
    }

    /**
     * Sets parameters upon mouse press release
     */
    public void setReleased() {
        currentXOffset += xOffset;
        currentYOffset += yOffset;
        xOffset = 0;
        yOffset = 0;

        gameChanged = true;
    }

    /**
     * Gets the x offset
     *
     * @return the current x offset + the event x offset
     */
    public double getxOffset() {
        return currentXOffset + xOffset;
    }

    /**
     * Gets the y offset
     *
     * @return the current y offset + the event y offset
     */
    public double getyOffset() {

        return currentYOffset + yOffset;
    }

    /**
     * Sets the map in use
     *
     * @param demoMap the map to be used in the game
     */
    public void setMap(MapAssembly demoMap) {
        this.map = demoMap;
    }

    /**
     * Gets the map in use
     *
     * @return the map currently used in the game
     */
    public MapAssembly getMap() {
        return map;
    }

    /**
     * Queries whether it is the players turn
     *
     * @return true iff it is the player's turn, else false
     */
    public boolean isPlayerTurn() {
        return playerTurn;
    }

    /**
     * Sets whether it is the players turn or not
     *
     * @param playerTurn true if it is to be the player's turn, else false
     */
    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Updates the temporary visible points
     */
    private void updateTempVisiblePoints() {
        for (int[] point : tempVisiblePoints) {
            point[3] -= 1;
            if (point[3] <= 0) {
                map.setPointHidden(point[0], point[1]);
            }
        }
    }

    /**
     * Cycle through Player Heroes and AI Enemies
     */
    public void nextTurn() {
        visionChanged = true;
        SoundPlayer.playEndTurn();
        // Check if any protection objectives have failed
        gameState.updateAllHeroProtectionGoals(getHeroManager());
        // hook the next turn
        if (!multiplayerGameManager.hookNextTurn()) {

            if (map.enemyPerformingMove()) {
                return;
            }
            // Enemy action was made
            if (playerTurn && map.hasEnemy()) {
                try {
                    map.enemyTurn(this);
                } catch (InterruptedException e) {
                    logger.error("ayylmao at the GameManager.nextTurn(..) Method");
                    Platform.exit();
                    Thread.currentThread().interrupt();
                }
            } else {
                map.playerTurn(this);
                updateBuffIcons();
                updateTempVisiblePoints();
                map.turnTick();
                for(CurrentHeroChangeListener listener:heroChangeListeners){
                	listener.onHeroChange(getHeroManager().getCurrentHero());
                }
            }

        } else {
            moveViewTo(getHeroManager().getCurrentHero().getX(), getHeroManager().getCurrentHero().getY());
            updateBuffIcons();
            updateTempVisiblePoints();
        }

        map.updateVisibilityArray();
        dayNightClock.tick();
        turnTickLiveTiles();
        abilitySelected = AbilitySelected.MOVE;
        abilitySelectedChanged = true;
        backgroundChanged = true;
        gameChanged = true;
        minimapChanged = true;
        saveOriginator.writeMap(this.map, this.getObjectives(), this.gameState);
    //    update(this.tracker,null);
    }
    
    /**
     * Add Listener for Hero Change Event
     * @param listener		CurrentHeroChangeListener
     */
    public void addCurrentHeroChangeListener(CurrentHeroChangeListener listener){
    	if(!this.heroChangeListeners.contains(listener)){
    		this.heroChangeListeners.add(listener);
    	}
    }
    
    /**
     * Remove Listening event for Hero Change
     * @param listener		CurrentHeroChangeListener
     */
    public void removeCurrentHeroChangeListener(CurrentHeroChangeListener listener){
    	heroChangeListeners.remove(listener);
    }

    private void turnTickLiveTiles() {
        for (LiveTile liveTile
                : map.getLiveTiles()) {
            if (liveTile.hasEffect()) {
                liveTile.turnTick();
            }
        }
    }

    /**
     * Adds a temporarily visible point to the map at the x, y coordinates.
     * A single tile can be made visible by setting the radius to 0.
     *
     * @param x        the x coordinate of the tile to be set
     * @param y        the y coordinate of the tile to be set
     * @param radius   the radius (as a number of tiles) that should be set visible
     * @param duration the number of turns that the visible point should last
     */
    public void setTemporaryVisiblePoint(int x, int y, int radius, int duration) {
        int[] point = {x, y, radius, duration};
        tempVisiblePoints.add(point);
        map.setPointRadiusVisible(x, y, radius);
    }

    /**
     * Gets the x coordinate which is currently selected in the game
     *
     * @return the selected cordinate
     */
    public int getSelectionX() {
        return selectionX;
    }

    /**
     * Gets the y coordinate which is currently selected in the game
     *
     * @return the selected cordinate
     */
    public int getSelectionY() {
        return selectionY;
    }

    /**
     * Sets the point which is currently selected in the game
     */
    public void setSelection(int x, int y) {
        this.selectionX = x;
        this.selectionY = y;

        selectionChanged = true;
    }

    /**
     * Attempts to move to the selected Point
     */
    public void moveToSelected() {
        if (selectionX != -1) {
            // This short circuits but if it's not multiplayer the second statement will do nothing
            if (map.moveEntity(map.getCurrentTurnHero(), selectionX, selectionY) && multiplayerGameManager
                    .hookPlayerMove(selectionX, selectionY)) {
                map.updateVisibilityArray();
                visionChanged = true;
                // See if this hero has moved on a position that meets any movement objectives
                gameState.updateMovementGoal(selectionX, selectionY);
            }
        }

        if (map.getCurrentTurnHero().getActionPoints() < 2) {
            nextTurn();
        }
    }

    /**
     * Processes the use of ability 1
     */
    public void abilityOne() {
        ability(map.getCurrentTurnHero().getAbilities().get(0));
    }

    /**
     * Processes the use of ability 2
     */
    public void abilityTwo() {
        if(map.getCurrentTurnHero().getAbilities().size() < 2){
            return;
        }
        ability(map.getCurrentTurnHero().getAbilities().get(1));
    }

    /**
     * Processes the use of Weapon Ability
     */
    public void weaponAbility() {
    	logger.info("weapon abilities aren't implemented yet. Using slash ability");
    	//ability(map.getCurrentTurnHero().getWeaponAbility());
    	ability(new Slash());
    }

    /**
     * Processes the use of Utility Ability
     */
    public void utilityAbility() {
        ability(map.getCurrentTurnHero().getUtilityAbility());
    }

    /**
     * Processes the use of a given ability
     */
    public void ability(AbstractAbility ability) {

        logger.info(abilityInfo(map.getCurrentTurnHero(), ability));

        if (selectionX != -1 && !playerPerformingMove && !map.enemyPerformingMove()) {
            AbstractCharacter owner = map.getCurrentTurnHero();

            // Check range
            if (!(ability.inRange(owner.getX(), owner.getY(), selectionX, selectionY))) {
                logger.info("NOT IN RANGE.");
                toAbilityInfoDialogue("NOT IN RANGE.");
                return;
            }
        	if (ability.onCooldown()) {
        		logger.info("ABILITY IS ON COOL-DOWN.");
                toAbilityInfoDialogue("ABILITY IS ON COOL-DOWN.");
        		return;
        	}
            if (ability.getCostAP() > ((AbstractHero)owner).getActionPoints()) {
            	logger.info("NOT ENOUGH ABILITY POINTS.");
                toAbilityInfoDialogue("NOT ENOUGH ABILITY POINTS.");
            	return;
            }

            TargetDesignation targetDesignation;
            if (ability.canUseOnPoint()) {
            	targetDesignation = TargetDesignation.POINT;
            } else {
            	targetDesignation = getTargetDesignation(owner, selectionX, selectionY);
            }
            Thread abilityAnimationThread = new Thread(() -> playerAbilityHandler(owner, ability, targetDesignation));
            try {
                abilityAnimationThread.join();
                abilityAnimationThread.setDaemon(true);
            } catch (InterruptedException e) {
                logger.info("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
                Platform.exit();
                Thread.currentThread().interrupt();
            }

            if (targetDesignation == TargetDesignation.FRIEND && ability.canUseOnFriend()) {
                // Friend Character
                abilityAnimationThread.start();
            }
            else if (targetDesignation == TargetDesignation.FOE && ability.canUseOnFoe()) {
                // Foe Character OR Unknown Entity OR Targetable LiveTile

                if (!map.getTile(selectionX, selectionY).getEntities().isEmpty()
                        &&!(map.getTile(selectionX, selectionY).getEntities().get(0) instanceof Targetable)) {
                    logger.info("NOT A VALID TARGET.");
                    toAbilityInfoDialogue("NOT A VALID TARGET.");
                    return;
                }

                if(isMultiplayer()){
                    multiplayerGameManager.enableTemporaryBlock();
                    multiplayerGameManager.hookAttack(owner, ability, selectionX, selectionY);
                }

                abilityAnimationThread.start();
            }
            else if (ability.canUseOnPoint()){
                // Point
                abilityAnimationThread.start();
            }
        }
        gameChanged = true;
    }

    private void playerAbilityHandler(AbstractCharacter owner, AbstractAbility ability,
                                      TargetDesignation targetDesignation) {
        playerPerformingMove = true;
        AbilityAnimationHandler.executeCompleteTurn(owner, ability, selectionX, selectionY, this, targetDesignation);
        gameChanged = true;
        playerPerformingMove = false;
        if(owner instanceof AbstractHero) {
        	((AbstractHero)owner).changeAP(-ability.getCostAP());
        }
        // disable the temporary block if this is multiplayer
        if(isMultiplayer()) {
            multiplayerGameManager.disableTemporaryBlock();
        }
    }

    private String abilityInfo(AbstractCharacter owner, AbstractAbility ability) {
        StringBuilder message = new StringBuilder();
        message.append(owner.getImageName())
                .append(" attempting to use ability ")
                .append(ability.getName())
                .append(" from ")
                .append(owner.getX()).append(", ").append(owner.getY())
                .append(" to ");

        Tile tile = map.getTile(selectionX, selectionY);

        // Checking for Entities
        if (tile.getEntities().isEmpty()) {
            if (!tile.hasLiveTile()) {
                message.append("EMPTY POINT");
            }
        } else if (tile.hasEnemy()) {
            message.append("ENEMY ")
                    .append(tile.getEntities().get(0).getImageName());
        } else if (tile.hasScenery()) {
            message.append("SCENERY ")
                    .append(tile.getEntities().get(0).getImageName());
        } else if (tile.hasHero()) {
            message.append("HERO ")
                    .append(tile.getEntities().get(0).getImageName());
        } else {
            message.append("ENTITY ")
                    .append(tile.getEntities().get(0).getEntityType().toString());
        }
        // Checking for LiveTile
        if (tile.hasLiveTile()) {
            message.append("LIVETILE ")
                    .append(tile.getLiveTile().getLiveTileType().toString());
        }
        message.append(" at ")
            .append(selectionX).append(", ").append(selectionY);
//        toAbilityInfoDialogue(message.toString());
        return message.toString();
    }

    /**
     * Outputs a message to the ability information dialogue box.
     * This will overwrite any pre-existing messages.
     *
     * @param message string message to be displayed.
     */
    private void toAbilityInfoDialogue(String message) {
        controller.getUIController().messageAbilityDialogue(message);
    }

    private TargetDesignation getTargetDesignation(AbstractCharacter owner, int targetX, int targetY) {
        Tile tile = map.getTile(targetX, targetY);

        if (tile.getEntities().isEmpty()
                && (tile.getLiveTile() == null || !(tile.getLiveTile() instanceof Targetable))) {
            return TargetDesignation.POINT;
        }

        if ((tile.hasHero() && owner instanceof AbstractHero)
                || tile.hasEnemy() && owner instanceof AbstractEnemy) {
            return TargetDesignation.FRIEND;
        }

        return TargetDesignation.FOE;
    }

    /**
     * Updates the ability hotbar to display the current hero's abilities
     */
    public void updateAbilityHotbar() {
        controller.getUIController().refreshAbilityHotbar();
    }

    /**
     * If a given enemy was hit, will send signal to update any enemy hit and kill
     * objectives as necessary.
     *
     * @require target != null
     * @require oldHealth > 0
     */
    public void processEnemyHitsAndDeaths(AbstractEnemy enemy) {
        this.scoreCounter.changeScore(2000, '+'); // This was put here to see all objectives being met
        this.gameState.updateScoreGoals(this.scoreCounter);
        this.gameState.updateEnemyHitGoals(enemy.getEnemyType());
        this.killCount += 1; 
    }

    /**
     * Sets the mouses new location
     *
     * @param mapX position on map x
     * @param mapY position on map y
     */
    public void setMouseLocation(int mapX, int mapY) {

        if (!map.canSelectPoint(mapX, mapY)) {
            return;
        }

        if (hoverX == mapX && hoverY == mapY) {
            return;
        }

        hoverX = mapX;
        hoverY = mapY;

        selectionChanged = true;
    }

    /**
     * Moves a hero to a point
     *
     * @param x the X point to move to
     * @param y the Y point to move to
     */
    public void moveToPoint(int x, int y) {
        /**
         * DO NOT EDIT THIS CODE TO REMOVE DUPLICATIONS IT WILL BREAK MULTIPLAYER
         * SERIOUSLY JUST DO NOT DO IT
         *
         * DON'T DO IT
         *
         *
         * JUST DON'T TOUCH THIS CODE AT ALL
         */
        AbstractHero hero = map.getCurrentTurnHero();

        // This short circuits but if it's not multiplayer the second statement will do nothing
        if (map.moveEntity(map.getCurrentTurnHero(), x, y)) {
            map.updateVisibilityArray();
            visionChanged = true;
            gameState.updateMovementGoal(x, y);
        }

        updateLiveTileEffectsOnCharacter(hero);
        gameChanged = true;
        minimapChanged = true;
        visionChanged = true;
        movementChanged = true;


        centerMapOnCurrentHero();
    }

    /**
     * Sets the controller of the GameManager's FXML resources
     *
     * @param duxComController FXML controller of gameManager's resources
     */
    public void setController(DuxComController duxComController) {
        controller = duxComController;
    }

    /**
     * Appends a message to the game log
     *
     * @param message text to be added to the game log
     */
    public void writeToControllerGameLog(String message) {
        controller.getUIController().writeToLogBox(message);
    }

    /**
     * Sets the view port to have a given width
     *
     * @param viewPortWidth the desired view port width
     */
    public void setViewPortWidth(double viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    /**
     * Sets the view port to have a given height
     *
     * @param viewPortHeight the desired view port height
     */
    public void setViewPortHeight(double viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    /**
     * Added by Thomas Bricknell: Sets up the game state and tracker objects that GameManager will
     * handle based on the builder it is provided. Will generate objectives based on enemies on the
     * map, a basic score objective and set it to watch its own objective tracker for any instances
     * when objectives are met.
     *
     * @param builder objective builder from current map to use
     * @require builder != null
     */
    public void setInitialGameInternalFramework(ObjectiveBuilder builder) {
        this.builder = builder;
        this.gameState = new GameState(this.builder.getObjectives());
        this.tracker = new ObjectiveTracker(gameState, this.builder.getObjectives());
        this.tracker.addObserver(this);
    }

    public void loadExistingGameInternalFramework(ObjectiveTracker tracker) {
        this.tracker = tracker;
        this.builder = new ObjectiveBuilder(null);
        this.builder.setObjectives(tracker.getObjectives());
        this.gameState = this.tracker.getGameState();
        this.tracker.addObserver(this);
    }

    /**
     * To be called anywhere that a rendering bug is found to be caused. This
     * should re-solve most issues be completely redrawing the world.
     */
    public void fullRenderRefresh() {
        gameChanged = true;
        visionChanged = true;
        backgroundChanged = true;
        selectionChanged = true;
        movementChanged = true;
    }

    /**
     * For teams to use in notifying that an objective has been completed.
     *
     * @param tracker     objective tracker that this game manager is watching
     * @param placeHolder keeps Java happy with implementing Observer (put in anything) NOTE: Can be
     *                    edited later on by teams wishing to use this for different purposes
     */
    @Override
    public void update(Observable tracker, Object placeHolder) {
        List<Objective> trackerObjectives = this.tracker.getCompletedObjectives();

                // Check if any protection objectives have failed - exit to overworld
        // if so
        for (Objective o : this.tracker.getIncompleteObjectives()) {
            if (o instanceof ProtectionObjective) {
                logger.info("You've lost the level! Game Over.");
                returnToOverworld();
                return;
            }
        }

        int newCompletions = 0;
        for (Objective o: trackerObjectives) {
            if (o.getDisplayed() <= 1) {
                newCompletions++;
            }
        }

        // Only display newly completed objectives
        if (newCompletions <= 0) {
            return;
        }

        // Objective completion - display objective/s completed at that moment, only once
        for (Objective o : trackerObjectives) {
            if (!(o instanceof ProtectionObjective) && o.getDisplayed() <= 1) {
                logger.info("Completed: " + o.toString());
            }
            o.incrementDisplayed();
        }

        
        // Every objective met - successfully finish level
        if (this.tracker.allObjectivesMet()) {
            logger.info("All objective complete! Game over.");
         	LevelRegister.conquerLevel();
            Platform.runLater(() -> returnToOverworld()); // NEED TO PUT FALADOR ON OVERWORLD
            return;
        }
        

        
    }

    private void returnToOverworld(){
    	this.getController().stopGame();
        this.getController().getInterfaceManager().loadSegmentImmediate(InterfaceSegmentType.OVERWORLD,
        		this.getController().getInterfaceManager().getStage(), "overworld");
       
    }
    
    /**
     * Gets the current game state from the manager
     *
     * @return the instance of GameState
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Gets the current objective tracker from the manager
     *
     * @return the instance of ObjectiveTracker
     */
    public ObjectiveTracker getTracker() {
        return this.tracker;
    }

    /**
     * Gets the current objectives from the manager
     *
     * @return the list of Objectives
     */
    public List<Objective> getObjectives() {
        return this.tracker.getObjectives();
    }

    /**
     * Gets the current score system from the manager
     *
     * @return the instance of ScoreSystem
     */
    public ScoreSystem getScoreCounter() {
        return this.scoreCounter;
    }

    /**
     * Retrieves the ShopManager
     *
     * @return the location to the shop manager
     */
    public ShopManager getShopManager() {
        return this.shopManager;
    }

    /**
     * Gets the x coordinate of the current enemy's intended move
     *
     * @return x coordinate
     */
    public int getEnemyMoveIntentX() {
        return enemyMoveIntentX;
    }

    /**
     * Gets the y coordinate of the current enemy's intended move
     *
     * @return y coordinate
     */
    public int getEnemyMoveIntentY() {
        return enemyMoveIntentY;
    }

    /**
     * Sets the current enemy's move intent
     *
     * @param enemyMoveIntentX x coordinate of move
     * @param enemyMoveIntentY y coordinate of move
     */
    public void setEnemyMoveIntent(int enemyMoveIntentX, int enemyMoveIntentY) {
        this.enemyMoveIntentX = enemyMoveIntentX;
        this.enemyMoveIntentY = enemyMoveIntentY;
    }

    public int getCurrentAbilityOwnerX() {
        return currentAbilityOwnerX;
    }

    public int getCurrentAbilityOwnerY() {
        return currentAbilityOwnerY;
    }

    public void setCurrentAbilityOwner(int x, int y) {
        this.currentAbilityOwnerX = x;
        this.currentAbilityOwnerY = y;
    }

    /**
     * Gets the x coordinate of the current enemy's intended target
     *
     * @return target x coordinate
     */
    public int getCurrentAbilityTargetX() {
        return currentAbilityTargetX;
    }

    /**
     * Gets the y coordinate of the current enemy's intended target
     *
     * @return target y coordinate
     */
    public int getCurrentAbilityTargetY() {
        return currentAbilityTargetY;
    }

    /**
     * Sets the current enemy's target intent
     *
     * @param enemyTargetIntentX x coordinate of target
     * @param enemyTargetIntentY y coordinate of target
     */
    public void setCurrentAbilityTarget(int enemyTargetIntentX, int enemyTargetIntentY, boolean hitOrSplat) {
        this.currentAbilityTargetX = enemyTargetIntentX;
        this.currentAbilityTargetY = enemyTargetIntentY;
        this.currentlyAbilityHit = hitOrSplat;
    }

    /**
     * Returns the ability that is expected to be cast in the next
     * animation.
     *
     * @return ability to be cast.
     */
    public AbstractAbility getCurrentAbilityCast() {
        return this.currentAbilityCast;
    }

    /**
     * Sets the ability that is currently expected to be cast in the next
     * animation.
     *
     * @param abilityCast the new ability to be cast.
     */
    public void setCurrentAbilityCast(AbstractAbility abilityCast) {
        this.currentAbilityCast = abilityCast;
    }

    /**
     * Returns true iff the gameManager wished the animation handler to perform
     * a hit effect of a damage splat effect.
     *
     * @return true iff ability hit expected.
     */
    public boolean getHitOrSplat() {
        return currentlyAbilityHit;
    }

    /**
     * Gets the hero manager
     *
     * @return hero manager of current game
     */
    public HeroManager getHeroManager() {
        return map.getHeroManager();
    }

    /**
     * Gets the enemy manager
     *
     * @return enemy manager of current game
     */
    public EnemyManager getEnemyManager() {
        return map.getEnemyManager();
    }

    /**
     * Gets the game controller
     *
     * @return controller of current game
     */
    public DuxComController getController() {
        return this.controller;
    }

    /**
     * Gets the status of the game's need for panning
     *
     * @return true iff panning is required
     */
    public boolean getPanStatus() {
        return doPan;
    }

    /**
     * Retrieves the location of the player wallet, that can be used to store
     * currency, buy/sell items in the local npc shop.
     *
     * @return the location of the wallet
     */
    public PlayerWallet getGameWallet() {
        return gameWallet;

    }

    /**
     * Centres the display on the current hero
     */
    public void centerMapOnCurrentHero() {
        if (map.getCurrentTurnHero() == null) {
            return;
        }
        centerMapOnPoint(map.getCurrentTurnHero().getX(), map.getCurrentTurnHero().getY());
    }

    /**
     * Toggles the mini map on or off
     *
     * @return true if miniMapVisible has been set to true, false if
     * miniMapVisible has been set to false.
     */
    public boolean toggleMiniMap() {
        miniMapVisible = !miniMapVisible;
        return miniMapVisible;
    }

    /**
     * Toggles the chat on or off
     */
    public void toggleChat() {
        chatVisible = !chatVisible;
    }

    /**
     * Notify the controller that the UI (buttons, tabs etc) should be disabled or enabled.
     * <p>
     * This is necessary for sections of the game where the user should not be able to interact with the game, such as
     * during instruction sequences.
     *
     * @param disable If true, disable user interaction. If false, enable it.
     */
    public void setDisableUserInteraction(boolean disable) {
        Platform.runLater(() -> controller.setDisableUserInteraction(disable));
    }


    /**
     * Toggles the health bars on or off
     */
    public void toggleMiniHealthBars() {
        miniHealthBarVisible = !miniHealthBarVisible;
    }

    /**
     * Returns whether the game has changed
     *
     * @return game changed state
     */
    public boolean isGameChanged() {
        return gameChanged;
    }

    /**
     * Sets the game changed state to a given boolean
     *
     * @param gameChanged the new state
     */
    public void setGameChanged(boolean gameChanged) {
        this.gameChanged = gameChanged;
    }

    /**
     * Returns whether the turn graphics have changed
     *
     * @return turn graphics state
     */
    public boolean isTurnGraphicsChanged() {
        return turnGraphicsChanged;
    }

    /**
     * Sets the turn graphics changed state to a given boolean
     *
     * @param turnGraphicsChanged the new state
     */
    public void setTurnGraphicsChanged(boolean turnGraphicsChanged) {
        this.turnGraphicsChanged = turnGraphicsChanged;
    }

    /**
     * Returns whether the background has changed
     *
     * @return background changed state
     */
    public boolean isBackgroundChanged() {
        return backgroundChanged;
    }

    /**
     * Sets the background changed state to a given boolean
     *
     * @param backgroundChanged the new state
     */
    public void setBackgroundChanged(boolean backgroundChanged) {
        this.backgroundChanged = backgroundChanged;
    }

    /**
     * Returns whether the minimap has changed
     *
     * @return minimap changed state
     */
    public boolean isMiniMapChanged() {
        return minimapChanged;
    }

    /**
     * Sets the minimap changed state to a given boolean
     *
     * @param minimapChanged the new state
     */
    public void setMiniMapChanged(boolean minimapChanged) {
        this.minimapChanged = minimapChanged;
    }

    /**
     * Returns whether the selection has changed
     *
     * @return selection changed state
     */
    public boolean isSelectionChanged() {
        return selectionChanged;
    }

    /**
     * Sets the selection changed state to a given boolean
     *
     * @param selectionChanged the new state
     */
    public void setSelectionChanged(boolean selectionChanged) {
        this.selectionChanged = selectionChanged;
    }

    /**
     * Returns whether the vision has changed
     *
     * @return vision changed state
     */
    public boolean isVisionChanged() {
        return visionChanged;
    }

    /**
     * Sets the vision changed state to a given boolean
     *
     * @param visionChanged the new state
     */
    public void setVisionChanged(boolean visionChanged) {
        this.visionChanged = visionChanged;
    }

    /**
     * Returns whether the movement has changed
     *
     * @return movement changed state
     */
    public boolean isMovementChanged() {
        return movementChanged;
    }

    /**
     * Sets the movement changed state to a given boolean
     *
     * @param movementChanged the new state
     */
    public void setMovementChanged(boolean movementChanged) {
        this.movementChanged = movementChanged;
    }

    /**
     * Returns whether the minimap is visible
     *
     * @return minimap visibility
     */
    public boolean isMiniMapVisible() {
        return miniMapVisible;
    }

    /**
     * Sets the minimap visibility state to a given boolean
     *
     * @param miniMapVisible the new state
     */
    public void setMiniMapVisible(boolean miniMapVisible) {
        this.miniMapVisible = miniMapVisible;
    }

    /**
     * Returns whether the chat is visible
     *
     * @return chat visibility
     */
    public boolean isChatVisible() {
        return chatVisible;
    }

    /**
     * Sets the chat visibility state to a given boolean
     *
     * @param chatVisible the new state
     */
    public void setChatVisible(boolean chatVisible) {
        this.chatVisible = chatVisible;
    }

    /**
     * Returns whether the health bars are visible
     *
     * @return health bar visibility
     */
    public boolean isMiniHealthBarVisible() {
        return miniHealthBarVisible;
    }

    /**
     * Sets the mini health bar visibility state to a given boolean
     *
     * @param miniHealthBarVisible the new state
     */
    public void setMiniHealthBarVisible(boolean miniHealthBarVisible) {
        this.miniHealthBarVisible = miniHealthBarVisible;
    }

    /**
     * Loads the image of everything on the map, hidden or not, into the texture cache.
     */
    public void loadAllTextures() {
        loadEntityTextures();
        loadDynamicEntityTextures();
        loadTileTextures();
        loadLiveTileTextures();
    }

    private void loadDynamicEntityTextures() {
        Set<Class> loaded = new HashSet<>();
        for (DynamicEntity dynamicEntity : map.getDynamicEntities()) {
            if (loaded.contains(dynamicEntity.getClass())) {
                continue;
            }
            for (int i = 0; i < dynamicEntity.getPhases().size(); i++) {
                String resource =
                        DataRegisterManager.getEntityDataRegister().getData(dynamicEntity.getEntityType())
                                .getEntityTextureName();
                TextureRegister.getTexture(resource);
                TextureRegister.getFadedTexture(resource);
                dynamicEntity.advanceCurrentPhase();
            }
            loaded.add(dynamicEntity.getClass());
        }
    }

    private void loadLiveTileTextures() {
        Set<Class> loaded = new HashSet<>();
        for (LiveTile liveTile : map.getLiveTiles()) {
            if (loaded.contains(liveTile.getClass())) {
                continue;
            }
            for (int i = 0; i < liveTile.getNumberFrames(); i++) {
                String resource = liveTile.getCurrentFrameTexture();
                TextureRegister.getTexture(resource);
                TextureRegister.getFadedTexture(resource);
                liveTile.turnTick();
            }
            for (int i = 0; i < liveTile.getNumberBaseFrames(); i++) {
                String resource = liveTile.getCurrentBaseTexture();
                TextureRegister.getTexture(resource);
                TextureRegister.getFadedTexture(resource);
                liveTile.turnTick();
            }
            loaded.add(liveTile.getClass());
        }
    }

    private void loadTileTextures() {
        Set<TileType> loaded = new HashSet<>();
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Tile tile = map.getTile(i, j);
                if (loaded.contains(tile.getTileType())) {
                    continue;
                }
                String resource =
                        DataRegisterManager.getTileDataRegister().getData(tile.getTileType()).getTileTextureName();
                TextureRegister.getTexture(resource);
                TextureRegister.getFadedTexture(resource);
                loaded.add(tile.getTileType());
            }
        }
    }

    private void loadEntityTextures() {
        Set<EntityType> loaded = new HashSet<>();
        for (Entity entity : map.getEntities()) {
            if (loaded.contains(entity.getEntityType())) {
                continue;
            }
            String resource;
            if (entity instanceof AbstractScenery || entity instanceof AbstractCharacter) {
                resource = entity.getImageName();
            } else {
                resource = DataRegisterManager.getEntityDataRegister().getData(entity.getEntityType())
                        .getEntityTextureName();
            }
            TextureRegister.getTexture(resource);
            TextureRegister.getFadedTexture(resource);
            loaded.add(entity.getEntityType());
        }
    }

    public void setAbilitySelected(AbilitySelected ability) {
        abilitySelected = ability;
        abilitySelectedChanged = true;
        gameChanged = true;
    }

    /**
     * Toggles sound on and off
     *
     * @return true if the sound is muted, false if the sound is not muted
     */
    public boolean toggleMute() {
        SoundPlayer.toggleMusicMute();
        SoundPlayer.toggleSoundFxMute();
        muted = !muted;
        return muted;
    }

    public AbilitySelected getAbilitySelected() {
        return abilitySelected;
    }

    public void setAbilitySelectedChanged(boolean abilitySelectedChanged) {
        this.abilitySelectedChanged = abilitySelectedChanged;
    }

    public boolean isAbilitySelectedChanged() {
        return abilitySelectedChanged;
    }

    public void setInventoryChanged(boolean inventoryChanged) {
        this.inventoryChanged = inventoryChanged;
    }

    public boolean isInventoryChanged() {
        return this.inventoryChanged;
    }

    /**
     * Determines whether a given map coordinate is currently on screen
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true iff on screen, else false
     */
    public boolean onScreen(double x, double y) {
        // get the in game coordinates and real coordinates on screen
        int scaledWidth = (int) (TileDataRegister.TILE_WIDTH * scale);
        int scaledHeight = (int) (TileDataRegister.TILE_HEIGHT * scale);
        double basex = (map.getWidth() * scaledWidth) / 2.0
                + scaledWidth / 4.0;
        double basey = scaledHeight / 4.0;

        //reverse the math logic from the GameRenderer
        double realX = ((y - x) / 2.0) * scaledWidth + currentXOffset + xOffset + basex;
        double realY = ((y + x) / 2.0) * scaledHeight + currentYOffset + yOffset + basey;

        return !(realX < -0.1 * viewPortWidth - basex || realX > 1.1 * viewPortWidth + basex ||
                realY < -0.1 * viewPortHeight - basex || realY > 1.1 * viewPortHeight + basey);
    }

    /**
     * Determines whether a given Entity is currently on screen
     *
     * @param entity entity
     * @return true iff entity on screen, else false
     */
    public boolean onScreen(Entity entity) {

        if (entity.getXLength() == 1 && entity.getYLength() == 1 && Math.abs(entity.getElevation()) == 0) {
            return onScreen(entity.getX(), entity.getY());
        }

        //reverse the math logic from the GameRenderer
        for (int i = 0; i < entity.getXLength(); i++) {
            if (onScreen(entity.getX() - i, entity.getY())) {
                return true;
            }
        }

        for (int j = 0; j < entity.getYLength(); j++) {
            if (onScreen(entity.getX(), entity.getY() - j)) {
                return true;
            }
        }

        if (onScreen(entity.getX() - entity.getElevation(), entity.getY() - entity.getElevation())) {
            return true;
        }

        if (entity instanceof AbstractScenery) {
            double height = ((AbstractScenery) entity).getSceneryType().getHeight();
            if (onScreen(entity.getX() - entity.getElevation() - height,
                    entity.getY() - entity.getElevation() - height)) {
                return true;
            }
        }

        return false;

    }
    
    /**
     * returns is the GameManager is a multiplayer manager
     * @return
     * 			true if the gameManager is a multiplayer Manager, else false
     */		
    public boolean isMultiplayer() {
        return multiplayerGameManager.isMultiplayer();
    }


    /**
     * Runs an update on the UI to display the current turn heroes buff icons.
     */
    public void updateBuffIcons() {
        controller.getUIController().clearActiveEffectsGrid();
        for (int i = 0; i < map.getCurrentTurnHero().getHeroBuffs().size(); i++) {
            controller.getUIController().addActiveEffectIcon((AbstractBuff) map.getCurrentTurnHero().getHeroBuffs().get(i), i);
        }
    }

    /**
     * Sets the display status of the debug readouts in the top left corner
     *
     * @param displayDebug the new display setting
     */
    public void setDisplayDebug(boolean displayDebug) {
        this.displayDebug = displayDebug;
    }

    /**
     * Gets the display status of the debug readouts in the top left corner
     *
     * @return the display setting
     */
    public boolean isDisplayDebug() {
        return displayDebug;
    }
    
    /**
     * Saves the state of the game
     * save the current state of the map when this method is called
     */
    public void saveGame(){
    	saveOriginator.writeMap(this.map, this.getObjectives(), this.gameState);
    	saveGame.saveGame(saveOriginator);
    }
    

    
    /**
     * Gets the player stats that need to be saved
     * 
     * @return playerStats to be saved
     */
    public PlayerStats getPlayerStats() {
        /* Get score and kills */
        String score = String.valueOf(this.getScoreCounter().getScore());
        String kills = Integer.toString(this.killCount);
        
        playerStats.setScoreId("1"); /* This will be changed in SavePlayerStats */
        playerStats.setUserId(""); /* This will be changed when SavePlayerStats */
        playerStats.setScore(score);
        playerStats.setKills(kills);
        playerStats.setNewTimestamp();
        
        return playerStats;
    }

    
    
    
    /**
     * Enable multiplayer
     */
    public void enableMultiplayer() {
        logger.info("Trying to enable multiplayer, current state [{}]", isMultiplayer());
        if (!isMultiplayer()) {
            multiplayerGameManager = new MultiplayerGameManager(this, true);
        }
        logger.info("Multiplayer state is now [{}]", isMultiplayer());
    }

    /**
     * Gets the GameManager logger for use in other classes (does this defeat
     * the purpose of Logger lol idk)
     *
     * @return a Logger
     */
    public Logger getLogger() {
        return logger;
    }

    public void setPopupHeroIndex(int index) {
        controller.getUIController().setPopupHeroIndex(index);
    }
}

