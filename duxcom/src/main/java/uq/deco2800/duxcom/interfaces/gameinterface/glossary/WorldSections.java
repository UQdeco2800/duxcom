package uq.deco2800.duxcom.interfaces.gameinterface.glossary;

/**
 * Ticket #61 - Story/Lore and In Game glossary
 *
 * An enumeration that holds all of the sections in the glossary
 *
 * @author rhysmckenzie
 */
public enum WorldSections {
    HISTORY("History", "Long ago in a far away land " +
            "known simply as The Pond, there was peace. Long lasting, " +
            "satisfying peace. This was until the International Bureau of " +
            "Inciting Submission opened a dark portal to another dimension " +
            "where a darkness known as the " +
            "Wicked Inter-dimensional Night Time Eradication Riders ruled " +
            "the land. The W.I.N.T.E.R quickly spread throughout I.B.I.S " +
            "and the rest of The Pond until almost all of the light had " +
            "faded from the land. Almost all. It was then that the Heroes " +
            "rose. These heroes fought and fought against the W.I.N.T.E.R," +
            " beating it back until their leader fell in battle, " +
            "Mallard the Brave as he was known. The W.I.N.T.E.R then began " +
            "to slowly push the heroes north until all the remaining heroes" +
            " had left was a single village as a foothold. It was here that" +
            " the heroes created the the Dashing United Crisis Kill Squad." +
            " And now a war between the D.U.C.K.S and the I.B.I.S wages in" +
            " the north with the D.U.C.K.S pushing forwards, Southbound as" +
            " ever to fight against the eclipsing W.I.N.T.E.R.",
            "historySection", "/ui/duxcom-icon.png"),
    LANGUAGE("Language", "The language of the ancients is found scattered" +
            "throughout the land, inscribed on pieces of gear, ancient " +
            "potions, and used to mark the resting places of the " +
            "dead.","languageSection", "/game language/duXcomCheatSheet.png");

    GlossarySection glossarySection;

    /**
     * Constructor to retrieve inputs, stores a glossarySection
     *
     * @param name Name given to the section
     * @param text The Text of the section
     * @param id ID given to the section (Buttons will have same ID)
     * @param image The image that will be displayed for this section
     */
    WorldSections(String name, String text, String id, String image) {
        this.glossarySection = new GlossarySection(name, text, id, image);
    }
}
