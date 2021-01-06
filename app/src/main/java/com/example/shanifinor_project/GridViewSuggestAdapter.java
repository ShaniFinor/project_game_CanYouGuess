package com.example.shanifinor_project;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

public class GridViewSuggestAdapter extends BaseAdapter {
    private List<String> suggestSource;
    private Context context;
    private Game gameActivity; //activity
    private Integer pressCount;

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, Game gameActivity) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.gameActivity = gameActivity;
        this.pressCount=0;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
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
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pressCount<gameActivity.answer.length){
                            char compare2=suggestSource.get(i).charAt(0);
                            Common.user_submit_answer[pressCount] = compare2;
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            gameActivity.gridViewAnswer.setAdapter(answerAdapter);
                            answerAdapter.notifyDataSetChanged();
//                            gameActivity.suggestSource.set(i, "null");
//                            gameActivity.suggestAdapter = new GridViewSuggestAdapter(gameActivity.suggestSource, context, gameActivity);
//                            gameActivity.gridViewSuggest.setAdapter(gameActivity.suggestAdapter);
//                            gameActivity.suggestAdapter.notifyDataSetChanged();
                            button.animate().alpha(0).setDuration(300);
                            pressCount+=1;
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
        }
        else {
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