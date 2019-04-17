package com.vexcellent.myjni.socket;

import com.sai.sailib.log.DLog;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class JavaWebSocketUtils {

    private static JavaWebSocketUtils utils;
    private static String  url = "ws://192.168.31.165:8080";

    private SocketClient client;

    public static JavaWebSocketUtils getInstance() {

        if (null == utils) {
            synchronized (JavaWebSocketUtils.class) {
                if (null == utils) {
                    utils = new JavaWebSocketUtils();
                }
            }
        }
        return utils;
    }

    public void Bulide() {
        try {
            URI uri = new URI(url);
            client = new SocketClient(uri){
                @Override
                public void onMessage(String message) {
                    super.onMessage(message);
                    DLog.e("收到消息=="+message);
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    //连接
    public void connect(){

        new Thread() {
            @Override
            public void run() {
                    if (null != client) {
                        try {
                            client.connectBlocking();
                            DLog.e("创建连接");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            DLog.e("创建连接失败");
                        }
                    }
            }
        }.start();

    }
    public void sendMsg(String msg) {
        if (null != client ) {
            client.send(msg);
        }
    }
    public void disconnect(){
        if (null != client) {
            client.close();
        }
    }


    class  SocketClient extends WebSocketClient {

        public SocketClient(URI serverURI) {
            super(serverURI);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            DLog.e("创建连接="+handshakedata.getHttpStatusMessage());
        }

        @Override
        public void onMessage(String message) {
            DLog.e("创建连接,收到消息="+message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            DLog.e("创建连接,关闭="+reason);
        }

        @Override
        public void onError(Exception ex) {
            DLog.e("创建连接失败="+ex.getMessage());
        }
    }
}
