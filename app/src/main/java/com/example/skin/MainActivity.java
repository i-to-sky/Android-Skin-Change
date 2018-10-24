package com.example.skin;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.example.i_to_sky.skin_library.activity.BaseActivity;

import java.io.File;

public class MainActivity extends BaseActivity {

    Button mChangeSkinBtn;

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
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DemoPlugin.apk";
                changeSkin(path, "com.example.i_to_sky.plugin_resources");
            }
        });
    }

}
