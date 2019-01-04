package com.bill.netlib.http;

import android.text.TextUtils;
import android.util.Log;

import com.bill.netlib.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitClient {

    private static class SingletonHolder {
        private final static RetrofitClient instance = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.instance;
    }

    private RetrofitClient() {
        mRetrofitBuilder = new Retrofit.Builder();
        mOkHttpBuilder = new OkHttpClient.Builder();
    }

    private OkHttpClient.Builder mOkHttpBuilder;
    private Retrofit.Builder mRetrofitBuilder;
    private HttpConfig config;

    public RetrofitClient setConfig(HttpConfig config) {
        this.config = config;
        return this;
    }

    public <K> K createApi(Class<K> cls) {
        return getRetrofit().create(cls);
    }

    public Retrofit getRetrofit() {
        String baseUrl = GlobalHttp.getInstance().getBaseUrl();
        Converter.Factory[] converterFactories = GlobalHttp.getInstance().getConverterFactories();
        CallAdapter.Factory[] callAdapterFactories = GlobalHttp.getInstance().getCallAdapterFactories();

        long readTimeout = GlobalHttp.getInstance().getReadTimeout();
        long writeTimeout = GlobalHttp.getInstance().getWriteTimeout();
        long connectTimeout = GlobalHttp.getInstance().getConnectTimeout();
        Interceptor[] interceptors = null;

        if (this.config != null) {
            if (!TextUtils.isEmpty(config.baseUrl)) {
                baseUrl = config.baseUrl;
            }
            if (config.converterFactories != null) {
                converterFactories = config.converterFactories;
            }
            if (config.callAdapterFactories != null) {
                callAdapterFactories = config.callAdapterFactories;
            }

            if (config.readTimeout != 0) {
                readTimeout = config.readTimeout;
            }
            if (config.writeTimeout != 0) {
                writeTimeout = config.writeTimeout;
            }
            if (config.connectTimeout != 0) {
                connectTimeout = config.connectTimeout;
            }
            if (config.interceptors != null) {
                interceptors = config.interceptors;
            }

            this.config = null;
        }

        mRetrofitBuilder.baseUrl(baseUrl);
        for (Converter.Factory converterFactory : converterFactories) {
            mRetrofitBuilder.addConverterFactory(converterFactory);
        }
        for (Converter.Factory converter : mRetrofitBuilder.converterFactories()) {
            Log.d("Bill", "converter:" + converter);
        }
        for (CallAdapter.Factory callAdapter : callAdapterFactories) {
            mRetrofitBuilder.addCallAdapterFactory(callAdapter);
        }
        mOkHttpBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        mOkHttpBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        mOkHttpBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        if (interceptors != null) {
            for (int i = 0; i < interceptors.length; i++) {
                if (!mOkHttpBuilder.interceptors().contains(interceptors[i])) {
                    mOkHttpBuilder.addInterceptor(interceptors[i]);
                }
            }
        }

        Interceptor[] globalInterceptors = GlobalHttp.getInstance().getInterceptors();
        if (globalInterceptors != null) {
            for (int i = 0; i < globalInterceptors.length; i++) {
                if (!mOkHttpBuilder.interceptors().contains(globalInterceptors[i])) {
                    mOkHttpBuilder.addInterceptor(globalInterceptors[i]);
                }
            }
        }

        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

}
