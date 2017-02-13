package uq.deco2800.duxcom.abilities;

import uq.deco2800.duxcom.GameLoop;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.common.DamageType;
import uq.deco2800.duxcom.dataregisters.AbilityDataClass;
import uq.deco2800.duxcom.entities.AbstractCharacter;
import uq.deco2800.duxcom.entities.Entity;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.graphics.particles.IconType;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;
import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.util.TurnTickable;

import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

/**
 * Created by woody on 26-Jul-16.
 */
public abstract class AbstractAbility implements TurnTickable {
	
	private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractAbility.class);

    private AbilityType abilityType;
    private String name;
    private String description;

    //positive value - will be subtracted from ap
    private int costAP;
    private int range;
    private int aoeRange;
    private int cooldownTurns;
    private int cooldownTurnsLeft;
    private double temporaryDmgMultiplier = 1;

    //negative for damage, positive for heal
    private double damage;
    private DamageType damageType;

    // IconType enums
    private IconType castIcon;
    private IconType hitIcon;
    private IconType splatIcon;

    // Ability logic booleans
    private boolean canUseOnFriend = false;
    private boolean canUseOnFoe = false;
    private boolean canUseOnPoint = false;


    /**
     * Transfers specific ability stats into Abstract super class;
     * to be called from individual character construction to reduce
     * duplicate code
     *
     * @param abilityDataClass The data class of the ability
     */
    public void setStats(AbilityDataClass abilityDataClass) {
        this.abilityType = abilityDataClass.getAbilityType();
        this.name = abilityDataClass.getName();
        this.description = abilityDataClass.getDescription();
        this.costAP = abilityDataClass.getCostAP();
        this.cooldownTurns = abilityDataClass.getCooldownTurns();
        this.range = abilityDataClass.getRange();
        this.aoeRange = abilityDataClass.getAoeRange();
        this.damage = abilityDataClass.getDamage();
        this.damageType = abilityDataClass.getDamageType();
        this.castIcon = abilityDataClass.getCastIcon();
        this.hitIcon = abilityDataClass.getHitIcon();
        this.splatIcon = abilityDataClass.getSplatIcon();
        this.canUseOnFriend = abilityDataClass.canUseOnFriend();
        this.canUseOnFoe = abilityDataClass.canUseOnFoe();
        this.canUseOnPoint = abilityDataClass.canUseOnPoint();
    }

    /**
     * Gets the type of the ability
     *
     * @return the type
     */
    public AbilityType getAbilityType() {
        return abilityType;
    }
    
    /**
     * Gets the aoe radius
     *
     * @return the aoeRange
     */
    public int getAoeRange() {
        return aoeRange;
    }

    /**
     * Gets the description of the ability
     *
     * @return the ability description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the base damage of the ability
     * @return the ability base damage
     */
    public double getBaseDamage() {
    	return damage;
    }
    
    /**
     * Gets the damage of the ability
     * @return the ability base damage
     */
    public double getDamage() {
    	return damage*temporaryDmgMultiplier;
    }
    
    /**
     * Sets the damage of the ability
     * @param damage the new base damage
     */
    public void setDamage(double damage) {
    	this.damage = damage;
    }

    /**
     * Gets the damage type of the ability
     *
     * @return the ability damage type
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Gets the range of the ability
     *
     * @return the ability range
     */
    public int getRange() {
        return range;
    }
    
    /**
     * Gets the AP cost of the ability
     *
     * @return the ability AP cost
     */
    public int getCostAP() {
        return costAP;
    }

    /**
     * Returns whether or not the ability is a combat ability
     *
     * @param offensive states whether or not the ability is to be considered offensive
     * @return true iff ability is a combat ability, else false
     */
    public boolean isCombat(boolean offensive) {
        return true;
    }

    /**
     * Constructor for the abstract class of abilities
     */
    public AbstractAbility() {
        // Constructor
    }

    /**
     * Checks if the ability is on cooldown
     *
     * @return true iff on cooldown
     */
    public boolean onCooldown() {
        return cooldownTurnsLeft > 0;
    }
    
    /**
     * Decrements cooldown of this ability.
     */
    public void tickCooldown(){
    	cooldownTurnsLeft--;
    }
    
    /**  
     * Multiplies current damage of ability, keep in mind values < 1 will decrease damage 
     * 
     * @param multiplier * The number to be multiplied by 
     */
    public void addTemporaryDamageMultiplier(double multiplier) { 
    	temporaryDmgMultiplier = multiplier; 
    } 
    
    /**
     * Returns the value of the current damage multiplier for the ability
     * @return the temporary damage multiplier
     */
    public double getTemporaryDamageMultiplier() {
    	return temporaryDmgMultiplier;
    }
    
    /**
     * Resets the Temporary Damage Multiplier of this ability to 1
     */
    public void resetTemporaryDamageMultiplier() { 
    	temporaryDmgMultiplier = 1; 
    }
    
    /**
     * Checks if the target is within range of an ability
     *
     * @return true iff in range
     */
    public boolean inRange(int originX, int originY, int targetX, int targetY) {
		double distanceToTarget = Math.round(distance(originX, originY, targetX, targetY));
        return distanceToTarget <= range;
    }

    /**
     * Called when the ability is used on a Targetable Entity. This will decide if the
	 * target is in range, and return useOnFriend or useOnFoe based on the
	 * implementing class of the Targetable interface.
	 * 
	 * @param origin
	 *		The entity using the ability.
	 * @param target
	 *		The target entity.
	 * @return	
	 *		True iff ability was successfully used.
     */
    public boolean useOnTarget(AbstractCharacter origin, Targetable target){
		if (!inRange(origin.getX(), origin.getY(), target.getX(), target.getY()) 
					|| !target.target(this) || this.onCooldown()) {
			return false;
		}
		if (origin instanceof AbstractHero) {
			((AbstractHero) origin).activatePassives(target, this);
		}
		this.cooldownTurnsLeft = cooldownTurns;
		if (origin.getClass() != target.getClass()) {
			return useOnFoe(origin, target);
		} else {
			return useOnFriend(origin, target);
		}
    }
	
	/**
     * Called when the ability is targeted at a friend
	 * 
	 * @return	
	 *		True iff ability can be used on a friend.
     */
	protected boolean useOnFriend(AbstractCharacter origin, Targetable target) {
		target.abilityEffect(origin, this);
		target.changeHealth(damage*temporaryDmgMultiplier, this.damageType);
		resetTemporaryDamageMultiplier();
		cooldownTurnsLeft = cooldownTurns;
		return true;
	}

    /**
     * Called when the ability is targeted at a foe
     *
     * @return True iff ability can be used on a foe.
     */
	protected boolean useOnFoe(AbstractCharacter origin, Targetable target) {
		target.abilityEffect(origin, this);
		target.changeHealth(damage*temporaryDmgMultiplier, this.damageType);
		resetTemporaryDamageMultiplier();
		cooldownTurnsLeft = cooldownTurns;
		return true;
	}

    /**
     * Called when the ability is targeted at a point on the map
     *
     * @param origin    hero using ability
     * @param x         x location on map
     * @param y         y location on map
     * @param elevation elevation of point on map
     */
    public boolean useOnPoint(AbstractCharacter origin, int x, int y, int elevation) {
    	logger.info("Attempting to use ability: "+this.getName());
    	if (!inRange(origin.getX(), origin.getY(), x, y) || this.onCooldown()) {
    		return false;
    	}
    	GameManager game = GameLoop.getCurrentGameManager();
    	MapAssembly map = game.getMap();
    	Tile tile = map.getTile(x, y);

        // Apply damage to initial Live Tile if applicable
        if (tile.hasLiveTile() && tile.getLiveTile() instanceof Targetable) {
            ((Targetable) tile.getLiveTile()).changeHealth(1.0, damageType);
        }

    	//if its an aoe ability do stuff with aoe
    	if (aoeRange > 0) {
    		logger.info("Using AOE ability");
    		return areaOfEffect(x, y, aoeRange, map);
    	}
    	if (tile.isOccupied()) {
    		logger.info("Using normal ability");
    		//if tile is occupied and target is Targetable, use ability on target
    		Entity entity = tile.getMovableEntity();
    		if (entity instanceof AbstractCharacter) {
    			return useOnTarget(origin, (AbstractCharacter) entity);
    		}
    	}
		return false;
    }
    
    /**
     * Called when an area of effect ability is targeted at a point on the map
     * 
     * @param x - x coordinate of point to target
     * 		y - y coordinate of point to target
     * 		xDistance - width of the area of effect ability in the x direction
     * 		yDistance - width of the area of effect ability in the y direction
     */
    public boolean areaOfEffect(int x, int y, int radius, MapAssembly map) {
    	this.cooldownTurnsLeft = cooldownTurns;
    	List<Tile> tiles = new ArrayList<Tile>();
    	tiles = map.getTilesAroundPoint(x, y, radius);
		for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).hasLiveTile() && tiles.get(i).getLiveTile() instanceof Targetable) {
                ((Targetable) tiles.get(i).getLiveTile()).changeHealth(1.0, damageType);
            }
			if (!tiles.get(i).isOccupied()) {
	    		continue;
	    	}
			Entity entity = tiles.get(i).getMovableEntity();
			if (entity instanceof AbstractHero) {
				((AbstractHero) entity).changeHealth(damage*temporaryDmgMultiplier);
			}
			else if (entity instanceof AbstractEnemy) {
				((AbstractEnemy) entity).changeHealth(damage*temporaryDmgMultiplier);
			}

		}
    	resetTemporaryDamageMultiplier();
    	return true;
    }

    /**
     * Gets the name of the ability
     *
     * @return the ability name
     */
    public String getName() {
        return name;
    }

    /**
     * Generates and returns a hashcode for the ability
     *
     * @return the ability hashcode
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Checks equality with this and another object
     *
     * @param o the object to be compared to
     * @return true iff object and this are equal, else false
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof AbstractAbility
                && ((AbstractAbility) o).getName().equals(this.name);
    }

    /**
     * Implements the turn tickable method by reducing the cooldown if non-zero.
     */
    @Override
    public void turnTick() {
        if (cooldownTurnsLeft > 0) {
            tickCooldown();
        }
    }

    /**
     * Takes in the requested upgrade and changes stats accordingly
     *
     * @param stat - stat to be upgraded
     * 		tier - tier number of upgrade
     * @return true if upgrade is completed
     */
    public boolean abilityUpgrade(String stat, int tier, AbstractHero hero) {
    	if (!hero.checkUpgrade(stat, tier)) {
    		return false;
    	}
    	if (stat.startsWith("damage")) {
    		damage = Math.round(damage * 1.2);
    		hero.addUpgrade(stat);
    		return true;
    	} else if (stat.startsWith("range")) {
    		range++;
    		hero.addUpgrade(stat);
    		return true;
    	}
    	return false;
    }

    /**
     * Return the Cast IconType of the ability
     *
     * @return IconType
     */
    public IconType getCastIcon() {
        return castIcon;
    }

    /**
     * Return the Hit IconType of the ability
     *
     * @return IconType
     */
    public IconType getHitIcon() {
        return hitIcon;
    }

    /**
     * Return the Splat IconType of the ability
     *
     * @return IconType
     */
    public IconType getSplatIcon() {
        return splatIcon;
    }
    
    public int getCooldownTurns() {
    	return cooldownTurns;
    }
    
    public void setCooldownTurnsLeft(int turns) {
        cooldownTurnsLeft = turns;
    }

    /**
     * Return the canUseOnFriend status of the ability
     *
     * @return true iff ability can be used on friend
     */
    public boolean canUseOnFriend() {
        return canUseOnFriend;
    }

    /**
     * Set the canUseOnFriend status of the ability
     *
     * @param canUseOnFriend new canUseOnFriend status
     */
    public void setCanUseOnFriend(boolean canUseOnFriend) {
        this.canUseOnFriend = canUseOnFriend;
    }

    /**
     * Return the canUseOnFoe status of the ability
     *
     * @return true iff ability can be used on foe
     */
    public boolean canUseOnFoe() {
        return canUseOnFoe;
    }

    /**
     * Set the canUseOnFoe status of the ability
     *
     * @param canUseOnFoe new canUseOnFoe status
     */
    public void setCanUseOnFoe(boolean canUseOnFoe) {
        this.canUseOnFoe = canUseOnFoe;
    }

    /**
     * Return the canUseOnPoint status of the ability
     *
     * @return true iff ability can be used on a point
     */
    public boolean canUseOnPoint() {
        return canUseOnPoint;
    }

    /**
     * Set the canUseOnPoint status of the ability
     *
     * @param canUseOnPoint new canUseOnPoint status
     */
    public void setCanUseOnPoint(boolean canUseOnPoint) {
        this.canUseOnPoint = canUseOnPoint;
    }
}
