package uq.deco2800.duxcom.coop;

import uq.deco2800.duxcom.coop.listeners.CountdownListener;
import uq.deco2800.duxcom.sound.SoundPlayer;

/**
 * Manages the game countdown
 *
 * Created by liamdm on 18/10/2016.
 */
public class GameCountdown {
    private final CountdownListener listener;
    private boolean isBlocking = true;
    private boolean isCountdownStarted = false;
    private boolean defsNotCountingDown = false;
    private static final double[] countdownStages = {1136, 2180, 3204, 4299, 5251, 6336, 7585, 8578, 9458, 10420, 12022};
    long countdownStarted = 0;

    public GameCountdown(CountdownListener listener) {
        this.listener = listener;
    }

    /**
     * If this should be blocking input
     */
    public boolean isBlocking(){
        return isCountingDown() || isBlocking;
    }

    /**
     * Start the game countdown
     */
    public void start(){
        isCountdownStarted = true;
        isBlocking = false;
        countdownStarted = System.currentTimeMillis();

        SoundPlayer.playGameCountdown();

        Thread countdownThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long)countdownStages[countdownStages.length - 1]);
                } catch (InterruptedException i){
                    Thread.currentThread().interrupt();
                }
                defsNotCountingDown = true;
                listener.onCountdownEnded();
            }
        });
        countdownThread.start();
    }

    /**
     * Returns the game countdown delta
     */
    public double getCountdownDelta() {
        long now = System.currentTimeMillis();
        return now - countdownStarted;
    }

    /**
     * Returns the number corresponding to the current countdown stage
     */
    public int getCountdownNumber() {
        double delta = getCountdownDelta();

        for (int i = 0; i < countdownStages.length; ++i) {
            double stage = countdownStages[i] + 250;

            if (delta < stage) {
                return 11 - i;
            }
        }

        return 0;
    }


    /**
     * Returns true if the game is counting down to game start
     */
    public boolean isCountingDown() {
        if(defsNotCountingDown) {
            return false;
        }

        if (!this.isCountdownStarted) {
            return false;
        }

        if (getCountdownDelta() > 13000) {
            this.isCountdownStarted = false;
        }

        return isCountdownStarted;
    }

}
