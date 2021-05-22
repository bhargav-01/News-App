package com.example.newsapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionHelper;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class nesTypeAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<newsType> newsArray;

    public nesTypeAdapter(Context context, ArrayList<newsType> newsArray) {
        this.context = context;
        this.newsArray = newsArray;
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

//        ((MainActivity.class)g)
        final View view= LayoutInflater.from(context).inflate(R.layout.card_item,container,false);
        final ImageView bannerTv=view.findViewById(R.id.myImageView);
        TextView textView=view.findViewById(R.id.newstype);

        newsType news=newsArray.get(position);

        Typeface typeface = ResourcesCompat.getFont(view.getContext(),R.font.libre_baskerville_bold);
        textView.setTypeface(typeface);
        textView.setText(news.getTitle());

        ((ViewPager)container).addView(view, container.getChildCount() > position ? position : ((ViewPager)container).getChildCount());
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
//        super.destroyItem(container, position, object);
    }


}
