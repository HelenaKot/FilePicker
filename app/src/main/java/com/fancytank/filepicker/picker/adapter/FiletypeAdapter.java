package com.fancytank.filepicker.picker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fancytank.filepicker.R;
import com.fancytank.filepicker.picker.data.FileItem;

import java.io.File;
import java.util.List;

public class FiletypeAdapter extends RecyclerView.Adapter<FiletypeViewHolder> {
    private List<FileItem> data;
    private final Context context;
    private DataProvider dataProvider;

    public interface DataProvider {
        List<FileItem> provide(File path);
    }

//    public interface OnClickListener {
//        void onFileItemClicked(int position, FileItem object);
//        void onFilePicked(FileItem object);
//    }

    public FiletypeAdapter(DataProvider dataProvider, Context context) {
        this.dataProvider = dataProvider;
        this.data = dataProvider.provide(null);
        this.context = context;
    }

    @Override
    public FiletypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picker_row, parent, false);
        return new FiletypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FiletypeViewHolder holder, final int position) {
        final FileItem myItem = data.get(position);
        holder.title.setText(myItem.getTitle());
        holder.subtitle.setText(myItem.getSubtitle());
        holder.image.setImageDrawable(context.getResources().getDrawable(myItem.getIcon()));
        holder.view.setOnClickListener(view -> onItemClicked(myItem));
    }

    private void onItemClicked(FileItem item) {
        if (item.isDirectory()) {
            this.data = dataProvider.provide(item.getFile());
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
