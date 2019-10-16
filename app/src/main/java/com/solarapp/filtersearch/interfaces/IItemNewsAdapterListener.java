package com.solarapp.filtersearch.interfaces;

import com.solarapp.filtersearch.data.News;
import com.solarapp.filtersearch.model.ArticlesItem;

public interface IItemNewsAdapterListener {
    void onItemNewsClick(ArticlesItem data);

    void onItemNewsSavedAndFavorite(News news);
}
