package com.example.i_to_sky.skin_library.skinview.type;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import com.example.i_to_sky.skin_library.utils.LogUtil;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class TextColorType extends BaseAttrType {

    @Override
    public void applySkin(View view, String resName) {

        try {
            ColorStateList colorStateList = getResourcesManager().getColorStateList(resName);
            if (colorStateList == null || !(view instanceof TextView)){
                return;
            }

            ((TextView)view).setTextColor(colorStateList);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("TextColorType applySkin error");
        }

    }
}
