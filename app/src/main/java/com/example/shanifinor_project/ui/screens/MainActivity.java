package com.example.shanifinor_project.ui.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanifinor_project.model.db.Repository;
import com.example.shanifinor_project.model.service.MyService;
import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.db.UserDao;
import com.example.shanifinor_project.model.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGameMenu, btnInstructions, btnGetStar;
    private MediaPlayer mpClickSound;

    private FloatingActionButton fab_add, fab_chart, fab_sound, fab_profile;
    private Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    private boolean isOpen = false;

    //  private Dialog dialogLogin;
    private EditText etUserNameLogin, etPasswordLogin, etEmailLogin;
    private Button btnLoginOk;
    // private ImageView imgCloseLogin;
    // private TextView textForRegistration;
    //  private TextView textForRegistration;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialogLogin;
    private AlertDialog dialogRegistration;

    //  private Dialog dialogRegistration;
    private EditText etUserNameRegister, etEmailRegister, etPasswordRegister, etConfirmPasswordRegister;
    private Button btnRegisterOk;
    private SubmitButton submitButtonRegister;
    private ImageView imgCloseRegister;

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private FrameLayout flipView;

    private AnimatorSet mSetRightOut2;
    private AnimatorSet mSetLeftIn2;
    private boolean mIsBackVisible2 = false;
    private View mCardFrontLayout2;
    private View mCardBackLayout2;
    private FrameLayout flipView2;

    private AnimatorSet mSetRightOut3;
    private AnimatorSet mSetLeftIn3;
    private boolean mIsBackVisible3 = false;
    private View mCardFrontLayout3;
    private View mCardBackLayout3;
    private FrameLayout flipView3;

    private AnimatorSet mSetRightOut4;
    private AnimatorSet mSetLeftIn4;
    private boolean mIsBackVisible4 = false;
    private View mCardFrontLayout4;
    private View mCardBackLayout4;
    private FrameLayout flipView4;

    private AnimatorSet mSetRightOut5;
    private AnimatorSet mSetLeftIn5;
    private boolean mIsBackVisible5 = false;
    private View mCardFrontLayout5;
    private View mCardBackLayout5;
    private FrameLayout flipView5;

    private FirebaseAuth fbAuth;
    private FirebaseUser fbUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private BatteryBroadcastReceiver batteryReceiver;
    private boolean isShowedAlarmBatteryOnce = false;

    public static final String PREFS_NAME = "MySharedPreferencesFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        backgroundLayoutColors();
        starsFlipAction();

        btnGameMenu = findViewById(R.id.btnGameMenu);
        btnGameMenu.setOnClickListener(this);
        btnInstructions = findViewById(R.id.btnInstructions);
        btnInstructions.setOnClickListener(this);
        btnGetStar = findViewById(R.id.btnGetStar);
        btnGetStar.setOnClickListener(this);
        mpClickSound = MediaPlayer.create(this, R.raw.click_sound);

        floatingActionMenuBar();

