package com.framgia.feastival.screen.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.Restaurant;
import com.framgia.feastival.data.source.model.RestaurantsResponse;
import com.framgia.feastival.screen.BaseActivity;
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
    public static final String STATE_SHOW_RESTAURANT_DETAIL = "STATE_SHOW_RESTAURANT_DETAIL";
    public static final String STATE_SHOW_GROUP_DETAIL = "STATE_SHOW_GROUP_DETAIL";
    public static final String STATE_CREATE_GROUP = "STATE_CREATE_GROUP";
    private ObservableField<String> mState;
    private static final double RADIUS = 1000;
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
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private View mBottomSheet;
    private ObservableField<Restaurant> mSelectedRestaurant;
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
        mState = new ObservableField<>();
        mState.set(STATE_SHOW_RESTAURANT_DETAIL);
        mSelectedRestaurant = new ObservableField<>();
        mSelectedRestaurant.set(new Restaurant());
    }

    public Context getContext() {
        return mContext;
    }

    public ObservableField<String> getState() {
        return mState;
    }

    public void setState(ObservableField<String> state) {
        mState = state;
        mState.notifyChange();
    }

    private void zoomInMyPositonAutomaticly() {
        if (mMap != null && isNeedInMyLocation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMyLocation, 14));
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
        double radius = RADIUS;
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
        mPresenter.getRestaurants(viewPoint.getPosition(), radius);
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
        marker.setVisible(false);
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
                .title(String.valueOf(restaurant.getId()))
                .snippet(MARKER_RESTAURANT + restaurant.getId()));
            marker.setTag(restaurant);
            mRestaurantsMarker.add(marker);
        }
    }

    private void createBottomSheet() {
        mBottomSheet = ((BaseActivity) mContext).findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);
        final TypedArray styledAttributes = mContext.getTheme().obtainStyledAttributes(
            new int[]{android.R.attr.actionBarSize});
        mBottomSheetBehavior.setPeekHeight((int) styledAttributes.getDimension(0, 0));
        styledAttributes.recycle();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    public ObservableField<Restaurant> getSelectedRestaurant() {
        return mSelectedRestaurant;
    }

    public void setSelectedRestaurant(
        ObservableField<Restaurant> selectedRestaurant) {
        mSelectedRestaurant = selectedRestaurant;
    }

    public void setSelectedRestaurant(Marker marker) {
        int restaurantId = Integer.parseInt(marker.getSnippet().replace(MARKER_RESTAURANT, ""));
        mSelectedRestaurant.set((Restaurant) marker.getTag());
        mSelectedRestaurant.notifyChange();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void onStart() {
        mMapFragment.getMapAsync(this);
        mPresenter.onStart();
        createBottomSheet();
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
            double newRadius =
                mPresenter.getDistance(viewPoint.getPosition(), marker.getPosition());
            updateCircle(viewPoint, newRadius);
            return;
        }
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            updateCircle(marker);
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(true);
            mPresenter.getRestaurants(marker.getPosition(), ((Circle) marker.getTag()).getRadius());
            return;
        }
        if (marker.getSnippet().equals(MARKER_RESIZE)) {
            Marker viewPoint = (Marker) marker.getTag();
            double radius = ((Circle) viewPoint.getTag()).getRadius();
            mPresenter.getRestaurants(viewPoint.getPosition(), radius);
            return;
        }
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
        if (marker.getSnippet().contains(MARKER_RESTAURANT)) {
            setSelectedRestaurant(marker);
            return true;
        }
        if (marker.getSnippet().equals(MARKER_VIEW_POINT)) {
            ((Marker) ((Circle) marker.getTag()).getTag()).setVisible(true);
            marker
                .setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            return true;
        }
        return false;
    }
}
