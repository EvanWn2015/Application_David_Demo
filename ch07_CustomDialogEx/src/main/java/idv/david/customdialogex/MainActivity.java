package idv.david.customdialogex;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Button btnLogin, btnOK, btnCancel;
    private EditText etName, etPassword;
    private Dialog myDialog;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog = new Dialog(MainActivity.this);
                myDialog.setTitle(getString(R.string.dialog_title));
                //使用者無法自行取消對話視窗，需要進行操作才行
                myDialog.setCancelable(false);
                myDialog.setContentView(R.layout.dialog_login);


                //透過myDialog.getWindow()取得這個對話視窗的Window物件
                Window dialogWindow = myDialog.getWindow();
                /*
                設定對話視窗位置：
                當参數值包含Gravity.LEFT時，對話視窗出現在左邊
                當参數值包含Gravity.RIGHT時，對話視窗出現在右邊
                當参數值包含Gravity.TOP時，對話視窗出現在上邊,
                當参數值包含Gravity.BOTTOM時，對話視窗出現在下邊
                當参數值包含Gravity.CENTER_HORIZONTAL時，對話視窗水平居中
                當参數值包含Gravity.CENTER_VERTICAL時，對話視窗垂直居中
                gravity的默認值為Gravity.CENTER，即Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL
                 */
                dialogWindow.setGravity(Gravity.CENTER);

                 /*
                設定對話視窗大小：
                呼叫getAttributes()，取得LayoutParams物件即可進行屬性設定
                相關屬性：
                x：X座標
                y：Y座標
                width：寬度
                height：高度
                alpha：透明度 (0.0 ～ 1.0)
                 */
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = 1000;
                lp.alpha = 0.5f;
                dialogWindow.setAttributes(lp);

                /*
                將對話視窗的大小依螢幕大小的百分比設置
                 */
//                WindowManager m = getWindowManager();
//                Display d = m.getDefaultDisplay(); // 取得螢幕寬、高用
//                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 獲取對話視窗當前的参數值
//                p.height = (int) (d.getHeight() * 0.6); // 高度設置為螢幕的0.6 (60%)
//                p.width = (int) (d.getWidth() * 0.95); // 寬度設置為螢幕的0.95 (95%)
//                dialogWindow.setAttributes(p);


                //取得自訂對話視窗上的所有元件都需透過myDialog才能findViewById
                btnOK = (Button) myDialog.findViewById(R.id.btnOK);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etName = (EditText) myDialog.findViewById(R.id.etName);
                        etPassword = (EditText) myDialog.findViewById(R.id.etPassword);
                        String name = etName.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();
                        tvResult.setText("輸入名稱： " + name + "\n" + "輸入密碼： " + password);
                        //關閉對話視窗
                        myDialog.cancel();
                    }
                });

                btnCancel = (Button) myDialog.findViewById(R.id.btnCancel);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvResult.setText("你按下取消鍵");
                        myDialog.cancel();
                    }
                });
                //小心！一定要記得show()
                myDialog.show(); // 結束一定要加入show
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
