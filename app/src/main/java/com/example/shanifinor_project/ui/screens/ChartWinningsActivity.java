package com.example.shanifinor_project.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.db.Repository;
import com.example.shanifinor_project.ui.adapter.WinningsChartAdapter;
import com.example.shanifinor_project.model.db.UserDao;
import com.example.shanifinor_project.model.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * list of the users. shows them in recyclerView
 */
public class ChartWinningsActivity extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRefUsers = database.getReference("users");
    private UserDao usersFromData=new UserDao();
    private UserDao cUser=new UserDao();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_winnings);

        RecyclerView recyclerView_winnings = findViewById(R.id.recyclerview_winnings);
        RecyclerView.LayoutManager layoutManager_chart = new LinearLayoutManager(ChartWinningsActivity.this);
        recyclerView_winnings.setLayoutManager(layoutManager_chart);

        final ArrayList<UserDao> winnings = new ArrayList<UserDao>();
        final WinningsChartAdapter winningsChartAdapter = new WinningsChartAdapter(winnings);
        recyclerView_winnings.setAdapter(winningsChartAdapter); //connecting between the Adapter and the RecyclerView

        myRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                winnings.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    usersFromData = userSnapshot.getValue(UserDao.class);
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        if (currentUser.getEmail().equals(usersFromData.getEmail())) {
                            cUser = usersFromData;
                        }
                    }
                    winnings.add(usersFromData);
                }
                Collections.sort(winnings, new Comparator<UserDao>() {
                    @Override
                    public int compare(UserDao o1, UserDao o2) {
                        if(o1.getNumOfWin()>o2.getNumOfWin()){
                            return -1;
                        }
                        else if(o1.getNumOfWin()<o2.getNumOfWin()){
                            return 1;
                        }
                        return 0;
                    }
                });
                winningsChartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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

    public UserDao getCurrentUser() {
        return cUser;
    }
}