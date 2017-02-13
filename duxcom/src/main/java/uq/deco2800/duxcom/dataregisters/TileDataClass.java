package uq.deco2800.duxcom.dataregisters;

/**
 * Stores the data that all instances of a type of tile must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class TileDataClass implements AbstractDataClass {

    // Declares the default movement modifier
    public static final int DEFAULT_MOVEMENT_MODIFIER = 1;

    private String tileTypeName;
    private String tileTextureName;
    private String minimapColorString;
    private int movementModifier;

    /**
     * Constructor for a TileDataClass with default movementAPModifier;
     *
     * @param tileTypeName       The name of the tile type
     * @param tileTextureName    The name of the tile type texture
     * @param minimapColorString The minimap color of the tile type as a String
     */
    public TileDataClass(String tileTypeName, String tileTextureName, String minimapColorString) {
        this.tileTypeName = tileTypeName;
        this.tileTextureName = tileTextureName;
        this.minimapColorString = minimapColorString;
        this.movementModifier = DEFAULT_MOVEMENT_MODIFIER;
    }

    /**
     * Constructor for a TileDataClass with specified movementAPModifier;
     *
     * @param tileTypeName       The name of the tile type
     * @param tileTextureName    The name of the tile type texture
     * @param minimapColorString The minimap color of the tile type as a String
     * @param movementModifier An integer that multiplies the cost of movement
     */
    public TileDataClass(String tileTypeName, String tileTextureName, String minimapColorString, int movementModifier) {
        this.tileTypeName = tileTypeName;
        this.tileTextureName = tileTextureName;
        this.minimapColorString = minimapColorString;
        this.movementModifier = movementModifier;
    }

    /**
     * Gets the name of the tile type
     *
     * @return String of the tile type name
     */
    public String getTileTypeName() {
        return tileTypeName;
    }

    /**
     * Sets the name of an tile type
     *
     * @param tileTypeName String of the new tile type name
     */
    public void setTileTypeName(String tileTypeName) {
        this.tileTypeName = tileTypeName;
    }

    /**
     * Gets the texture of the tile type
     *
     * @return String of the texture name
     */
    public String getTileTextureName() {
        return tileTextureName;
    }

    /**
     * Sets the texture of the tile type
     *
     * @param tileTextureName String of the new texture name
     */
    public void setTileTextureName(String tileTextureName) {
        this.tileTextureName = tileTextureName;
    }

    /**
     * Gets the minimap color of the tile type
     *
     * @return String of the minimap color
     */
    public String getMinimapColorString() {
        return minimapColorString;
    }

    /**
     * Sets the minimap color of the tile type
     *
     * @param minimapColorString String of the new minimap color
     */
    public void setMinimapColorString(String minimapColorString) {
        this.minimapColorString = minimapColorString;
    }

    /**
     * Gets the movement modifier of the tile type
     *
     * @return the tile type movement modifier
     */
    public int getMovementModifier() {
        return movementModifier;
    }

    /**
     * Sets the movement modifier of the tile type
     *
     * @param movementModifier The new movement modifier of the tile type
     */
    public void setMovementModifier(int movementModifier) {
        this.movementModifier = movementModifier;
    }
}
