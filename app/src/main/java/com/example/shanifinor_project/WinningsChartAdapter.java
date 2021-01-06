package com.example.shanifinor_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WinningsChartAdapter extends RecyclerView.Adapter<WinningsChartAdapter.WinViewHolder> {
    private ArrayList<User> winnings;

    public WinningsChartAdapter(ArrayList<User> winnings) {
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
        User currentWin = winnings.get(position);
        holder.placeTextView.setText(currentWin.getPlace()+"");
        holder.playerImageView.setImageResource(
                holder.playerImageView.getResources().getIdentifier(currentWin.getIcon(),
                        "drawable",
                        holder.nameTextView.getContext().getPackageName())
        );
        holder.nameTextView.setText(currentWin.getName());
        holder.playerNumWinningsTextView.setText(currentWin.getNumOfWin()+"");
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
            placeTextView = itemView.findViewById(R.id.textView_winnings_chart_place1);
            playerImageView = itemView.findViewById(R.id.playerImageView);
            nameTextView = itemView.findViewById(R.id.textView_winnings_chart_name1);
            playerNumWinningsTextView = itemView.findViewById(R.id.textView_winnings_chart_winningsNum);

        }
    }
}
