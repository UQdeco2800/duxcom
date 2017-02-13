package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.entities.heros.HeroType;

/**
 * Links each enemy type from the EnemyType enum with a set of data from EnemyDataClass.
 *
 * @author lars06
 */
public class HeroDataRegister extends AbstractDataRegister<HeroType, HeroDataClass> {

    /**
     * Package private constructor
     *
     * Should only be called by DataRegisterManager
     */
    HeroDataRegister() {
        super();

        linkDataToType(HeroType.KNIGHT, new HeroDataClass("Knight", "knight",
                40, 7, 1, 5, 15, 8));

        linkDataToType(HeroType.ARCHER, new HeroDataClass("Archer", "archer",
        		40, 5, 1.2, 12, 15, 10));

        linkDataToType(HeroType.CAVALIER, new HeroDataClass("Cavalier", "cavalier",
                40, 5, 1.8, 5, 15, 12));

        linkDataToType(HeroType.PRIEST, new HeroDataClass("Priest", "priest",
                40, 2, 1, 5, 15, 8));

        linkDataToType(HeroType.ROGUE, new HeroDataClass("Rogue", "rogue",
                40, 4, 1.2, 5, 15, 8));

        linkDataToType(HeroType.WARLOCK, new HeroDataClass("Warlock", "warlock",
                40, 2, 1, 5, 15, 8));

        linkDataToType(HeroType.DUCK, new HeroDataClass("Duck", "duck",
                40, 1, 1, 1, 7, 2));

        linkDataToType(HeroType.BETA_IBIS, new HeroDataClass("Beta Tester", "beta_ibis",
                40, 1, 2, 7, Integer.MAX_VALUE, Integer.MAX_VALUE));

        linkDataToType(HeroType.IBIS, new HeroDataClass("Ibis", "real_ibis",
                40, 10, 50, 5, 50, 50));
    }

}
