package com.example.shanifinor_project;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {
    private ImageView imgClues;
    private AlertDialog cluesDialog;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private FrameLayout flipView;

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    public Button btnSubmit;
    public GridView gridViewAnswer, gridViewSuggest;
    public ImageView imageViewQuestion;
    int[] imageList = {
            R.drawable.ic_add,
            R.drawable.ic_star
    };
    public char[] answer;
    String correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imgClues = findViewById(R.id.imgClues);
        imgClues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCluesAlert();
            }
        });

        findViews();
        loadAnimations();
        changeCameraDistance();
        flipView = findViewById(R.id.flipView);
      //  flipCard(flipView);

        initView();
    }

    private void initView() {
        gridViewAnswer = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);

        imageViewQuestion = findViewById(R.id.imageHidden);

        setUpList();

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++) {
                    result += String.valueOf(Common.user_submit_answer[i]);
                }
                if (result.equals(correct_answer)) {
                    Toast.makeText(getApplicationContext(), "Finish! this is " + result, Toast.LENGTH_SHORT).show();

                    //Reset
                    Common.count = 0;
                    Common.user_submit_answer = new char[correct_answer.length()];

                    //Set Adapter
                    GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setUpNullList(), getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(), Game.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setUpList();
                }
                else {
                    Toast.makeText(Game.this,"Incorrect!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setUpList() {
        //Random Picture
        Random random = new Random();
        int imageSelected = imageList[random.nextInt(imageList.length)];
        imageViewQuestion.setImageResource(imageSelected);
        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);
        answer = correct_answer.toCharArray();
        Common.user_submit_answer = new char[answer.length];

        //Add answer character to list
        suggestSource.clear();
        for (char item : answer) {
            //convert correct answer to characters and add to list suggest
            //add picture name to list
            suggestSource.add(String.valueOf(item));
        }

        //Random add some characters to list
        //Random some characters from alphabet list and add to our suggest list
        for (int i = answer.length; i < answer.length * 2; i++) {
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);
        }
        //Sort random our list
        Collections.shuffle(suggestSource);

        //Set for GridView
        answerAdapter =new GridViewAnswerAdapter(setUpNullList(),this);
        suggestAdapter=new GridViewSuggestAdapter(suggestSource,this,this);

        suggestAdapter.notifyDataSetChanged();
        answerAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);
    }

    private char[] setUpNullList() {
        char result[] = new char[answer.length];
        for (int i = 0; i < answer.length; i++) {
            result[i] = ' ';
        }
        return result;
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

    private void showCluesAlert() {
        AlertDialog.Builder cluesBuilder = new AlertDialog.Builder(this);
        final CharSequence[] arrClues = {"פתיחת משבצת שבתמונה", "גילוי אות", "העלמת חצי מהאותיות שבמאגר", "גלה מה התמונה המסתתרת"};
        cluesBuilder.setTitle("רמזים").setItems(arrClues, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Game.this, arrClues[i], Toast.LENGTH_SHORT).show();
            }
        });
        cluesDialog = cluesBuilder.create();
        cluesDialog.show();
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (mIsBackVisible == false) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else if (mIsBackVisible) {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }
}