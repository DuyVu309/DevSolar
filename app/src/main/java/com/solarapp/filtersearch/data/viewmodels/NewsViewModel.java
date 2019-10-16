package com.solarapp.filtersearch.data.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.solarapp.filtersearch.data.News;
import com.solarapp.filtersearch.model.ArticlesItem;
import com.solarapp.filtersearch.model.ArticlesItemRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private ArticlesItemRepository repository = new ArticlesItemRepository(getApplication());

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ArticlesItem>> getAllArticles(String query) {
        return repository.getNewsMutableLiveData(query);
    }

    public LiveData<List<News>> getNewsSavedAndFavorite() {
        return repository.getSavedAndFavoriteLiveData();
    }

    public void insertNews(News news) {
        repository.insert(news);
    }

    public void updateNews(News news) {
        repository.update(news);
    }

    public void deleteNews(News news) {
        repository.delete(news);
    }

    public void downloadFile(ArticlesItem item) {
        repository.downloadFile(item);
    }
}
