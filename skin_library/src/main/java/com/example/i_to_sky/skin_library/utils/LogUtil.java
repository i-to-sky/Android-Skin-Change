package com.example.i_to_sky.skin_library.utils;

import android.util.Log;

/**
 * Created by weiyupei on 2018/10/18.
 */

public class LogUtil {

    private static final String TAG = "SkinLog";

    public static boolean mDebug = true;

    public static void d(String msg){
        if (mDebug){
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg){
        if (mDebug){
            Log.e(TAG, msg);
        }
    }

}
