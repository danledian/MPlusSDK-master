package com.zhaoguan.mplus.sdk;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lt.volley.http.error.VolleyError;

import java.io.UnsupportedEncodingException;

/**
 * Created by husong on 2017/6/28.
 */

public abstract class BActivity extends AppCompatActivity{

    private static final String TAG = "BActivity";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    public void handleResponseError(VolleyError response) {
        dismissProgressDialog();
        String msg = null;
        if(response == null || response.mNetworkResponse == null){
            showToast(R.string.server_error);
            return;
        }
        int status = response.mNetworkResponse.getStatusCode();
        try {
            msg = new String(response.mNetworkResponse.getStatusMessage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, String.format("http ErrorMessage:%s, status:%d", msg, status));
        showToast(msg);
    }

    public void showToast(String msg){
        Toast
        .makeText(MPlusApplication.getContext(), msg, Toast.LENGTH_SHORT)
        .show();
    }

    public void showToast(int resId){
        showToast(getString(resId));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void showProgressDialog(String msg, boolean cancelable){
        if(mProgressDialog == null){
            mProgressDialog = ProgressDialog.show(this, null, msg, false, cancelable);
        }else {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void showProgressDialog(String msg){
        showProgressDialog(msg, false);
    }

    public void showProgressDialog(int resId){
        showProgressDialog(getString(resId), false);
    }

    public void dismissProgressDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
