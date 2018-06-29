package com.zhaoguan.mplus.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mplus.sdk.utils.ToastUtils;
import com.zhaoguan.mpluslibs.IMPlusObject;
import com.zhaoguan.mpluslibs.MPlusDevice;
import com.zhaoguan.mpluslibs.RegexUtils;
import com.zhaoguan.mpluslibs.entity.UserLab;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetAutoTimeActivity extends BActivity {

    private static final String TAG = "SetAutoTimeActivity";

    private static final long MAX_PERIOD = 12 * 60 * 60 * 1000;
    private static final long MIN_PERIOD = 4 * 60 * 60 * 1000;

    public static void launch(BActivity activity){
        Intent it = new Intent(activity, SetAutoTimeActivity.class);
        activity.startActivity(it);
    }


    @BindView(R.id.startTime_et)
    EditText mStartTimeEt;
    @BindView(R.id.endTime_et)
    EditText mEndTimeEt;
    @BindView(R.id.save_bt)
    Button mSaveBt;

    private IMPlusObject mSetAutoTestTimeObject;

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_auto_time;
    }

    @OnClick(R.id.save_bt)
    public void onViewClicked() {
        String startTime = mStartTimeEt.getText().toString().trim();
        String endTime = mEndTimeEt.getText().toString().trim();
        String period = String.format("%s-%s", startTime, endTime);
        Log.d(TAG, String.format("period:%s", period));
        if(!RegexUtils.isPeriod(period)){
            ToastUtils.showShort(R.string.period_format_error);
            return;
        }
        if(!isLegalPeriod(startTime, endTime, MIN_PERIOD, MAX_PERIOD)){
            ToastUtils.showShort(R.string.period_tips);
            return;
        }
        showProgressDialog(R.string.loading);
        if(mSetAutoTestTimeObject != null){
            mSetAutoTestTimeObject.cancel();
        }
        mSetAutoTestTimeObject = MPlusDevice.get().setAutoTestTime(UserLab.get().getPatientId(), period, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.showShort(R.string.save_success);
                dismissProgressDialog();
            }

            @Override
            public void onError(VolleyError volleyError) {
                handleResponseError(volleyError);
                dismissProgressDialog();
            }
        });
    }


    private boolean isLegalPeriod(String startTime, String endTime, long min, long max){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            long startT = formatter.parse(startTime).getTime();
            long endT = formatter.parse(endTime).getTime();
            if(endT < startT){
                endT += 24 * 60 * 60 * 1000;
            }
            long period = endT - startT;
            if(period >= min && period <= max){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