//        fbAuth = FirebaseAuth.getInstance();
//        fbUser = fbAuth.getCurrentUser();
//        if (fbUser != null) { //if a user is signed in
//            Toast.makeText(this, "user logged in: " + fbUser.getEmail(), Toast.LENGTH_SHORT).show();
//            //    fab_login.setImageResource(R.drawable.ic_logout);
//        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        User.getInstance().isLoggedIn();

        batteryReceiver = new MainActivity.BatteryBroadcastReceiver();
        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!User.getInstance().isLoggedIn()) {
//            createLoginDialog();
//        }

        //User has successfully logged in, save this information
// We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();

//Set "hasLoggedIn" to true
        editor.putBoolean("hasLoggedIn", true);

// Commit the edits!
        editor.commit();

//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();

        if(!hasLoggedIn || fbUser==null)
        {
            createLoginDialog();
        }


    }


    public void backgroundLayoutColors() {
        //changes the background colors
        ConstraintLayout constraintLayout = findViewById(R.id.page_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    public void starsFlipAction() {
        //calls to the functions that flips each star. they all flip together at the background of the activity_main screen.

        findViews();
        loadAnimations();
        changeCameraDistance();
        flipView = findViewById(R.id.frameLayoutStar1);
        flipCard(flipView);

        findViews2();
        loadAnimations2();
        changeCameraDistance2();
        flipView2 = findViewById(R.id.frameLayoutStar2);
        flipCard2(flipView2);

        findViews3();
        loadAnimations3();
        changeCameraDistance3();
        flipView3 = findViewById(R.id.frameLayoutStar3);
        flipCard3(flipView3);

        findViews4();
        loadAnimations4();
        changeCameraDistance4();
        flipView4 = findViewById(R.id.frameLayoutStar4);
        flipCard4(flipView4);

        findViews5();
        loadAnimations5();
        changeCameraDistance5();
        flipView5 = findViewById(R.id.frameLayoutStar5);
        flipCard5(flipView5);
    }


    public void floatingActionMenuBar() {
        //floating action menu bar.
        //fab_chart- opens the layout of the activity_chart_winnings
        //fab_sound- opens the sound options
        //fab_profile- opens the layout of activity_user_profile
        fab_add = findViewById(R.id.add_btn);
        fab_chart = findViewById(R.id.win_chart_btn);
        fab_sound = findViewById(R.id.sound_btn);
        fab_profile = findViewById(R.id.profile_btn);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);

        if (!MyService.isInstanceCreated()) {
            fab_sound.setImageResource(R.drawable.ic_volume_off);
        } else {
            fab_sound.setImageResource(R.drawable.ic_volume);
        }

        //when you press the fab_add button-
        //if the menu is closed- the fab_add rotates 45 degrees (looks like x) and the fab_chart, fab_sound, fab_profile are now opened.
        //if the menu is open- the fab_add rotates 45 degrees back (looks like +) and the fab_chart, fab_sound, fab_profile are now closed.
        fab_add.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#80FFFFFF")));
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab_chart.startAnimation(fabClose);
                    fab_sound.startAnimation(fabClose);
                    fab_profile.startAnimation(fabClose);
                    fab_add.startAnimation(fabRClockwise);

                    fab_chart.setClickable(false);
                    fab_sound.setClickable(false);
                    fab_profile.setClickable(false);

                    isOpen = false;
                } else {
                    fab_chart.startAnimation(fabOpen);
                    fab_sound.startAnimation(fabOpen);
                    fab_profile.startAnimation(fabOpen);
                    fab_add.startAnimation(fabRAntiClockwise);

                    fab_chart.setClickable(true);
                    fab_sound.setClickable(true);
                    fab_profile.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartWinningsActivity.class);
                startActivity(intent);
            }
        });
        fab_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "you clicked on sound", Toast.LENGTH_SHORT).show();
                Intent intentService = new Intent(MainActivity.this, MyService.class);
                if (MyService.isInstanceCreated()) {
                    stopService(intentService);
                    fab_sound.setImageResource(R.drawable.ic_volume_off);
                } else {
                    startService(intentService);
                    fab_sound.setImageResource(R.drawable.ic_volume);
                }
            }
        });
        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
