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
 * Created by josh on 10/20/16.
 */
public class CripplingBladeTest {

    @Test
    public void nameTest() {
        AbstractHero hero = new Archer(1, 2);
        CripplingBlade cripplingBlade = new CripplingBlade(hero);

        assertTrue("Crippling Blade".equals(cripplingBlade.getName()));
    }

    @Test
    public void activationTimerTest() {
        AbstractHero hero = new Archer(1, 2);
        CripplingBlade cripplingBlade = new CripplingBlade(hero);

        assertTrue(cripplingBlade.getActivationTimer() == 1);
    }

    @Test
    public void descriptionTest() {
        AbstractHero hero = new Archer(1, 2);
        CripplingBlade cripplingBlade = new CripplingBlade(hero);

        assertTrue("Slow the enemy when you attack".equals(cripplingBlade.getDescription()));
    }

    @Test
    public void activateOnAbilityUseTest() {
        AbstractHero hero = new Archer(1, 2);
        AbstractHero target = new Archer(1, 2);
        AbstractAbility ability = new Projectile();
        CripplingBlade cripplingBlade = new CripplingBlade(hero);

        cripplingBlade.activateOnAbilityUse(target, ability);
        List<AbstractBuff> buffs = target.getHeroBuffs();

        assertTrue(buffs.size() == 1);
        assertTrue("Slow".equals(buffs.get(0).getName()));
    }
}
