package idv.david.preferencefragmentex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new InfoFragment(), "InfoFragment")
                .commit();
    }
}
