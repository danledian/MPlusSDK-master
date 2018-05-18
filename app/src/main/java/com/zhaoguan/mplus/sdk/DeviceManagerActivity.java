package com.zhaoguan.mplus.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mplus.sdk.utils.ToastUtils;
import com.zhaoguan.mpluslibs.MPlusDevice;
import com.zhaoguan.mpluslibs.entity.UserLab;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceManagerActivity extends BaseActivity {


    private static final String TAG = "DeviceManagerActivity";

    @BindView(R.id.query_bind_device_bt)
    Button mQueryBindDeviceBt;
    @BindView(R.id.config_device_bt)
    Button mConfigDeviceBt;
    @BindView(R.id.content_tv)
    TextView mContentTv;
    @BindView(R.id.unbound_bt)
    Button mUnboundBt;
    @BindView(R.id.set_auto_time_bt)
    Button mSetAutoTimeBt;

    public static void launch(BaseActivity activity) {
        Intent it = new Intent(activity, DeviceManagerActivity.class);
        activity.startActivity(it);
    }

    private String mDeviceInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config_device;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        setTitle(getString(R.string.device_manager));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.query_bind_device_bt, R.id.config_device_bt, R.id.unbound_bt, R.id.set_auto_time_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query_bind_device_bt:

                showProgressDialog(R.string.loading);

                MPlusDevice.get().queryInfoFromServer(UserLab.get().getPatientId(), new VolleyResponse.Listener() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, String.format("deviceInfo:%s", s));
                        dismissProgressDialog();
                        mContentTv.setText(String.format("%s", s));
                        mDeviceInfo = s;
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        handleResponseError(volleyError);
                    }
                });

                break;
            case R.id.config_device_bt:
                DeviceConfigActivity.launch(this);
                break;
            case R.id.unbound_bt:

                Log.d(TAG, "click unbound_bt");
//
                if(TextUtils.isEmpty(mDeviceInfo)){
                    ToastUtils.showShort("请先查询设备信息再解绑");
                    return;
                }

                String objectId = getDeId(mDeviceInfo);
                if(TextUtils.isEmpty(objectId)){
                    ToastUtils.showShort("未绑定设备");
                    return;
                }
                showProgressDialog(R.string.loading);

                MPlusDevice.get().unbindDevice(objectId, new VolleyResponse.Listener() {
                    @Override
                    public void onSuccess(String s) {
                        dismissProgressDialog();
                        ToastUtils.showShort("解绑成功");
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        handleResponseError(volleyError);
                    }
                });

                break;
            case R.id.set_auto_time_bt:

                SetAutoTimeActivity.launch(this);

                break;
            default:
                break;
        }
    }

    private String getDeId(String mDeviceInfo) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(mDeviceInfo);
            if(jsonObject.has("result")){
                org.json.JSONObject jResult = jsonObject.getJSONObject("result");
                return jResult.optString("objectId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
