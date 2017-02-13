package uq.deco2800.duxcom.achievements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static uq.deco2800.duxcom.achievements.AchievementType.*;

/**
 * Created by Daniel Gormly on 14/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Achievement {

    @JsonProperty
    // The ID of an achievement. Generated via UUID.randomUUID().toString().
    // Should only be generated when getting a new Achievement from the AchievementRegister.
    private String id = null;

    @JsonProperty
    // Title of the Achievement. Must be unique system wide.
    private String name = null;

    @JsonProperty
    // An achievement's description.
    private String description = null;

    @JsonProperty
    // An achievement's type
    private String type = null;

    @JsonProperty
    // An achievement's score
    private int score = 0;

    private AchievementType typeEnum;
    /**
     * Constructor for a Achievement class. Used for Jackson deserialising to object.
     * Will automatically generate a new UUID for the achievement's ID.
     */
    public Achievement() {
        // Constructor for Jackson Serialising
    }
    /**
     * Creates a new user instance auto filling details.
     *
     * @param name
     *            The achievement's requested title. Must not be empty.
     * @param description
     *            The achievement's description
     * @param type
     *            The achievement's type
     * @param score
     *            The achievement's completion score.
     */
    public Achievement(String id, String name, String description, String type, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
        this.type = type;
        selectEnum(type);
    }

    public Achievement(String id, String name, String description, AchievementType type, int score) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.score = score;
        this.typeEnum = type;
        selectType(type);
    }

    /**
     * Converts Enum type to String. Used for parsing to file.
     *
     * @param type
     */
    private void selectType(AchievementType type) {

        switch (type) {
            case KILL:
                this.type = "KILL";
                break;
            case SCORE:
                this.type = "SCORE";
                break;
            case TIME:
                this.type = "TIME";
                break;
            case DEATH:
                this.type = "DEATH";
                break;
            case MIS:
                this.type = "MIS";
            default:
                break;
        }
    }


    /**
     * Converts String to Enum.
     *
     * @param type
     */
    private void selectEnum(String type) {

        switch (type) {
            case "KILL":
                this.typeEnum = KILL;
                break;
            case "SCORE":
                this.typeEnum = SCORE;
                break;
            case "TIME":
                this.typeEnum = TIME;
                break;
            case "DEATH":
                this.typeEnum = DEATH;
                break;
            case "MIS":
                this.typeEnum = MIS;
            default:
                break;
        }
    }

    /**
     * Returns the Achievement's Id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the Achievment's Id.
     *
     * @param id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the unique achievement name.
     *
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the achievements name. This must be unique otherwise it will fail.
     *
     * @param name of the achievement to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the achievement.
     *
     * @return description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the achievement.
     *
     * @param desciption the description to set
     */
    public void setDescription(String desciption) {
        this.description = desciption;
    }

    /**
     * Gets the Achievement type of the achievement.
     *
     * @return type.
     */
    public String getType() {
        return this.type;
    }

    public AchievementType getTypeEnum() {
        return this.typeEnum;
    }

    /**
     * Sets the achievement type.
     *
     * @param type the type to set
     */
    public void setTypeEnum(AchievementType type) {
        selectType(type);
    }

    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets the score required to complete the achievement.
     *
     * @return the score as an int
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the score required to complete the achievement.
     *
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Maps an Achievement to its string equivalent.
     *
     * @return
     */
    public String toString() {
        return id + "^" + name + "^" + description + "^" + type + "^" + score;
    }

    /**
     * Checks if both achievements are equivalent.
     *
     * @param o the achievement object
     * @return true if they are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Achievement)) {
            return false;
        }

        Achievement that = (Achievement) o;

        if (getScore() != that.getScore()) {
            return false;
        }

        if (!getId().equals(that.getId())) {
            return false;
        }

        if (!getName().equals(that.getName())) {
            return false;
        }

        if (!description.equals(that.description))
        {
            return false;
        }
        return getType().equals(that.getType());

    }

    /**
     * Generate a hashcode for the achievement.
     *
     * @return int, unique code associated with the achievement.
     */
    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getScore();
        return result;
    }
}
