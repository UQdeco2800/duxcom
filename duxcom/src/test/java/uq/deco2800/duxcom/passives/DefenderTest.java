package uq.deco2800.duxcom.passives;

import org.junit.Test;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;

import static org.junit.Assert.assertTrue;

/**
 * Created by tiget on 10/20/16.
 */
public class DefenderTest {

    @Test
    public void nameTest() {
        AbstractHero hero = new Archer(1, 2);
        Defender defender = new Defender(hero);

        assertTrue("Defender".equals(defender.getName()));
    }

    @Test
    public void activationTimerTest() {
        AbstractHero hero = new Archer(1, 2);
        Defender defender = new Defender(hero);

        assertTrue(defender.getActivationTimer() == 1);
    }

    @Test
    public void descriptionTest() {
        AbstractHero hero = new Archer(1, 2);
        Defender defender = new Defender(hero);

        assertTrue("Give armour to nearby heroes".equals(defender.getDescription()));
    }
}
