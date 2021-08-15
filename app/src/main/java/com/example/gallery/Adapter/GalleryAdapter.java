package com.example.gallery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.MainActivity;
import com.example.gallery.Model.ItemImageView;
import com.example.gallery.R;
import com.example.gallery.ShowImageActivity;

import java.io.Serializable;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder>{

    private Context context;
    private List<ItemImageView> list_item;

    public GalleryAdapter(Context context, List<ItemImageView> list_item) {
        this.context = context;
        this.list_item = list_item;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new GalleryHolder(
                LayoutInflater.from(context).inflate(R.layout.date_item, parent, false)
        );

    }
    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, int position) {

        ItemImageView itemImageView = list_item.get(position);

        holder.textView.setText(itemImageView.getDate());
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        ImageAdapter imageAdapter = new ImageAdapter(context, itemImageView.getList_img(), new ImageAdapter.PhotoListener() {
            @Override
            public void onClickPhoto(int post, List<String> list_data) {
//                Toast.makeText(context, "Click a imnage", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, ShowImageActivity.class);
                myIntent.putExtra("post", post);
                myIntent.putExtra("list_img", (Serializable) list_data);
                context.startActivity(myIntent);
            }

        });
        holder.recyclerView.setAdapter(imageAdapter);
    }
    @Override
    public int getItemCount() {
        return list_item.size();
    }

    public class GalleryHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        RecyclerView recyclerView;

        public GalleryHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.date_image);
            recyclerView = itemView.findViewById(R.id.recycleview_listImage);
        }
    }
}
