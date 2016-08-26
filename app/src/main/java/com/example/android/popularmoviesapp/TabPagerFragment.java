package com.example.android.popularmoviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabPagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabPagerFragment extends Fragment {

    ViewPager viewPager;
    ProgressBar progressBar;
    TabLayout tabLayout;
    String mShowType;
    static String SHOWTYPE_PARAM = "showtype";
    static String SHOWTYPE_VALUE_TV = "tv";
    static String SHOWTYPE_VALUE_MOVIE = "movie";

    public TabPagerFragment() {
        // Required empty public constructor
    }

    public static TabPagerFragment newInstance(String showType) {
        TabPagerFragment fragment = new TabPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOWTYPE_PARAM, showType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_tab_pager, container, false);

        if (getArguments() != null) {
            mShowType = getArguments().getString(SHOWTYPE_PARAM);
        }

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        viewPager.setAdapter(new MovieGridFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getContext()));
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }


}
