package com.zhaoguan.mpluslibs;

import android.text.TextUtils;
import android.util.Log;

import com.lt.volley.http.Request;
import com.lt.volley.http.VolleyResponse;
import com.lt.volley.http.error.VolleyError;
import com.zhaoguan.mpluslibs.exception.MPlusVolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by husong on 2017/9/26.
 */

public class MPlusDeviceRequest extends Request{

    private static final String TAG = "MPlusDeviceRequest";

    private static final int CODE_NO_DEVICE = 306;
    private static final String CODE_NO_DEVICE_MESSAGE= "Unbound device";

    private IMPlusObject mQueryInfoObject;
    private Request mSetAutoTestRequest;

    public void setAutoTestTime(String patientId, final String period, final VolleyResponse.Listener response){
        if(!RegexUtils.isPeriod(period)){
            throw new IllegalArgumentException("period format error");
        }
        if (mQueryInfoObject != null)
            mQueryInfoObject.cancel();
        mQueryInfoObject = MPlusDevice.get().queryInfoFromServer(patientId, new VolleyResponse.Listener() {
            @Override
            public void onSuccess(String s) {
                String objectId = getDeviceId(s);
                Log.d(TAG, String.format("objectId:%s", objectId));
                if(!TextUtils.isEmpty(objectId)){
                    if(mSetAutoTestRequest != null){
                        mSetAutoTestRequest.setCanceled(true);
                    }
                    mSetAutoTestRequest = MPlusHttpClientImpl.get().setAutoTestTime(objectId, period, response);
                }else {
                    if(response != null){
                        response.onError(new MPlusVolleyError(new RuntimeException(CODE_NO_DEVICE_MESSAGE), CODE_NO_DEVICE, CODE_NO_DEVICE_MESSAGE));
                    }
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                if(response != null){
                    response.onError(volleyError);
                }
            }
        });
    }


    private String getDeviceId(String content){
        try {
            JSONObject json = new JSONObject(content);
            if(json.has("result")){
                JSONObject jResult = json.getJSONObject("result");
                if(jResult.has("objectId")){
                    return jResult.getString("objectId");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setCanceled(boolean isCanceled) {

        if(isCanceled){
            if(mQueryInfoObject != null){
                mQueryInfoObject.cancel();
            }
            if(mSetAutoTestRequest != null){
                mSetAutoTestRequest.setCanceled(true);
            }
        }
    }
}
