package com.example.i_to_sky.skin_library.skinview;

import android.view.View;

import com.example.i_to_sky.skin_library.skinview.type.BaseAttrType;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class SkinAttr {

    public String mResName;
    public BaseAttrType mAttrType;



    public SkinAttr(BaseAttrType attrType, String resName) {
        mResName = resName;
        mAttrType = attrType;
    }

    public void apply(View view) {
        mAttrType.apply(view, mResName);
    }

}
