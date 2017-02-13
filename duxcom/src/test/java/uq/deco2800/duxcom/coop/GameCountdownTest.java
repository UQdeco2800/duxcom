package uq.deco2800.duxcom.coop;

import org.junit.Test;
import uq.deco2800.duxcom.coop.listeners.CountdownListener;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tests the game countdown.
 *
 * Created by liamdm on 21/10/2016.
 */
public class GameCountdownTest {

    @Test
    public void testGameCountdown() throws InterruptedException {
        AtomicBoolean triggered = new AtomicBoolean(false);
        GameCountdown gc = new GameCountdown(new CountdownListener() {
            @Override
            public void onCountdownEnded() {
                triggered.set(true);
            }
        });
        assertFalse(gc.isCountingDown());
        assertTrue(gc.isBlocking());

        assertTrue(gc.getCountdownNumber() < 12);
        assertTrue(gc.getCountdownDelta() > 0);

        gc.start();

        assertTrue(gc.isCountingDown());
        assertTrue(gc.isBlocking());

        while(gc.isCountingDown());
        assertTrue(triggered.get());

        assertFalse(gc.isCountingDown());
        assertFalse(gc.isBlocking());
    }
}