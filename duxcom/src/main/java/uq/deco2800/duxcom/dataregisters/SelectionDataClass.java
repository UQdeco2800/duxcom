package uq.deco2800.duxcom.dataregisters;

/**
 * Stores the data that all instances of a type of selection marker must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class SelectionDataClass implements AbstractDataClass {
    private String selectionTypeName;
    private String selectionTextureName;

    /**
     * Constructor for a SelectionDataClass
     *
     * @param selectionTypeName    The name of the selection marker
     * @param selectionTextureName The name of the selection marker texture
     */
    public SelectionDataClass(String selectionTypeName, String selectionTextureName) {
        this.selectionTextureName = selectionTextureName;
        this.selectionTypeName = selectionTypeName;
    }

    /**
     * Gets the name of the selection marker
     *
     * @return String of the selection marker name
     */
    public String getSelectionTypeName() {
        return selectionTypeName;
    }

    /**
     * Sets the name of a selection marker
     *
     * @param selectionTypeName String of the new selection marker name
     */
    public void setSelectionTypeName(String selectionTypeName) {
        this.selectionTypeName = selectionTypeName;
    }

    /**
     * Gets the texture of the selection marker
     *
     * @return String of the selection marker name
     */
    public String getSelectionTextureName() {
        return selectionTextureName;
    }

    /**
     * Sets the texture of the selection marker
     *
     * @param selectionTextureName String of the new selection marker name
     */
    public void setSelectionTextureName(String selectionTextureName) {
        this.selectionTextureName = selectionTextureName;
    }
}
