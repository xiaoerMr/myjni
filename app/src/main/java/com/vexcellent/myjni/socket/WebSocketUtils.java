package com.vexcellent.myjni.socket;



import com.sai.sailib.log.DLog;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class WebSocketUtils{

    private static final int FRAME_QUEUE_SIZE = 5;
    private static final int DEFAULT_SOCKET_OUTTIME = 3000;
    private static final int DEFAULT_SOCKET_RECONNECTINTERVAL = 3000;

    private static WebSocketUtils utils;
    private static final int port = 9006;
    private static String  url = "ws://192.168.31.165:8080";

    private static WebSocketFactory factory;
    private WebSocket mWebSocket;
    private ConnectStatus mConnectStatus;
    private TimerTask mReconnectTimerTask;//定时任务
    private Timer mReconnectTimer = new Timer();//定时任务
    private WebSocketListener mWebSocketListener;


    public static WebSocketUtils getInstance() {

        if (utils == null) {
            synchronized (WebSocketUtils.class){

                if (utils == null) {
                    utils = new WebSocketUtils();
                    factory = new WebSocketFactory().setConnectionTimeout(DEFAULT_SOCKET_OUTTIME);
                }
            }
        }
        return utils;
    }

    //连接
    public void connect(){
        try {
            mWebSocket = factory.createSocket(url)
                    .setFrameQueueSize(FRAME_QUEUE_SIZE)//设置帧队列最大值为5
                    .setMissingCloseFrameAllowed(false)//设置不允许服务端关闭连接却未发送关闭帧
                    .addListener(new NVWebSocketListener())
                    .connectAsynchronously();  // 这里我改成了同步调用 异步调用请使用connectAsynchronously()


            if (null != mWebSocket && mWebSocket.isOpen() ) {
                DLog.e("------连接成功---1---");
                setConnectStatus(ConnectStatus.CONNECTING);
            }else {
                setConnectStatus(ConnectStatus.CONNECT_FAIL);
                DLog.e("------连接失败---1---");
            }

        } catch (IOException e) {
            setConnectStatus(ConnectStatus.CONNECT_FAIL);
            DLog.e("------连接失败------" +e.getMessage());
            e.printStackTrace();
        }
    }
    // 客户端像服务器发送消息
    public void sendMessage(String msg) {
        DLog.e("-----发送-------"+msg);
        mWebSocket.sendText(msg);
    }

    //重新连接
    public void reconnect() {
        if (mWebSocket != null && !mWebSocket.isOpen() && getConnectStatus() != ConnectStatus.CONNECTING) {
            mReconnectTimerTask = new TimerTask() {
                @Override
                public void run() {
                    DLog.e("-----断开-重连-------");
                    connect();
                }
            };
            mReconnectTimer.schedule(mReconnectTimerTask, DEFAULT_SOCKET_RECONNECTINTERVAL);
        }
    }

    //断开连接
    public void disconnect() {
        if (mWebSocket != null) {
            mWebSocket.disconnect();
        }
        DLog.e("-----断开-------");
        setConnectStatus(ConnectStatus.CONNECT_DISCONNECT);
    }

    //设置连接状态
    private void setConnectStatus(ConnectStatus connectStatus) {
        mConnectStatus = connectStatus;
    }
    //获取连接状态
    public String getSocketStatus(){
        if (null == mWebSocket) {
            return "mWebSocket 为空";
        }
        if (mWebSocket.isOpen()) {
            return "mWebSocket 可用";
        }else {
            return "mWebSocket 不可用";
        }
    }
    public ConnectStatus getConnectStatus() {
        return mConnectStatus;
    }
    //连接状态值
    public enum ConnectStatus {
        CONNECT_DISCONNECT,// 断开连接
        CONNECT_SUCCESS,//连接成功
        CONNECT_FAIL,//连接失败
        CONNECTING//正在连接
    }

    public void setWebSocketListener(WebSocketListener webSocketListener) {
        mWebSocketListener = webSocketListener;
    }

    //监听事件
    class NVWebSocketListener extends WebSocketAdapter {

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            super.onConnected(websocket, headers);
            DLog.e("------连接成功------");
            setConnectStatus(ConnectStatus.CONNECT_SUCCESS);
            if (mWebSocketListener != null) {
                mWebSocketListener.onConnected(websocket,headers);
            }
        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
            super.onConnectError(websocket, exception);
            DLog.e("------连接错误------"+exception.getError());
            setConnectStatus(ConnectStatus.CONNECT_FAIL);
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
            DLog.e("------关闭---");
            setConnectStatus(ConnectStatus.CONNECT_DISCONNECT);
            reconnect();

        }

        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            super.onTextMessage(websocket, text);
            DLog.e(text);
            if (mWebSocketListener != null) {
                mWebSocketListener.onTextMessage(websocket,text);
            }
        }
    }
}
