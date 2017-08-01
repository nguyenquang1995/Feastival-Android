package com.framgia.feastival.screen.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the data to be used in the Main screen.
 */
public class MainViewModel implements MainContract.ViewModel, OnMapReadyCallback,
    GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener {
    private static final String TAG = MainViewModel.class.getName();
    private static final String MARKER_VIEW_POINT = "MARKER_VIEW_POINT";
    private static final String MARKER_RESIZE = "MARKER_RESIZE";
    private static final String MARKER_RESTAURANT = "MARKER_RESTAURANT";
    private static final String MARKER_GROUP = "MARKER_GROUP";
    private static final double mRadius = 600;
    private Context mContext;
    private MainContract.Presenter mPresenter;
    private SupportMapFragment mMapFragment;
    private GoogleMap mMap;
    private List<Marker> mRestaurantsMarker;
    private List<Marker> mViewPointMarker;
    private LatLng mMyLocation;
    private Marker mMarkerMyLocation;
    private boolean isNeedInMyLocation;
    private ProgressDialog mProgressDialog;
    private GoogleMap.OnMyLocationChangeListener mMyLocationChangeListener =
        new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                mMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
                zoomInMyPositonAutomaticly();
            }
        };

    public MainViewModel(Context context) {
        mContext = context;
        mRestaurantsMarker = new ArrayList<>();
        mViewPointMarker = new ArrayList<>();
    }

    private void zoomInMyPositonAutomaticly() {
        if (mMap != null && isNeedInMyLocation) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, 14));// 0-18
            markMyLocation(mMyLocation);
            isNeedInMyLocation = false;
        }
    }

    private void markMyLocation(LatLng location) {
        if (mMap != null) {
            if (mMarkerMyLocation != null) {
                removeViewPoint(mMarkerMyLocation);
                mViewPointMarker.remove(mMarkerMyLocation);
            }
            mMarkerMyLocation = addMarkerViewPoint(location);
        }
    }

    private void removeViewPoint(Marker viewPoint) {
        if (mMap == null) return;
        Circle circle = (Circle) viewPoint.getTag();
        Marker markerResize = (Marker) circle.getTag();
        markerResize.remove();
        circle.remove();
        mViewPointMarker.remove(viewPoint);
        viewPoint.remove();
    }

    private void updateCircle(Marker viewPoint, double newRadious) {
        Circle circle = (Circle) viewPoint.getTag();
        Marker makerResize = (Marker) circle.getTag();
        circle.setRadius(newRadious);
    }

    private void updateCircle(Marker viewPoint) {
        Circle circle = (Circle) viewPoint.getTag();
        Marker makerResize = (Marker) circle.getTag();
        circle.setCenter(viewPoint.getPosition());
        makerResize.setPosition(
            SphericalUtil.computeOffset(viewPoint.getPosition(), circle.getRadius(), 90));
    }

    private Marker addMarkerViewPoint(LatLng location) {
        Marker marker = mMap.addMarker(new MarkerOptions()
            .position(location)
            .snippet(MARKER_VIEW_POINT)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        marker.setTag(drawCircle(marker));
        marker.setDraggable(true);
        mViewPointMarker.add(marker);
        return marker;
    }

    private Circle drawCircle(Marker viewPoint) {
        if (mMap == null) return null;
        Circle circle = (Circle) viewPoint.getTag();
        double radius = mRadius;
        if (circle != null) {
            radius = circle.getRadius();
            circle.remove();
        }
        circle = mMap.addCircle(new CircleOptions()
            .center(viewPoint.getPosition())
            .fillColor(mContext.getResources().getColor(R.color.color_pink_50))
            .radius(radius)
            .strokeWidth(2)
            .strokeColor(mContext.getResources().getColor(R.color.color_red_accent_200)));
        circle.setTag(drawResizeMarker(viewPoint, circle));
        return circle;
    }

    private Marker drawResizeMarker(Marker viewPoint, Circle circle) {
        if (mMap == null) return null;
        Marker marker = (Marker) circle.getTag();
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
            .position(SphericalUtil.computeOffset(circle.getCenter(), circle.getRadius(), 90))
            .snippet(MARKER_RESIZE)
            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_resize_circle)));
        marker.setTag(viewPoint);
        marker.setDraggable(true);
        return marker;
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

    private void markNearbyRestaurants(RestaurantsResponse restaurantsResponse) {
        mRestaurantsMarker.clear();
        for (Restaurant restaurant : restaurantsResponse.getList()) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(restaurant.getLatitude(), restaurant.getLongtitude()))
                .title(String.valueOf(restaurant.getId())));
            marker.setTag(restaurant);
            mRestaurantsMarker.add(marker);
        }
    }

    private double getDistance(LatLng latLngA, LatLng latLngB) {
        Location locationA = new Location("");
        locationA.setLatitude(latLngA.latitude);
        locationA.setLongitude(latLngA.longitude);
        Location locationB = new Location("");
        locationB.setLatitude(latLngB.latitude);
        locationB.setLongitude(latLngB.longitude);
        return locationA.distanceTo(locationB);
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
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void setMapFragment(SupportMapFragment mapFragment) {
        mMapFragment = mapFragment;
    }

    @Override
    public void onGetRestaurantsSuccess(RestaurantsResponse restaurantsResponse) {
        markNearbyRestaurants(restaurantsResponse);
    }

    @Override
    public void onGetRestaurantsFailed(Throwable e) {
    }

    @Override
    public void onMapLoaded() {
        mProgressDialog.dismiss();
        getMyLocation();
        mPresenter.getRestaurants();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            marker
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (marker.getSnippet().equals(MARKER_RESIZE)) {
            Marker viewPoint = (Marker) marker.getTag();
            double newRadious =
                getDistance(viewPoint.getPosition(), marker.getPosition());
            updateCircle(viewPoint, newRadious);
        } else if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            updateCircle(marker);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        // TODO: 29/07/2017
    }

    @Override
    public void onMapClick(LatLng latLng) {
        for (Marker marker : mViewPointMarker) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(false);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(true);
            marker
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            return true;
        }
        return false;
    }
}
