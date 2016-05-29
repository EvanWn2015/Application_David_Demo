package idv.david.broadcastsenderex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Broadcast_2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Broadcast_2收到！", Toast.LENGTH_SHORT)
                .show();
    }
}
