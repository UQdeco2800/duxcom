package uq.deco2800.duxcom.coop;

import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.Knight;

import static org.junit.Assert.*;

/**
 * Tests the squad class
 *
 * Created by liamdm on 19/10/2016.
 */
public class SquadTest {

    @Test
    public void testSquad(){
        Squad s = new Squad("testowner");

        assertTrue(s.allHeroKill());

        s.addHero("t1", new Knight(0, 1));
        s.addHero("t2", new Knight(0, 2));
        s.addHero("t3", new Knight(0, 3));

        assertEquals("t2", s.nextHero());
        assertEquals("t3", s.nextHero());
        assertNull(s.nextHero());
        assertEquals("t2", s.nextHero());
        assertEquals("t3", s.nextHero());
        assertNull(s.nextHero());

        s.killHero("t2");

        assertEquals("t3", s.nextHero());
        assertNull(s.nextHero());

        assertFalse(s.allHeroKill());

        assertTrue(s.isInSquad("t2"));
        assertTrue(s.isInSquad("t3"));

        s.killHero("t1");
        s.killHero("t3");

        assertTrue(s.allHeroKill());


    }
}