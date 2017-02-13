package uq.deco2800.duxcom.entities.enemies;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAction;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyActionManager;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.maps.mapgen.bounds.Coordinate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by jay-grant on 21/09/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnemyManagerTest {

    private EnemyManager enemyManager;

    @Mock
    private AbstractEnemy mockEnemy;
    @Mock
    private AbstractHero mockHero;
    @Mock
    private MapAssembly mockMap;
    @Mock
    private GameManager mockGameManager;
    @Mock
    private EnemyActionManager mockActionManager;
    @Mock
    private Coordinate mockCoordinate;
    @Mock
    private AbstractAbility mockAbility;
    @Mock
    private EnemyAction mockAction;
    @Mock
    private AbstractCharacter mockTarget;
    @Mock
    private Tile mockTile;

    @Test
    public void testSimpleMethods() {
        enemyManager = new EnemyManager();

        assertTrue(enemyManager.isEmpty());
        assertFalse(enemyManager.isPerformingTurn());

        enemyManager.addEnemy(mockEnemy);
        assertFalse(enemyManager.isEmpty());

        enemyManager.addEnemy(mockEnemy);
        enemyManager.addEnemy(mockEnemy);
        assertTrue(enemyManager.getEnemyList().size() == 3);

        enemyManager.nextEnemy();
        assertTrue(enemyManager.enemyIndex == 0);

        enemyManager.nextEnemy();
        enemyManager.nextEnemy();
        assertTrue(enemyManager.enemyIndex == 2);
    }
    
    @Test
    public void testEnemyGet() {
        enemyManager = new EnemyManager();

        assertTrue(enemyManager.isEmpty());
        assertFalse(enemyManager.isPerformingTurn());

        enemyManager.addEnemy(mockEnemy);
        assertEquals(enemyManager.get(0), mockEnemy);
    }
    
    @Test
    public void mockMapTests() {
        when(mockEnemy.getActionManager()).thenReturn(mockActionManager);
        enemyManager = new EnemyManager();
        enemyManager.setMap(mockMap);
        verify(mockEnemy, never()).getActionManager();
        verify(mockActionManager, never()).setMap(mockMap);
        assertTrue(enemyManager.map.equals(mockMap));

        when(mockEnemy.getActionManager()).thenReturn(mockActionManager);
        enemyManager = new EnemyManager();
        enemyManager.addEnemy(mockEnemy);
        enemyManager.addEnemy(mockEnemy);
        enemyManager.setMap(mockMap);
        verify(mockEnemy, times(2)).getActionManager();
        verify(mockActionManager, times(2)).setMap(mockMap);
    }

    @Ignore
    @Test
    public void singleTakeTurnTests() {
        /** Test 1 */
        enemyManager = new EnemyManager();
        buildConstants();
        enemyManager.performingTurn = true;

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, never()).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockEnemy, never()).getAction();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        verify(mockEnemy, never()).setCommit(false);
        verify(mockEnemy, never()).getActionManager();
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockEnemy, never()).getX();
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 2 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
//        when(mockEnemy.isCommitted()).thenReturn(false);
//
//        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(false);
//
//        when(mockActionManager.isEmpty()).thenReturn(true);
//        when(mockActionManager.isEmpty()).thenReturn(false);
//
//        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(false);
//
//        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockGameManager, times(1)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(1)).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 3 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); //Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(2)).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 4 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(1);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction); // Changed

        /** Test 5 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(3)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 6 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, never()).updateActions();
        verify(mockActionManager, never()).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 7 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(1)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(1)).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 8 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(1);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(3)).getX();
        verify(mockTarget, times(4)).getX(); // Three times when working // Changed
        verify(mockActionManager, times(1)).dropAction(mockAction); // Changed

        /** Test 9 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(1);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(3)).getX();
        verify(mockTarget, times(4)).getX(); // Three times when working // Changed
        verify(mockActionManager, times(1)).dropAction(mockAction); // Changed

        /** Test 10 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(1)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(1)).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 11 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(3)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 12 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy); // TWICE WHEN WORKING
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(3)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 13 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(2)).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 14 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(1);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX();
        verify(mockTarget, times(4)).getX(); // Three times when working // Changed
        verify(mockActionManager, times(1)).dropAction(mockAction); // Changed

        /** Test 15 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(1);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX();
        verify(mockTarget, times(4)).getX(); // Three times when working // Changed
        verify(mockActionManager, times(1)).dropAction(mockAction); // Changed

        /** Test 16 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(2)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, times(2)).getX();
        verify(mockTarget, never()).getX(); // Three times when working
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 17 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 18 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;
        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(false);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, times(3)).moveViewTo(anyInt(), anyInt());
        verify(mockAction, times(1)).hasMoves();
        verify(mockEnemy, times(1)).setCommit(false);
        // performMove calls
        verify(mockEnemy, times(1)).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, times(2)).setEnemyMoveIntent(anyInt(), anyInt()); // Changed
        verify(mockTile, times(1)).addEntity(mockEnemy);
        verify(mockTile, times(1)).removeMovableEntity();
        verify(mockEnemy, times(1)).move(anyInt(), anyInt());
        verify(mockAction, times(1)).dropMove();
        verify(mockEnemy, times(1)).setCommit(true);
        // performAbility calls
        verify(mockAction, times(1)).getAbility();
        verify(mockAction, times(1)).getTarget();
        verify(mockAbility, times(1)).getRange();
        verify(mockEnemy, times(4)).getX(); // Potentially 3 times
        verify(mockTarget, times(4)).getX(); // 3 times if entered
        verify(mockActionManager, times(1)).dropAction(mockAction);

        /** Test 19 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 20 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 21 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 22 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;
        when(mockEnemy.isCommitted()).thenReturn(false);
        when(mockActionManager.targetMoved()).thenReturn(false);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, never()).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 23 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 24 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(false);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 25 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;

        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(null);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);

        /** Test 26 */
        enemyManager = new EnemyManager();
        buildConstants();

        enemyManager.performingTurn = false;
        when(mockEnemy.isCommitted()).thenReturn(true);
        when(mockActionManager.targetMoved()).thenReturn(true);
        when(mockActionManager.isEmpty()).thenReturn(true);
        when(mockAction.hasMoves()).thenReturn(true);
        when(mockAction.getAbility()).thenReturn(mockAbility);
        when(mockAbility.getRange()).thenReturn(5);

        enemyManager.takeTurn(mockGameManager);

        // moveHandler calls
        verify(mockEnemy, times(1)).isCommitted();
        verify(mockActionManager, times(1)).targetMoved();
        verify(mockActionManager, times(1)).updateActions();
        verify(mockActionManager, times(1)).isEmpty();
        verify(mockGameManager, never()).moveViewTo(anyInt(), anyInt());
        verify(mockAction, never()).hasMoves();
        verify(mockEnemy, never()).setCommit(false);
        // performMove calls
        verify(mockEnemy, never()).getValidCoordinate(mockMap, mockCoordinate);
        verify(mockGameManager, never()).setEnemyMoveIntent(anyInt(), anyInt());
        verify(mockTile, never()).addEntity(mockEnemy);
        verify(mockTile, never()).removeMovableEntity();
        verify(mockEnemy, never()).move(anyInt(), anyInt());
        verify(mockAction, never()).dropMove();
        verify(mockEnemy, never()).setCommit(true);
        // performAbility calls
        verify(mockAction, never()).getAbility();
        verify(mockAction, never()).getTarget();
        verify(mockAbility, never()).getRange();
        verify(mockEnemy, never()).getX(); // Potentially 3 times
        verify(mockTarget, never()).getX(); // 3 times if entered
        verify(mockActionManager, never()).dropAction(mockAction);
    }

    @Test
    public void multiTakeTurnTests() {
        //TODO
        // God save the queen
    }

    private void buildConstants() {
        reset(mockEnemy);
        reset(mockAction);
        reset(mockActionManager);
        reset(mockAbility);
        reset(mockMap);
        reset(mockGameManager);
        reset(mockHero);
        reset(mockTarget);
        reset(mockTile);
        enemyManager.currentEnemy = mockEnemy;
        enemyManager.map = mockMap;
        when(mockEnemy.getAction()).thenReturn(mockAction);
        when(mockEnemy.getActionManager()).thenReturn(mockActionManager);
        when(mockEnemy.getValidCoordinate(mockMap, mockCoordinate)).thenReturn(mockCoordinate);
        when(mockEnemy.getX()).thenReturn(0);
        when(mockEnemy.getY()).thenReturn(0);
        when(mockAction.getMove(0)).thenReturn(mockCoordinate);
        when(mockAction.getAbility()).thenReturn(mockAbility); // can change to null
        when(mockAction.getTarget()).thenReturn(mockTarget);
        when(mockMap.getTile(0, 0)).thenReturn(mockTile);
    }

}
