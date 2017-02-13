package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.*;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.passives.Defender;
import uq.deco2800.duxcom.passives.HealthRegen;

import java.util.ArrayList;

/**
* Knight hero class
*
* Created by lars06 on 24-Aug-16.
*/
public class Knight extends AbstractHero{

    /**
     * Attributes of the Hero 'Knight'
     */
    private static final HeroDataClass heroDataClass =
           DataRegisterManager.getHeroDataRegister().getData(HeroType.KNIGHT);

    /**
     * Constructs an instance of Knight with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
   public Knight(int x, int y) {
       super(EntityType.KNIGHT, x, y);
       setStats(HeroType.KNIGHT, heroDataClass);

       ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
       heroAbilities.add(new Slash());
       heroAbilities.add(new CallToArms());
       super.utilityAbility = new Heal();
       super.abilities = heroAbilities;
       super.passives.add(new HealthRegen(this));
       super.passives.add(new Defender(this));
       
       /* Add default items */
       this.inventory.equipPrimaryWeapon(ItemGenerate.sword.BRONZE_SWORD.generate());
       this.inventory.addItem(ItemGenerate.potion.AP_POTION.generate());
       
       /* Bind Texture */
       this.setProfileImage("profile_knight");
       this.setName("Knight");
   }
}