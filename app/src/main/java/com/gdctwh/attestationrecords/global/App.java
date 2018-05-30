package com.gdctwh.attestationrecords.global;

import android.app.Application;

import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


/**
 * Created by Administrator on 2018/4/2.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UMConfigure.init(this,"5ac96da1b27b0a4a5100001f"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx2cbe1ac1139d4dd4", "90e95c265b35397142631f4e008e6fc4");
        //第3个参数为回调地址
        PlatformConfig.setSinaWeibo("2390492479", "7946d7c98a91f909616b2097ee8e596b","http://open.weibo.com/apps/2390492479/privilege/oauth"
        );
        PlatformConfig.setQQZone("1106808744", "fsaqVSBkguZkxYfK");


        MobSDK.init(this);

    }
    public static App getInstance(){
        return instance;
    }
}
