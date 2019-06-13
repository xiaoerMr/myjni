package com.sai.myjni.socket.socket;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketModel implements BaseSocket {

    private static final String host = "192.168.0.105";
//    private static final String host = "47.94.197.110";
    private static final int prot = 8080;

    private Socket socket;

    private BufferedWriter outputStream;
    private DataInputStream inputStream;

    @Override
    public void initSocket() throws IOException {
        Log.e("--socket--SocketModel--", "----连接服务器----" + host + ":" + prot);
        socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(host, prot);
        socket.connect(socketAddress, 8 * 1000);

        socket.setTcpNoDelay(true); // 关闭 Nagle 算法
        socket.setReceiveBufferSize(1024 * 10000);
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        inputStream = new DataInputStream(socket.getInputStream());
//        isRunning = true;
        Log.e("--socket--SocketModel--", "----连接服务器--初始化完成--");

    }

    @Override
    public void socketClose() throws IOException {
        if (socket != null) {

            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (!socket.isClosed()) {
                socket.close();
            }
            socket = null;
        }
    }

    @Override
    public void socketSend(String msg) throws IOException {
        if (!TextUtils.isEmpty(msg) &&
                socket != null &&
                !socket.isClosed() &&
                outputStream != null) {


            outputStream.write(msg);
            outputStream.flush();
            Log.e("--socket--SocketModel--", "----发送数据完毕--" + msg);
        } else {
            throw new IOException("发送时为空(socket,outputStream,socket.isClosed)");
        }
    }

    @Override
    public String socketReceive() throws IOException {

        if (socket !=null && socket.isConnected() && inputStream != null) {

            byte[] buf = new byte[1024];
            int len = 0;

            len = inputStream.read(buf);

            String text = "";
            if (len > 0) {
                text = new String(buf, 0, len);
            }
            Log.e("--socket--SocketModel--", "---接受到的消息=>" + text);

            return text;
        } else {
            throw new IOException("服务器没连接");
        }
    }

    @Override
    public void reconnectionSocket() throws IOException {
        socketClose();
        initSocket();
    }

    @Override
    public boolean socketLive() {
        boolean flag ;
        if (!startPing(host)) {
            flag = false;
        }

        try {
            socketSend("");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
//        return startPing(host);
    }

    private boolean startPing(String ip) {

        boolean success = false;
        Process p = null;

        try {
            p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -W 1 " + ip);
            int status = p.waitFor();
            if (status == 0) {
                success = true;
            } else {
                success = false;
            }
        } catch (IOException e) {
            success = false;
        } catch (InterruptedException e) {
            success = false;
        } finally {
            p.destroy();
        }

        Log.e("--socket--SocketModel--", "--startPing..."+success);
        return success;
    }
}
