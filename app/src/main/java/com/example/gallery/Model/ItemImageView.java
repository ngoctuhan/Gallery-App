package com.example.gallery.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemImageView {


    private String date;

    private List<String> list_img;

    public ItemImageView() {
        list_img = new ArrayList<>();
    }
    public ItemImageView(String date, List<String> list_img) {
        this.date = date;
        this.list_img = list_img;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getList_img() {
        return list_img;
    }

    public void setList_img(List<String> list_img) {
        this.list_img = list_img;
    }

    public void add_Image(String imagPath)
    {
        this.list_img.add(imagPath);
    }

    @Override
    public String toString() {
        return "ItemImageView{" +
                "date='" + date + '\'' +
                ", list_img=" + list_img +
                '}';
    }
}
