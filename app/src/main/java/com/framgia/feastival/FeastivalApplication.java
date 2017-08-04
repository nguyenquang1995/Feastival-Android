package com.framgia.feastival;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

/**
 * Created by framgia on 07/08/2017.
 */
public class FeastivalApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getApplicationContext());
        FirebaseApp.initializeApp(getApplicationContext());
    }
}
