package com.tools.jj.tools;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.tools.jj.tools.activity.permission.PermissionsActivity;
import com.tools.jj.tools.activity.permission.PermissionsChecker;
import com.tools.jj.tools.imageload.IBitmapCallBack;
import com.tools.jj.tools.imageload.ImageLoader;
import com.tools.jj.tools.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    private TextView tvTest;
    private ImageView ivTest;

    //wumaoyuan
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            LogUtil.d("缺少权限");
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        } else {
            //必须先处理完毕权限授权后才可以执行以下方法
            LogUtil.d("全部授权");
            initActivity();
        }
    }

    private void initActivity() {
        tvTest=findViewById(R.id.tv_test);
        ivTest=findViewById(R.id.iv_test);
        String textUrl="http://img4.imgtn.bdimg.com/it/u=2936355529,2533421941&fm=27&gp=0.jpg";
        String textUrl2="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2598121514,583614246&fm=15&gp=0.jpg";
        ImageLoader.getInstance().load(textUrl).fitCenter().into(ivTest);
        ImageLoader.getInstance().load(textUrl2).resize(50,50).bitmapCallBack(new IBitmapCallBack() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap) {
                Drawable drawable= new BitmapDrawable(getResources(),bitmap);
                tvTest.setBackground(drawable);
            }

            @Override
            public void onBitmapFailed(Exception e) {
                LogUtil.i(e.toString());

            }
        }).into(tvTest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

}
