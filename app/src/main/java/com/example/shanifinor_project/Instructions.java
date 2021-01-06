package com.example.shanifinor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Instructions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        TextView textView_title1=findViewById(R.id.textView_title1);
        String text="המשחק";
        SpannableString spannableString=new SpannableString(text);
        UnderlineSpan underlineSpan=new UnderlineSpan();
        spannableString.setSpan(underlineSpan,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_title1.setText(spannableString);
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
        item_level.setTitle("שלב: "+"1");

        return true;
    }
}