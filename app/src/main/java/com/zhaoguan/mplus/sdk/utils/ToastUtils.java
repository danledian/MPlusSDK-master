package com.zhaoguan.mplus.sdk.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.zhaoguan.mplus.sdk.MPlusApplication;

public final class ToastUtils {

    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(MPlusApplication.getContext().getResources().getText(resId).toString(), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        Toast.makeText(MPlusApplication.getContext(), String.format(format, args), duration).show();
    }
}
