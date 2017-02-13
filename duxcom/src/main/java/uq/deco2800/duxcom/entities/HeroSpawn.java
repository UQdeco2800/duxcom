package uq.deco2800.duxcom.entities;

/**
 * A real wood stack that is themed based on UQ
 *
 * Created by liamdm on 16/08/2016.
 */
public class HeroSpawn extends Entity {

    private HeroSpawn(int x, int y, int lengthX, int lengthY) {
        super(EntityType.HERO_SPAWN, x, y, lengthX, lengthY);
        setHidden(true);
    }

    public HeroSpawn(int x, int y) {
        super(EntityType.HERO_SPAWN, x, y, 1, 1);
        setHidden(true);
    }

    public String getImageName(){
        return "hero-spawn";
    }

}
