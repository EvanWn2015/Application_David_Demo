package idv.david.activitylifecycleex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

// Activity 生命週期
// 對於資源釋放相當重要
public class MainActivity extends ActionBarActivity {

    final private static String TAG = "ActivityLifeCycleEx";


    @Override
    protected void onCreate(Bundle savedInstanceState) { // 載入
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "This is onCreate");
    }

    @Override
    protected void onStart() { // 開始
        super.onStart();
        Log.e(TAG, "This is onStart");
    }

    @Override
    protected void onResume() {  // 暫停狀態下，繼續
        super.onResume();
        Log.e(TAG, "This is onResume");   // 標籤紀錄，'e'代表Error 信息
    }

    @Override
    protected void onPause() {  // 暫停
        super.onPause();
        Log.e(TAG, "This is onPause");
    }

    @Override
    protected void onRestart() {  // 停止狀態下，開始
        super.onRestart();
        Log.e(TAG, "This is onRestart");
    }

    @Override
    protected void onStop() { // 停止。暫停太久後
        super.onStop();
        Log.e(TAG, "This is onStop");
    }

    @Override
    protected void onDestroy() { // 結束。停止太久後
        super.onDestroy();
        Log.e(TAG, "This is onDestroy");
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
