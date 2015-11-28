package com.example.sudhanshu.codesign.utils;

import android.support.v4.util.LruCache;

/**
 * Created by sudhanshu on 11/28/2015.
 */
public class SystemLruCache {

    private static SystemLruCache instance;
    private int MAX_SIZE = 1024;
    private LruCache<Object, Object> lru;

    private SystemLruCache() {

        lru = new LruCache<>(MAX_SIZE);

    }

    public static SystemLruCache getInstance() {

        if (instance == null) {

            instance = new SystemLruCache();
        }

        return instance;

    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }
}