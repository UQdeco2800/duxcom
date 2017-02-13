package uq.deco2800.duxcom.maps.mapgen;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;

import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.entities.dynamics.Water;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.enemies.EnemyManager;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyActionGenerator;
import uq.deco2800.duxcom.entities.enemies.enemychars.EnemyKnight;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.HeroManager;
import uq.deco2800.duxcom.entities.heros.Ibis;
import uq.deco2800.duxcom.entities.heros.Knight;
import uq.deco2800.duxcom.entities.heros.Priest;
import uq.deco2800.duxcom.entities.heros.listeners.ActionPointListener;
import uq.deco2800.duxcom.entities.heros.listeners.HealthListener;
import uq.deco2800.duxcom.maps.DemoMap;
import uq.deco2800.duxcom.maps.EmptyMap;
import uq.deco2800.duxcom.maps.TestMovementRangeMap;
import uq.deco2800.duxcom.maps.mapgen.bounds.BlockGroup;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.Array2D;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test the MapAssembly class
 * @author Alex McLean
 */
@RunWith(MockitoJUnitRunner.class)
public class MapAssemblyTest {

    private static Logger logger = LoggerFactory.getLogger(MapAssemblyTest.class);

    private MapAssembly mapAssembly;

    @Mock
    private HeroManager heroManagerMock;
    @Mock
    private EnemyManager enemyManagerMock;
    @Mock
    private HealthListener healthListenerMock;
    @Mock
    private ActionPointListener actionPointListenerMock;
    @Mock
    private AbstractEnemy abstractEnemyMock;
    @Mock
    private AbstractHero abstractHeroMock;
    @Mock
    private HashMap<Integer, Array2D<Tile>> tileMapMock;
    @Mock
    private Tile tileMock;
    @Mock
    private DemoMap demoMapMock;
    @Mock
    private EnemyActionGenerator enemyActionGeneratorMock;
    @Mock
    private GameManager gameManagerMock;

    @Test
    public void testMapAssemblyRuntimeException() {
        String exceptionMessage = "";
        when(demoMapMock.getWidth()).thenReturn(10);
        when(demoMapMock.getHeight()).thenReturn(9);
        try {
            mapAssembly = new MapAssembly(demoMapMock);
        } catch (MapAssembly.MapAssemblyRuntimeException exception) {
            exceptionMessage = exception.getMessage();
            logger.error("Non-square map", exception);
        }
        assertEquals("Sorry but map assembly does not currently support maps that are not square! Your map was 10 by 9", exceptionMessage);
    }

