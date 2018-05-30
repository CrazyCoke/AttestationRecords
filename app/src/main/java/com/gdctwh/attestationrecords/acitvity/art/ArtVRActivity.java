package com.gdctwh.attestationrecords.acitvity.art;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtVRActivity extends BaseActionBarActivity {

    private static final String TAG = "ArtVRActivity";
    @BindView(R.id.vr_pan_view)
    VrPanoramaView vrPanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position",-1);
        load360Image(position);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_art_vr;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_vr;
    }


    /**
     * 加载360度全景图片
     */
    private void load360Image(int position) {
        vrPanView = (VrPanoramaView) findViewById(R.id.vr_pan_view);
        /**获取assets文件夹下的图片**/
        InputStream open = null;
        try {
            if ( position != -1 && position <= 5){
                open = getAssets().open("vr_img" + position  + ".jpg");
            }else {
                open = getAssets().open("andes.jpg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(open);
        /**设置加载VR图片的相关设置**/
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        if (position != -1 && position <= 5){
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
        }else {
            options.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
        }
        /**设置加载VR图片监听**/
        vrPanView.setEventListener(new VrPanoramaEventListener() {
            /**
             * 显示模式改变回调
             * 1.默认
             * 2.全屏模式
             * 3.VR观看模式，即横屏分屏模式
             * @param newDisplayMode 模式
             */
            @Override
            public void onDisplayModeChanged(int newDisplayMode) {
                super.onDisplayModeChanged(newDisplayMode);
                Log.d(TAG, "onDisplayModeChanged()->newDisplayMode=" + newDisplayMode);
            }

            /**
             * 加载VR图片失败回调
             * @param errorMessage
             */
            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Log.d(TAG, "onLoadError()->errorMessage=" + errorMessage);
            }

            /**
             * 加载VR图片成功回调
             */
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                Log.d(TAG, "onLoadSuccess->图片加载成功");
            }

            /**
             * 点击VR图片回调
             */
            @Override
            public void onClick() {
                super.onClick();
                Log.d(TAG, "onClick()");
            }
        });
        /**加载VR图片**/
        vrPanView.loadImageFromBitmap(bitmap, options);
    }

    @Override
    protected void onDestroy() {
        //一定要在onDestroy()时调用，用来释放占用内存
        vrPanView.pauseRendering();
        vrPanView.shutdown();
        super.onDestroy();

    }

}
