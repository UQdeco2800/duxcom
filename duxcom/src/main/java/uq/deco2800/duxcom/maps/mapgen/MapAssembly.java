package uq.deco2800.duxcom.maps.mapgen;

import javafx.application.Platform;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.controllers.LootPopUpController;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.entities.*;
import uq.deco2800.duxcom.entities.dynamics.DynamicEntity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyActionGenerator;
import uq.deco2800.duxcom.entities.enemies.listeners.DeathListener;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.BetaTester;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.listeners.ActionPointListener;
import uq.deco2800.duxcom.entities.heros.listeners.HealthListener;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.instructions.LevelInstructions;
import uq.deco2800.duxcom.interfaces.overlaymaker.popup.OverlayMakerPopUp;
import uq.deco2800.duxcom.loot.LootManager;
import uq.deco2800.duxcom.maps.AbstractGameMap;
import uq.deco2800.duxcom.maps.MapType;
import uq.deco2800.duxcom.maps.mapgen.biomes.BiomeType;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockGroup;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockPointMapper;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;
import uq.deco2800.duxcom.maps.mapgen.generator.biomegenerator.BiomeGenerator;
import uq.deco2800.duxcom.tiles.LiveTile;
import uq.deco2800.duxcom.tiles.LiveTileType;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.util.Array2D;
import uq.deco2800.duxcom.util.TurnTickable;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Stores a sequence of maps to be rendered. Represents multiple maps on one display. This class
 * pretends to be a normal map but actually contains all of them. <p> Created by liamdm on
 * 17/08/2016.
 */
