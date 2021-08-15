package com.example.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapter.ImageAdapter;
import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.Album;
import com.example.gallery.Model.AlbumImage;
import com.example.gallery.Model.Picture;
import com.example.gallery.Utils.Loader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowListImageActivity extends AppCompatActivity {

    private   String path, name;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_image);
        recyclerView = findViewById(R.id.list_image_show);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        List<String> list_data = new ArrayList<>();

        if (path.compareToIgnoreCase("database") == 0)
        {
            SqlLiteConection sqlLiteConection = new SqlLiteConection(this);
            List<AlbumImage> listAI= sqlLiteConection.getImageWithAlbumName(name);

            for(AlbumImage a: listAI)
            {
                list_data.add(a.getImage_path());
            }
        }
        else list_data = Loader.getAllImagesByFolder(this, path);
        ImageAdapter imageAdapter = new ImageAdapter(this, list_data, new ImageAdapter.PhotoListener() {
            @Override
            public void onClickPhoto(int post, List<String> list_img) {
                Intent myIntent = new Intent(ShowListImageActivity.this, ShowImageActivity.class);
                myIntent.putExtra("post", post);
                myIntent.putExtra("list_img", (Serializable) list_img);
                ShowListImageActivity.this.startActivity(myIntent);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(imageAdapter);
    }
}
