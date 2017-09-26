package com.zhaoguan.mplus.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mpluslibs.MPlusDevice;
import com.zhaoguan.mpluslibs.entity.UserLab;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetReportActivity extends BActivity {

    private static final String TAG = "GetReportActivity";



    public static void launch(BActivity activity){
        Intent it = new Intent(activity, GetReportActivity.class);
        activity.startActivity(it);
    }

    @BindView(R.id.startTime_et)
    EditText mStartTimeEt;
    @BindView(R.id.endTime_et)
    EditText mEndTimeEt;
    @BindView(R.id.query_end_bt)
    Button queryEndBt;
    @BindView(R.id.query_bt)
    Button queryBt;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_report;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.query_end_bt, R.id.query_bt})
    public void onViewClicked(View view) {

        String endTime = mEndTimeEt.getText().toString();
        String startTime = mStartTimeEt.getText().toString();

        switch (view.getId()) {
            case R.id.query_end_bt:

                Log.d(TAG, "click query_end_bt");
                if (TextUtils.isEmpty(endTime))
                    return;

                MPlusDevice.get().getReportsWithEnd(UserLab.get().getPatientId(), endTime, 10, new VolleyResponse.Listener() {
                    @Override
                    public void onSuccess(String s) {
                        contentTv.setText(String.format("%s", s));
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        handleResponseError(volleyError);
                    }
                });
                break;
            case R.id.query_bt:

                Log.d(TAG, "click query_bt");

                if (TextUtils.isEmpty(endTime) || TextUtils.isEmpty(startTime))
                    return;

                MPlusDevice.get().getReportsWithEndAndBegin(UserLab.get().getPatientId(), startTime, endTime, new VolleyResponse.Listener() {
                    @Override
                    public void onSuccess(String s) {
                        contentTv.setText(String.format("%s", s));
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        handleResponseError(volleyError);
                    }
                });

                break;
        }
    }
}
