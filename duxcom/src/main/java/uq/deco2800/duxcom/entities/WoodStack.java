package uq.deco2800.duxcom.entities;

/**
 * Created by wondertroy on 31/07/2016.
 */
public class WoodStack extends Entity {



	private WoodStack(int x, int y, int lengthX, int lengthY) {
		super(EntityType.WOOD_STACK, x, y, lengthX, lengthY);
	}

	/**
	 * Creates a wood stack at a given position
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public WoodStack(int x, int y) {
		super(EntityType.WOOD_STACK, x, y, 1, 1);

	}

	public String getImageName(){
		return "tall";
	}

}
