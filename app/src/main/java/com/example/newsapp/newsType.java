package com.example.newsapp;

public class newsType {
    String title;
    int image;
    String url;

    public newsType(String title, int image, String url) {
        this.title = title;
        this.image = image;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
