package uq.deco2800.duxcom.entities;

import uq.deco2800.duxcom.loot.LootRarity;

/**
 * Entities that can be picked up
 * 
 * @author The_Magic_Karps
 */
public abstract class PickableEntities extends Entity {

    /**
     * Rarity of the loot to be generated
     */
    private LootRarity rarity;
    
    /**
     * Contructor to make pickable entities that will extend entity
     * 
     * @param entityType EntityType of the entity
     * @param x x coordinate the entity will reside in
     * @param y y coordinate the entity will reside in
     * @param lengthX the x length of the entity
     * @param lengthY the y length of the entity
     */
    public PickableEntities(EntityType entityType, int x, int y, int lengthX, int lengthY) {
        super(entityType, x, y, lengthX, lengthY);
    }
    
    /**
     * Set the rarity of the loot to be generated
     * 
     * @param rarity rarity of loot to be generated
     */
    public void setLootRarity(LootRarity rarity) {
        this.rarity = rarity;
    }
    
    /**
     * Retrieves the rarity of the loot to be generated
     * 
     * @return rarity of the loot to be generated
     */
    public LootRarity getLootRarity() {
        return this.rarity;
    }
}
