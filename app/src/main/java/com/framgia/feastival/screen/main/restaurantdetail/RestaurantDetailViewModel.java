package com.framgia.feastival.screen.main.restaurantdetail;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.framgia.feastival.BR;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.screen.main.MainViewModel;

/**
 * Created by tmd on 01/08/2017.
 */
public class RestaurantDetailViewModel extends BaseObservable {
    private Restaurant mSelectedRestaurant;
    private RestaurantsGroupsAdapter mRestaurantsGroupsAdapter;

    public RestaurantDetailViewModel(MainViewModel mainViewModel) {
        mRestaurantsGroupsAdapter = new RestaurantsGroupsAdapter(mainViewModel);
    }

    @Bindable
    public Restaurant getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    public void setSelectedRestaurant(Restaurant selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
        notifyPropertyChanged(BR.selectedRestaurant);
        mRestaurantsGroupsAdapter.updateData(selectedRestaurant.getGroups());
    }

    public RestaurantsGroupsAdapter getRestaurantsGroupsAdapter() {
        return mRestaurantsGroupsAdapter;
    }
}
