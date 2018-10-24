package com.example.i_to_sky.skin_library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.i_to_sky.skin_library.config.SkinConfig;

/**
 * Created by weiyupei on 2018/10/12.
 */

public class SPUtil {

    private static SPUtil mInstance;

    private Context mContext;

    private SPUtil(){}

    public static SPUtil getInstance(){
        if (mInstance == null){
            synchronized (SPUtil.class){
                if (mInstance == null){
                    mInstance = new SPUtil();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        mContext = context.getApplicationContext();
    }

    public void clear() {
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.SKIN_PLUGIN_INFO, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public void setSkinPluginPath(String skinPluginPath) {
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.SKIN_PLUGIN_INFO, Context.MODE_PRIVATE);
        sp.edit().putString(SkinConfig.SKIN_PLUGIN_PATH, skinPluginPath);
    }

    public String getSkinPluginPath(){
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.SKIN_PLUGIN_INFO, Context.MODE_PRIVATE);
        return sp.getString(SkinConfig.SKIN_PLUGIN_PATH, "");
    }

    public void setSkinPluginPackage(String skinPluginPackage) {
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.SKIN_PLUGIN_INFO, Context.MODE_PRIVATE);
        sp.edit().putString(SkinConfig.SKIN_PLUGIN_PACKAGE, skinPluginPackage);
    }

    public String getSkinPluginPackage(){
        SharedPreferences sp = mContext.getSharedPreferences(SkinConfig.SKIN_PLUGIN_INFO, Context.MODE_PRIVATE);
        return sp.getString(SkinConfig.SKIN_PLUGIN_PACKAGE, "");
    }

}
