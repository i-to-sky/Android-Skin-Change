package com.example.i_to_sky.skin_library.manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.example.i_to_sky.skin_library.listener.ILoadSkinListener;
import com.example.i_to_sky.skin_library.listener.ISkinUpdate;
import com.example.i_to_sky.skin_library.skinview.SkinView;
import com.example.i_to_sky.skin_library.utils.LogUtil;
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
    private ResourcesManager mResourcesManager;

    private String mSkinPath;
    private String mSkinPackage;
    private String mSuffix;

    private boolean mUsePluginResources;

    private List<ISkinUpdate> mSkinObservers = new ArrayList<>();

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (mInstance == null) {
            synchronized (SkinManager.class) {
                if (mInstance == null) {
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
    public void init(Context context) {
        mContext = context.getApplicationContext();
        SPUtil.getInstance().init(context);

        try {

            if (!hasSelfPermission()) {
                return;
            }

            String skinPluginPath = SPUtil.getInstance().getSkinPluginPath();
            String skinPluginPackage = SPUtil.getInstance().getSkinPluginPackage();
            String suffix = SPUtil.getInstance().getResourceSuffix();

            if (!isValidPluginInfo(skinPluginPath, skinPluginPackage)) {
                return;
            }

            initSkinPluginResource(skinPluginPath, skinPluginPackage, suffix);
            mSkinPath = skinPluginPath;
            mSkinPackage = skinPluginPackage;
            mSuffix = suffix;


        } catch (Exception e) {
            e.printStackTrace();
            SPUtil.getInstance().clear();
            LogUtil.e("init skin plugin error");
        }


    }

    private void initSkinPluginResource(String skinPluginPath, String skinPluginPackage, String suffix) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPluginPath);

        Resources superResources = mContext.getResources();
        mSkinPluginResources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());
        mResourcesManager = new ResourcesManager(mSkinPluginResources, skinPluginPackage, suffix);

        mUsePluginResources = true;
    }

    private boolean isValidPluginInfo(String skinPluginPath, String skinPluginPackage) {
        if (TextUtils.isEmpty(skinPluginPath) || TextUtils.isEmpty(skinPluginPackage)) {
            return false;
        }

        File skinPluginFile = new File(skinPluginPath);
        if (!skinPluginFile.exists()) {
            return false;
        }

        PackageInfo packageInfo = getPackageInfo(skinPluginPath);
        return skinPluginPackage.equals(packageInfo.packageName);
    }

    private PackageInfo getPackageInfo(String skinPluginPath) {
        PackageManager packageManager = mContext.getPackageManager();

        try {
            return packageManager.getPackageArchiveInfo(skinPluginPath, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void notifyChangedListeners() {

        if (mSkinObservers == null) {
            return;
        }

        for (ISkinUpdate observer : mSkinObservers) {
            observer.onNotifySkinUpdate();
        }

    }

    public void register(ISkinUpdate observer) {

        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }

        if (observer != null) {

            if (!mSkinObservers.contains(observer)) {
                mSkinObservers.add(observer);
            }

            observer.onSkinUpdate();
        }

    }

    public void unRegister(ISkinUpdate observer) {

        if (mSkinObservers == null) {
            return;
        }

        if (mSkinObservers.contains(observer)) {
            mSkinObservers.remove(observer);
        }

    }

    private void loadSkin(final String skinPluginPath, final String skinPluginPackage, final String suffix, final ILoadSkinListener loadSkinListener) {

        loadSkinListener.onLoadStart();

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    initSkinPluginResource(skinPluginPath, skinPluginPackage, suffix);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("asynctask init skin error");
                    SPUtil.getInstance().clear();
                    loadSkinListener.onLoadError(ILoadSkinListener.UNKNOWN_ERROR_CODE);
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean isLoadSuccess) {
                super.onPostExecute(isLoadSuccess);
                if (isLoadSuccess) {

                    try {
                        updatePluginInfo(skinPluginPath, skinPluginPackage, suffix);
                        notifyChangedListeners();
                        loadSkinListener.onLoadComplete();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e("asynctask update skin error");
                        SPUtil.getInstance().clear();
                        loadSkinListener.onLoadError(ILoadSkinListener.UNKNOWN_ERROR_CODE);
                    }

                }

            }
        }.execute();

    }

    private void updatePluginInfo(String skinPluginPath, String skinPluginPackage, String suffix) {
        mSkinPath = skinPluginPath;
        mSkinPackage = skinPluginPackage;
        mSuffix = suffix;

        SPUtil.getInstance().setSkinPluginPath(skinPluginPath);
        SPUtil.getInstance().setSkinPluginPackage(skinPluginPackage);
        SPUtil.getInstance().setResourceSuffix(suffix);

    }

    private void clearSkinInfo() {
        mSkinPath = "";
        mSkinPackage = "";
        mSuffix = "";
        mUsePluginResources = false;
        SPUtil.getInstance().clear();
    }

    public void removeSkinInfo() {
        clearSkinInfo();
        notifyChangedListeners();
    }

    //应用内换肤
    public void changeSkinInner(String suffix) {
        clearSkinInfo();
        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
        SPUtil.getInstance().setResourceSuffix(suffix);
        notifyChangedListeners();
    }

    //插件换肤
    public void changeSkinByPlugin(final String skinPluginPath, final String skinPluginPackage, ILoadSkinListener loadSkinListener) {
        changeSkinByPlugin(skinPluginPath, skinPluginPackage, "", loadSkinListener);
    }

    //插件换肤
    public void changeSkinByPlugin(final String skinPluginPath, final String skinPluginPackage, String suffix, ILoadSkinListener loadSkinListener) {

        LogUtil.d("changeSkin");
        if (loadSkinListener == null) {
            LogUtil.d("loadSkinListener is null, set defalut listener");
            loadSkinListener = ILoadSkinListener.DEFAULT_LOAD_SKIN_LISTENER;
        }

        ILoadSkinListener callback = loadSkinListener;

        try {
            if (!isValidPluginInfo(skinPluginPath, skinPluginPackage)) {
                callback.onLoadError(ILoadSkinListener.ILLEGAL_PARAMS_ERROR_CODE);
                return;
            }

            if (isValidResourcesManager(skinPluginPackage)) {

                if (!suffix.equals(mResourcesManager.getResourcesSuffix())) {
                    mResourcesManager.setResourcesSuffix(suffix);
                }

                updatePluginInfo(skinPluginPath, skinPluginPackage, suffix);
                notifyChangedListeners();
                callback.onLoadComplete();
                return;
            }

            loadSkin(skinPluginPath, skinPluginPackage, suffix, callback);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("changeSkin error");
        }


    }

    public List<SkinView> getSkinViews(View view) {
        return SkinViewUtil.getSkinViews(view);
    }

    public ResourcesManager getResourcesManager() {

        if (mResourcesManager == null) {
            mResourcesManager = new ResourcesManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
        }

        if (!mUsePluginResources) {

            if (!isValidResourcesManager(mContext.getPackageName())) {
                mResourcesManager = new ResourcesManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
            }

            if (!mSuffix.equals(mResourcesManager.getResourcesSuffix())) {
                mResourcesManager.setResourcesSuffix(mSuffix);
            }

        }
        return mResourcesManager;
    }

    private boolean isValidResourcesManager(String packageName) {
        return mResourcesManager != null && packageName.equals(mResourcesManager.getPackageName());
    }

    public boolean hasSelfPermission() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

}
