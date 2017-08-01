package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 27/07/2017.
 */
public class Restaurant {
    @SerializedName("id")
    private int mId;
    @SerializedName("category_id")
    private int mCategoryId;
    @SerializedName("manager_id")
    private int mManagerId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("latitude")
    private float mLatitude;
    @SerializedName("longitude")
    private float mLongtitude;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("phonenumber")
    private String mPhoneNumber;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("is_approved")
    private boolean mIsApproved;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdateAt;

    public int getId() {
        return mId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public int getManagerId() {
        return mManagerId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAddress() {
        return mAddress;
    }

    public float getLatitude() {
        return mLatitude;
    }

    public float getLongtitude() {
        return mLongtitude;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public boolean isApproved() {
        return mIsApproved;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }
}
