package idv.david.assetsex;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {
    private Button btnFonts, btnReturn;
    private TextView tvAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        readTXT();
    }

    private void findViews() {
        tvAsset = (TextView)findViewById(R.id.tvAsset);
        btnFonts = (Button)findViewById(R.id.btnFonts);
        btnFonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface font = Typeface.createFromAsset(getAssets(), "fonts/tradegothicltstdbdcn20.ttf");
                tvAsset.setTypeface(font);
            }
        });

        btnReturn = (Button)findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Typeface.DEFAULT即為系統預設字型
                Typeface defaultFont = Typeface.DEFAULT;
                tvAsset.setTypeface(defaultFont);
            }
        });
    }

    private void readTXT() {
        BufferedReader br = null;
        try {
            InputStream is = getAssets().open("android_intro.txt");
            // 藉由InputStreamReader水管將InputStream與BufferedReader串接在一起
            br = new BufferedReader(new InputStreamReader(is));
            // 使用StringBuilder做字串append以節省記憶體使用
            StringBuilder sb = new StringBuilder();
            String text;
            // BufferedReader才有的功能"readLine()"
            while ((text = br.readLine()) != null) {
                sb.append(text);
                sb.append("\n");
            }
            tvAsset.setText(sb);
        } catch (IOException ie) {
            Log.e("MainActivity", ie.toString());
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException ie) {
                    Log.e("MainActivity", ie.toString());
                }
            }
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
