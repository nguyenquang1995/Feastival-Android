package com.framgia.feastival.screen.login;

import com.framgia.feastival.data.source.LoginDataSouce;
import com.framgia.feastival.data.source.model.LoginResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Listens to user actions from the UI ({@link LoginActivity}), retrieves the data and updates
 * the UI as required.
 */
final class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = LoginPresenter.class.getName();
    private final LoginContract.ViewModel mViewModel;
    private LoginDataSouce mLoginDataSouce;
    private CompositeDisposable mCompositeDisposable;

    LoginPresenter(LoginContract.ViewModel viewModel, LoginDataSouce loginDataSouce) {
        mViewModel = viewModel;
        mLoginDataSouce = loginDataSouce;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    public void logIn(String email, String password) {
        Disposable disposable = mLoginDataSouce.logIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<LoginResponse>() {
                @Override
                public void onNext(@NonNull LoginResponse response) {
                    mViewModel.onLoginSuccess(response);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    mViewModel.onLoginFail(e.getMessage());
                }

                @Override
                public void onComplete() {
                }
            });
        mCompositeDisposable.add(disposable);
    }
}
