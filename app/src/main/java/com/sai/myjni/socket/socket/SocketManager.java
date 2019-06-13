package com.sai.myjni.socket.socket;

import android.text.TextUtils;
import android.util.Log;

import com.sai.sailib.log.DLog;
import com.sai.sailib.time.TimeUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketManager {

    private static ScheduledExecutorService pool;
    private static SocketModel model;
    private static SocketManager manager;

    public static SocketManager getInstance() {
        if (manager == null) {
            synchronized (SocketManager.class) {
                manager = new SocketManager();
            }
        }
        return manager;
    }

    private SocketManager() {
        if (pool == null) {
            pool = Executors.newScheduledThreadPool(2);
            model = new SocketModel();

            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        model.initSocket();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("--socket--SocketManager--", "---连接服务器失败--- ");
                    }
                }
            });
        }
    }

    public void sendMsg(final String msg, final SocketListener listener) {

        pool.execute(new Runnable() {
            @Override
            public void run() {
                if (model.socketLive()) {
                    try {
                        Log.e("--socket--SocketManager--", "---发送消息---");

                        model.socketSend(msg);
                        listener.onSuccess("发送成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                        listener.onFail(SocketErrorCode.code_send_fail);
                    }
                } else {
                    Log.e("--socket--SocketManager--", "---未连接服务器");
                    listener.onFail(SocketErrorCode.code_connect_die);
                }
            }
        });
    }

    //等待接受数
    public void receiveMsg(final SocketListener listener) {
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                if (model.socketLive()) {
                    Log.e("--socket--SocketManager--", "---等待接受数据---");

                    try {
                        String s = model.socketReceive();

//                        if (!TextUtils.isEmpty(s)) {
//                            Log.e("--socket--SocketManager--", "---收数据--" + s);


//                            if (s.equals("服务器没连接")) {
//                                listener.onFail(SocketErrorCode.code_connect_no);
//                            } else {
                                listener.onSuccess(s);
//                            }
//                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("--socket--SocketManager--", "---等待接受数据--失败-");

                        listener.onFail(SocketErrorCode.code_receive_fail);
                    }
                } else {
                    Log.e("--socket--SocketManager--", "---连接--- 断开");

                    listener.onFail(SocketErrorCode.code_connect_die);
                }
            }
        }, 3 * 1000, 3 * 1000, TimeUnit.MILLISECONDS);

    }

    public void closeSocket(final SocketListener listener) {
        pool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    model.socketClose();
                    listener.onSuccess("关闭成功");
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFail(SocketErrorCode.code_close_fail);
                }
            }
        });
    }

    public void reConSocket(final SocketListener listener) {
        pool.execute(new Runnable() {

            @Override
            public void run() {

                try {
                    model.reconnectionSocket();

                    if (model.socketLive()) {
                        listener.onSuccess("");
                    } else {
                        listener.onFail(SocketErrorCode.code_connect_die);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFail(SocketErrorCode.code_connect_die);
                }
            }
        });
    }

    public void live(final SocketListener listener) {
        //5 秒后 10秒循环执行
        pool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                boolean live = model.socketLive();
                if (!live) {
//                    reConSocket(listener);
                    listener.onFail(SocketErrorCode.code_connect_die);
                } else {
                    listener.onSuccess("socket 活动状态");
                }
            }
        }, 5 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

}
