package com.framgia.feastival.data.source.remote;

import com.framgia.feastival.data.service.FeastivalService;
import com.framgia.feastival.data.service.ServiceGenerator;
import com.framgia.feastival.data.source.LoginDataSouce;
import com.framgia.feastival.data.source.model.LoginResponse;

import io.reactivex.Observable;

/**
 * Created by framgia on 04/08/2017.
 */
public class LoginRemoteDataSource implements LoginDataSouce {
    private FeastivalService mService;

    public LoginRemoteDataSource() {
        mService = ServiceGenerator.createService(FeastivalService.class);
    }

    public Observable<LoginResponse> logIn(String email, String password) {
        return mService.logIn(email, password);
    }
}
