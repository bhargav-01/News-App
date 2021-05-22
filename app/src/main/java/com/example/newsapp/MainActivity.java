package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.ferfalk.simplesearchview.SimpleSearchView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private String News_REQUEST_URL = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json";
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    SimpleSearchView searchView;

    private ArrayList<newsType> newsTypeArrayList;
    private nesTypeAdapter newsTypeAdapter;
//    WrapViewPager mViewPager;

    private  newsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        final ListView listView=(ListView)findViewById(R.id.list);
        adapter=new newsAdapter(getApplicationContext(),new ArrayList<News>());

        searchView=findViewById(R.id.searchView);
        viewPager =findViewById(R.id.viewPager);
        toolbar=findViewById(R.id.toolbar);

        runOnUiThread(new Runnable() {
            @SuppressLint("ResourceType")
            @Override
            public void run() {
                listView.setAdapter(adapter);

                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                loadCards();
                LoaderManager.getInstance(MainActivity.this).initLoader(1, null, MainActivity.this);

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        String title=newsTypeArrayList.get(position).getTitle();
                        toolbar.setTitle(title);
                        News_REQUEST_URL=newsTypeArrayList.get(position).getUrl();
                        LoaderManager.getInstance(MainActivity.this).restartLoader(1,null,MainActivity.this);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });




    }

    private void loadCards() {
        newsTypeArrayList=new ArrayList<>();
        newsTypeArrayList.add(new newsType("General",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"));
        newsTypeArrayList.add(new newsType("Sports",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"));
        newsTypeArrayList.add(new newsType("Entertainment",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/entertainment/in.json"));
        newsTypeArrayList.add(new newsType("Health",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"));
        newsTypeArrayList.add(new newsType("Technology",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"));
        newsTypeArrayList.add(new newsType("Science",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/science/in.json"));
        newsTypeArrayList.add(new newsType("Business",R.drawable.bgfinal,"https://saurav.tech/NewsAPI/top-headlines/category/business/in.json"));
        newsTypeAdapter =new nesTypeAdapter(this,newsTypeArrayList);
        viewPager.setAdapter(newsTypeAdapter);
    }



    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return  new NewsAsyncLoader(this,News_REQUEST_URL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (searchView.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchView.setMenuItem(item);
                searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        News_REQUEST_URL="https://newsapi.org/v2/everything?q="+query+"&language=en&sortBy=popularity&apiKey=4dbc17e007ab436fb66416009dfb59a8";
                        LoaderManager.getInstance(MainActivity.this).restartLoader(1,null,MainActivity.this);
                        Log.d("SimpleSearchView", "Submit:" + query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Log.d("SimpleSearchView", "Text changed:" + newText);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextCleared() {
                        Log.d("SimpleSearchView", "Text cleared");
                        return false;
                    }
                });

            }
        });

        Log.d("bhargav","patel");
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }


    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {
        adapter.clear();


        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {
          adapter.clear();
    }
}