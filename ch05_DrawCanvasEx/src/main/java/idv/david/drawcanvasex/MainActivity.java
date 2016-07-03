package idv.david.drawcanvasex;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {
    private Handler handler;
    private Button btnGen;
    private RectBar rectBar;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        handler.post(r);
        // 啟動app時螢幕自動轉為橫向
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findViews();
    }

    private void findViews() {
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.setBackgroundColor(Color.BLACK);
        rectBar = (RectBar)findViewById(R.id.rectBar);
        btnGen = (Button)findViewById(R.id.btnGen);
        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rectBar.invalidate(); // 重新初始化元件
            }
        });
    }
    
    Runnable r = new Runnable() {
        @Override
        public void run() {
            rectBar.invalidate();
            handler.postDelayed(r,1000);
        }
    };


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
