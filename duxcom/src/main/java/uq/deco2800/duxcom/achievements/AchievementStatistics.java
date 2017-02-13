package uq.deco2800.duxcom.achievements;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Achievement Statics loads and saves the accounts progression throughout the game.
 *
 * Currently the class writes and saved to the settings file in resources/.achievements.txt
 *
 * Created by Daniel Gormly on 20/10/2016.
 */
public class AchievementStatistics {
    private static int kills = 0;
    private static int deaths = 0;
    private static int min = 0;
    private static int hours = 0;
    private static int games = 0;
    private static int login = 0;
    private static int tutorial = 0;
    private static int secret = 0;

    public static int getLogins() { return login; }

    public static void addLogins(int login) { AchievementStatistics.login += login; }

    private static final File ACCOUNT_FILE = new File(AchievementManager.PATH, ".account.txt");

    private static Logger logger = LoggerFactory.getLogger(AchievementManager.class);

    public static int getKills() {
        return kills;
    }

    public static void addKills(int kills) {
        AchievementStatistics.kills += kills;
    }

    public static int getDeaths() {
        return deaths;
    }

    public static void addDeaths(int deaths) {
        AchievementStatistics.deaths += deaths;
    }

    public static int getMins() {
        return min;
    }

    public static void addMins(int min) {
        AchievementStatistics.min += min;
        if (min > 60) {
            int numHours = (int) Math.floor(min/60);
            int numMin = min % 60;
            addHours(numHours);
            setMins(numMin);
        }
    }

    private static void setMins(int mins) { min = mins; }

    public static int getHours() {
        return hours;
    }

    public static void addHours(int hours) {
        AchievementStatistics.hours += hours;
    }

    public static int getGames() {
        return games;
    }

    public static void addGames(int games) {
        AchievementStatistics.games += games;
    }

    public static int getTutorial() {
        return tutorial;
    }

    public static void addTutorial(int tutorial) {
        AchievementStatistics.tutorial += tutorial;
    }

    public static int getSecret() {
        return secret;
    }

    public static void addSecret(int secret) {
        AchievementStatistics.secret += secret;
    }

    private AchievementStatistics() {
        /* placeholder. */
    };

    public static void save() {
        String line = "";
        try {
            FileWriter fw = new FileWriter(ACCOUNT_FILE, false);
            line = kills + "^" + deaths + "^" + min + "^" + hours + "^" + games + "^" + tutorial + "^" + secret + "^"
                    + login;
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line);
            fw.flush();
            bw.flush();
            bw.close();
            fw.close();
        } catch (Exception exception) {
            logger.error("Could not save achievement statistics file.", exception);
        }
    }

    public static void load() {
        if (ACCOUNT_FILE.exists()) {
            try {
                FileReader achievementReader = new FileReader(ACCOUNT_FILE);
                BufferedReader bufferedAchievement = new BufferedReader(achievementReader);
                String line;
                while ((line = bufferedAchievement.readLine()) != null) {
                    String[] splitLine = line.split("\\^", -1);
                    kills = Integer.parseInt(splitLine[0]);
                    deaths = Integer.parseInt(splitLine[1]);
                    min = Integer.parseInt(splitLine[2]);
                    hours = Integer.parseInt(splitLine[3]);
                    games = Integer.parseInt(splitLine[4]);
                    tutorial = Integer.parseInt(splitLine[5]);
                    secret = Integer.parseInt(splitLine[6]);
                    login = Integer.parseInt(splitLine[7]);
                }
                bufferedAchievement.close();
                achievementReader.close();
            } catch (Exception exception) {
                logger.error("Could not load achievement statistics file.", exception);
            }
        }
    }

    public static void reset() {
        kills = 0;
        deaths = 0;
        min = 0;
        hours = 0;
        games= 0;
        login = 0;
        tutorial = 0;
        secret = 0;
    }
}
