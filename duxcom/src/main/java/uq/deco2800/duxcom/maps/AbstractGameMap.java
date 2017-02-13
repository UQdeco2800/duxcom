package uq.deco2800.duxcom.maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.entities.Chest;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.controllers.heroselectcontrollers.HeroSelectController;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.EnemyType;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.HeroType;
import uq.deco2800.duxcom.entities.scenery.AbstractScenery;
import uq.deco2800.duxcom.entities.scenery.StackableScenery;
import uq.deco2800.duxcom.graphics.scenery.SceneryType;
import uq.deco2800.duxcom.instructions.LevelInstructions;
import uq.deco2800.duxcom.inventory.ChestManager;
import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectivesystem.ObjectiveBuilder;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import uq.deco2800.duxcom.tiles.LiveTile;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.Array2D;
import uq.deco2800.duxcom.util.TurnTickable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by wondertroy on 09-Aug-16.
 */
public abstract class AbstractGameMap implements TurnTickable {

    protected Array2D<Tile> tiles;
    protected String name;
    protected HeroManager heroManager = new HeroManager();
    protected ChestManager chestManager = new ChestManager();
    protected EnemyManager enemyManager = new EnemyManager();
    protected ArrayList<Entity> entities = new ArrayList<>();
    private ObjectiveBuilder builder = new ObjectiveBuilder(null);
    private ScoreSystem scoring = new ScoreSystem();
    private static Logger logger = LoggerFactory.getLogger(AbstractGameMap.class);
    protected MapType mapType;

    //spawn points for each hero
    int[][] spawnPoints;



	/* Tickable related methods */


    /**
     * Returns the hero whose turn it currently is
     *
     * @return the current hero
     */
    public AbstractHero getCurrentTurnHero() {
        return heroManager.getCurrentHero();
    }

    /**
     * Gets the array of tiles of the map
     *
     * @return the map's tiles
     */
    public Array2D<Tile> getTiles() {
        return tiles;
    }

    /**
     * Gets the hero manager of the map
     *
     * @return map's hero manager
     */
    public HeroManager getHeroManager() {
        return heroManager;
    }

    /**
     * Gets the enemy manager of the map
     *
     * @return map's enemy manager
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Gets the entities on the map
     *
     * @return map's entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return The name of the maps.
     */
    public String getName() {
        return name;
    }

    /**
     * Get score system from map (initial only - testing purposes)
     *
     * @return score system in map
     */
    public ScoreSystem getScoreSystem() {
        return this.scoring;
    }

    /**
     * Gets objectives contained on this map.
     *
     * @return map's objectives
     * Added by Thomas Bricknell - 9/10/16
     */
    public List<Objective> getObjectives() {
        return builder.getObjectives();
    }

    /**
     * Retrieves a Tile at the specified coordinates.
     *
     * @param x The x-coordinate of the Tile to retrieve
     * @param y The y-coordinate of the Tile to retrieve
     * @return The Tile at the specified coordinates
     */
    public Tile getTile(int x, int y) {
        return tiles.get(x, y);
    }

    /**
     * Attempts to add an entity to the map at the entity's stored position
     *
     * @param entity the entity to add
     * @return true iff successfully added, else false
     */
    public boolean addEntity(Entity entity) {
        entities.add(entity);
        Collections.sort(entities);
        return true;
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

    public boolean addChest(Chest chest) {
        if (addEntity(chest)) {
            chestManager.addChest(chest);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to add a hero to the map at the hero's stored position
     *
     * @param hero the hero to add
     * @return true iff successfully added, else false
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
     * Attempts to add an enemy to the map at the enemy's stored position
     *
     * @param enemy the enemy to add
     * @return true iff successfully added, else false
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
     * SCALABLE SCENERY
     */
    public boolean addScenery(SceneryType sceneryType, int x, int y, double scale) {
        return addEntity(new AbstractScenery(sceneryType, x, y, scale));
    }

    /**
     * Adds a given type of scenery to the map at a given point
     *
     * @param sceneryType the type of the scenery
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @param xLength     the x length
     * @param yLength     the y length
     * @return true iff successfully added
     */
    public boolean addScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength) {
        return addEntity(new AbstractScenery(sceneryType, x, y, xLength, yLength));
    }

    /**
     * SCALED
     */
    public boolean addScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength, double scale) {
        return addEntity(new AbstractScenery(sceneryType, x, y, xLength, yLength, scale));
    }

    public boolean addStackableScenery(SceneryType sceneryType, int x, int y) {
        return addEntity(new StackableScenery(sceneryType, x, y));
    }

    /**
     * SCALED
     */
    public boolean addStackableScenery(SceneryType sceneryType, int x, int y, double scale) {
        return addEntity(new StackableScenery(sceneryType, x, y, scale));
    }

    public boolean addStackableScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength) {
        return addEntity(new StackableScenery(sceneryType, x, y, xLength, yLength));
    }

