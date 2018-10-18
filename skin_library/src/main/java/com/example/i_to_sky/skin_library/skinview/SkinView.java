package com.example.i_to_sky.skin_library.skinview;

import android.view.View;

import java.util.List;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class SkinView {

    public View mView;
    public List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        mView = view;
        mAttrs = skinAttrs;
    }

    public void applySkin() {
        if (mView == null) {
            return;
        }

        for (SkinAttr attr : mAttrs) {
            attr.applySkin(mView);
        }
    }

}
