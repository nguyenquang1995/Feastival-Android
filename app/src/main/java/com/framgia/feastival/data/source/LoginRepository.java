package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.LoginResponse;

import io.reactivex.Observable;

/**
 * Created by framgia on 04/08/2017.
 */
public class LoginRepository implements LoginDataSouce {
    private LoginDataSouce mLoginDataSouce;

    public LoginRepository(LoginDataSouce loginDataSouce) {
        mLoginDataSouce = loginDataSouce;
    }

    @Override
    public Observable<LoginResponse> logIn(String email, String password) {
        return mLoginDataSouce.logIn(email, password);
    }
}
