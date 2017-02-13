package uq.deco2800.duxcom.entities;

/**
 * A real wood stack that is themed based on UQ
 *
 * Created by liamdm on 16/08/2016.
 */
public class RealWoodStack extends Entity {

    private RealWoodStack(int x, int y, int lengthX, int lengthY) {
        super(EntityType.REAL_WOOD_STACK, x, y, lengthX, lengthY);
    }

    public RealWoodStack(int x, int y) {
        super(EntityType.REAL_WOOD_STACK, x, y, 1, 1);

    }

    public String getImageName(){
        return "real_wood_stack";
    }

}
