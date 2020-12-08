package com.wanglei.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    public MediaPlayer mediaPlayer;
    public boolean tag = false;
    private String path;

    public MusicService() {

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //获取从输入框传递过来的路径
        String path = intent.getStringExtra("path");
        this.path = path;
        mediaPlayer = new MediaPlayer();
        //设置播放完成监听
        mediaPlayer.setOnCompletionListener(this);
        try {

            // mediaPlayer.setDataSource("/mnt/sdcard/孙大剩.mp3");
            //设置播放资源
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  通过 Binder 来保持 Activity 和 Service 的通信
    public MyBinder binder = new MyBinder();

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e("oncompletion", "oncomple");
        stopSelf();
        //播放完成发送广播
        Intent intent = new Intent();
        intent.setAction("com.music.action.stop");
        //发送广播
        sendBroadcast(intent);
    }

    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    /**
     * 暂停或者播放
     */
    public void playOrPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}

