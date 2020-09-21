package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class newsAdapter extends ArrayAdapter<News> {
    public newsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0,objects);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }

        News n=getItem(position);
        TextView title=(TextView)listItemView.findViewById(R.id.title);
        title.setText(n.getTitle());

        TextView section=(TextView)listItemView.findViewById(R.id.section);
        String []s=n.getDate().split("T");
        section.setText("Section: "+n.getSection());

        TextView type=(TextView)listItemView.findViewById(R.id.type);
        type.setText("Type: "+n.getType());

        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText("Publish-Date: "+s[0]);

        return  listItemView;


    }

}
