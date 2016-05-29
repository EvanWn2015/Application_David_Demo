package idv.david.alertdialogex;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onExitClick(View view) {
        AlertFragment alertFragment = new AlertFragment();
        FragmentManager fm = getSupportFragmentManager(); // 取得FragmentManager權限
        alertFragment.show(fm, "alert"); // (權限， "標籤")
    }

    public static class AlertFragment extends DialogFragment implements DialogInterface.OnClickListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    //設定圖示
                    .setIcon(R.drawable.alert) // 5.0後沒顯示
                    //設定訊息內容
                    .setMessage(R.string.message_alert)
                    //設定確認鍵 (positive用於確認)
                    .setPositiveButton(R.string.text_btYes, this) // ("顯示內容", 位置)
                    //設定取消鍵 (negative用於取消)
                    .setNegativeButton(R.string.text_btNo, this)
                    // .setNeutralButton() // 中立的
                    .create();
            return alertDialog;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE: // 正向
                    getActivity().finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE: // 消極
                    dialog.cancel();
                    break;
                default:
                    break;
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
