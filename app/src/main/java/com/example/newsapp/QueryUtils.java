package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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
    private static final String ACCEPT_PROPERTY = "application/geo+json;version=1";
    private static final String USER_AGENT_PROPERTY = "newsapi.org (patelbhargav9@gmail.com)";
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
            JSONArray result=jsonObject.getJSONArray("articles");
            Log.e(LOG_TAG,Integer.toString(result.length()));
            Log.d(LOG_TAG,Integer.toString(result.length()));
//            System.out.println("bhargav"+ result.length());
            for(int i=0;i<result.length();i++)
            {
                JSONObject item=result.getJSONObject(i);
                if(item.getString("author").isEmpty() && !item.getString("urlToImage").isEmpty())
                    news.add(new News(item.getString("title"),"Sports",item.getString("description"),item.getString("url"),item.getString("publishedAt"),item.getJSONObject("source").getString("name"),item.getString("urlToImage"),item.getJSONObject("source").getString("name")));
                if(!item.getString("urlToImage").isEmpty())
                    news.add(new News(item.getString("title"),"Sports",item.getString("description"),item.getString("url"),item.getString("publishedAt"),item.getString("author"),item.getString("urlToImage"),item.getJSONObject("source").getString("name")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
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
            httpsURLConnection.setConnectTimeout(30000);
            httpsURLConnection.setReadTimeout(60000);
            httpsURLConnection.setRequestProperty("Accept", ACCEPT_PROPERTY);  // added
            httpsURLConnection.setRequestProperty("User-Agent", USER_AGENT_PROPERTY); // added
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();
            Log.e(LOG_TAG,url.toString()+httpsURLConnection.getResponseCode());
            if(httpsURLConnection.getResponseCode()==200)
            {
                inputStream=httpsURLConnection.getInputStream();
                jsonResponce=ReadInputStream(inputStream);
            }
            else
            {
//                inputStream=
                Log.e(LOG_TAG,url.toString()+"bhagav");
                ReadInputStream(httpsURLConnection.getErrorStream());
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
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
            else
            {
                Log.e(LOG_TAG,url.toString()+"bhagav");
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
//        System.out.println("Error: "+bufferedReader.readLine());
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
