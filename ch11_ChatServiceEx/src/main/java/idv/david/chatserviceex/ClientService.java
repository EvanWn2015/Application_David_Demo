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
import java.net.Socket;

public class ClientService extends Service {
    public final static int MESSAGE_OUT = 0;
    public final static int MESSAGE_IN = 1;
    public static final int SOCKET_CONNECT_FAIL = 2;

    private String ip;
    private int port;
    private Messenger messenger;
    private Socket socket;

    @Override
    public void onDestroy() {
        closeSocket();
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        private Intent intent;

        public ServiceBinder(Intent intent) {
            this.intent = intent;
        }

        public void createSocket() {
            if (socket == null || socket.isClosed()) {
                Bundle bundle = intent.getExtras();
                ip = bundle.getString("ip");
                port = bundle.getInt("port");
                messenger = bundle.getParcelable("messenger");
                SocketConnectTask socketConnectTask = new SocketConnectTask();
                socketConnectTask.execute();
            }
        }

        public void closeSocket() {
            ClientService.this.closeSocket();
        }

        public void sendMsg(String msg) {
            ClientService.this.sendMsg(msg);
        }

    }

    @Override
    // bindService()啓動Service會呼叫onBind()而非onStartCommand()
    public IBinder onBind(Intent intent) {
        ServiceBinder serviceBinder = new ServiceBinder(intent);
        return serviceBinder;
    }

    private class SocketConnectTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            // runClient();
            try {
                socket = new Socket(ip, port);
                ChatListener chatListener = new ChatListener();
                chatListener.start();
                // 傳送訊息測試
                // sendMsgOut("client");
            } catch (IOException e) {
                Log.e("IOException", e.toString());
                Message msg = new Message();
                msg.what = SOCKET_CONNECT_FAIL;
                Bundle bundle = new Bundle();
                bundle.putString("msg", "連線失敗");
                msg.setData(bundle);
                try {
                    messenger.send(msg);
                } catch (RemoteException e1) {
                    Log.e("RemoteException", e1.toString());
                }
            }

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

    private void closeSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    }

}
