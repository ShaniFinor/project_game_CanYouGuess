package com.example.shanifinor_project.model.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.shanifinor_project.R;


public class MyService extends Service {
    public static MediaPlayer player;
    private static MyService instance = null;

    public MyService() {
    }
    public static boolean isInstanceCreated() {
        return instance != null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not a bound service");
    }

    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
//        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player = MediaPlayer.create(this, R.raw.bg_music);
        instance=this;
        if (!player.isPlaying()) {
            player.setLooping(true);
            player.start();
        }
        return START_STICKY;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
            instance = null;
        }
    }
}
