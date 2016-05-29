package idv.david.drawableaniex;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private ImageView ivDice;
    private TextView tvResult;
    private Button btnRollDice;
    private AnimationDrawable aniDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        ivDice = (ImageView) findViewById(R.id.ivDice);
        tvResult = (TextView) findViewById(R.id.tvResult);
        // 從程式資源中取得動畫檔，設定給ImageView物件
//        Resources res = getResources();   //Resources 資源物件
        aniDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_roll_dice);

        btnRollDice = (Button) findViewById(R.id.btnRollDice);
        btnRollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 檢查動畫是否正在播放
                if (!aniDrawable.isRunning()) {  // 判斷式否在輪撥中
                    ivDice.setImageDrawable(aniDrawable);
                    aniDrawable.start(); // 開始播放
                    btnRollDice.setText("停止");
                } else {
                    aniDrawable.stop();  // 停止播放
                    btnRollDice.setText("擲骰子");
                    int random = (int) (Math.random() * 6 + 1);  // 產生亂數
                    String result = getString(R.string.dice_result) + random;
                    tvResult.setText(result);
                    switch (random) {
                        case 1:
                            ivDice.setImageResource(R.drawable.dice01);
                            break;
                        case 2:
                            ivDice.setImageResource(R.drawable.dice02);
                            break;
                        case 3:
                            ivDice.setImageResource(R.drawable.dice03);
                            break;
                        case 4:
                            ivDice.setImageResource(R.drawable.dice04);
                            break;
                        case 5:
                            ivDice.setImageResource(R.drawable.dice05);
                            break;
                        case 6:
                            ivDice.setImageResource(R.drawable.dice06);
                            break;
                    }
                }
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
