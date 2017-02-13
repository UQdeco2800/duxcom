package uq.deco2800.duxcom.entities;

import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;

/**
 * A real wood stack that is themed based on UQ
 *
 * Created by liamdm on 16/08/2016.
 */
public class Trap extends Entity {

    private Trap(int x, int y, int lengthX, int lengthY) {
        super(EntityType.TRAP, x, y, lengthX, lengthY);
    }

    public Trap(int x, int y) {
        super(EntityType.HERO_SPAWN, x, y, 1, 1);
    }

    public String getImageName(){
        return "trap";
    }

    public boolean trigger(AbstractEnemy enemy) {
        return true;
    }

}
