package uq.deco2800.duxcom.entities.heros;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.abilities.AbilitySelected;
import uq.deco2800.duxcom.abilities.AbstractAbility;
import uq.deco2800.duxcom.abilities.Slash;
import uq.deco2800.duxcom.abilities.Targetable;
import uq.deco2800.duxcom.buffs.AbstractBuff;
import uq.deco2800.duxcom.dataregisters.HeroDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.EntityType;
import uq.deco2800.duxcom.entities.heros.listeners.ActionPointListener;
import uq.deco2800.duxcom.entities.heros.listeners.ItemEquipListener;
import uq.deco2800.duxcom.entities.heros.listeners.MovementListener;
import uq.deco2800.duxcom.graphics.TextureRegister;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.HeroPopUpController;
import uq.deco2800.duxcom.interfaces.gameinterface.heropopup.controllers.HeroStatisticsController;
import uq.deco2800.duxcom.inventory.HeroInventory;
import uq.deco2800.duxcom.items.EquippableItem;
import uq.deco2800.duxcom.items.Item;
import uq.deco2800.duxcom.items.ItemType;
import uq.deco2800.duxcom.items.weapon.Weapon;
import uq.deco2800.duxcom.passives.AbstractPassive;
import uq.deco2800.duxcom.sound.SoundPlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstraction of the Heroes in the game. Heroes are an extension of entity that are able to be
 * targetted, turnticked, and saved.
 *
 * @author woody
 */
public abstract class AbstractHero extends AbstractCharacter implements ItemEquipListener{

	// Logger
    private static Logger logger = LoggerFactory.getLogger(AbstractHero.class);
	
    private String name = "placeholder_name";
    protected HeroType heroType;
    protected int level = 0;
    protected int xp = 0;
    protected String profileImage = "profile_placeholder";
    
    /**
     * Set the name of a hero
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set the profile iamge of a hero
     */
    public void setProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
    
    public String getProfileImage(){
    	return this.profileImage;
    }
    
    /**
     * action points are spent to perform actions like moving or using an ability
     * each hero will have a base AP and a recharge AP - at the start of each hero's turn,
     * they will replenish a certain amount of AP allowing them to "save up" to perform actions.
     * <p>
     * if a hero does not have enough AP to use an action, it will not be allowed to be used.
     */
    protected int baseAP;
    protected int rechargeAP;
    protected static final int MAX_LEVEL = 10;
    protected int maxXP = 1000;
    protected AbstractAbility weaponAbility;
    protected AbstractAbility utilityAbility;
    protected List<AbstractPassive> passives = new ArrayList<>();

    //Use 'changeAP' to modify AP variable. Ensures listeners are notified.
    protected int actionPoints;

    protected List<ActionPointListener> actionPointListeners = new ArrayList<>();
    protected List<MovementListener> movementListeners = new ArrayList<>();

    protected boolean actionPointListenersEnabled = false;
    protected boolean movementListenersEnabled = true;

    // Visibility range. Set to 7 by default.
    protected int visibilityRange = 7;

    // Heros inventory, only 12 items
    protected HeroInventory inventory = new HeroInventory(12);

    //AP cost for hero to move to the given point
    protected Float[][] movementCost;
    
    /**
     * Stores the most  efficient path back to the hero from the given point
     * 0 means the fastest path is x + 1
     * 1 means the fastest path is x - 1
     * 2 means the fastest path is y + 1
     * 3 means the fastest path is y - 1
     */
    protected Integer[][] movementPath;

    //a string of upgraded stats
    ArrayList<String> upgraded;

    private int upgradePoints = 0;

    /**
     * Class constructor
     *
     * @param entityType The entity type of the hero to be created
     * @param x          the x coordinate of the hero
     * @param y          the y coordinate of the hero
     */
    public AbstractHero(EntityType entityType, int x, int y) {
        super(entityType, x, y, 1, 1);
        inventory.addItemEquipListener(this);
    }

