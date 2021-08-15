package com.example.gallery.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.R;
import com.example.gallery.Utils.CustomDialog;
import com.example.gallery.Utils.FileFunction;

import java.util.List;

public class ImageAdapter extends  RecyclerView.Adapter<ImageAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener{

    private Context context;
    private List<String> list_image;
    protected PhotoListener photoListener;
    private int pos;

    public ImageAdapter(Context context, List<String> list_image, PhotoListener photoListener) {
        this.context = context;
        this.list_image = list_image;
        this.photoListener = photoListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imgFile = list_image.get(position);
        pos = position;
        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(imgFile));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println(pos);

                photoListener.onClickPhoto(position, list_image);
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                pos = position;
                PopupMenu popup = new PopupMenu(context, view);
                popup.setOnMenuItemClickListener(ImageAdapter.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
                return false;
            }
        });

    }
    @Override
    public int getItemCount() {
        return list_image.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

//        Toast.makeText(context, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.delete_item:
                // do your code
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setTitle("Xóa hình ảnh")
                        .setMessage("Bạn có chắc chắn xóa hình ảnh này chứ ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (FileFunction.deleteFile(context, list_image.get(pos)) > 0)
                                    Toast.makeText(context, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context, "Có lỗi xảy ra, Thử lại", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

                return true;
            case R.id.rename_item:
                // do your code
                return true;
            case R.id.share_item:
                // do your code
                return true;
            case R.id.infor_item:
                // do your code
                return true;
            default:
                return false;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }

    public interface PhotoListener
    {
        void onClickPhoto(int postion, List<String> list_file);

    }

}
