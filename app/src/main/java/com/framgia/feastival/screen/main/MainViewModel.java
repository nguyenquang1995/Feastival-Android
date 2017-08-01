package com.framgia.feastival.screen.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.framgia.feastival.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Exposes the data to be used in the Main screen.
 */
public class MainViewModel implements MainContract.ViewModel, OnMapReadyCallback,
    GoogleMap.OnMapLoadedCallback {
    private static final String TAG = MainViewModel.class.getName();
    private Context mContext;
    private MainContract.Presenter mPresenter;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private LatLng myLocation;
    private boolean isNeedInMyLocation;
    private ProgressDialog mProgressDialog;
    private GoogleMap.OnMyLocationChangeListener mMyLocationChangeListener =
        new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                zoomInMyPositonAutomaticly();
            }
        };

    public MainViewModel(Context context) {
        mContext = context;
    }

    private void zoomInMyPositonAutomaticly() {
        if (mMap != null && isNeedInMyLocation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));// 0-18
            isNeedInMyLocation = false;
        }
    }

    private void showNotifyLoading() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle(mContext.getString(R.string.loading_map_dialog_title));
        mProgressDialog.setMessage(mContext.getString(R.string.loading_map_dialog_message));
        mProgressDialog.show();
    }

    public void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(mContext,
            Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).requestPermission();
            }
        } else {
            mMap.setMyLocationEnabled(true);
            isNeedInMyLocation = true;
            mMap.setOnMyLocationChangeListener(mMyLocationChangeListener);
        }
    }

    @Override
    public void onStart() {
        mMapFragment.getMapAsync(this);
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showNotifyLoading();
        mMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void setMapFragment(SupportMapFragment mapFragment) {
        mMapFragment = mapFragment;
    }

    @Override
    public void onMapLoaded() {
        mProgressDialog.dismiss();
        getMyLocation();
    }
}
