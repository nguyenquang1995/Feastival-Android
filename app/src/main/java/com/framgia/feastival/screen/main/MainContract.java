package com.framgia.feastival.screen.main;

import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;
import com.google.android.gms.maps.SupportMapFragment;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void setMapFragment(SupportMapFragment mapFragment);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
