package com.example.i_to_sky.skin_library.skinview.type;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.i_to_sky.skin_library.utils.LogUtil;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class BackgroundType extends BaseAttrType {

    @Override
    public void applySkin(View view, String resName) {

        try {

            Drawable drawable = getResourcesManager().getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            } else {
                int color = getResourcesManager().getColor(resName);
                view.setBackgroundColor(color);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("BackgroundType applySkin error");
        }

    }
}
