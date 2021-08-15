package com.example.gallery.Model;

public class ItemImageFolder {

    private  String path;
    private  String FolderName;
    private int numberOfPics = 0;
    private String firstPic;

    public ItemImageFolder() {
    }

    public ItemImageFolder(String path, String folderName) {
        this.path = path;
        FolderName = folderName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getNumberOfPics() {
        return numberOfPics;
    }

    public void setNumberOfPics(int numberOfPics) {
        this.numberOfPics = numberOfPics;
    }

    public void addpics(){
        this.numberOfPics++;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    @Override
    public String toString() {
        return "ItemImageFolder{" +
                "path='" + path + '\'' +
                ", FolderName='" + FolderName + '\'' +
                ", numberOfPics=" + numberOfPics +
                ", firstPic='" + firstPic + '\'' +
                '}';
    }
}
