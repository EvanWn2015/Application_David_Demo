package idv.david.buttonex;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;




public class MainActivity extends ActionBarActivity {
    private Button btnOne, btnCustom;
    private ImageButton imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btnOne = (Button) findViewById(R.id.btnOne);
        //寫法一：匿名類別
        btnOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                //按鈕按下後執行的內容
                String text = ((Button) view).getText().toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT)
                        .show();
            }
        });

        btnCustom = (Button) findViewById(R.id.btnCustom);
        btnCustom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getString(R.string.btncustom), Toast.LENGTH_SHORT)
                        .show();
            }
        });

        imgBtn = (ImageButton) findViewById(R.id.imgBtn);
        MyClickListener listener = new MyClickListener();
        imgBtn.setOnClickListener(listener);

    }

    //寫法三：新增類別並實作View.OnClickListener
    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, getString(R.string.imgbtn), Toast.LENGTH_SHORT)
                    .show();
        }
    }


    //寫法二：xml定義onClick屬性
    //自訂onClick方法格式必須是public void XXX(View view) {}
    public void onButtonClick(View view) {
        String text = ((Button) view).getText().toString();
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT)
                .show();
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
