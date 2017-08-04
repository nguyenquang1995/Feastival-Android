package com.framgia.feastival.screen.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.framgia.feastival.data.source.model.LoginResponse;
import com.framgia.feastival.screen.chat.ChatActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.framgia.feastival.data.Constant.SharePreference.PRE_NAME;
import static com.framgia.feastival.data.Constant.SharePreference.PRE_TOKEN;

/**
 * Exposes the data to be used in the Login screen.
 */
public class LoginViewModel extends BaseObservable implements LoginContract.ViewModel {
    private LoginContract.Presenter mPresenter;
    private boolean mIsLogin;
    private String mAccount;
    private String mPassword;
    private Activity mActivity;

    public LoginViewModel(Activity activity) {
        mActivity = activity;
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
        setLogin(true);
        mPresenter.logIn(mAccount, mPassword);
    }

    @Override
    public void onRegisterClick() {
    }

    @Override
    public void onForgotPassWordClick() {
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        setLogin(false);
        mActivity.getSharedPreferences(
            PRE_NAME, MODE_PRIVATE).edit()
            .putString(PRE_TOKEN, loginResponse.getUserSession().getToken()).commit();
        mActivity.startActivity(new Intent(mActivity, ChatActivity.class));
    }

    @Override
    public void onLoginFail(String message) {
        setLogin(false);
    }
}
