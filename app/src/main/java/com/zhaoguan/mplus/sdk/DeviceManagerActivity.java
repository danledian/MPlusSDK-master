package com.zhaoguan.mplus.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mplus.sdk.bean.DeviceInfoResponse;
import com.zhaoguan.mpluslibs.MPlusDevice;
import com.zhaoguan.mpluslibs.entity.UserLab;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceManagerActivity extends BActivity {


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

    public static void launch(BActivity activity) {
        Intent it = new Intent(activity, DeviceManagerActivity.class);
        activity.startActivity(it);
    }

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

//                Log.d(TAG, "click unbound_bt");
//
//                showProgressDialog(R.string.loading);
//
//                MPlusDevice.get().queryInfoFromServer(UserLab.get().getPatientId(), new VolleyResponse.Listener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Log.d(TAG, String.format("deviceInfo:%s", s));
//                        if(TextUtils.isEmpty(s)){
//                            return;
//                        }
//                        DeviceInfoResponse.ResultsBean bindDevice = getBindDevice(s);
//
//                        if(bindDevice == null){
//                            dismissProgressDialog();
//                            Toast.makeText(DeviceManagerActivity.this, "未绑定设备", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        unbindMPlusDevice(bindDevice.getObjectId());
//
//                    }
//
//                    @Override
//                    public void onError(VolleyError volleyError) {
//                        handleResponseError(volleyError);
//                    }
//                });
                break;
            case R.id.set_auto_time_bt:

                SetAutoTimeActivity.launch(this);
//                showProgressDialog(R.string.loading);
//
//                MPlusDevice.get().queryInfoFromServer(UserLab.get().getPatientId(), new VolleyResponse.Listener() {
//                    @Override
//                    public void onSuccess(String s) {
//                        Log.d(TAG, String.format("deviceInfo:%s", s));
//                        if(TextUtils.isEmpty(s)){
//                            return;
//                        }
//                        DeviceInfoResponse.ResultsBean bindDevice = getBindDevice(s);
//
//                        if(bindDevice == null){
//                            dismissProgressDialog();
//                            Toast.makeText(DeviceManagerActivity.this, "未绑定设备", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        setAutoTime(bindDevice.getObjectId(), "21:15-09:00");
//                    }
//
//                    @Override
//                    public void onError(VolleyError volleyError) {
//                        handleResponseError(volleyError);
//                    }
//                });

                break;
            default:
                break;
        }
    }

//    /**
//     *
//     * @param objectId
//     * @param period  格式必须为：21:00-09:00
//     */
//    private void setAutoTime(String objectId, String period) {
//
//        Log.d(TAG, String.format("objectId:%s", objectId));
//
//        if(TextUtils.isEmpty(objectId))
//            return;
//
//        MPlusDevice.get().setAutoTestTime(objectId, period, new VolleyResponse.Listener() {
//            @Override
//            public void onSuccess(String s) {
//                dismissProgressDialog();
//                Toast.makeText(DeviceManagerActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(VolleyError volleyError) {
//                handleResponseError(volleyError);
//            }
//        });
//
//    }

    private DeviceInfoResponse.ResultsBean getBindDevice(String s){
        List<DeviceInfoResponse.ResultsBean> deviceList = JSONObject.parseObject(s, DeviceInfoResponse.class).getResults();

        DeviceInfoResponse.ResultsBean bindDevice = null;
        for (DeviceInfoResponse.ResultsBean bean: deviceList){
            if(bean.isActive() && !TextUtils.isEmpty(bean.getObjectId())){
                bindDevice = bean;
                break;
            }
        }
        return bindDevice;
    }

//    private void unbindMPlusDevice(String objectId) {
//
//        Log.d(TAG, String.format("objectId:%s", objectId));
//        MPlusDevice.get().unbindDevice(objectId, new VolleyResponse.Listener() {
//            @Override
//            public void onSuccess(String s) {
//                dismissProgressDialog();
//                Toast.makeText(DeviceManagerActivity.this, "解绑成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(VolleyError volleyError) {
//                handleResponseError(volleyError);
//            }
//        });
//    }





}
