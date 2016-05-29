package idv.david.notificationex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private final static int NOTIFICATION_ID = 100; // 自定義ID
    private Button btnSend, btnCancel;
    private NotificationManager notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在Activity創建同時就取得NotificationManager物件
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); // 取得系統服務
        findViews();
    }

    private void findViews() {
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //資料放入bundle物件後再放進intent物件
                String title = getString(R.string.title);
                String content = getString(R.string.content);
                Intent intent = new Intent(MainActivity.this, NotiActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("content", content);
                intent.putExtras(bundle);
                /*
                   Intent指定好要幹嘛後，就去做了，如startActivity(intent);
                   而PendingIntent則是先把某個Intent包好，以後再去執行Intent要幹嘛
                   暫時凍結Activity，FLAG_UPDATE_CURRENT更新目前Activity
                 */
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this
                        , 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //

                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // 取得系統默認的通知聲

                Notification notification = new Notification.Builder(MainActivity.this) // 注意寫法  沒有';'一路向下寫 最後.build();結束
                        //狀態列的文字
                        .setTicker(getString(R.string.ticker))
                        //訊息面板的標題
                        .setContentTitle(title)
                        //訊息面板的內容文字
                        .setContentText(content)
                        //訊息的圖示
                        .setSmallIcon(R.drawable.ic_secret_notification)
                        //點擊後會自動移除狀態列上的通知訊息
                        .setAutoCancel(true)
                        //等使用者點了之後才會開啟指定的Activity
                        .setContentIntent(pendingIntent)
                        //加入聲音
                        .setSound(soundUri)
                        //加入狀態列下拉後的進一步操作
                        .addAction(R.drawable.ic_secret_notification, "View", pendingIntent) // 5.0後的進階通知欄下拉功能功能，加入動作
                        .addAction(0, "Remind", pendingIntent)
                        .build();
                //呼叫notify()送出通知訊息
                notificationManager.notify(NOTIFICATION_ID, notification);
                // notificationManager.notify(100, notification);


            }
        });

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //呼叫cancel()並給予指定的notification ID即可取消
                notificationManager.cancel(NOTIFICATION_ID);
                // notificationManager.cancel(100);
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
