package com.framgia.feastival.screen.main.restaurantdetail;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.framgia.feastival.R;
import com.framgia.feastival.data.source.model.Group;
import com.framgia.feastival.databinding.ItemGroupBinding;
import com.framgia.feastival.screen.main.MainContract;
import com.framgia.feastival.screen.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmd on 01/08/2017.
 */
public class RestaurantsGroupsAdapter
    extends RecyclerView.Adapter<RestaurantsGroupsAdapter.ViewHolder> {
    private List< Group> mList = new ArrayList<>();
    private MainContract.ViewModel mViewModel;

    public RestaurantsGroupsAdapter(MainContract.ViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void updateData(List<Group> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGroupBinding binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_group, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RestaurantsGroupsAdapter.ViewHolder holder, int position) {
        Group groupDetailViewModel = mList.get(position);
        holder.bindData(groupDetailViewModel);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemGroupBinding mItemGroupBinding;

        public ViewHolder(ItemGroupBinding itemGroupBinding) {
            super(itemGroupBinding.getRoot());
            mItemGroupBinding = itemGroupBinding;
        }

        public void bindData(Group group) {
            mItemGroupBinding.setViewModel((MainViewModel) mViewModel);
            mItemGroupBinding.setGroup(group);
            mItemGroupBinding.executePendingBindings();
        }
    }
}
