package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tmd on 01/08/2017.
 */
public class Group {
    @SerializedName("id")
    private int mId;
    @SerializedName("category_id")
    private int mCategoryId;
    @SerializedName("restaurant_id")
    private int mRestaurantId;
    @SerializedName("creator_id")
    private int mCreatorId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("time")
    private String mTime;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("latitude")
    private float mLatitude;
    @SerializedName("longitude")
    private float mLongtitude;
    @SerializedName("size")
    private int mSize;
    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("completed")
    private boolean mCompleted;
    @SerializedName("description")
    private String mDescription;
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

    public int getRestaurantId() {
        return mRestaurantId;
    }

    public int getCreatorId() {
        return mCreatorId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTime() {
        return mTime;
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

    public int getSize() {
        return mSize;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }
}
