package com.solarapp.filtersearch.data;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NewsDao {
    @Insert(onConflict = REPLACE)
    void insertNews(News news);

    @Update(onConflict = REPLACE)
    void updateNews(News news);

    @Delete
    void deleteNews(News news);

    @Query("SELECT * FROM News")
    public MutableLiveData<List<News>> findAllNewsSync();
}
