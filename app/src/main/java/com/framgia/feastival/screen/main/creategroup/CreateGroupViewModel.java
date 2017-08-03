package com.framgia.feastival.screen.main.creategroup;

import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.screen.BaseViewModel;
import com.framgia.feastival.screen.main.MainViewModel;

/**
 * Exposes the data to be used in the CreateGroup screen.
 */
public class CreateGroupViewModel {
    private BaseViewModel mBaseViewModel;
    private Restaurant mSelectedRestaurant;

    public CreateGroupViewModel(MainViewModel mainViewModel) {
        mBaseViewModel = mainViewModel;
    }

    public Restaurant getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    public void setSelectedRestaurant(
        Restaurant selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
    }
}
