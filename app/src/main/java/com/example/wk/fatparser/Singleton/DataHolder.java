package com.example.wk.fatparser.Singleton;

import com.example.wk.fatparser.POJOsForConvert.CGlobal;

public class DataHolder {
    private static DataHolder dataHolder;
    private static CGlobal cGlobal;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }

    public static CGlobal getcGlobal() {
        return cGlobal;
    }

    public static void setcGlobal(CGlobal cGlobal) {
        DataHolder.cGlobal = cGlobal;
    }
}
