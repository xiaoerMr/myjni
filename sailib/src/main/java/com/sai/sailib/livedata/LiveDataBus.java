package com.sai.sailib.livedata;



import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * LiveData是Android Architecture Components提出的框架。
 * LiveData是一个可以被观察的数据持有类，它可以感知并遵循Activity、Fragment或Service等组件的生命周期。
 * 正是由于LiveData对组件生命周期可感知特点，因此可以做到仅在组件处于生命周期的激活状态时才更新UI数据。
 *
 * LiveData需要一个观察者对象，一般是Observer类的具体实现。
 * 当观察者的生命周期处于STARTED或RESUMED状态时，LiveData会通知观察者数据变化；
 * 在观察者处于其他状态时，即使LiveData的数据变化了，也不会通知。
 *
 * 优点
 *
 * 1: UI和实时数据保持一致，因为LiveData采用的是观察者模式，
 *      这样一来就可以在数据发生改变时获得通知，更新UI。
 * 2: 避免内存泄漏，观察者被绑定到组件的生命周期上，
 *      当被绑定的组件销毁（destroy）时，观察者会立刻自动清理自身的数据。
 * 3: 不会再产生由于Activity处于stop状态而引起的崩溃，
 *      例如：当Activity处于后台状态时，是不会收到LiveData的任何事件的。
 * 4: 不需要再解决生命周期带来的问题，
 *      LiveData可以感知被绑定的组件的生命周期，只有在活跃状态才会通知数据变化。
 * 5: 实时数据刷新，当组件处于活跃状态或者从不活跃状态到活跃状态时总是能收到最新的数据。
 * 6: 解决Configuration Change问题，在屏幕发生旋转或者被回收再次启动，立刻就能收到最新的数据。
 *
 * @author dianxiaoer
 */
public class LiveDataBus {

    private static LiveDataBus liveDataBus;
    private final Map<String, MutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        if (null == liveDataBus) {
            synchronized (LiveDataBus.class){
                if (null == liveDataBus) {
                     liveDataBus  = new LiveDataBus();
                }
            }
        }
        return liveDataBus;
    }

    public <T> MutableLiveData<T> getChannel(String name, Class<T> type){
        if (!bus.containsKey(name)) {
            bus.put(name, new MutableLiveData<>());
        }

        return (MutableLiveData<T>) bus.get(name);
    }
    public MutableLiveData<Object> getChannel(String name) {
        return getChannel(name, Object.class);
    }
}
