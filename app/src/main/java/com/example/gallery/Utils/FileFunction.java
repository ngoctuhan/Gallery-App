package com.example.gallery.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.gallery.Model.Picture;

import java.io.File;

public class FileFunction {

    public static int deleteFile(Context context, String path)
    {

        ContentResolver resolver = context.getApplicationContext().getContentResolver();

        Uri imageUri = Uri.parse(path);

        int numImagesRemoved = resolver.delete(
                imageUri,
                null,
                null);

        return numImagesRemoved;
    }

    public static void renameFile(String oldName, String newName)
    {

    }

    public static Picture getInfor(String path)
    {
        Picture p = new Picture();

        return p;
    }


}
