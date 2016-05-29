package idv.hangermo.p2pserverex;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity {
    private EditText etPort;
    private TextView tvMessages;
    private ServerStartTask serverStartTask;
    private ServerSocket serverSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        etPort = (EditText) findViewById(R.id.etPort);
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
        // progress dialog starts
        setProgressBarIndeterminateVisibility(true);
        serverStartTask = new ServerStartTask();
        serverStartTask.execute(port);
        Toast.makeText(this, "Server starts", Toast.LENGTH_SHORT).show();

    }

    public void onStopClick(View v) {
        try {
            serverSocket.close();
            Toast.makeText(this, "Server stops", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
        // progress dialog stops
        setProgressBarIndeterminateVisibility(false);
    }

    public void onClearClick(View v) {
        tvMessages.setText("");
    }

    private void serverStart(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new SocketThread(socket);
                socketThread.start();
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

    private class ServerStartTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            int port = params[0];
            serverStart(port);
            return null;
        }
    }

    private class SocketThread extends Thread {
        private Socket socket;

        public SocketThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));

                // receive a message
                String incomingMsg = in.readLine();
                Log.i("TcpServer", "Client: " + incomingMsg + "\n");
                handleMsg("Client: " + incomingMsg + "\n");

                // send a message
                String outgoingMsg = "Hello, client, I am server!\n";
                out.write(outgoingMsg);
                out.flush();
                Log.i("TcpServer", "Server: " + outgoingMsg);
                handleMsg("Server: " + outgoingMsg);
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                }
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

}
