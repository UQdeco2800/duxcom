package uq.deco2800.duxcom.interfaces.gameinterface.glossary;

/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An enumeration that holds all of the sections in the glossary
 *
 * @author rhysmckenzie
 */
public enum GlossarySections {
    WORLD("World", "Pick a topic that you would like to read about",
            "worldSection", "/ui/duxcom-icon.png"),
    HEROES("Heroes", "Pick a Hero that you would" +
            " like to know more about","heroesSection", "/heroes/knight/knight.png"),
    ENEMIES("Enemies", "Pick an Enemy that you " +
            "would like to know more about", "enemiesSection",
            "/enemies/dark_knight.png");

    GlossarySection glossarySection;

    /**
     * Constructor to retrieve inputs, stores a glossarySection
     *
     * @param name Name given to the section
     * @param text The Text of the section
     * @param id ID given to the section (Buttons will have same ID)
     * @param image The image that will be displayed for this section
     */
    GlossarySections(String name, String text, String id, String image) {
        this.glossarySection = new GlossarySection(name, text, id, image);
    }
}
