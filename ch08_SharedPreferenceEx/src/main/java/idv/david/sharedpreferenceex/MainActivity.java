package idv.david.sharedpreferenceex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private final static String TAG = "MainActivity";
    private final static String PREF_NAME = "music";
    private final static boolean DEFAULT_SHUFFLE = true;
    private final static boolean DEFAULT_REPEAT = true;
    private final static int DEFAULT_VOLUME = 5;

    private EditText etVolume;
    private RadioButton rbYes, rbNo;
    private Button btnSave, btnLoad, btnDefault;
    private CheckBox cbRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        loadPref();
    }

    private void findViews() {
        etVolume = (EditText) findViewById(R.id.etVolume);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
        cbRepeat = (CheckBox) findViewById(R.id.cbRepeat);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref();
            }
        });

        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPref();
            }
        });

        btnDefault = (Button) findViewById(R.id.btnDefault);
        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDefault();
            }
        });

    }

    private void savePref() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int volume;
        try {
            volume = Integer.parseInt(etVolume.getText().toString());
        } catch (NumberFormatException nfe) {
            Toast.makeText(this, getString(R.string.et_error), Toast.LENGTH_SHORT).show();
            Log.e(TAG, nfe.toString());
            return;
        }
        boolean isShuffle = rbYes.isChecked();
        boolean isRepeat = cbRepeat.isChecked();

        preferences.edit()
                .putInt("volume", volume)
                .putBoolean("isShuffle", isShuffle)
                .putBoolean("isRepeat", isRepeat)
                .apply();
        Toast.makeText(this, getString(R.string.save_pref), Toast.LENGTH_SHORT).show();
    }

    private void loadPref() {
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int volume = preferences.getInt("volume", DEFAULT_VOLUME);
        etVolume.setText(String.valueOf(volume));
        boolean isShuffle = preferences.getBoolean("isShuffle", DEFAULT_SHUFFLE);
        if (isShuffle)
            rbYes.setChecked(true);
        else
            rbNo.setChecked(true);


        boolean isRepeat = preferences.getBoolean("isRepeat", DEFAULT_REPEAT);
        if (isRepeat)
            cbRepeat.setChecked(true);
        else
            cbRepeat.setChecked(false);
    }

    private void backDefault() {
        etVolume.setText(String.valueOf(DEFAULT_VOLUME));
        rbNo.setChecked(DEFAULT_SHUFFLE);
        cbRepeat.setChecked(DEFAULT_REPEAT);
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
}