public class MapAssembly implements TurnTickable, ActionPointListener, DeathListener {

    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapAssembly.class);

    /**
     * An extension of RunTimeException to be used for MapAssembly exceptions
     */
    public static class MapAssemblyRuntimeException extends RuntimeException {

        /**
         * Exception constructor
         *
         * @param string the message of the exception
         */
        public MapAssemblyRuntimeException(String string) {
            super(string);
        }

    }
    private MapType mapType;
    private static int blockSize = 3;

    private int width = 0;
    private int height = 0;

    private boolean entitiesUpdated = true;
    private boolean dynamicEntitiesUpdated = true;

    /**
     * Coordinates the block number to the entry in the tile map
     */
    protected HashMap<Integer, Array2D<Tile>> tileMap = new HashMap<>();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<LiveTile> liveTiles = new ArrayList<>();
    private ArrayList<DynamicEntity> dynamicEntities = new ArrayList<>();

    protected HeroManager heroManager = new HeroManager();
    protected EnemyManager enemyManager = new EnemyManager();
    EnemyActionGenerator enemyActionGenerator = new EnemyActionGenerator(this);
    protected DynamicsManager dynamicsManager = new DynamicsManager(this);
    protected BiomeGenerator generator = new BiomeGenerator();
    protected BlockGroup currentBlocks = new BlockGroup();

    // 2D array representation of visibility. A 1 indicates that the
    // corresponding map co-ordinates should be consider visible.
    protected int[][] visibilityArray;
    // A list of points to be set visible. Format: [x, y, radius]
    protected List<int[]> visiblePoints = new ArrayList<>();

    private LevelInstructions levelInstructions;

    /**
     * For Loots that are dropped on the map
     */
    protected LootManager lootManager = new LootManager(this);
    protected boolean lootStatusChanged = false;

    // false until the chests loot areas have been rendered once.
    private boolean chestsBeenRendered = false;

    /**
     * Gets the current BlockGroup
     *
     * @return the current BlockGroup
     */
    public BlockGroup getBlockCoordinateMap() {
        return currentBlocks;
    }

    /**
     * Adds a block whilst keeping the current block group list up to date
     *
     * @param blockNumber the number of the block to be added too
     * @param block       the block to be added
     */
    private void addBlock(int blockNumber, Array2D<Tile> block) {
        currentBlocks.addBlock(BlockPointMapper.tryConvertNumberToCoordinate(blockNumber));
        tileMap.put(blockNumber, block);
    }

    /**
     * Constructor using a preset level type
     */
    public MapAssembly() {
        generator = new BiomeGenerator(2584);

        ArrayList<Coordinate> blockSpawnPoints = new ArrayList<>();
        blockSpawnPoints.add(new Coordinate(0, 0));
        for (int i = 0; i < 40; ++i) {
            int x = new Random().nextInt(10);
            int y = new Random().nextInt(10);
            Coordinate c = new Coordinate(x, y);
            if (!tileMap.containsKey(c)) {
                blockSpawnPoints.add(new Coordinate(x, y));
            }
        }
        for (Coordinate c : blockSpawnPoints) {
            addBlock(BlockPointMapper.getBlockNumber(c), generator.generateBlock(blockSize, blockSize, BiomeType.TEST_REALISTIC));
        }

        AbstractHero hero = new BetaTester(0, 0);
        addHero(hero);

        heroManager.setHero(0);

        width = currentBlocks.getGroupBound().getWidth();
        height = currentBlocks.getGroupBound().getHeight();


    }

    protected String name;

    /**
     * Server constructor for the map assembly
     *
     * @param serverName the name of the server
     * @param mapToCopy  the map to copy
     */
    public MapAssembly(String serverName, AbstractGameMap mapToCopy) {
        // get sonar to shutup by using server name
        if (serverName.isEmpty()) {
            return;
        }

        copyMap(mapToCopy);
        MapAssembly.blockSize = mapToCopy.getWidth();
        addBlock(BlockPointMapper.getBlockNumber(new Coordinate(0, 0)), mapToCopy.getTiles());

        visibilityArray = new int[width][height];

        mapToCopy.getEntities().forEach(this::addEntity);
    }

    /**
     * Copy a map to this assembly
     *
     * @param mapToCopy the map to copy
     */
    private void copyMap(AbstractGameMap mapToCopy) {

        width = mapToCopy.getWidth();
        height = mapToCopy.getHeight();

        if (width != height) {
            throw new MapAssemblyRuntimeException("Sorry but map assembly does not currently support maps " +
                    "that are not square! Your map was " + mapToCopy.getWidth() + " by " + mapToCopy.getHeight());
        }

    }

    /**
     * Copy the tiles from an existing abstract map and put at 0, 0
     *
     * @param mapToCopy the map which is to be copied into the MapAssembly
     */
    public MapAssembly(AbstractGameMap mapToCopy) {
        copyMap(mapToCopy);
        MapAssembly.blockSize = mapToCopy.getWidth();
        addBlock(BlockPointMapper.getBlockNumber(new Coordinate(0, 0)), mapToCopy.getTiles());

        mapToCopy.getEntities().forEach(this::addEntity);

        this.heroManager = mapToCopy.getHeroManager();
        this.enemyManager = mapToCopy.getEnemyManager();

        heroManager.setHero(heroManager.getHeroList().size()-1);
        enemyManager.setEnemy(0);
        enemyManager.setMap(this);
        visibilityArray = new int[width][height];

        levelInstructions = mapToCopy.getLevelInstructions();
        mapType = mapToCopy.getMapType();
    }

    /**
     * Adds a health listener to all heroes in the map assembly
     *
     * @param healthListener the listener to be added
     */
    public void addHealthListenerAllHeroes(HealthListener healthListener) {
        heroManager.addHealthListenerAllHeroes(healthListener);
    }


    /**
     * Adds an action point listener to all heroes in the map assembly
     *
     * @param actionPointListener the listener to be added
     */
    public void addActionPointListenerAllHeroes(ActionPointListener actionPointListener) {
        heroManager.addActionPointListenerAllHeroes(actionPointListener);
    }


    /**
     * Returns the instructions for the level.
     *
     * @return the instructions for the level
     */
    public LevelInstructions getLevelInstructions() {
        return levelInstructions;
    }

    /**
     * Enables the listeners for the current hero.
     * <p>
     * This usually happens automatically on change of turn, but for the very first hero this has to be done manually.
     */
    public void initialiseStatusListeners() {
        heroManager.initialiseStatusListeners();
    }

    /**
     * Get the hero whose turn it currently is
     *
     * @return the hero whose turn it is
     */
    public AbstractHero getCurrentTurnHero() {
        return heroManager.getCurrentHero();
    }

    /**
     * Returns the next hero from the hero manager.
     *
     * @return the next hero.
     */
    public AbstractHero getNextTurnHero() {
        return heroManager.getNextHero();
    }

    public Entity getCurrentTurnEntity(boolean playerTurn) {
        if (!playerTurn && !enemyManager.isEmpty()) {
            return enemyManager.getCurrentEnemy();
        } else {
            return heroManager.getCurrentHero();
        }
    }

    /**
     * Gets the x point at which the entity whose turn it is lies
     *
     * @param playerTurn boolean representing whether or not it is the players turn
     * @return the x point of the entity
     */
    public int getCurrentTurnEntityX(boolean playerTurn) {
        return getCurrentTurnEntity(playerTurn).getX();
    }

    /**
     * Gets the y point at which the entity whose turn it is lies
     *
     * @param playerTurn boolean representing whether or not it is the players turn
     * @return the y point of the entity
     */
    public int getCurrentTurnEntityY(boolean playerTurn) {
        return getCurrentTurnEntity(playerTurn).getY();
    }


    /**
     * Gets a list of all physical entities on a map
     */
    public List<Entity> getEntities() {

        ArrayList<Entity> clonedEntities = new ArrayList<>(entities);

        Array2D<Tile> tileArray = tileMap.get(0);
        for (int i = 0; i < tileArray.getWidth(); i++) {
            for (int j = 0; j < tileArray.getHeight(); j++) {
                clonedEntities.addAll(tileArray.get(i, j).getEntities());
            }
        }

        Collections.sort(clonedEntities);

        return clonedEntities;
    }

    /**
     * Returns a list of all LiveTiles
     *
     * @return
     */
    public List<LiveTile> getLiveTiles() {
        liveTiles = new ArrayList<>();
        for (Array2D<Tile> tileArray : tileMap.values()) {
            for (Tile tile : tileArray.getList()) {
                if (tile.getLiveTile() != null) {
                    liveTiles.add(tile.getLiveTile());
                }
            }
        }

        return liveTiles;
    }

    /**
     * Gets a list of all dynamic entities in the assembly
     *
     * @return list of dynamic entities
     */
    public List<DynamicEntity> getDynamicEntities() {
        if (!dynamicEntitiesUpdated) {
            return dynamicEntities;
        }

        dynamicEntities.clear();

        Array2D<Tile> tileArray = tileMap.get(0);
        for (int i = 0; i < tileArray.getWidth(); i++) {
            for (int j = 0; j < tileArray.getHeight(); j++) {
                for (int k = 0; k < tileArray.get(i, j).getEntities().size(); k++) {
                    if (tileArray.get(i, j).getEntities().get(k) instanceof DynamicEntity) {
                        dynamicEntities.add((DynamicEntity) tileArray.get(i, j).getEntities().get(k));
                    }
                }
            }
        }

        dynamicEntitiesUpdated = false;

        return dynamicEntities;
    }

    /**
     * Gets the block at the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Array2D<Tile> getBlock(int x, int y) {
        Coordinate selectedCoordinate = BlockPointMapper.getBlockPoint(x, y);
        Integer selectedBlockNumber = BlockPointMapper.getBlockNumber(selectedCoordinate);

        return tileMap.get(selectedBlockNumber);
    }

    /**
     * Switches between Enemy turns and Player turns
     */
    public void playerTurn(GameManager gameManager) {
    	heroManager.getCurrentHero().onEndTurn();
        heroManager.nextHero();

        gameManager.setPopupHeroIndex(heroManager.getHeroIndex());
        gameManager.updateAbilityHotbar();
        gameManager.moveViewTo(heroManager.getCurrentHero().getX(), heroManager.getCurrentHero().getY());
        if (!enemyManager.isEmpty()) {
            gameManager.setPlayerTurn(true);
        }
        gameManager.updateLiveTileEffectsOnCharacter(heroManager.getCurrentHero());
        if (heroManager.getCurrentHero().getHealth() <= 0) {
            endTurn(gameManager);
        }
    }

    /**
     * adds a list of given hero's to the spawn points on the map
     *
     * @param heroes - the heroes to add to the map
     */
    public void addHeroes(List<AbstractHero> heroes) throws NotEnoughSpawnPointsException {
        List<Entity> entityList = getEntities();
        List<Entity> heroSpawns = new ArrayList<>();

        //get the hero spawn points
        heroSpawns.addAll(entityList.stream().filter(entity ->
                entity.getEntityType().equals(EntityType.HERO_SPAWN)).collect(Collectors.toList()));

        if (heroSpawns.size() < heroes.size()) {
            throw new NotEnoughSpawnPointsException("There are not enough spawn points to accommodate the heroes!");
        }

        if (!heroSpawns.isEmpty()) {
            //set the heroes position
            for (int i = 0; i < heroes.size(); i++) {
                AbstractHero currentHero = heroes.get(i);
                Entity heroSpawn = heroSpawns.get(i);

                currentHero.setX(heroSpawn.getX());
                currentHero.setY(heroSpawn.getY());
            }

            //remove any remaining heroSpawn entities
            for (Entity entity : heroSpawns) {
                getTile(entity.getX(), entity.getY()).removeMovableEntity();
            }

            //add the hero
            heroes.forEach(this::addHero);
        }

    }

    /**
     * Creates a new thread to perform the enemy turn process
     */
    public void enemyTurn(GameManager gameManager) throws InterruptedException {

        if (enemyManager.getCurrentEnemy().isHidden() || enemyManager.getCurrentEnemy().getHealth() <= 0) {
            if (enemyManager.hasRevealedEnemy()) {
                enemyManager.nextEnemy();
                gameManager.nextTurn();
            } else {
                endTurn(gameManager);
            }
            return;
        }

        Thread enemyTurnThread = new Thread(() -> {
            if (!enemyManager.isEmpty() && !enemyManager.isPerformingTurn()) {
                gameManager.setPlayerTurn(false);
                enemyManager.takeTurn(gameManager);
                enemyManager.nextEnemy();
                gameManager.getMap().turnTick();
                Platform.runLater(() -> gameManager.nextTurn());
            } else {
                endTurn(gameManager);
            }
        });
        enemyTurnThread.join();
        enemyTurnThread.setDaemon(true);
        enemyTurnThread.start();
    }

    private void endTurn(GameManager gameManager) {
        gameManager.setPlayerTurn(false);
        Platform.runLater(() -> gameManager.nextTurn());
    }

    /**
     * Returns the enemy performing move status
     *
     * @return true iff enemy is currently performing a turn
     */
    public boolean enemyPerformingMove() {
        return enemyManager.isPerformingTurn();
    }

    /**
     * Returns true iff the EnemyManager hosts at lease one AbstractEnemy
     *
     * @return true iff EnemyManager is not empty
     */
    public boolean hasEnemy() {
        return !enemyManager.isEmpty();
    }

    /**
     * Retrieves a Tile at the specified coordinates.
     *
     * @param x The x-coordinate of the Tile to retrieve
     * @param y The y-coordinate of the Tile to retrieve
     * @return The Tile at the specified coordinates
     */
    public Tile getTile(int x, int y) {
        int realX = BlockPointMapper.coordinateWithinBlock(x);
        int realY = BlockPointMapper.coordinateWithinBlock(y);
        return getBlock(x, y).get(realX, realY);
    }

    /**
     * Retrieves the tiled in a given radius around a point on the map. The tiles returned are in a circular shape.
     * See http://i.imgur.com/9PGer.jpg for more details
     *
     * @param x0     the x coordinate of the point
     * @param y0     the y coordinate of the point
     * @param radius the radius of the point. 0 will mean a single tile returned, 1 will mean 3x3 square etc.
     * @return the list of tiles that are within the defined radius of the point
     */

    public List<Tile> getTilesAroundPoint(int x0, int y0, int radius) {
        List<Tile> tileList = new ArrayList<>();

        // radius 1
        if (radius == 1) {
            for (int a = -1; a < 2; a++) {
                for (int b = -1; b < 2; b++) {
                    if (canSelectPoint(x0 + a, y0 + b)) {
                        tileList.add(getTile(x0 + a, y0 + b));
                    }
                }
            }
            return tileList;
        }

        //get the tiles in a given radius
        for (int y = -radius; y <= radius; y++) {
            for (int x = -radius; x <= radius; x++) {
                if (x * x + y * y <= radius * radius + radius * 0.85) {
                    if (canSelectPoint(x0 + x, y0 + y)) {
                        tileList.add(getTile(x0 + x, y0 + y));
                    }
                }
            }
        }
        return tileList;
    }

    public List<Coordinate> getSurroundingLiveTileCoordinates(LiveTileType liveTileType, int x, int y, int radius) {
        List<Coordinate> ret = new ArrayList<>();

        // radius 1
        if (radius == 1) {
            for (int a = -1; a < 2; a++) {
                for (int b = -1; b < 2; b++) {
                    if (canSelectPoint(x + a, y + b) && getTile(x + a, y + b).hasLiveTile() && getTile(x + a, y + b).getLiveTile().getLiveTileType() == liveTileType) {
                        ret.add(new Coordinate(x + a, y + b));
                    }
                }
            }
            return ret;
        }

        for (int b = -radius; b <= radius; b++) {
            for (int a = -radius; a <= radius; a++) {

                if (canSelectPoint(x + a, y + b) && getTile(x + a, y + b).hasLiveTile() && getTile(x + a, y + b).getLiveTile().getLiveTileType() == liveTileType) {
//                    System.out.println("tile at " + (x+a) + ", " + (y+b) + " is " + getTile(x + a, y + b).getLiveTile().getLiveTileType());
                    ret.add(new Coordinate(x + a, y + b));
                }
            }
        }
        return ret;
    }



    /**
     * Gets the entity at a given set of coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the tile at the given point
     */
    public List<Entity> getEntities(int x, int y) {
        return getTile(x, y).getEntities();
    }

    public Entity getMovableEntity(int x, int y) {
        return getTile(x, y).getMovableEntity();
    }

    public LiveTile getLiveTile(int x, int y) {
        return getTile(x, y).getLiveTile();
    }

    /**
     * Moves a given entity to a given point if it is empty. Returns false if it is an invalid move.
     * <p> Note that entities belong to a map, and that the map should decide where entities are
     * allowed to move to. Entities can decide weather they are able to move a certain distance.
     * This is why heroes have a move method as well as the map.
     *
     * @param entity       the entity to be moved
     * @param destinationX the x point to move the entity to
     * @param destinationY the y point to move the entity to
     */
    public Boolean moveEntity(Entity entity, int destinationX, int destinationY) {
        int originX = entity.getX();
        int originY = entity.getY();

        //noinspection Duplicates
        if (!getTile(destinationX, destinationY).isOccupied() && entity.move(destinationX, destinationY)) {
            getTile(destinationX, destinationY).addEntity(entity);
            getTile(originX, originY).removeMovableEntity();
            entitiesUpdated = true;
            if (entity instanceof DynamicEntity) {
                dynamicEntitiesUpdated = true;
            }

            return true;
        }
        return false;
    }


    /**
     * Move an entity from one point to another.
     *
     * @param originX      the x point of the entity
     * @param originY      the y point of the entity
     * @param destinationX the x destination point
     * @param destinationY the y destination point
     * @return true if entity was successfully moved
     */
    public Boolean moveEntity(int originX, int originY, int destinationX, int destinationY) {
        if ((!getTile(originX, originY).isOccupied()) || getTile(destinationX, destinationY).isOccupied()) {
            return false;
        }
        Entity entity = getMovableEntity(originX, originY);
        return moveEntity(entity, destinationX, destinationY);
    }

    /**
     * Update the movement range of the current hero when notified that their AP has changed.
     *
     * @param newActionPoints  the hero's new action points
     * @param baseActionPoints the hero's action points before change
     */
    @Override
    public void onActionPointChange(int newActionPoints, int baseActionPoints) {
        AbstractHero currentHero = getCurrentTurnHero();
        if (currentHero instanceof BetaTester) {
            // Beta tester can move anywhere, so set all its movement costs to zero.
            Float[][] betaTesterMovementRange = new Float[getWidth()][getHeight()];
            for (Float[] f : betaTesterMovementRange) {
                Arrays.fill(f, 0.0f);
            }
            currentHero.setMovementCost(betaTesterMovementRange);
        } else {
            getCurrentTurnHeroMovementRange();
        }
    }

    /**
     * Calculate the minimum cost to reach each position on the map for the current turn hero.
     *
     * @return An array of the minimum costs for the current hero to reach each position on the map. If the hero does
     * not have enough AP to reach a point, the cost is null.
     */
    private void getCurrentTurnHeroMovementRange() {
        AbstractHero currentHero = getCurrentTurnHero();
        Float[][] movementRange = new Float[getWidth()][getHeight()];
        Integer[][] movementPath = new Integer[getWidth()][getHeight()];

        calculateMoves(currentHero.getX(), currentHero.getY(), movementRange, movementPath);
        //Set movement cost to current position to 0
        movementRange[currentHero.getX()][currentHero.getY()] = 0.0f;
        movementPath[currentHero.getX()][currentHero.getY()] = null;
        currentHero.setMovementCost(movementRange);
        currentHero.setMovementPath(movementPath);
    }


    /**
     * Calculate the cost of reaching all other tiles on the map from position (x, y), given that it has a starting
     * cost of movementCost[x][y]. If any of these costs is less than the existing cost in movementCost, update it.
     *
     * @param x            starting x position from which to calculate move costs.
     * @param y            starting y position from which to calculate move costs.
     * @param movementCost an array of movement costs of moving from an original position (not necessarily (x, y)).
     */
    private void calculateMoves(int x, int y, Float[][] movementCost, Integer[][] movementPath) {
//        float cost = (float) (1 / getCurrentTurnHero().getSpeed());
//        LiveTile liveTile = getTile(x, y).getLiveTile();
//        if (liveTile != null && !liveTile.isDestroyed()){
//        	cost *= DataRegisterManager.getLiveTileRegister().getData(liveTile.getLiveTileType()).getMovementModifier();
//        } else {
//        	cost *= DataRegisterManager.getTileDataRegister().getData(getTile(x, y).getTileType()).getMovementModifier();
//        }

        /** ----------------------------------------------------------------------------------------------------------*/

        // Good ratio was 0.7 / 1.3

        int range = (int) (getCurrentTurnHero().getActionPoints() * 0.8);
        int xPos = getCurrentTurnHero().getX();
        int yPos = getCurrentTurnHero().getY();

        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {

                // Calculates a rough approximation of a circle
                boolean inRadius = Math.abs(i) + Math.abs(j) < (range * 1.5);
                // Check that the point to be set is actually on the map
                boolean inMapX = (x + i < movementCost.length) && (x + i >= 0);
                boolean inMapY = (y + j < movementCost[0].length) && (y + j >= 0);
                if (inRadius && inMapX && inMapY) {
                    movementCost[x + i][y + j] = (float) (Point2D.distance(xPos, yPos, xPos + i, yPos + j) * 1.3);

                    if (i > 0) {
                        movementPath[x + i][y + j] = 0;
                    }
                    if (i < 0) {
                        movementPath[x + i][y + j] = 1;
                    }
                    if (j > 0) {
                        movementPath[x + i][y + j] = 2;
                    }
                    if (j < 0) {
                        movementPath[x + i][y + j] = 3;
                    }
                }
            }
        }

        /** ----------------------------------------------------------------------------------------------------------*/
