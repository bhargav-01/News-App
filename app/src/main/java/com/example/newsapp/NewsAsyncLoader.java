package com.example.newsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class NewsAsyncLoader extends AsyncTaskLoader<ArrayList<News>> {

    private  String mUrl;

    public NewsAsyncLoader(MainActivity mainActivity,String mUrl) {
        super(mainActivity);
        this.mUrl=mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if(mUrl==null)
        {
            return  null ;
        }

        ArrayList<News> news=QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
