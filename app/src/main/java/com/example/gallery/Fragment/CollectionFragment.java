package com.example.gallery.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapter.CollectionAdapter;
import com.example.gallery.Adapter.GalleryAdapter;
import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.ItemImageFolder;
import com.example.gallery.Model.ItemImageView;
import com.example.gallery.Model.Picture;
import com.example.gallery.R;
import com.example.gallery.ShowImageActivity;
import com.example.gallery.ShowListImageActivity;
import com.example.gallery.Utils.Loader;

import java.util.List;

public class CollectionFragment extends Fragment {

    RecyclerView recyclerView;
    CollectionAdapter collectionAdapter;
    Context conn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_collections, container, false);
        conn = container.getContext();

        List<ItemImageFolder> list_data = Loader.getPicturePaths(conn);
//        System.out.println(list_data.get(0));
        recyclerView = view.findViewById(R.id.list_imageFolder);
        collectionAdapter = new CollectionAdapter(conn, list_data, new CollectionAdapter.FolderListener() {
            @Override
            public void onClickFolder(String path, String folderName) {

                Intent myIntent = new Intent(conn, ShowListImageActivity.class);
                myIntent.putExtra("path", path);
                myIntent.putExtra("name", folderName);
                conn.startActivity(myIntent);
            }

            @Override
            public void onLongClick(int postion) {

                SqlLiteConection sqlLiteConection = new SqlLiteConection(conn);
                if (sqlLiteConection.remove_album(list_data.get(postion).getFolderName())) {
                    Toast.makeText(conn, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list_data.remove(postion);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(conn, "Chỉ xóa được album người dùng tạo.", Toast.LENGTH_SHORT).show();
                }
            }

        });
        recyclerView.setAdapter(collectionAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(conn, 4));
        return view;
    }
}
