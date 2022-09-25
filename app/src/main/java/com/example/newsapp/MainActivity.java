package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    //  2ba31f87ab5648d1a121bd4f7254061f

    RecyclerView newsRv, categoryRV;
    ProgressBar loadingPB;
    ArrayList<Articles> articlesArrayList;
    ArrayList<CategoryRVModel> categoryRVModelArrayList;
    CategoryRVAdapter categoryRVAdapter;
    NewsRvAdapter newsRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        newsRv = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);

        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
        newsRvAdapter = new NewsRvAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList, this, this::onCategoryClick);
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        newsRv.setAdapter(newsRvAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        getCategories();
        getNews("All");

        newsRvAdapter.notifyDataSetChanged();

    }


    private void getCategories() {
        categoryRVModelArrayList.add(new CategoryRVModel("All", "https://images.unsplash.com/photo-1565453006698-a17d83b9e2af?ixid=MnwxMjA3fDB8fG51d3NWYXB1cnx1bnwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://images.unsplash.com/photo-1611162617213-7d7a39e9b1d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science", "https://media.istockphoto.com/photos/abstract-futuristic-with-connection-lines-on-blue-background-plexus-picture-id1285395672?b=1&k=20&m=1285395672&s=170667a&w=0&h=Wkn9h7EjOTLAAG8zSoTcnvrDrtFjTOPryJeIOBlD3r4="));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports", "https://images.unsplash.com/photo-1587280501635-68a0e82cd5ff?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=400&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("General", "https://images.unsplash.com/photo-1513151233558-d860c5398176?ixlib=rb-1.2.1&ixid=eyJhcHBfawQiojEyMDd9&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment", "https://images.unsplash.com/photo-1563341591-a4ef278eb34b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fGVudGVydGFpbm1lbnR8ZW58MHx8MHx8&auto=format&fit=crop&w=400&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        articlesArrayList.clear();
        String categoryUrl = " https://newsapi.org/v2/top-headlines?country=sa&category=" + category + "&apiKey=2ba31f87ab5648d1a121bd4f7254061f";
        String url = "https://newsapi.org/v2/top-headlines?country=sa&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=2ba31f87ab5648d1a121bd4f7254061f";
        String BASE_URL = "https://newsapi.org/";
        loadingPB.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);

        Call<NewsModel> call;

        if (category.equals("All")) {
            call = retrofitApi.getAllNews(url);
        } else {
            call = retrofitApi.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {

            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();

                for (int i = 0; i < articles.size(); i++) {
                    try {
                        articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                                articles.get(i).getUrl(), articles.get(i).getContent()));
                    }catch (Exception e){
                        Log.e("error", "onResponse: ",e);
                    }

                }
                newsRvAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
    }
}