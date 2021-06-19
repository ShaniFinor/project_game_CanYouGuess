package com.example.shanifinor_project.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.shanifinor_project.ui.common.Common;
import com.example.shanifinor_project.ui.adapter.GridViewAnswerAdapter;
import com.example.shanifinor_project.ui.adapter.GridViewSuggestAdapter;
import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.db.UserDao;
import com.example.shanifinor_project.model.classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.plattysoft.leonids.ParticleSystem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ImageView imgClues;
    private AlertDialog cluesDialog;

    private List<String> suggestSource = new ArrayList<>();
    private GridViewAnswerAdapter answerAdapter;
    private GridViewSuggestAdapter suggestAdapter;
    private Button btnSubmit;
    public GridView gridViewAnswer, gridViewSuggest;
    private ImageView imageViewQuestion;

    public char[] answer;
    private String correct_answer = "";
    private static Random random = new Random();
    private String answerFinishedLevel = "";
    private int openPictureItems = 0;
    private int maxOpenPictureItems = 5;
    private TextView hiddenAndShownItems;

    //private String databaseStringAnswer = "";
    //private String databaseListStringSuggestedList = "";
    //private List<Integer> userDataSuggestArrayList = new ArrayList<>();

    private char[] result;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private ConstraintLayout constraintLayout;
    private Integer chosenLevelFromMenuActivity;


    private final DatabaseReference myRefImageData = database.getReference("imagesData");

    private ImageButton imageButtonDeleteLetter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        constraintLayout = findViewById(R.id.gameConstraintLayout);
        imgClues = findViewById(R.id.imgClues);
        imgClues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCluesAlert();
            }
        });
        btnSubmit = findViewById(R.id.btnSubmit);
        hiddenAndShownItems = findViewById(R.id.hiddenAndShownItems_tv);
        imageButtonDeleteLetter = findViewById(R.id.imageButton_deleteLetter);

        gridViewAnswer = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);
        imageViewQuestion = findViewById(R.id.imageHidden);

        int numOfWin = User.getInstance().getNumOfWin();
        chosenLevelFromMenuActivity = Objects.requireNonNull(getIntent().getExtras()).getInt("chosenLevel");

        if (chosenLevelFromMenuActivity <= numOfWin) {
            showFinishedLevel();
        } else {
//            List<Integer> userDataSuggestArrayList = User.getInstance().getPlaceChosenFromSuggestedString();
//            for (int j = 0; j < suggestSource.size(); j++) {
//                if (userDataSuggestArrayList.size() > j) {
//                    gridViewSuggest.getChildAt(userDataSuggestArrayList.get(j)).setAlpha(0);
//                    gridViewSuggest.getChildAt(j).setClickable(false);
//                }
//            }


            //databaseListStringSuggestedList = User.getInstance().getSuggestedString();

            //databaseStringAnswer = User.getInstance().getGuessedAnswer();
            runExistLevel();
        }
    }

    public void showAllThePageViews() {
        //set- visibility: visible , clickable: true
        btnSubmit.setVisibility(View.VISIBLE);
        btnSubmit.setClickable(true);
        imgClues.setVisibility(View.VISIBLE);
        imgClues.setClickable(true);
        hiddenAndShownItems.setVisibility(View.VISIBLE);
        hiddenAndShownItems.setClickable(true);
        imageButtonDeleteLetter.setVisibility(View.VISIBLE);
        imageButtonDeleteLetter.setClickable(true);
    }

    public void doNotShowAllThePageViews() {
        //set- visibility: invisible , clickable: false
        btnSubmit.setVisibility(View.INVISIBLE);
        btnSubmit.setClickable(false);
        imgClues.setVisibility(View.INVISIBLE);
        imgClues.setClickable(false);
        hiddenAndShownItems.setVisibility(View.INVISIBLE);
        hiddenAndShownItems.setClickable(false);
        imageButtonDeleteLetter.setVisibility(View.INVISIBLE);
        imageButtonDeleteLetter.setClickable(false);
    }

    public void showPictureInTheOpenSquares() {
        //open the squares - if the user opened squares it opens them.
        List<String> openSquaresList = User.getInstance().getDatabaseOpenSquares();
        for (int i = 0; i < openSquaresList.size(); i++) {
            String tag = openSquaresList.get(i);
            ImageView imageView2 = constraintLayout.findViewWithTag(tag);
            imageView2.setVisibility(View.INVISIBLE);
            imageView2.setClickable(false);
        }
        openPictureItems = openSquaresList.size();
        hiddenAndShownItems.setText((maxOpenPictureItems + User.getInstance().getPlace()) + "/" + openPictureItems);
    }

    public void hideThePicture() {
/**  // close all the squares that were open
//        List<String> openSquaresList = User.getInstance().getDatabaseOpenSquares();
//        for (int i = 0; i < openSquaresList.size(); i++) {
//            String tag = openSquaresList.get(i);
//            ImageView imageView = constraintLayout.findViewWithTag(tag);
//            imageView.setVisibility(View.VISIBLE);
//            imageView.setClickable(true);
//        }
//        openSquaresList.clear();
//        User.getInstance().setDatabaseOpenSquares(openSquaresList);
//        openPictureItems = 0;
//        hiddenAndShownItems.setText(maxOpenPictureItems + "/" + "0"); **/

        // the loop numbers are both 5 because the squares (images that hide the picture) is 5*5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //goes on every square and makes it visible - see the square.
                String tag = i + "," + j;
                ImageView imageView2 = constraintLayout.findViewWithTag(tag);
                imageView2.setVisibility(View.VISIBLE);
                imageView2.setClickable(true);
            }
        }
        User.getInstance().setDatabaseOpenSquares(null);
        openPictureItems = 0;
        hiddenAndShownItems.setText(maxOpenPictureItems + "/" + "0");
    }


    private void setUpList() {
        //   answer = correct_answer.toCharArray();
        answer = new char[correct_answer.length()];
        for (int i = 0; i < correct_answer.length(); i++) {
            answer[i] = correct_answer.charAt(i);
        }
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
        answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);

        suggestAdapter.notifyDataSetChanged();
        answerAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);
    }


    private void showCluesAlert() {
        // shows the clue options
        AlertDialog.Builder cluesBuilder = new AlertDialog.Builder(this);
//        final CharSequence[] arrClues = {"פתיחת משבצת שבתמונה - עולה כוכב אחד", "גילוי אות", "העלמת חצי מהאותיות שבמאגר", "ראה מה התמונה המסתתרת"};
        final CharSequence[] arrClues = {"פתיחת משבצת שבתמונה - עולה כוכב אחד"};
        cluesBuilder.setTitle("רמזים").setItems(arrClues, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(GameActivity.this, arrClues[i], Toast.LENGTH_SHORT).show();
                if (arrClues[i] == "פתיחת משבצת שבתמונה - עולה כוכב אחד") {
                    if (User.getInstance().getStars() > 0) {
                        User.getInstance().setStars(User.getInstance().getStars() - 1);
                        User.getInstance().setPlace(User.getInstance().getPlace() + 1);
                        invalidateOptionsMenu();
                        showPictureInTheOpenSquares();
                    } else {
                        Toast.makeText(GameActivity.this, "איו לך מספיק כוכבים בשביל לקנות רמז זה", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cluesDialog = cluesBuilder.create();
        cluesDialog.show();
    }


    private void runExistLevel() {
        showAllThePageViews(); //set- visibility: visible , clickable: true

        //open the squares - if the user opened squares it opens them.
        showPictureInTheOpenSquares();

        findImagePlaceFromFB(chosenLevelFromMenuActivity.toString());
    }

    private void findImagePlaceFromFB(final String chosenLevel) {
        //finds the image in the given place
        myRefImageData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
                    if (imageSnapshot.getKey().equals(chosenLevel)) {
                        correct_answer = imageSnapshot.child("imageNameForUri").getValue(String.class);
                        Picasso.get().load(imageSnapshot.child("imageUri").getValue(String.class)).into(imageViewQuestion);
                    }
                }
                runLevel();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "onFailure of findImagePlaceFromFB ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void runLevel() {
        setUpAnswer();
        setUpSaveList();
        initView();
    }

    private void setUpAnswer() {
        answer = new char[correct_answer.length()];
        for (int i = 0; i < correct_answer.length(); i++) {
            answer[i] = correct_answer.charAt(i);
        }

        Common.user_submit_answer = new char[answer.length];

        String databaseStringAnswer = User.getInstance().getGuessedAnswer();
        Common.count = databaseStringAnswer.length();
        for (int i = 0; i < databaseStringAnswer.length(); i++) {
            Common.user_submit_answer[i] = databaseStringAnswer.charAt(i);
        }
        answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), this);
        gridViewAnswer.setAdapter(answerAdapter);
    }

    private void setUpSaveList() {
        String databaseListStringSuggestedList = User.getInstance().getSuggestedString();
        if (databaseListStringSuggestedList.equals("")) {
            suggestSource.clear();
            //Add answer character to list
            for (char item : answer) {
                //convert correct answer to characters and add to list suggest
                //add picture name to list
                suggestSource.add(String.valueOf(item));
            }

            //Random add some characters to list
            //Random some characters from alphabet list and add to our suggest list
            if (answer.length < 15) {
                for (int i = answer.length; i < 16; i++) {
                    suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);
                }
            } else {
                for (int i = answer.length; i < answer.length + 5; i++) {
                    suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);
                }
            }
            // Sort random our list
            Collections.shuffle(suggestSource);

            // databaseListStringSuggestedList = suggestSource.toString();
            for (int i = 0; i < suggestSource.size(); i++) {
                databaseListStringSuggestedList += suggestSource.get(i);
                if (i != suggestSource.size() - 1) {
                    databaseListStringSuggestedList += ",";
                }
            }
        } else {
            String[] temp = databaseListStringSuggestedList.split(",");
            suggestSource.clear();
            suggestSource.addAll(Arrays.asList(temp));
        }
        User.getInstance().setSuggestedString(databaseListStringSuggestedList);


        //Set for GridView
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        List<Integer> placeChosenFromSuggestedString = User.getInstance().getPlaceChosenFromSuggestedString();
        if (placeChosenFromSuggestedString != null &&
                !placeChosenFromSuggestedString.isEmpty()) {
            for (int j = 0; j < placeChosenFromSuggestedString.size(); j++) {
                if (gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)) != null) {
                    gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setAlpha(0);
                    gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setClickable(false);
                }
            }
        }
        suggestAdapter.notifyDataSetChanged();
    }

    private void initView() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringResult = "";
                for (int i = 0; i < Common.user_submit_answer.length; i++) {
                    stringResult += String.valueOf(Common.user_submit_answer[i]);
                }
                if (stringResult.equals(correct_answer)) {
                    Toast.makeText(getApplicationContext(), "Finish! this is " + stringResult, Toast.LENGTH_SHORT).show();
                    User.getInstance().setStars(User.getInstance().getStars() + 1);
                    User.getInstance().setNumOfWin(User.getInstance().getNumOfWin() + 1);
                    User.getInstance().setSuggestedString("");
                    User.getInstance().setPlace(0);
                    invalidateOptionsMenu();

                    showFinishedLevel();
                    new ParticleSystem(GameActivity.this, 50, R.drawable.ic_star, 10000000)
                            .setSpeedModuleAndAngleRange(0f, 0.4f, 0, 0)
                            .setRotationSpeed(144)
                            .setAcceleration(0.00005f, 90)
                            .setStartTime(0)
                            .emit(0, 0, 15);
                    gridViewSuggest.setVisibility(View.INVISIBLE);

                    //after 5000 millySeconds the run() method start.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideThePicture();
                            chosenLevelFromMenuActivity++;
                            myRefImageData.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int childrenCount = (int) snapshot.getChildrenCount();
                                    if (childrenCount > User.getInstance().getNumOfWin()) {
                                        gridViewSuggest.setVisibility(View.VISIBLE);
                                        runExistLevel();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                            reset();
                        }
                    }, 5000);
                }
                else {
                    Toast.makeText(GameActivity.this, "Incorrect!!", Toast.LENGTH_SHORT).show();

                    //the animation (YoYo) starts and after 700 millySeconds the method reset() starts.
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .delay(50)
                            .repeat(0)
                            .playOn(gridViewAnswer);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    }, 700);
                }
            }
        });
    }

    private void reset() {
        //Reset
        Common.count = 0;
        Common.user_submit_answer = new char[correct_answer.length()];

        User.getInstance().setPlaceChosenFromSuggestedString(null);
        User.getInstance().setGuessedAnswer("");

        //Set Adapter
        answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), getApplicationContext());
        gridViewAnswer.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();

        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, GameActivity.this);
        gridViewSuggest.setAdapter(suggestAdapter);
    }


    private char[] setResultUserAnswerList() {
        result = new char[answer.length];
        String databaseStringAnswer = User.getInstance().getGuessedAnswer();
        for (int i = 0; i < answer.length; i++) {
            if (databaseStringAnswer.length() > i) {
                result[i] = databaseStringAnswer.charAt(i);
            } else {
                result[i] = ' ';
            }
        }
        return result;
    }

    private char[] setAnswerListForFinishedLevel() {
        result = new char[answerFinishedLevel.length()];
        for (int i = 0; i < answer.length; i++) {
            if (answerFinishedLevel.length() > i) {
                result[i] = answerFinishedLevel.charAt(i);
            } else {
                result[i] = ' ';
            }
        }
        return result;
    }


    public void flipSquares(View view) {
        //onClick
        if (openPictureItems < (maxOpenPictureItems + User.getInstance().getPlace())) {
            String tag = view.getTag().toString();
            ImageView imageViews = constraintLayout.findViewWithTag(tag);
            imageViews.setVisibility(View.INVISIBLE);
            imageViews.setClickable(false);
            List<String> databaseOpenSquares = User.getInstance().getDatabaseOpenSquares();
            databaseOpenSquares.add(tag);
            User.getInstance().setDatabaseOpenSquares(databaseOpenSquares);

            openPictureItems++;
            hiddenAndShownItems.setText((maxOpenPictureItems + User.getInstance().getPlace()) + "/" + openPictureItems);
        }
    }


    public void showFinishedLevel() {
        doNotShowAllThePageViews(); //set- visibility: invisible , clickable: false

        // the loop numbers are both 5 because the squares (images that hide the picture) is 5*5;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //goes on every square and opens it.
                String tag = i + "," + j;
                ImageView imageView2 = constraintLayout.findViewWithTag(tag);
                imageView2.setVisibility(View.INVISIBLE);
                imageView2.setClickable(false);
            }
        }

        setUpImageFinishedLevel();
    }

    private void setUpImageFinishedLevel() {
        myRefImageData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
                    if (imageSnapshot.getKey().equals(chosenLevelFromMenuActivity.toString())) {
                        answerFinishedLevel = imageSnapshot.child("imageNameForUri").getValue(String.class);
                        Picasso.get().load(imageSnapshot.child("imageUri").getValue(String.class)).into(imageViewQuestion);
                    }
                }
                setUpAnswerForFinishedLevel();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setUpAnswerForFinishedLevel() {
        answer = new char[answerFinishedLevel.length()];
        for (int i = 0; i < answerFinishedLevel.length(); i++) {
            answer[i] = answerFinishedLevel.charAt(i);
        }

        Common.user_submit_answer = new char[answer.length];

        Common.count = answer.length;
        for (int i = 0; i < answer.length; i++) {
            Common.user_submit_answer[i] = answerFinishedLevel.charAt(i);
        }
        answerAdapter = new GridViewAnswerAdapter(setAnswerListForFinishedLevel(), this);
        gridViewAnswer.setAdapter(answerAdapter);
    }

    public void deleteWord(View view) {
        //start onClick of imageButton (image of a trash can)

        if (User.getInstance().getGuessedAnswer().length() > 1) {
            User.getInstance().getPlaceChosenFromSuggestedString().remove(User.getInstance().getPlaceChosenFromSuggestedString().size() - 1);
            Common.user_submit_answer[User.getInstance().getPlaceChosenFromSuggestedString().size()] = '\u0000';

            StringBuilder usersCurrentAnswer = new StringBuilder();
            for (int i = 0; i < Common.user_submit_answer.length; i++) {
                if (Common.user_submit_answer[i] != '\u0000') {
                    usersCurrentAnswer.append(Common.user_submit_answer[i]);
                    User.getInstance().setGuessedAnswer(usersCurrentAnswer.toString());
                }
            }
            Common.setCount(usersCurrentAnswer.length());
//            Common.setCount(Common.getCount()-1);
            answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), getApplicationContext());
            gridViewAnswer.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();

            suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, GameActivity.this);
            gridViewSuggest.setAdapter(suggestAdapter);
        } else {
            reset();
        }
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
    protected void onStop() {
        if (chosenLevelFromMenuActivity > User.getInstance().getNumOfWin()) {
            StringBuilder usersCurrentAnswer = new StringBuilder();

            for (int i = 0; i < Common.user_submit_answer.length; i++) {
                if (Common.user_submit_answer[i] != '\u0000') {
                    usersCurrentAnswer.append(Common.user_submit_answer[i]);
                    User.getInstance().setGuessedAnswer(usersCurrentAnswer.toString());
                }
            }
        }
        super.onStop();
    }


