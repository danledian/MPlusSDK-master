package com.zhaoguan.mplus.sdk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

//        System.loadLibrary("CRCLibrary");

//        Test.test();

    }

    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.config_device_bt:
                DeviceManagerActivity.launch(this);
                break;
            case R.id.about_user_bt:
                LoginActivity.launch(this);
                break;
            case R.id.get_report_bt:
                GetReportActivity.launch(this);
                break;
            default:
                break;
        }

    }
}
