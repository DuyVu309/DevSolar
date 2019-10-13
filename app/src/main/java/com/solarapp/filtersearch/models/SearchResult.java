package com.solarapp.filtersearch.models;

import java.util.List;

public class SearchResult {
    private int totalResults;
    private List<ArticlesItem> articles;
    private String status;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticlesItem> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesItem> articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "SearchResult{" +
                        "totalResults = '" + totalResults + '\'' +
                        ",articles = '" + articles + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}