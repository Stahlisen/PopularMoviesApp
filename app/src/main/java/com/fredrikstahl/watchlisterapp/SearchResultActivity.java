package com.fredrikstahl.watchlisterapp;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private RecyclerView mSearchResultRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mSearchResultRecylerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Movies(14)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tv shows(0)"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Actors(1)"));

        mSearchResultRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        
        if (getIntent().ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            mTabLayout.addTab(mTabLayout.newTab().setText(query));
        }
    }

    private class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.SearchResultViewHolder> {

        @Override
        public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(SearchResultViewHolder holder, int position) {

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
