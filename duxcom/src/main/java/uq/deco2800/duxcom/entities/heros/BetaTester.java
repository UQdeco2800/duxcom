package uq.deco2800.duxcom.entities.heros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

/**
 * Allows for beta testing given infinite movement ability
 *
 * Created by liamdm on 03-Aug-16.
 */
public class BetaTester extends AbstractHero {

    private static Logger logger = LoggerFactory.getLogger(BetaTester.class);

    /**
     * Attributes of 'Beta Tester'
     */
    private static final HeroDataClass heroDataClass =
            DataRegisterManager.getHeroDataRegister().getData(HeroType.BETA_IBIS);

    /**
     * Constructs an instance of BetaTester with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public BetaTester(int x, int y) {
        super(EntityType.BETA_IBIS, x, y);
        setStats(HeroType.BETA_IBIS, heroDataClass);

        super.abilities.add(new Projectile());
        super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.sword.VALYRIAN_STEEL_SWORD.generate());
        this.inventory.addItem(ItemGenerate.potion.AP_POTION.generate());
    }

    /**
     * called when an ability is used on this hero
     * @return true iff targetable
     */
    @Override
    public boolean target(AbstractAbility ability) {
        logger.error("Beta tester is impervious to abilities!");
        return false;
    }
}
