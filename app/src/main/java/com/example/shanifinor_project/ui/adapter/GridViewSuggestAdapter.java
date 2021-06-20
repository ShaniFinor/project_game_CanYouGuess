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


import java.util.ArrayList;
import java.util.List;

/**
 * The class builds the letter buttons displayed to the user according to the list of letters received.
 * disappear the buttons that the user selected (placeChosenFromSuggestedString) from the suggest list.
 * when the user press on a button from the suggest list, if the number of buttons he pressed on, smaller from the length of the word that the user need to find:
 * disappear the button from the suggest list, send the letter to GridViewAnswerAdapter (create new button with that letter).
 */
public class GridViewSuggestAdapter extends BaseAdapter {
    private List<String> suggestSource;
    private Context context;
    private GameActivity gameActivity; //activity
    private Integer pressCount;


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

                //disappear the buttons that the user selected (placeChosenFromSuggestedString) from the suggest list.
                List<Integer> placeChosenFromSuggestedString = User.getInstance().getPlaceChosenFromSuggestedString();
                if (placeChosenFromSuggestedString != null && !placeChosenFromSuggestedString.isEmpty()) {
                    for (int j = 0; j < placeChosenFromSuggestedString.size(); j++) {
                        if (gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)) != null) {
                            gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setAlpha(0);
                            gameActivity.gridViewSuggest.getChildAt(placeChosenFromSuggestedString.get(j)).setClickable(false);
                        }
                    }
                }

                //when the user press on a button from the suggest list.
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //if the number of buttons he pressed on,
                        // smaller from the length of the word that the user need to find.
                        if (pressCount < gameActivity.answer.length) {

                            //compare= the char from the suggestSource list in the place that the user pressed on (i).
                            // charAt(0) because in every place in the list there is one char.
                            char compare = suggestSource.get(i).charAt(0);

                            Common.user_submit_answer[pressCount] = compare; //Get char (add the char to the Common int the pressCount place (in the next empty place)).
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context); //context: GameActivity
                            gameActivity.gridViewAnswer.setAdapter(answerAdapter);//Update UI

                            List<Integer> placeChosenFromSuggestedString = User.getInstance().getPlaceChosenFromSuggestedString();
                            if (placeChosenFromSuggestedString == null) {
                                placeChosenFromSuggestedString = new ArrayList<>();
                            }
                            placeChosenFromSuggestedString.add(i); //adds the button's place from the suggestList
                            User.getInstance().setPlaceChosenFromSuggestedString(placeChosenFromSuggestedString);
                            answerAdapter.notifyDataSetChanged();

                            StringBuilder usersCurrentAnswer = new StringBuilder();

                            for (int j = 0; j < Common.user_submit_answer.length; j++) {
                                if (Common.user_submit_answer[j] != '\u0000') {
                                    usersCurrentAnswer.append(Common.user_submit_answer[j]);
                                    User.getInstance().setGuessedAnswer(usersCurrentAnswer.toString());
                                }
                            }

                            button.animate().alpha(0).setDuration(300); // animate that disappears the button that the user pressed on.
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
