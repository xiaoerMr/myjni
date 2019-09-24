package com.vexcellent.saihttplib.core;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 店小二
 * @date 2019/9/19.
 * GitHub：
 * email：
 * description：
 */
public abstract class SaiObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    protected abstract void onSuccess(T o);

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    protected abstract void onFailure(Throwable e);

    @Override
    public void onComplete() {

    }
}
