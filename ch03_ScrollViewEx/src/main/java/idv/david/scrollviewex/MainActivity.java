package idv.david.scrollviewex;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView tvCount;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        tvCount.setText(Integer.toString(count));
    }

    private void findViews() {
        tvCount = (TextView) findViewById(R.id.tvCount);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    public void onAddClick(View view) {
        count++;
        tvCount.setText(Integer.toString(count));
        //藉由程式碼動態產生TextView，this指的是MainActivity這個物件
        TextView textView = new TextView(this);
        textView.setText(Integer.toString(count));
        linearLayout.addView(textView);

        //開啟執行緒，若scrollView填滿螢幕則聚焦在最下方
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
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