    @Test
    public void testNotEmptyEnemyManager() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(10);
        when(demoMapMock.getHeight()).thenReturn(10);
        when(enemyManagerMock.isEmpty()).thenReturn(false);
        when(enemyManagerMock.numberEnemies()).thenReturn(10);
        mapAssembly = new MapAssembly(demoMapMock);
        verify(enemyManagerMock, times(1)).setMap(mapAssembly);
        //verify(enemyManagerMock, times(0)).setEnemy(9);
    }

    @Test
    public void getBlockCoordinateMap() throws Exception {
        mapAssembly = new MapAssembly();
        BlockGroup blockGroup = mapAssembly.getBlockCoordinateMap();
        assertNotNull(blockGroup);
    }

    @Test
    public void addHealthListenerAllHeroes() throws Exception {
        mapAssembly = new MapAssembly();
        mapAssembly.heroManager = heroManagerMock;
        mapAssembly.enemyManager = enemyManagerMock;
        mapAssembly.addHealthListenerAllHeroes(healthListenerMock);
        verify(heroManagerMock, times(1)).addHealthListenerAllHeroes(healthListenerMock);
    }

    @Test
    public void addActionPointListenerAllHeroes() throws Exception {
        mapAssembly = new MapAssembly();
        mapAssembly.heroManager = heroManagerMock;
        mapAssembly.addActionPointListenerAllHeroes(actionPointListenerMock);
        verify(heroManagerMock, times(1)).addActionPointListenerAllHeroes(actionPointListenerMock);
    }

    @Test
    public void initialiseStatusListeners() throws Exception {
        mapAssembly = new MapAssembly();
        mapAssembly.heroManager = heroManagerMock;
        mapAssembly.initialiseStatusListeners();
        verify(heroManagerMock, times(1)).initialiseStatusListeners();
    }

    @Test
    public void getCurrentTurnHero() throws Exception {
        mapAssembly = new MapAssembly();
        mapAssembly.heroManager = heroManagerMock;
        mapAssembly.getCurrentTurnHero();
        verify(heroManagerMock, times(1)).getCurrentHero();
    }

    @Test
    public void getCurrentTurnEntityPoint() throws Exception {
        when(heroManagerMock.getCurrentHero()).thenReturn(abstractHeroMock);
        when(enemyManagerMock.getCurrentEnemy()).thenReturn(abstractEnemyMock);

        mapAssembly = new MapAssembly();
        mapAssembly.heroManager = heroManagerMock;
        mapAssembly.enemyManager = enemyManagerMock;
        mapAssembly.getCurrentTurnEntityX(false);
        mapAssembly.getCurrentTurnEntityY(false);

        when(enemyManagerMock.isEmpty()).thenReturn(false);
        mapAssembly.getCurrentTurnEntityX(true);
        mapAssembly.getCurrentTurnEntityY(true);

        when(enemyManagerMock.isEmpty()).thenReturn(true);
        mapAssembly.getCurrentTurnEntityX(true);
        mapAssembly.getCurrentTurnEntityY(true);

        when(enemyManagerMock.isEmpty()).thenReturn(true);
        mapAssembly.getCurrentTurnEntityX(false);
        mapAssembly.getCurrentTurnEntityY(false);

        verify(enemyManagerMock, times(2)).getCurrentEnemy();
        verify(heroManagerMock, times(6)).getCurrentHero();
    }

    @Test
    public void getEntities() throws Exception {
        mapAssembly = new MapAssembly();
        assertNotNull(mapAssembly.getEntities());
    }

    @Test
    public void getDynamicEntities() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        demoMap.addEntity(new Water(0, 0, -1));
        mapAssembly = new MapAssembly(demoMap);
        assertNotNull(mapAssembly.getDynamicEntities());
    }

    @Test
    public void getBlock() throws Exception {
        mapAssembly = new MapAssembly();
        mapAssembly.tileMap = tileMapMock;
        mapAssembly.getBlock(0, 0);
        verify(tileMapMock).get(0);
    }

    @Test
    public void getTileByCoordinate() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(TileType.DT_GRASS_DARK_1, mapAssembly.getTile(0, 0).getTileType());
    }

    @Test
    public void getTileByPoint() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(TileType.DT_GRASS_DARK_1, mapAssembly.getTile(0, 0).getTileType());
    }

    @Test
    public void getEntityByCoordinate() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(Priest.class, mapAssembly.getMovableEntity(10, 8).getClass());
    }

    @Test
    public void getEntityByPoint() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(Priest.class, mapAssembly.getMovableEntity(10, 8).getClass());
    }

    @Test
    public void moveEntityByEntity() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        demoMap.addEntity(abstractEnemyMock);
        when(abstractEnemyMock.getX()).thenReturn(9);
        when(abstractEnemyMock.getY()).thenReturn(9);

        when(abstractEnemyMock.move(10, 10)).thenReturn(true);
        assertFalse(mapAssembly.moveEntity(abstractEnemyMock, 10, 10));

        when(abstractEnemyMock.move(10, 10)).thenReturn(false);
        assertFalse(mapAssembly.moveEntity(abstractEnemyMock, 10, 10));

    }

    @Test
    public void moveEntityByPoint() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        assertFalse(mapAssembly.moveEntity(9, 9, 1, 1));

        demoMap.addEntity(abstractEnemyMock);
        when(abstractEnemyMock.getX()).thenReturn(9);
        when(abstractEnemyMock.getY()).thenReturn(9);

        when(abstractEnemyMock.move(10, 10)).thenReturn(true);
        assertFalse(mapAssembly.moveEntity(9, 9, 10, 10));

        when(abstractEnemyMock.move(10, 10)).thenReturn(false);
        assertFalse(mapAssembly.moveEntity(9, 9, 10, 10));

    }

	/**
	 * Ensure that when the current turn hero's AP changes that they are given an new, non-null array of movement
	 * costs.
	 */
	@Test
	public void testNotNullMovementRangeOnActionPointChange() {
		mapAssembly = new MapAssembly();
		mapAssembly.onActionPointChange(0, 100);
		assertNotNull(mapAssembly.getCurrentTurnHero().getMovementCost());

		DemoMap demoMap = new DemoMap("test");
		mapAssembly = new MapAssembly(demoMap);
		mapAssembly.onActionPointChange(0, 100);
		assertNotNull(mapAssembly.getCurrentTurnHero().getMovementCost());
	}

	/**
	 * Tests movement range calculations on a designated test map.
	 *
	 * Test map contains only two ducks and normal (no movement modifier) tiles.
	 */
	@Test
    @Ignore
	public void testMovementRangeOnActionChange() {
		mapAssembly = new MapAssembly(new TestMovementRangeMap("test"));
        mapAssembly.getHeroManager().nextHero();
        assertTrue(mapAssembly.getCurrentTurnHero().getY() == 8);

		mapAssembly.onActionPointChange(mapAssembly.getCurrentTurnHero().getActionPoints(),
				mapAssembly.getCurrentTurnHero().getBaseActionPoints());

		// Manually set the expected cost array  (the correct range for the duck at (10, 8)
		Float[][] expectedRange = new Float[mapAssembly.getWidth()][mapAssembly.getHeight()];
		for (int i = 0; i < expectedRange.length; i++) {
			for (int j = 0; j < expectedRange[0].length; j++) {
				float cost = Math.abs(mapAssembly.getCurrentTurnHero().getX() - i)
						+ Math.abs(mapAssembly.getCurrentTurnHero().getY() - j);
				if (cost <= mapAssembly.getCurrentTurnHero().getActionPoints()) {
					expectedRange[i][j] = cost;
				}
			}
		}
		// Some of the costs need specific setting because they are affected by the position of the two ducks.
		expectedRange[10][8] = 0.0f;
		expectedRange[10][9] = null;
		expectedRange[10][10] = 4.0f;
		expectedRange[10][11] = 5.0f;
		expectedRange[10][12] = 6.0f;
		expectedRange[10][13] = 7.0f;
		expectedRange[10][14] = null;
		expectedRange[10][15] = null;
		
		assertArrayEquals(expectedRange, mapAssembly.getCurrentTurnHero().getMovementCost());
		
		//Manually set the expected path array 
		Integer[][] expectedPath = new Integer[mapAssembly.getWidth()][mapAssembly.getHeight()];
		expectedPath[10][1] = 3;
		expectedPath[9][2] = 3;
		expectedPath[10][2] = 3;
		expectedPath[11][2] = 3;
		expectedPath[8][3] = 3;
		expectedPath[9][3] = 3;
		expectedPath[10][3] = 3;
		expectedPath[11][3] = 3;
		expectedPath[12][3] = 3;
		expectedPath[7][4] = 3;
		expectedPath[8][4] = 3;
		expectedPath[9][4] = 3;
		expectedPath[10][4] = 3;
		expectedPath[11][4] = 3;
		expectedPath[12][4] = 3;
		expectedPath[13][4] = 3;
		expectedPath[6][5] = 3;
		expectedPath[7][5] = 3;
		expectedPath[8][5] = 3;
		expectedPath[9][5] = 3;
		expectedPath[10][5] = 3;
		expectedPath[11][5] = 3;
		expectedPath[12][5] = 3;
		expectedPath[13][5] = 3;
		expectedPath[14][5] = 3;
		expectedPath[5][6] = 3;
		expectedPath[6][6] = 3;
		expectedPath[7][6] = 3;
		expectedPath[8][6] = 3;
		expectedPath[9][6] = 3;
		expectedPath[10][6] = 3;
		expectedPath[11][6] = 3;
		expectedPath[12][6] = 3;
		expectedPath[13][6] = 3;
		expectedPath[14][6] = 3;
		expectedPath[15][6] = 3;
		expectedPath[4][7] = 3;
		expectedPath[5][7] = 3;
		expectedPath[6][7] = 3;
		expectedPath[7][7] = 3;
		expectedPath[8][7] = 3;
		expectedPath[9][7] = 3;
		expectedPath[10][7] = 3;
		expectedPath[11][7] = 3;
		expectedPath[12][7] = 3;
		expectedPath[13][7] = 3;
		expectedPath[14][7] = 3;
		expectedPath[15][7] = 3;
		expectedPath[16][7] = 3;
		expectedPath[3][8] = 1;
		expectedPath[4][8] = 1;
		expectedPath[5][8] = 1;
		expectedPath[6][8] = 1;
		expectedPath[7][8] = 1;
		expectedPath[8][8] = 1;
		expectedPath[9][8] = 1;
		expectedPath[10][8] = null;
		expectedPath[11][8] = 0;
		expectedPath[12][8] = 0;
		expectedPath[13][8] = 0;
		expectedPath[14][8] = 0;
		expectedPath[15][8] = 0;
		expectedPath[16][8] = 0;
		expectedPath[17][8] = 0;
		expectedPath[4][9] = 2;
		expectedPath[5][9] = 2;
		expectedPath[6][9] = 2;
		expectedPath[7][9] = 2;
		expectedPath[8][9] = 2;
		expectedPath[9][9] = 2;
		expectedPath[10][9] = null;
		expectedPath[11][9] = 2;
		expectedPath[12][9] = 2;
		expectedPath[13][9] = 2;
		expectedPath[14][9] = 2;
		expectedPath[15][9] = 2;
		expectedPath[16][9] = 2;
		expectedPath[5][10] = 2;
		expectedPath[6][10] = 2;
		expectedPath[7][10] = 2;
		expectedPath[8][10] = 2;
		expectedPath[9][10] = 2;
		expectedPath[10][10] = 1;
		expectedPath[11][10] = 2;
		expectedPath[12][10] = 2;
		expectedPath[13][10] = 2;
		expectedPath[14][10] = 2;
		expectedPath[15][10] = 2;
		expectedPath[6][11] = 2;
		expectedPath[7][11] = 2;
		expectedPath[8][11] = 2;
		expectedPath[9][11] = 2;
		expectedPath[10][11] = 2;
		expectedPath[11][11] = 2;
		expectedPath[12][11] = 2;
		expectedPath[13][11] = 2;
		expectedPath[14][11] = 2;
		expectedPath[7][12] = 2;
		expectedPath[8][12] = 2;
		expectedPath[9][12] = 2;
		expectedPath[10][12] = 2;
		expectedPath[11][12] = 2;
		expectedPath[12][12] = 2;
		expectedPath[13][12] = 2;
		expectedPath[8][13] = 2;
		expectedPath[9][13] = 2;
		expectedPath[10][13] = 2;
		expectedPath[11][13] = 2;
		expectedPath[12][13] = 2;
		expectedPath[9][14] = 2;
		expectedPath[10][14] = null;
		expectedPath[11][14] = 2;
		
		

		assertArrayEquals(expectedPath, mapAssembly.getCurrentTurnHero().getMovementPath());
	}

    @Test
    public void addEntity() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        assertTrue(mapAssembly.addEntity(abstractHeroMock));
    }

    @Test
    public void addHero() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        assertTrue(mapAssembly.addHero(abstractHeroMock));
        when(abstractHeroMock.getX()).thenReturn(10);
        when(abstractHeroMock.getY()).thenReturn(8);
        assertFalse(mapAssembly.addHero(abstractHeroMock));
    }

    @Test
    public void getWidth() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(40, mapAssembly.getWidth());
    }

    @Test
    public void getHeight() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(40, mapAssembly.getHeight());
    }

    @Test
    public void turnTick() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        when(abstractHeroMock.getX()).thenReturn(0);
        when(abstractHeroMock.getY()).thenReturn(0);
        mapAssembly.addEntity(abstractHeroMock);
        mapAssembly.turnTick();
        verify(abstractHeroMock, times(1)).turnTick();
    }

    @Test
    public void canSelectPoint() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);

        assertFalse(mapAssembly.canSelectPoint(50, 50));
        assertTrue(mapAssembly.canSelectPoint(30, 30));
    }

    @Test
    public void getBlockSize() throws Exception {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        assertEquals(40, MapAssembly.getBlockSize());
    }

    @Test
    public void hiddenEnemyTest() {
        DemoMap demoMap = new DemoMap("test");
        mapAssembly = new MapAssembly(demoMap);
        AbstractEnemy enemy0 = new EnemyKnight(0, 0);
        AbstractEnemy enemy1 = new EnemyKnight(2, 2);
        mapAssembly.addEnemy(enemy0);
        mapAssembly.addEnemy(enemy1);
        AbstractHero hero0 = new Ibis(25, 25);
        mapAssembly.addHero(hero0);

        mapAssembly.updateVisibilityArray();
        mapAssembly.updateEntityHidden();
        // Test 0: Both enemies are hidden as hero0 is far away
        assertTrue(enemy0.isHidden());
        assertTrue(enemy1.isHidden());

        hero0.setX(1);
        hero0.setY(1);
        mapAssembly.updateVisibilityArray();
        mapAssembly.updateEntityHidden();
        // Test 1: Both enemies are now revealed as her0 is adjacent
        assertFalse(enemy0.isHidden());
        assertFalse(enemy1.isHidden());
    }

    @Test
    public void getVisibilityTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        mapAssembly.hideMap();
        int[][] expectedVision0 = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0},
        };
        int[][] actualVision = mapAssembly.getVisibilityArray();
        assertTrue(Arrays.deepEquals(expectedVision0, actualVision));
        //assertEquals(expectedVision0, actualVision);

        mapAssembly.revealMap();
        actualVision = mapAssembly.getVisibilityArray();
        int[][] expectedVision1 = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1},
        };
        assertTrue(Arrays.deepEquals(expectedVision1, actualVision));
        //assertEquals(expectedVision1, mapAssembly.getVisibilityArray());
    }

    @Test
    public void getHeroManagerTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        // Assert that heroManagerMock was copied succ
        assertEquals(mapAssembly.getHeroManager(), demoMapMock.getHeroManager());
    }

    @Test
    public void getEnemyManagerTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        // Assert that enemyManager was copied succ
        assertEquals(mapAssembly.getEnemyManager(), demoMapMock.getEnemyManager());
    }

    @Test
    public void getEnemyActionGeneratorTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        mapAssembly.enemyActionGenerator = enemyActionGeneratorMock;
        // Assert that EnemyActionManager is stored locally succ
        assertEquals(enemyActionGeneratorMock, mapAssembly.getEnemyActionGenerator());
    }

    @Test
    public void hasEnemyTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        // Test 0 manager is empty thus hasEnemy is false
        when(enemyManagerMock.isEmpty()).thenReturn(true);
        assertFalse(mapAssembly.hasEnemy());
        // Test 1 manager is not empty thus hasEnemy is true
        when(enemyManagerMock.isEmpty()).thenReturn(false);
        assertTrue(mapAssembly.hasEnemy());
    }

    @Test
    public void enemyPerformingMoveTest() {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        // Test 0 true
        when(enemyManagerMock.isPerformingTurn()).thenReturn(true);
        assertTrue(mapAssembly.enemyPerformingMove());
        // Test 1 false
        when(enemyManagerMock.isPerformingTurn()).thenReturn(false);
        assertFalse(mapAssembly.enemyPerformingMove());

    }

    @Test @Ignore
    public void endTurnTestHiddenEnemy() throws InterruptedException {
        when(demoMapMock.getEnemyManager()).thenReturn(enemyManagerMock);
        when(demoMapMock.getHeroManager()).thenReturn(heroManagerMock);
        when(demoMapMock.getWidth()).thenReturn(3);
        when(demoMapMock.getHeight()).thenReturn(3);
        mapAssembly = new MapAssembly(demoMapMock);

        // Test 0 setPlayerTurn is not called unless enemy-turn related functionality is called
        verify(gameManagerMock, never()).setPlayerTurn(false);
        verify(enemyManagerMock, times(0)).nextEnemy();
        verify(gameManagerMock, times(0)).setPlayerTurn(false);
        verify(gameManagerMock, times(0)).nextTurn();

        when(enemyManagerMock.getCurrentEnemy()).thenReturn(abstractEnemyMock);
        when(abstractEnemyMock.isHidden()).thenReturn(true);
        mapAssembly.enemyTurn(gameManagerMock);
        verify(enemyManagerMock, times(1)).nextEnemy();
        verify(gameManagerMock, times(1)).setPlayerTurn(false);
        verify(gameManagerMock, times(1)).nextTurn();
    }

    @Test
    public void updateVisibilityArrayTest() {
    	// Test 1: 1 hero on map (vision outside of range of map)
    	AbstractHero knight = new Knight(1,1);
        knight.setVisibilityRange(50);
        EmptyMap testMap = new EmptyMap("test");
        testMap.addHero(knight);

        mapAssembly = new MapAssembly(testMap);
        mapAssembly.updateVisibilityArray();

        int[][] expectedVision1 = new int [mapAssembly.getWidth()][mapAssembly.getHeight()];
        for (int x = 0; x < mapAssembly.getWidth(); x++){
        	for (int y = 0; y < mapAssembly.getHeight(); y++) {
        		expectedVision1[x][y] = 1;
        	}
        }

        int[][] actualVision1 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision1, actualVision1);

        // Test 2: 1 hero on map (vision full in range of map)
        knight.setVisibilityRange(1);
        knight.setX(2);
        knight.setY(2);

        int[][] expectedVision2 = new int [mapAssembly.getWidth()][mapAssembly.getHeight()];
        expectedVision2[1][2] = 1;
        expectedVision2[2][1] = 1;
        expectedVision2[2][2] = 1;
        expectedVision2[2][3] = 1;
        expectedVision2[3][2] = 1;

        mapAssembly.updateVisibilityArray();
        int[][] actualVision2 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision2, actualVision2);

        // Test 3: 2 heroes on map (intersecting vision)
        AbstractHero knight2 = new Knight(1,1);
        knight2.setVisibilityRange(1);
        EmptyMap testMap2 = new EmptyMap("test");
        testMap2.addHero(knight);
        testMap2.addHero(knight2);

        MapAssembly mapAssembly2 = new MapAssembly(testMap2);
        mapAssembly2.updateVisibilityArray();
        
        expectedVision2[0][1] = 1;
        expectedVision2[1][0] = 1;
        expectedVision2[1][1] = 1;

        int[][] actualVision3 = mapAssembly2.visibilityArray;
        assertArrayEquals(expectedVision2, actualVision3);

        // Test 4: Set point visible
        knight.setVisibilityRange(1);
        knight.setX(0);
        knight.setY(0);
        testMap.addHero(knight);

        mapAssembly = new MapAssembly(testMap);
        mapAssembly.setPointVisible(2, 2);
        mapAssembly.updateVisibilityArray();

        int[][] expectedVision4 = new int [mapAssembly.getWidth()][mapAssembly.getHeight()];
        // Knight visibility
        expectedVision4[0][0] = 1;
        expectedVision4[0][1] = 1;
        expectedVision4[1][0] = 1;
        // Point visibility
        expectedVision4[2][2] = 1;

        int[][] actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision4, actualVision4);

        // Test 5: Hide visible point
        mapAssembly.setPointHidden(2, 2);
        mapAssembly.updateVisibilityArray();

        expectedVision4[2][2] = 0;

        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision4, actualVision4);

        // Test 6: Hide visible point in hero vision (should remain visible)
        mapAssembly.setPointVisible(1, 0);
        mapAssembly.updateVisibilityArray();
        mapAssembly.setPointHidden(1, 0);
        mapAssembly.updateVisibilityArray();

        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision4, actualVision4);

        // Test 7: Set point visible with radius
        mapAssembly.setPointRadiusVisible(0, 5, 2);
        mapAssembly.updateVisibilityArray();

        expectedVision4[0][3] = 1;
        expectedVision4[0][4] = 1;
        expectedVision4[0][5] = 1;
        expectedVision4[0][6] = 1;
        expectedVision4[0][7] = 1;
        expectedVision4[1][4] = 1;
        expectedVision4[1][5] = 1;
        expectedVision4[1][6] = 1;
        expectedVision4[2][5] = 1;

        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision4, actualVision4);
        

        // Test 8: Hide visible point with radius
        mapAssembly.setPointHidden(0, 5);
        mapAssembly.updateVisibilityArray();

        int[][] expectedVision8 = new int [mapAssembly.getWidth()][mapAssembly.getHeight()];
        // Knight visibility
        expectedVision8[0][0] = 1;
        expectedVision8[0][1] = 1;
        expectedVision8[1][0] = 1;

        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision8, actualVision4);
        
        // Test 9: Set point visible that is not on the map (should do nothing)
        mapAssembly.setPointVisible(mapAssembly.getWidth() + 1,
        		mapAssembly.getHeight() + 1);
        mapAssembly.updateVisibilityArray();
        
        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision8, actualVision4);
        
        // Test 10: Hide point not on map
        mapAssembly.setPointHidden(mapAssembly.getWidth() + 1,
        		mapAssembly.getHeight() + 1);
        mapAssembly.updateVisibilityArray();
        
        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision8, actualVision4);
        
        // Test 11: Set point visible that is not on the map (should do nothing)
        mapAssembly.setPointRadiusVisible(mapAssembly.getWidth() + 1,
        		mapAssembly.getHeight() + 1, 20);
        mapAssembly.updateVisibilityArray();
        
        actualVision4 = mapAssembly.visibilityArray;
        assertArrayEquals(expectedVision8, actualVision4);
    }

    @AfterClass
    public static void resetMapAssembly() {
        MapAssembly.setBlockSize(3);
    }

}