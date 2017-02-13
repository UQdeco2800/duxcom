package uq.deco2800.duxcom.turns;

import org.junit.Assert;
import org.junit.Test;
import uq.deco2800.duxcom.turns.*;

/**
 * Tests out a basic turn counter.
 * Created by Tom B on 4/09/2016.
 */
public class TurnCounterTest {

    @Test
    public void testCounterInitialisation() {

        // Test turn counter with unlimited turns
        TurnCounter t = new TurnCounter();
        Assert.assertEquals(0, t.getTurns());
        Assert.assertEquals("Turns made: 0", t.toString());

        // Test turn counter limited by a certain number of turns
        LimitedTurnCounter t2 = new LimitedTurnCounter(10);
        Assert.assertEquals(0, t2.getTurns());
        Assert.assertEquals(10, t2.getTurnLimit());
        Assert.assertEquals("Limit: 10; Turns made: 0", t2.toString());

    }

    @Test
    public void testCounterReachingLimit() {
        // Test turn counter limited by a certain number of turns
        LimitedTurnCounter t2 = new LimitedTurnCounter(10);
        Assert.assertEquals(0, t2.getTurns());
        t2.incrementTurn();
        Assert.assertEquals(1, t2.getTurns());
        t2.changeTurns(10);
        Assert.assertEquals(10, t2.getTurns());
        Assert.assertEquals(t2.getTurnLimit(), t2.getTurns());

    }

    @Test
    public void testCounterNotReachingLimit() {
        // Test turn counter with unlimited turns
        TurnCounter t = new TurnCounter();
        Assert.assertEquals(0, t.getTurns());
        t.incrementTurn();
        Assert.assertEquals(1, t.getTurns());
        t.changeTurns(10);
        Assert.assertEquals(11, t.getTurns());

        // Test turn counter limited by a certain number of turns
        LimitedTurnCounter t2 = new LimitedTurnCounter(10);
        Assert.assertEquals(0, t2.getTurns());
        t2.incrementTurn();
        Assert.assertEquals(1, t2.getTurns());
        t2.changeTurns(8);
        Assert.assertEquals(9, t2.getTurns());
    }

    @Test
    public void testCounterReset() {
        // Test turn counter with unlimited turns
        TurnCounter t = new TurnCounter();
        Assert.assertEquals(0, t.getTurns());
        t.incrementTurn();
        Assert.assertEquals(1, t.getTurns());
        t.reset();
        Assert.assertEquals(0, t.getTurns());

        // Test turn counter limited by a certain number of turns
        LimitedTurnCounter t2 = new LimitedTurnCounter(10);
        Assert.assertEquals(0, t2.getTurns());
        t2.incrementTurn();
        Assert.assertEquals(1, t2.getTurns());
        t2.changeTurns(8);
        Assert.assertEquals(9, t2.getTurns());
        t2.reset();
        Assert.assertEquals(0, t.getTurns());
    }
}
