package com.example.skin;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.i_to_sky.skin_library.activity.BaseActivity;
import com.example.i_to_sky.skin_library.manager.SkinManager;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DemoPlugin.apk";
    private final String PACKAGE_NAME = "com.example.i_to_sky.plugin_resources";

    Button mChangeSkinBtn;
    Button mResetSkinBtn;
    Button mGreenSkinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mChangeSkinBtn = (Button)findViewById(R.id.change_skin_btn);
        mChangeSkinBtn.setOnClickListener(this);
        mResetSkinBtn = (Button) findViewById(R.id.reset_skin_btn);
        mResetSkinBtn.setOnClickListener(this);
        mGreenSkinBtn = (Button) findViewById(R.id.change_skin_green_btn);
        mGreenSkinBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.change_skin_btn:
                changeSkin(SDCARD_PATH , PACKAGE_NAME);
                break;
            case R.id.reset_skin_btn:
                SkinManager.getInstance().removeSkinInfo();
                break;
            case R.id.change_skin_green_btn:
                changeSkin(SDCARD_PATH, PACKAGE_NAME, "green");
                break;

        }
    }
}
