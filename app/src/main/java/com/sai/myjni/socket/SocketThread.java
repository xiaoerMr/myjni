package com.sai.myjni.socket;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sai.myjni.livedata.SaiLiveDateBus;
import com.sai.sailib.log.DLog;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketThread extends Thread {

    private static final String host = "192.168.0.105";
    private static final int prot = 8080;
    private boolean isRunning,isConnected;

    private Socket socket;

    private BufferedWriter outputStream;
    private DataInputStream inputStream;

    @Override
    public void run() {
        super.run();

        initSocket();

    }




    private void initSocket(){
        try {
            if (socket == null) {
                connectSocket();
            }
            sendData("-----测试数据-----");
            receiveMsg();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        SaiLiveDateBus.SingletonHolder()
//                .with("SocketService_send", String.class)
//                .observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//
//                    }
//                });
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

            String text = new String(buf, 0, len);
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
        if (!TextUtils.isEmpty(data)  && outputStream != null) {
            try {
                DLog.e("发送数据="+data);
                outputStream.write(data);
                outputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        if (socket != null && !socket.isClosed() ) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
