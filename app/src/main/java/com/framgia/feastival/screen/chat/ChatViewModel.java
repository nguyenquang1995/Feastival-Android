package com.framgia.feastival.screen.chat;

/**
 * Exposes the data to be used in the Chat screen.
 */
public class ChatViewModel implements ChatContract.ViewModel {
    private ChatContract.Presenter mPresenter;

    public ChatViewModel() {
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
    public void setPresenter(ChatContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
