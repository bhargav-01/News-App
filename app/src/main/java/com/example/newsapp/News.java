package com.example.newsapp;

public class News {
    private String title ;
    private String  type;
    private String section;
    private String News_url;
    private  String date;

    News(String title,String type,String  section, String News_url,String date)
    {
        this.title=title;
        this.date=date;
        this.News_url=News_url;
        this.section=section;
        this.type=type;
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

    public String getSection() {
        return section;
    }

    public String getType() {
        return type;
    }
}
