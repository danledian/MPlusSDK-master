package com.zhaoguan.mpluslibs.exception;

import com.lt.volley.http.NetworkResponse;
import com.lt.volley.http.ResponseBody;
import com.lt.volley.http.StatusLine;
import com.lt.volley.http.error.VolleyError;

/**
 * Created by ldd on 2015/12/6.
 */
public class MPlusNotLoginError extends VolleyError {
    public MPlusNotLoginError(Throwable cause) {
        super(cause);
        StatusLine statusLine = new StatusLine(204, "User not login Error");
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBytes("MPlusNotLoginError".getBytes());
        mNetworkResponse = new NetworkResponse(statusLine, responseBody, null);
    }

    @Override
    public int getType() {
        return -1;
    }
}
