package com.tools.jj.tools.activity.permission;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.tools.jj.tools.utils.LogUtil;

/**
 * Created by HeJiaJun on 2018/8/27.
 * Email:1021661582@qq.com
 * des:
 * version: 1.0.0
 */

public abstract class BasePermissionActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    private  String[] permissionList;
    private PermissionsChecker mPermissionsChecker; // 权限检测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionList=setRequestString();
        mPermissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPermissionsChecker.lacksPermissions(permissionList)) {
            LogUtil.d("全部授权");
            initActivity();
        } else {
            LogUtil.d("缺少权限");
            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, permissionList);

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }

    public abstract String[] setRequestString();
    public abstract void initActivity();
}
