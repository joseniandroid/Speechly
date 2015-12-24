package tech.sidespell.speechlyandroid.activity;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import tech.sidespell.speechlyandroid.R;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        DialogInterface.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView     mTvTime;
    private ToggleButton mBtnSwitch;
    private EditText     mEtTimeInput;

    private long timeRemaining = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding all views
        mTvTime = (TextView) findViewById(R.id.tvTime);
        mBtnSwitch = (ToggleButton) findViewById(R.id.btnSwitch);

        // Set the appropriate listeners
        mBtnSwitch.setOnCheckedChangeListener(this);

        // Set the font to the textview
        AssetManager assetManager = getAssets();
        Typeface     customFont   = Typeface.createFromAsset(assetManager, "fonts/SourceSansPro_Light.otf");
        mTvTime.setTypeface(customFont);

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                timeRemaining -= 1000;
                mTvTime.setText(timeRemaining + "");

                if (timeRemaining > 0) {
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.postDelayed(runnable, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            /*
             1) the ON button was clicked by the user
             2) show the dialog
            */
            Log.d(TAG, "onCheckedChanged: ON");
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.user_input, null);

            mEtTimeInput = (EditText) view.findViewById(R.id.etTimeInput);

            new AlertDialog.Builder(this)
                    .setTitle("Please input a time")
                    .setView(view)
                    .setPositiveButton("OK", this)
                    .setNegativeButton("Cancel", this)
                    .show();
        } else {
            /*
            1) the OFF button was clicked by the user
            2) if the timer is ON, stop it, reset time text to "00:00", set timeRemaining to 0
            */
            Log.d(TAG, "onCheckedChanged: OFF");
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                /*
                 1) the user has clicked the OK button
                 2) accept the input from the EditText
                 3) if the input is valid, start the timer
                 4) if the input is invalid, set time text to "00:00" and reset mTimeRemaining variable
                 5) make the toggle button turned OFF
                 6) the input cannot be null
                 7) after removing extra blank spaces, it must have 5 characters
                 Example "  05:23  "
                 8) it must have a ':' at the center
                 9) the first 2 are digits
                 10) the last 2 are digits
                */
                Log.d(TAG, "OK Clicked with time input value of " + mEtTimeInput.getText().toString());
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                Log.d(TAG, "Cancel clicked");
                break;
        }
    }
}
