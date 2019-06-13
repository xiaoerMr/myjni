package com.sai.myjni.socket;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sai.myjni.R;
import com.sai.myjni.base.BaseActivity;
import com.sai.myjni.socket.socket.SocketErrorCode;
import com.sai.myjni.socket.socket.SocketListener;
import com.sai.myjni.socket.socket.SocketManager;
import com.sai.sailib.log.DLog;

import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

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

    private SocketManager socketManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_socket;
    }

    @Override
    protected void initView() {
        setBarName("webSocket 测试");
    }

    @Override
    protected void onResume() {
        super.onResume();
        socketManager = SocketManager.getInstance();
        //接受消息
        copyMsg();
    }

    private void copyMsg() {
        socketManager.receiveMsg(new SocketListener() {
            @Override
            public void onSuccess(final String data) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(data);
                    }
                });

            }

            @Override
            public void onFail(final int failMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(SocketErrorCode.getErrorMsg(failMsg));
                    }
                });
            }
        });
    }

    @OnClick({R.id.start, R.id.send, R.id.end,R.id.state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:

                break;

            case R.id.send:
                Random random = new Random();
                socketManager.sendMsg("我是客户端" + random.nextInt(), new SocketListener() {
                    @Override
                    public void onSuccess(final String data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }

                    @Override
                    public void onFail(int failMsg) {
                        DLog.e(SocketErrorCode.getErrorMsg(failMsg));
                    }
                });
                break;

            case R.id.end:
//                text.setText(startPing("192.168.0.105") +"");
                socketManager.closeSocket(new SocketListener() {
                    @Override
                    public void onSuccess(String data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("已断开");
                            }
                        });
                    }

                    @Override
                    public void onFail(int failcode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("断开失败");
                            }
                        });
                    }
                });
                break;
            case R.id.state:
                socketManager.reConSocket(new SocketListener() {
                    @Override
                    public void onSuccess(String data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("重新连接成功");
                                copyMsg();
                            }
                        });
                    }

                    @Override
                    public void onFail(final int failcode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(SocketErrorCode.getErrorMsg(failcode));
                            }
                        });
                    }
                });
                break;
        }
    }

    private boolean startPing(String ip){
        Log.e("Ping", "startPing...");
        boolean success=false;
        Process p =null;

        try {
            p = Runtime.getRuntime().exec("ping -c 1 -i 0.2 -W 1 " +ip);
            int status = p.waitFor();
            if (status == 0) {
                success=true;
            } else {
                success=false;
            }
        } catch (IOException e) {
            success=false;
        } catch (InterruptedException e) {
            success=false;
        }finally{
            p.destroy();
        }

        return success;
    }
}
