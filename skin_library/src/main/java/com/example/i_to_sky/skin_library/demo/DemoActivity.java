package com.example.i_to_sky.skin_library.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.example.i_to_sky.skin_library.listener.ISkinUpdate;
import com.example.i_to_sky.skin_library.manager.SkinManager;

import java.util.List;

/**
 * Created by weiyupei on 2018/10/14.
 */

public class DemoActivity extends AppCompatActivity implements ISkinUpdate{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegister(this);
    }

    @Override
    public void onSkinUpdate() {
        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                updateView();
            }
        });
    }

    //待实现
    @Override
    public void onNotifySkinUpdate() {

    }

    //待实现
    private void updateView(){

    }

}
