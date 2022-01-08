package com.example.finalproject.Datamodel;

public class ItemDataModel {

    int image;
    String txtname;

    public ItemDataModel(int image, String txtname) {
        this.image = image;
        this.txtname = txtname;
    }

    public int getImage() {
        return image;
    }

    public String getTxtname() {
        return txtname;
    }
}