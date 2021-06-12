package com.example.shanifinor_project.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.example.shanifinor_project.ui.common.Common;

public class GridViewAnswerAdapter extends BaseAdapter {
    private char[] answerCharacter;
    private Context context;

    public GridViewAnswerAdapter(char[] answerCharacter, Context context) {
        this.answerCharacter = answerCharacter;
        this.context = context; //context: GameActivity
    }

    @Override
    public int getCount() {
        return answerCharacter.length;
    }

    @Override
    public Object getItem(int i) {
        return answerCharacter[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            //create new button
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(100, 100));
            button.setPadding(2, 2, 2, 2);
            button.setTextColor(Color.WHITE);
            button.setTextSize(30);
            button.setText(String.valueOf(answerCharacter[i]));
        } else {
            button = (Button) view;
        }
        if (answerCharacter[i] == ' ' || String.valueOf(answerCharacter[i]).equals("") || answerCharacter[i] == '\u0000') {
            button.setBackgroundResource(R.drawable.background_button);
        } else {
            button.setBackgroundResource(R.drawable.background_letters);
        }

        return button;

    }
}
