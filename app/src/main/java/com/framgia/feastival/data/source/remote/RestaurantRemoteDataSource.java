package com.framgia.feastival.data.source.remote;

import com.framgia.feastival.data.source.RestaurantDataSource;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.framgia.feastival.data.service.FeastivalService;
import com.framgia.feastival.data.service.ServiceGenerator;

import io.reactivex.Observable;

/**
 * Created by tmd on 07/07/2017.
 */
public class RestaurantRemoteDataSource implements RestaurantDataSource {
    private FeastivalService mService;

    public RestaurantRemoteDataSource() {
        mService = ServiceGenerator.createService(FeastivalService.class);
    }

    @Override
    public Observable<RestaurantsResponse> getRestaurants() {
        return mService.getRestaurants();
    }
}
