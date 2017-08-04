package com.framgia.feastival.data.source.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.framgia.feastival.BR;

/**
 * Created by framgia on 04/08/2017.
 */
public class FriendlyMessage extends BaseObservable {
    private String mId;
    private String mText;
    private String mName;

    public FriendlyMessage() {
    }

    public FriendlyMessage(String text, String name) {
        this.mText = text;
        this.mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    @Bindable
    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
        notifyPropertyChanged(BR.text);
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
        notifyPropertyChanged(BR.name);
    }
}
