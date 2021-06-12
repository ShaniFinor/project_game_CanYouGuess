package com.example.shanifinor_project.ui.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.example.shanifinor_project.model.db.UserDao;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button changeProfile;
    private EditText newUserName, newUserEmail;
    private ImageButton userLogout;
    private ImageView imageProfile;
    static final int IMAGE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        changeProfile = findViewById(R.id.changeProfile_button);
        newUserName = findViewById(R.id.newName_editText);
        newUserEmail = findViewById(R.id.newEmail_editText);
        userLogout = findViewById(R.id.logout_imageView);
        imageProfile = findViewById(R.id.imageViewProfile);

        newUserName.setText(User.getInstance().getName());
        newUserEmail.setText(User.getInstance().getEmail());

        newUserName.setFocusable(false);
        newUserEmail.setFocusable(false);
        if(!User.getInstance().getIcon().equals("") && !User.getInstance().getIcon().equals(null)) {
            Picasso.get().load(User.getInstance().getIcon()).into(imageProfile);
        }
        changeProfile.setOnClickListener(this);
        userLogout.setOnClickListener(this);
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

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == changeProfile) {
            if (!newUserName.isFocusable() && !newUserEmail.isFocusable()) {
                newUserEmail.setFocusableInTouchMode(true);
                newUserName.setFocusableInTouchMode(true);

                changeProfile.setText("שמירת  שינויים");
            } else {
                saveProfileChange();
                // hide the keyboard
                InputMethodManager hideKeyboard = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                hideKeyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

                changeProfile.setText("עריכה");
            }
        }

        if (v == userLogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("אתם בטוחים שאתם רוצים להתנתק?")
                    .setCancelable(false)
                    .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            User.getInstance().logOut();
                            UserProfileActivity.this.finish();
                        }
                    })
                    .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setInverseBackgroundForced(true);
            alert.show();
        }
    }


    public void nextPageToChangeImageProfile(View view) {
        Intent intent = new Intent(UserProfileActivity.this, ImageItemsProfileActivity.class);
        startActivityForResult(intent, IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //get answer from image choose gallery options
        // this method starts when you press on image from the options.
        if (requestCode == IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                        String url = data.getStringExtra("url");
                        User.getInstance().setIcon(url);
                        Picasso.get().load(User.getInstance().getIcon()).into(imageProfile);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveProfileChange() {
        if (!newUserEmail.getText().toString().isEmpty()) {
            User.getInstance().setEmail(newUserEmail.getText().toString());
        }
        if (!newUserName.getText().toString().isEmpty()) {
            User.getInstance().setName(newUserName.getText().toString());
        }
        newUserName.setFocusable(false);
        newUserEmail.setFocusable(false);
    }
}