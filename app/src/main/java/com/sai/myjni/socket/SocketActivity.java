package com.sai.myjni.socket;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class SocketActivity extends BaseActivity {

    @BindView(R.id.start)
    Button start;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.end)
    Button end;
    @BindView(R.id.state)
    Button state;
    @BindView(R.id.text)
    TextView text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socket;
    }

    @Override
    protected void initView() {
        setBarName("webSocket 测试");
        service();
    }


    @OnClick({R.id.start, R.id.send, R.id.end,R.id.state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
//                WebSocketUtils.getInstance().connect();
//                JavaWebSocketUtils.getInstance().Bulide();
//                JavaWebSocketUtils.getInstance().connect();
                OkHttpWebSocketUtils.getInstance().connect();
                break;

            case R.id.send:
//                WebSocketUtils.getInstance().sendMessage("我是客户端");
//                JavaWebSocketUtils.getInstance().sendMsg("我是客户端");
                String sendMsg = OkHttpWebSocketUtils.getInstance().sendMsg("我是客户端");
                text.setText(sendMsg);
                break;

            case R.id.end:
//                WebSocketUtils.getInstance().disconnect();
//                JavaWebSocketUtils.getInstance().disconnect();
                break;
            case R.id.state:
                String status = WebSocketUtils.getInstance().getSocketStatus();
                text.setText(status);
                break;
        }
    }

    private void service(){
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().withWebSocketUpgrade(new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("server onOpen");
                System.out.println("server request header:" + response.request().headers());
                System.out.println("server response header:" + response.headers());
                System.out.println("server response:" + response);
            }
            @Override
            public void onMessage(WebSocket webSocket, String string) {
                System.out.println("server onMessage");
                System.out.println("server message:" + string);
//                //接受到5条信息后，关闭消息定时发送器
//                if(msgCount == 5){
//                    mTimer.cancel();
//                    webSocket.close(1000, "close by server");
//                    return;
//                }
                webSocket.send("response-" + string);
            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("server onClosing");
                System.out.println("server code:" + code + " reason:" + reason);
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("server onClosed");
                System.out.println("code:" + code + " reason:" + reason);
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                //出现异常会进入此回调
                System.out.println("server onFailure");
                System.out.println("throwable:" + t);
                System.out.println("response:" + response);
            }
        }));
    }

}
