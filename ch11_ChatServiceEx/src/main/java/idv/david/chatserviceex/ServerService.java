package idv.david.chatserviceex;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Service {
    public final static int SERVER_ON = 0;
    public final static int SERVER_OFF = 1;
    public final static int MESSAGE_OUT = 2;
    public final static int MESSAGE_IN = 3;

    private ServerSocket serverSocket;
    private int port;
    private Messenger messenger;
    private Socket socket;

    @Override
    public void onDestroy() {
        closeServer();
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        private Intent intent;

        public ServiceBinder(Intent intent) {
            this.intent = intent;
        }

        public void startServer() {
            if (serverSocket == null || serverSocket.isClosed()) {
                Bundle bundle = intent.getExtras();
                port = bundle.getInt("port");
                messenger = bundle.getParcelable("messenger");
                StartServerTask startServerTask = new StartServerTask();
                startServerTask.execute();
            }
        }

        public void closeServer() {
            ServerService.this.closeServer();
        }

        public void sendMsg(String msg) {
            ServerService.this.sendMsg(msg);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        ServiceBinder serviceBinder = new ServiceBinder(intent);
        return serviceBinder;
    }

    private class StartServerTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            runServer();
            return null;
        }
    }

    private class ChatListener extends Thread {

        @Override
        public void run() {
            Message msg = null;
            Bundle bundle = null;
            try {
                while (true) {
                    String line = null;
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    if ((line = in.readLine()) != null) {
                        String msgIn = line;
                        msg = new Message();
                        msg.what = MESSAGE_IN;
                        bundle = new Bundle();
                        bundle.putString("msgIn", msgIn);
                        msg.setData(bundle);
                        messenger.send(msg);
                        // 傳送測試訊息
                        // sendMsgOut("Server");
                    }
                }
            } catch (RemoteException e) {
                Log.e("RemoteException", e.toString());
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    }

    private void sendMsg(String msgOut) {
        if (socket != null && !socket.isClosed()) {
            Message msg = null;
            Bundle bundle = null;
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));
                // 輸出的文字加上"\n"是因為接收端用BufferedReader.readLine()必須讀到換行字元才會停止
                out.write(msgOut + "\n");
                out.flush();
                msg = new Message();
                msg.what = MESSAGE_OUT;
                bundle = new Bundle();
                bundle.putString("msgOut", msgOut);
                msg.setData(bundle);
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.e("RemoteException", e.toString());
            } catch (IOException e) {
                Log.e("IOException", e.toString());
                try {
                    if (socket != null) {
                        socket.close();
                        Toast.makeText(this, "Socket is closed!",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e1) {
                    Log.e("IOException", e1.toString());
                }
            }
        } else {
            Toast.makeText(this, "Socket is closed!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void runServer() {
        try {
            serverSocket = new ServerSocket(port);
            Message msg = new Message();
            // 傳送SERVER_ON訊息到Activity
            msg.what = SERVER_ON;
            messenger.send(msg);
            while (true) {
                socket = serverSocket.accept();
                ChatListener chatListener = new ChatListener();
                chatListener.start();
            }
        } catch (RemoteException e) {
            Log.e("RemoteException", e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
    }

    private void closeServer() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            Message msg = new Message();
            // 傳送SERVER_OFF訊息到Activity
            msg.what = SERVER_OFF;
            messenger.send(msg);
        } catch (RemoteException e) {
            Log.e("RemoteException", e.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }
    }

}
