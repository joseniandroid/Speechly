package tech.sidespell.speechlyandroid.controller;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

/**
 * What are all the things this Timer has
 * <p/>
 * Instance Variables
 * - It knows how much time it has remaining.
 * - It knows whether it is running or stopped.
 * <p/>
 * Constructor: What it needs to run
 * - a Handler
 * - an optional remaining time in long or String format
 * <p/>
 * What it does?
 * - capable of starting
 * - capable of stopping
 * - capable of reset
 * - capable of notifying when the user interface needs to be updated
 * - capable of notifying when the timer stopped running
 */
public abstract class SpeechlyTimer implements Runnable {

    private static final String TAG                  = SpeechlyTimer.class.getSimpleName();
    private static final int    TIME_INPUT_LENGTH    = 5;
    private static final int    COLON_INDEX_POSITION = 2;

    private long    mTimeRemaining;
    private Handler mHandler;

    public SpeechlyTimer(Handler handler) {
        mHandler = handler;
    }

    public SpeechlyTimer(Handler handler, long timeRemaining) {
        mHandler = handler;
        mTimeRemaining = timeRemaining;
    }

    public static boolean isValidInput(String time) {
        if (TextUtils.isEmpty(time)) {
            return false;
        }

        String trimmedInput = time.trim();
        return trimmedInput.length() == TIME_INPUT_LENGTH &&
                trimmedInput.indexOf(':') == COLON_INDEX_POSITION;
    }

    public static long convertToMilliseconds(String time) {
        try {
            int minutes = Integer.parseInt(time.substring(0, COLON_INDEX_POSITION));
            int seconds = Integer.parseInt(time.substring(COLON_INDEX_POSITION + 1, time.length()));
            return (long) ((minutes * 60 + seconds) * 1000);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertToString(long time) {
        int totalSeconds = (int) (time / 1000);
        int minutes      = totalSeconds / 60;
        int seconds      = totalSeconds % 60;

        String minutesString = (minutes < 10) ? "0" + minutes : minutes + "";
        String secondsString = (seconds < 10) ? "0" + seconds : seconds + "";

        return minutesString + ":" + secondsString;
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
        } else {
            onTimerStopped();
        }
    }

    public abstract void updateUI(long timeRemaining);

    public abstract void onTimerStopped();
}
