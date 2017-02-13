package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Flare;
import uq.deco2800.duxcom.abilities.Projectile;
import uq.deco2800.duxcom.abilities.ScatterShot;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;

import java.util.ArrayList;

/**
 * Archer hero class
 *
 * Created by lars06 on 24-Aug-16.
 */
public class Archer extends AbstractHero {

	/**
	 * Attributes of the Hero 'Archer'
	 */
	private static final HeroDataClass heroDataClass =
            DataRegisterManager.getHeroDataRegister().getData(HeroType.ARCHER);

    /**
     * Constructs an instance of Archer with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Archer(int x, int y) {
        super(EntityType.ARCHER, x, y);
        setStats(HeroType.ARCHER, heroDataClass);

        ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
        heroAbilities.add(new Projectile());
        heroAbilities.add(new ScatterShot());
        super.utilityAbility = new Flare();
        super.abilities = heroAbilities;
        
        /* Add default items */
        this.inventory.equipPrimaryWeapon(ItemGenerate.bow.WOODEN_BOW.generate());
//        this.inventory.equipSecondaryWeapon(ItemGenerate.axe.BRONZE_AXE.generate());
        this.inventory.addItem(ItemGenerate.potion.HEALTH_POTION.generate());
        
        /* Bind Texture */
        this.setProfileImage("profile_archer");
        this.setName("Archer");
    }
}
