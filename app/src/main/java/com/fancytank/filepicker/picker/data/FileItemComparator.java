package com.fancytank.filepicker.picker.data;

import java.io.File;
import java.util.Comparator;

public class FileItemComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        if (o1.isDirectory() != o2.isDirectory()) {
            return o1.isDirectory() ? -1 : 1;
        }
        return o1.getName().compareToIgnoreCase(o2.getName());
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
