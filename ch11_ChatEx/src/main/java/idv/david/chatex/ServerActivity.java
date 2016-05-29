package idv.david.chatex;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

public class ServerActivity extends AppCompatActivity {
    private EditText etPort, etOutMsg;
    private TextView tvMessages;
    private ServerStartTask serverStartTask;
    private ServerSocket serverSocket;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        findViews();
    }

    @Override
    protected void onPause() {
        serverClose();
        super.onPause();
    }

    private void findViews() {
        etPort = (EditText) findViewById(R.id.etPort);
        etOutMsg = (EditText) findViewById(R.id.etOutMsg);
        tvMessages = (TextView) findViewById(R.id.tvMessages);
        // 取得WiFi資訊要設加uses-permission: ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // Formatter.formatIpAddress()因為不能格式化IPv6而被deprecated
        String serverIp = Formatter.formatIpAddress(wifiInfo.getIpAddress());
        tvMessages.setText("Server IP: " + serverIp + "\n");
    }

    public void onStartClick(View v) {
        int port = Integer.parseInt(etPort.getText().toString().trim());
        if (serverSocket != null && !serverSocket.isClosed()) {
            Toast.makeText(this, "Server is already started",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // progress dialog starts
        setProgressBarIndeterminateVisibility(true);
        serverStartTask = new ServerStartTask();
        serverStartTask.execute(port);
        Toast.makeText(this, "Server starts", Toast.LENGTH_SHORT).show();
    }

    public void onStopClick(View v) {
        serverClose();
    }

    public void onSendClick(View v) {
        if (socket != null && !socket.isClosed()) {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));
                String outMsg = etOutMsg.getText() + "\n";
                out.write(outMsg);
                out.flush();
                handleMsg("○ Send: " + outMsg);
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    }

    public void onClearClick(View v) {
        tvMessages.setText("");
    }

    private void serverStart(int port) {
        try {
            if (serverSocket == null || serverSocket.isClosed()) {
                serverSocket = new ServerSocket(port);
            }
            while (true) {
                socket = serverSocket.accept();
                ChatListener chatListener = new ChatListener();
                chatListener.start();
            }
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                }
            }
        }
    }

    private void serverClose() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                socket.close();
                Toast.makeText(this, "Server stops", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        // progress dialog stops
        setProgressBarIndeterminateVisibility(false);
    }

    private class ServerStartTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            int port = params[0];
            serverStart(port);
            return null;
        }
    }

    private class ChatListener extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String line = null;
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    if ((line = in.readLine()) != null) {
                        String inMsg = line;
                        // 不可呼叫close，會導致socket關閉
                        // in.close();
                        handleMsg("● Received: " + inMsg + "\n");
                    }
                }
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    }


    private void handleMsg(final String msg) {
        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                tvMessages.append(msg + "  " + dateFormat.format(new Date())
                        + "\n");
            }
        });
    }

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

    @Override
    protected void onDestroy() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        super.onDestroy();
    }

}
