package com.framgia.feastival.screen.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Exposes the data to be used in the Login screen.
 */
public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {
    private LoginContract.Presenter mPresenter;
    private boolean mIsLogin;
    private String mAccount;
    private String mPassword;

    public LoginViewModel() {
    }

    @Bindable
    public boolean isLogin() {
        return mIsLogin;
    }

    public void setLogin(boolean login) {
        mIsLogin = login;
        notifyPropertyChanged(BR.login);
    }

    @Bindable
    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
        notifyPropertyChanged(BR.password);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoginClick() {
    }

    @Override
    public void onRegisterClick() {
    }

    @Override
    public void onForgotPassWordClick() {
    }
}
