package com.bill.netlib.manager;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class DisposeManager {
    private static class SingletonHolder {
        private final static DisposeManager instance = new DisposeManager();
    }

    public static DisposeManager getInstance() {
        return SingletonHolder.instance;
    }

    public DisposeManager() {
        disposableMap = new HashMap<>();
    }

    private Map<String, Disposable> disposableMap;

    public void add(String tag, Disposable disposable) {
        if (!TextUtils.isEmpty(tag))
            disposableMap.put(tag, disposable);
    }

    public Disposable remove(String tag) {
        if (!TextUtils.isEmpty(tag))
            return disposableMap.remove(tag);
        return null;
    }

    public void cancel(String... tags) {
        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                Disposable disposable = remove(tags[i]);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        }
    }

    public void cancelAll() {
        Iterator<Map.Entry<String, Disposable>> iterator = disposableMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Disposable> entry = iterator.next();
            Disposable disposable = entry.getValue();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            iterator.remove();
        }

    }

}
