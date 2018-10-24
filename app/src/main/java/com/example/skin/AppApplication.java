package com.example.skin;

import android.app.Application;

import com.example.i_to_sky.skin_library.manager.SkinManager;

/**
 * Created by weiyupei on 2018/10/23.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }

}
