package uq.deco2800.duxcom.campaign.levels;

import uq.deco2800.duxcom.campaign.Difficulty;
import uq.deco2800.duxcom.maps.AbstractGameMap;


/**
 * Created by Elliot on 10/14/2016.
 */
public class LevelOne extends AbstractLevel {

    /**
     * Construct a level object.
     * @param map - Map included in level
     * @param difficulty - Difficulty of level
     * @param levelName - Name of level
     * @param storyText - Story and context of level
     */
    public LevelOne(AbstractGameMap map, Difficulty difficulty, String levelName, String storyText) {
        super(map, difficulty, levelName, storyText);
    }
}
