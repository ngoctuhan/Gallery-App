package com.example.gallery.Model;

import java.util.List;

public class Album {

    private int ID;
    private String name;

    public Album(String name) {
        this.name = name;
    }

    public Album(int ID, String name) {
        this.ID = ID;
        this.name = name;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Album{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
