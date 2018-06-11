package com.zhaoguan.mplus.sdk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mpluslibs.IMPlusObject;
import com.zhaoguan.mpluslibs.MPlusUser;
import com.zhaoguan.mpluslibs.entity.UserLab;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_name_et)
    EditText mLoginNameEt;
    @BindView(R.id.login_pwd_et)
    EditText mLoginPwdEt;
    @BindView(R.id.login_bt)
    Button mLoginBt;
    @BindView(R.id.content_tv)
    TextView mContentTv;

    public static void launch(BaseActivity activity) {
        Intent it = new Intent(activity, LoginActivity.class);
        activity.startActivity(it);
    }


    private IMPlusObject mLoginObject;

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle(getString(R.string.login));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(UserLab.get().getPatientId())){
            mLoginPwdEt.setText(UserLab.get().getPatientId());
        }else {
            mLoginPwdEt.setText("5afcf4bf9f5454546f000f43");
        }
    }

    @OnClick({R.id.login_bt, R.id.register_bt})
    public void onViewClicked(View view) {

        switch (view.getId()){
            case R.id.login_bt:
                login();
                break;
            case R.id.register_bt:
                showRegisterDialog();
                break;
        }

    }


    private void showRegisterDialog(){
        new AlertDialog.Builder(this)
        .setMessage("本Demo默认已提供账号进行测试，是否继续注册新账号")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                register();
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        })
        .create()
        .show();

    }


    private IMPlusObject mRegisterObject;

    private void register() {

        mRegisterObject = MPlusUser.get().register(new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                saveLoginInfo(s);
                mContentTv.setText(String.format("注册信息：%s", s));
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });

    }

    private void saveLoginInfo(String s) {
        try {
            JSONObject json = new JSONObject(s);
            if(json.has("result")){
                JSONObject result = json.getJSONObject("result");
                if(result.has("patientId")){
                    String patientId = result.getString("patientId");
                    Log.d("login", String.format("login patientId:%s", patientId));
                    mLoginPwdEt.setText(patientId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void login() {
        String pwd = mLoginPwdEt.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入手机号或者密码", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog("正在登录");
        mLoginObject = MPlusUser.get().login(pwd, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String s) {
                dismissProgressDialog();
                mContentTv.setText(String.format("登录信息：%s", s));
            }

            @Override
            public void onError(VolleyError volleyError) {
                handleResponseError(volleyError);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoginObject != null)
            mLoginObject.cancel();
        if(mRegisterObject != null)
            mRegisterObject.cancel();
    }
}
