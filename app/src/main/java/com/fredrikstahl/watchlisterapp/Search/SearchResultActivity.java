package com.fredrikstahl.watchlisterapp.Search;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fredrikstahl.watchlisterapp.API.TMDBSearchAPIFetcher;
import com.fredrikstahl.watchlisterapp.Model.Movie;
import com.fredrikstahl.watchlisterapp.R;
import com.fredrikstahl.watchlisterapp.TabPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResultActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    public static String KEY_EXTRA_QUERY = "query";
    Fragment mCurrentFragment;
    HashMap<String, ArrayList<Movie>> searchResultMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String query = getIntent().getStringExtra(KEY_EXTRA_QUERY);
        Log.d("SearchResultActivity", "query = " + query);

        mCurrentFragment = SearchResultTabPagerFragment.newInstance(query);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_frame, mCurrentFragment)
                .commit();

        /*
        if (getIntent().ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            mTabLayout.addTab(mTabLayout.newTab().setText(query));
        }
        */
    }





    private class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.SearchResultViewHolder> {

        @Override
        public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        //Add comment
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class SearchResultViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public SearchResultViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        public SearchResultRecyclerViewAdapter(String[] myDataSet) {

        }






    }

}
