package com.zhaoguan.mpluslibs;

import com.lt.volley.http.error.VolleyError;

import java.io.UnsupportedEncodingException;

/**
 * Created by husong on 2017/6/29.
 */

public class MPlusErrorDelivery {

    public static void handleResponseError(VolleyError response, MPlusConfigCallback callback) {
        String msg = null;
        if(response == null || response.mNetworkResponse == null){
            return;
        }
        int status = response.mNetworkResponse.getStatusCode();
        try {
             msg = new String(response.mNetworkResponse.getStatusMessage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(callback != null)
            callback.onFailure(status, msg);
    }
}