//                if (fbUser != null) {
//                    User.getInstance().logOut();
//                    fbUser = null;
//                    Toast.makeText(MainActivity.this, "User signed out", Toast.LENGTH_SHORT).show();
//                    fab_login.setImageResource(R.drawable.ic_login);
//                } else {
//                    createLoginDialog();
//                }
            }
        });

    }

    public void createLoginDialog() {
        //a dialog to login
        dialogBuilder = new AlertDialog.Builder(this);
        final View loginPopupView = getLayoutInflater().inflate(R.layout.layout_login, null);
        dialogBuilder.setView(loginPopupView);
        dialogLogin = dialogBuilder.create();
        dialogLogin.setContentView(R.layout.layout_login);
        // dialogLogin.setCancelable(true);
        etEmailLogin = loginPopupView.findViewById(R.id.etEmail_login);
        etUserNameLogin = loginPopupView.findViewById(R.id.etUserName_login);
        etPasswordLogin = loginPopupView.findViewById(R.id.etPassword_login);
        //imgCloseLogin = loginPopupView.findViewById(R.id.close_login);
        btnLoginOk = loginPopupView.findViewById(R.id.btnLoginOk);
        dialogLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLogin.show();
        dialogLogin.setCanceledOnTouchOutside(false);
        btnLoginOk.setOnClickListener(this);
//        imgCloseLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogLogin.dismiss();
//            }
//        });

//        btnLoginOk.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    dialogLogin.dismiss();
//
//                }
//                return true;
//            }
//        });
        final TextView textForRegistration = loginPopupView.findViewById(R.id.textForRegistration);
        textForRegistration.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    createRegisterDialog();
                    dialogLogin.dismiss();
                }
                return true;
            }
        });
    }

    public void createRegisterDialog() {
        //a dialog for register
        dialogBuilder = new AlertDialog.Builder(this);
        final View registerPopupView = getLayoutInflater().inflate(R.layout.layout_register, null);
        dialogBuilder.setView(registerPopupView);
        dialogRegistration = dialogBuilder.create();
        dialogRegistration.setContentView(R.layout.layout_register);
        // dialogRegistration.setCancelable(true);
        etEmailRegister = registerPopupView.findViewById(R.id.etEmail_register);
        etPasswordRegister = registerPopupView.findViewById(R.id.etPassword_register);
        etUserNameRegister = registerPopupView.findViewById(R.id.etUserName_register);
        etConfirmPasswordRegister = registerPopupView.findViewById(R.id.etConfirmPassword_register);
        imgCloseRegister = registerPopupView.findViewById(R.id.close_register);
        //btnRegisterOk = registerPopupView.findViewById(R.id.btnRegisterOk);
        submitButtonRegister = registerPopupView.findViewById(R.id.submitButton);
        dialogRegistration.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogRegistration.show();
        // btnRegisterOk.setOnClickListener(this);
        dialogRegistration.setCanceledOnTouchOutside(false);
        submitButtonRegister.setOnClickListener(this);
        imgCloseRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRegistration.dismiss();
                createLoginDialog();
            }
        });

