package uq.deco2800.duxcom.items;

import uq.deco2800.duxcom.entities.heros.HeroStat;
import uq.deco2800.duxcom.inventory.LootInventory;
import uq.deco2800.duxcom.items.armour.Armour;
import uq.deco2800.duxcom.items.consumable.Food;
import uq.deco2800.duxcom.items.consumable.Potion;
import uq.deco2800.duxcom.items.shield.Shield;
import uq.deco2800.duxcom.items.weapon.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Base Item
 *
 * @author Team 10 = ducksters
 */
public class ItemGenerate {
    /*
    To generate a single item, use ItemGenerate.itemType.itemName.generate()

    To generate loot, create a LootInventory and addLoot()
    or, use ItemGenerate.loot() and pass it the inventory you want to deposit
    the loot into

    To generate all items, use allItems method;
     */
    private static HashMap<ItemMethods, RarityLevel> itemRarityMap = new HashMap<>();

    /**
     * Creates a map of an items enum and its rarity.
     * Used for generating loot. Saves a lot of time as this way the loot method doesn't have to
     * essentially create every item in the game if it wants to find an appropriate loot item.
     */
    static {
        for (sword item : sword.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (axe item : axe.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (dagger item : dagger.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (hammer item : hammer.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (shield item : shield.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (poisonPotion item : poisonPotion.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (firePotion item : firePotion.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (wetPotion item : wetPotion.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (staff item : staff.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
//        for (bodyArmour item : bodyArmour.values()) {
//            itemRarityMap.put(item, item.getRarity());
//        }
//        for (electricalStaff item : electricalStaff.values()) {
//            itemRarityMap.put(item, item.getRarity());
//        }
//        for (fireStaff item : fireStaff.values()) {
//            itemRarityMap.put(item, item.getRarity());
//        }
//        for (waterStaff item : waterStaff.values()) {
//            itemRarityMap.put(item, item.getRarity());
//        }
        for (bow item : bow.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
        for (lance item : lance.values()) {
            itemRarityMap.put(item, item.getRarity());
        }
    }

    /**
     * An interface necessary for loot generation.
     */
    private interface ItemMethods {
        Item generate();

        RarityLevel getRarity();
    }

    /**
     * a list of every shield in the game
     */
    public enum shield implements ItemMethods {
//        WOODEN_SHEILD(RarityLevel.COMMON, "Wooden Shield", 150, 100, 20, 80),
//        IRON_SHIELD(RarityLevel.COMMON, "Iron Shield", 150, 100, 20, 80),
        STEEL_SHIELD(RarityLevel.COMMON, "Steel Shield", 150, 100, 20, 80);
//        PLATINUM_SHIELD(RarityLevel.COMMON, "Platinum Shield", 150, 100, 20, 80),
//        ADAMANTIUM_SHIELD(RarityLevel.LEGENDARY, "Adamantium Shield", 150, 100, 20, 80);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int armour;
        private int cost;


        shield(RarityLevel rarity, String name, int weight, int durability, int armour, int cost) {
            this.rarity = rarity;
            this.name = name;
            this.durability = durability;
            this.armour = armour;
            this.cost = cost;
            this.weight = weight;
        }

        @Override
        public Shield generate() {
            return constructShield(rarity, name, weight, durability, armour, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

//    /**
//     * a list of every bodyArmour in the game
//     */
//    public enum bodyArmour implements ItemMethods {
//        WOODEN_ARMOUR(RarityLevel.COMMON, "Wooden Armour", 150, 100, 20, 80),
//        IRON_ARMOUR(RarityLevel.COMMON, "Iron Armour", 150, 100, 20, 80),
//        STEEL_ARMOUR(RarityLevel.COMMON, "Steel Armour", 150, 100, 20, 80),
//        PLATINUM_ARMOUR(RarityLevel.COMMON, "Platinum Armour", 150, 100, 20, 80),
//        TATTERED_ROBE(RarityLevel.COMMON, "Tattered Robe", 150, 100, 20, 80);
//
//        private RarityLevel rarity;
//        private String name;
//        private int weight;
//        private int durability;
//        private int armour;
//        private int cost;
//
//
//        bodyArmour(RarityLevel rarity, String name, int weight, int durability, int armour, int cost) {
//            this.rarity = rarity;
//            this.name = name;
//            this.durability = durability;
//            this.armour = armour;
//            this.cost = cost;
//            this.weight = weight;
//        }
//
//        @Override
//        public Armour generate() {
//            return constructArmour(rarity, name, weight, durability, armour, cost);
//        }
//
//        @Override
//        public RarityLevel getRarity() {
//            return rarity;
//        }
//    }

    /**
     * a list of every sword in the game
     */
    public enum sword implements ItemMethods {
        BRONZE_SWORD(RarityLevel.COMMON, "Bronze Sword", "sword1", 150, 110, 80, 30),
//        IRON_SWORD(RarityLevel.COMMON, "Iron Sword", 150, 110, 120, 110),
        STEEL_SWORD(RarityLevel.COMMON, "Steel Sword", "sword2", 140, 110, 190, 300),
//        PLATINUM_SWORD(RarityLevel.EPIC, "Platinum Sword", 120, 110, 250, 900),
        VALYRIAN_STEEL_SWORD(RarityLevel.UNCOMMON, "Valyrian Steel Sword", "sword3", 150, 200, 350, 1500);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        sword(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Sword generate() {
            return constructSword(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every axe in the game
     */
    public enum axe implements ItemMethods {

        BRONZE_AXE(RarityLevel.COMMON, "Bronze Axe", "axe1", 190, 110, 100, 30),
//        IRON_AXE(RarityLevel.COMMON, "Iron Axe", 190, 110, 130, 110),
        STEEL_AXE(RarityLevel.COMMON, "Steel Axe", "axe2", 180, 110, 220, 300),
        PLATINUM_AXE(RarityLevel.UNCOMMON, "Platinum Axe", "axe3", 160, 110, 280, 900);
//        SHINNING_AXE(RarityLevel.LEGENDARY, "Shinning Axe", 160, 150, 100, 140);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        axe(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Axe generate() {
            return constructAxe(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every dagger in the game
     */
    public enum dagger implements ItemMethods {

        //TODO: add appropriate stats
        BRONZE_DAGGER(RarityLevel.COMMON, "Bronze Dagger", "dagger1", 190, 110, 100, 30),
//        IRON_DAGGER(RarityLevel.COMMON, "Iron Dagger", 190, 110, 130, 110),
        STEEL_DAGGER(RarityLevel.COMMON, "Steel Dagger", "dagger2", 180, 110, 220, 300),
//        PLATINUM_DAGGER(RarityLevel.EPIC, "Platinum Dagger", 160, 110, 280, 900),
        CEREMONIAL_DAGGER(RarityLevel.UNCOMMON, "Ceremonial Dagger", "dagger3", 160, 110, 280, 900);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        dagger(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Dagger generate() {
            return constructDagger(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every hammer in the game
     */
    public enum hammer implements ItemMethods {

        //TODO: add appropriate stats
        BRONZE_HAMMER(RarityLevel.COMMON, "Bronze Hammer", "hammer1", 160, 110, 280, 900),
//        IRON_HAMMER(RarityLevel.COMMON, "Iron Hammer", 160, 110, 280, 900),
        STEEL_HAMMER(RarityLevel.COMMON, "Steel Hammer", "hammer2", 160,110, 280, 900),
        PLATINUM_HAMMER(RarityLevel.UNCOMMON, "Platinum Hammer", "hammer3", 160, 110, 280, 900);
//        THORS_HAMMER(RarityLevel.EPIC, "Thors Hammer", 160, 110, 280, 900);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        hammer(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Hammer generate() {
            return constructHammer(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every staff in the game
     */
    public enum staff implements ItemMethods {

        APPRENTICE_STAFF(RarityLevel.COMMON, "Apprentice Staff", "staff1", 150, 110, 80, 30),
        MAGE_STAFF(RarityLevel.COMMON, "Mage Staff", "staff2", 150, 110, 80, 30),
        GANDALF_STAFF(RarityLevel.UNCOMMON, "Gandalfs Staff", "staff3", 150, 110, 80, 30);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        staff(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Staff generate() {
            return constructStaff(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every electrical lance in the game
     */
    public enum lance implements ItemMethods {

        BRONZE_LANCE(RarityLevel.COMMON, "Bronze Lance", "lance1", 150, 110, 80, 30),
        PLATINUM_LANCE(RarityLevel.COMMON, "Platinum Lance", "lance2", 150, 110, 80, 30),
        LEGENDARY_LANCE(RarityLevel.UNCOMMON, "Legendary Lance", "lance3", 150, 110, 80, 30);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        lance(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Lance generate() {
            return constructLance(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

//    /**
//     * a list of every electrical staff in the game
//     */
//    public enum electricalStaff implements ItemMethods {
//
//        ZAPPY_STAFF(RarityLevel.COMMON, "Zappy Staff", 150, 110, 80, 30),
//        SPARK_STAFF(RarityLevel.COMMON, "Spark Staff", 150, 110, 80, 30),
//        THUNDER_STAFF(RarityLevel.UNCOMMON, "Thunder Staff", 150, 110, 80, 30),
//        STORM_STICK(RarityLevel.UNCOMMON, "Storm Stick", 150, 110, 80, 30);
//
//        private RarityLevel rarity;
//        private String name;
//        private int weight;
//        private int durability;
//        private int damage;
//        private int cost;
//
//        electricalStaff(RarityLevel rarity, String name, int... stats) {
//            this.rarity = rarity;
//            this.weight = stats[0];
//            this.durability = stats[1];
//            this.damage = stats[2];
//            this.cost = stats[3];
//            this.name = name;
//        }
//
//        @Override
//        public ElectricalStaff generate() {
//            return constructStaff(rarity, name, weight, durability, damage, cost);
//        }
//
//        @Override
//        public RarityLevel getRarity() {
//            return rarity;
//        }
//    }
//
//    /**
//     * a list of every fire staff in the game
//     */
//    public enum fireStaff implements ItemMethods {
//
//        EMBER_STAFF(RarityLevel.COMMON, "Ember Staff", 150, 110, 80, 30),
//        FLAME_STAFF(RarityLevel.COMMON, "Flame Staff", 150, 110, 80, 30),
//        INFERNO_STAFF(RarityLevel.RARE, "Inferno Staff", 150, 110, 80, 30),
//        //geddit, hot staff xD. Meme level = RarityLevel.EPIC
//        HOT_STAFF(RarityLevel.EPIC, "Hot Staff", 150, 110, 80, 30);
//
//        private RarityLevel rarity;
//        private String name;
//        private int weight;
//        private int durability;
//        private int damage;
//        private int cost;
//
//        fireStaff(RarityLevel rarity, String name, int... stats) {
//            this.rarity = rarity;
//            this.weight = stats[0];
//            this.durability = stats[1];
//            this.damage = stats[2];
//            this.cost = stats[3];
//            this.name = name;
//        }
//
//        @Override
//        public Staff generate() {
//            return constructFireStaff(rarity, name, weight, durability, damage, cost);
//        }
//
//        @Override
//        public RarityLevel getRarity() {
//            return rarity;
//        }
//    }
//
//    /**
//     * a list of every magic staff in the game
//     */
//    public enum waterStaff implements ItemMethods {
//
//        NOVICE_STAFF(RarityLevel.COMMON, "Novice Staff", 150, 110, 80, 30),
//        APPRENTICE_STAFF(RarityLevel.COMMON, "Apprentice Staff", 150, 110, 80, 30),
//        MAGE_STAFF(RarityLevel.RARE, "Mage Staff", 150, 110, 80, 30),
//        WIZARD_STAFF(RarityLevel.EPIC, "Wizard Staff", 150, 110, 80, 30),
//        GANDALFS_STAFF(RarityLevel.EPIC, "Gandalfs Staff", 150, 110, 80, 30),
//        MOIST_STAFF(RarityLevel.COMMON, "Moist Staff", 150, 110, 80, 30),
//        DAMP_STAFF(RarityLevel.COMMON, "Damp Staff", 150, 110, 80, 30),
//        WAVE_STAFF(RarityLevel.RARE, "Wave Staff", 150, 110, 80, 30),
//        TSUNAMI_STAFF(RarityLevel.EPIC, "Tsunami Staff", 150, 110, 80, 30);
//
//        private RarityLevel rarity;
//        private String name;
//        private int weight;
//        private int durability;
//        private int damage;
//        private int cost;
//
//        waterStaff(RarityLevel rarity, String name, int... stats) {
//            this.rarity = rarity;
//            this.weight = stats[0];
//            this.durability = stats[1];
//            this.damage = stats[2];
//            this.cost = stats[3];
//            this.name = name;
//        }
//
//        @Override
//        public WaterStaff generate() {
//            return constructWaterStaff(rarity, name, weight, durability, damage, cost);
//        }
//
//        @Override
//        public RarityLevel getRarity() {
//            return rarity;
//        }
//    }

    /**
     * a list of every bow in the game
     */
    public enum bow implements ItemMethods {

//        TRAINING_BOW(RarityLevel.COMMON, "Training Bow", 150, 110, 80, 30),
        WOODEN_BOW(RarityLevel.COMMON, "Wooden Bow", "bow1", 150, 110, 80, 30),
//        COMPOSITE_BOW(RarityLevel.COMMON, "Composite Bow", 150, 110, 80, 30),
        ELVISH_BOW(RarityLevel.COMMON, "Elvish Bow", "bow2", 150, 110, 80, 30),
        SACRED_BOW(RarityLevel.COMMON, "Sacred Bow", "bow3", 150, 110, 80, 30);

        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;
        private String texture;

        bow(RarityLevel rarity, String name, String texture, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
            this.texture = texture;
        }

        @Override
        public Bow generate() {
            return constructBow(rarity, name, texture, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every poison potion grenade in the game
     */
    public enum poisonPotion implements ItemMethods {

        POISON_BOMB(RarityLevel.UNCOMMON, "Poison Bomb", 150, 110, 80, 30);


        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;

        poisonPotion(RarityLevel rarity, String name, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
        }

        @Override
        public PoisonPotion generate() {
            return constructPoisonPotion(rarity, name, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every fire potion grenade in the game
     */
    public enum firePotion implements ItemMethods {

        FIRE_BOMB(RarityLevel.UNCOMMON, "Fire Bomb", 150, 110, 80, 30);


        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;

        firePotion(RarityLevel rarity, String name, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
        }

        @Override
        public FirePotion generate() {
            return constructFirePotion(rarity, name, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every wet potion grenade in the game
     */
    public enum wetPotion implements ItemMethods {

        WET_BOMB(RarityLevel.UNCOMMON, "Wet Bomb", 150, 110, 80, 30);


        private RarityLevel rarity;
        private String name;
        private int weight;
        private int durability;
        private int damage;
        private int cost;

        wetPotion(RarityLevel rarity, String name, int... stats) {
            this.rarity = rarity;
            this.weight = stats[0];
            this.durability = stats[1];
            this.damage = stats[2];
            this.cost = stats[3];
            this.name = name;
        }

        @Override
        public WetPotion generate() {
            return constructWetPotion(rarity, name, weight, durability, damage, cost);
        }

        @Override
        public RarityLevel getRarity() {
            return rarity;
        }
    }

    /**
     * a list of every food item in the game
     */
    public enum food {

        GUZMANS_BURRITO("Guzmans Burrito", 10, 15, HeroStat.HEALTH, 50),
        BREAD("Bread", 5, 5, HeroStat.HEALTH, 20);


        private String name;
        private int weight;
        private int cost;
        HeroStat stat;
        int strength;


        food(String name, int weight, int cost, HeroStat stat, int strength) {
            this.weight = weight;
            this.strength = strength;
            this.cost = cost;
            this.name = name;
            this.stat = stat;
        }

        public Food generate() {
            return constructFood(name, weight, cost, stat, strength);
        }
    }

    /**
     * a list of every potion in the game
     */
    public enum potion {

        HEALTH_POTION("Health Potion", 10, 100, HeroStat.HEALTH, 100, 5),
        AP_POTION("AP Potion", 10, 100, HeroStat.ACTION_POINTS, 100, 5);


        private String name;
        private int weight;
        private int cost;
        HeroStat stat;
        int strength;
        int duration;

        potion(String name, int weight, int cost, HeroStat stat, int strength, int duration) {
            this.weight = weight;
            this.strength = strength;
            this.cost = cost;
            this.name = name;
            this.stat = stat;
            this.duration = duration;
        }

        public Potion generate() {
            return constructPotion(name, weight, cost, stat, strength, duration);
        }
    }


    /* Default properties for each item */
    private static final int BASEWEAPONWEIGHT = 100;
    private static final int BASEWEAPONCOST = 100;
    private static final int BASEWEAPONDAMAGE = 100;
    private static final int BASEWEAPONDURABILITY = 100;

    private static int getItemCount() {
        return hammer.values().length + axe.values().length +
                dagger.values().length + sword.values().length
                + shield.values().length + poisonPotion.values().length
                + firePotion.values().length + wetPotion.values().length
//                + bodyArmour.values().length + electricalStaff.values().length
//                + fireStaff.values().length + waterStaff.values().length
                + staff.values().length + lance.values().length +
                + bow.values().length + potion.values().length
                + food.values().length;
    }

    /**
     * creates an array of every item in the game. Useful for things like the shop which needs to
     * list every item
     *
     * @return an array of every item in the game
     */
    public static Item[] allItems() {
        Item[] itemArray = new Item[getItemCount()];
        int i = 0;

        for (sword item : sword.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (axe item : axe.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (dagger item : dagger.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (hammer item : hammer.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (shield item : shield.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (poisonPotion item : poisonPotion.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (firePotion item : firePotion.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (wetPotion item : wetPotion.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (staff item : staff.values()) {
            itemArray[i] = item.generate();
            i++;
        }
//        for (electricalStaff item : electricalStaff.values()) {
//            itemArray[i] = item.generate();
//            i++;
//        }
//        for (fireStaff item : fireStaff.values()) {
//            itemArray[i] = item.generate();
//            i++;
//        }
//        for (waterStaff item : waterStaff.values()) {
//            itemArray[i] = item.generate();
//            i++;
//        }
//        for (bodyArmour item : bodyArmour.values()) {
//            itemArray[i] = item.generate();
//            i++;
//        }
        for (food item : food.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (potion item : potion.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (bow item : bow.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        for (lance item : lance.values()) {
            itemArray[i] = item.generate();
            i++;
        }
        return itemArray;
    }


    /**
     * adds a random item to the inventory its given
     *
     * @param lootLevel  the rarity of the item to be added
     * @param inventory  the inventory for the item to be stored in
     * @param slotNumber the slotnumber for the item to be stored in
     */
    public static void loot(RarityLevel lootLevel,
                            LootInventory inventory,
                            int slotNumber) {
        // The amount of items with that rarity level
        int count = 0;
        for (RarityLevel rarityLevel : itemRarityMap.values()) {
            if (rarityLevel.equals(lootLevel)) {
                count++;
            }
        }

        if (count == 0) {
            return;
        }

        Random randomInt = new Random();
        // A random index from 0 -> how many items have that loot level
        int index = randomInt.nextInt(count);
        //Incrementer
        int i = 0;
        //go through each item
        for (ItemMethods item : itemRarityMap.keySet()) {
            if (item.getRarity().equals(lootLevel)) {
                //if this item is at the same index as the random number 'index', then add this item
                if (i == index) {
                    Item generateItem = item.generate();

                    /*There is a weird bug where duplicate items can appear in a chest, this should
                    fix this.
                     */
                    if (!inventory.containsItem(generateItem)) {
                        inventory.addItem(generateItem, slotNumber);
                        return;
                    } else {
                        /*
                        If the item was in the chest before it was even added (???) then generate
                        a new item and check again */
                        i--;
                    }
                }
                i++;
            }
        }
        // On the rare case that this line is reached, no item will be added.
    }


    /**
     * Generates a spriteName of an item using the items name as input, for example, an Iron Sword
     * has the spriteName item/weapon/IronSword.png
     *
     * @param itemName the name of the item
     * @return the spriteName of the item
     */
    private static String spriteName(String itemName) {
        return itemName.replaceAll("\\s", "_").toLowerCase();
    }

    /**
     * will be used to generate the filenames for the image regestry
     */
    private static String spriteLocation(String itemName) {
        String directory = "item/weapon/";
        String fileName = itemName.replaceAll("\\s", "");
        String fileExtension = ".png";
        return directory + fileName + fileExtension;
    }

    /**
     * A shield generated with default properties
     *
     * @return A shield with default properties
     */
    private static Shield constructShield(RarityLevel rarity, String name,
                                          int baseWeight, int baseDurability,
                                          int baseArmour, int baseCost) {
        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseArmour, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int armour = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Shield(name, cost, weight, inventorySpriteName,
                durability, armour, rarity);
    }

    /**
     * An bodyArmour generated with default properties
     *
     * @return An bodyArmour with default properties
     */
    private static Armour constructArmour(RarityLevel rarity, String name,
                                          int baseWeight, int baseDurability,
                                          int baseArmour, int baseCost) {
        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseArmour, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int armour = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Armour(name, cost, weight, inventorySpriteName,
                true, durability, armour, rarity);
    }

    /**
     * A sword generated with default properties
     *
     * @return A sword with default properties
     */
    private static Sword constructSword(RarityLevel rarity, String name, String texture,
                                        int baseWeight, int baseDurability,
                                        int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Sword(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    private static Staff constructStaff(RarityLevel rarity, String name, String texture,
                                        int baseWeight, int baseDurability,
                                        int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Staff(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

//    /**
//     * An Electrical staff  generated with default properties
//     *
//     * @return An electrical staff with default properties
//     */
//    private static ElectricalStaff constructStaff(RarityLevel rarity, String name,
//                                                            int baseWeight, int baseDurability,
//                                                            int baseDamage, int baseCost) {
//
//        //Don't change code below this line. It randomizes the stats a little
//        // bit so that each weapon can be somewhat unique
//        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
//                , baseDamage, baseCost);
//
//        int weight = stats.get(0);
//        int durability = stats.get(1);
//        int damage = stats.get(2);
//        int cost = stats.get(3);
//
//        String inventorySpriteName = spriteName(name);
//
//        //This is where the constructSword is actually constructed.
//        return new ElectricalStaff(name, cost, weight, inventorySpriteName,
//                durability, damage, rarity);
//    }
//
//    /**
//     * A Fire staff  generated with default properties
//     *
//     * @return A fire staff with default properties
//     */
//    private static Staff constructFireStaff(RarityLevel rarity, String name,
//                                                int baseWeight, int baseDurability,
//                                                int baseDamage, int baseCost) {
//
//        //Don't change code below this line. It randomizes the stats a little
//        // bit so that each weapon can be somewhat unique
//        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
//                , baseDamage, baseCost);
//
//        int weight = stats.get(0);
//        int durability = stats.get(1);
//        int damage = stats.get(2);
//        int cost = stats.get(3);
//
//        String inventorySpriteName = spriteName(name);
//
//        //This is where the constructSword is actually constructed.
//        return new Staff(name, cost, weight, inventorySpriteName,
//                durability, damage, rarity);
//    }
//
//    /**
//     * A Water staff  generated with default properties
//     *
//     * @return A water staff with default properties
//     */
//    private static WaterStaff constructWaterStaff(RarityLevel rarity, String name,
//                                                  int baseWeight, int baseDurability,
//                                                  int baseDamage, int baseCost) {
//
//        //Don't change code below this line. It randomizes the stats a little
//        // bit so that each weapon can be somewhat unique
//        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
//                , baseDamage, baseCost);
//
//        int weight = stats.get(0);
//        int durability = stats.get(1);
//        int damage = stats.get(2);
//        int cost = stats.get(3);
//
//        String inventorySpriteName = spriteName(name);
//
//        //This is where the constructSword is actually constructed.
//        return new WaterStaff(name, cost, weight, inventorySpriteName,
//                durability, damage, rarity);
//    }

    /**
     * A Bow  generated with default properties
     *
     * @return A bow with default properties
     */
    private static Bow constructBow(RarityLevel rarity, String name, String texture,
                                    int baseWeight, int baseDurability,
                                    int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Bow(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    /**
     * A Lance  generated with default properties
     *
     * @return A lance with default properties
     */
    private static Lance constructLance(RarityLevel rarity, String name, String texture,
                                    int baseWeight, int baseDurability,
                                    int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new Lance(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    /**
     * A Food  generated with default properties
     *
     * @return A food with default properties
     */
    private static Food constructFood(String name, int weight, int cost, HeroStat stat,
                                      int strength) {
        String inventorySpriteName = spriteName(name);
        return new Food(name, cost, weight, inventorySpriteName, stat, strength);
    }

    private static Potion constructPotion(String name, int weight, int cost, HeroStat stat,
                                          int strength, int duration) {
        String inventorySpriteName = spriteName(name);
        return new Potion(name, cost, weight, inventorySpriteName, stat, strength, duration);
    }

    /**
     * A poison potion generated with default properties
     *
     * @return A posion potion with default properties
     */
    private static PoisonPotion constructPoisonPotion(RarityLevel rarity, String name,
                                                      int baseWeight, int baseDurability,
                                                      int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new PoisonPotion(name, cost, weight, inventorySpriteName,
                durability, damage, rarity);
    }

    /**
     * A fire potion grenade generated with default properties
     *
     * @return A fire potion grenade with default properties
     */
    private static FirePotion constructFirePotion(RarityLevel rarity, String name,
                                                  int baseWeight, int baseDurability,
                                                  int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new FirePotion(name, cost, weight, inventorySpriteName,
                durability, damage, rarity);
    }

    /**
     * A wet potion generated with default properties
     *
     * @return A wet potion with default properties
     */
    private static WetPotion constructWetPotion(RarityLevel rarity, String name,
                                                int baseWeight, int baseDurability,
                                                int baseDamage, int baseCost) {

        //Don't change code below this line. It randomizes the stats a little
        // bit so that each weapon can be somewhat unique
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        //This is where the constructSword is actually constructed.
        return new WetPotion(name, cost, weight, inventorySpriteName,
                durability, damage, rarity);
    }

    /**
     * An axe generated with default properties
     *
     * @return An axe with default properties
     */
    private static Axe constructAxe(RarityLevel rarity, String name, String texture,
                                    int baseWeight, int baseDurability, int baseDamage,
                                    int baseCost) {

        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        return new Axe(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    /**
     * A dagger generated with default properties
     *
     * @return A dagger with default properties
     */
    private static Dagger constructDagger(RarityLevel rarity, String name, String texture,
                                          int baseWeight, int baseDurability,
                                          int baseDamage, int baseCost) {
        //randomize(Weight, Durability, Damage, Cost)
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        return new Dagger(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    /**
     * A hammer generated with default properties
     *
     * @return A hammer with default properties
     */
    private static Hammer constructHammer(RarityLevel rarity, String name, String texture,
                                          int baseWeight, int baseDurability,
                                          int baseDamage, int baseCost) {
        ArrayList<Integer> stats = randomize(baseWeight, baseDurability
                , baseDamage, baseCost);

        int weight = stats.get(0);
        int durability = stats.get(1);
        int damage = stats.get(2);
        int cost = stats.get(3);

        String inventorySpriteName = spriteName(name);

        return new Hammer(name, cost, weight, inventorySpriteName,
                durability, damage, rarity, texture);
    }

    /**
     * A class that takes a generated weapons default attributes and randomizes them.
     *
     * @param weightModifier     the weapons default weight
     * @param durabilityModifier the weapons default durability
     * @param damageModifier     the weapons default damage
     * @param costModifier       the weapons default cost
     * @return a list of all the newly randomized attributes
     */
    private static ArrayList<Integer> randomize(int weightModifier,
                                                int durabilityModifier,
                                                int damageModifier,
                                                int costModifier) {
        Random randomGenerator = new Random();
        // randomModifier increases or decreases stat by up to 10%
        int weightRandModifier = randomGenerator.nextInt(20) + 90;
        int damageRandModifier = randomGenerator.nextInt(20) + 90;

        // cost increases if damage increases, decreases if weight increases
        int costRandModifier = damageRandModifier * 2 - weightRandModifier;

        // convert from integer to percent by dividing by 100^2
        ArrayList<Integer> stats = new ArrayList<>();
        stats.add(BASEWEAPONWEIGHT * weightModifier * weightRandModifier / 10000);
        stats.add(BASEWEAPONDURABILITY * durabilityModifier / 100);
        stats.add(BASEWEAPONDAMAGE * damageModifier * damageRandModifier / 10000);
        stats.add(BASEWEAPONCOST * costModifier * costRandModifier / 10000);
        return stats;
    }

}

