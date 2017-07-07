package com.shrimpcolo.johnnytam.ishuying.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrimpcolo.johnnytam.ishuying.base.BaseRecycleViewHolder.BuilderItemViewHolder;
import com.shrimpcolo.johnnytam.ishuying.utils.ConstContent;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaseRecycleViewAdapter<T, VH extends BaseRecycleViewHolder<T>>
        extends RecyclerView.Adapter<VH> {

    List<T> mData;
    BuilderItemViewHolder<VH> mBuilderItemViewHolder;

    @LayoutRes
    private int mLayoutItemViewResId;

    public BaseRecycleViewAdapter(@NonNull List<T> data,
                                  @LayoutRes int layoutItemViewResId,
                                  @NonNull BuilderItemViewHolder<VH> itemBuilder) {
        setList(data);
        this.mLayoutItemViewResId = layoutItemViewResId;

        this.mBuilderItemViewHolder = itemBuilder;
    }

    @Override
    public int getItemViewType(int position) {
        return ConstContent.VIEW_TYPE_ITEM;
    }

    private void setList(List<T> movies) {
        mData =  checkNotNull(movies);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                    inflate(mLayoutItemViewResId, parent, false);

        return mBuilderItemViewHolder.build(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (holder == null) return;

        holder.updateItem(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void replaceData(List<T> data) {
        setList(data);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mData;
    }
}