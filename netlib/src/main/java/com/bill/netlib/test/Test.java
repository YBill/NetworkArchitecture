package com.bill.netlib.test;

import android.util.Log;

import com.bill.netlib.interceptor.HttpLogInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Test {

    public interface MovieService {

        @GET("top250")
        Observable<TestEntity> getTopService(@Query("start") int start, @Query("count") int count);

        @GET("top250")
        Observable<String> getTopService2(@Query("start") int start, @Query("count") int count);

    }

    public void test() {
        HttpLogInterceptor httpLogInterceptor = new HttpLogInterceptor();

        OkHttpClient.Builder builder =
                new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(httpLogInterceptor);


        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopService2(0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("Bill", "s:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Bill", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Bill", "onComplete");
                    }
                });
    }

    public void test2() {
        HttpLogInterceptor httpLogInterceptor = new HttpLogInterceptor();

        OkHttpClient.Builder builder =
                new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .addInterceptor(httpLogInterceptor);


        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopService(0, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TestEntity testEntity) {
                        List<TestEntity.SubjectsEntity> list = testEntity.getSubjects();
                        for (TestEntity.SubjectsEntity sub : list) {
                            Log.d("Bill", sub.getTitle());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Bill", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Bill", "onComplete");
                    }
                });
    }

}
