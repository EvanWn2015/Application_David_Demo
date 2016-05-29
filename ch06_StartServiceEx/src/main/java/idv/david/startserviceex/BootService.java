package idv.david.startserviceex;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;



public class BootService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("start", "Service已被開啟");
        i.putExtras(b);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, i, 0);
        Notification notification = new Notification.Builder(this)  // 通知欄信息
                .setTicker("Service Message")
                .setSmallIcon(R.drawable.ic_secret_notification)
                .setContentTitle("開啟完成，產生Notification！")
                .setContentText("This is content text")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(10, notification);

        return START_STICKY;  // 執行一段時間再次啟動，保持執行狀態
    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
