package com.fredrikstahl.watchlisterapp.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fredrikstahl.watchlisterapp.R;

/**
 * Created by fredrikstahl on 13/9/2016.
 */
public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder>{
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder (TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public SearchResultRecyclerViewAdapter (String[] data) {
        mDataset = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
