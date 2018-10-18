package com.example.i_to_sky.skin_library.skinview.type;

import android.view.View;

/**
 * Created by weiyupei on 2018/10/15.
 */

public abstract class BaseAttrType {

    public final static String TYPE_BACKGROUND = "background";
    public final static String TYPE_TEXTCOLOR = "textColor";
    public final static String TYPE_SRC = "src";

    public abstract void applySkin(View view, String resName);

}
