package idv.david.servicebindex;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service {
    private final IBinder binder = new ServiceBinder();

    public class ServiceBinder extends Binder {
        // 呼叫getService()可以取得Service，
        // 這樣一來client就可以與Service互動
        MusicService getService() {
            return MusicService.this;
        }
    }

    // 假設MusicService提供音樂播放服務
    public String play() {
        String msg = getString(R.string.msg_musicPlay);
        return msg;
    }

    public String stop() {
        String msg = getString(R.string.msg_musicStop);
        return msg;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    // 為了讓連結Service的client可以取得IBinder物件
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        return binder;
    }

    @Override
    // 沒有任何client連結Service時會呼叫此方法
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
