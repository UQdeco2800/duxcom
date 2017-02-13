package uq.deco2800.duxcom.dataregisters;

import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyAttitude;
import uq.deco2800.duxcom.entities.enemies.artificialincompetence.EnemyMode;

/**
 * Stores the data that all instances of a type of enemy must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author liamdm
 * @author Alex McLean
 */
public class EnemyDataClass implements AbstractDataClass {
    private String enemyName;
    private String enemyGraphic;
    private EnemyMode mode;
    private EnemyAttitude attitude;
    private double baseHealth;
    private double baseDamage;
    private double baseCritChance;
    private double baseArmour;
    private double baseMagicResist;
    private double baseAP;
    private double baseSpeed;
    private int moveRange;
    private int baseAttackRange;

    /**
     * Class constructor to set all the required parameters
     *
     * @param enemyName       enemyName
     * @param enemyGraphic    the graphic of the enemy
     * @param mode            mode
     * @param attitude        attitude
     * @param baseHealth      hit points
     * @param baseDamage      base baseDamage
     * @param baseCritChance  base crit chance
     * @param baseArmour      base baseArmour
     * @param baseMagicResist base magic resistance
     * @param baseAttackRange base baseAttackRange
     * @param moveRange       base moveRange
     * @param baseSpeed       base baseSpeed
     */
    public EnemyDataClass(String enemyName, String enemyGraphic, EnemyMode mode, EnemyAttitude attitude,
                          double baseHealth, double baseDamage, double baseCritChance, double baseArmour, double baseMagicResist,
                          double baseAP, double baseSpeed, int moveRange, int baseAttackRange) {

        this.enemyName = enemyName;
        this.enemyGraphic = enemyGraphic;
        this.mode = mode;
        this.attitude = attitude;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.baseCritChance = baseCritChance;
        this.baseArmour = baseArmour;
        this.baseMagicResist = baseMagicResist;
        this.baseAP = baseAP;
        this.baseSpeed = baseSpeed;
        this.moveRange = moveRange;
        this.baseAttackRange = baseAttackRange;

    }

    /**
     * Gets the enemyName of the data class
     *
     * @return The data class' enemyName
     */
    public String getEnemyName() {
        return enemyName;
    }

    /**
     * Sets the data class' enemyName
     *
     * @param enemyName the new enemyName
     */
    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    /**
     * Gets the enemyGraphic of the data class
     *
     * @return The data class' enemyGraphic
     */
    public String getEnemyGraphic() {
        return enemyGraphic;
    }

    /**
     * Sets the data class' enemyGraphic
     *
     * @param enemyGraphic the new graphic
     */
    public void setEnemyGraphic(String enemyGraphic) {
        this.enemyGraphic = enemyGraphic;
    }

    /**
     * Gets the EnemyMode of the data class
     *
     * @return The data class' EnemyMode
     */
    public EnemyMode getMode() {
        return mode;
    }

    /**
     * Sets the data class' EnemyMode
     *
     * @param mode the new enemyMode
     */
    public void setMode(EnemyMode mode) {
        this.mode = mode;
    }

    /**
     * Gets the EnemyAttitude of the data class
     *
     * @return The data class' EnemyAttitude
     */
    public EnemyAttitude getAttitude() {
        return attitude;
    }

    /**
     * Sets the data class' EnemyAttitude
     *
     * @param attitude the new EnemyAttitude
     */
    public void setAttitude(EnemyAttitude attitude) {
        this.attitude = attitude;
    }

    /**
     * Gets the baseHealth of the data class
     *
     * @return The data class' baseHealth
     */
    public double getBaseHealth() {
        return baseHealth;
    }

    /**
     * Sets the data class' baseHealth
     *
     * @param baseHealth the new baseHealth
     */
    public void setBaseHealth(double baseHealth) {
        this.baseHealth = baseHealth;
    }

    /**
     * Gets the baseDamage of the data class
     *
     * @return The data class' baseDamage
     */
    public double getBaseDamage() {
        return baseDamage;
    }

    /**
     * Sets the data class' baseDamage
     *
     * @param baseDamage the new baseDamage
     */
    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    /**
     * Gets the crit chance of the data class
     *
     * @return The data class' crit chance
     */
    public double getBaseCritChance() {
        return baseCritChance;
    }

    /**
     * Sets the data class' crit chance
     *
     * @param baseCritChance the new crit chance
     */
    public void setBaseCritChance(double baseCritChance) {
        this.baseCritChance = baseCritChance;
    }

    /**
     * Gets the baseArmour of the data class
     *
     * @return The data class' baseArmour
     */
    public double getBaseArmour() {
        return baseArmour;
    }

    /**
     * Sets the data class' baseArmour
     *
     * @param baseArmour the new baseArmour
     */
    public void setBaseArmour(double baseArmour) {
        this.baseArmour = baseArmour;
    }

    /**
     * Gets the magic resistance of the data class
     *
     * @return The data class' magic resistance
     */
    public double getBaseMagicResist() {
        return baseMagicResist;
    }

    /**
     * Sets the data class' magic resistance
     *
     * @param baseMagicResist the new magic resistance
     */
    public void setBaseMagicResist(double baseMagicResist) {
        this.baseMagicResist = baseMagicResist;
    }

    /**
     * Gets the action points of the data class
     *
     * @return The data class' action points
     */
    public double getBaseAP() {
        return baseAP;
    }

    /**
     * Sets the data class' action points
     *
     * @param baseAP the new action points
     */
    public void setBaseAP(double baseAP) {
        this.baseAP = baseAP;
    }

    /**
     * Gets the baseSpeed of the data class
     *
     * @return The data class' baseSpeed
     */
    public double getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * Sets the data class' baseSpeed
     *
     * @param baseSpeed the new baseSpeed
     */
    public void setBaseSpeed(double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    /**
     * Gets the movement range of the data class
     *
     * @return The data class' movement range
     */
    public int getMoveRange() {
        return moveRange;
    }

    /**
     * Sets the data class' movement range
     *
     * @param moveRange the new movement range
     */
    public void setMoveRange(int moveRange) {
        this.moveRange = moveRange;
    }

    /**
     * Gets the attack range of the data class
     *
     * @return The data class' attacke range
     */
    public int getBaseAttackRange() {
        return baseAttackRange;
    }

    /**
     * Sets the data class' attack range
     *
     * @param baseAttackRange the new attack range
     */
    public void setBaseAttackRange(int baseAttackRange) {
        this.baseAttackRange = baseAttackRange;
    }


}
