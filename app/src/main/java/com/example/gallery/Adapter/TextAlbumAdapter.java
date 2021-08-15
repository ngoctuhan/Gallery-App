package com.example.gallery.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Model.Album;
import com.example.gallery.R;

import java.util.List;

public class TextAlbumAdapter extends RecyclerView.Adapter<TextAlbumAdapter.TextAlbumHolder>{

    Context context;
    List<Album> list_data;
    AlbumListener albumListener;


    public TextAlbumAdapter(Context context, List<Album> list_data, AlbumListener albumListener) {
        this.context = context;
        this.list_data = list_data;
        this.albumListener = albumListener;
    }

    @NonNull
    @Override
    public TextAlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TextAlbumHolder(
                LayoutInflater.from(context).inflate(R.layout.text_item_folder, parent, false)
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull TextAlbumHolder holder, int position) {
        Album album = list_data.get(position);
        System.out.println(album);
        holder.textView.setText(album.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albumListener.onClickAlbum(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class TextAlbumHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        public TextAlbumHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name_album);
        }
    }

    public interface AlbumListener
    {
        public void onClickAlbum(int postion);
    }
}
