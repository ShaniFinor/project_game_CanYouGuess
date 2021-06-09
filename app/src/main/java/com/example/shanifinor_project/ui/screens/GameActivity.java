package com.example.shanifinor_project.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    //private Integer numOfStars;

    private List<String> suggestSource = new ArrayList<>();
    private GridViewAnswerAdapter answerAdapter;
    private GridViewSuggestAdapter suggestAdapter;
    private Button btnSubmit;
    public GridView gridViewAnswer, gridViewSuggest;
    private ImageView imageViewQuestion;
    private List<Integer> imageList = new ArrayList<Integer>();
    public char[] answer;
    private String correct_answer = "";
    private static Random random = new Random();
    private int currentImage = 0;
    private String answerFinishedLevel = "";
    //    private GridView gameGridView;
//    private static final String[] gridViewSquares = new String[]{
//            "", "", "", "", "", "", "", "",
//            "", "", "", "", "", "", "", ""};
    private int openPictureItems = 0;
    private int maxOpenPictureItems = 5;
    private TextView hiddenAndShownItems;
    //  private String openImageViewItemsString="";
    //private List<String> openImageViewItemsList = new ArrayList<>();

    //private String databaseStringAnswer = "";
    //private String databaseListStringSuggestedList = "";
    private char[] result;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    /**
     * private DatabaseReference myRefStars = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/stars");
     * private DatabaseReference myRefGuessedAnswer = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/guessedAnswer");
     * private DatabaseReference myRefSuggestedString = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/suggestedString");
     * private DatabaseReference myRefDatabaseOpenSquares = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/databaseOpenSquares");
     * private DatabaseReference myRefPlaceChosenFromSuggestedString = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/placeChosenFromSuggestedString");
     * private DatabaseReference myRefDatabaseNumOfWin = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/numOfWin");
     **/
    private UserDao currentUser;
    //private List<Integer> userDataSuggestArrayList = new ArrayList<>();

    private ConstraintLayout constraintLayout;
    private Integer chosenLevelFromMenuActivity;

    private List<StorageReference> imagesFromStorage;
    private StorageReference storageReference;
    private final DatabaseReference myRefImageData = database.getReference("imagesData");
    private List<String> listImagesName = new ArrayList<String>();
    private File localFile = null;


//    @Override
//    protected void onStart() {
//        super.onStart();
//        myRefImageData.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
//                    if (imageSnapshot.getKey().equals(chosenLevelFromMenuActivity.toString())) {
//                        correct_answer = imageSnapshot.child("imageName").getValue(String.class);
//                        Toast.makeText(getApplicationContext(), "on  "+correct_answer , Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), "onFailure 514 " , Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /**UserDao userDao = User.getInstance().getUserDao();
         String guessedAnswer = User.getInstance().getUserDao().getGuessedAnswer();

         userDao.setGuessedAnswer("a");
         User.getInstance().setUserDao(userDao);**/

        /**DatabaseReference myRefUsers = database.getReference("users");
         myRefUsers.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
        winnings.clear();
        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
        usersFromData = userSnapshot.getValue(UserDao.class);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
        if (currentUser.getEmail().equals(usersFromData.getEmail())) {
        cUser = usersFromData;
        }
        }
        //User user2=new User(usersFromData.getIcon(),usersFromData.getName(),usersFromData.getNumOfWin());
        //winnings.add(user2);
        winnings.add(usersFromData);
        }
        winningsChartAdapter.notifyDataSetChanged();
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {}
        });**/


        constraintLayout = findViewById(R.id.gameConstraintLayout);
        imgClues = findViewById(R.id.imgClues);
        imgClues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCluesAlert();
            }
        });
        btnSubmit = findViewById(R.id.btnSubmit);

