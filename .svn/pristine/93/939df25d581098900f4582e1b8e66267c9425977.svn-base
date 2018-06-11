package com.zhaoguan.mpluslibs.exception;

/**
 * Created by husong on 2017/6/27.
 */

public abstract class MPlusError extends Exception{

    protected static int TYPE_INITIALIZE = 1;
    protected static int TYPE_ARGUMENT = 2;

    protected int type;

    public MPlusError(int type) {
        this.type = type;
    }

    public MPlusError(int type, String message) {
        super(message);
        this.type = type;
    }

    public MPlusError(String message, Throwable cause, int type) {
        super(message, cause);
        this.type = type;
    }

    public abstract int getType();
}
