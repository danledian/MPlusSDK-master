package com.zhaoguan.mpluslibs.exception;

import com.lt.volley.http.NetworkResponse;
import com.lt.volley.http.ResponseBody;
import com.lt.volley.http.StatusLine;
import com.lt.volley.http.error.VolleyError;

/**
 * Created by husong on 2017/6/27.
 */
public class MPlusVolleyError extends VolleyError {

    public MPlusVolleyError(Throwable cause, int code, String message) {
        super(cause);
        StatusLine statusLine = new StatusLine(code, message);
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBytes(message == null?"".getBytes():message.getBytes());
        mNetworkResponse = new NetworkResponse(statusLine, responseBody, null);
    }

    @Override
    public int getType() {
        return -1;
    }
}
