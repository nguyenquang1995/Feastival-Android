package com.framgia.feastival.screen.login;

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
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
