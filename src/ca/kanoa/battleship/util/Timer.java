package ca.kanoa.battleship.util;

/**
 * Used to keep track of time. Has a stopwatch and a timer mode
 */
public class Timer {

    private long stopwatchStart;
    private long delay;

    /**
     * Creates a new timer with only stopwatch functionality
     */
    public Timer() {
        this(0);
    }

    /**
     * Creates a new timer with both stopwatch and timer functionality
     * @param delay The time to check in miliseconds
     */
    public Timer(long delay) {
        this.delay = delay;
        reset();
    }

    public void reset() {//resets the timer
        stopwatchStart = System.currentTimeMillis();
    }

    public long elapsed() {//counts elepsed tiime
        return System.currentTimeMillis() - stopwatchStart;
    }

    public boolean check() {//checks the timer
        return elapsed() >= delay;
    }

}
