package com.example.i_to_sky.skin_library.manager;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.example.i_to_sky.skin_library.utils.LogUtil;

/**
 * Created by weiyupei on 2018/10/18.
 */

public class ResourcesManager {

    private static final String DEFTYPE_DRAWABLE = "drawable";
    private static final String DEFTYPE_COLOR = "color";

    private Resources mResources;
    private String mPluginPackageName;
    private String mSuffix;

    public ResourcesManager(Resources resources, String pluginPackageName, String suffix) {
        mResources = resources;
        mPluginPackageName = pluginPackageName;
        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    public String getPackageName() {
        return mPluginPackageName;
    }

    public void setResourcesSuffix(String suffix) {
        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    public String getResourcesSuffix() {
        return mSuffix;
    }

    public Drawable getDrawableByName(String name) {

        try {
            LogUtil.d("getDrawableByName: name = " + name + " plugin package name = " + mPluginPackageName);
            name = appendSuffix(name);
            return mResources.getDrawable(mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName));

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("getDrawableByName error");
        }

        return null;

    }

    public int getColor(String name) {

        try {
            LogUtil.d("getColor: name = " + name + " plugin package name = " + mPluginPackageName);
            name = appendSuffix(name);
            return mResources.getColor(mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            LogUtil.e("getColor error");
        }
        return 0;
    }

    public ColorStateList getColorStateList(String name) {

        try {
            LogUtil.d("getColorState: name = " + name + " plugin package name = " + mPluginPackageName);
            name = appendSuffix(name);
            return mResources.getColorStateList(mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            LogUtil.e("getColorStateList error");
        }
        return null;
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix)) {
            name = name + "_" + mSuffix;
        }
        return name;
    }

}
