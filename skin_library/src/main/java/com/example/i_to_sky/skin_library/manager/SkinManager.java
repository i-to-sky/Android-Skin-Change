package com.example.i_to_sky.skin_library.manager;

import android.content.Context;

/**
 * Created by weiyupei on 2018/10/11.
 */

public class SkinManager {

    private static SkinManager mInstance = null;

    private Context mContext;

    private SkinManager(){}

    private static SkinManager getInstance(){
        if (mInstance == null){
            synchronized (SkinManager.class){
                if (mInstance == null){
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        mContext = context.getApplicationContext();
    }

    public void loadSkin(){

    }

}
