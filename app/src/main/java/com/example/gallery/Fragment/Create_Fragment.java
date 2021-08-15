package com.example.gallery.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapter.ImageSelectAdapter;
import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.Picture;
import com.example.gallery.R;
import com.example.gallery.Utils.Loader;
import com.example.gallery.Utils.PopupEvent;

import java.util.List;

public class Create_Fragment extends Fragment {

    RecyclerView recyclerView;
    ImageSelectAdapter imageSelectAdapter;
    Context context;
    List<Picture> pictureList;
    View view;
    SqlLiteConection sqlLiteConection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_collection, container, false);
        context = container.getContext();
        sqlLiteConection = new SqlLiteConection(context);
        pictureList = Loader.load_all_image_from_disk(context);

        recyclerView = view.findViewById(R.id.indicatorRecycler);
        imageSelectAdapter = new ImageSelectAdapter(pictureList, context, new ImageSelectAdapter.SelectionItem() {
            @Override
            public void onClickItem(int postion) {

                if (pictureList.get(postion).getSelected() == false) {
                    pictureList.get(postion).setSelected(true);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(postion);
                }
                else
                {
                    pictureList.get(postion).setSelected(false);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.scrollToPosition(postion);
                }
            }
        });
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(imageSelectAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_album, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_new:
                // Do Activity menu item stuff here

                PopupEvent.new_add_click(context, view,  pictureList, sqlLiteConection);
                return true;


            case R.id.add_old:
                PopupEvent.new_old_click(context, view,  pictureList, sqlLiteConection);
                return false;
            default:
                break;
        }
        return false;
    }
}