    /**
     * SCALED
     */
    public boolean addStackableScenery(SceneryType sceneryType, int x, int y, int xLength, int yLength, double scale) {
        return addEntity(new StackableScenery(sceneryType, x, y, xLength, yLength, scale));
    }

    /**
     * Attempts to add an enemy hit objective to this map of the specified
     * enemy type
     *
     * @param type - enemy type of enemy hit objective to add
     * @return true if enemies are present && an existing enemy hit objective
     * doesn't already exist && at least one enemy of given type exists,
     * else false.
     * @require type != null
     * Thomas Bricknell - 9/10/16
     */
    public boolean addEnemyHitObjective(EnemyType type) {

        // Check if enemies are present on map
        if (enemyManager.isEmpty()) {
            return false;
        }

        // Check to see if given type of enemy is present
        if (!this.enemyManager.hasType(type)) {
            return false;
        }

        // Check to make sure enemy type hasn't already been added as an enemy hit objective
        if (!this.builder.containsAHitObjective(type)) {
            this.builder.add(type);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to add an enemy kill objective to this map of the specified
     * enemy type
     *
     * @param type - enemy type of enemy kill objective to add
     * @return true if enemies are present && an existing enemy kill objective
     * doesn't already exist && there isn't less of given type of enemy
     * on the map than the goal specified, else false.
     * @require type != null
     * Thomas Bricknell - 9/10/2016
     */
    public boolean addEnemyKillObjective(EnemyType type, int goal) {
        // Check if enemies are present on map
        if (this.enemyManager.isEmpty()) {
            return false;
        }

        // Check to see if there's enough of specified type of enemy
        // on map so objective can be fulfilled
        if (this.enemyManager.getCountOfEnemyType(type) < goal) {
            return false;
        }

        // Check to make sure enemy type hasn't already been added as an enemy kill objective
        if (!this.builder.containsAKillObjective(type)) {
            this.builder.add(type, goal);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to add a protection objective to this map for the specified entity
     *
     * @param e         - entity type to add
     * @param threshold minimum number to remain before failure
     * @return true if the map isn't empty and the entity specified is present,
     * else false
     * @require e != null
     * Thomas Bricknell - 9/10/16
     */
    public boolean addProtectionObjective(EntityType e, int threshold) {
        if (this.getEntities().isEmpty()) {
            return false;
        }

        try {
            // Heroes
            HeroType heroType = HeroType.valueOf(e.toString());

            // Must be present on the map
            if (!this.heroManager.hasType(heroType)) {
                return false;
            }

            // Must not exceed number of particular hero on map
            if (getHeroCountBasedOnType(heroType) < threshold) {
                return false;
            }

        } catch (IllegalArgumentException ie) {
            // Other entities
            int count = 0;
            logger.info("Not a hero", ie);
            boolean result = false;
            for (Entity entity : this.getEntities()) {
                if (entity.getEntityType().equals(e)) {
                    result = true;
                    break;
                }
            }
            return result;
        }

        if (!this.builder.containsProtectionObjective(e)) {
            this.builder.add(e, threshold);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to add in a score objective, as long as one doesn't already
     * exist.
     *
     * @param goal - points goal
     * @return true if score objective is able to be added, else false.
     * @require goal > 0
     * Thomas Bricknell - 9/10/2016
     */
    public boolean addScoreObjective(int goal) {
        if (!this.builder.containsScoreObjective()) {
            this.builder.add(this.scoring, goal);
            return true;
        }
        return false;
    }

    private int subAddMovementObjective(int x, int y) {

        for (AbstractEnemy e : this.enemyManager.getEnemyList()) {
            if (e.getX() == x && e.getY() == y) {
                return 0;
            }
        }

        for (AbstractHero h : this.heroManager.getHeroList()) {
            if (h.getX() == x && h.getY() == y) {
                return 0;
            }
        }
        return 1;
    }

    public boolean addMovementObjective(int x, int y) {

        // No negative positions
        if (x < 0 || y < 0) {
            return false;
        }

        // No hero, enemy or other entity cannot be occupying the same space on startup
        // when adding a movement objective
        for (Entity e : this.entities) {
            if (e.getX() == x && e.getY() == y) {
                return false;
            }
        }

        int signal = subAddMovementObjective(x, y);
        if (signal == 0) {
            return false;
        }
        this.builder.add(x, y);
        return true;
    }

    /**
     * Get the number of the specified hero type on the map
     */
    private int getHeroCountBasedOnType(HeroType type) {
        int count = 0;
        for (AbstractHero h : this.heroManager.getHeroList()) {
            if (h.getHeroType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return The width of the DemoMap
     */
    public int getWidth() {
        return tiles.getWidth();
    }

    /**
     * @return The height of the DemoMap
     */
    public int getHeight() {
        return tiles.getHeight();
    }

    @Override
    public void turnTick() {
        for (int y = 0; y < tiles.getHeight(); y++) {
            for (int x = 0; x < tiles.getWidth(); x++) {
                Tile tile = tiles.get(x, y);
                tile.turnTick();

            }
        }
    }

    protected void addSelectedHeroes() {
        List<AbstractHero> heroes = new ArrayList<>();
        try {
            heroes = HeroSelectController.staticGetSelectedHeroes();
        } catch (NullPointerException npe) {
            return;
        }
        for (int i = 0; i < heroes.size(); i++) {
            AbstractHero currentHero = heroes.get(i);
            currentHero.setX(spawnPoints[i][0]);
            currentHero.setY(spawnPoints[i][1]);
            addHero(currentHero);
        }

    }

    /**
     * Initialises the map as an empty checkered map with the given dimensions and tiles
     * Should only be called in a map constructor
     *
     * @param tileType1 the TileType of the first tile in the pattern
     * @param tileType2 the TileType of the second tile in the pattern
     * @param width     the width of the map
     * @param height    the height of the map
     */
    public void initialiseEmptyCheckeredMap(TileType tileType1, TileType tileType2, int width, int height) {
        setSize(width, height);
        TileType[] floorPieces = {tileType1,
                tileType2};

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if ((x + y) % 2 == 0) {
                    tiles.set(x, y, new Tile(floorPieces[0]));
                } else {
                    tiles.set(x, y, new Tile(floorPieces[1]));
                }
            }
        }
    }

    public void scatterThatDirt(int startX, int startY, int height, int width) {

        ArrayList<Tile> pieces = new ArrayList<>();
        pieces.add(new Tile(TileType.RS_PATH1));
        pieces.add(new Tile(TileType.RS_PATH2));
        pieces.add(new Tile(TileType.RS_PATH3));
        pieces.add(new Tile(TileType.RS_PATH4));



        Random randomGen = new Random();
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                Tile tile = pieces.get(randomGen.nextInt(pieces.size()));
                tiles.set(i, j, tile);
                //SceneryType tree = trees.get(randomGen.nextInt(trees.size()));
            }
        }
    }

    /**
     * Sets a tile at a given point to a certain type of tile
     *
     * @param x        the x coordinate of the tile to be changed
     * @param y        the y coordinate of the tile to be changed
     * @param tileType the new tile type
     */
    public void setTile(int x, int y, TileType tileType) {
        getTile(x, y).setTileType(tileType);
    }


    protected void setSize(int width, int height) {
        tiles = new Array2D<>(width, height);
    }

    /**
     * Returns which map this is
     *
     * @return the type of map this is
     */
    public MapType getMapType() {
        return this.mapType;
    }
    
    public void removeEntity() {
    	/* CHANGE THIS */
    	ArrayList<Entity> replacement = new ArrayList<>();
    	for(Entity entity: entities) {
    		if(entity instanceof AbstractHero){
    			heroManager.removeHero((AbstractCharacter) entity);
    		} else if(entity instanceof AbstractEnemy) {
    			enemyManager.onDeath((AbstractCharacter) entity);
    		} else {
    			replacement.add(entity);
    		}
    	}
    	entities = replacement;
    }
    
}

