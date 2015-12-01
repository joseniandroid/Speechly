package tech.sidespell.speechlyandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import tech.sidespell.speechlyandroid.R;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView     mTvTime;
    private ToggleButton mBtnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding all views
        mTvTime = (TextView) findViewById(R.id.tvTime);
        mBtnSwitch = (ToggleButton) findViewById(R.id.btnSwitch);

        // Set the appropriate listeners
        mBtnSwitch.setOnCheckedChangeListener(this);
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
            Log.d(TAG, "onCheckedChanged: ON");
        } else {
            Log.d(TAG, "onCheckedChanged: OFF");
        }
    }
}
