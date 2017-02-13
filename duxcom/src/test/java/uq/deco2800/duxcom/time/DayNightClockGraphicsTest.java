package uq.deco2800.duxcom.time;

import org.junit.Before;
import org.junit.Test;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.DarknessGraphicsHandler;

import static org.junit.Assert.*;

/**
 * Created by elliot on 18/08/16.
 */
public class DayNightClockGraphicsTest {
    DayNightClock testClock;
    GameManager testGameManager = new GameManager();
    DarknessGraphicsHandler graphicsHandler;

    @Before
    public void setUp() throws Exception {
        this.graphicsHandler = new DarknessGraphicsHandler();
        this.testClock = new DayNightClock(0.5);
    }

    @Test
    public void testGraphics() throws Exception {
    }
}
