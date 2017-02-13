package uq.deco2800.duxcom.dataregisters;

/**
 * Stores the data that all instances of a type of entity must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class EntityDataClass implements AbstractDataClass {
    private String entityTypeName;
    private String entityTextureName;
    private String minimapColorString;

    /**
     * Constructor for an EntityDataClass
     *
     * @param entityTypeName     The name of the entity
     * @param entityTextureName  The name of the entity texture
     * @param minimapColorString The minimap color of the entity as a String
     */
    public EntityDataClass(String entityTypeName, String entityTextureName, String minimapColorString) {
        this.entityTypeName = entityTypeName;
        this.entityTextureName = entityTextureName;
        this.minimapColorString = minimapColorString;
    }

    /**
     * Gets the name of the entity
     *
     * @return String of the entity name
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * Sets the name of an entity
     *
     * @param entityTypeName String of the new entity name
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    /**
     * Gets the texture of the entity
     *
     * @return String of the texture name
     */
    public String getEntityTextureName() {
        return entityTextureName;
    }

    /**
     * Sets the texture of the entity
     *
     * @param entityTextureName String of the new texture name
     */
    public void setEntityTextureName(String entityTextureName) {
        this.entityTextureName = entityTextureName;
    }

    /**
     * Gets the minimap color of the entity
     *
     * @return String of the minimap color
     */
    public String getMinimapColorString() {
        return minimapColorString;
    }

    /**
     * Sets the minimap color of the entity
     *
     * @param minimapColorString String of the new minimap color
     */
    public void setMinimapColorString(String minimapColorString) {
        this.minimapColorString = minimapColorString;
    }
}
