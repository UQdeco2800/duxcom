package uq.deco2800.duxcom.achievements;

import static uq.deco2800.duxcom.achievements.AchievementType.*;

/**
 * Achievement Loader creates all possible achievements and loads them into the
 * achievement manager.
 *
 * Created by Gormly on 13/10/2016.
 */
public class AchievementLoader {
    /* Kill achievements. */
    private static Achievement kill1 = new Achievement("kill1", "The Littlest Genocide",
            "Pubstop 25 Enemies.", KILL, 25);
    private static Achievement kill2 = new Achievement("kill2", "Black eye",
            "Destroy 50 enemies.", KILL, 50);
    private static Achievement kill3 = new Achievement("kill3", "Cage Fight",
            "Pummel 100 enemies.", KILL, 100);
    private static Achievement kill4 = new Achievement("kill4", "Blood For The Sky God",
            "Wipe out 200 enemies.", KILL, 200);

    /* Death achievements. */
    private static Achievement death1 = new Achievement("death1", "Accident Prone",
            "Fail 25 times.", DEATH, 25);
    private static Achievement death2 = new Achievement("death2", "Pinata Party",
            "At least the everyone else is having fun. Die 50 times.", DEATH, 50);
    private static Achievement death3 = new Achievement("death3",
            "Pyramid Of Skulls", "Storm before the calm? Lose 100 soldiers.", DEATH, 100);
    private static Achievement death4 = new Achievement("death4", "Rome Has Fallen",
            "The war is over! Lose 200 soldiers.", DEATH, 200);

    /* Login Achievements. */
    private static Achievement score1 = new Achievement("login1", "Can't Eat Just One",
            "Login 25 times." , SCORE, 25);
    private static Achievement score2 = new Achievement("login2", "A Daily Routine",
            "Login 50 times.", SCORE, 50);
    private static Achievement score3 = new Achievement("login3", "'Tis The Season",
            "Login 100 times.", SCORE, 100);
    private static Achievement score4 = new Achievement("login4", "Call of Duty",
            "This is getting out of hand.", SCORE, 200);

    private static Achievement time1 = new Achievement("time1", "Traveller",
            "Play 2.5 hours of games.", TIME, 25);
    private static Achievement time2 = new Achievement("time2", "Challenger",
            "Play 5 hours of games.", TIME, 50);
    private static Achievement time3 = new Achievement("time3", "Knight In Shining Armour",
            "Play 10 hours of games.", TIME, 100);
    private static Achievement time4 = new Achievement("time4", "Lord Of Falador",
            "Play 20 hours of games.", TIME, 200);

    /* Random Achievements. */
    private static Achievement tutorialAch = new Achievement("tutorial", "Tutorial Island",
            "You're learning!", MIS, 1);
    private static Achievement secretAch = new Achievement("secret", "?", "????", MIS, 1);

    private AchievementLoader() {
        /* placeholder */
    }

    /**
     * Loads all achievements into the Achievement Manager.
     */
    public static void addAll(){
        /* Add kill Achievements. */
        AchievementManager.addAchievement(kill1);
        AchievementManager.addAchievement(kill2);
        AchievementManager.addAchievement(kill3);
        AchievementManager.addAchievement(kill4);

        /* Add Death Achievements. */
        AchievementManager.addAchievement(death1);
        AchievementManager.addAchievement(death2);
        AchievementManager.addAchievement(death3);
        AchievementManager.addAchievement(death4);

        /* Add Score Achievements. */
        AchievementManager.addAchievement(score1);
        AchievementManager.addAchievement(score2);
        AchievementManager.addAchievement(score3);
        AchievementManager.addAchievement(score4);

        /* Add Time Achievements. */
        AchievementManager.addAchievement(time1);
        AchievementManager.addAchievement(time2);
        AchievementManager.addAchievement(time3);
        AchievementManager.addAchievement(time4);

        /* Random Achievements. */
        AchievementManager.addAchievement(tutorialAch);
        AchievementManager.addAchievement(secretAch);
    }
}
