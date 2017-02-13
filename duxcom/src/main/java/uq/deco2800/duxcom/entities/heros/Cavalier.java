package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Charge;
import uq.deco2800.duxcom.abilities.GroundhogDay;
import uq.deco2800.duxcom.abilities.Heal;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

import java.util.ArrayList;

/**
 * Created by spress11
 * Created by lars06
 *
 * Class for holding attribute values for Enemy Archer
 */
public class Cavalier extends AbstractHero {

    /**
     * Attributes of 'Cavalier'
     */
    private static final HeroDataClass heroDataClass =
            DataRegisterManager.getHeroDataRegister().getData(HeroType.CAVALIER);

    /**
     * Constructs an instance of Cavalier with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Cavalier(int x, int y) {
        super(EntityType.CAVALIER, x, y);
        setStats(HeroType.CAVALIER, heroDataClass);

        ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
        heroAbilities.add(new Charge());
        heroAbilities.add(new GroundhogDay());
        super.abilities = heroAbilities;
        super.utilityAbility = new Heal();
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.lance.BRONZE_LANCE.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
        
        /* Bind Texture */
        this.setProfileImage("profile_cavalier");
        this.setName("Cavalier");
    }
}
