package com.chinasofti.smartclassroomtv.utils;

import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;

public class ToastUtil {
    /**
     *
     * @param context  上下文
     * @param content  显示的内容
     * @param duration  显示时长，单位毫秒
     */
    public static void show(Context context, String content, int duration){
        new ToastDialog(context).setContentText(content).setDuration(duration).show();
    }
}
