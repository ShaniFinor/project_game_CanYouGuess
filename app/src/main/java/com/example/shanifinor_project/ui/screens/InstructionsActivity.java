package com.example.shanifinor_project.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructionsActivity extends AppCompatActivity {
//    private FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private DatabaseReference myRefStars = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/stars");
//    private DatabaseReference myRefDatabaseNumOfWin = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/numOfWin");

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
        final MenuItem item_stare_points = menu.findItem(R.id.star_points);
        final MenuItem item_level = menu.findItem(R.id.level);

        item_stare_points.setTitle("כוכבים: " + User.getInstance().getStars());
        item_level.setTitle("ניצחונות: " + User.getInstance().getNumOfWin());

//        myRefStars.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                numOfStars = snapshot.getValue(Integer.class);
//                item_stare_points.setTitle(numOfStars + "");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        myRefDatabaseNumOfWin.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                numOfWin = snapshot.getValue(Integer.class);
//                item_level.setTitle("שלב: " + numOfWin);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

        return true;
    }
}