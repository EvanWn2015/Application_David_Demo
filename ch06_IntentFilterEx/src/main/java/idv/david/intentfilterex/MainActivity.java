package idv.david.intentfilterex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MainActivity extends ActionBarActivity {
    private Button btnBrowser, btnEditPic, btnViewPic, btnDialTel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btnBrowser = (Button) findViewById(R.id.btnBrowser);
        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://developer.android.com");
                // 建立intent物件並同時給予請求action與資源位置(uri)
                Intent intent = new Intent(Intent.ACTION_VIEW, uri); // 遊覽
                startActivity(intent);
                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com")));
            }
        });

        btnEditPic = (Button) findViewById(R.id.btnEditPic);
        btnEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_EDIT); // 編輯
                File file = new File("/sdcard/result.png");
                //Uri.fromFile讓File物件轉成資源位置
                //image/*代表只要是圖片檔案的資料都能處理，如.jpg, .png, .gif
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                // intent.setDataAndType(Uri.fromFile(new File("/sdcard/result.png")), "image/*");
                startActivity(intent);

            }
        });

        btnViewPic = (Button) findViewById(R.id.btnViewPic);
        btnViewPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW); // 遊覽
                File file = new File("/sdcard/result.png");
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            }
        });

        btnDialTel = (Button) findViewById(R.id.btnDialTel);
        btnDialTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel://0978000000");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri); // 撥號
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
