package com.solarapp.filtersearch.data;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solarapp.filtersearch.R;

@Entity
public class News {
    @PrimaryKey(autoGenerate = true)
    public long employId;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "favorite")
    public boolean favorite;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "image")
    public String image;
    @ColumnInfo(name = "path_file")
    public String pathFile;


    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions().centerCrop())
                .placeholder(R.drawable.ic_loading)
                .into(view);
    }
}
