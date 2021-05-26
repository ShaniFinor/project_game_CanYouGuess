package com.example.shanifinor_project.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.shanifinor_project.ui.common.Common;
import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.example.shanifinor_project.ui.screens.GameActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GridViewSuggestAdapter extends BaseAdapter {
    private List<String> suggestSource;
    private Context context;
    private GameActivity gameActivity; //activity
    private Integer pressCount;

    //  private Integer p = 0;
    //private List<Integer> placeChosenFromSuggestedString = new ArrayList<>();

    //  private Integer[] userDataSuggestStringArray;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    // private DatabaseReference myRef= database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/placeChosenFromSuggestedString");

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, GameActivity gameActivity) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.gameActivity = gameActivity;
        this.pressCount = Common.count;
    }

    public List<String> getSuggestSource() {
        return suggestSource;
    }

    public void setSuggestSource(List<String> suggestSource) {
        this.suggestSource = suggestSource;
    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int i) {
        return suggestSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, final View view, ViewGroup viewGroup) {
        final Button button;
        if (view == null) {
            if (suggestSource.get(i).equals("null")) {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(100, 100));
                button.setPadding(2, 2, 2, 2);
                button.setBackgroundResource(R.drawable.background_letters);
            } else {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(100, 100));
                button.setPadding(2, 2, 2, 2);
                button.setBackgroundResource(R.drawable.background_letters);
                button.setTextColor(Color.WHITE);
                button.setTextSize(30);
                button.setText(suggestSource.get(i));

                List<Integer> placeChosenFromSuggestedString = User.getInstance().getPlaceChosenFromSuggestedString();
                if (placeChosenFromSuggestedString != null &&
                        !placeChosenFromSuggestedString.isEmpty()) {
                    for (int j = 0; j < placeChosenFromSuggestedString.size(); j++) {
                        if (gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)) != null) {
                            gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setAlpha(0);
                            gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setClickable(false);
                        }
                    }
                }
//                if (!userDataSuggestStringArray.isEmpty()) {
//                    for (int j = 0; j < userDataSuggestStringArray.size(); j++) {
//                        if (gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)) != null) {
//                            gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)).setAlpha(0);
//                            gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)).setClickable(false);
//                        }
//                    }
//                }
//
//                        if (!userDataSuggestStringArray.isEmpty()) {
//                            for (int j = 0; j < userDataSuggestStringArray.size(); j++) {
//                                if (gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)) != null) {
//                                    gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)).setAlpha(0);
//                                    gameActivity.gridViewSuggest.getChildAt(userDataSuggestStringArray.get(j)).setClickable(false);
//                                }
//                            }
//                        }

//                else {

//                }

//                if(button.getAlpha()==0 && !userDataSuggestStringArray.contains(button)){
//                    button.setAlpha(1);
//                }

//                myRef= database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/place");
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        p = snapshot.getValue(Integer.class);
////                        gameActivity.gridViewSuggest.getChildAt(p).animate().alpha(0).setDuration(300);
//                        gameActivity.gridViewSuggest.getChildAt(p).setAlpha(0);
//                        gameActivity.gridViewSuggest.getChildAt(p).setClickable(false);
//                        //pressCount+=1;
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pressCount < gameActivity.answer.length) {
                            char compare2 = suggestSource.get(i).charAt(0);
                            Common.user_submit_answer[pressCount] = compare2; //Get char
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            gameActivity.gridViewAnswer.setAdapter(answerAdapter);
                            List<Integer> placeChosenFromSuggestedString = User.getInstance().getPlaceChosenFromSuggestedString();
                            if (placeChosenFromSuggestedString == null) {
                                placeChosenFromSuggestedString = new ArrayList<>();
                            }
                            placeChosenFromSuggestedString.add(i);
                            User.getInstance().setPlaceChosenFromSuggestedString(placeChosenFromSuggestedString);
                            answerAdapter.notifyDataSetChanged();
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();


//                            myRef= database.getReference("users/" + FirebaseAuth.getInstance().getUid() + "/suggestedString");
//                            myRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    userDataSuggestString = snapshot.getValue(String.class);
//                                    userDataSuggestStringArray=new String[userDataSuggestString.length()];
//                                    userDataSuggestStringArray=userDataSuggestString.split(",");
//                                    userDataSuggestStringArray[i]="";
//                                    for (int j = 0; j < userDataSuggestStringArray.length; j++) {
//                                        userDataSuggestString+=userDataSuggestStringArray[j];
//                                        if(j!=userDataSuggestStringArray.length-1){
//                                            userDataSuggestString+=",";
//                                        }
//                                    }
//
//                                    myRef.setValue(userDataSuggestString);
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//                                }
//                            });


                            button.animate().alpha(0).setDuration(300);
                            button.setClickable(false);
                            pressCount += 1;
                        }
//                        //if correct answer contains character user just clicked, add it into answer list
//                        if (String.valueOf(gameActivity.answer).contains(suggestSource.get(i))) {
//                            char compare = suggestSource.get(i).charAt(0); //Get char
//
//                            for (int i = 0; i < gameActivity.answer.length; i++) {
//                                if (compare == gameActivity.answer[i]) {
//                                    Common.user_submit_answer[i] = compare;
//                                }
//                            }
//                            //Update UI
//                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
//                            gameActivity.gridViewAnswer.setAdapter(answerAdapter);
//                            answerAdapter.notifyDataSetChanged();
//
//                            //Remove from suggest source
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();
//                        } else { //else, just remove
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();
//                        }
                    }
                });
            }
        } else {
            button = (Button) view;
        }
        return button;
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.GridView;
//
//import java.util.List;
//
//public class GridViewSuggestAdapter extends BaseAdapter {
//    private List<String> suggestSource;
//    private Context context;
//    private Game gameActivity; //activity
//
//    public GridViewSuggestAdapter(List<String> suggestSource, Context context, Game gameActivity) {
//        this.suggestSource = suggestSource;
//        this.context = context;
//        this.gameActivity = gameActivity;
//    }
//
//    @Override
//    public int getCount() {
//        return suggestSource.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return suggestSource.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//        final Button button;
//        if (view == null) {
//            if (suggestSource.get(i).equals("null")) {
//                button = new Button(context);
//                button.setLayoutParams(new GridView.LayoutParams(100, 100));
//                button.setPadding(2, 2, 2, 2);
//                button.setBackgroundResource(R.drawable.background_letters);
//
//            } else {
//                button = new Button(context);
//                button.setLayoutParams(new GridView.LayoutParams(100, 100));
//                button.setPadding(2, 2, 2, 2);
//                button.setBackgroundResource(R.drawable.background_letters);
//                button.setTextColor(Color.WHITE);
//                button.setTextSize(30);
//                button.setText(suggestSource.get(i));
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //if correct answer contains character user just clicked, add it into answer list
//                        if (String.valueOf(gameActivity.answer).contains(suggestSource.get(i))) {
//                            char compare = suggestSource.get(i).charAt(0); //Get char
//
//                            for (int i = 0; i < gameActivity.answer.length; i++) {
//                                if (compare == gameActivity.answer[i]) {
//                                    Common.user_submit_answer[i] = compare;
//                                }
//                            }
//                            //Update UI
//                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
//                            gameActivity.gridViewAnswer.setAdapter(answerAdapter);
//                            answerAdapter.notifyDataSetChanged();
//
//                            //Remove from suggest source
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();
//                            button.animate().alpha(0).setDuration(300);
//                        } else { //else, just remove
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//            }
//        } else {
//            button = (Button) view;
//        }
//        return button;
//    }
//}