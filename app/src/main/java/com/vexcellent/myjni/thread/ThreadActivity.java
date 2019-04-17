package com.vexcellent.myjni.thread;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sai.sailib.time.TimeUtils;
import com.vexcellent.myjni.R;
import com.vexcellent.myjni.base.BaseActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

public class ThreadActivity extends BaseActivity {

    @BindView(R.id.normal)
    Button normal;
    @BindView(R.id.fornormal)
    Button fornormal;
    @BindView(R.id.textView2)
    TextView text;
    private ThreadPoolExecutor executor;
    private Handler handler;
    private ScheduledExecutorService scheduledThreadPool;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thread;
    }

    @Override
    protected void initView() {
        setBarName("线程池");

        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        String txt = (String) msg.obj;
                        text.setText(txt);
                        break;
                    case 2:
                        String txt2 = (String) msg.obj;
                        text.setText(txt2);
                        break;
                }
            }
        };

        text.setText(TimeUtils.getTime());
    }

    @OnClick({R.id.normal, R.id.fornormal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.normal:
                doBuildNormal();//默认线程池
                break;
            case R.id.fornormal://定时循环线程池
                doBuildFornormal();
                break;
        }
    }


    /**
     * corePoolSize: 该线程池中核心线程的数量。
     * maximumPoolSize：该线程池中最大线程数量。(区别于corePoolSize)
     * keepAliveTime:从字面上就可以理解，是非核心线程空闲时要等待下一个任务到来的时间，
     * 当任务很多，每个任务执行时间很短的情况下调大该值有助于提高线程利用率。
     * 注意：当allowCoreThreadTimeOut属性设为true时，该属性也可用于核心线程。
     * unit:上面时间属性的单位
     * workQueue:任务队列，后面详述。
     * threadFactory:线程工厂，可用于设置线程名字等等，一般无须设置该参数。
     */
    private void doBuildNormal() {

        if (null == executor) {
            int corePoolSize = 5;
            int maximumPoolSize = 10;
            int keepAliveTime = 10;
            TimeUnit unit = TimeUnit.SECONDS;
            LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

            executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                String res = TimeUtils.stampToDate();
                message.obj = Thread.currentThread().getName() + ", " + res;
                handler.sendMessage(message);
            }
        });
    }

    private void doBuildFornormal() {
        //创建Scheduled线程池
        if (null == scheduledThreadPool) {
            scheduledThreadPool = Executors.newScheduledThreadPool(3);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 2;

                String res = TimeUtils.stampToDate();
                message.obj = Thread.currentThread().getName() + ", 延迟2秒并循环1秒一次, " + res;
                handler.sendMessage(message);

            }
        };

        TimeUnit unit = TimeUnit.SECONDS;
        //延迟2s
        //scheduledThreadPool.schedule(runnable, 2, unit);
        //延迟2s后启动，每1s执行一次
        //scheduledThreadPool.scheduleAtFixedRate(runnable,5,1,unit);
        //启动后第一次延迟2s执行，后面延迟1s执行
        scheduledThreadPool.scheduleWithFixedDelay(runnable, 2, 1, unit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shutdownThread();
    }

    private void shutdownThread() {
        //shutdown原理：将线程池状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程。
        //shutdownNow原理：将线程池的状态设置成STOP状态，然后中断所有任务(包括正在执行的)的线程，
        //  并返回等待执行任务的列表
        if (null != executor) {
            executor.shutdownNow();
        }
        if (null != scheduledThreadPool) {
            scheduledThreadPool.shutdownNow();
        }
    }
}
