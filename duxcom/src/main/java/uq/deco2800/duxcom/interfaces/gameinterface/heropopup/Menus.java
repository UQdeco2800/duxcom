package uq.deco2800.duxcom.interfaces.gameinterface.heropopup;

/**
 * Ticket #66 - HeroPopUp Menu Enumeration that stores all the sections in HeroPopUp
 *
 * @author The_Magic_Karps & Ducksters
 */
public enum Menus {
    EQUIPPED("Equipped", "/ui/fxml/heropopupmenu/equipped.fxml", "equippedSection"),
    CHEST("Local Chest", "/ui/fxml/heropopupmenu/localChest.fxml", "chestSection"),
    INVENTORY("Inventory", "/ui/fxml/heropopupmenu/inventory.fxml", "inventorySection"),
    ABILITIES("Abilities", "/ui/fxml/heropopupmenu/abilities.fxml", "abilitiesSection");

    // FXML location of the section
    private String location;

    // Name given to the section
    private String name;

    // ID given to the section (Buttons will have same ID)
    private String id;

    /**
     * Constructor to retrieve inputs
     *
     * @param name     Name given to the section
     * @param location FXML location of the section
     * @param id       ID given to the section (Buttons will have same ID)
     */
    Menus(String name, String location, String id) {
        this.location = location;
        this.name = name;
        this.id = id;
    }

    /**
     * Retrieves FXML file location
     *
     * @return location of fxml files
     */
    public String getLocation() {
        return this.location;
    }

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
}
