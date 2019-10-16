package com.solarapp.filtersearch.interfaces;

import com.solarapp.filtersearch.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("everything")
    Call<SearchResult> getSearchResult(@Query("q") String query, @Query("language") String language, @Query("apiKey") String key);
}
