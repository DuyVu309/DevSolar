package com.solarapp.filtersearch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.solarapp.filtersearch.models.ArticlesItem;
import com.solarapp.filtersearch.models.ArticlesItemRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private ArticlesItemRepository repository = new ArticlesItemRepository();

    public LiveData<List<ArticlesItem>> getAllArticles(String query) {
        return repository.getMutableLiveData(query);
    }
}
