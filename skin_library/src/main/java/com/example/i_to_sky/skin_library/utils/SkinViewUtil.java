package com.example.i_to_sky.skin_library.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.i_to_sky.skin_library.config.SkinConfig;
import com.example.i_to_sky.skin_library.skinview.SkinAttr;
import com.example.i_to_sky.skin_library.skinview.SkinView;
import com.example.i_to_sky.skin_library.skinview.type.BackgroundType;
import com.example.i_to_sky.skin_library.skinview.type.BaseAttrType;
import com.example.i_to_sky.skin_library.skinview.type.SrcType;
import com.example.i_to_sky.skin_library.skinview.type.TextColorType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyupei on 2018/10/15.
 */

public class SkinViewUtil {

    public static List<SkinView> getSkinViews(View view) {
        List<SkinView> skinViews = new ArrayList<>();
        parseSkinViews(view, skinViews);
        return skinViews;
    }

    public static void parseSkinViews(View view, List<SkinView> skinViews) {

        SkinView skinView = parseSkinView(view);

        if (skinView != null) {
            skinViews.add(skinView);
        }

        if (view instanceof ViewGroup) {

            ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                parseSkinViews(child, skinViews);
            }

        }
    }

    public static SkinView parseSkinView(View view) {

        Object tag = view.getTag();

        if (tag == null || !(tag instanceof String)) {
            return null;
        }

        List<SkinAttr> skinAttrs = parseTag(((String) tag));
        if (!skinAttrs.isEmpty()) {
            return new SkinView(view, skinAttrs);
        }

        return null;

    }

    public static List<SkinAttr> parseTag(String tag) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        if (TextUtils.isEmpty(tag)) {
            return skinAttrs;
        }

        String[] items = tag.split("[|]");
        for (String item : items) {

            if (!item.startsWith(SkinConfig.SKIN_PREFIX)){
                continue;
            }
            String[] resItems = item.split(":");
            if (resItems.length != 3){
                continue;
            }

            String resName = resItems[1];
            String resType = resItems[2];

            BaseAttrType attrType = getSupportAttrType(resType);
            if (attrType == null) {
                continue;
            }
            SkinAttr attr = new SkinAttr(attrType, resName);
            skinAttrs.add(attr);
        }
        return skinAttrs;
    }

    private static BaseAttrType getSupportAttrType(String type){

        switch (type){
            case BaseAttrType.TYPE_BACKGROUND:
                return new BackgroundType();
            case BackgroundType.TYPE_SRC:
                return new SrcType();
            case BackgroundType.TYPE_TEXTCOLOR:
                return new TextColorType();
        }

        return null;

    }

}
