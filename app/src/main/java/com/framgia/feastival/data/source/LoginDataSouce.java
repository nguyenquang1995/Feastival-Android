package com.framgia.feastival.data.source;

import com.framgia.feastival.data.source.model.LoginResponse;

import io.reactivex.Observable;

/**
 * Created by framgia on 04/08/2017.
 */
public interface LoginDataSouce {
    Observable<LoginResponse> logIn(String email, String password);
}
