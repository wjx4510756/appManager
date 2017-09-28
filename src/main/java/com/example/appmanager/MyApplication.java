package com.example.appmanager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 11070562 on 2017/9/27.
 */

public class MyApplication extends Application {

    private static Context mContext;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
    }

    public static Context getContext(){
        return mContext;
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public  static SharedPreferences.Editor getEditor(){
        return editor;
    }
}
