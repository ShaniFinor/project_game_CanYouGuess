package com.example.shanifinor_project.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shanifinor_project.ui.screens.ImageItemsProfileActivity;
import com.example.shanifinor_project.R;

import java.util.ArrayList;

/**
 * Basic class of adapter for displaying list data in RecyclerView.
 * This is a base class for an adapter.
 * Adapters provide a link from an application-specific data set to the views displayed within RecyclerView.
 *
 * A class that builds the list of images displayed to the user according to the images he receives.
 * The purpose of the class is to display the images as an items.
 */
public class ImageItemAdapter extends RecyclerView.Adapter<ImageItemAdapter.ViewHolder> {

    private ArrayList<String> imageList;

    public ImageItemAdapter(ArrayList<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    private Context context;
    @NonNull
    @Override
    public ImageItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageItemAdapter.ViewHolder holder, int position) {
        // loading the images from the position
        Glide.with(holder.itemView.getContext()).load(imageList.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, imageList.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                ((ImageItemsProfileActivity)context).
                        closeAndSendUrlToUserProfile(imageList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
