package com.zhaoguan.mpluslibs;

import android.util.Log;

import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mpluslibs.entity.MPlusObject;
import com.zhaoguan.mpluslibs.entity.UserLab;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by husong on 2017/6/28.
 */

public class MPlusUser {

    private static MPlusUser mMPlusUser;

    public static MPlusUser get(){
        if(mMPlusUser == null){
            synchronized (MPlusDevice.class){
                if (mMPlusUser == null){
                    mMPlusUser = new MPlusUser();
                }
            }
        }
        return mMPlusUser;
    }


    public IMPlusObject register(final VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().register(listener));
    }

    private void saveLoginInfo(String s) {
        try {
            JSONObject json = new JSONObject(s);
            if(json.has("result")){
                JSONObject result = json.getJSONObject("result");
                if(result.has("patientId")){
                    String patientId = result.getString("patientId");
                    Log.d("login", String.format("login patientId:%s", patientId));
                    UserLab.get().setLogin(patientId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public IMPlusObject login(String patientId, final VolleyResponse.Listener listener) {
        return getMPlusObject(getHttpClient().login(patientId, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String s) {
                saveLoginInfo(s);
                if(listener != null){
                    listener.onSuccess(s);
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                if(listener != null)
                    listener.onError(volleyError);
            }
        }));
    }


    public IMPlusHttp getHttpClient() {
        synchronized (this){
            return MPlusHttpClientImpl.get();
        }
    }

    public IMPlusObject getMPlusObject(Request request) {
        return new MPlusObject(request);
    }

}
