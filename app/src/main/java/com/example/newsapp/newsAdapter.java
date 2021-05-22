package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class newsAdapter extends ArrayAdapter<News> {
    public newsAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0,objects);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {


        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }

        final News n=getItem(position);
        TextView title=(TextView)listItemView.findViewById(R.id.title);
        String str=n.getTitle();
        StringBuilder input1 = new StringBuilder();
        input1.append(str);
        String str1=new String(input1.reverse());
        String arr[]=str1.split("-",2);

        if(arr.length==1)
        {
            title.setText(str);
        }
        else
        {
            StringBuilder input2 = new StringBuilder();
            input2.append(arr[1]);
            String str2=new String(input2.reverse());
            title.setText(str2);
        }
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),webview.class);
                intent.putExtra("url",n.getNews_url());
                v.getContext().startActivity(intent);
            }
        });

        listItemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/*");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                share.putExtra(Intent.EXTRA_SUBJECT, n.getTitle());
                share.putExtra(Intent.EXTRA_TITLE,"News App");
                share.putExtra(Intent.EXTRA_TEXT, n.getTitle() + "\n\nNews App\n\n"+n.getNews_url());
                v.getContext().startActivity(Intent.createChooser(share, "Share link!"));
            }
        });


        Picasso.get().load(Uri.parse(n.getImageUrl())).transform(new RoundedCornersTransformation(10, 10)).resize(150, 150).into((ImageView) listItemView.findViewById(R.id.image));
        String []s=n.getDate().split("T");
        TextView org=(TextView)listItemView.findViewById(R.id.org);
        org.setText(n.getOrg());
        TextView date=(TextView)listItemView.findViewById(R.id.date);
        date.setText(s[0]);

        return  listItemView;


    }

}
