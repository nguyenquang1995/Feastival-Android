package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tmd on 28/07/2017.
 */
public class RestaurantsResponse {
    @SerializedName("restaurants")
    private List<Restaurant> mList;

    public List<Restaurant> getList() {
        return mList;
    }
}
