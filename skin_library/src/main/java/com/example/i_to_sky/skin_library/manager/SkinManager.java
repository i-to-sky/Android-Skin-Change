package com.example.i_to_sky.skin_library.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;

import com.example.i_to_sky.skin_library.listener.ISkinUpdate;
import com.example.i_to_sky.skin_library.skinview.SkinView;
import com.example.i_to_sky.skin_library.utils.SPUtil;
import com.example.i_to_sky.skin_library.utils.SkinViewUtil;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyupei on 2018/10/11.
 */

public class SkinManager {

    private static SkinManager mInstance = null;

    private Context mContext;
    private Resources mSkinPluginResources;

    private String mSkinPluginPath;
    private String mSkinPluginPackage;

    private List<ISkinUpdate> mSkinObservers = new ArrayList<>();

    private SkinManager(){}

    public static SkinManager getInstance(){
        if (mInstance == null){
            synchronized (SkinManager.class){
                if (mInstance == null){
                    mInstance = new SkinManager();
                }
            }
        }
        return mInstance;
    }

    /*
     * 首次启动app时候，尝试从缓存中获取保存的插件信息
     * 若获取到的插件信息不合法，或者插件信息不存在，则return
     * 若获取到的插件信息合法，则开始对app进行换肤，恢复到上一次app的皮肤状态
     */
    public void init(Context context){
        mContext = context.getApplicationContext();
        SPUtil.getInstance().init(context);

        String skinPluginPath = SPUtil.getInstance().getSkinPluginPath();
        String skinPluginPackage = SPUtil.getInstance().getSkinPluginPackage();

        try {

            if (!isValidPluginInfo(skinPluginPath, skinPluginPackage)){
                return;
            }

            loadSkinPlugin(skinPluginPath, skinPluginPackage);
            mSkinPluginPath = skinPluginPath;
            mSkinPluginPackage = skinPluginPackage;


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadSkinPlugin(String skinPluginPath, String skinPluginPackage) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPluginPath);

        Resources superResources = mContext.getResources();
        mSkinPluginResources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());


    }

    private boolean isValidPluginInfo(String skinPluginPath, String skinPluginPackage){
        if (TextUtils.isEmpty(skinPluginPath) || TextUtils.isEmpty(skinPluginPackage)){
            return false;
        }

        File skinPluginFile = new File(skinPluginPath);
        if (!skinPluginFile.exists()){
            return false;
        }

        PackageInfo packageInfo = getPackageInfo(skinPluginPath);
        return skinPluginPackage.equals(packageInfo.packageName);
    }

    private PackageInfo getPackageInfo(String skinPluginPath){
        PackageManager packageManager = mContext.getPackageManager();
        return packageManager.getPackageArchiveInfo(skinPluginPath, PackageManager.GET_ACTIVITIES);
    }

    public void notifyChangedListeners(){

        if (mSkinObservers == null){
            return;
        }

        for (ISkinUpdate observer : mSkinObservers){
            observer.onNotifySkinUpdate();
        }

    }



    public void register(ISkinUpdate observer){

        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }

        if (observer != null){

            if (!mSkinObservers.contains(observer)){
                mSkinObservers.add(observer);
            }

            observer.onSkinUpdate();
        }

    }

    public void unRegister(ISkinUpdate observer) {

        if (mSkinObservers == null){
            return;
        }

        if (!mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }

    }

    public List<SkinView> getSkinViews(View view){
        return SkinViewUtil.getSkinViews(view);
    }

    public Resources getSkinPluginResources(){
        return mSkinPluginResources;
    }

}
