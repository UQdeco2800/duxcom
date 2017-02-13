package uq.deco2800.duxcom.abilities;

/**
 * Used for the mapping of integers to abilities and vice versa
 *
 * Created by liamdm on 24/10/2016.
 */
public class AbilityEnumMapper {
    /**
     * Temp method used until for use in multiplayer related methods that use int
     * instead of SelectedAbility enum.
     */
    public static AbilitySelected intToAbilitySelected(int ability) {
        switch (ability) {
            case 2:
                return AbilitySelected.ABILITY1;
            case 3:
                return AbilitySelected.ABILITY2;
            case 4:
                return AbilitySelected.WEAPON;
            case 5:
                return AbilitySelected.UTILITY;
            default:
                return AbilitySelected.MOVE;
        }
    }

    /**
     * Temp method used until for use in multiplayer related methods that use int
     * instead of SelectedAbility enum.
     */
    public static int abilitySelectedToInt(AbilitySelected ability) {
        switch (ability) {
            case MOVE:
                return 1;
            case ABILITY1:
                return 2;
            case ABILITY2:
                return 3;
            case WEAPON:
                return 4;
            case UTILITY:
                return 5;
            default:
                return 0;
        }
    }
}
