package com.example.gallery.Adapter;

import android.annotation.SuppressLint;
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

import com.example.gallery.Model.Picture;
import com.example.gallery.R;

import java.util.List;

public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.ImageSelecHolder> {

    List<Picture> list_all_picture;
    Context context;
    private SelectionItem onSelectionItem;

    public ImageSelectAdapter(List<Picture> list_all_picture, Context context, SelectionItem onSelectionItem) {
        this.list_all_picture = list_all_picture;
        this.context = context;
        this.onSelectionItem = onSelectionItem;
    }

    @NonNull
    @Override
    public ImageSelecHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ImageSelectAdapter.ImageSelecHolder(
                LayoutInflater.from(context).inflate(R.layout.indicator_holder, parent, false)
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ImageSelecHolder holder, int position) {

        // Set size of layout card view

        holder.cardView.getLayoutParams().height = 355;
        holder.cardView.getLayoutParams().width = 355;

        Picture pic = list_all_picture.get(position);
        holder.selectItem.setBackgroundColor(pic.getSelected() ? Color.parseColor("#00000000") : Color.parseColor("#8c000000"));
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(pic.getPicturePath()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectionItem.onClickItem(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list_all_picture.size();
    }

    public class ImageSelecHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        View selectItem;

        public ImageSelecHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageIndicator);
            selectItem = itemView.findViewById(R.id.activeImage);
            cardView =  itemView.findViewById(R.id.indicatorCard);
        }
    }
    public interface SelectionItem
    {
        public  void onClickItem(int postion);
    }

}
