package uq.deco2800.duxcom.entities.heros;

import uq.deco2800.duxcom.abilities.*;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.items.ItemGenerate;
import uq.deco2800.duxcom.passives.AbstractPassive;
import uq.deco2800.duxcom.passives.PoisonBlade;

import java.util.ArrayList;

/**
* Rogue hero class
*
* Created by spress11 on 25-Aug-16.
*/
public class Rogue extends AbstractHero {

    /**
     * Attributes of the Hero 'Rogue'
     */
    private static final HeroDataClass heroDataClass =
           DataRegisterManager.getHeroDataRegister().getData(HeroType.ROGUE);

    /**
     * Constructs an instance of Rogue with the given coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
   public Rogue(int x, int y) {
       super(EntityType.ROGUE, x, y);
       setStats(HeroType.ROGUE, heroDataClass);

       ArrayList<AbstractAbility> heroAbilities = new ArrayList<>();
       ArrayList<AbstractPassive> passives = new ArrayList<>();
       heroAbilities.add(new Slash());
       heroAbilities.add(new Bomb());
       heroAbilities.add(new ItsATrap());
       super.utilityAbility = new Heal();
       passives.add(new PoisonBlade(this));
       super.passives = passives;
       super.abilities = heroAbilities;
       
       /* Add default items */
       this.inventory.equipPrimaryWeapon(ItemGenerate.dagger.BRONZE_DAGGER.generate());
       this.inventory.equipSecondaryWeapon(ItemGenerate.poisonPotion.POISON_BOMB.generate());

       /* Bind Texture */
       this.setProfileImage("profile_rogue");
       this.setName("Rogue");
   }
}