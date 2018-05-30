package com.gdctwh.attestationrecords.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 用于在TextView中显示 html标签中的图片
 */
public class URLImageParser implements Html.ImageGetter {

    private Context context;
    private TextView tvContent;
    private int actX;//实际的宽  放大缩小基于textview的宽度
    private int actY;


    private static final String TAG = "URLImageParser";

    public URLImageParser(Context context, TextView tvContent) {
        this.context = context;
        this.tvContent = tvContent;
        //获取全屏大小

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        actY = metrics.heightPixels ;

        //actx 减去textview的padding
        int paddingPx = Math.round(40 * (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT ));
        actX = widthPixels - paddingPx;
        Log.d(TAG, "URLImageParser: actx =" + actX );
    }

    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        Log.d(TAG, "getDrawable: source = " + source);
        Glide.with(context)
                .load(source)
                .asBitmap()
                //.placeholder(R.drawable.default_duan)
                //.error(R.drawable.default_duan)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }

                    @Override
                    public void onLoadStarted(Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        //加载失败就不管
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int x = resource.getWidth();
                        int y = resource.getHeight();

                            //进行等比例缩放程序
                            Matrix matrix = new Matrix();
                            matrix.postScale((float) (actX * 1.00 / x), (float) (actX * 1.00 / x));
                            //长和宽放大缩小的比例
                            resource = Bitmap.createBitmap(resource, 0, 0, x, y,matrix, true);

                        urlDrawable.bitmap = resource;
                        urlDrawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                        tvContent.invalidate();
                        tvContent.setText(tvContent.getText()); // 解决图文重叠
            }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }


                    @Override
                    public void setRequest(Request request) {

                    }

                    @Override
                    public Request getRequest() {
                        return null;
                    }
                });
        return urlDrawable;
    }

    public class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}