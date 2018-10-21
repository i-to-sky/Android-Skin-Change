package com.example.i_to_sky.skin_library.skinview.type;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.i_to_sky.skin_library.utils.LogUtil;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class SrcType extends BaseAttrType {

    @Override
    public void applySkin(View view, String resName) {

        try {
            Drawable drawable = getResourcesManager().getDrawableByName(resName);
            if (drawable == null || !(view instanceof ImageView)) {
                return;
            }
            ((ImageView)view).setImageDrawable(drawable);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("srcType applySkin error");
        }


    }
}
