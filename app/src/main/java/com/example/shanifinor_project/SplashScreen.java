package com.example.shanifinor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.ibrahimsn.particle.ParticleView;

public class SplashScreen extends AppCompatActivity {

    private ParticleView particleView;
    private ProgressBar progressBar;
    private TextView progressText;
    private int i= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
particleView=findViewById(R.id.particleBackgroundView);
        progressBar=findViewById(R.id.progress_bar);
        progressText=findViewById(R.id.text_view_progress);

        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i<=100){
                    progressText.setText(i+"%");
                    progressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this,50);
                }
                else {
                    handler.removeCallbacks(this);
                    particleView.pause();
                    Intent intent= new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        },50);
    }
}