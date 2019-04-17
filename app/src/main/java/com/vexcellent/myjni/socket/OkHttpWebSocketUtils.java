package com.vexcellent.myjni.socket;

import com.dianxiaoer.dutillibrary.log.DLog;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class OkHttpWebSocketUtils {
    private static String  url = "ws://192.168.31.165:8080";

    private static OkHttpWebSocketUtils utils;
    private WebSocket mWebSocket;

    public static OkHttpWebSocketUtils getInstance() {

        if (utils == null) {
            synchronized (OkHttpWebSocketUtils.class){

                if (utils == null) {
                    utils = new OkHttpWebSocketUtils();
                }
            }
        }
        return utils;
    }


    public void connect(){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        //构造request对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //建立连接
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                mWebSocket = webSocket;
                DLog.e("连接成功");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                DLog.e("连接成功,接受消息="+text);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                DLog.e("连接关闭,原因="+reason);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                DLog.e("连接正在关闭,原因="+reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                DLog.e("连接失败,Throwable="+t.toString());
            }
        });
    }

    public String sendMsg(String msg){
        boolean isSuccessed = mWebSocket.send(msg);
        return "发送:" +isSuccessed;
    }

}
