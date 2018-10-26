package com.example.i_to_sky.skin_library.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.i_to_sky.skin_library.listener.ILoadSkinListener;
import com.example.i_to_sky.skin_library.listener.ISkinUpdate;
import com.example.i_to_sky.skin_library.manager.SkinManager;
import com.example.i_to_sky.skin_library.skinview.SkinView;

import java.util.List;

/**
 * Created by weiyupei on 2018/10/14.
 */

public class BaseActivity extends AppCompatActivity implements ISkinUpdate, ILoadSkinListener {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

    private String mSkinPluginPath;
    private String mSkinPluginPackage;
    private String mSuffix;

    private ViewGroup mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));
        mContentView = findViewById(android.R.id.content);
        SkinManager.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unRegister(this);
    }

    @Override
    public void onSkinUpdate() {
        mContentView.post(new Runnable() {
            @Override
            public void run() {
                updateView(mContentView);
            }
        });
    }

    @Override
    public void onNotifySkinUpdate() {
        updateView(mContentView);
    }

    public void updateView(View view) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(view);
        if (skinViews == null || skinViews.isEmpty()) {
            return;
        }
        for (SkinView skinView : skinViews) {
            skinView.applySkin();
        }
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadComplete() {

    }

    @Override
    public void onLoadError(int errorCode) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doAfterRequest(requestCode, grantResults);
    }

    private void doAfterRequest(int requestCode, int [] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SkinManager.getInstance().changeSkinByPlugin(mSkinPluginPath, mSkinPluginPackage, mSuffix,this);
            }
        }
    }

    protected void changeSkin(String skinPluginPath, String skinPluginPackage) {
        changeSkin(skinPluginPath, skinPluginPackage, "");
    }

    protected void changeSkin(String skinPluginPath, String skinPluginPackage, String suffix) {
        mSkinPluginPath = skinPluginPath;
        mSkinPluginPackage = skinPluginPackage;
        mSuffix = suffix;
        if (SkinManager.getInstance().hasSelfPermission()) {
            SkinManager.getInstance().changeSkinByPlugin(skinPluginPath, skinPluginPackage, suffix, this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }
}
