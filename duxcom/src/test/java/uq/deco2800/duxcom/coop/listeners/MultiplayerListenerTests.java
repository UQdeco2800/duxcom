package uq.deco2800.duxcom.coop.listeners;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * Tests the multiplayer listeners.
 *
 * Created by liamdm on 21/10/2016.
 */
public class MultiplayerListenerTests {
    @Test
    public void testBlockStateListener(){
        AtomicBoolean passing =new AtomicBoolean(false);
        BlockStateListener blockStateListener = new BlockStateListener() {
            @Override
            public void onRequireUpdate() {
                passing.set(true);
            }
        };

        blockStateListener.onRequireUpdate();

        assertTrue(passing.get());
    }
    @Test
    public void testCountdownListener(){
        AtomicBoolean passing =new AtomicBoolean(false);
        CountdownListener countdownListener = new CountdownListener() {
            @Override
            public void onCountdownEnded() {
                passing.set(true);
            }

        };

        countdownListener.onCountdownEnded();

        assertTrue(passing.get());
    }
    @Test
    public void testTurnTimeoutListener(){
        AtomicBoolean passing =new AtomicBoolean(false);
        TurnTimeoutListener turnTimeoutListener = new TurnTimeoutListener() {
            @Override
            public void onTurnTimeout(String user) {
                passing.set(true);
            }


        };

        turnTimeoutListener.onTurnTimeout("test");

        assertTrue(passing.get());
    }
}