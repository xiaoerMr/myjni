package com.vexcellent.myjni.netstate;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dianxiaoer.dutillibrary.log.DLog;
import com.vexcellent.myjni.BuildConfig;

public class NetManager {
    private static NetManager manager;
    private ConnectivityManager mConnectivityManager;
    private NetStateListener listener;
    private NetType oldType;
    private final Activity activity;

    public static NetManager getInstance(Context context) {
        if (null == manager) {
            synchronized (NetManager.class) {
                if (null == manager) {
                    manager = new NetManager(context);
                }
            }
        }
        return manager;
    }

    private NetManager(Context context) {
        activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    private void geState() {
        final NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (null == info) {
            listener.StateNone();
            return;
        }

        if (null == listener) {
            return;
        }


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                switch (info.getType()) {
                    case -1:
                        if (null == oldType || oldType != NetType.NONE) {
                            listener.StateNone();//
                            oldType = NetType.NONE;
                        }
                        break;

                    case ConnectivityManager.TYPE_MOBILE:
                        if (null == oldType || oldType != NetType.NET) {
                            listener.StateNet();//
                            oldType = NetType.NET;
                        }
                        break;

                    case ConnectivityManager.TYPE_WIFI:
                        if (null == oldType || oldType != NetType.WIFI) {
                            listener.StateWifi();//
                            oldType = NetType.WIFI;
                        }
                        break;
                }
            }
        });

    }

    public void getNetState(NetStateListener listen) {

        if (null == mConnectivityManager) {
            return;
        }
        if (null == listener) {
            return;
        }
        this.listener = listen;

        //判断当前系统是否小于24(7.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SDK7Manager();
            geState();
        } else if (Build.VERSION_CODES.N > Build.VERSION.SDK_INT && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 - 7.0
            SDK57Manager();
            geState();
        } else {
            //5.0以下
            SDK5Manager();
        }
    }

    private void SDK5Manager() {

    }

    private void SDK57Manager() {
        mConnectivityManager.requestNetwork(new NetworkRequest.Builder().build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        geState();
//                DLog.e("=======网络连接======");
                    }

                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);
//                geState();
                        listener.StateNone();
                        DLog.e("=======网络断开======");
                    }

                    @Override
                    public void onUnavailable() {
                        super.onUnavailable();
//                geState();
                        listener.StateNone();
                        DLog.e("=======网络超时======");
                    }

                    @Override
                    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                        super.onCapabilitiesChanged(network, networkCapabilities);
                        geState();

//                DLog.e("=======网络类型变化======" + networkCapabilities.hashCode());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SDK7Manager() {
        mConnectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                geState();
//                DLog.e("=======网络连接======");
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
//                geState();
                listener.StateNone();
                DLog.e("=======网络断开======");
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
//                geState();
                listener.StateNone();
                DLog.e("=======网络超时======");
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                geState();

//                DLog.e("=======网络类型变化======" + networkCapabilities.hashCode());
            }

//            @Override
//            public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
//                super.onLinkPropertiesChanged(network, linkProperties);
//                DLog.e("=======连接属性变化======");
//                geState();
//            }

        });
    }

    public void unRegister() {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != mConnectivityManager) {
                mConnectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());
            }
        } else {//5.0以下

        }
    }

}