//    public void downloadImage(View view){
//
////    private File localFile = null;
////    private StorageReference storageReference;
//
//        // Create a Cloud Storage reference from the app
//        StorageReference storageRef=FirebaseStorage.getInstance().getReference();
//
//        File localFile =null;
//        try {
//            localFile=File.createTempFile("images","jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Uri fileUri=Uri.fromFile(localFile);
//        StorageReference pathReference = storageRef.child("images/star.jpg");
//        pathReference.getFile(localFile).
//                addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        });
//
////        // Create a reference with an initial file path and name
////        StorageReference pathReference = storageRef.child("images/star.jpg");
//
//        // Reference to an image file in Cloud Storage
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
//
//        // ImageView in your Activity
//        ImageView imageView = findViewById(R.id.imageHidden);
//
//        // Download directly from StorageReference using Glide
//        // (See MyAppGlideModule for Loader registration)
//        Glide.with(this /* context */)
//                .load(storageReference)
//                .into(imageView);
//    }


//on create
//        findViews();
//        loadAnimations();
//        changeCameraDistance();
//        flipView = findViewById(R.id.flipView);
//        flipCard(flipView);


//    private void changeCameraDistance() {
//        int distance = 8000;
//        float scale = getResources().getDisplayMetrics().density * distance;
//        mCardFrontLayout.setCameraDistance(scale);
//        mCardBackLayout.setCameraDistance(scale);
//    }
//
//    private void loadAnimations() {
//        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
//        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
//    }
//
//    private void findViews() {
//        mCardBackLayout = findViewById(R.id.card_back);
//        mCardFrontLayout = findViewById(R.id.card_front);
//    }
//
//    public void flipCard(View view) {
//        if (mIsBackVisible == false) {
//            mSetRightOut.setTarget(mCardFrontLayout);
//            mSetLeftIn.setTarget(mCardBackLayout);
//            mSetRightOut.start();
//            mSetLeftIn.start();
//            mIsBackVisible = true;
//        } else if (mIsBackVisible) {
//            mSetRightOut.setTarget(mCardBackLayout);
//            mSetLeftIn.setTarget(mCardFrontLayout);
//            mSetRightOut.start();
//            mSetLeftIn.start();
//            mIsBackVisible = false;
//        }
//    }

}