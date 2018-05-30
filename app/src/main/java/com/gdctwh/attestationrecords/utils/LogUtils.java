package com.gdctwh.attestationrecords.utils;

import android.util.Log;

/**
 * Created by CokaZhang on 2018/3/23.
 * log工具类
 */
public class LogUtils {

    public static boolean isDebug = true;

    public static void d(String TAG,String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }
    public static void i(String TAG,String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }
    public static void e(String TAG,String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }
    public static void w(String TAG,String msg) {
        if (isDebug)
            Log.w(TAG, msg);
    }
    public static void v(String TAG,String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }
}
