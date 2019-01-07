package com.bill.netlib.http;

import android.text.TextUtils;

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

    }

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

        Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder();

        mRetrofitBuilder.baseUrl(baseUrl);
        for (Converter.Factory converterFactory : converterFactories) {
            mRetrofitBuilder.addConverterFactory(converterFactory);
        }
        for (CallAdapter.Factory callAdapter : callAdapterFactories) {
            mRetrofitBuilder.addCallAdapterFactory(callAdapter);
        }

        OkHttpClient.Builder mOkHttpBuilder = new OkHttpClient.Builder();
        mOkHttpBuilder.readTimeout(readTimeout, TimeUnit.SECONDS);
        mOkHttpBuilder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        mOkHttpBuilder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                mOkHttpBuilder.addInterceptor(interceptor);
            }
        }

        Interceptor[] globalInterceptors = GlobalHttp.getInstance().getInterceptors();
        if (globalInterceptors != null) {
            for (Interceptor globalInterceptor : globalInterceptors) {
                mOkHttpBuilder.addInterceptor(globalInterceptor);
            }
        }

        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

}
