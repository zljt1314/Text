package com.bway.banndemo;

import android.app.Application;

import org.xutils.x;

/**
 * autor: 李金涛.
 * date:2017/6/27
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
