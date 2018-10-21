package com.example.i_to_sky.skin_library.skinview.type;

import android.view.View;

import com.example.i_to_sky.skin_library.manager.ResourcesManager;
import com.example.i_to_sky.skin_library.manager.SkinManager;

/**
 * Created by weiyupei on 2018/10/15.
 */

public abstract class BaseAttrType {

    public final static String TYPE_BACKGROUND = "background";
    public final static String TYPE_TEXTCOLOR = "textColor";
    public final static String TYPE_SRC = "src";

    public abstract void applySkin(View view, String resName);

    public ResourcesManager getResourcesManager() {
        return SkinManager.getInstance().getResourcesManager();
    }

}
