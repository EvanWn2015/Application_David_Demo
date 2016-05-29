package idv.david.preferencefragmentex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;


public class MainActivity extends ActionBarActivity {
    private TextView tvPersonalInfo;
    private Button btnPersonalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvPersonalInfo = (TextView) findViewById(R.id.tvPersonalInfo);
        btnPersonalInfo = (Button) findViewById(R.id.btnPersonalInfo);
        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPersonalInfo();
    }

    public void showPersonalInfo() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String str;
        str = "姓名：" + sharedPref.getString("prefName", "") + "\n" +
                "性別：" + sharedPref.getString("prefSex", "") + "\n" +
                "興趣：";

        Set<String> setHobbySelected = sharedPref.getStringSet("prefHobby", null);

        if (setHobbySelected != null)
            for (String s : setHobbySelected)
                str += s + ", ";

        if (sharedPref.getBoolean("prefHobbyBaseball", false))
            str += "棒球";

        if (sharedPref.getBoolean("prefHobbyMusic", false))
            str += ", 音樂";

        if (sharedPref.getBoolean("prefHobbySwimming", false))
            str += ", 游泳";

        str += "\n" +
                "鈴聲設定：" + sharedPref.getString("prefRingtone", "") + "\n" +
                "資料是否公開：";

        if (sharedPref.getBoolean("prefDisclosure", false))
            str += "是";
        else
            str += "否";

        tvPersonalInfo.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
