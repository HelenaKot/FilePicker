package com.fancytank.filepicker.picker;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fancytank.filepicker.R;
import com.fancytank.filepicker.picker.adapter.FiletypeAdapter;
import com.fancytank.filepicker.picker.data.FileItem;
import com.fancytank.filepicker.picker.data.FileProvider;
import com.fancytank.filepicker.picker.exception.FileProviderException;

import java.util.ArrayList;

public class PickerFragment extends Fragment {
    RecyclerView recyclerView;
    FiletypeAdapter adapter;
    FileProvider fileProvider;

    private final ArrayList<FileItem> NULL_LIST = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.picker_fragment, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_file_picker);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fileProvider = new FileProvider();

        FiletypeAdapter.DataProvider providerInterface = path -> {
            try {
                return fileProvider.provide(path);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof FileProviderException) {
                    final String errorType = ((FileProviderException) e).getExceptionType().name();
                    Log.e("ERRTYPE", errorType);
                    Toast.makeText(getActivity(), errorType, Toast.LENGTH_SHORT).show();
                }
            }
            return NULL_LIST;
        };

        adapter = new FiletypeAdapter(providerInterface, root.getContext());
        recyclerView.setAdapter(adapter);
    }


}
