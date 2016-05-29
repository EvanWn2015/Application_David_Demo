package idv.david.servicebindex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvResult;
    private Button btPlay, btStop;
    private boolean isBound;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        btPlay = (Button) findViewById(R.id.btPlay);
        btStop = (Button) findViewById(R.id.btStop);
        // 一開始隱藏「播放音樂」、「停止播放」按鈕
        btPlay.setVisibility(View.INVISIBLE); // 隱藏元件setVisibility()，INVISIBLE<不可見>
        btStop.setVisibility(View.INVISIBLE);
    }

    public void onConnectClick(View view) {
        doBindService();
    }

    public void onDisconnectClick(View view) {
        doUnbindService();
    }

    public void onPlayClick(View view) {
        String message = musicService.play();
        tvResult.setText(message);
    }

    public void onStopClick(View view) {
        String message = musicService.stop();
        tvResult.setText(message);
    }

    void doBindService() {
        if (!isBound) {
            Intent intent = new Intent(this, MusicService.class);
            // 連結intent所指定的Service
            // serviceCon是實作ServiceConnection介面的物件
            // Context.BIND_AUTO_CREATE代表只要連結到Service，就會自動建立該Service
            // isBound代表是否與Service連結，一旦連結就設定為true
            bindService(intent, serviceCon, Context.BIND_AUTO_CREATE); // 建立Service
            isBound = true;
        }
    }

    void doUnbindService() {
        // 先檢查是否與Service連結，如果是則呼叫unbindService()，
        // 解除與該Service間的連結，並將isBound設為false
        if (isBound) {
            unbindService(serviceCon);  // 結束Service
            isBound = false;
            btPlay.setVisibility(View.INVISIBLE);
            btStop.setVisibility(View.INVISIBLE);
            tvResult.setText(R.string.msg_serviceDisconnected);
        }
    }

    private ServiceConnection serviceCon = new ServiceConnection() { //直接實作，匿名類別
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.ServiceBinder) iBinder).getService();
            tvResult.setText(R.string.msg_serviceConnected);
            btPlay.setVisibility(View.VISIBLE);  // VISIBLE，可見的
            btStop.setVisibility(View.VISIBLE);
        }

        @Override
        // 失去與Service間的連結時會呼叫此方法，但並未移除ServiceConnection，
        // 當Service再次執行時，onServiceConnected()會再次被呼叫
        public void onServiceDisconnected(ComponentName className) {
            musicService = null;
            tvResult.setText(R.string.msg_serviceLostConnection);
        }
    };

    @Override
    protected void onDestroy() {  // 生命週期
        super.onDestroy();
        doUnbindService();
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
