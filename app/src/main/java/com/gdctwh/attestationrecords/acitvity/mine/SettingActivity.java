package com.gdctwh.attestationrecords.acitvity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.utils.LogUtils;
import com.gdctwh.attestationrecords.utils.OtherUtils;
import com.gdctwh.attestationrecords.utils.ToastUtils;
import com.gdctwh.attestationrecords.widget.BottomMenuDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;

public class SettingActivity extends BaseActionBarActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";

    @BindView(R.id.img_head)
    RoundedImageView imgHead;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.et_signature)
    EditText etSignature;
    @BindView(R.id.btn_save)
    Button btnSave;
    BottomMenuDialog selectDialog;


    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int PHONE_CROP = 3;
    private Uri imgUri; // 由于android手机的图片基本都会很大，所以建议用Uri，而不用Bitmap

    Uri mCutUri;
    private String IMAGE_TYPE = "image/*";

    @Override
    protected void initEvent() {
        super.initEvent();
        imgHead.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_setting;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                showSelectDialog();
                break;
        }
    }

    private void showSelectDialog() {
        selectDialog = new BottomMenuDialog.Builder(this)
                .setTitle("更换头像")
                .addMenu("从手机相册选择", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDialog.dismiss();
                        getImgFromPhoto();

                    }
                }).addMenu("拍一张", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDialog.dismiss();
                        getImgFromCamera();
                    }
                }).create();

        selectDialog.show();
    }

    /**
     * 从手机中获取图片
     */
    private void getImgFromPhoto() {
            /*Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image*//*");
            startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);*/

        //调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    /**
     * 调用系统相机拍照
     */
    private void getImgFromCamera() {

        //创建一个file，用来存储拍照后的照片
        File outputfile = new File(this.getExternalCacheDir(), "output.png");
        try {
            if (outputfile.exists()) {
                outputfile.delete();//删除
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imgUri = FileProvider.getUriForFile(this,
                    "com.gdctwh.attestationRecords.fileprovider", //可以是任意字符串
                    outputfile);
        } else {
            imgUri = Uri.fromFile(outputfile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, PHOTO_PICKED_FROM_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            //ToastUtils.showShortToast(this, "照片获取失败！");
            return;
        }
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            ToastUtils.showShortToast(this, "储存不可用！");
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_FROM_FILE: //从相册图片后返回的uri
                //启动裁剪
                startActivityForResult(CutForPhoto(data.getData()), PHONE_CROP);
                break;
            case PHOTO_PICKED_FROM_CAMERA: //相机返回的 uri
                //启动裁剪
                String path = this.getExternalCacheDir().getPath();
                String name = "output.png";
                startActivityForResult(CutForCamera(path, name), PHONE_CROP);
                break;
            case PHONE_CROP:
                try {
                    //获取裁剪后的图片，并显示出来
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            this.getContentResolver().openInputStream(mCutUri));
                    imgHead.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 图片裁剪
     *
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            LogUtils.d(TAG, "CutForPhoto: " + cutfile);
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            Log.d(TAG, "mCameraUri: " + mCutUri);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", OtherUtils.dip2px(this, 200)); //200dp
            intent.putExtra("outputY", OtherUtils.dip2px(this, 200));
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, IMAGE_TYPE);
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 拍照之后，启动裁剪
     *
     * @param camerapath 路径
     * @param imgname    img 的名字
     * @return
     */
    @NonNull
    private Intent CutForCamera(String camerapath, String imgname) {
        try {

            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(camerapath, imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(this,
                        "com.rachel.studyapp.fileprovider",
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", OtherUtils.dip2px(this, 200));
            intent.putExtra("outputY", OtherUtils.dip2px(this, 200));
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
