package com.bill.netframe;

import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Observable;

public interface MovieService {

    @GET("top250")
    Observable<MovieEntity> getTopService(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<String> getTopService2(@Query("start") int start, @Query("count") int count);

}
