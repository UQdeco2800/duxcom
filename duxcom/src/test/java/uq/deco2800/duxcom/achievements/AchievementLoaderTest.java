package uq.deco2800.duxcom.achievements;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Gormly on 21/10/2016.
 */
public class AchievementLoaderTest {

    @Test
    public void loadAllAchievements() {
        AchievementLoader.addAll();
        assertEquals(18, AchievementManager.getAllAchievements().size());
    }

}
