package idv.david.broadcastreceiverex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class PhoneReceiver extends BroadcastReceiver{ // 繼承 BroadcastReceiver
    @Override
    public void onReceive(Context context, Intent intent) { // 改寫onReceive(context位置, intent內容)
        String incomePhone = "";
        String phoneState = "";
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            phoneState = bundle.getString(TelephonyManager.EXTRA_STATE);
        }

        if(phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) { // 來電中
            incomePhone = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Intent i = new Intent(context, MainActivity.class); // new Intent(context現在位置到, MainActivity)
            Bundle b = new Bundle();
            b.putString("type", "phone"); // 建立來判斷使用
            b.putString("incomePhone", incomePhone);
            b.putString("phoneState", phoneState);
            i.putExtras(b);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // 產生新的畫面
            context.startActivity(i); // 這個一執行就動作(i)
        }
    }
}
