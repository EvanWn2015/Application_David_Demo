package idv.david.startserviceex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "開啟完成，準備啟動Service", Toast.LENGTH_SHORT)
                .show();
        //透過intent物件前往BootService
        Intent i = new Intent(context, BootService.class);
        context.startService(i);
    }
}
