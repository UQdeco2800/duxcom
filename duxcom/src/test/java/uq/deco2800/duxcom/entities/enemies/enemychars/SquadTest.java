package uq.deco2800.duxcom.entities.enemies.enemychars;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the SquadGenerator class
 *
 * @author Alex McLean
 */
public class SquadTest {

    private Squad squadTest;

    @Test
    public void testSquads() {
        squadTest = new Squad();

        squadTest.getSquad(0,0,0);
        assertEquals(EnemyGrunt.class, squadTest.squadMembers.get(0).getClass());

        squadTest.getSquad(0,0,1);
        assertEquals(EnemyKnight.class, squadTest.squadMembers.get(1).getClass());

        squadTest.getSquad(0,0,2);
        assertEquals(EnemyDarkMage.class, squadTest.squadMembers.get(2).getClass());

        squadTest.getSquad(0,0,3);
        assertEquals(EnemyArcher.class, squadTest.squadMembers.get(3).getClass());

        squadTest.getSquad(0,0,69);
        assertEquals(EnemySupport.class, squadTest.squadMembers.get(3).getClass());
    }

}