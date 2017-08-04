package com.framgia.feastival.screen.login;

import com.framgia.feastival.data.source.model.LoginResponse;
import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
        void onLoginClick();
        void onRegisterClick();
        void onForgotPassWordClick();
        void onLoginSuccess(LoginResponse loginResponse);
        void onLoginFail(String message);
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
        void logIn(String email, String password);
    }
}
