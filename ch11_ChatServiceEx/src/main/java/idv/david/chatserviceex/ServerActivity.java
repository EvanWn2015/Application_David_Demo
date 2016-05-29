package idv.david.chatserviceex;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import static idv.david.chatserviceex.ServerService.MESSAGE_IN;
import static idv.david.chatserviceex.ServerService.MESSAGE_OUT;
import static idv.david.chatserviceex.ServerService.SERVER_OFF;
import static idv.david.chatserviceex.ServerService.SERVER_ON;

public class ServerActivity extends AppCompatActivity {
    private EditText etPort, etMsgOut;
    private Switch stStart;
    private TextView tvIp;
    private ListView lvMessages;
    private Handler handler;
    private ArrayAdapter<String> messageAdapter;
    private boolean isBound;
    private ServerService.ServiceBinder serviceBinder;

    private static class MsgHandler extends Handler {
        private ArrayAdapter<String> messageAdapter;
        private Activity activity;

        public MsgHandler(Activity activity, ArrayAdapter<String> messageAdapter) {
            super();
            this.activity = activity;
            this.messageAdapter = messageAdapter;
        }

        public void handleMessage(Message msg) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String timeStamp = dateFormat.format(new Date());
            Bundle bundle = null;
            String message = null;
            switch (msg.what) {
                case SERVER_ON:
                    Toast.makeText(activity, "Server starts", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case SERVER_OFF:
                    Toast.makeText(activity, "Server stops", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case MESSAGE_OUT:
                    bundle = msg.getData();
                    message = bundle.getString("msgOut") + "  " + timeStamp;
                    messageAdapter.add("○ Send: " + message);
                    break;
                case MESSAGE_IN:
                    bundle = msg.getData();
                    message = bundle.getString("msgIn") + "  " + timeStamp;
                    messageAdapter.add("★ Received: " + message);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        findViews();
    }

    private void findViews() {
        tvIp = (TextView) findViewById(R.id.tvIp);
        etPort = (EditText) findViewById(R.id.etPort);
        etMsgOut = (EditText) findViewById(R.id.etMsgOut);
        stStart = (Switch) findViewById(R.id.stStart);
        stStart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    serviceBinder.startServer();
                } else {
                    serviceBinder.closeServer();
                }
            }
        });
        // 取得WiFi資訊要設加uses-permission: ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // Formatter.formatIpAddress()因為不能格式化IPv6而被deprecated
        String serverIp = Formatter.formatIpAddress(wifiInfo.getIpAddress());
        tvIp.setText(serverIp);
        messageAdapter = new ArrayAdapter<>(this, R.layout.listview_item);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        lvMessages.setAdapter(messageAdapter);
        handler = new MsgHandler(this, messageAdapter);
    }

    @Override
    protected void onStart() {
        doBindService();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }

    public void onSendClick(View v) {
        if (stStart.isChecked()) {
            String msgOut = etMsgOut.getText().toString().trim();
            serviceBinder.sendMsg(msgOut);
            etMsgOut.setText("");
        } else {
            Toast.makeText(this, "尚未連線", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClearClick(View v) {
        messageAdapter.clear();
    }

    private void doBindService() {
        if (!isBound) {
            int port = Integer.parseInt(etPort.getText().toString().trim());
            Intent intent = new Intent(this, ServerService.class);
            Bundle bundle = new Bundle();
            bundle.putInt("port", port);
            // Messenger指向Handler而且屬於Parcelable，可以放在Bundle內傳送
            bundle.putParcelable("messenger", new Messenger(handler));
            intent.putExtras(bundle);
            // 連結intent所指定的Service
            // serviceCon是實作ServiceConnection介面的物件
            // Context.BIND_AUTO_CREATE代表只要連結到Service，就會自動建立該Servive
            // isBound代表是否與Service連結，一旦連結就設定為true
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
    }

    private void doUnbindService() {
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            serviceBinder = ((ServerService.ServiceBinder) binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();

        switch (item.getItemId()) {
            case R.id.server:
                intent.setClass(this, ServerActivity.class);
                startActivity(intent);
                break;
            case R.id.client:
                intent.setClass(this, ClientActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

}
