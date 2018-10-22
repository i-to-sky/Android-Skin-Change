package com.example.i_to_sky.skin_library.listener;

/**
 * Created by weiyupei on 2018/10/22.
 */

public interface ILoadSkinListener {

    int UNKNOWN_ERROR_CODE = 0;
    int ILLEGAL_PARAMS_ERROR_CODE = 1;


    DefaultLoadSkinListener DEFAULT_LOAD_SKIN_LISTENER = new DefaultLoadSkinListener();

    void onLoadStart();

    void onLoadComplete();

    void onLoadError(int errorCode);

    class DefaultLoadSkinListener implements ILoadSkinListener{

        @Override
        public void onLoadStart() {

        }

        @Override
        public void onLoadComplete() {

        }

        @Override
        public void onLoadError(int errorCode) {

        }
    }

}
