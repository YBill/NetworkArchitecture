package com.bill.netlib;

import com.bill.netlib.config.HttpConfig;
import com.bill.netlib.http.GlobalHttp;
import com.bill.netlib.http.RetrofitClient;
import com.bill.netlib.manager.DisposeManager;
import com.bill.netlib.test.Test;

public class HttpUtils {

    public static GlobalHttp global() {
        return GlobalHttp.getInstance();
    }

    public static <K> K createApi(Class<K> cls) {
        return RetrofitClient.getInstance().createApi(cls);
    }

    public static HttpConfig.Builder config() {
        return new HttpConfig.Builder();
    }

    public static void cancel(String... tag) {
        DisposeManager.getInstance().cancel(tag);
    }

    public static void cancelAll() {
        DisposeManager.getInstance().cancelAll();
    }

    public static boolean test() {
        Test test = new Test();
        test.test();

        return true;
    }

}
