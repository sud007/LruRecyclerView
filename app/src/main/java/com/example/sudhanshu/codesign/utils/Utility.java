package com.example.sudhanshu.codesign.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sudhanshu on 11/28/2015.
 */
public class Utility {

    /**
     * Saving image to cache with the image name as the key
     * @param bitmap
     * @param key
     */
    public static void saveImageToCache(Bitmap bitmap, String key) {
        SystemLruCache.getInstance().getLru().put(key, bitmap);
    }

    /**
     * retrieving image from the cache.
     * @param key
     * @return
     */
    public static Bitmap getImageFromCache(String key) {

        Bitmap bitmap = null;
        return (Bitmap) SystemLruCache.getInstance().getLru().get(key);

    }

    /**
     * read a file from assests.
     *
     * @param ctx
     * @param fileName
     * @return
     */
    public static String prepString(Context ctx, String fileName) {

        String dummyString = null;
        // To load text file
        InputStream input;
        AssetManager assetManager = ctx.getAssets();

        try {
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            String text = new String(buffer);

            dummyString = text;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dummyString;
    }


}
