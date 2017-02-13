package uq.deco2800.duxcom.passives;

import org.junit.Test;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.entities.heros.Archer;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by tiget on 10/20/16.
 */
public class PoisonBladeTest {

    @Test
    public void nameTest() {
        AbstractHero hero = new Archer(1, 2);
        PoisonBlade poisonBlade = new PoisonBlade(hero);

        assertTrue("Poison Blade".equals(poisonBlade.getName()));
    }

    @Test
    public void activationTimerTest() {
        AbstractHero hero = new Archer(1, 2);
        PoisonBlade poisonBlade = new PoisonBlade(hero);

        assertTrue(poisonBlade.getActivationTimer() == 1);
    }

    @Test
    public void descriptionTest() {
        AbstractHero hero = new Archer(1, 2);
        PoisonBlade poisonBlade = new PoisonBlade(hero);

        assertTrue("Poison the enemy when you attack".equals(poisonBlade.getDescription()));
    }

    @Test
    public void activateOnAbilityUseTest() {
        AbstractHero hero = new Archer(1, 2);
        AbstractHero target = new Archer(1, 2);
        AbstractAbility ability = new Projectile();
        PoisonBlade poisonBlade = new PoisonBlade(hero);

        poisonBlade.activateOnAbilityUse(target, ability);
        List<AbstractBuff> buffs = target.getHeroBuffs();

        assertTrue(buffs.size() == 1);
        assertTrue("Poisoned".equals(buffs.get(0).getName()));
    }
}
