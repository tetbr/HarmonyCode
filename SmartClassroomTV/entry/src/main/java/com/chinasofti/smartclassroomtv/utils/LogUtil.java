package com.chinasofti.smartclassroomtv.utils;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class LogUtil {
    static HiLogLabel hiLogLabel = new HiLogLabel(HiLog.LOG_APP,0x11,"mynote111");
    public static void inof(String content){
        HiLog.info(hiLogLabel,content);
    }
    public static  void debug(String content){
        HiLog.debug(hiLogLabel,content);
    }

    //类似android的Logcat
    public static void error(String tag,String content){
        HiLog.error(new HiLogLabel(HiLog.LOG_APP,0x11,tag),content);
    }

}
