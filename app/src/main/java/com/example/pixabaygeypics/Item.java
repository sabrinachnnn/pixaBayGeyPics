package com.example.pixabaygeypics;

public class Item {

    private  String imageURL;
    private  int likes;

    public Item(String imageURL, int likes){
        this.imageURL = imageURL;
        this.likes=likes;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getLikes() {
        return likes;
    }
}
