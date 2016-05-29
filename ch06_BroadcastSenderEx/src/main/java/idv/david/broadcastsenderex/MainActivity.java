package idv.david.broadcastsenderex;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private final static String BROADCAST_1_ACTION = "idv.david.broadcastsenderex.Broadcast_1";
    private final static String BROADCAST_2_ACTION = "idv.david.broadcastsenderex.Broadcast_2";
    private Button btnBC_1, btnBC_2, btnBC2_reg, btnBC_2_cancel;
    private Broadcast_2 broadcast_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        //已在AndroidManifest.xml設定廣播
        btnBC_1 = (Button) findViewById(R.id.btnBC_1);
        btnBC_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BROADCAST_1_ACTION);
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "Broadcast_1送出", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        btnBC_2 = (Button) findViewById(R.id.btnBC_2);
        btnBC_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BROADCAST_2_ACTION);
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this, "Broadcast_2送出", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //由程式碼進行廣播註冊
        btnBC2_reg = (Button) findViewById(R.id.btnBC2_reg);
        btnBC2_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 把要註冊到AndroManifest的廣播事件接收IntentFilter包在intentFilter裡
                IntentFilter intentFilter = new IntentFilter(BROADCAST_2_ACTION);
                broadcast_2 = new Broadcast_2();
                registerReceiver(broadcast_2, intentFilter); // 執行註冊broadcast_2 到intentFilter
                Toast.makeText(MainActivity.this, "Broadcast_2已註冊", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //由程式碼處理廣播註銷
        btnBC_2_cancel = (Button) findViewById(R.id.btnBC_2_cancel);
        btnBC_2_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unregisterReceiver(broadcast_2); // 註銷intentFilter
                Toast.makeText(MainActivity.this, "Broadcast_2已註銷", Toast.LENGTH_SHORT)
                        .show();
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
