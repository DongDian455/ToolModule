package com.dongdian.jj.toolmodule;



import android.Manifest;
import android.content.Intent;

import com.tools.jj.tools.activity.permission.BasePermissionActivity;

public class MainActivity extends BasePermissionActivity {


    @Override
    public String[] setRequestString() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE};
    }

    @Override
    public void initActivity() {

    }
}
