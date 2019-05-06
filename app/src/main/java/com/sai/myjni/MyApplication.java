package com.sai.myjni;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sai.sailib.log.DLog;
import com.sai.sailib.smartdialog.SmartShow;

import java.util.Stack;

public class MyApplication extends Application {
    private static final boolean DEBUG_MODE = true;
    /**
     * 先进后出
     */
    public static Stack<Activity> store = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initDLog();
        SmartShow.init(this);
//        registerActivityLifecycleCallbacks(new ActivityCallBack());
    }
    private void initDLog(){
        DLog.Config config = DLog.init(this)
                .setLogSwitch(DEBUG_MODE)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(DEBUG_MODE)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1.0 的 Logcat
                .setConsoleFilter(DLog.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(DLog.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(2);// log栈深度，默认为1
        DLog.d(config.toString());
    }

    class ActivityCallBack implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
            store.add(activity);

            //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
//            if (activity.findViewById(R.id.toolbar) != null) { //找到 Toolbar 并且替换 Actionbar
//                if (activity instanceof AppCompatActivity) {
//                    ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
//                    ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
//                        activity.getActionBar().setDisplayShowTitleEnabled(false);
//                    }
//                }
//            }
//            if (activity.findViewById(R.id.toolbar_title) != null) { //找到 Toolbar 的标题栏并设置标题名
//                ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
//            }
//            if (activity.findViewById(R.id.toolbar_back) != null) { //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
//                activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        activity.onBackPressed();
//                    }
//                });
//            }

    }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            store.remove(activity);

        }
    }


}
