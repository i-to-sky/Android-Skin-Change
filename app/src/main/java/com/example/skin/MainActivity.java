package com.example.skin;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.i_to_sky.skin_library.activity.BaseActivity;
import com.example.i_to_sky.skin_library.manager.SkinManager;

import java.io.File;

public class MainActivity extends BaseActivity {

    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DemoPlugin.apk";
    private final String PACKAGE_NAME = "com.example.i_to_sky.plugin_resources";

    Button mChangeSkinBtn;
    Button mResetSkinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mChangeSkinBtn = (Button)findViewById(R.id.change_skin_btn);
        mChangeSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin(SDCARD_PATH , PACKAGE_NAME);
            }
        });
        mResetSkinBtn = (Button) findViewById(R.id.reset_skin_btn);
        mResetSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().removeSkinInfo();
            }
        });
    }

}