//        imageList.add(R.drawable.ic_add);
//        imageList.add(R.drawable.ic_star);

        gridViewAnswer = findViewById(R.id.gridViewAnswer);
        gridViewSuggest = findViewById(R.id.gridViewSuggest);
        imageViewQuestion = findViewById(R.id.imageHidden);

        int numOfWin = User.getInstance().getNumOfWin();
        chosenLevelFromMenuActivity = Objects.requireNonNull(getIntent().getExtras()).getInt("chosenLevel", numOfWin);

        if (chosenLevelFromMenuActivity <= numOfWin) {
            showFinishedLevel();
        } else {
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setClickable(true);
            imgClues.setVisibility(View.VISIBLE);
            imgClues.setClickable(true);

            //shows the squares. and if the user opened squares it opens them.
            showPictureInTheOpenSquares();

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

        /**myRefDatabaseNumOfWin.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
        numOfWin = snapshot.getValue(Integer.class);
        chosenLevelFromMenuActivity = Objects.requireNonNull(getIntent().getExtras()).getInt("chosenLevel", numOfWin);

        if (chosenLevelFromMenuActivity < numOfWin) {
        showFinishedLevel();
        } else {
        myRefDatabaseOpenSquares.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
        openImageViewItemsList.clear();
        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
        openImageViewItemsList.add(userSnapshot.getValue(String.class));
        }
        if (!openImageViewItemsList.isEmpty()) {
        showPictureInOpenSquares();
        }
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {
        }
        });
        //        myRefPlaceChosenFromSuggestedString.addValueEventListener(new ValueEventListener() {
        //            @Override
        //            public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
        //                    userDataSuggestArrayList.add(userSnapshot.getValue(Integer.class));
        //
        //                }
        //                for (int j = 0; j < suggestSource.size(); j++) {
        //                    if(userDataSuggestArrayList.size()> j){
        //                        gridViewSuggest.getChildAt(userDataSuggestArrayList.get(j)).setAlpha(0);
        //                        gridViewSuggest.getChildAt(j).setClickable(false);
        //                    }
        //                }
        //            }
        //            @Override
        //            public void onCancelled(@NonNull DatabaseError error) {
        //            }
        //        });
        myRefSuggestedString.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
        databaseListStringSuggestedList = snapshot.getValue(String.class);
        suggestSource.add(databaseListStringSuggestedList);
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {
        }
        });
        //        myRefPlaceChosenFromSuggestedString.addValueEventListener(new ValueEventListener() {
        //            @Override
        //            public void onDataChange(@NonNull DataSnapshot snapshot) {
        //                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
        //                    userDataSuggestArrayList.add(userSnapshot.getValue(Integer.class));
        //                }
        //            }
        //            @Override
        //            public void onCancelled(@NonNull DatabaseError error) {
        //            }
        //        });
        myRefGuessedAnswer.addValueEventListener(new ValueEventListener() {
        @Override public void onDataChange(@NonNull DataSnapshot snapshot) {
        databaseStringAnswer = snapshot.getValue(String.class);
        runExistLevel();
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {
        }
        });
        }
        }

        @Override public void onCancelled(@NonNull DatabaseError error) {
        }
        });**/

    }


    public void showPictureInTheOpenSquares() {
        List<String> openSquaresList = User.getInstance().getDatabaseOpenSquares();
        for (int i = 0; i < openSquaresList.size(); i++) {
            String tag = openSquaresList.get(i);
            ImageView imageView2 = constraintLayout.findViewWithTag(tag);
            imageView2.setVisibility(View.INVISIBLE);
            imageView2.setClickable(false);
        }
        openPictureItems = openSquaresList.size();
        hiddenAndShownItems = findViewById(R.id.hiddenAndShownItems_tv);
        hiddenAndShownItems.setText((maxOpenPictureItems + User.getInstance().getPlace()) + "/" + openPictureItems);
    }

    public void hideThePicture() {
        // close all the squares tha were open
        List<String> openSquaresList = User.getInstance().getDatabaseOpenSquares();
        for (int i = 0; i < openSquaresList.size(); i++) {
            String tag = openSquaresList.get(i);
            ImageView imageView = constraintLayout.findViewWithTag(tag);
            imageView.setVisibility(View.VISIBLE);
            imageView.setClickable(true);
        }
        openSquaresList.clear();
        User.getInstance().setDatabaseOpenSquares(openSquaresList);
        openPictureItems = 0;
        hiddenAndShownItems = findViewById(R.id.hiddenAndShownItems_tv);
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

//    private void setUpRandomImage() {
//        // Random Picture
//        currentImage = imageList.get(random.nextInt(imageList.size()));
////        DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/place");
////        myRef.setValue(imageList.indexOf((Integer) currentImage));
//        imageViewQuestion.setImageResource(currentImage);
//        correct_answer = getResources().getResourceName(currentImage);
//        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);
//    }

//    private char[] setUpNullList() {
//       result = new char[answer.length];
//        for (int i = 0; i < answer.length; i++) {
//            result[i] = ' ';
//        }
//        return result;
//    }


    private void showCluesAlert() {
        // shows the clue options
        AlertDialog.Builder cluesBuilder = new AlertDialog.Builder(this);
        final CharSequence[] arrClues = {"פתיחת משבצת שבתמונה - עולה כוכב אחד", "גילוי אות", "העלמת חצי מהאותיות שבמאגר", "ראה מה התמונה המסתתרת"};
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
        //setUpRandomImage();
        // setUpImage();
        findImagePlaceFromFB(chosenLevelFromMenuActivity.toString());

    }

    private void findImagePlaceFromFB(final String chosenLevel) {
        myRefImageData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //   Iterable<DataSnapshot> children = snapshot.getChildren();
                for (DataSnapshot imageSnapshot : snapshot.getChildren()) {
                    if (imageSnapshot.getKey().equals(chosenLevel)) {
                        correct_answer = imageSnapshot.child("imageNameForUri").getValue(String.class);
                        Picasso.get().load(imageSnapshot.child("imageUri").getValue(String.class)).into(imageViewQuestion);

                        //  setImageViewFromFB(imageSnapshot.child("imageNameForUri").getValue(String.class));
//                        correct_answer = imageSnapshot.child("imageName").getValue(String.class);
//                        setImageViewFromFB(correct_answer);
                    }
                }
                //  Toast.makeText(getApplicationContext(), "onSuccess  " + correct_answer, Toast.LENGTH_SHORT).show();
                runLevel();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "onFailure 514 ", Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void G() {
//        setUpAnswer();
//        setUpSaveList();
//        initView();
//    }

//    private void setUpImage() {
//        findImagePlaceFromFB();
//
//      //  correct_answer=listImagesName.get(chosenLevelFromMenuActivity);
//
//        //currentImage = imageList.get(random.nextInt(imageList.size()));
//        //imageViewQuestion.setImageResource(currentImage);
//       // correct_answer = getResources().getResourceName(currentImage);
//
//        //correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") +1);
//
////        DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/place");
////        myRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                currentImage = snapshot.getValue(Integer.class);
////                imageViewQuestion.setImageResource(imageList.get(currentImage));
////            }
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////            }
////        });
////        correct_answer = getResources().getResourceName(imageList.get(currentImage));
////        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);
//
//    }


//    public void setImageViewFromFB(String imageName) {
//       // correct_answer = imageName;
//        storageReference = FirebaseStorage.getInstance().getReference();
//        try {
//            localFile = File.createTempFile("images", "jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        final Uri fileUri = Uri.fromFile(localFile);
//        final StorageReference riversRef = storageReference.child("images/" + imageName + ".jpg");
//        riversRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                imageViewQuestion.setImageURI(fileUri);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "onFailure 538 ", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

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
        //answer = correct_answer.toCharArray();
//        answer = new char[correct_answer.length()];
//        for (int i = 0; i < correct_answer.length(); i++) {
//            answer[i] = correct_answer.charAt(i);
//        }
//
//        Common.user_submit_answer = new char[answer.length];
//
//        Common.count = databaseStringAnswer.length();
//        for (int i = 0; i < databaseStringAnswer.length(); i++) {
//            Common.user_submit_answer[i] = databaseStringAnswer.charAt(i);
//        }


//        databaseListStringSuggestedList="";
//        suggestSource.clear();
//        DatabaseReference myRef2 = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/suggestedString");
//        myRef2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseListStringSuggestedList = snapshot.getValue(String.class);
//                suggestSource.add(databaseListStringSuggestedList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });


//        if (suggestSource == null || suggestSource.size() == 0|| databaseListStringSuggestedList.equals("")) {
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
            //Sort random our list
            Collections.shuffle(suggestSource);

            //    databaseListStringSuggestedList = suggestSource.toString();
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
//            for (int i = 0; i < temp.length; i++) {
//                suggestSource.add(temp[i]);
//            }
        }
        User.getInstance().setSuggestedString(databaseListStringSuggestedList);


        //Set for GridView
//        answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);


        suggestAdapter.notifyDataSetChanged();
//        answerAdapter.notifyDataSetChanged();
//        gridViewAnswer.setAdapter(answerAdapter);

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


//        DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/guessedAnswer");
//        myRef.setValue(result2);
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

                    //imageList.remove((Integer) currentImage);
                    //       imagesFromStorage.remove(currentImage);
                    hideThePicture();
                    invalidateOptionsMenu();
                    chosenLevelFromMenuActivity++;
                    myRefImageData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int childrenCount = (int) snapshot.getChildrenCount();
                            if (childrenCount > User.getInstance().getNumOfWin()) {
                                findImagePlaceFromFB(User.getInstance().getNumOfWin() + 1 + "");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else {
                    Toast.makeText(GameActivity.this, "Incorrect!!", Toast.LENGTH_SHORT).show();
                }
                reset();
            }
        });
    }

    private void reset() {
        //Reset
        Common.count = 0;
        Common.user_submit_answer = new char[correct_answer.length()];

        User.getInstance().setPlaceChosenFromSuggestedString(null);
        User.getInstance().setGuessedAnswer("");
        //  DatabaseReference myRef = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/guessedAnswer");


        //Set Adapter
//        answerAdapter = new GridViewAnswerAdapter(setUpNullList(), getApplicationContext());
        answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), getApplicationContext());
        gridViewAnswer.setAdapter(answerAdapter);
        answerAdapter.notifyDataSetChanged();


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef2 = database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/suggestedString");
//        myRef2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseListStringSuggestedList = snapshot.getValue(String.class);
//                suggestSource.add(databaseListStringSuggestedList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

