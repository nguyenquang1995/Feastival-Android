package com.framgia.feastival.screen.main;

import com.framgia.feastival.data.source.RestaurantDataSource;
import com.framgia.feastival.data.source.model.RestaurantsResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link MainActivity}), retrieves the data and updates
 * the UI as required.
 */
final class MainPresenter implements MainContract.Presenter {
    private static final String TAG = MainPresenter.class.getName();
    private final MainContract.ViewModel mViewModel;
    private RestaurantDataSource mRestaurantRepository;
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainContract.ViewModel viewModel,
                         RestaurantDataSource restaurantRepository) {
        mViewModel = viewModel;
        mRestaurantRepository = restaurantRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getRestaurants() {
        Disposable disposable = mRestaurantRepository.getRestaurants()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<RestaurantsResponse>() {
                @Override
                public void onNext(@NonNull RestaurantsResponse response) {
                    mViewModel.onGetRestaurantsSuccess(response);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mViewModel.onGetRestaurantsFailed(e);
                }

                @Override
                public void onComplete() {
                }
            });
        mCompositeDisposable.add(disposable);
    }
}
