package com.bill.netlib.config;

import com.bill.netlib.http.RetrofitClient;

import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

public class HttpConfig {

    public long readTimeout;
    public long writeTimeout;
    public long connectTimeout;
    public Interceptor[] interceptors;

    public CallAdapter.Factory[] callAdapterFactories;
    public Converter.Factory[] converterFactories;
    public String baseUrl;

    private HttpConfig(Builder builder) {
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.connectTimeout = builder.connectTimeout;
        this.interceptors = builder.interceptors;

        this.callAdapterFactories = builder.callAdapterFactories;
        this.converterFactories = builder.converterFactories;
        this.baseUrl = builder.baseUrl;
    }

    public static class Builder {
        private long readTimeout;
        private long writeTimeout;
        private long connectTimeout;
        private Interceptor[] interceptors;

        private CallAdapter.Factory[] callAdapterFactories;
        private Converter.Factory[] converterFactories;
        private String baseUrl;

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setInterceptors(Interceptor... interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public Builder setCallAdapterFactories(CallAdapter.Factory... callAdapterFactories) {
            this.callAdapterFactories = callAdapterFactories;
            return this;
        }

        public Builder setConverterFactories(Converter.Factory... converterFactories) {
            this.converterFactories = converterFactories;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public RetrofitClient build() {
            return RetrofitClient.getInstance().setConfig(new HttpConfig(this));
        }

    }

}
