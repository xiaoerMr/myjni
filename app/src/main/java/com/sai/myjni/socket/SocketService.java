package com.sai.myjni.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sai.sailib.log.DLog;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketService extends Service {

   public static final int Msg_Service_Socket_ = 1;
    private static final String host = "192.168.0.105";
    private static final int prot = 8080;

    private Socket socket;

    private BufferedWriter outputStream;
    private DataInputStream inputStream;
    private boolean isRunning,isConnected;


    private void reConnectSocket() throws IOException {
        if (socket == null) {
            connectSocket();
        }else {
            if (socket.isClosed() ) {
                close();
                connectSocket();
            }
        }
    }
    private void connectSocket() throws IOException {
        socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(host, prot);
        socket.connect(socketAddress, 4 * 1000);
        socket.setTcpNoDelay(true); // 关闭 Nagle 算法
        socket.setReceiveBufferSize(1024 * 10000);
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inputStream = new DataInputStream(socket.getInputStream());
        isRunning = true;
        DLog.e("连接服务器");
//        if (mReceiveListener != null) {
//            mReceiveListener.onConnected(this);
//        }
    }

    //接受消息 receive
    private void receiveMsg(){
        if (socket.isConnected() && inputStream != null) {
            if (isConnected == false) {
                isConnected = true;
//                if (mReceiveListener != null) {
//                    mReceiveListener.onConnected(this);
//                }
//                SendBroadCastUtil.sendNetworkStateBroadcast(mContext, isConnected);
            }
            DLog.e( "连接服务器等待接受消息");

            byte[] buf = new byte[1024];
            int len = 0;

            try {
                len = inputStream.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String text = "";
            if(len > 0){
                 text = new String(buf, 0, len);
            }

            Message message = new Message();
            message.what = msg_receive;
            message.obj = text;
            handlerSocket.sendMessage(message);
            DLog.e( "接受到的消息=>"+text);
        }else {
            try {
                connectSocket();
                receiveMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //发送数据
    public void sendData(String data) {
        Message message = new Message();
        message.what = msg_send;
        message.obj = data;
        handlerSocket.sendMessage(message);
    }

    public void close(){
        if (socket != null && !socket.isClosed() ) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static final int msg_send =0x111;
    private static final int msg_receive =0x112;
    Handler handlerSocket = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case msg_send:
                   final String data = (String) msg.obj;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(data)  &&
                                    socket != null && !socket.isClosed()&&
                                    outputStream != null  ) {
                                try {
                                    socketSend(data);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                try {
                                    reConnectSocket();
                                    socketSend(data);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }).start();

                    break;

                case msg_receive:
                    final String s = (String) msg.obj;
                    if (listener != null) {
                        listener.onReceiveMsg(s);
                    }
                    DLog.e( listener ==null?"null":"notnull"+"接受到的消息=>"+s);

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private void socketSend(String data) throws IOException {
        DLog.e("发送数据="+data);
        outputStream.write(data);
        outputStream.flush();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket == null) {
                        connectSocket();
                    }
                    while ( socket != null && socket.isConnected() ){
                        receiveMsg();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public SocketService getService(){
            return SocketService.this;
        }
    }

    interface receiveMsgListener{
        void onReceiveMsg(String msg);
    }

    private receiveMsgListener listener;

    public void setListener(receiveMsgListener listener) {
        this.listener = listener;
    }
}
