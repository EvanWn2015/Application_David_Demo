package idv.david.compositebuttonex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {
    private RadioGroup rgBrand;
    private TextView tvResult;
    private Switch switchPower;
    private CheckBox cbJapan, cbGermany, cbFrance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        rgBrand = (RadioGroup) findViewById(R.id.rgBrand);
        cbJapan = (CheckBox) findViewById(R.id.cbJapan);
        cbGermany = (CheckBox) findViewById(R.id.cbGermany);
        cbFrance = (CheckBox) findViewById(R.id.cbFrance);
        tvResult = (TextView) findViewById(R.id.tvResult);
        switchPower = (Switch) findViewById(R.id.switchPower);

        rgBrand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(id);
                tvResult.setText(rb.getText().toString());
            }
        });

        switchPower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Switch sw = (Switch) compoundButton;
                String s = sw.getText().toString();
                String swResult = "";
                if (sw.isChecked()) {
                    swResult += s + " " + sw.getTextOn().toString();
                } else {
                    swResult += s + " " + sw.getTextOff().toString();
                }
                tvResult.setText(swResult);
            }
        });
    }

    public void onToggleButtonClick(View v) {
        ToggleButton tb = (ToggleButton)v;
        String tbResult = tb.getText().toString();
        tvResult.setText(tbResult);
    }

    public void onBtnSubmitClick(View v) {
        String cbResult = "";
        if (cbJapan.isChecked())
            cbResult += cbJapan.getText().toString() + " ";
        if (cbGermany.isChecked())
            cbResult += cbGermany.getText().toString() + " ";
        if (cbFrance.isChecked())
            cbResult += cbFrance.getText().toString() + " ";
        tvResult.setText(cbResult);
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
