package com.example.gallery.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gallery.Model.Album;
import com.example.gallery.Model.AlbumImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlLiteConection extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Gallery";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "AlbumImage";
    private static final String TABLE_NAME2 = "Album";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    public SqlLiteConection(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_IMAGE);
        sqLiteDatabase.execSQL(create_students_table);

        String create_album_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT)", TABLE_NAME2, KEY_ID, KEY_NAME);
        sqLiteDatabase.execSQL(create_album_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_students_table);

        String drop_table_album = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME2);
        sqLiteDatabase.execSQL(drop_table_album);
        onCreate(sqLiteDatabase);
    }

    public void addNewAlbum(Album album)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, album.getName());
        System.out.println(db.insert(TABLE_NAME2, null, values));
        db.close();
    }
    public void addNewRecord(AlbumImage albumImage) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, albumImage.getAlbum_name());
        values.put(KEY_IMAGE, albumImage.getImage_path());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public  List<Album>  getAllAlbums()
    {
        List<Album>  list_album = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + ";", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                Album a = new Album(cursor.getInt(0), cursor.getString(1));
                list_album.add(a);
                cursor.moveToNext();
            }
        }
        return list_album;
    }
    public List<AlbumImage> getImageWithAlbumName(String Album_name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<AlbumImage> list_data =  new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_NAME + " = ?", new String[] { String.valueOf(Album_name) },null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                AlbumImage a = new AlbumImage(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                list_data.add(a);
                cursor.moveToNext();
            }
        }
        return list_data;
    }

    public boolean remove_album(String album_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_NAME2, KEY_NAME + "= '" + album_name +"';", null) ;
            db.delete(TABLE_NAME, KEY_NAME + "= '" + album_name + "';", null) ;
            db.close();
            return true;
        }
        catch (Exception e){
            return false;
        }

    }
}