//        submitButtonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // thread- so the button will have time to finish its annotation and after it finishes, it closed the dialog
//                final Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(3500);
//                            dialogRegistration.dismiss();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//            }
//        });

    }

    @Override
    public void onClick(final View view) {
        if ((view == btnGameMenu) || (view == btnInstructions) || (view == btnGetStar)) {
            if (MyService.isInstanceCreated()) { //אם הצליל ברקע פועל אז לאפשר צליל בעת לחיצה
                mpClickSound.start();
            }
            Intent intent = new Intent();
            if (view == btnGameMenu) {
                intent = new Intent(MainActivity.this, GameMenuActivity.class);
            }
            if (view == btnInstructions) {
                intent = new Intent(this, InstructionsActivity.class);
            }
            if (view == btnGetStar) {
                intent = new Intent(this, GetStarActivity.class);
            }
            startActivity(intent);
        }
//        if(view==starFlip1){
//            float scale = view.getResources().getDisplayMetrics().density;
//            view.setCameraDistance(8000 * scale);
//        }

        if (view == submitButtonRegister) {
            if (etEmailRegister.getText().toString().length() != 0 &&
                    etPasswordRegister.getText().toString().length() != 0) {
                if (etConfirmPasswordRegister.getText().toString().
                        equals(etPasswordRegister.getText().toString())) {
                    //firebase. saves the new user values
                    fbAuth.createUserWithEmailAndPassword(etEmailRegister.getText().toString(), etPasswordRegister.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        UserDao userDao = new UserDao
                                                (0, "ic_star", etUserNameRegister.getText().toString(), etEmailRegister.getText().toString(),
                                                        0, 0, "", "",
                                                        new ArrayList<String>(), new ArrayList<Integer>());
                                        User.getInstance().registerNewUserToFirebase(userDao);
                                        // thread- so the button will have time to finish its annotation and after it finishes, it closed the dialog
                                        final Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(3500);
                                                    dialogRegistration.dismiss();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        thread.start();
                                        // Toast.makeText(MainActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                        //   fab_login.setImageResource(R.drawable.ic_logout);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "password failed.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }

        if (view == btnLoginOk) {
            if (etEmailLogin.getText().toString() != null &&
                    etPasswordLogin.getText().toString() != null &&
                    (!etEmailLogin.getText().toString().isEmpty()) &&
                    (!etPasswordLogin.getText().toString().isEmpty())) {
                //firebase. checking if the user is exist
                fbAuth.signInWithEmailAndPassword(etEmailLogin.getText().toString(), etPasswordLogin.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // Toast.makeText(MainActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();
                                    dialogLogin.dismiss();
                                    // fab_login.setImageResource(R.drawable.ic_logout);
                                    User.getInstance().login();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });
            }
        }
    }

    //region Flip Star 1
    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        //    mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        //  mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.starFlip1);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }
//endregion

    //region Flip Star 2
    private void changeCameraDistance2() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout2.setCameraDistance(scale);
        //    mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations2() {
        mSetRightOut2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews2() {
        //  mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout2 = findViewById(R.id.starFlip2);
    }

    public void flipCard2(View view) {
        if (!mIsBackVisible2) {
            mSetRightOut2.setTarget(mCardFrontLayout2);
            mSetLeftIn2.setTarget(mCardFrontLayout2);
            mSetRightOut2.start();
            mSetLeftIn2.start();
            mIsBackVisible2 = true;
        } else {
            mSetRightOut2.setTarget(mCardFrontLayout2);
            mSetLeftIn2.setTarget(mCardFrontLayout2);
            mSetRightOut2.start();
            mSetLeftIn2.start();
            mIsBackVisible2 = false;
        }
    }
    //endregion

    //region Flip Star 3
    private void changeCameraDistance3() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout3.setCameraDistance(scale);
        // mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations3() {
        mSetRightOut3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews3() {
        //  mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout3 = findViewById(R.id.starFlip3);
    }

    public void flipCard3(View view) {
        if (!mIsBackVisible3) {
            mSetRightOut3.setTarget(mCardFrontLayout3);
            mSetLeftIn3.setTarget(mCardFrontLayout3);
            mSetRightOut3.start();
            mSetLeftIn3.start();
            mIsBackVisible3 = true;
        } else {
            mSetRightOut3.setTarget(mCardFrontLayout3);
            mSetLeftIn3.setTarget(mCardFrontLayout3);
            mSetRightOut3.start();
            mSetLeftIn3.start();
            mIsBackVisible3 = false;
        }
    }
    //endregion

    //region Flip Star 4
    private void changeCameraDistance4() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout4.setCameraDistance(scale);
        //    mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations4() {
        mSetRightOut4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews4() {
        //  mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout4 = findViewById(R.id.starFlip4);
    }

    public void flipCard4(View view) {
        if (!mIsBackVisible4) {
            mSetRightOut4.setTarget(mCardFrontLayout4);
            mSetLeftIn4.setTarget(mCardFrontLayout4);
            mSetRightOut4.start();
            mSetLeftIn4.start();
            mIsBackVisible4 = true;
        } else {
            mSetRightOut4.setTarget(mCardFrontLayout4);
            mSetLeftIn4.setTarget(mCardFrontLayout4);
            mSetRightOut4.start();
            mSetLeftIn4.start();
            mIsBackVisible4 = false;
        }
    }
//endregion

    //region Flip Star 5
    private void changeCameraDistance5() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout5.setCameraDistance(scale);
        //    mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations5() {
        mSetRightOut5 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn5 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews5() {
        //  mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout5 = findViewById(R.id.starFlip5);
    }

    public void flipCard5(View view) {
        if (!mIsBackVisible5) {
            mSetRightOut5.setTarget(mCardFrontLayout5);
            mSetLeftIn5.setTarget(mCardFrontLayout5);
            mSetRightOut5.start();
            mSetLeftIn5.start();
            mIsBackVisible5 = true;
        } else {
            mSetRightOut5.setTarget(mCardFrontLayout5);
            mSetLeftIn5.setTarget(mCardFrontLayout5);
            mSetRightOut5.start();
            mSetLeftIn5.start();
            mIsBackVisible5 = false;
        }
    }
//endregion


    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int batteryLevel = intent.getIntExtra("level", 0);
            if (batteryLevel <= 20 && !isShowedAlarmBatteryOnce) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("LOW BATTERY")
                        .setMessage("Your battery is low. please recharge your device or turn on low power mode")
                        .setPositiveButton("ok", null)
                        .show();
                isShowedAlarmBatteryOnce = true;
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        moveTaskToBack(true);
//    }
}