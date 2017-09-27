package com.zhaoguan.mpluslibs.exception;

import com.lt.volley.http.NetworkResponse;
import com.lt.volley.http.ResponseBody;
import com.lt.volley.http.StatusLine;
import com.lt.volley.http.error.VolleyError;

/**
 * Created by husong on 2017/6/27.
 */
public class MPlusNotLoginError extends VolleyError {
    public MPlusNotLoginError(Throwable cause) {
        super(cause);
        StatusLine statusLine = new StatusLine(204, "User not login Error");
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBytes("MPlus Not Login Error".getBytes());
        mNetworkResponse = new NetworkResponse(statusLine, responseBody, null);
    }

    @Override
    public int getType() {
        return -1;
    }
}
