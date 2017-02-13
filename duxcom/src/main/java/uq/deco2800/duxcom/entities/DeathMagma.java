package uq.deco2800.duxcom.entities;


/**
 * Entity used to represent the death of a character
 *
 * @author Alex McLean
 */
public class DeathMagma extends PickableEntities {
    
    /**
     * Creates a tomb stone at a given position
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public DeathMagma(int x, int y) {
        super(EntityType.DEATH_MAGMA, x, y, 1, 1);
    }

    /**
     * Gets the name of the texture to render the entity
     *
     * @return death magma texture name
     */
    public String getImageName(){
        return "rs_magma";
    }
}
