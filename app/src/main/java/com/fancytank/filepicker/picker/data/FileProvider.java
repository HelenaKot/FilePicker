package com.fancytank.filepicker.picker.data;

import android.os.Environment;
import android.support.annotation.Nullable;

import com.fancytank.filepicker.R;
import com.fancytank.filepicker.picker.exception.FileProviderException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.fancytank.filepicker.picker.exception.ExceptionType.ACCESS_ERROR;
import static com.fancytank.filepicker.picker.exception.ExceptionType.NOT_MOUNTED;
import static com.fancytank.filepicker.picker.exception.ExceptionType.USB_ACTIVE;

public class FileProvider {

    public ArrayList<FileItem> provide(@Nullable final File directory) throws FileProviderException {
        return (directory != null) ? listFiles(directory) : listRoots();
    }

    private ArrayList<FileItem> listFiles(File browseDir) throws FileProviderException {
        checkForReadExceptions(browseDir);

        ArrayList<FileItem> items = new ArrayList<>();
        File[] files = browseDir.listFiles();

        if (files == null) {
            throw new NullPointerException(browseDir.getAbsolutePath() + " content is NULL");
        }

        Arrays.sort(files, new FileItemComparator());

        items.add(getBackItem(browseDir));

        for (File file : files) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            items.add(makeFileItem(file));
        }

        return items;
    }

    private FileItem makeFileItem(File file) {
        FileItem item = new FileItem();
        item.title = file.getName();
        item.file = file;
        if (file.isDirectory()) {
            item.icon = R.drawable.ic_directory;
            item.subtitle = "Folder";
        } else {
            String fname = file.getName();
            fname = fname.toLowerCase();
            if (fname.endsWith(".jpg") || fname.endsWith(".png")
                    || fname.endsWith(".gif") || fname.endsWith(".jpeg")) {
                //todo :
                item.icon = R.drawable.ic_directory;
                item.thumb = file.getAbsolutePath();
            }
        }
        return item;
    }

    private FileItem getBackItem(File currentDir) {
        FileItem back = new FileItem();
        back.title = "..";
        back.icon = R.drawable.ic_directory;
        back.file = currentDir.getParentFile();
        return back;
    }

    private void checkForReadExceptions(File browseDir) throws FileProviderException {
        if (!browseDir.canRead()) {
            if (browseDir.getAbsolutePath().startsWith(Environment.getExternalStorageDirectory().toString())) {
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
                    if (Environment.MEDIA_SHARED.equals(Environment.getExternalStorageState())) {
                        throw new FileProviderException(USB_ACTIVE);
                    } else {
                        throw new FileProviderException(NOT_MOUNTED);
                    }
                }
            }
            throw new FileProviderException(ACCESS_ERROR);
        }
    }

    private ArrayList<FileItem> listRoots() {
        ArrayList<FileItem> items = new ArrayList<>();

        items.add(getDefaultExternalStorage());
        items.add(getRoot());

        return items;
    }

    private FileItem getRoot() {
        FileItem root = new FileItem();
        root.title = "/";
        root.subtitle = "SystemRoot";
        root.icon = R.drawable.ic_directory;
        root.file = new File("/");
        return root;
    }

    private FileItem getDefaultExternalStorage() {
        FileItem storage = new FileItem();
        storage.title = (Environment.isExternalStorageRemovable() ? "SdCard" : "InternalStorage");
        storage.icon = Environment.isExternalStorageRemovable() ? R.drawable.ic_external_storage : R.drawable.ic_storage;
        storage.file = Environment.getExternalStorageDirectory();
        return storage;
    }

}
