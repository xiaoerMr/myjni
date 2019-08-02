package com.sai.myjni.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RxObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T o) {
        if (o instanceof BaseResponse) {
            BaseResponse baseResponse = (BaseResponse)o;
            if (baseResponse.isSuccess()) {
                onSuccess(o);
            }else {
                onFail(new Throwable(baseResponse.msg));
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T o);
    protected abstract void onFail(Throwable e);

}
