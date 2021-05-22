package com.example.newsapp;

public class News {
    private String title ;
    private String  type;
    private String section;
    private String News_url;
    private  String ImageUrl;
    private  String date;
    private  String author;
    private String org;

    News(String title, String type, String section, String News_url, String date, String author, String ImageUrl, String org)
    {
        this.title=title;
        this.date=date;
        this.News_url=News_url;
        this.section=section;
        this.type=type;
        this.author=author;
        this.ImageUrl=ImageUrl;
        this.org=org;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getNews_url() {
        return News_url;
    }

    public String getOrg() {
        return org;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getSection() {
        return section;
    }

    public String getType() {
        return type;
    }
}
