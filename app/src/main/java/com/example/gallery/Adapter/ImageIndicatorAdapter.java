package com.example.gallery.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.gallery.Model.Picture;
import com.example.gallery.R;

import java.util.ArrayList;

public class ImageIndicatorAdapter extends RecyclerView.Adapter<ImageIndicatorAdapter.IndicatorHolder>{


    ArrayList<Picture> pictureList;
    Context context;
    private final imageIndicatorListener imageListerner;


    public ImageIndicatorAdapter(imageIndicatorListener imageListerner) {
        this.imageListerner = imageListerner;
    }

    public ImageIndicatorAdapter(ArrayList<Picture> pictureList, Context pictureContx, imageIndicatorListener imageListerner) {
        this.pictureList = pictureList;
        this.context = pictureContx;
        this.imageListerner = imageListerner;
    }

    @NonNull
    @Override
    public IndicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ImageIndicatorAdapter.IndicatorHolder(
                LayoutInflater.from(context).inflate(R.layout.indicator_holder, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorHolder holder, int position) {

        Picture pic = pictureList.get(position);
        int pos = position;
        holder.positionController.setBackgroundColor(pic.getSelected() ? Color.parseColor("#00000000") : Color.parseColor("#8c000000"));
        holder.image.setImageBitmap(BitmapFactory.decodeFile(pic.getPicturePath()));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic.setSelected(true);
                notifyDataSetChanged();
                imageListerner.onImageIndicatorClicked(pos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public class IndicatorHolder extends RecyclerView.ViewHolder{

        public ImageView image;

        View positionController;

        IndicatorHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageIndicator);
            positionController = itemView.findViewById(R.id.activeImage);
        }
    }
    public interface imageIndicatorListener {

        void onImageIndicatorClicked(int postion);
    }

}


