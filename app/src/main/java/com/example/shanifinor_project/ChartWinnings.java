package com.example.shanifinor_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class ChartWinnings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_winnings);

        ArrayList<User> winnings = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            winnings.add(new User(i + 1,
                    "ic_star",
                    "name",
                    i
            ));
        }

        RecyclerView recyclerView_winnings = findViewById(R.id.recyclerview_winnings);
        RecyclerView.LayoutManager layoutManager_chart = new LinearLayoutManager(ChartWinnings.this);
        recyclerView_winnings.setLayoutManager(layoutManager_chart);

        WinningsChartAdapter winningsChartAdapter = new WinningsChartAdapter(winnings);
        recyclerView_winnings.setAdapter(winningsChartAdapter);
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
}