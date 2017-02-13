package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.abilities.InnerStrength;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

import java.util.ArrayList;

/**
 * Created by spress11
 *
 * Priest hero class
 */
public class Priest extends AbstractHero {

    /**
     * Attributes of 'Priest'
     */
    private static final HeroDataClass heroDataClass =
            DataRegisterManager.getHeroDataRegister().getData(HeroType.PRIEST);

    /**
     * Constructs an instance of Priest with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Priest(int x, int y) {
        super(EntityType.PRIEST, x, y);
        setStats(HeroType.PRIEST, heroDataClass);

        ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
        heroAbilities.add(new Heal());
        heroAbilities.add(new InnerStrength());
        super.abilities = heroAbilities;
        super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.poisonPotion.POISON_BOMB.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
    }
}
