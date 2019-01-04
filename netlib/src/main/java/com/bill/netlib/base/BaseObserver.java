package com.bill.netlib.base;

import com.bill.netlib.manager.DisposeManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {

    public String setTag() {
        return null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        DisposeManager.getInstance().add(setTag(), d);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        DisposeManager.getInstance().remove(setTag());
    }

    @Override
    public void onComplete() {
        DisposeManager.getInstance().remove(setTag());
    }
}
