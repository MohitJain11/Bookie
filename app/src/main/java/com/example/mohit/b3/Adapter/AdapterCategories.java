package com.example.mohit.b3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mohit.b3.R;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {
    Context context;
    List<Integer> images;

    public AdapterCategories(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.all_category_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.image.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image_of_category);
        }
    }
}
