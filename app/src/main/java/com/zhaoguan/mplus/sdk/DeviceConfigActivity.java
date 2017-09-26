package com.zhaoguan.mplus.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhaoguan.mpluslibs.MPlusConfigCallback;
import com.zhaoguan.mpluslibs.MPlusDevice;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceConfigActivity extends BActivity {

    private static final String TAG = "DeviceConfigActivity";


    public static void launch(BActivity activity){
        Intent it = new Intent(activity, DeviceConfigActivity.class);
        activity.startActivity(it);
    }

    @BindView(R.id.wifiName_et)
    EditText mWifiNameEt;
    @BindView(R.id.wifiPwd_et)
    EditText mWifiPwdEt;
    @BindView(R.id.start_config_bt)
    Button mStartConfigBt;
    @BindView(R.id.content_tv)
    TextView contentTv;


    private String wifiName;
    private String wifiPwd;


    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(R.string.config_device);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_config;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

        WifiManager manager = (WifiManager) MPlusApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(manager.getConnectionInfo() != null){
            String ssid = manager.getConnectionInfo().getSSID();
            mWifiNameEt.setText(ssid.substring(1, ssid.length() - 1));
        }
    }

    @OnClick(R.id.start_config_bt)
    public void onViewClicked() {
        wifiName = mWifiNameEt.getText().toString();
        wifiPwd = mWifiPwdEt.getText().toString();

        if(TextUtils.isEmpty(wifiName))
            return;

        MPlusDevice.get().voiceConfig(wifiName, wifiPwd, new MPlusConfigCallback() {
            @Override
            public void onStartVoice() {
                Log.d(TAG, "onStartVoice");
            }

            @Override
            public void onFailure(int code, String message) {
                showToast(String.format("onFailure:%s", message));
            }

            @Override
            public void onConfigSuccess(String response) {
                Log.d(TAG, "onConfigSuccess" + response);
                showToast("配置成功");
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        MPlusDevice.get().stopVoiceConfig();
    }
}
