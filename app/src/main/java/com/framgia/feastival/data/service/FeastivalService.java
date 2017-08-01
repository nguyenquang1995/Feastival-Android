package com.framgia.feastival.data.service;

import com.framgia.feastival.data.source.model.RestaurantsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tmd on 19/07/2017.
 */
public interface FeastivalService {
    @GET("restaurants")
    Observable<RestaurantsResponse> getRestaurants();
    @GET("restaurants")
    Observable<RestaurantsResponse> getRestaurants(@Query("lat") float lat,
                                                   @Query("lng") float lng,
                                                   @Query("distance") float distance);
}
