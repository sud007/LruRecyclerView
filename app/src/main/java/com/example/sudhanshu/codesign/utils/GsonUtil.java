package com.example.sudhanshu.codesign.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This Project is created by Sudhanshu Singh
 * about.me/sudhanshusingh
 * plus.google.com/+sudhanshusingh
 * Created by sudhanshu on 11/28/2015.
 */
public class GsonUtil {
    // build a GSON
    private static Gson gson = new GsonBuilder().create();

    /**
     * getting a GSON object instance
     *
     * @return
     */
    public static Gson getGson() {
        return gson;
    }

    /**
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     *
     *
     * @param src
     * @param clazz
     * @return
     */
    public static <T> String toJson(T src, Class<T> clazz) {
        return gson.toJson(src, clazz);
    }
}
