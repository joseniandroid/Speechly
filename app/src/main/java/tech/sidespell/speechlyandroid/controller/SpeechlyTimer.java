package tech.sidespell.speechlyandroid.controller;

import android.os.Handler;
import android.util.Log;

/**
 * What are all the things this Timer has
 * <p>
 * Instance Variables
 * - It knows how much time it has remaining.
 * - It knows whether it is running or stopped.
 * <p>
 * Constructor: What it needs to run
 * - a Handler
 * - an optional remaining time in long or String format
 * <p>
 * What it does?
 * - capable of starting
 * - capable of stopping
 * - capable of reset
 * - capable of notifying when the user interface needs to be updated
 * - capable of notifying when the timer stopped running
 */
public abstract class SpeechlyTimer implements Runnable {

    private static final String TAG = SpeechlyTimer.class.getSimpleName();

    private long    mTimeRemaining;
    private Handler mHandler;

    public SpeechlyTimer(Handler handler) {
        mHandler = handler;
    }

    public SpeechlyTimer(Handler handler, long timeRemaining) {
        mHandler = handler;
        mTimeRemaining = timeRemaining;
    }

    public void start() {
        mHandler.postDelayed(this, 1000);
    }

    public void setTimeRemaining(long timeRemaining) {
        mTimeRemaining = timeRemaining;
    }

    @Override
    public void run() {
        Log.d(TAG, "timeRemaining: " + mTimeRemaining / 1000);

        updateUI(mTimeRemaining);
        mTimeRemaining -= 1000;

        if (mTimeRemaining >= 0) {
            mHandler.postDelayed(this, 1000);
        }
    }

    public abstract void updateUI(long timeRemaining);
}
