package uq.deco2800.duxcom.time;

/**
 * Created by elliot on 18/08/16.
 */
public class DayNightClock {


    /**
     * Keeps track of in game days played.
     */
    private int daysPlayed;

    /**
     * Keep track of time in game.
     */
    private double time;
    /**
     * Time interval in game.
     */
    private double interval;

    /**
     * Constant values for time of day.
     */
    public static final int NIGHT = 0;
    public static final int DAWN = 1;
    public static final int MORNING = 2;
    public static final int AFTERNOON = 3;
    public static final int DUSK = 4;

    /**
     * Initialise DayNightClock with an interval.
     * @param interval - The interval in which the clock value increases by.
     */
    public DayNightClock(double interval) {
        this.time = 0;
        if (interval < 0) {
            this.interval = 0;
        } else {
            this.interval = interval;
        }

    }

    /**
     * This constructor is used for testing purposes.
     * @param value
     * @param interval
     */
    public DayNightClock(double value, double interval) {
        if (interval < 0) {
            this.interval = 0;
        } else {
            this.interval = interval;
        }
        if (value > 23.99 || value < 0) {
            this.time = 0;
        } else {
            this.time = value;
        }
        this.daysPlayed = 0;
    }

    /**
     * Disable the clock - set tick interval to 0. 
     */
    public void disableClock() {
        this.interval = 0;
    }

    /**
     * Enable the clock with an interval value.
     * @param intervalValue Clock will tick with new interval value.
     */
    public void enableClock(double intervalValue) {
        this.interval = intervalValue;
    }


    /**
     * @return Current time of game.
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Ticks the day night cycle. Because of time based game - this tick happens every time after the player
     * has made a move.
     */
    public void tick() {
        double nextValue = this.time + this.interval;
        if (nextValue >= 23.99) {
            this.time = 0;
            this.daysPlayed += 1;
        } else {
            this.time += interval;
        }
    }

    public double getInterval() {
        return this.interval;
    }

    /**
     *
     * @param value The value to change the time to. Value of the time will always be between 0-23.
     */
    public void changeTime(double value) {
        if (value > 23.99) {
            this.time = 0;
        } else {
            this.time = value;
        }
    }

    /**
     * This method is used to decide what time of the day it is. This method should be used
     * for buffering heroes attacks.
     * @return Time of day depending on time.
     */
    public int getTimeForAbility() {
        if (this.time < 5) {
            return DAWN;
        }
        if (this.time < 12) {
            return MORNING;
        }
        if (this.time < 16) {
            return AFTERNOON;
        }
        if (this.time < 20) {
            return DUSK;
        }
        return NIGHT;
    }

    /**
     * @return In game days.
     */
    public int getDays() {
        return this.daysPlayed;
    }
}
