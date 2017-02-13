package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the EnemyActionManager class
 *
 * Created by jay-grant on 23/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnemyActionManagerTest {

    private EnemyActionManager manager;

    @Mock
    AbstractEnemy enemyMock;

    @Mock
    EnemyAction actionMock;

    @Mock
    EnemyAction actionMockTwo;

    @Mock
    MapAssembly mapMock;

    @Mock
    EnemyActionGenerator generatorMock;

    @Mock
    AbstractCharacter targetMock;

    @Test
    public void getActionTest() {
        when(actionMock.getNumberMoves()).thenReturn(0);
        when(actionMockTwo.getNumberMoves()).thenReturn(1);
        manager.getAction(0, 0);
        manager.getAction(0, 1);
    }

    @Test
    public void updateActionsTest() {
        manager.updateActions();
    }

    @Test
    public void targetMovedTest() {
        when(actionMock.getTargetPointX()).thenReturn(0);
        when(actionMock.getTargetPointY()).thenReturn(0);
        assertFalse(manager.targetMoved());

        when(actionMock.getTargetPointX()).thenReturn(1);
        when(actionMock.getTargetPointY()).thenReturn(3);
        assertTrue(manager.targetMoved());
    }

    @Before
    public void beforeTests() {
        manager = new EnemyActionManager(enemyMock);
        manager.addAction(actionMock);
        when(actionMock.getTarget()).thenReturn(targetMock);
        when(targetMock.getX()).thenReturn(0);
        when(targetMock.getY()).thenReturn(0);
        manager.addAction(actionMockTwo);
        manager.setMap(mapMock);
        when(mapMock.getEnemyActionGenerator()).thenReturn(generatorMock);
    }

}
