package com.framgia.feastival.screen.chat;

/**
 * Listens to user actions from the UI ({@link ChatActivity}), retrieves the data and updates
 * the UI as required.
 */
final class ChatPresenter implements ChatContract.Presenter {
    private static final String TAG = ChatPresenter.class.getName();
    private final ChatContract.ViewModel mViewModel;

    ChatPresenter(ChatContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }
}
