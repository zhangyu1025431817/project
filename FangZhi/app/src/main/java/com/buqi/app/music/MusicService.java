package com.buqi.app.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.buqi.app.R;

import java.io.IOException;

/**
 * Created by smacr on 2017/2/21.
 */

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private boolean isReady = false;
    @Override
    public void onCreate() {
        //onCreate在Service的生命周期中只会调用一次
        super.onCreate();
        //初始化媒体播放器
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        if(mediaPlayer == null){
            return;
        }
        mediaPlayer.stop();
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mp.release();
                stopSelf();
                return false;
            }
        });
        try{
            mediaPlayer.prepare();
            isReady = true;
        } catch (IOException e) {
            e.printStackTrace();
            isReady = false;
        }
        if(isReady){
            //将背景音乐设置为循环播放
            mediaPlayer.setLooping(true);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            if(mediaPlayer.isPlaying()){
                //停止播放音乐
                mediaPlayer.stop();
            }
            //释放媒体播放器资源
            mediaPlayer.release();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //每次调用Context的startService都会触发onStartCommand回调方法
        //所以onStartCommand在Service的生命周期中可能会被调用多次
        if(isReady && !mediaPlayer.isPlaying()){
            //播放背景音乐
            mediaPlayer.start();
        }
        return START_STICKY;
    }
}
