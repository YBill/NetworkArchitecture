package com.bill.netlib.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpLogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d("Bill", request.method() + "  " + request.url());
        Response response;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            throw e;
        }
        return response;
    }

}
