package uq.deco2800.duxcom.campaign;

import uq.deco2800.duxcom.campaign.levels.AbstractLevel;

import java.util.ArrayList;

/**
 * Created by Elliot on 10/14/2016.
 */
public class Campaign {
    private ArrayList<AbstractLevel> levels;
    private int currentLevel;
    private String campaignName;
    private String campaignDescription;

    /**
     * Construct Campaign with levels.
     * @Require campaignDescription is less than 214 characters.
     */
    public Campaign(String campaignName, String campaignDescription) {
        this.levels = new ArrayList<>();
        this.currentLevel = 1;
        this.campaignDescription = campaignDescription;
        this.campaignName = campaignName;
    }

    /**
     * Return the level requested.
     * @param levelNumber - The level number in the campaign.
     * @return getLevel in accordance to level number.
     */
    public AbstractLevel getLevel(int levelNumber) {
        return levels.get(levelNumber - 1);
    }

    /**
     * Add a level to the campaign.
     * @param level
     */
    public void addLevelToCampaign(AbstractLevel level) {
        this.levels.add(level);
    }

    /**
     * Checks whether this campaign is completed (i.e. all levels are completed)
     * @return True if campaign is completed, false otherwise.
     */
    public boolean isCompleted() {
        boolean isCompleted = true;
        for (AbstractLevel level : levels) {
            if (!level.isCompleted()) {
                isCompleted = false;
            }
        }
        return isCompleted;
    }

    /**
     * Get this campaigns name.
     * @return The campaigns name
     */
    public String getCampaignName() {
        return this.campaignName;
    }

    /**
     * Return the campaign description
     * @return The campaign description
     */
    public String getCampaignDescription() {
        return this.campaignDescription;
    }
}
