package com.solarapp.filtersearch.interfaces;

import com.solarapp.filtersearch.API.ParamApi;
import com.solarapp.filtersearch.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("?q={key_search}&amp;language=[language to search]&amp;apiKey=" + ParamApi.KEY_API)
    Call<List<Response>> getSearchResult(@Path(value = "key_search", encoded = true) String key_search);
}
