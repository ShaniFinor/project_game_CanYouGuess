package com.example.shanifinor_project;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.List;

public class GridViewAnswerAdapter extends BaseAdapter {
    private char[] answerCharacter;
    private Context context;

    public GridViewAnswerAdapter(char[] answerCharacter, Context context) {
        this.answerCharacter = answerCharacter;
        this.context = context;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            //create new button
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(100, 100));
            button.setPadding(2, 2, 2, 2);
            button.setBackgroundResource(R.drawable.background_letters);
            button.setTextColor(Color.WHITE);
            button.setTextSize(30);
            button.setText(String.valueOf(answerCharacter[i]));
        } else {
            button = (Button) view;
        }
        return button;
    }
}




//////////////////////////////////////////////////
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
//public class GridViewAnswerAdapter extends BaseAdapter {
//    private char[] answerCharacter;
//    private Context context;
//
//    public GridViewAnswerAdapter(char[] answerCharacter, Context context) {
//        this.answerCharacter = answerCharacter;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return answerCharacter.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return answerCharacter[i];
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        Button button;
//        if (view == null) {
//            //create new button
//            button = new Button(context);
//            button.setLayoutParams(new GridView.LayoutParams(100, 100));
//           // button.setPadding(8, 8, 8, 8);
//            button.setPadding(2, 2, 2, 2);
//            button.setBackgroundResource(R.drawable.background_letters);
//            button.setTextColor(Color.WHITE);
//            button.setTextSize(30);
//            button.setText(String.valueOf(answerCharacter[i]));
//        } else {
//            button = (Button) view;
//        }
//        return button;
//    }
//}
