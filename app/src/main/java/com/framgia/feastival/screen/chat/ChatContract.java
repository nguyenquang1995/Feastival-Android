package com.framgia.feastival.screen.chat;

import com.framgia.feastival.screen.BasePresenter;
import com.framgia.feastival.screen.BaseViewModel;

/**
 * This specifies the contract between the view and the presenter.
 */
interface ChatContract {
    /**
     * View.
     */
    interface ViewModel extends BaseViewModel<Presenter> {
    }

    /**
     * Presenter.
     */
    interface Presenter extends BasePresenter {
    }
}
