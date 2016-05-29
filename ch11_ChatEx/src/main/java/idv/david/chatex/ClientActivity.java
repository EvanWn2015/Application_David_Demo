package idv.david.chatex;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

public class ClientActivity extends AppCompatActivity {
    private EditText etIp, etPort, etOutMsg;
    private TextView tvMessages;
    private ProgressDialog progressDialog;
    private SocketConnectTask connectTask;
    private Socket socket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
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
        etOutMsg = (EditText) findViewById(R.id.etOutMsg);
        tvMessages = (TextView) findViewById(R.id.tvMessages);
    }

    public void onConnectClick(View v) {
        String ip = etIp.getText().toString().trim();
        String port = etPort.getText().toString().trim();
        connectTask = new SocketConnectTask();
        connectTask.execute(ip, port);
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
            progressDialog = new ProgressDialog(ClientActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String ip = params[0];
                int port = Integer.parseInt(params[1]);
                socket = new Socket(ip, port);
                ChatListener chatListener = new ChatListener();
                chatListener.start();
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog.cancel();
        }
    }

    private class ChatListener extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String line = null;
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
