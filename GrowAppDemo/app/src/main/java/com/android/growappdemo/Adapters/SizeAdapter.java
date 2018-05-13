package com.android.growappdemo.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.growappdemo.Models.Http.Size;
import com.android.growappdemo.R;

import java.util.List;


public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder> {

    public interface SizeEventWatcher {
        void onSizeSelected(Size size);
        void onSizeDeselected(Size size);
    }

    private SizeEventWatcher sizeListener;
    private List<Size> sizes;
    private Context context;

    public void setSizeListener(SizeEventWatcher sizeListener) {
        this.sizeListener = sizeListener;
    }

    public SizeAdapter(Context context, List<Size> sizes) {
        this.context =  context;
        this.sizes = sizes;
    }

    @Override
    public SizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.size_item, parent, false);
        return new SizeViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(SizeViewHolder holder, int position) {
        Size size = sizes.get(position);

        if (size.getAvailable()) {
            holder.size.setBackgroundDrawable(context.getDrawable(R.drawable.circle));
            holder.size.setTextColor(Color.WHITE);
        } else {
            holder.size.setBackgroundDrawable(context.getDrawable(R.drawable.size_disabled));
            holder.size.setTextColor(Color.WHITE);
        }
        holder.size.setText(size.getSize());
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }

    public class SizeViewHolder extends RecyclerView.ViewHolder {

        private TextView size;
        public SizeViewHolder(View itemView) {
            super(itemView);

            size = (TextView) itemView.findViewById(R.id.size_id);
        }
    }
}
