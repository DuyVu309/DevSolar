package com.solarapp.filtersearch.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static volatile NewsDatabase sInstance;

    public static NewsDatabase getInMemoryDb(Context context) {
        if (sInstance == null) {
            synchronized (NewsDatabase.class) {
                if (sInstance == null) {
                    sInstance =
                            Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news_database")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public abstract NewsDao newsDao();
}
