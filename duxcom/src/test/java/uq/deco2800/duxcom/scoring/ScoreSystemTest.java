package uq.deco2800.duxcom.scoring;
import org.junit.*;

/**
 * Created by Tom B on 23/08/2016.
 */
public class ScoreSystemTest {
    @Test
    public void changeScoreTest() {
        ScoreSystem scoring = new ScoreSystem();
        Assert.assertEquals(0, scoring.getScore());
        scoring.changeScore(100, '+');
        Assert.assertEquals(100, scoring.getScore());
        scoring.changeScore(25, '-');
        Assert.assertEquals(75, scoring.getScore());
        scoring.changeScore(100, '-');
        Assert.assertEquals(0, scoring.getScore());
    }

    @Test
    public void resetScoreTest() {
        ScoreSystem scoring = new ScoreSystem();
        Assert.assertEquals(0, scoring.getScore());
        scoring.changeScore(100, '+');
        Assert.assertEquals(100, scoring.getScore());
        scoring.resetScore();
        Assert.assertEquals(0, scoring.getScore());
    }
}
