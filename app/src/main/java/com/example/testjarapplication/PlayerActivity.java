package com.example.testjarapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {
    private final static String TAG = "PlayerActivity";

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private String url;
    private SurfaceHolder.Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        initLoad();
        initView();
    }

    private void initLoad() {
        if (getIntent().hasExtra("videourl")) {
            url = getIntent().getStringExtra("videourl");
            Log.d(TAG, "init url : \n" + url);
        }
        callback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.d(TAG, "surfaceCreated");
                startPlay();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                stopPlay();
            }
        };
    }

    private void startPlay() {
        if (mediaPlayer == null && url != null) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(url);
                mediaPlayer.setSurface(surfaceView.getHolder().getSurface());
                mediaPlayer.setOnPreparedListener(MediaPlayer::start);
                mediaPlayer.prepareAsync();
                Log.d(TAG, "startPlay");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopPlay() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initView() {
        surfaceView = findViewById(R.id.act_player_sv);
        surfaceView.getHolder().addCallback(callback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlay();
    }
}
