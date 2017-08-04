package com.framgia.feastival.screen.chat;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.framgia.feastival.R;
import com.framgia.feastival.databinding.ActivityChatBinding;
import com.framgia.feastival.screen.BaseActivity;

/**
 * Chat Screen.
 */
public class ChatActivity extends BaseActivity {
    private ChatContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ChatViewModel(this);
        ChatContract.Presenter presenter =
            new ChatPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
        ActivityChatBinding binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setViewModel((ChatViewModel) mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onStart();
    }

    @Override
    protected void onStop() {
        mViewModel.onStop();
        super.onStop();
    }
}
