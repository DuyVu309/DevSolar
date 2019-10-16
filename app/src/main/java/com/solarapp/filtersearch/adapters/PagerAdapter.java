package com.solarapp.filtersearch.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.solarapp.filtersearch.fragment.FragmentNews;
import com.solarapp.filtersearch.utils.Constaint;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = FragmentNews.newInstance(Constaint.TAB_NEWS);
                break;
            case 1:
                frag = FragmentNews.newInstance(Constaint.TAB_SAVED);
                break;
            case 2:
                frag = FragmentNews.newInstance(Constaint.TAB_FAVORITE);
                break;
        }
        if (frag != null) {
            return frag;
        } else {
            return FragmentNews.newInstance(Constaint.TAB_NEWS);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "TIN TỨC";
                break;
            case 1:
                title = "Đã Lưu";
                break;
            case 2:
                title = "Yêu thích";
                break;
        }
        return title;
    }
}
