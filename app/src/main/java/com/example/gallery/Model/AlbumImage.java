package com.example.gallery.Model;

public class AlbumImage {

    private int ID;
    private String album_name;
    private String image_path;

    public AlbumImage(int ID, String album_name, String image_path) {
        this.ID = ID;
        this.album_name = album_name;
        this.image_path = image_path;
    }

    public AlbumImage(String album_name, String image_path) {
        this.album_name = album_name;
        this.image_path = image_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }
}
