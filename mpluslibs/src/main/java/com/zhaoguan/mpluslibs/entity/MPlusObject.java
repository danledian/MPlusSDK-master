package com.zhaoguan.mpluslibs.entity;

import com.lt.volley.http.Request;
import com.zhaoguan.mpluslibs.IMPlusObject;

/**
 * Created by husong on 2017/6/28.
 */

public class MPlusObject implements IMPlusObject {

    private Request request;

    public MPlusObject(Request request) {
        this.request = request;
    }

    @Override
    public void cancel() {
        if(request != null)
            request.setCanceled(true);
    }
}
