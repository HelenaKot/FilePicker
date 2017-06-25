package com.fancytank.filepicker.picker.data;

import java.io.File;

public class FileItem {
    int icon;
    String title;
    String subtitle = "";
    String thumb;
    File file;

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getIcon() {
        return icon;
    }

    public File getFile() {
        return file;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

}
