/*
 * Contact Group 10 - ducksters for detail
 * Ticket Issue: #12 - Pickup Items and Inventory
 */

package uq.deco2800.duxcom.items;

/**
 * Base Item
 *
 * @author Team 10 = ducksters
 */
public abstract class Item {

    // Properties that all items have:
    protected String name;		 /* the name of the item */
    protected int cost;          /* the monetary value of the item */
    protected int weight;        /* weight of the item */
    protected String inventorySpriteName; /* inventory sprite name location of the item */
    protected boolean tradable;  /* describes tradability of the item */
    protected ItemType type;
    protected String image = "unregistered_texture";
    private String description;
    private String statsString;

    /**
     * This is the base Item constructor, all parameters are required.
     *
     * @param name                of the item
     * @param cost                of the item
     * @param weight              of the item
     * @param inventorySpriteName is the resource location of the item
     * @param tradable            allows the item to be tradable
     * @param type                of the item
     */
    public Item(String name, int cost, int weight, String inventorySpriteName, boolean tradable,
                ItemType type) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.inventorySpriteName = inventorySpriteName;
        this.tradable = tradable;
        this.type = type;
        this.description = null;
    }



    public ItemType getType() {
        return type;
    }

    /**
     * Returns the monetary value associated with this item
     *
     * @return the cost of the item
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns the weight of this item
     *
     * @return the weight of the item
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Returns the sprites name location in resources of which is the sprite to be used to show the
     * item in the inventory
     *
     * @return the items sprite
     */
    public String getInvetorySpriteName() {
        return this.inventorySpriteName;
    }

    /**
     * Returns the true if the item can be traded otherwise, the item is not meant to be traded.
     *
     * @return the tradability of the item
     */
    public boolean isTradable() {
        return this.tradable;
    }

    /**
     * Returns the name of the item.
     *
     * @return assigned name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the image texture string of the item.
     *
     * @return image texture string
     */
    public String getImage() {
        return this.image;
    }

    public String getDescription() {
        if (description == null) {
            description = DescriptionGenerate.descriptionGenerate(this);
        }
        return description;
    }

    public String getStatsString() {
        if (statsString == null) {
            statsString = DescriptionGenerate.statsGenerate(this);
        }
        return statsString;
    }

    /**
     * Evaluates if the item is equivalent to the one being compare to. Will return true if all the
     * item properties are the same.
     *
     * @param o (item) to compare against
     * @return true if the items are equivalent, else false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (cost != item.cost) {
            return false;
        }
        if (weight != item.weight) {
            return false;
        }
        if (tradable != item.tradable) {
            return false;
        }
        if (!name.equals(item.name)) {
            return false;
        }
        return inventorySpriteName != null ? inventorySpriteName.equals(item.inventorySpriteName) :
                item.inventorySpriteName == null;
    }

    /**
     * Generate a hash code for the item.
     *
     * @return a unique code for the items state.
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.name.hashCode();
        hash = hash * 41 + this.weight;
        hash = hash * 73 + this.cost;
        hash = hash * 79 + this.inventorySpriteName.hashCode();
        hash = hash * 83 + (this.tradable ? 1 : 0);
        return hash;
    }

}