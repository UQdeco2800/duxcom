package uq.deco2800.duxcom.campaign.levels;

import uq.deco2800.duxcom.campaign.Difficulty;
import uq.deco2800.duxcom.entities.enemies.AbstractEnemy;
import uq.deco2800.duxcom.entities.heros.AbstractHero;
import uq.deco2800.duxcom.maps.AbstractGameMap;

import java.util.ArrayList;

/**
 * Created by Elliot on 10/14/2016.
 */
public abstract class AbstractLevel {
    /**
     * Class variables - enemies, heroes, objectives, difficulty and map.
     */
    protected AbstractGameMap map;
    protected Difficulty difficulty;
    protected ArrayList<AbstractEnemy> enemies;
    protected ArrayList<AbstractHero> heroes;
    protected String levelName;
    private boolean completed;
    private String storyText;

    /**
     * @param map - Map associated to this level
     * @param difficulty - Difficulty associated to this level
     * @param levelName - Level Name
     * @param storyText - Story text (context of level 214 characters)
     */
    public AbstractLevel(AbstractGameMap map, Difficulty difficulty, String levelName, String storyText) {
        this.completed = false;
        this.levelName = levelName;
        this.map = map;
        this.difficulty = difficulty;
        this.storyText = storyText;
    }


    /**
     * Flag this level as completed by the user.
     */
    public void completeLevel() {
        this.completed = true;
    }

    /**
     * Check if the level has been completed in regards to objectives.
     * @return True if the level objectives have been completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * @return - the level name.
     */
    public String getLevelName() {
        return this.levelName;
    }

    /**
     *  Set the level name of level.
     * @param levelName to change this.level name to.
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Get the story context of level.
     * @return story context of level.
     */
    public String getStoryText() {
        return this.storyText;
    }

    /**
     * Set the story text of this level.
     * @param storyText to change this.storyText to.
     */
    public void setStoryText(String storyText) {this.storyText = storyText;}

}
