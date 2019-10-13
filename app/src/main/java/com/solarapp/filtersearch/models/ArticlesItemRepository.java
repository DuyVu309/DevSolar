package com.solarapp.filtersearch.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.solarapp.filtersearch.API.ParamApi;
import com.solarapp.filtersearch.interfaces.GetDataService;
import com.solarapp.filtersearch.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ArticlesItemRepository {
    private static final String DEFAULT_LANGUAGE = "vi";
    private MutableLiveData<List<ArticlesItem>> mutableLiveData = new MutableLiveData<>();

    public ArticlesItemRepository() {
    }

    public MutableLiveData<List<ArticlesItem>> getMutableLiveData(String query) {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SearchResult> call = service.getSearchResult(query, DEFAULT_LANGUAGE, ParamApi.KEY_API);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(@NonNull Call<SearchResult> call, @NonNull retrofit2.Response<SearchResult> response) {
                try {
                    if (response.body() != null && response.body().getStatus().equalsIgnoreCase("ok")) {
                        mutableLiveData.setValue(response.body().getArticles());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SearchResult> call, @NonNull Throwable t) {
                Log.e("ArticlesItemRepository", "onFailure: " + t);
            }
        });
        return mutableLiveData;
    }
}