//        cost = (movementCost[x][y] == null) ? cost : cost + movementCost[x][y];
//        //Move x + 1
//        calculateMoveCost(x + 1, y, cost, movementCost, 0, movementPath);
//        //Move x - 1
//        calculateMoveCost(x - 1, y, cost, movementCost, 1, movementPath);
//        //Move y + 1
//        calculateMoveCost(x, y + 1, cost, movementCost, 2, movementPath);
//        //Move y - 1
//        calculateMoveCost(x, y - 1, cost, movementCost, 3, movementPath);

    }

    /**
     * Determine whether to update the cost of moving to position (x, y).
     * <p>
     * This method checks whether (x, y) is still a valid position on the map. If it is, and the new cost to reach that
     * position is less than the existing cost (movementCost[x][y]), then the cost is updated.
     *
     * @param x            x position of cost to be checked
     * @param y            y position of cost to be checked
     * @param cost         new cost to reach position (x, y)
     * @param movementCost an array of movement costs to reach positions on the map
     */
    private void calculateMoveCost(int x, int y, float cost, Float[][] movementCost, int direction,
                                   Integer[][] movementPath) {
        if (canSelectPoint(x, y)) {
            if (getTile(x, y).isOccupied() && !getTile(x, y).getMovableEntity().equals(getCurrentTurnHero())) {
                // If the tile is occupied this is not a valid path
                return;
            }

            if (getCurrentTurnHero().getActionPoints() >= cost
                    && (movementCost[x][y] == null || cost < movementCost[x][y])) {
                movementCost[x][y] = cost;
                movementPath[x][y] = direction;
                calculateMoves(x, y, movementCost, movementPath);
            }
        }
    }

    /**
     * Adds an entity to the map assembly
     *
     * @param entity the entity to be added
     * @return true iff the entity is successfully added
     */
    public boolean addEntity(Entity entity) {
        entitiesUpdated = true;
        if (entity instanceof DynamicEntity) {
            dynamicEntitiesUpdated = true;
        }
        return getTile(entity.getX(), entity.getY()).addEntity(entity);
    }

    /**
     * Adds a given type of scenery to the map at a given point
     *
     * @param sceneryType the type of the scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @return true iff successfully added
     */
    public boolean addScenery(SceneryType sceneryType, int x, int y) {
        return addEntity(new AbstractScenery(sceneryType, x, y));
    }

    /**
     * Adds a live tile to the map assembly
     *
     * @param liveTile the LiveTile to be added
     * @return true iff LiveTile addition got the succ
     */
    public boolean addLiveTile(LiveTile liveTile) {
        return getTile(liveTile.getX(), liveTile.getY()).addLiveTile(liveTile);
    }

    /**
     * Adds a hero to the map assembly
     *
     * @param hero the hero to be added
     * @return true iff the hero is successfully added
     */
    public boolean addHero(AbstractHero hero) {
        if (addEntity(hero)) {
            heroManager.addHero(hero);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return The width of the DemoMap
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the DemoMap
     */
    public int getHeight() {
        return height;
    }

    /**
     * Ticks the turn of all entities in the assembly
     */
    @Override
    public void turnTick() {
        getEntities().forEach(Entity::turnTick);
        getLiveTiles().forEach(LiveTile::turnTick);
    }

    /**
     * Adds an enemy to the map assembly
     *
     * @param enemy the enemy to be added
     * @return true iff the enemy is successfully added
     */
    public boolean addEnemy(AbstractEnemy enemy) {
        if (addEntity(enemy)) {
            enemyManager.addEnemy(enemy);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the point is on the game map
     */
    public boolean canSelectPoint(int x, int y) {
        return currentBlocks.contains(x, y);
    }

    /**
     * Returns the size of the blocks in the assembly
     *
     * @return the block size
     */
    public static int getBlockSize() {
        return blockSize;
    }

    /**
     * Sets the size of the blocks in the assembly
     *
     * @param blockSize the new block size
     */
    public static void setBlockSize(int blockSize) {
        MapAssembly.blockSize = blockSize;
    }

    public DynamicsManager getDynamicsManager() {
        return this.dynamicsManager;
    }

    public EnemyActionGenerator getEnemyActionGenerator() {
        return this.enemyActionGenerator;
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Adds a death listener to all enemies in the map assembly
     *
     * @param deathListener the listener to be added
     */
    public void addDeathListenerAllEnemies(DeathListener deathListener) {
        enemyManager.addDeathListenerAllEnemies(deathListener);
    }

    /**
     * Adds a death listener to all heroes in the map assembly
     *
     * @param deathListener the listener to be added
     */
    public void addDeathListenerAllHeroes(DeathListener deathListener) {
        heroManager.addDeathListenerAllHeroes(deathListener);
    }

    /**
     * Adds a death listener to all enemies in the map assembly to the enemy manager
     */
    public void addDeathListenerAllEnemiesToManager() {
        addDeathListenerAllEnemies(enemyManager);
    }

    /**
     * Adds a death listener to all heroes in the map assembly to the hero manager
     */
    public void addDeathListenerAllHeroesToManager() {
        addDeathListenerAllHeroes(heroManager);
    }

    @Override
    public void onDeath(AbstractCharacter character) {
        if (character instanceof AbstractHero) {
            AbstractHero hero = (AbstractHero) character;
            getTile(hero.getX(), hero.getY()).removeMovableEntity();
            addScenery(SceneryType.HEROGRAVE, hero.getX(), hero.getY());
        } else {
            AbstractEnemy enemy = (AbstractEnemy) character;
            getTile(enemy.getX(), enemy.getY()).removeMovableEntity();
            PickableEntities deathMagma = new DeathMagma(enemy.getX(), enemy.getY());
            addEntity(deathMagma);
            deathMagma.setLootRarity(lootManager.getDefinedRarity(enemy));
            lootManager.makeLoot(deathMagma, deathMagma.getLootRarity());
            dynamicEntitiesUpdated = true;
            setLootStatusChanged(true);
            addEntity(new DeathMagma(enemy.getX(), enemy.getY()));
        }
        if (heroManager.getHeroList().isEmpty() || enemyManager.getEnemyList().isEmpty()) {
            // Gameover stuff
        }
    }

    /**
     * Check if there are extra, or less loot on map
     *
     * @return true if loot status has changed
     */
    public boolean isLootStatusChanged() {
        return lootStatusChanged;
    }

    /**
     * Set the loot status on the map has changed
     *
     * @param value true if loot status has changed false if no change
     */
    public void setLootStatusChanged(boolean value) {
        lootStatusChanged = value;
    }

    /**
     * If the chests loot area hasnt been rendered, render it. If it has been, don't render it again
     * @return whether or not the chests have been rendered
     */
    public boolean haveChestsBeenRendered() {
        if (!chestsBeenRendered) {
            logger.debug("RENDERING CHEST LOOT AREA");
            chestsBeenRendered = true;
            return true;
        }
        return false;
    }

    /**
     * Obtain a List of dead enemy entities that have not been looted
     *
     * @return a list of unlooted dead enemies
     */
    public List<PickableEntities> getUnlootedDeath() {
        return lootManager.getAllUnlooted();
    }

    /**
     * Open up Loot window
     */
    public void openLootWindow() throws IOException {
        if (!lootManager.getInvInLootArea(getCurrentTurnHero()).isEmpty()) {
            LootPopUpController controller = (LootPopUpController) OverlayMakerPopUp
                    .makeWithGameManager(GameLoop.getCurrentGameManager()
                                    .getController().getGamePane(), "/ui/fxml/lootPopUp.fxml",
                            GameLoop.getCurrentGameManager()).getController();
            controller.show();
        }
    }

    /**
     * Returns reference of LootManager created in this map
     *
     * @return LootManager created in this map
     */
    public LootManager getLootManager() {
        return lootManager;
    }

    /**
     * Updates the visibility array for the map relevant to the vision of the
     * heroes, visible enemies, and any points that have be set to visible
     * (by abilities, etc.)
     */

    public void updateVisibilityArray() {
        // Hide the map ie. reset everything back to 0 (hidden)
        hideMap();
        List<AbstractHero> heroes = heroManager.getHeroList();
        List<AbstractEnemy> enemies = enemyManager.getEnemyList();


        // Update visiblity array based on all the heroes on the map
        for (AbstractHero hero : heroes) {
            characterVisionHandler(hero);
        }

        // Update array based on points set by setPointVisible or
        // setPointRadiusVisible
        for (int[] visiblePoint : visiblePoints) {
            pointVisionHandler(visiblePoint[0], visiblePoint[1], visiblePoint[2]);
        }

        // Update array based on visible enemies
        enemies.stream().filter(enemy -> !enemy.isHidden()).forEach(this::characterVisionHandler);
        updateEntityHidden();
    }

    /**
     * Updates visibility array to set the tiles around a hero to visible
     * within the range of that hero's visibilityRange.
     *
     * @param character
     */
    private void characterVisionHandler(AbstractCharacter character) {
        int x = character.getX();
        int y = character.getY();
        int range = 2;
        if (character instanceof AbstractHero) {
            range = ((AbstractHero) character).getVisibilityRange();
        }

        setRadiusVisible(x, y, range);
    }

    /**
     * Updates the visibility array to set a radius around a point on the map
     * to visible.
     */
    private void pointVisionHandler(int x, int y, int radius) {
        if (radius == 0) {
            visibilityArray[x][y] = 1;
        }

        setRadiusVisible(x, y, radius);
    }

    /**
     * Calculates a round approximation of a circle and updates the visibility
     * array to set all the tiles in that circle around a point to be visible.
     *
     * @param x     the x coordinate of the tile to be set
     * @param y     the y coordinate of the tile to be set
     * @param range the radius (as a number of tiles) that should be set
     *              visible.
     */
    private void setRadiusVisible(int x, int y, int range) {
        for (int j = -range; j <= range; j++) {
            for (int k = -range; k <= range; k++) {
                // Calculates a rough approximation of a circle
                boolean inRadius = Math.abs(j) + Math.abs(k) < (range * 1.5);
                // Check that the point to be set is actually on the map
                boolean inMapX = (x + j < visibilityArray.length) && (x + j >= 0);
                boolean inMapY = (y + k < visibilityArray[0].length) && (y + k >= 0);
                if (inRadius && inMapX && inMapY) {
                    visibilityArray[x + j][y + k] = 1;
                }
            }
        }
    }

    /**
     * Updates the hidden status of the entities on the map. Sets them to
     * visible if they are in the players vision or hidden if they are in the
     * fog of war.
     */
    public void updateEntityHidden() {
        List<Entity> entitiesOnMap = getEntities();
        for (Entity entityOnMap : entitiesOnMap) {
            if (visibilityArray[entityOnMap.getX()][entityOnMap.getY()]
                    != 1) {
                entityOnMap.setHidden(true);
            } else {
                entityOnMap.setHidden(false);
            }
        }
    }

    /**
     * Returns the map's visibility array.
     *
     * @return the visibility array of the map
     */
    public int[][] getVisibilityArray() {
        return visibilityArray;
    }

    /**
     * Updates the visibility array to set the entire map to be visible.
     */
    public void revealMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                visibilityArray[x][y] = 1;
            }
        }
    }

    /**
     * Updates the visibility array to set the entire map to be hidden.
     */
    public void hideMap() {
        visibilityArray = new int[width][height];
    }

    /**
     * Sets a single tile on the map to be visible.
     *
     * @param x the x coordinate of the tile to be set
     * @param y the y coordinate of the tile to be set
     */
    public void setPointVisible(int x, int y) {
        if (this.canSelectPoint(x, y)) {
            int[] point = {x, y, 0};
            visiblePoints.add(point);
        }
    }

    /**
     * Sets all the tiles in a radius around a point on the map to be visible.
     *
     * @param x      the x coordinate of the tile to be set
     * @param y      the y coordinate of the tile to be set
     * @param radius the radius (as a number of tiles) that should be set
     *               visible.
     */
    public void setPointRadiusVisible(int x, int y, int radius) {
        if (this.canSelectPoint(x, y)) {
            int[] point = {x, y, radius};
            visiblePoints.add(point);
        }
    }

    /**
     * Sets a point that was previously set to visible to be hidden. This
     * function should be used to reset individual tiles set with
     * setPointVisible() and areas set with setPointRadiusVisible(). The entire
     * radius around a point set with setPointRadiusVisible() will be hidden by
     * this function.
     *
     * @param x the x coordinate of the tile to be set hidden
     * @param y the y coordinate of the tile to be set hidden
     */
    public void setPointHidden(int x, int y) {
        for (int i = 0; i < visiblePoints.size(); i++) {
            if (visiblePoints.get(i)[0] == x && visiblePoints.get(i)[1] == y) {
                visiblePoints.remove(i);
            }
        }
    }

    public void toggleEntitiesChanged() {
        this.entitiesUpdated = true;
    }

    /**
     * Get the tileMap of the current map
     *
     * @return
     * @author Wise Quackers
     */
    public Map<Integer, Array2D<Tile>> getTileMap() {
        return this.tileMap;
    }
    
    /**
     * Get the MapType of the current map
     * @return the MapType of the map
     */
    public MapType getMapType(){
    	return this.mapType;
    }

    public int getCurrentHeroIndex() {
        return heroManager.getHeroIndex();
    }
    
}

