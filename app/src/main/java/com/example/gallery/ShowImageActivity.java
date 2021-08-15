package com.example.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gallery.Adapter.ImageIndicatorAdapter;
import com.example.gallery.Adapter.ImagesPagerAdapter;
import com.example.gallery.Model.Picture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends AppCompatActivity {

    private List<Picture>list_image;
    private int previousSelected = -1;
    private RecyclerView recyclerView;
    private ImageIndicatorAdapter imageIndicatorAdapter;
    ImageView imageView;
    ImagesPagerAdapter pagingImages;
    private ViewPager imagePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_show);

        int post = getIntent().getIntExtra("post", 0);
        List<String> list_img = (List<String>) getIntent().getSerializableExtra("list_img");

        list_image = convert_listStr2Pic(list_img);


        imagePager = findViewById(R.id.imagePager);
        pagingImages = new ImagesPagerAdapter(this, list_image);
        imagePager.setAdapter(pagingImages);
        imagePager.setOffscreenPageLimit(3);
        imagePager.setCurrentItem(post);//displaying the image at the current position passed by the ImageDisplay Activity


        recyclerView = findViewById(R.id.indicatorRecycler);
        imageIndicatorAdapter = new ImageIndicatorAdapter((ArrayList<Picture>) list_image, this, new ImageIndicatorAdapter.imageIndicatorListener() {
            @Override
            public void onImageIndicatorClicked(int postion) {

                list_image.get(previousSelected).setSelected(false);
                previousSelected = postion;
                imagePager.setCurrentItem(postion);
                list_image.get(postion).setSelected(true);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scrollToPosition(postion);

            }
        });
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(this,1,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(imageIndicatorAdapter);

        list_image.get(post).setSelected(true);
        previousSelected = post;
        imageIndicatorAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(post);



    }
    public static List<Picture> convert_listStr2Pic(List<String> list_str)
    {
        List <Picture> list_pic = new ArrayList<>();

        for(int i = 0; i < list_str.size(); i++)
        {
            Picture p =  new Picture();
            p.setPicturePath(list_str.get(i));
            p.setSelected(false);
            list_pic.add(p);
        }
        return list_pic;
    }
}
