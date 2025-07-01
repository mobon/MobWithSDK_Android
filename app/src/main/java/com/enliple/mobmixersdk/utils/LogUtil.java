package com.enliple.mobmixersdk.utils;

import android.annotation.SuppressLint;
import android.util.Log;

public class LogUtil {

    private static boolean isDebug = true;

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    @SuppressLint("LogNotTimber")
    public static void log(String message) {
        if (isDebug) {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length > 1) {
                String fileName = stackTrace[1].getFileName();
                int lineNumber = stackTrace[1].getLineNumber();
                String methodName = stackTrace[1].getMethodName();

                String filenameInfo = String.format(" (%s:%d)", fileName, lineNumber);
                String methodInfo = String.format(" %s() ", methodName);

                Log.e("MobWith", methodInfo + message + filenameInfo);
            } else {
                Log.e("MobWith", message); // fallback
            }
        }
    }
}