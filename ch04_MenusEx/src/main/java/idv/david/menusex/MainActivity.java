package idv.david.menusex;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    public void findViews() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        registerForContextMenu(tvResult);
    } //使用contextMenu需要先宣告一組元素給使用

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String result = "";
        switch (item.getItemId()) {
            case R.id.menuMLB:
                result += getString(R.string.mlb);
                break;
            case R.id.al:
                result += getString(R.string.mlb) + ">>" + getString(R.string.american);
                break;
            case R.id.nl:
                result += getString(R.string.mlb) + ">>" + getString(R.string.national);
                break;
            case R.id.pbl:
                result += getString(R.string.pbl);
                break;
            case R.id.cpbl:
                result += getString(R.string.cpbl);
                break;
            case R.id.exit:
                System.exit(0); //離開目前app
            default:
                return super.onOptionsItemSelected(item);
        }
        tvResult.setText(result);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                tvResult.setText(""); //清除已顯示的String
                break;
            case R.id.yellow:
                tvResult.setBackgroundColor(Color.YELLOW); //改變背景顏色
                break;
            case R.id.green:
                tvResult.setBackgroundColor(Color.GREEN);
                break;
            case R.id.red:
                tvResult.setBackgroundColor(Color.RED);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
