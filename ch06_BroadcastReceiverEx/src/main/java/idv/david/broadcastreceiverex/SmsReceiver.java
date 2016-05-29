package idv.david.broadcastreceiverex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.DateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String smsContent = "";
        String sender = "";
        Date date = null;
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus"); // bundle接收到的內容"pdus"，pdus轉換成Object[]
            SmsMessage[] smsMessages = new SmsMessage[pdus.length]; // 建立實體長度為接收到的pdus.長度
            for (int i = 0; i < pdus.length; i++) {
                smsMessages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                smsContent += smsMessages[i].getDisplayMessageBody();
            }
            sender = smsMessages[0].getDisplayOriginatingAddress(); // 取出第一筆資料時候的來源（號碼）
            date = new Date(smsMessages[0].getTimestampMillis()); // 取出第一筆資料時候的時間
        }

        Intent i = new Intent(context, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("type", "sms");
        b.putString("sender", sender);
        b.putString("smsContent", smsContent);
        //將Date物件轉成String資料類型
        DateFormat df = DateFormat.getInstance();
        String strDate = df.format(date);
        b.putString("date", strDate);
        i.putExtras(b);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
