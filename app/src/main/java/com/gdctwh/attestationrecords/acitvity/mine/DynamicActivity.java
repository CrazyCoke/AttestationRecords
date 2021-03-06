package com.gdctwh.attestationrecords.acitvity.mine;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;

import java.net.URL;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class DynamicActivity extends BaseActionBarActivity {

    @Override
    protected void initData() {
        super.initData();
        JZVideoPlayerStandard jzVideoPlayerStandard =  findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "敲敲打打");
        Glide.with(this).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                .into(jzVideoPlayerStandard.thumbImageView);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dynamic;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_dynamic;
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
