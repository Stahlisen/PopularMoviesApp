package com.fredrikstahl.watchlisterapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fredrikstahl.watchlisterapp.Model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDetailFragment extends Fragment {
    Movie detailMovie;
    String movieTrailerVideoId;



    public ShowDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false);
    }

}
