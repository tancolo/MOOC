package com.shrimpcolo.johnnytam.ishuying.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder {
    protected T itemContent;

    public BaseRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void updateItem(T itemContent) {
        this.itemContent = itemContent;
        onBindItem(itemContent);
    }

    protected abstract void onBindItem(T itemContent);

    public interface BuilderItemViewHolder<VH> {
        VH build(View itemView);
    }
}