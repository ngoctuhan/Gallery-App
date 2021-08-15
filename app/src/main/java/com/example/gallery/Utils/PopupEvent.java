package com.example.gallery.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Adapter.TextAlbumAdapter;
import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.Album;
import com.example.gallery.Model.AlbumImage;
import com.example.gallery.Model.Picture;
import com.example.gallery.R;

import java.util.List;

public class PopupEvent {


    public static void new_add_click(Context context, View view,List<Picture> list_data, SqlLiteConection sqlLiteConection)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_new_add, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button button = popupView.findViewById(R.id.btn_confirm);
        EditText editText = popupView.findViewById(R.id.name_folder);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
                // insert to folder
                Album a = new Album(editText.getText().toString());
                sqlLiteConection.addNewAlbum(a);

                AlbumImage albumImage;
                for (Picture p: list_data)
                {
                    if (p.getSelected() == true)
                    {
                        albumImage = new AlbumImage(a.getName(), p.getPicturePath());
                        sqlLiteConection.addNewRecord(albumImage);
                    }
                }
                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public static void new_old_click(Context context, View view,List<Picture> list_data, SqlLiteConection sqlLiteConection)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_add_old, null);
        final PopupWindow popupWindow;
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        RecyclerView recyclerView = popupView.findViewById(R.id.list_album);
        List<Album> albumList = sqlLiteConection.getAllAlbums();

        TextAlbumAdapter textAlbumAdapter = new TextAlbumAdapter(context, albumList, new TextAlbumAdapter.AlbumListener() {
            @Override
            public void onClickAlbum(int postion) {

                popupWindow.dismiss();
                // add to album postion = postion
                Album a =  albumList.get(postion);
                AlbumImage  albumImage;
                for( Picture p : list_data)
                {
                    if (p.getSelected() == true)
                    {
                        albumImage = new AlbumImage(a.getName(), p.getPicturePath());
                        sqlLiteConection.addNewRecord(albumImage);
                    }
                }
                Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new GridLayoutManager(context,albumList.size(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(textAlbumAdapter);



    }

}
