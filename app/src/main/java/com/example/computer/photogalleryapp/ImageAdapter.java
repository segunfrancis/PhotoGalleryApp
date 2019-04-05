package com.example.computer.photogalleryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ImageModel> modelList;

    public ImageAdapter(Context context, List<ImageModel> model) {
        this.context = context;
        this.modelList = model;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.image_list_item, viewGroup, false);
        ImageViewHolder viewHolder = new ImageViewHolder(itemView);
        viewHolder.setClickListener(new ImageViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putParcelableArrayListExtra("modelList", (ArrayList<? extends Parcelable>) modelList);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder imageViewHolder, int i) {
        Glide.with(context).load(modelList.get(i).getUrl())
                .thumbnail(0.5f)
                .override(200, 200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ImageViewHolder) imageViewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        private ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private ImageViewHolder.ClickListener mClickListener;

        public interface ClickListener {
            void onItemClick(View view, int position);
        }

        public void setClickListener(ImageViewHolder.ClickListener clickListener) {
            mClickListener = clickListener;
        }
    }
}
