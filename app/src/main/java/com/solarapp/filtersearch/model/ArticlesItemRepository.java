package com.solarapp.filtersearch.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.solarapp.filtersearch.data.News;
import com.solarapp.filtersearch.data.NewsDao;
import com.solarapp.filtersearch.data.NewsDatabase;
import com.solarapp.filtersearch.interfaces.GetDataService;
import com.solarapp.filtersearch.network.API.ParamApi;
import com.solarapp.filtersearch.network.RetrofitClientInstance;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ArticlesItemRepository {
    private static final String DEFAULT_LANGUAGE = "vi";
    private MutableLiveData<List<ArticlesItem>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<News>> newsLiveData = new MutableLiveData<>();
    private NewsDao mNewsDao;


    public ArticlesItemRepository(Context mContext) {
        NewsDatabase mDb = NewsDatabase.getInMemoryDb(mContext);
        mNewsDao = mDb.newsDao();
    }

    public MutableLiveData<List<ArticlesItem>> getNewsMutableLiveData(String query) {
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


    public MutableLiveData<List<News>> getSavedAndFavoriteLiveData() {
        return newsLiveData;
    }

    public void insert(News news) {
        new insertAsyncTask(mNewsDao).execute(news);
    }

    public void update(News news) {
        new updateAsyncTask(mNewsDao).execute(news);
    }

    public void delete(News news) {
        new deleteAsyncTask(mNewsDao).execute(news);
    }

    public void downloadFile(ArticlesItem item) {
        new downloadAsyncTask(mNewsDao).execute(item);
    }


    private static class downloadAsyncTask extends AsyncTask<ArticlesItem, Void, ArticlesItem> {
        @SuppressLint("SdCardPath")
        final String PATH_FILE = "/data/data/com.solarapp.filtersearch/";
        String fileName = PATH_FILE + (System.currentTimeMillis() / 1000) + ".html";
        private NewsDao mAsyncTaskDao;

        downloadAsyncTask(NewsDao newsDao) {
            mAsyncTaskDao = newsDao;
        }

        @Override
        protected ArticlesItem doInBackground(ArticlesItem... articlesItems) {
            try {
                URL url = new URL(articlesItems[0].getUrl()); //you can write here any link

                File file = new File(fileName);

                long startTime = System.currentTimeMillis();
                Log.d("ImageManager", "download begining");
                Log.d("ImageManager", "download url:" + url);
                Log.d("ImageManager", "downloaded file name:" + fileName);
                /* Open a connection to that URL. */
                URLConnection ucon = url.openConnection();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
// Output stream to write file
                OutputStream output = new FileOutputStream(fileName);

                byte data[] = new byte[1024];

                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
            } catch (IOException e) {
                Log.d("ImageManager", "Error: " + e);
            }
            return articlesItems[0];
        }

        @Override
        protected void onPostExecute(ArticlesItem item) {
            News news = new News();
            news.title = item.getTitle();
            news.description = item.getDescription();
            news.date = item.getPublishedAt();
            news.image = item.getUrlToImage();
            news.pathFile = fileName;
            mAsyncTaskDao.insertNews(news);
        }
    }

    private static class insertAsyncTask extends AsyncTask<News, Void, Void> {
        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao newsDao) {
            mAsyncTaskDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            mAsyncTaskDao.insertNews(news[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<News, Void, Void> {
        private NewsDao mAsyncTaskDao;

        updateAsyncTask(NewsDao newsDao) {
            mAsyncTaskDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            mAsyncTaskDao.updateNews(news[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<News, Void, Void> {
        private NewsDao mAsyncTaskDao;

        deleteAsyncTask(NewsDao newsDao) {
            mAsyncTaskDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            mAsyncTaskDao.deleteNews(news[0]);
            return null;
        }
    }
}
