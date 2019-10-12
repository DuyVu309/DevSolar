package com.solarapp.filtersearch.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.solarapp.filtersearch.fragment.FragmentNews;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag = FragmentNews.newInstance();
                break;
            case 1:
                //frag = new FragmentTwo();
                break;
            case 2:
                //frag = new FragmentThree();
                break;
        }
        if (frag != null) {
            return frag;
        } else {
            return FragmentNews.newInstance();
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
