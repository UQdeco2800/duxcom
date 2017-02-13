package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.abilities.AbilityName;
import uq.deco2800.duxcom.abilities.AbilityType;
import uq.deco2800.duxcom.common.DamageType;

/**
 * Links each ability from the AbilityName enum with a set of data from AbilityDataClass.
 *
 * @author spress11
 */
public class AbilityDataRegister extends AbstractDataRegister<AbilityName, AbilityDataClass> {

	/**
	 * Package private constructor
	 *
	 * Should only be called by DataRegisterManager
	 */
	AbilityDataRegister() {
        super();
        
        linkDataToType(AbilityName.BOMB,
                new AbilityDataClass(AbilityType.WEAPON, "Bomb", "Blow up an area with an explosive bomb", 
                		4, 1, 6, 3, -40, DamageType.EXPLOSIVE, false, false, true));

        linkDataToType(AbilityName.CHARGE,
                new AbilityDataClass(AbilityType.CAVALIER, "Charge", 
                		"Charge forward in a straight line, causing damage to enemies", 
                		10, 1, 10, 0, -40, DamageType.CRUSHING, false, false, true));
        
        linkDataToType(AbilityName.CRIPPLINGBLOW,
                new AbilityDataClass(AbilityType.CAVALIER, "Crippling Blow", 
                		"Strike an nearby enemy dealing physical damage", 
                		3, 1, 1, 0, -12, DamageType.CRUSHING, false, true, false));
        
        linkDataToType(AbilityName.FIREBALL,
                new AbilityDataClass(AbilityType.WARLOCK, "Fireball", 
                		"Shoot a fireball dealing area of effect damage", 5, 1, 4, 2, -20, DamageType.FIRE, false, false, true));
        
        linkDataToType(AbilityName.HEAL,
                new AbilityDataClass(AbilityType.UTILITY, "Heal", "Heal a nearby hero", 3, 1, 2, 0, 12, DamageType.HEAL, true, false, false));
        
        linkDataToType(AbilityName.INNERSTRENGTH,
                new AbilityDataClass(AbilityType.PRIEST, "Inner Strength", "Give a hero a buff to armour and damage",
                		5, 3, 3, 0, 0, DamageType.HEAL, false, true, false));
        
        linkDataToType(AbilityName.LIGHTNINGSTRIKE,
                new AbilityDataClass(AbilityType.WARLOCK, "Lightning Strike", "Strike an enemy with a bolt of lightning", 
                		6, 1, 4, 0, -30, DamageType.ELECTRIC, false, true, false));
        
        linkDataToType(AbilityName.LONGRANGETESTABILITY,
                new AbilityDataClass(AbilityType.WEAPON, "LongRangeTestAbility", "Placeholder Ability for Enemy Action testing",
                		0, 0, 8, 0, -1, DamageType.PIERCING, false, true, false));
        
        linkDataToType(AbilityName.PROJECTILE,
                new AbilityDataClass(AbilityType.ARCHER, "Projectile", "Shoot an arrow at the target", 
                		3, 1, 5, 0, -12, DamageType.PIERCING, false, true, false));
        
        linkDataToType(AbilityName.SHORTRANGETESTABILITY,
                new AbilityDataClass(AbilityType.WEAPON, "ShortRangeTestAbility", "Placeholder Ability for Enemy Action testing",
                		0, 0, 2, 0, -1, DamageType.NORMAL, false, true, true));
        
        linkDataToType(AbilityName.SLASH,
                new AbilityDataClass(AbilityType.WEAPON, "Slash", "Slash a nearby enemy with a sword", 
                		2, 1, 1, 0, -15, DamageType.SLASHING, false, true, false));
        
        linkDataToType(AbilityName.STAB,
                new AbilityDataClass(AbilityType.WEAPON, "Stab", "Stab a nearby enemy with a sword/knife", 
                		4, 1, 1, 0, -12, DamageType.THRUSTING, false, true, false));
		linkDataToType(AbilityName.INSIGHT,
				new AbilityDataClass(AbilityType.PRIEST, "Insight", "Increase vision range on a hero",
						5, 3, 5, 0, 0, DamageType.HEAL, false, true, false));
		linkDataToType(AbilityName.FLARE,
				new AbilityDataClass(AbilityType.ARCHER, "Flare", "Shoot a flare to give vision in a 3 tile radius",
						5, 3, Integer.MAX_VALUE, 5, 0, DamageType.NORMAL, false, true, false));
		linkDataToType(AbilityName.TELEPORT,
                new AbilityDataClass(AbilityType.UTILITY, "Teleport", "Teleport to given position", 
                		1, 3, 10, 0, 0, DamageType.HEAL, false, true, false));
		linkDataToType(AbilityName.CALLTOARMS,
				new AbilityDataClass(AbilityType.KNIGHT, "Call To Arms", "All heroes in 3 tiles gain increased damage for 2 turns",
						4, 4, 0, 3, 0, DamageType.HEAL, true, false, false));
		linkDataToType(AbilityName.SCATTERSHOT,
				new AbilityDataClass(AbilityType.ARCHER, "Scatter Shot", "Shoot several arrows at the same time to damage enemies in an area",
						4, 4, 6, 2, -10, DamageType.PIERCING, false, false, true));
		linkDataToType(AbilityName.TRAP,
				new AbilityDataClass(AbilityType.ROGUE, "It's A Trap!", "Place down a trap which when stepped on, deals damage and reduces AP",
						3, 4, 2, 1, -20, DamageType.NORMAL, false, false, true));
		linkDataToType(AbilityName.GROUNDHOGDAY,
				new AbilityDataClass(AbilityType.CAVALIER, "Groundhog Day", "Return to the hero's current location at the end of the turn",
						3, 3, 0, 0, 0, DamageType.NORMAL, true, false, false));
	}
}
