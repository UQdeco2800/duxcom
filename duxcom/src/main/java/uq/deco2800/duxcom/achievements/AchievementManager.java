package uq.deco2800.duxcom.achievements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Achievement Manager is the manager used to control all possible achievements, which ones have been unlocked
 * and to manage how they are saved.
 *
 * Currently the class writes and saved to the settings file in resources/.achievements.txt
 *
 * Created by Daniel Gormly on 13/10/2016.
 */
public class AchievementManager {

    private static String accountName = "leggy";

    private static Logger logger = LoggerFactory.getLogger(AchievementManager.class);

    private static List<Achievement> allAchievementList = new ArrayList<>();

    private static List<Achievement> accountAchievementList = new ArrayList<>();

    protected static final String PATH = "" + System.getProperty("user.dir") + File.separator + "src" + File.separator
            + File.separator + "main" + File.separator + "resources" + File.separator + "settings";

    private static final File ACHIEVEMENTS_FILE = new File(PATH, ".achievements.txt");

    /**
     * Placeholder for static class.
     */
    private AchievementManager() {
        /* placeholder */
    }

    /**
     * Add a new achievement to the game.
     *
     * @param achievement, Achievement to be added.
     */
    public static void addAchievement(Achievement achievement) {
        if (achievement != null && !allAchievementList.contains(achievement)
                && checkValidId(achievement, allAchievementList)) {
            allAchievementList.add(achievement);
        }
    }

    /**
     * Return list of all possible achievements.
     *
     * @return
     */
    public static List<Achievement> getAllAchievements() {
        return allAchievementList;
    }

    /**
     * Gets all the achievements the user has unlocked.
     *
     * @return
     */
    public static List<Achievement> getAccountAchievements() {
        return accountAchievementList;
    }

    /**
     * Sets the username. Default is "leggy".
     *
     * @param username
     */
    public static void setUsername(String username) {
        accountName = username;
    }

    /**
     * Returns the username currently logged in. If "HACK THE WORLD", username = "leggy".
     * @return username
     */
    public static String getUsername() {
        return accountName;
    }

    /**
     * Add an unlocked achievement to the users account. This is saved locally
     * and can be modified manually.
     *
     * @param achievement, Achievement to be added.
     */
    public static void addAchievementToAccount(Achievement achievement) {
        if (achievement != null && !accountAchievementList.contains(achievement)
                && checkValidId(achievement, accountAchievementList)) {
            accountAchievementList.add(achievement);
            saveAccountAchievements();
        }
    }

    /**
     * Save all Achievements to the users account and add to local file. This
     * save can be modified.
     */
    public static void saveAccountAchievements() {
        String line = "";
        try {
            FileWriter fw = new FileWriter(ACHIEVEMENTS_FILE, false);
            for (Achievement i: accountAchievementList) {
                line = line + i.toString() + "\n";
            }
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            fw.flush();
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception exception) {
            logger.error("Could not find achievement file.", exception);
        }
    }

    /**
     * Load local account's achievements from local file into manager.
     * Load overwrites current account achievements that havm't been saved.
     * if load fails, current account achievements are restored.
     */
    public static void loadAccountAchievements() {
        /* Clear account achievements before load */
        List<Achievement> accountAchievements = getAccountAchievements();
        accountAchievementList = new ArrayList<>();
        try {
            if (ACHIEVEMENTS_FILE.exists()) {
                FileReader achievementReader = new FileReader(ACHIEVEMENTS_FILE);
                BufferedReader bufferedAchievement = new BufferedReader(achievementReader);
                String line;
                while ((line = bufferedAchievement.readLine()) != null) {
                    accountAchievementList.add(mapAchievement(line));
                }
                bufferedAchievement.close();
                achievementReader.close();
            }
        } catch (Exception exception) {
            /* Restore achievements. */
            accountAchievementList = accountAchievements;
            logger.error("Could not find achievement file", exception);
        }
    }

    /**
     * Map String to Achievement.
     *
     * @param line, Line to be converted into an Achievemen.
     * @return Achievement, achievement parsed from the line of code.
     *         Null, If line could not be parsed.
     */
    protected static Achievement mapAchievement(String line) {
        String[] parts = line.split("\\^", -1);
        if (parts.length == 5) {
            try {
                return new Achievement(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
            } catch (Exception e) {
                logger.error("Failed to load achievement.", e);
            }
        }
        return null;
    }

    /**
     * Returns an Achievement associated with the Achievement ID.
     *
     * @param achievementId
     * @return
     */
    public static Achievement getAchievement(String achievementId) {
        for (Achievement i : allAchievementList) {
            if (i.getId().equals(achievementId)) {
                return i;
            }
        }
        return new Achievement("error", "Error", "Could not load achievement.",
                AchievementType.KILL, 1);
    }

    /**
     * Checks if the given achievemet's id is unique.
     *
     * @param achievement
     * @param list, List be checked.
     * @return true, The given achievement's Id is unique.
     */
    private static boolean checkValidId(Achievement achievement, List<Achievement> list) {
        for (Achievement i: list) {
            if (i.getId().equals(achievement.getId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the achievement has been unlocked on the account.
     *
     * @param achievement
     * @return true, achievement has been unlocked.
     */
    public static boolean isUnlocked(Achievement achievement) {
        if (accountAchievementList.contains(achievement)) {
            return true;
        }
        return false;
    }

    /**
     * Unlocks all achievements for the account.
     */
    public static void unlockAll() {
        accountAchievementList = allAchievementList;
    }

    /**
     * Resets the AchievementManager.
     */
    public static void reset() {
        allAchievementList = new ArrayList<>();
        accountAchievementList = new ArrayList<>();
    }
}