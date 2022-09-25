package com.example.newsapp;

import java.util.ArrayList;

public class NewsModel {

    private int totalResults;
    private String status;
    private ArrayList<Articles> articles;


    public NewsModel(int totalResults, String status, ArrayList<Articles> articals) {
        this.totalResults = totalResults;
        this.status = status;
        this.articles = articals;
    }


    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }
}
