package com.framgia.feastival.screen.chat;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.framgia.feastival.BR;
import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.FriendlyMessage;
import com.framgia.feastival.databinding.ItemMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;
import static com.framgia.feastival.data.Constant.SharePreference.PRE_NAME;
import static com.framgia.feastival.data.Constant.SharePreference.PRE_TOKEN;

/**
 * Exposes the data to be used in the Chat screen.
 */
public class ChatViewModel extends BaseObservable implements ChatContract.ViewModel {
    private static final String MESSAGES_CHILD = "messages";
    private ChatContract.Presenter mPresenter;
    private String mToken;
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private String mUserName;
    private String mMessage;
    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> mFirebaseAdapter;

    public ChatViewModel(Activity activity) {
        mActivity = activity;
        mToken = mActivity.getSharedPreferences(PRE_NAME, MODE_PRIVATE).getString(PRE_TOKEN, null);
        if (mToken == null) {
            mActivity.finish();
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInWithCustomToken(mToken);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>(
            FriendlyMessage.class,
            R.layout.item_message,
            MessageViewHolder.class,
            mDatabaseReference.child(MESSAGES_CHILD)
        ) {
            @Override
            protected void populateViewHolder(final MessageViewHolder viewHolder,
                                              FriendlyMessage friendlyMessage, int position) {
                viewHolder.bindData(friendlyMessage);
            }
        };
    }

    @Bindable
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
        notifyPropertyChanged(BR.message);
    }

    @Bindable
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> getFirebaseAdapter() {
        return mFirebaseAdapter;
    }

    public void setFirebaseAdapter(
        FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder> firebaseAdapter) {
        mFirebaseAdapter = firebaseAdapter;
        notifyPropertyChanged(com.framgia.feastival.BR.firebaseAdapter);
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onButtonSendClick() {
        FriendlyMessage friendlyMessage = new FriendlyMessage(mMessage, mUserName);
        mDatabaseReference.child(MESSAGES_CHILD).push().setValue(friendlyMessage);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private ItemMessageBinding mBinding;

        public MessageViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public void bindData(FriendlyMessage friendlyMessage) {
            if (friendlyMessage == null) {
                return;
            }
            mBinding.setMessage(friendlyMessage);
            mBinding.executePendingBindings();
        }
    }
}
