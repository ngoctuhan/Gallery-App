package com.example.gallery.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class Previewer {

    public static byte[] thumnail_image(String fileName)
    {
        byte[] imageData = null;

        try
        {
            final int THUMBNAIL_SIZE = 64;

            FileInputStream fis = new FileInputStream(fileName);
            Bitmap imageBitmap = BitmapFactory.decodeStream(fis);

            imageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageData = baos.toByteArray();

            return imageData;

        }
        catch(Exception ex) {

        }
        return imageData;
    }

}
