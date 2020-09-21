package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class QueryUtils {


    private static final String LOG_TAG ="nbbb" ;

    public static ArrayList<News> fetchNewsData(String mUrl) {
        URL url=create(mUrl);
        String  jsonResponse="";
        try {
             jsonResponse=makeHttpRequest(url);
        }
        catch ( Exception e)
        {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<News> news=extractFeatureFromJson(jsonResponse);
        return  news;

    }

    private static ArrayList<News> extractFeatureFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        ArrayList<News> news=new ArrayList<>();

        try{
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONObject responce=jsonObject.getJSONObject("response");
            JSONArray result=responce.getJSONArray("results");

            for(int i=0;i<result.length();i++)
            {
                JSONObject item=result.getJSONObject(i);
                news.add(new News(item.getString("webTitle"),item.getString("type"),item.getString("sectionName"),item.getString("webUrl"),item.getString("webPublicationDate")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  news;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponce="";
        HttpsURLConnection httpsURLConnection=null;
        InputStream inputStream=null;
        if(url==null)
        {
            return  jsonResponce;
        }
        try{
            httpsURLConnection=(HttpsURLConnection)url.openConnection();
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            if(httpsURLConnection.getResponseCode()==200)
            {
                inputStream=httpsURLConnection.getInputStream();
                jsonResponce=ReadInputStream(inputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(httpsURLConnection!=null)
            {
                httpsURLConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }

     return jsonResponce;
    }

    private static String ReadInputStream(InputStream inputStream) throws IOException {
        StringBuilder s=new StringBuilder();
        if(inputStream==null)
        {
            return  null;
        }
        InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        String line=bufferedReader.readLine();
        while (line!=null)
        {
            s.append(line);
            line=bufferedReader.readLine();

        }
        return  s.toString();
    }

    private static URL create(String mUrl){
        URL url=null;
        try {
          url=new URL(mUrl);
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return  url;
    }

}
