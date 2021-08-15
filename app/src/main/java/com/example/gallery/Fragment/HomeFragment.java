package com.example.gallery.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapter.GalleryAdapter;
import com.example.gallery.Model.ItemImageView;
import com.example.gallery.R;
import com.example.gallery.Utils.Loader;

import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    Context conn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        conn = container.getContext();
        List<ItemImageView> list_data = Loader.listOfImages(conn);
        recyclerView = view.findViewById(R.id.list_dataImage);
        galleryAdapter = new GalleryAdapter(conn, list_data);
        recyclerView.setAdapter(galleryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(conn);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
}
