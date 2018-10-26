package com.example.skin;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.i_to_sky.skin_library.activity.BaseActivity;
import com.example.i_to_sky.skin_library.manager.SkinManager;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DemoPlugin.apk";
    private final String PACKAGE_NAME = "com.example.i_to_sky.plugin_resources";

    Button mChangeSkinBtn;
    Button mResetSkinBtn;
    Button mGreenSkinBtn;
    Button mBlueSkinBtn;
    ListView mListView;

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
        mBlueSkinBtn = (Button) findViewById(R.id.change_skin_blue_btn);
        mBlueSkinBtn.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_list_view, null);
                updateView(convertView);
                return convertView;
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });

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
            case R.id.change_skin_blue_btn:
                SkinManager.getInstance().changeSkinInner("blue");
                break;

        }
    }
}
