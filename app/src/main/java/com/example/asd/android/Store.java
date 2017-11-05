package com.example.asd.android;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.File;

public class Store {

    // static properties
    private static final String LOG_TAG = "AppStore";
    private static final String BASE_FILE_NAME = "base";

    // instance properties
    private Context context;

    /**
     * @param ctx android.content.Context
     */
    Store(Context ctx) {
        context = ctx;
    }

    /**
     * 写入应用基础存储
     *
     * @param bytes 需要写入的bytes
     */
    public Store writeAppFileStore(byte[] bytes) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(BASE_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(bytes);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * 写入缓存
     *
     * @param bytes 需要写入的bytes
     */
    public Store writeAppCacheStore(byte[] bytes) {
        return this;
    }

    /**
     * 检测外部存储是否可写
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 检测外部存储是否可读
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 获取公共相册
     *
     * @param albumName 相册名
     */
    public File getAlbunmStoreateDir(String albumName) {

        File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(pictures, albumName);

        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }

        return file;
    }

    /**
     * 获取专用相册文档
     *
     * @param albumName 相册名
     */
    public File getAlbunmStoreageDir(String albumName) {
        File pictures = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(pictures, albumName);

        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }

        return file;
    }
}
