package ca.kanoa.battleship.util;

/**
 * Used to keep track of time. Has a stopwatch and a timer mode
 */
public class Timer {

    private long stopwatchStart;
    private long delay;

    public Timer() {
        reset();
    }

    public Timer(long delay) {
        this.delay = delay;
        reset();
    }

    public void reset() {
        stopwatchStart = System.currentTimeMillis();
    }

    public long elapsed() {
        return System.currentTimeMillis() - stopwatchStart;
    }

    public boolean check() {
        return elapsed() >= delay;
    }

}
