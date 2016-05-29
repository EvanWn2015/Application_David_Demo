package idv.hangermo.p2pclientex;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;


public class MainActivity extends AppCompatActivity {
    private EditText etIp, etPort;
    private TextView tvMessages;
    private ProgressDialog progressDialog;
    private SocketConnectTask connectTask;
    private Socket socket;
    private int port;
    private String ip;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    @Override
    protected void onPause() {
        if (connectTask != null) {
            connectTask.cancel(true);
        }
        super.onPause();
    }

    private void findViews() {
        etIp = (EditText) findViewById(R.id.etIp);
        etPort = (EditText) findViewById(R.id.etPort);
        tvMessages = (TextView) findViewById(R.id.tvMessages);
    }

    public void onConnectClick(View v) {
        ip = etIp.getText().toString().trim();
        port = Integer.parseInt(etPort.getText().toString().trim());
        connectTask = new SocketConnectTask();
        connectTask.execute();
    }

    public void onClearClick(View v) {
        tvMessages.setText("");
    }

    private void runTcpClient() {
        try {
            socket = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 輸出的文字加上"\n"是因為server端用BufferedReader.readLine()必須讀到換行字元才會停止
            String outMsg = "Hi, server, I am client!\n";
            out.write(outMsg);
            out.flush();
            Log.i("TcpClient", "Client: " + outMsg);
            handleMsg("Client: " + outMsg);
            // accept server response
            String inMsg = in.readLine();
            Log.i("TcpClient", "Server: " + inMsg + "\n");
            handleMsg("Server: " + inMsg + "\n");
            // close connection
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    private class SocketConnectTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            runTcpClient();
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog.cancel();
        }
    }


}
