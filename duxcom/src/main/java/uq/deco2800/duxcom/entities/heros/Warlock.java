package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Fireball;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.LightningStrike;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

import java.util.ArrayList;

/**
 * Created by spress11
 *
 * Warlock Hero Class
 */
public class Warlock extends AbstractHero {

    /**
     * Attributes of 'Warlock'
     */
    private static final HeroDataClass heroDataClass =
            DataRegisterManager.getHeroDataRegister().getData(HeroType.WARLOCK);

    /**
     * Constructs an instance of Warlock with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Warlock(int x, int y) {
        super(EntityType.WARLOCK, x, y);
        setStats(HeroType.WARLOCK, heroDataClass);

        ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
        heroAbilities.add(new LightningStrike());
        heroAbilities.add(new Fireball());
        super.abilities = heroAbilities;
        super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.staff.APPRENTICE_STAFF.generate());
        this.inventory.equipSecondaryWeapon(ItemGenerate.poisonPotion.POISON_BOMB.generate());
    }
}
