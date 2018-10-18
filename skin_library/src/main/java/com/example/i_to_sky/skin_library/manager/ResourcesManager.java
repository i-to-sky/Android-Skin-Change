package com.example.i_to_sky.skin_library.manager;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.i_to_sky.skin_library.utils.LogUtil;

/**
 * Created by weiyupei on 2018/10/18.
 */

public class ResourcesManager {

    private static final String DEFTYPE_DRAWABLE = "drawable";
    private static final String DEFTYPE_COLOR = "color";

    private Resources mResources;
    private String mPluginPackageName;

    public ResourcesManager(Resources resources, String pluginPackageName) {
        mResources = resources;
        mPluginPackageName = pluginPackageName;
    }

    public Drawable getDrawableByName(String name){

        try {
            LogUtil.d("getDrawableByName: name = " + name + " plugin package name = " + mPluginPackageName);
            return mResources.getDrawable(mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName));

        } catch (Exception e){
            e.printStackTrace();
            LogUtil.e("getDrawableByName error");
        }

        return null;

    }

}