package uq.deco2800.duxcom.dataregisters;

/**
 * Stores the data that all instances of a type of hero must share.
 *
 * Also provides public getter and setter methods for all data.
 *
 * @author lars06
 */
public class HeroDataClass implements AbstractDataClass {
    private String heroName;
    private String heroGraphic;
    private int baseHealth;
    private double baseArmour;
    private double baseSpeed;
    private int baseVisibilityRange;
    private int baseAP;
    private int baseRechargeAP;

    /**
     * Class constructor to set all the required parameters
     *
     * @param heroName            name
     * @param baseHealth          base health
     * @param baseArmour          base bodyArmour
     * @param baseSpeed           base speed
     * @param baseVisibilityRange base range of visibility
     * @param baseAP              base action points
     * @param baseRechargeAP      base amount of rechargeable action points
     */
    public HeroDataClass(String heroName, String heroGraphic, int baseHealth, double baseArmour, double baseSpeed, 
    		int baseVisibilityRange, int baseAP, int baseRechargeAP) {

        this.heroGraphic = heroGraphic;
        this.heroName = heroName;
        this.baseHealth = baseHealth;
        this.baseArmour = baseArmour;
        this.baseSpeed = baseSpeed;
        this.baseVisibilityRange = baseVisibilityRange;
        this.baseAP = baseAP;
        this.baseRechargeAP = baseRechargeAP;
    }

    /**
     * Gets the heroName of the data class
     *
     * @return The data class' heroName
     */
    public String getHeroName() {
        return heroName;
    }

    /**
     * Sets the data class' heroName
     *
     * @param heroName the new heroName
     */
    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    /**
     * Gets the base health of the data class
     *
     * @return The data class' base health
     */
    public int getBaseHealth() {
        return baseHealth;
    }

    /**
     * Sets the data class' base health
     */
    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
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
     * Gets the visibility range of the data class
     *
     * @return The data class' visibility range
     */
    public int getBaseVisibilityRange() {
        return baseVisibilityRange;
    }

    /**
     * Sets the visibility range of the hero
     *
     * Odd numbers are preferred. If you use an even number 1 is added to it.
     * ie. 6 is equivalent to 7.
     *
     * @param radius the new visibility radius
     */
    public void setBaseVisibilityRange(int radius) {
        int range = radius;
        if (radius < 1) {
            range = 1;
        } else if (radius % 2 == 0) {
            range = radius + 1;
        }
        this.baseVisibilityRange = range;
    }

    /**
     * Gets the base AP of the data class
     *
     * @return The data class' base AP
     */
    public int getBaseAP() {
        return baseAP;
    }

    /**
     * Sets the data class' base AP
     */
    public void setBaseAP(int baseAP) {
        this.baseAP = baseAP;
    }

    /**
     * Gets the recharge AP of the data class
     *
     * @return The data class' recharge AP
     */
    public int getBaseRechargeAP() {
        return baseRechargeAP;
    }

    /**
     * Sets the data class' recharge AP
     */
    public void setBaseRechargeAP(int baseRechargeAP) {
        this.baseRechargeAP = baseRechargeAP;
    }

    /**
     * Gets the graphic of the data class
     *
     * @return The data class' graphic
     */
    public String getHeroGraphic() {
        return heroGraphic;
    }

    /**
     * Sets the data class' graphic
     *
     * @param heroGraphic the new graphic
     */
    public void setHeroGraphic(String heroGraphic) {
        this.heroGraphic = heroGraphic;
    }

}
