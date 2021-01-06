package com.example.shanifinor_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameMenu extends AppCompatActivity implements View.OnClickListener {
    //the levels in the game. each level leads to a different picture that the user has to guess
    private Button btn_level_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        btn_level_1 = findViewById(R.id.btnLevel1);
        btn_level_1.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if (view == btn_level_1) {
            intent = new Intent(GameMenu.this, Game.class);
        }
        startActivity(intent);
    }
}