    /**
     * Attempt to move the hero to a new position on the map.
     * <p>
     * This probably should not be called directly - use the moveEntity method in map.
     *
     * @return true if hero has enough AP to move the new location. False otherwise.
     */
    @Override
    public boolean move(int x, int y) {
        // Note that if a hero cannot reach a tile it has null movement cost.

        if (movementCost[x][y] != null) {
            super.move(x, y);
            changeAP(-((int) Math.ceil(movementCost[x][y])));

            notifyMovement(x, y);

            //Since a hero moved, update its local chest
            HeroPopUpController.getHeroPopUpController().updateLocalChest();

            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the movement cost array of the hero
     *
     * @param newMovementCost the new movement cost array
     */
    public void setMovementCost(Float[][] newMovementCost) {
        movementCost = newMovementCost;
    }

    /**
     * Gets the movement cost array of the hero
     *
     * @return the current movement cost array
     */
    public Float[][] getMovementCost() {
        return movementCost;
    }
    
    /**
     * Sets the movement path array of the hero
     *
     * @param newMovementPath the new movement cost array
     */
    public void setMovementPath(Integer[][] newMovementPath) {
        movementPath = newMovementPath;
    }

    /**
     * Gets the movement path array of the hero
     *
     * @return the current movement path array
     */
    public Integer[][] getMovementPath() {
        return movementPath;
    }
    
    /**
     * Returns the most efficient path to the given location
     * (null if there is no path)
     *
     * @return array of the path to the given point
     */
    public List<int[]> getSelectedPath(int x, int y) {
    	ArrayList<int[]> selectedPath = new ArrayList<>();
    	if (movementPath[x][y] != null) {
	    	calculateSelectedPath(x, y, selectedPath);
    	}
    	return selectedPath;
    }
    
    /**
     * Returns the most efficient path to the given location
     *
     * @return array of the path to the given point
     */
    private void calculateSelectedPath(int x, int y, ArrayList<int[]> selectedPath) {
    	Integer direction = movementPath[x][y];
    	if (direction != null) {
	    	switch (direction) {
	    		case 0:
	    			calculateSelectedPath(x - 1, y, selectedPath);
	    			break;
	    		case 1:
	    			calculateSelectedPath(x + 1, y, selectedPath);
	    			break;
	    		case 2:
	    			calculateSelectedPath(x, y - 1, selectedPath);
	    			break;
	    		case 3:
	    			calculateSelectedPath(x, y + 1, selectedPath);
	    			break;
	    		default:
	    			break;
	    	}
    	}
    	int[] tile = {x, y};
    	selectedPath.add(tile);
    }

    /**
     * Checks that the hero has enough action points to use an ability, and if so attempts to use it
     *
     * @param i the number of the ability to be used
     * @param x The x coordinate of the selection
     * @param y The y coordinate of the selection
     */
    public boolean useAbility(int i, int x, int y) {
        if (i >= 0 && i < abilities.size() &&
                (actionPoints - abilities.get(i).getCostAP() >= 0) &&
                abilities.get(i).useOnPoint(this, x, y, 0)) {
            changeAP(-abilities.get(i).getCostAP());
            Entity target = GameLoop.getCurrentGameManager().getMap().getMovableEntity(x, y);
            if (target instanceof Targetable) {
                activatePassives((Targetable) target, abilities.get(i));
            }
            return true;
        }
        return false;
    }

    /**
     * returns a HashMap of a heroes' stats stored with HeroStat enum variables as keys
     *
     * @return HashMap(HeroStat, Double), HeroStat is key to the value for the stat
     */
    public Map<HeroStat, Double> getStats() {
        HashMap<HeroStat, Double> heroStats = new HashMap<>();
        heroStats.put(HeroStat.SPEED, speed);
        heroStats.put(HeroStat.DAMAGE, damage);
        heroStats.put(HeroStat.HEALTH, health);
        heroStats.put(HeroStat.BASE_AP, (double) baseAP);
        heroStats.put(HeroStat.RECHARGE_AP, (double) rechargeAP);
        heroStats.put(HeroStat.ACTION_POINTS, (double) actionPoints);
        heroStats.put(HeroStat.ARMOUR, armour);
        heroStats.put(HeroStat.LEVEL, (double) level);
        heroStats.put(HeroStat.XP, (double) xp);
        heroStats.put(HeroStat.BASE_HEALTH, baseHealth);
        heroStats.put(HeroStat.VISIBILITY_RANGE, (double) visibilityRange);
        return heroStats;
    }

    /**
     * Sets the heroes stats from the heroDataClass
     *
     * @param heroType      the type of the hero
     * @param heroDataClass the data class containing the stats of the hero
     */
    public void setStats(HeroType heroType, HeroDataClass heroDataClass) {
        this.heroType = heroType;
    	this.speed = heroDataClass.getBaseSpeed();
        this.health = heroDataClass.getBaseHealth();
        this.baseHealth = heroDataClass.getBaseHealth();
        this.baseAP = heroDataClass.getBaseAP();
        this.rechargeAP = heroDataClass.getBaseRechargeAP();
        this.actionPoints = heroDataClass.getBaseAP();
        this.armour = heroDataClass.getBaseArmour();
        this.graphic = heroDataClass.getHeroGraphic();
        this.visibilityRange = heroDataClass.getBaseVisibilityRange();
        this.abilities = new ArrayList<>();
        this.upgraded = new ArrayList<>();
    }
    
    /**
     * Change the heros stats from StatisticModifier
     *
     * @param stat      the stat that is going to me modified
     * @param value the data class containing the stats of the hero
     */
    public void changeStat(HeroStat stat , double value) {
    	double rawValue = value;
    	
    	if(rawValue < 0 && stat == HeroStat.LEVEL)
    		rawValue = 1; //Level should not be lower than 1
    	else if(rawValue < 0)
    		rawValue = 0; //Other stats should not be negative
    	
    	switch(stat){
		case ACTION_POINTS:
			this.actionPoints = (int) rawValue;
			break;
		case ARMOUR:
			this.armour = rawValue;
			break;
		case BASE_AP:
			this.baseAP = (int) rawValue;
			break;
		case BASE_HEALTH:
			this.baseHealth = rawValue;
			break;
		case DAMAGE:
			this.damage = rawValue;
			break;
		case HEALTH:
			this.health = rawValue;
			break;
		case LEVEL:
			this.level = (int) rawValue;
			break;
		case RECHARGE_AP:
			this.rechargeAP = (int) rawValue;
			break;
		case SPEED:
			this.speed = value;
			break;
		case VISIBILITY_RANGE:
			this.visibilityRange = (int) rawValue;
			break;
		case XP:
			this.xp = (int) rawValue;
			break;
		default:
			throw new UnsupportedOperationException();
    	}
    }

    /**
     * Activates all passives on the hero
     *
     * @param target the target of the passives
     * @param ability the ability tied to the passives
     */
    public void activatePassives(Targetable target, AbstractAbility ability) {
        for (AbstractPassive passive : passives) {
            passive.activateOnAbilityUse(target, ability);
        }
    }

    /**
     * Attempt to change AP of a hero, hero cannot have more AP than baseAP
     *
     * @param change the change in AP of the hero, can be positive or negative
     */
    public void changeAP(int change) {
        // If the change in AP is so large that the new AP would overflow (which happens with the beta-tester ibis) cap
        // the AP at the maximum integer value.
        if ((long) actionPoints + (long) change > Integer.MAX_VALUE) {
            actionPoints = Integer.MAX_VALUE;
            return;
        }

        if (actionPoints + change < 0) {
            actionPoints = 0;
        } else if (actionPoints + change <= baseAP) {
            actionPoints += change;
        } else {
            actionPoints = baseAP;
        }
        notifyActionPointChange();
    }
    
    /**
     * Attempt to change base AP of a hero
     *
     * @param change the base AP of the hero
     */
    public void changeBaseAP(int change) {
        // If the change in AP is so large that the new AP would overflow (which happens with the beta-tester ibis) cap
        // the AP at the maximum integer value.
        if ((long) baseAP + (long) change > Integer.MAX_VALUE) {
        	baseAP = Integer.MAX_VALUE;
            return;
        }

        if (baseAP + change < 0) {
        	baseAP = 0;
        } else {
        	baseAP += change;
        }
    }

    /**
     * Notify all the action point listeners that the AP of this hero has changed.
     */
    private void notifyActionPointChange() {
        if (actionPointListenersEnabled) {
            for (ActionPointListener actionPointListener : actionPointListeners) {
                actionPointListener.onActionPointChange(actionPoints, baseAP);
            }
        }
    }

    private void notifyMovement(int x, int y) {
        if (movementListenersEnabled) {
            for (MovementListener movementListener : movementListeners) {
                movementListener.onMovement(x, y);
            }
        }
    }

    /**
     * Gets the action points of the hero
     *
     * @return the hero's action points
     */
    public int getActionPoints() {
        return actionPoints;
    }

    /**
     * Gets the base action points of the hero
     *
     * @return the hero's action points
     */
    public int getBaseActionPoints() {
        return baseAP;
    }

    /**
     * Sets the action points of the hero
     * DO NOT USE THIS NORMALLY, USE CHANGEAP
     *
     * @param actionPoints the new action points value
     */
    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    /**
     * Recharges a hero's AP according to their recharge AP stat
     */
    private void rechargeAP() {
        changeAP(rechargeAP);
    }

    /**
     * Returns the hero's visibility range
     *
     * @return visibilityRange
     */
    public int getVisibilityRange() {
        return visibilityRange;
    }

    /**
     * Sets the visibility range of the hero
     * <p>
     * Odd numbers are preferred. If you use an even number 1 is added to it.
     * ie. 6 is equivalent to 7.
     *
     * @param radius the new visibility radius
     */
    public void setVisibilityRange(int radius) {
        int range = radius;
        if (radius < 1) {
            range = 1;
        } else if (radius % 2 == 0) {
            range = radius + 1;
        }
        this.visibilityRange = range;
    }

    /**
     * Alerts the hero that an ability has been used on it.
     *
     * @param origin  the entity casting the ability
     * @param ability the ability being used
     */
    public void abilityEffect(Entity origin, AbstractAbility ability) {
        for (AbstractPassive passive : this.getPassives()) {
            passive.activateOnAbilityEffect(origin, ability);
        }
        for (AbstractBuff buff : this.getHeroBuffs()) {
        	buff.onAbilityEffect(origin, ability);
        }
    }

    /**
     * Gets the buffs currently on the hero
     *
     * @return a list of buffs that are currently on the hero
     */
    public List<AbstractBuff> getHeroBuffs() {
        return currentBuffs;
    }

    /**
     * Sets the buffs currently on the hero
     *
     * @param buffs a list of buffs to be put on the hero
     */
    public void setHeroBuffs(List<AbstractBuff> buffs) {
        this.currentBuffs = buffs;
    }

    /**
     * Removes a buff from the hero if the hero currently possesses said buff
     *
     * @param buff the buff to be removed
     */
    public void removeBuff(AbstractBuff buff) {
        if (currentBuffs.contains(buff)) {
            HeroStat buffStatAffected = buff.getStatAffected();
            double strength = buff.getBuffStrength();
            if (buff.getStatAffected() == HeroStat.DAMAGE) {
            	changeBuffAffects(1/strength, buffStatAffected);
            } else {
            	changeBuffAffects(-strength, buffStatAffected);
            }
            currentBuffs.remove(buff);
        }
    }

    /**
     * Adds a buff to the hero
     *
     * @param buff the buff to be added
     */
    @Override
    public void addBuff(AbstractBuff buff) {
        if(!currentBuffs.contains(buff)) {
            currentBuffs.add(buff);
            HeroStat buffStatAffected = buff.getStatAffected();
            double strength = buff.getBuffStrength();
            changeBuffAffects(strength, buffStatAffected);
        }
    }

    /**
     * Alters a certain hero stat caused by a buff
     *
     * @param strength the change in stat value
     * @param heroStat the stat to be changed
     */
    public void changeBuffAffects(double strength, HeroStat heroStat) {
        //If anyone has a better way to do this rather than a switch statement, hit up spress11
        switch (heroStat) {
            case ARMOUR:
                this.armour += strength;
                break;
            case DAMAGE:
                for(AbstractAbility ability : abilities) {
                	ability.setDamage(ability.getBaseDamage()*strength);
                }
                break;
            case SPEED:
                this.speed += strength;
                break;
            case RECHARGE_AP:
                this.rechargeAP += strength;
                break;
            case BASE_AP:
                this.baseAP += strength;
                break;
            case VISIBILITY_RANGE:
                this.visibilityRange += strength;
                break;
            default:
                break;
        }
    }

    /**
     * Gets the utility ability of the hero
     *
     * @return the utility ability currently on the hero
     */
    public AbstractAbility getUtilityAbility() {
        return utilityAbility;
    }

    /**
     * Sets the utility ability of the hero
     *
     * @param ability the new utility ability on the hero
     */
    public void setUtilityAbility(AbstractAbility ability) {
        this.utilityAbility = ability;
    }

    /**
     * Gets the weapon ability of the hero
     *
     * @return the weapon ability currently on the hero
     */
    public AbstractAbility getWeaponAbility() {
        return weaponAbility;
    }

    /**
     * Sets the weapon ability of the hero
     *
     * @param ability the new weapon ability on the hero
     */
    public void setWeaponAbility(AbstractAbility ability) {
        this.weaponAbility = ability;
    }

    /**
     * Gets the passives of the hero
     *
     * @return a list of passives on the hero
     */
    public List<AbstractPassive> getPassives() {
        return passives;
    }

    /**
     * Gets the ability associated with the SelectedAbility for this hero
     * 
     * @param ability the SelectedAbility to return
     * @return the ability associated with the SelectedAbility for this hero
     */
    public AbstractAbility getSelectedAbility(AbilitySelected ability) {
    	AbstractAbility returnAbility = null;
	    	if (ability == AbilitySelected.ABILITY1 
                        && abilities.size() > 0) {
	    		returnAbility = abilities.get(0);
	    	}
	    	else if (ability == AbilitySelected.ABILITY2 
                        && abilities.size() > 1) {
	    		returnAbility = abilities.get(1);
	    	}
	    	else if (ability == AbilitySelected.WEAPON) {
	    		return new Slash();
	    		//return weaponAbility;
	    	}
	    	else if (ability == AbilitySelected.UTILITY) {
	    		returnAbility = utilityAbility;
	    	}
			return returnAbility;
    }

    /**
     * Gets the current job of the hero
     *
     * @return the hero's current job
     */
    public HeroType getHeroType() {
        return this.heroType;
    }

    /**
     * Sets the passives of the hero
     *
     * @param passives a list of passives on the hero
     */
    public void setPassives(List<AbstractPassive> passives) {
        this.passives = passives;
    }

    /**
     * Returns the level of the hero
     *
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the current xp of the hero
     *
     * @return xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * Changes the level of the hero by a given amount
     *
     * @param change the amount the level should be changed
     */
    public void changeLevel(int change) {
        if ((long) level + (long) change > Integer.MAX_VALUE) {
            level = MAX_LEVEL;
            return;
        }

        if (level + change < 0) {
            level = 0;
        } else if (level + change < MAX_LEVEL) {
            level += change;
        } else {
            level = MAX_LEVEL;
        }
    }

    /**
     * Changes the xp of the hero by a given amount
     *
     * @param change the amount the xp should be changed
     */
    public void changeXP(int change) {
        if ((long) xp + (long) change > Integer.MAX_VALUE) {
            xp = maxXP;
            for (int i = level + 1; i < MAX_LEVEL; i++) {
                maxXP += 100 * level;
                xp = maxXP;
            }
            level = MAX_LEVEL;
            return;
        }

        if ((long) xp + (long) change < Integer.MIN_VALUE) {
            xp = 0;
            level = 0;
            return;
        }

        if (xp + change < maxXP) {
            xp += change;
        } else {
            changeLevel(1);
            xp = (xp + change) - maxXP;
            maxXP += 100 * level;
            if (xp > maxXP) {
                changeXP(0);
            }
        }
    }

    /**
     * Updates the hero's state when their turn begins.
     * <p>
     * Methods which override this method should call super.onTurn() so that basic hero functions
     * (e.g. AP recharging) are still carried out.
     */
    public void onTurn() {
        enableListeners();
        rechargeAP();
        tickCooldowns();
        tickPassives();
        AbstractBuff.tickBuffs(currentBuffs, this);
    }

    /**
     * Ticks the cooldowns of this hero's abilities
     */
    private void tickCooldowns() {
        ArrayList<AbstractAbility> allAbilities = new ArrayList<>(abilities);
        //heroes dont yet have weapon abilities
        // when this is implemented, make sure to add it
        allAbilities.add(utilityAbility);
        for (AbstractAbility ability : allAbilities) {
            ability.turnTick();
        }
    }
    
    public void onEndTurn() {
    	for (AbstractBuff buff : currentBuffs) {
    		buff.onEndTurn(this);
    	}
    }

    /**
     * Ticks the passives that are affecting this hero
     */
    private void tickPassives() {
        for (AbstractPassive passive : passives) {
            passive.turnTick();
        }
    }

    /**
     * Enable the hero's listener so that when an AP or health change occurs, its listeners are updated.
     * <p>
     * This method is typically called at the start of the hero's turn. For now only the current turn hero fires health
     * and AP change events.
     */
    @Override
    public void enableListeners() {
        super.enableListeners();
        actionPointListenersEnabled = true;
        movementListenersEnabled = true;
        notifyActionPointChange();
    }

    /**
     * Disable the hero's listeners so that none of them receive health or AP change notifications.
     * <p>
     * Note that the listeners are NOT removed - they will receive notifications again when they are reenabled.
     */
    @Override
    public void disableListeners() {
        super.disableListeners();
        actionPointListenersEnabled = false;
    }

    /**
     * Adds an action point listener to the hero
     *
     * @param actionPointListener the listener to be added
     */
    public void addActionPointListener(ActionPointListener actionPointListener) {
        if (!actionPointListeners.contains(actionPointListener)) {
            actionPointListeners.add(actionPointListener);
        }
    }



    /**
     * Removes a given action point listener from the hero.
     *
     * @param actionPointListener the listener to be removed
     */
    public void removeActionPointListener(ActionPointListener actionPointListener) {
        actionPointListeners.remove(actionPointListener);
    }

    /**
     * Adds an action point listener to the hero
     *
     * @param movementListener the listener to be added
     */
    public void addMovementListener(MovementListener movementListener) {
        if (!movementListeners.contains(movementListener)) {
            movementListeners.add(movementListener);
        }
    }

    /**
     * Gets the hero's inventory
     *
     * @return hero inventory
     */
    public HeroInventory getInventory() {
        return this.inventory;
    }
    
    /**
     * Add loot/shop items to inventory
     * @param item the item to add
     * @return boolean	true - added successfully; false - full
     */
    public boolean addToInventory(Item item){
    	return inventory.addItem(item);
    }

    /**
     * Equip Primary Weapon from Inventory
     * 
     * @param itemNo the item's number
     * @return true when success, false when failure
     */
    public boolean equipPrimaryWeapon(int itemNo){
    	Item weapon = inventory.getItemFromSlot(itemNo);
    	if (!(weapon instanceof Weapon)) 
            return false;
    	if(inventory.equipPrimaryWeapon(weapon)){
    		Weapon thisWeapon = (Weapon)weapon;
    		this.setWeaponAbility(thisWeapon.getWeaponAbility());
    		this.changeDamage((double)thisWeapon.getDamage());
    		return true;
    	}
    	return false;
    }
    
    /**
     * Ticks the turn of a hero
     * Should be overrode
     */
    public void turnTick() {
        // Tick tock
    }
    
    /**
     * Takes in the requested upgrade and changes stats accordingly
     * 
     * @param stat - stat to be upgraded
     * 		tier - tier number of upgrade
     * @return true if upgrade is completed
     */
    public boolean statUpgrade(String stat, int tier) {
    	if (!checkUpgrade(stat, tier)) {
    		return false;
    	}
    	if (stat.startsWith("armour")) {
            super.armour++;
            addUpgrade(stat);
            return true;
        }
    	if (stat.startsWith("health")) {
        	super.baseHealth += 10;
        	addUpgrade(stat);
        	return true;
    	}
    	if (stat.startsWith("ap")) {
    		baseAP++;
    		addUpgrade(stat);
    		return true;
    	}
    	return false;
    }
    
    /**
     * Checks if the requested upgrade tier is valid (less than or equal to level,
     * and not already upgraded)
     * 
     * @param stat - stat to be upgraded
     * 		tier - tier number of upgrade
     * @return true if upgrade is possible
     */
    public boolean checkUpgrade(String stat, int tier) {
    	if (tier >= 1 && upgradePoints > 0) {
    		String type = stat.substring(0, stat.indexOf('_'));
    		int count = 0;
    		for (String i: upgraded) {
    			if (i.startsWith(type)) {
    				count++;
    			}
    		}
    		if (count != tier - 1) {
    			return false;
    		}
    	}
        return tier <= level && !upgraded.contains(stat);
    }

    /**
     * Adds an upgrade to the list of upgrades
     *
     * @param stat upgraded stat
     */
    public void addUpgrade(String stat) {
        upgraded.add(stat);
        upgradePoints--;
    }
    
    /**
     * Checks a list of all upgrades possible for a hero, and returns whether they
     * are possible (in a list of booleans). This can be used to determine if
     * the box should be blanked out or not when the upgrade window is opened.
     * 
     * @param upgrades - list of strings of all upgrades
     * @return checked - list of booleans corresponding to valid and invalid upgrades
     */
    public ArrayList<Boolean> checkAllUpgrades(ArrayList<String> upgrades) {
    	ArrayList<Boolean> checked = new ArrayList<Boolean>();
    	int tier = 1;
    	int counter = 0;
    	for (String upgrade:upgrades) {
    		if (checkUpgrade(upgrade, tier)) {
    			checked.add(true);
    		} else {
    			checked.add(false);
    		}
    		if ((counter + 1) % 5 == 0) {
    			tier++;
    		}
    		counter++;
    	}
    	return checked;
    }

    /**
     * Get the name of the hero
     */
    public String getName() {
        return name;
    }

    /**
     * get the currently avaliable upgrade points
     * @return - the number of points used to upgrade stats
     */
    public int getUpgradePoints() {
        return upgradePoints;
    }

    /**
     * sets the upgrade points
     * @param upgradePoints - the number of points used to upgrade stats
     */
    public void setUpgradePoints(int upgradePoints) {
        this.upgradePoints = upgradePoints;
    }
    
    /**
     * Action when Equip item
     * @param item
     * @param inverse
     */
	@Override
	public void itemEquip(Item item, boolean inverse) {
		if(!(item instanceof EquippableItem)){
			return;
		}
		logger.debug("Item "+(inverse?"Unequipped":"Equpped")+": "+item.getName());
		HeroStatisticsController.applyHeroStatistic(this, (EquippableItem) item, inverse);
		updateGraphics();
		SoundPlayer.playEquip();
	}
	
	@Override
	public void updateGraphics(){
		String imageName = textureStringBuilder();
		boolean textureExist = TextureRegister.searchTextureString(imageName);
		
		//Try to search for the available texture if no exist
		String[] imageLevel = imageName.split("_");
		int stringLevel = imageLevel.length - 1;
		while(!textureExist&& stringLevel!=0){
			String subStringBuild = imageLevel[0];
			for(int i=1;i<=stringLevel;i++){
				subStringBuild += "_"+imageLevel[i];
			}
			logger.debug("Texture Search results: "+TextureRegister.searchTextureString(subStringBuild)+" - "+subStringBuild);
			textureExist = TextureRegister.searchTextureString(subStringBuild);
			if(textureExist){
				this.setImageName(subStringBuild);
			}
			stringLevel -= 1;
		}
		
		if(!textureExist){
			this.setImageName(getTypeName());
		}
		logger.debug("Primary: "+this.getInventory().getPrimaryWeapon()+" - Secondary: "+this.getInventory().getSecondaryWeapon());
		logger.debug("ID: "+this.hashCode()+" Image Name: "+this.getImageName());
	}
	
	private String textureStringBuilder(){
		Item armour = inventory.getArmour();
		Item primaryWeapon = inventory.getPrimaryWeapon();
		Item secondaryWeapon = inventory.getSecondaryWeapon();
		Item shield = inventory.getShield();
		logger.debug("Get item results: "+armour+" "+primaryWeapon+" "+secondaryWeapon);
		
		String imageName = "";
		imageName = getTypeName();
		
		if(primaryWeapon != null){
			imageName += getWeaponImageExtension(primaryWeapon.getType());
		}

		if(shield != null){
			imageName += "_ironshield";
		}
		logger.debug("String build Returned: "+imageName);
		return imageName;
	}

    private String getWeaponImageExtension(ItemType itemType) {
        switch(itemType){
            case AXE:
                return "_axe";
            case BOW:
                return "_bow";
            case FIRE_POTION:
                return "_potionred";
            case HAMMER:
                return "_hammer";
            case MACE:
                return "_mace";
            case POTION:
                return "_potionblue";
            case POISON_POTION:
                return "_potiongreen";
            case SWORD:
                return "_sword";
            case WET_POTION:
                return "_potionblue";
            default:
                return "_none";
        }
    }
	
	private String getTypeName(){
		String imageName = "";
		
		String[] imageSeparator = this.getImageName().split("_");
		imageName = (String) Array.get(imageSeparator, 0);
		
		return imageName;
	}

    @Override
    public String getImageName() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.getImageName());

        if (inventory.getPrimaryWeapon() != null) {
            builder.append("_").append(inventory.getPrimaryWeapon().getImage());
        } else {
            builder.append("_none");
        }

        if (inventory.getShield() != null) {
            builder.append("_").append("shield");
        } else {
            builder.append("_none");
        }
        return builder.toString();
    }


    public boolean canWield(Weapon weapon) {

        if (weapon == null) {
            return true;
        }

        ItemType weaponType = weapon.getType();

        switch (heroType) {
            case ARCHER:
                return weaponType == ItemType.BOW;
            case CAVALIER:
                return weaponType == ItemType.SWORD || weaponType == ItemType.LANCE;
            case KNIGHT:
                return weaponType == ItemType.SWORD || weaponType == ItemType.AXE || weaponType == ItemType.HAMMER;
            case PRIEST:
                return true;
            case ROGUE:
                return weaponType == ItemType.DAGGER;
            case WARLOCK:
                return weaponType == ItemType.STAFF;
            default:
                return false;
        }
    }
}
