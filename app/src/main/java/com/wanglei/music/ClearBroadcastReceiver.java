package com.wanglei.music;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ClearBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive", "oncomple");
        //音乐播放完成 监听广播 程序退出
        //接收到通知 退出程序
        System.exit(0);
    }
}
