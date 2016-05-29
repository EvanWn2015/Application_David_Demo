package idv.david.activityreturnex;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    //請求代碼，自行定義
    private final int LOGIN_REQUEST = 0;
    private Button btnLogin;
    private ImageView ivFlower;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        ivFlower = (ImageView)findViewById(R.id.ivFlower);
        tvResult = (TextView)findViewById(R.id.tvResult);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                //將自行定義的請求代碼一起送出，才能確認資料來源與出處是否為同一個

                // startActivity(intent);
                startActivityForResult(intent, LOGIN_REQUEST); // 回傳型Activity
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //（int 請求代碼, int 結果代碼, Intent data）
        //判斷請求代碼是否相同，確認來源是否正確
        if(requestCode != LOGIN_REQUEST) {
            return;
        }

        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();  // 取出回傳後Intent內的資料
                String result = bundle.getString("result");
                tvResult.setText(result);
                ivFlower.setImageResource(R.drawable.flower);
                break;
            case RESULT_CANCELED:
                tvResult.setText("取消登入，有好圖竟然不想看...");
                ivFlower.setImageResource(0);
                break;
        }
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
