package com.framgia.feastival.data.source.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by framgia on 04/08/2017.
 */
public class LoginResponse {
    @SerializedName("messages")
    private String mMessage;
    @SerializedName("user_session")
    private UserSession mUserSession;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public UserSession getUserSession() {
        return mUserSession;
    }

    public void setUserSession(UserSession userSession) {
        mUserSession = userSession;
    }

    /**
     * Obtain user's session.
     */
    public static class UserSession {
        @SerializedName("id")
        private int mId;
        @SerializedName("email")
        private String mEmail;
        @SerializedName("user_token")
        private String mToken;

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getEmail() {
            return mEmail;
        }

        public void setEmail(String email) {
            mEmail = email;
        }

        public String getToken() {
            return mToken;
        }

        public void setToken(String token) {
            mToken = token;
        }
    }
}
