package uq.deco2800.duxcom.time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by elliot on 18/08/16.
 */
public class DayNightClockTest {
    DayNightClock testClock;
    double interval;

    @Before
    public void setUp() throws Exception {
        interval = 0.5;
        this.testClock = new DayNightClock(interval);
    }

    @Test
    public void testInvalidIntervalOnConstruction() {
        DayNightClock clock = new DayNightClock(0, -1);
        assertEquals(0, clock.getInterval(), 0);
        clock = new DayNightClock(-5, 1);
        assertEquals(0, clock.getTime(), 0);
        clock = new DayNightClock(-1);
        assertEquals(0, clock.getInterval(), 0);
    }

    @Test
    public void testValidConstruction() {
        DayNightClock clock = new DayNightClock(22, 0.5);
        assertEquals(22, clock.getTime(), 0);
        assertEquals(0.5, clock.getInterval(), 0);
        clock = new DayNightClock(0.5);
        assertEquals(0.5, clock.getInterval(), 0);
    }


    @Test
    public void testTick() {
        assertEquals(0, testClock.getTime(), 0);
        this.testClock.tick();
        assertEquals(interval, testClock.getTime(), 0);
    }

    @Test
    public void testTimeChange() {
        //Test that an overflow time resets back to 0
        this.testClock.changeTime(25);
        assertEquals(0, testClock.getTime(), 0);
        //Test that a normal time change validates
        this.testClock.changeTime(23.98);
        assertEquals(23.98, testClock.getTime(), 0);
    }

    @Test
    public void testTickOverflow() {
        this.testClock.changeTime(23.99);
        this.testClock.tick();
        assertEquals(0, this.testClock.getTime(), 0);
    }

    @Test
    public void testCorrectDayTime() {
        this.testClock.changeTime(21);
        assertEquals(DayNightClock.NIGHT, this.testClock.getTimeForAbility());
        this.testClock.changeTime(4);
        assertEquals(DayNightClock.DAWN, this.testClock.getTimeForAbility());
        this.testClock.changeTime(8);
        assertEquals(DayNightClock.MORNING, this.testClock.getTimeForAbility());
        this.testClock.changeTime(15);
        assertEquals(DayNightClock.AFTERNOON, this.testClock.getTimeForAbility());
    }

    @Test
    public void testDayCounter() {
        DayNightClock clock = new DayNightClock(23.98, 1);
        assertEquals(0, clock.getDays());
        clock.tick();
        assertEquals(1, clock.getDays());
    }

    @Test
    public void testGetInterval() {
        DayNightClock clock = new DayNightClock(1);
        assertEquals(1, clock.getInterval(), 0);
    }

    @Test
    public void testDisableEnableClock() {
        DayNightClock clock = new DayNightClock(1);
        clock.disableClock();
        assertEquals(0, clock.getInterval(), 0);
        clock.enableClock(0.5);
        assertEquals(0.5, clock.getInterval(), 0);
    }


}
