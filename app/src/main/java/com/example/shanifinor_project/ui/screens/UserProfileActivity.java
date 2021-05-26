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

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button changeProfile;
    private EditText newUserName, newUserEmail;
    private ImageButton userLogout;
    private ImageView imageProfile;
    private Uri imageLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        changeProfile=findViewById(R.id.changeProfile_button);
        newUserName=findViewById(R.id.newName_editText);
        newUserEmail=findViewById(R.id.newEmail_editText);
        userLogout=findViewById(R.id.logout_imageView);
        imageProfile=findViewById(R.id.imageViewProfile);

        newUserName.setText(User.getInstance().getName());
        newUserEmail.setText(User.getInstance().getEmail());

        newUserName.setFocusable(false);
        newUserEmail.setFocusable(false);

        Picasso.get().load(User.getInstance().getIcon()).into(imageProfile);

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

        item_stare_points.setTitle(User.getInstance().getStars() + "");
        item_level.setTitle("ניצחונות: " + User.getInstance().getNumOfWin());

        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==changeProfile) {
            if (!newUserName.isFocusable() && !newUserEmail.isFocusable()) {
                newUserEmail.setFocusableInTouchMode(true);
                newUserName.setFocusableInTouchMode(true);

                changeProfile.setText("שמירת  שינויים");
            } else {
                saveProfileChange();

                InputMethodManager hideKeyboard = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                hideKeyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);

                changeProfile.setText("עריכה");
            }
        }

        if(v==userLogout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            User.getInstance().logOut();
                            UserProfileActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setInverseBackgroundForced(true);
            alert.show();

        }

    }


//    public void nextPageToAddLevel(View view) {
//        Intent intent=new Intent(UserProfileActivity.this,EditProfileActivity.class);
//        startActivity(intent);
//    }

    public void chooseImageFromLocalFile(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (dataIntent != null) {
                    if (dataIntent.getData() != null) {
                        imageLocation = dataIntent.getData();
                        imageProfile.setImageURI(imageLocation);
                    }
                }
            }
        }
    }


    private void saveProfileChange(){
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