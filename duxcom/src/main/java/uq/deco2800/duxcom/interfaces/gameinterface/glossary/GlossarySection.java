package uq.deco2800.duxcom.interfaces.gameinterface.glossary;

/**
 * Used in glossary enums
 */
public class GlossarySection {

    // The string to be displayed in each section
    private String text;

    //The location of the image to be displayed
    private String image;

    // Name given to the section
    private String name;

    // ID given to the section (Buttons will have same ID)
    private String id;

    /**
     * Constructor to retrieve inputs
     *
     * @param name Name given to the section
     * @param text The Text of the section
     * @param id ID given to the section (Buttons will have same ID)
     * @param image The image that will be displayed for this section
     */
    GlossarySection(String name, String text, String id, String image) {
        this.name = name;
        this.id = id;
        this.text = text;
        this.image = image;
    }

    /**
     * Retrieves sections text
     *
     * @return text of the section
     */

    public String getText() {return this.text;}

    /**
     * Retrieves name given to the section
     *
     * @return name given to the section
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves ID given to the section (Buttons will have same ID)
     *
     * @return ID given to the section (Buttons will have same ID)
     */
    public String getId() {
        return this.id;
    }

    /**
     * Retrieves image of the section
     *
     * @return Image for the section
     */
    public String getImage() {
        return this.image;
    }
}
