package com.gdctwh.attestationrecords.factory;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.gdctwh.attestationrecords.fragment.main.ArtFragment;
import com.gdctwh.attestationrecords.fragment.main.AuthenticateFragment;
import com.gdctwh.attestationrecords.fragment.main.MineFragment;
import com.gdctwh.attestationrecords.fragment.main.NewsFragment;
import com.gdctwh.attestationrecords.utils.IConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MainFragmentFactory {

    private static MainFragmentFactory mInstance;
    private Map<String, Fragment> mFragmentMap = new HashMap<String, Fragment>();
    private Fragment mFragment;
    private static final String TAG = "MainFragmentFactory";

    private MainFragmentFactory() {
    }

    public static MainFragmentFactory getmInstance() {

        if (mInstance == null) {
            synchronized (MainFragmentFactory.class) {
                if (mInstance == null) {
                    mInstance = new MainFragmentFactory();
                }
            }
        }
        return mInstance;
    }

    public Fragment getMainFragment(String fragmentName) {
        switch (fragmentName) {
            case IConstants.FRAGMENTTAG.FRAGMENT_NEWS:
                if (mFragmentMap.containsKey(IConstants.FRAGMENTTAG.FRAGMENT_NEWS)) {
                    mFragment = mFragmentMap.get(IConstants.FRAGMENTTAG.FRAGMENT_NEWS);
                    Log.d(TAG, "getMainFragment: had");
                } else {
                    Log.d(TAG, "getMainFragment: new");
                    mFragment = new NewsFragment();
                    mFragmentMap.put(IConstants.FRAGMENTTAG.FRAGMENT_NEWS, mFragment);
                }
                break;
            case IConstants.FRAGMENTTAG.FRAGMENT_ART:
                if (mFragmentMap.containsKey(IConstants.FRAGMENTTAG.FRAGMENT_ART)) {
                    mFragment = mFragmentMap.get(IConstants.FRAGMENTTAG.FRAGMENT_ART);
                } else {
                    mFragment = new ArtFragment();
                    mFragmentMap.put(IConstants.FRAGMENTTAG.FRAGMENT_ART, mFragment);
                }
                break;
            case IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE:
                if (mFragmentMap.containsKey(IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE)) {
                    mFragment = mFragmentMap.get(IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE);
                } else {
                    mFragment = new AuthenticateFragment();
                    mFragmentMap.put(IConstants.FRAGMENTTAG.FRAGMENT_AUTHENTICATE, mFragment);
                }
                break;
            case IConstants.FRAGMENTTAG.FRAGMENT_MINE:
                if (mFragmentMap.containsKey(IConstants.FRAGMENTTAG.FRAGMENT_MINE)) {
                    mFragment = mFragmentMap.get(IConstants.FRAGMENTTAG.FRAGMENT_MINE);
                } else {
                    mFragment = new MineFragment();
                    mFragmentMap.put(IConstants.FRAGMENTTAG.FRAGMENT_MINE, mFragment);
                }
                break;
            default:
                break;

        }
        return mFragment;
    }

}
