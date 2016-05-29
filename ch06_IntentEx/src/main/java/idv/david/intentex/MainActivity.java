package idv.david.intentex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private EditText etTeam, etPlayer, etSalary;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        etTeam = (EditText) findViewById(R.id.etTeam);
        etPlayer = (EditText) findViewById(R.id.etPlayer);
        etSalary = (EditText) findViewById(R.id.etSalary);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(); // 轉換Activity 需先建立Intent，使用Intent來轉換
                intent.setClass(MainActivity.this, ResultActivity.class); // 轉換 Activity 到 Activity
                Bundle bundle = new Bundle(); // 轉換Activity攜帶參數

                // 傳送基本資料並對使用者輸入資料做例外處理
                try {
                    String team = etTeam.getText().toString();
                    String player = etPlayer.getText().toString();
                    double salary = Double.parseDouble(etSalary.getText().toString());

                    if(team.isEmpty() || player.isEmpty()) {
                        throw new Exception();
                    }

                    bundle.putString("team", team);  // list map集合
                    bundle.putString("player", player);
                    bundle.putDouble("salary", salary);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, getString(R.string.inputError), Toast.LENGTH_SHORT).show();
                    return;
                }

                // 傳送序列化物件
                int id = 1;
                int logo = R.drawable.p23;
                String name = "洛杉磯道奇";
                Team team = new Team(id, logo, name);
                bundle.putSerializable("teamObj", team);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
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
