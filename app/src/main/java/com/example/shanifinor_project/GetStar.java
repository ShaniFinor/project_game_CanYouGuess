package com.example.shanifinor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.plattysoft.leonids.ParticleSystem;

public class GetStar extends AppCompatActivity implements View.OnClickListener {
// game in order to get a star. after pressing 100 times on the button and its starts an annotate of a star, the user gets a star.
    private Button btnButton;
    private TextView txtCounter;
    private ImageView img_star_animation;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_star);

        btnButton = findViewById(R.id.btnButton);
        txtCounter = findViewById(R.id.txtCounter);
        img_star_animation = findViewById(R.id.img_star_animation);
        btnButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        points++;
        txtCounter.setText("" + points);

        if (points == 15) {
            btnButton.setClickable(false);
            points = 0;
            img_star_animation.setVisibility(View.VISIBLE);
            StartAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_anim);
                    img_star_animation.setAnimation(animation);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            YoYo.with(Techniques.Shake)
                                    .duration(700)
                                    .delay(50)
                                    .repeat(0)
                                    .playOn(img_star_animation);
                        }
                    },750);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            YoYo.with(Techniques.ZoomOut)
//                                    .duration(700)
//                                    .delay(60)
//                                    .repeat(0)
//                                    .playOn(img_star_animation);
                            img_star_animation.setVisibility(View.INVISIBLE);
                            doConfettiAnimation();
                        }
                    }, 2000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtCounter.setText("0");
                            btnButton.setClickable(true);
                        }
                    }, 2000);
                }
            }, 3500);
        }
    }

    private void StartAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.image_star_game_anim);
        img_star_animation.startAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item_stare_points = menu.findItem(R.id.star_points);
        item_stare_points.setTitle("1");

        MenuItem item_level = menu.findItem(R.id.level);
        item_level.setTitle("שלב: " + "1");

        return true;
    }

    public void doConfettiAnimation() {
        findViewById(R.id.img_star_animation).post(new Runnable() {
            @Override
            public void run() {
                new ParticleSystem(GetStar.this, 100, R.drawable.ic_star, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.img_star_animation), 100);
            }
        });
    }
}