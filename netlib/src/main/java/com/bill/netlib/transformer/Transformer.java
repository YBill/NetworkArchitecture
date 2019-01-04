package com.bill.netlib.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Transformer {

    private static final ObservableTransformer defaultTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    private static final ObservableTransformer newThread = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.newThread());
        }
    };

    public static <T> ObservableTransformer<T, T> io2Main() {
        return defaultTransformer;
    }

    public static <T> ObservableTransformer<T, T> newThread() {
        return newThread;
    }


}
