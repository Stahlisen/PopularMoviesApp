package com.fredrikstahl.watchlisterapp.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.fredrikstahl.watchlisterapp.API.TMDBSearchAPIFetcher;
import com.fredrikstahl.watchlisterapp.Model.Movie;
import com.fredrikstahl.watchlisterapp.MovieDataParser;
import com.fredrikstahl.watchlisterapp.MovieDetailActivity;
import com.fredrikstahl.watchlisterapp.PosterGridAdapter;
import com.fredrikstahl.watchlisterapp.R;
import com.fredrikstahl.watchlisterapp.TabPagerFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fredrikstahl on 14/04/17.
 */

public class SearchResultTabPagerFragment extends Fragment {
    ProgressBar progressBar;
    TabLayout tabLayout;
    GridView gridView;
    PosterGridAdapter posterGridAdapter;
    HashMap<String, ArrayList<Movie>> searchResultData;
    String[] tabNames = {"Movies", "TV Shows"};
    String query;
    int CURRENT_TABID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultData = (HashMap<String, ArrayList<Movie>>) getArguments().getSerializable("SearchResultData");

        query = getArguments().getString("query");
        Log.d("SearchResultFragment", "arguments = " + getArguments().toString());
        Log.d("SearchResultFragment", "query = " + query);

        if (savedInstanceState != null) {
            //TODO: set tab id
        } else {
            //TODO: set default tab id
        }

        TMDBSearchAPIFetcher fetcher = new TMDBSearchAPIFetcher(this);
        fetcher.execute(query);
    }

    public static SearchResultTabPagerFragment newInstance(String searchQuery) {
        SearchResultTabPagerFragment fragment = new SearchResultTabPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("query", searchQuery);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setupTabs() {
        for (String tabName : tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CURRENT_TABID = tab.getPosition();
                setupViewForTab(tab, getDataForTab(tab));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SearchResultFragment", "entering onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_tab_pager, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        progressBar =  (ProgressBar) rootView.findViewById(R.id.progressBar);
        gridView = (GridView) rootView.findViewById(R.id.movieGridView);


        setupTabs();

        return rootView;
    }

    private ArrayList<Movie> getDataForTab(TabLayout.Tab currentTab) {
        ArrayList<Movie> currentTabData = null;
        if (currentTab.getText().equals("Movies")) {
            currentTabData = searchResultData.get(MovieDataParser.MOVIE_LABEL);
        } else if (currentTab.getText().equals("TV Shows")){
            currentTabData = searchResultData.get(MovieDataParser.TV_LABEL);
        }
        return currentTabData;
    }

    public void onSearchAPIFetcherCompleted(HashMap<String, ArrayList<Movie>> result) {
        this.searchResultData = result;

        TabLayout.Tab currentTab = tabLayout.getTabAt(CURRENT_TABID);

        setupViewForTab(currentTab, getDataForTab(currentTab));
        progressBar.setVisibility(View.GONE);
    }

    public void setupViewForTab(TabLayout.Tab tab, ArrayList<Movie> tabData) {
        posterGridAdapter = null;
        posterGridAdapter = new PosterGridAdapter(getContext(), tabData);
        gridView.invalidateViews();
        gridView.setAdapter(posterGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = posterGridAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra("Movie", (Serializable) selectedMovie);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);

            }
        });
    }
}