//        suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(), GameActivity.this);

        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, GameActivity.this);
        gridViewSuggest.setAdapter(suggestAdapter);
//        myRefPlaceChosenFromSuggestedString.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                    userDataSuggestArrayList.add(userSnapshot.getValue(Integer.class));
//
//                    for (int j = 0; j < suggestSource.size(); j++) {
//                        if(userDataSuggestArrayList.size()< j){
//                                gridViewSuggest.getChildAt(j).setAlpha(1);
//                                gridViewSuggest.getChildAt(j).setClickable(true);
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        suggestAdapter.notifyDataSetChanged();


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
//    private char[] setAnswerList() {
//        result = new char[correct_answer.length()];
//        for (int i = 0; i < correct_answer.length(); i++) {
//            result[i] = correct_answer.charAt(i);
//        }
//        return result;
//    }

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

            hiddenAndShownItems = findViewById(R.id.hiddenAndShownItems_tv);
            openPictureItems++;
            hiddenAndShownItems.setText((maxOpenPictureItems + User.getInstance().getPlace()) + "/" + openPictureItems);
        }
    }


    public void showFinishedLevel() {
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
        btnSubmit.setVisibility(View.INVISIBLE);
        btnSubmit.setClickable(false);
        imgClues.setVisibility(View.INVISIBLE);
        imgClues.setClickable(false);

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
                        // setImageViewFromFB(answerFinishedLevel);
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

    public void deleteWord(View view) {
        if(User.getInstance().getGuessedAnswer().length()>1) {


//            User.getInstance().setPlaceChosenFromSuggestedString(User.getInstance().getPlaceChosenFromSuggestedString());
//
//            char[] guessedAnswerTemp = new char[User.getInstance().getGuessedAnswer().length() - 1];
//            String stringGuessedAnswerTemp = "";
//            for (int i = 0; i < guessedAnswerTemp.length; i++) {
//                guessedAnswerTemp[i] = User.getInstance().getGuessedAnswer().charAt(i);
//                stringGuessedAnswerTemp += User.getInstance().getGuessedAnswer().charAt(i);
//            }
//            Common.setUser_submit_answer(guessedAnswerTemp);
            User.getInstance().getPlaceChosenFromSuggestedString().remove(User.getInstance().getPlaceChosenFromSuggestedString().size() - 1);
            Common.user_submit_answer[User.getInstance().getPlaceChosenFromSuggestedString().size()]='\u0000';

            StringBuilder usersCurrentAnswer = new StringBuilder();
//            User.getInstance().setGuessedAnswer(stringGuessedAnswerTemp);
            for (int i = 0; i < Common.user_submit_answer.length; i++) {
                if (Common.user_submit_answer[i] != '\u0000') {
                    usersCurrentAnswer.append(Common.user_submit_answer[i]);
                    User.getInstance().setGuessedAnswer(usersCurrentAnswer.toString());
                }
            }

            answerAdapter = new GridViewAnswerAdapter(setResultUserAnswerList(), getApplicationContext());
            gridViewAnswer.setAdapter(answerAdapter);
            answerAdapter.notifyDataSetChanged();


            suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, GameActivity.this);
            gridViewSuggest.setAdapter(suggestAdapter);

        }
        else {
            reset();
        }

    }


//    public void downloadImage(View view){
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


//    public void listAllPaginated(@Nullable String pageToken) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference listRef = storage.getReference().child("files/uid");
//
//        // Fetch the next page of results, using the pageToken if we have one.
//        Task<ListResult> listPageTask = pageToken != null
//                ? listRef.list(100, pageToken)
//                : listRef.list(100);
//
//        listPageTask
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        List<StorageReference> prefixes = listResult.getPrefixes();
//                        List<StorageReference> items = listResult.getItems();
//
//                        // Process page of results
//                        // ...
//
//                        // Recurse onto next page
//                        if (listResult.getPageToken() != null) {
//                            listAllPaginated(listResult.getPageToken());
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Uh-oh, an error occurred.
//            }
//        });
//    }
}