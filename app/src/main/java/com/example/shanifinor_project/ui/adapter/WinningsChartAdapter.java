package com.example.shanifinor_project.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.example.shanifinor_project.model.db.UserDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WinningsChartAdapter extends RecyclerView.Adapter<WinningsChartAdapter.WinViewHolder> {
    private ArrayList<UserDao> winnings;

    public WinningsChartAdapter(ArrayList<UserDao> winnings) {
        this.winnings = winnings;
    }

    @NonNull
    @Override
    public WinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View winView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem_winnings_chart, parent, false);
        return new WinViewHolder(winView);
    }

    @Override
    public void onBindViewHolder(@NonNull WinViewHolder holder, int position) {
        UserDao currentWin = winnings.get(position);
       // holder.placeTextView.setText(position + 1 + "");
        holder.placeTextView.setText("");

        if (!User.getInstance().getIcon().equals("")) {
            Picasso.get().load(currentWin.getIcon()).into(holder.playerImageView);
        }
        holder.nameTextView.getContext().getPackageName();
//        holder.playerImageView.setImageResource(
//                holder.playerImageView.getResources().getIdentifier(currentWin.getIcon(),
//                        "drawable",
//                        holder.nameTextView.getContext().getPackageName())
//        );
        holder.nameTextView.setText(currentWin.getName());
        holder.playerNumWinningsTextView.setText(currentWin.getNumOfWin() + "");
    }

    @Override
    public int getItemCount() {
        return winnings.size();
    }

    public static class WinViewHolder extends RecyclerView.ViewHolder {

        public TextView placeTextView;
        public ImageView playerImageView;
        public TextView nameTextView;
        public TextView playerNumWinningsTextView;

        public WinViewHolder(@NonNull View itemView) {
            super(itemView);
            placeTextView = itemView.findViewById(R.id.textView_winnings_chart_place);
            playerImageView = itemView.findViewById(R.id.playerImageView);
            nameTextView = itemView.findViewById(R.id.textView_winnings_chart_name);
            playerNumWinningsTextView = itemView.findViewById(R.id.textView_winnings_chart_winningsNum);

        }
    }
}
