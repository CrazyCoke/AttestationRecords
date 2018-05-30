package com.gdctwh.attestationrecords.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by CokaZhang on 2018/3/23 0005.
 * SharedPreference工具类
 */

public class SharedPreferenceUtils {
    public static boolean getBoolean(Context context,String key){
        return getBoolean(context,key,false);
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
     return   PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key,defaultValue);
    }
    public static int getInt(Context context,String key){
        return getInt(context,key, -1);
    }
    public static int getInt(Context context,String key,int defaultValue){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key,defaultValue);
    }
    public static String getString(Context context,String key){
       return getString(context,key,null);
    }
    public static String getString(Context context,String key,String defaultValue){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key,defaultValue);
    }

    public static boolean putBoolean(Context context,String key,boolean value){
       SharedPreferences.Editor  editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key,value);
        return editor.commit();
    }
    public static boolean putInt(Context context,String key,int value){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    public static boolean putString(Context context,String key,String value){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        return editor.commit();
    }

}
