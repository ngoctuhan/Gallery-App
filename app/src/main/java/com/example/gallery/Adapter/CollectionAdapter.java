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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.ItemImageFolder;
import com.example.gallery.Model.ItemImageView;
import com.example.gallery.R;
import com.example.gallery.Utils.FileFunction;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> implements PopupMenu.OnMenuItemClickListener{


    List<ItemImageFolder> list_item;
    Context context;
    protected FolderListener folderListener;
    int pos;

    public CollectionAdapter(Context context, List<ItemImageFolder> list_item , FolderListener folderListener) {
        this.list_item = list_item;
        this.context = context;
        this.folderListener = folderListener;
    }
    @NonNull
    @Override
    public CollectionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CollectionAdapter.CollectionHolder(
                LayoutInflater.from(context).inflate(R.layout.pic_folder_item, parent, false)
        );
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CollectionHolder holder, int position) {

        ItemImageFolder itemImageFolder = list_item.get(position);
        holder.textView_Folder.setText(itemImageFolder.getFolderName());

        holder.textView_Amount.setText(itemImageFolder.getNumberOfPics() + "");

        holder.imageView.setImageBitmap(BitmapFactory.decodeFile(itemImageFolder.getFirstPic()));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                folderListener.onClickFolder(itemImageFolder.getPath(), itemImageFolder.getFolderName());
            }
        });

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                pos = position;
                PopupMenu popup = new PopupMenu(context, view);
                popup.setOnMenuItemClickListener(CollectionAdapter.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
                return false;

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_item.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.delete_item:
                // do your code
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setTitle("Xóa album")
                        .setMessage("Bạn có chắc chắn xóa album này chứ ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                folderListener.onLongClick(pos);
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

    public class CollectionHolder extends RecyclerView.ViewHolder {

        TextView textView_Folder;
        TextView textView_Amount;
        ImageView imageView;
        public CollectionHolder(@NonNull View itemView) {
            super(itemView);

            textView_Folder = itemView.findViewById(R.id.folderName);
            textView_Amount = itemView.findViewById(R.id.folderSize);
            imageView = itemView.findViewById(R.id.folderPic);
        }
    }

    public interface FolderListener{

        void onClickFolder(String path, String folderName);

        void onLongClick(int postion);
    }
}
