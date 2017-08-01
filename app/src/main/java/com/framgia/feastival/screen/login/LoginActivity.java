package com.framgia.feastival.screen.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.framgia.feastival.R;
import com.framgia.feastival.databinding.ActivityLoginBinding;
import com.framgia.feastival.screen.BaseActivity;

/**
 * Login Screen.
 */
public class LoginActivity extends BaseActivity {
    private LoginContract.ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new LoginViewModel();
        LoginContract.Presenter presenter =
            new LoginPresenter(mViewModel);
        mViewModel.setPresenter(presenter);
        ActivityLoginBinding binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel((LoginViewModel) mViewModel);
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
