package com.example.shanifinor_project.ui.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.shanifinor_project.model.service.MyService;
import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.google.firebase.database.FirebaseDatabase;

/**
 * the levels in the game.
 * each level leads to a different picture that the user has to guess
 */
public class GameMenuActivity extends AppCompatActivity {

    private Integer numOfWin = 0;
    private Integer numOfStars;
    private GridView menuGridView;
    private static final String[] gridViewLevel = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18"};
    private MediaPlayer mpClickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        mpClickSound = MediaPlayer.create(this, R.raw.click_sound);

        menuGridView = findViewById(R.id.gameMenuGridView);

        //context: GameMenuActivity.
        // R.layout.gamemenu_levelview: the view of one level in the page of the gridView levels.
        //R.id.list_item: the textView in the "R.layout.gamemenu_levelview".
        //gridViewLevel: the array of string of the numbers of the levels.
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(GameMenuActivity.this,
                R.layout.gamemenu_levelview, R.id.list_item, gridViewLevel);
        menuGridView.setAdapter(menuAdapter);

        numOfWin = User.getInstance().getNumOfWin();

        menuGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (int i = 0; i < numOfWin; i++) {
                    menuGridView.getChildAt(i).setBackgroundResource(R.drawable.finished_level_button);
                }
                menuGridView.getChildAt(numOfWin).setBackgroundResource(R.drawable.current_level_button);
                menuGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (MyService.isInstanceCreated()) { // if the background music is on- make a sound when click on the button.
                    mpClickSound.start();
                }
                if (position <= numOfWin) {
                    Intent intent = new Intent(GameMenuActivity.this, GameActivity.class);
                    intent.putExtra("chosenLevel", position + 1);
                    startActivity(intent);
                }
            }
        });
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

        item_stare_points.setTitle(getResources().getString(R.string.stars)+" "+ User.getInstance().getStars());
        item_level.setTitle(getResources().getString(R.string.wins) +" "+ User.getInstance().getNumOfWin());
        return true;
    }
}