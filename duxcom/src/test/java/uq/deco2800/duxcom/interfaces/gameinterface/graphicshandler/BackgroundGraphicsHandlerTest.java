package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uq.deco2800.duxcom.GameManager;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.BackgroundGraphicsHandler;
import uq.deco2800.duxcom.time.DayNightClock;
import static org.junit.Assert.*;

/**
*	Coppied from Elliots test DayNightClock test
**/

public class BackgroundGraphicsHandlerTest {
    DayNightClock testClock;
    GameManager testGameManager = new GameManager();
    BackgroundGraphicsHandler graphicsHandler;

    @Before
    public void setUp() throws Exception {
        this.graphicsHandler = new BackgroundGraphicsHandler();
        this.testClock = new DayNightClock(0.5);
    }

    @Test @Ignore
    public void checkRatio1() throws Exception {

        //testing time > 0
          testClock.changeTime(0);
        double expected = 0.5;
        assertEquals(expected, graphicsHandler.getRatio1(testClock.getTime()), 0);
        // testing time > 6
        testClock.changeTime(6);
        double expected1 = 0;
        assertEquals(expected1, graphicsHandler.getRatio1(testClock.getTime()), 0);
        
    }
    @Test @Ignore
    public void checkRatio2() throws Exception {

        //testing time > 6
          testClock.changeTime(6);
        double expected = 0.5;
        assertEquals(expected, graphicsHandler.getRatio2(testClock.getTime()), 0);
        // testing time > 12
        testClock.changeTime(12);
        double expected1 = 0;
        assertEquals(expected1, graphicsHandler.getRatio2(testClock.getTime()), 0);
        
    }
    @Test @Ignore
    public void checkRatio3() throws Exception {

        //testing time > 12
          testClock.changeTime(12);
        double expected = 0.5;
        assertEquals(expected, graphicsHandler.getRatio3(testClock.getTime()), 0);
        // testing time > 18
        testClock.changeTime(18);
        double expected1 = 0;
        assertEquals(expected1, graphicsHandler.getRatio3(testClock.getTime()), 0);
        
    }
    @Test @Ignore
    public void checkRatio4() throws Exception {

        //testing time > 18
          testClock.changeTime(18);
        double expected = 0.3;
        assertEquals(expected, graphicsHandler.getRatio4(testClock.getTime()), 0);
        // testing time > 0
        testClock.changeTime(0);
        double expected1 = 0;
        assertEquals(expected1, graphicsHandler.getRatio4(testClock.getTime()), 0);
        
    }

    @Test @Ignore
    public void testGraphics() throws Exception {
    }
}
