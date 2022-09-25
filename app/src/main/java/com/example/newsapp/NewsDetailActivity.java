package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    String title,desc,content,imageUrl,url;

    TextView newsTitle,newsDescription,newsContent;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsTitle=findViewById(R.id.news_title);
        newsDescription=findViewById(R.id.news_description);
        newsContent=findViewById(R.id.news_content);
        image=findViewById(R.id.news_image);

        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("content");
        content=getIntent().getStringExtra("desc");
        imageUrl=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");


        newsTitle.setText(title);
        newsDescription.setText(desc);
        newsContent.setText(content);
        Picasso.get().load(imageUrl).into(image);


    }
}