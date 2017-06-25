package com.fancytank.filepicker.picker.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancytank.filepicker.R;

public class FiletypeViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView title;
    TextView subtitle;
    ImageView image;

    public FiletypeViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        title = (TextView) itemView.findViewById(R.id.title);
        subtitle = (TextView) itemView.findViewById(R.id.subtitle);
        image = (ImageView) itemView.findViewById(R.id.image);
    }

}