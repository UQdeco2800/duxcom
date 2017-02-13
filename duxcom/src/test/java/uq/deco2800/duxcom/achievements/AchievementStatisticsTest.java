package uq.deco2800.duxcom.achievements;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Gormly on 21/10/2016.
 */
public class AchievementStatisticsTest {

    @Test
    public void getterSetters() {
        /* Setters. */
        AchievementStatistics.addKills(1);
        AchievementStatistics.addDeaths(1);
        AchievementStatistics.addMins(61);
        AchievementStatistics.addHours(1);
        AchievementStatistics.addGames(1);
        AchievementStatistics.addLogins(1);
        AchievementStatistics.addTutorial(1);
        AchievementStatistics.addSecret(1);

        /* Getters. */
        int kills = AchievementStatistics.getKills();
        int deaths = AchievementStatistics.getDeaths();
        int min = AchievementStatistics.getMins();
        int hours = AchievementStatistics.getHours();
        int games = AchievementStatistics.getGames();
        int logins = AchievementStatistics.getLogins();
        int tutorial = AchievementStatistics.getTutorial();
        int secret = AchievementStatistics.getSecret();

        assertEquals(1, kills);
        assertEquals(1, deaths);
        assertEquals(1, min);
        assertEquals(2, hours);
        assertEquals(1, games);
        assertEquals(1, logins);
        assertEquals(1, tutorial);
        assertEquals(1, secret);
    }

    @Test
    public void saveAndLoad() {
        AchievementStatistics.reset();

        AchievementStatistics.addKills(1);
        AchievementStatistics.addDeaths(2);
        AchievementStatistics.addMins(3);
        AchievementStatistics.addHours(4);
        AchievementStatistics.addGames(5);
        AchievementStatistics.addLogins(6);
        AchievementStatistics.addTutorial(7);
        AchievementStatistics.addSecret(8);

        AchievementStatistics.save();

        AchievementStatistics.reset();

        AchievementStatistics.load();

        int kills = AchievementStatistics.getKills();
        int deaths = AchievementStatistics.getDeaths();
        int min = AchievementStatistics.getMins();
        int hours = AchievementStatistics.getHours();
        int games = AchievementStatistics.getGames();
        int logins = AchievementStatistics.getLogins();
        int tutorial = AchievementStatistics.getTutorial();
        int secret = AchievementStatistics.getSecret();

        assertEquals(1, kills);
        assertEquals(2, deaths);
        assertEquals(3, min);
        assertEquals(4, hours);
        assertEquals(5, games);
        assertEquals(6, logins);
        assertEquals(7, tutorial);
        assertEquals(8      , secret);
    }
}
