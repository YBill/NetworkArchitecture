package com.bill.netframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bill.netlib.HttpUtils;
import com.bill.netlib.observer.CommonObserver;
import com.bill.netlib.transformer.Transformer;

import java.util.List;

import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils.global().setDebug(true).build("https://api.douban.com/v2/movie/");
    }

    public void handleClick(View view) {
//        if (HttpUtils.test()) {
//            return;
//        }

        HttpUtils
                .createApi(MovieService.class)
                .getTopService(0, 5)
                .compose(Transformer.<MovieEntity>io2Main())
                .subscribe(new CommonObserver<MovieEntity>() {

                    @Override
                    public String setTag() {
                        return "tap5";
                    }

                    @Override
                    public void onNext(MovieEntity movie) {
                        super.onNext(movie);
                        Log.i("Bill", "onNext: " + movie.getTitle());
                        List<MovieEntity.SubjectsEntity> list = movie.getSubjects();
                        for (MovieEntity.SubjectsEntity sub : list) {
                            Log.d("Bill", sub.getTitle());
                        }
                    }
                });

    }

    public void handleClick2(View view) {
        MovieService movieService = HttpUtils
                .config()
                .setConverterFactories(ScalarsConverterFactory.create())
                .createApi(MovieService.class);

        movieService.getTopService2(5, 10)
                .compose(Transformer.<String>io2Main())
                .subscribe(new CommonObserver<String>() {

                    @Override
                    public String setTag() {
                        return "tap5_10";
                    }

                    @Override
                    public void onNext(String movie) {
                        Log.i("Bill", "onNext");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Bill", "onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Bill", "onError:" + e.getMessage());
                    }
                });
    }
}
