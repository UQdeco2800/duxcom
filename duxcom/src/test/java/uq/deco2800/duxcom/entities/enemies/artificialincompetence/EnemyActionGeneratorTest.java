package uq.deco2800.duxcom.entities.enemies.artificialincompetence;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

import java.util.ArrayList;
import java.util.List;

/**
 * A test to ensure that Enemy Action Generator does what it says it does
 *
 * Created by jay-grant on 23/10/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EnemyActionGeneratorTest {

    private EnemyActionGenerator generator;

    @Mock
    MapAssembly mapMock;

    @Mock
    AbstractEnemy ownerMock;

    @Mock
    AbstractAbility abilityMock;

    @Mock
    EnemyActionManager actionManagerMock;

    @Mock
    AbstractHero heroMock;

    @Mock
    AbstractEnemy enemyMock;

    @Test
    public void emptyAbilitiesTest() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.BALANCED);
        when(ownerMock.getAbilities()).thenReturn(new ArrayList<>(0));
        generator.generateAction(ownerMock);
        assertEquals(generator.numberOfMoves, 10);
        assertTrue(generator.getPrimaryAction() == null);

        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.SUPPORTIVE);
        when(ownerMock.getAbilities()).thenReturn(new ArrayList<>(0));
        generator.generateAction(ownerMock);
        assertEquals(generator.numberOfMoves, 10);
        assertTrue(generator.getPrimaryAction() == null);
    }


    /**
     * Case that Hero is found at 1,1
     */
    @Test
    public void generateActionTestBalancedOne() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.BALANCED);
        when(abilityMock.canUseOnFoe()).thenReturn(true);

        // getSurrounding (true, false, true)
        // range = 2
        // moveRadius = 2
        // so xMin 1 = xMax
        // xMax = 9 = yMax
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);

        // so what point do we want to be the target?
        // target = 1, 1
        when(mapMock.canSelectPoint(1, 1)).thenReturn(true);
        when(mapMock.getMovableEntity(1, 1)).thenReturn(heroMock);
        when(heroMock.getX()).thenReturn(1);
        when(heroMock.getY()).thenReturn(1);
        generator.generateAction(ownerMock);
    }

    /**
     * Case that Hero is found at 7,7
     */
    @Test
    public void generateActionTestBalancedTwo() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.BALANCED);
        when(abilityMock.canUseOnFoe()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(7, 7)).thenReturn(true);
        when(mapMock.getMovableEntity(7, 7)).thenReturn(heroMock);
        when(heroMock.getX()).thenReturn(7);
        when(heroMock.getY()).thenReturn(7);
        generator.generateAction(ownerMock);
    }

    /**
     * Case that Enemy is found at 1,1
     */
    @Test
    public void generateActionTestSupportOne() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.SUPPORTIVE);
        when(abilityMock.canUseOnFoe()).thenReturn(false);
        when(abilityMock.canUseOnFriend()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(1, 1)).thenReturn(true);
        when(mapMock.getMovableEntity(1, 1)).thenReturn(enemyMock);
        when(heroMock.getX()).thenReturn(1);
        when(heroMock.getY()).thenReturn(1);
        generator.generateAction(ownerMock);
    }

    /**
     * Case that Enemy is found at 7,7
     */
    @Test
    public void generateActionTestSupportTwo() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.SUPPORTIVE);
        when(abilityMock.canUseOnFoe()).thenReturn(false);
        when(abilityMock.canUseOnFriend()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(7, 7)).thenReturn(true);
        when(mapMock.getMovableEntity(7, 7)).thenReturn(enemyMock);
        when(heroMock.getX()).thenReturn(7);
        when(heroMock.getY()).thenReturn(7);
        generator.generateAction(ownerMock);
    }

    /**
     * Case that Enemy is found at 1,1
     */
    @Test
    public void generateActionTestEvasiveOne() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.EVASIVE);
        when(abilityMock.canUseOnFoe()).thenReturn(false);
        when(abilityMock.canUseOnFriend()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(1, 1)).thenReturn(true);
        when(mapMock.getMovableEntity(1, 1)).thenReturn(enemyMock);
        when(heroMock.getX()).thenReturn(1);
        when(heroMock.getY()).thenReturn(1);
        generator.generateAction(ownerMock);
    }

    /**
     * Case that Enemy is found at 7,7
     */
    @Test
    public void generateActionTestEvasiveTwo() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.EVASIVE);
        when(abilityMock.canUseOnFoe()).thenReturn(false);
        when(abilityMock.canUseOnFriend()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(7, 7)).thenReturn(true);
        when(mapMock.getMovableEntity(7, 7)).thenReturn(enemyMock);
        when(heroMock.getX()).thenReturn(7);
        when(heroMock.getY()).thenReturn(7);
        generator.generateAction(ownerMock);
    }

    /**
     * Basic defensive call (just calls balanced)
     */
    @Test
    public void getActionTestDefensive() {
        when(ownerMock.getAttitude()).thenReturn(EnemyAttitude.DEFENSIVE);
        when(abilityMock.canUseOnFoe()).thenReturn(true);
        when(ownerMock.getX()).thenReturn(5);
        when(ownerMock.getY()).thenReturn(5);
        when(mapMock.canSelectPoint(7, 7)).thenReturn(true);
        when(mapMock.getMovableEntity(7, 7)).thenReturn(heroMock);
        when(heroMock.getX()).thenReturn(7);
        when(heroMock.getY()).thenReturn(7);
        generator.generateAction(ownerMock);
    }

    @Before
    public void beforeTest() {
        generator = new EnemyActionGenerator(mapMock);
        when(ownerMock.getActionManager()).thenReturn(actionManagerMock);
        when(ownerMock.getMoveRange()).thenReturn(1);
        List<AbstractAbility> abilities = new ArrayList<>(1);
        abilities.add(abilityMock);
        when(abilityMock.getRange()).thenReturn(1);
        when(ownerMock.getAbilities()).thenReturn(abilities);
    }

}
