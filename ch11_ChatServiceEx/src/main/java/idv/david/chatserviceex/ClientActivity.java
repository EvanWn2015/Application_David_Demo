package idv.david.chatserviceex;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import static idv.david.chatserviceex.ClientService.MESSAGE_IN;
import static idv.david.chatserviceex.ClientService.MESSAGE_OUT;
import static idv.david.chatserviceex.ClientService.SOCKET_CONNECT_FAIL;

public class ClientActivity extends AppCompatActivity {
    private EditText etIp, etPort, etMsgOut;
    private ListView lvMessages;
    private ArrayAdapter<String> messageAdapter;
    private Handler handler;
    private boolean isBound;
    private ClientService.ServiceBinder serviceBinder;

    private static class MsgHandler extends Handler {
        ArrayAdapter<String> messageAdapter;

        public MsgHandler(ArrayAdapter<String> messageAdapter) {
            super();
            this.messageAdapter = messageAdapter;
        }

        public void handleMessage(Message msg) {
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            String timeStamp = dateFormat.format(new Date());
            Bundle bundle = null;
            String message = null;
            switch (msg.what) {
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
                case SOCKET_CONNECT_FAIL:
                    bundle = msg.getData();
                    message = bundle.getString("msg");
                    messageAdapter.add("※ " + message + " ※ " + timeStamp);
                    break;
            }
        }
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        findViews();
    }

    private void findViews() {
        etIp = (EditText) findViewById(R.id.etIp);
        etPort = (EditText) findViewById(R.id.etPort);
        etMsgOut = (EditText) findViewById(R.id.etMsgOut);
        messageAdapter = new ArrayAdapter<>(this, R.layout.listview_item);
        lvMessages = (ListView) findViewById(R.id.lvMessages);
        lvMessages.setAdapter(messageAdapter);
        handler = new MsgHandler(messageAdapter);
    }

    public void onConnectClick(View view) {
        doBindService();
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        super.onDestroy();
    }

    public void onSendClick(View v) {
        if (serviceBinder != null) {
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
            String ip = etIp.getText().toString().trim();
            int port = Integer.parseInt(etPort.getText().toString().trim());
            Log.e("Client", ip);
            Log.e("Client", Integer.toString(port));
            Intent intent = new Intent(this, ClientService.class);
            Bundle bundle = new Bundle();
            bundle.putString("ip", ip);
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
            serviceBinder.closeSocket();
            unbindService(serviceConnection);
            isBound = false;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            serviceBinder = ((ClientService.ServiceBinder) binder);
            Log.e("Client", "Connect");
            serviceBinder.createSocket();
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
