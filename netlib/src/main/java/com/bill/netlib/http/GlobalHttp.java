package com.bill.netlib.http;

import com.bill.netlib.interceptor.HttpLogInterceptor;

import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalHttp {

    private static class SingletonHolder {
        private final static GlobalHttp instance = new GlobalHttp();
    }

    public static GlobalHttp getInstance() {
        return SingletonHolder.instance;
    }

    private CallAdapter.Factory[] callAdapterFactories;
    private Converter.Factory[] converterFactories;
    private String baseUrl;

    private long readTimeout = 10;
    private long writeTimeout = 10;
    private long connectTimeout = 10;
    private Interceptor[] interceptors;
    private boolean debug;

    public GlobalHttp setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public GlobalHttp setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public GlobalHttp setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public GlobalHttp setCallAdapterFactory(CallAdapter.Factory... factories) {
        callAdapterFactories = new CallAdapter.Factory[factories.length];
        for (int i = 0; i < factories.length; i++) {
            callAdapterFactories[i] = factories[i];
        }
        return this;
    }

    public GlobalHttp setConverterFactory(Converter.Factory... factories) {
        converterFactories = new Converter.Factory[factories.length];
        for (int i = 0; i < factories.length; i++) {
            converterFactories[i] = factories[i];
        }
        return this;
    }

    public GlobalHttp setDebug(boolean debug) {
        this.debug = debug;
        if (debug) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptors = new Interceptor[]{
                    new HttpLogInterceptor()
            };
        }
        return this;
    }

    public void build(String baseUrl) {
        this.baseUrl = baseUrl;

        if (null == callAdapterFactories) {
            callAdapterFactories = new CallAdapter.Factory[]{
                    RxJava2CallAdapterFactory.create()
            };
        }

        if (null == converterFactories) {
            converterFactories = new Converter.Factory[]{
                    GsonConverterFactory.create()
            };
        }

    }

    public CallAdapter.Factory[] getCallAdapterFactories() {
        return callAdapterFactories;
    }

    public Converter.Factory[] getConverterFactories() {
        return converterFactories;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public Interceptor[] getInterceptors() {
        return interceptors;
    }

    public boolean isDebug() {
        return debug;
    }
}
