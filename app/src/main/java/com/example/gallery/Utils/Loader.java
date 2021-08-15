package com.example.gallery.Utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


import com.example.gallery.Database.SqlLiteConection;
import com.example.gallery.Model.Album;
import com.example.gallery.Model.AlbumImage;
import com.example.gallery.Model.ItemImageFolder;
import com.example.gallery.Model.ItemImageView;
import com.example.gallery.Model.Picture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Loader {


    public static ArrayList<ItemImageView> listOfImages(Context context)
    {
        Uri url;
        Cursor cursor;
        int column_idex_data, column_idex_folder;
        ArrayList<ItemImageView> listOfAllImages = new ArrayList<>();
        String absPathOfIamge;
        url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(url, projection, null, null, orderBy+ " DESC");
        column_idex_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while(cursor.moveToNext())
        {
            absPathOfIamge = cursor.getString(column_idex_data);
            String date = getInfomationImage(absPathOfIamge);
            if (listOfAllImages.size() == 0)
            {
                ItemImageView itemImageView = new ItemImageView();
                itemImageView.setDate(date);
                itemImageView.add_Image(absPathOfIamge);
                listOfAllImages.add(itemImageView);
            }
            else {
                String last_date = listOfAllImages.get(listOfAllImages.size() - 1).getDate();

                if (date.compareToIgnoreCase(last_date) == 0)
                {
                    // chua sang mot ngay moi
                    listOfAllImages.get(listOfAllImages.size()-1).add_Image(absPathOfIamge);
                }
                else
                {
                    ItemImageView itemImageView = new ItemImageView();
                    itemImageView.setDate(date);
                    itemImageView.add_Image(absPathOfIamge);
                    listOfAllImages.add(itemImageView);
                }
            }
        }
        return listOfAllImages;
    }

    public static String getInfomationImage(String filePath)
    {
        File file = new File(filePath);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(file.lastModified());
    }

    public static List<Picture> load_all_image_from_disk(Context context)
    {
        List <Picture> list_data = new ArrayList<>();
        Uri url;
        Cursor cursor;
        int column_idex_data, column_idex_folder;
        String absPathOfIamge;
        url = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(url, projection, null, null, orderBy+ " DESC");
        column_idex_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while(cursor.moveToNext())
        {
            absPathOfIamge = cursor.getString(column_idex_data);
            Picture pic = new Picture();
            pic.setSelected(false);
            pic.setPicturePath(absPathOfIamge);
            list_data.add(pic);
        }
        return list_data;
    }

    public static ArrayList<ItemImageFolder> getPicturePaths(Context context){
        ArrayList<ItemImageFolder> picFolders = new ArrayList<>();
        ArrayList<String> picPaths = new ArrayList<>();
        Uri allImagesuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = context.getContentResolver().query(allImagesuri, projection, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do{
                ItemImageFolder folds = new ItemImageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String datapath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                //String folderpaths =  datapath.replace(name,"");
                String folderpaths = datapath.substring(0, datapath.lastIndexOf(folder+"/"));
                folderpaths = folderpaths+folder+"/";
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths);

                    folds.setPath(folderpaths);
                    folds.setFolderName(folder);
                    folds.setFirstPic(datapath);
                    folds.addpics();
                    picFolders.add(folds);
                }else{
                    for(int i = 0;i<picFolders.size();i++){
                        if(picFolders.get(i).getPath().equals(folderpaths)){
                            picFolders.get(i).setFirstPic(datapath);
                            picFolders.get(i).addpics();
                        }
                    }
                }
            }while(cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // load from database
        SqlLiteConection sqlLiteConection = new SqlLiteConection(context);
        List<Album> albumList = sqlLiteConection.getAllAlbums();
        ItemImageFolder itemImageFolder;
        for (Album a: albumList)
        {
            List<AlbumImage> list_image_album = sqlLiteConection.getImageWithAlbumName(a.getName());
            if (list_image_album.size() > 0)
            {
                itemImageFolder = new ItemImageFolder();
                itemImageFolder.setPath("database");
                itemImageFolder.setFolderName(a.getName());
                itemImageFolder.setFirstPic(list_image_album.get(0).getImage_path());
                itemImageFolder.setNumberOfPics(list_image_album.size());
                picFolders.add(itemImageFolder);
            }
        }
        return picFolders;
    }

    public static ArrayList<String> getAllImagesByFolder(Context context, String path){
        ArrayList<String> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = context.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                images.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            }while(cursor.moveToNext());
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
}
