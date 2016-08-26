package com.example.android.popularmoviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by fredrikstahl on 8/8/2016.
 */
public class MovieGridFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Trending", "Best Rated", "New"};
    private Context context;

    public MovieGridFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        MovieGridFragment mgf = new MovieGridFragment();
        Bundle b = new Bundle();
        Log.d(getClass().getSimpleName(), Integer.toString(position));
        switch(position) {
            case 0:
                b.putString("showType", "movie");
                mgf.setArguments(b);
                break;
            case 1:
                b.putString("showType", "tv");
                mgf.setArguments(b);
                break;
            case 2:
                b.putString("showType", "movie");
                mgf.setArguments(b);
                break;
        }


        return mgf;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
