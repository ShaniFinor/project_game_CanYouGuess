package com.example.shanifinor_project.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shanifinor_project.model.service.MyService;
import com.example.shanifinor_project.R;

import me.ibrahimsn.particle.ParticleView;

public class SplashScreenActivity extends AppCompatActivity {

    private ParticleView particleView;
    private ProgressBar progressBar;
    private TextView progressText;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        particleView = findViewById(R.id.particleBackgroundView);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.text_view_progress);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i <= 100) {
                    progressText.setText(i + "%");
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 50);
                } else {
                    handler.removeCallbacks(this);
                    particleView.pause();
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    Intent intentService = new Intent(SplashScreenActivity.this, MyService.class);
              //      startService(intentService);

                    if (MyService.isInstanceCreated()) {
                        stopService(intentService);
                    } else {
                        startService(intentService);
                    }

                }
            }
        }, 50);
    }
}


//package com.example.shanifinor_project.ui.screens;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.example.shanifinor_project.R;
//import com.example.shanifinor_project.model.db.Repository;
//import com.example.shanifinor_project.model.utils.Observer;
//
//import me.ibrahimsn.particle.ParticleView;
//
//public class SplashScreenActivity extends AppCompatActivity implements Observer {
//
//    private ParticleView particleView;
//    private ProgressBar progressBar;
//    private TextView progressText;
//    private int i = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//
//        Repository.getInstance().register(this);
//
//        getSupportActionBar().hide();
//        particleView = findViewById(R.id.particleBackgroundView);
//        progressBar = findViewById(R.id.progress_bar);
//        progressText = findViewById(R.id.text_view_progress);
//    }
//
//    @Override
//    public void update() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (i <= 100) {
//                    progressText.setText(i + "%");
//                    progressBar.setProgress(i);
//                    i++;
//                    handler.postDelayed(this, 50);
//                } else {
//                    handler.removeCallbacks(this);
//                    particleView.pause();
//                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                    Repository.getInstance().unregister(SplashScreenActivity.this);
//                    startActivity(intent);
//                }
//            }
//        }, 50);
//    }
//}