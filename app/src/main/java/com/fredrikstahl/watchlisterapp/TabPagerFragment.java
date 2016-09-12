package com.fredrikstahl.watchlisterapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.fredrikstahl.watchlisterapp.Model.Movie;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class TabPagerFragment extends ShowsFragment {

    ProgressBar progressBar;
    TabLayout tabLayout;
    GridView gridView;
    PosterGridAdapter posterGridAdapter;
    FetchMovieData fmd;
    String mShowType;
    static String SHOWTYPE_PARAM = "showtype";
    static String SHOWTYPE_VALUE_TV = "tv";
    static String SHOWTYPE_VALUE_MOVIE = "movie";
    int CURRENT_TABID;
    String[] tabSetup;
    HashMap<String, String> tabNameApiParamMapping;

    HashMap<String,ArrayList<Movie>> showsdata;

    public TabPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mShowType = getArguments().getString(SHOWTYPE_PARAM);
            Log.d("mShowType", mShowType);
        }

        if (mShowType == SHOWTYPE_VALUE_MOVIE) {
            tabSetup = getResources().getStringArray(R.array.tab_values_shows_movie);

        } else if (mShowType == SHOWTYPE_VALUE_TV) {
            tabSetup = getResources().getStringArray(R.array.tab_values_shows_tv);
        } else {
            tabSetup = new String[]{};
        }

        if(savedInstanceState != null) {
            CURRENT_TABID = savedInstanceState.getInt("CURRENT_TABID");
            Log.d("tabId", Integer.toString(CURRENT_TABID));

            showsdata = (HashMap<String, ArrayList<Movie>>) savedInstanceState.getSerializable("showsdata");
        } else {
            CURRENT_TABID = 0;
            showsdata = new HashMap<String, ArrayList<Movie>>();
        }
    }

    public static TabPagerFragment newInstance(String showType) {
        TabPagerFragment fragment = new TabPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOWTYPE_PARAM, showType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_tab_pager, container, false);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);
        tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        gridView = (GridView) rootView.findViewById(R.id.movieGridView);

        setupTabs();
        if (!showsdata.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            dataFetchingDone();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
            fmd = new FetchMovieData();
            fmd.execute();
        }

        return rootView;
    }

    public void setupTabs() {
        int i = 0;
        tabNameApiParamMapping = new HashMap<String, String>();
        for (String s : tabSetup ) {
            if (i == CURRENT_TABID) {
                tabLayout.addTab(tabLayout.newTab().setText(s), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(s));
            }

            switch (s) {
                case "Trending":
                    tabNameApiParamMapping.put(s, getString(R.string.api_param_trending));
                    break;
                case "Best rated":
                    tabNameApiParamMapping.put(s, getString(R.string.api_param_best_rated));
                    break;
                case "On the air":
                    tabNameApiParamMapping.put(s, getString(R.string.api_param_on_the_air));
                    break;
                case "Coming soon":
                    tabNameApiParamMapping.put(s, getString(R.string.api_param_coming_soon));
                break;
            }
            i++;
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CURRENT_TABID = tab.getPosition();
                setupViewForTab(tab);

                Log.d("tabId", Integer.toString(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("tabAction","onTabUnselected" + tab.getText().toString());
                //Log.d("tabId", Integer.toString(CURRENT_TABID));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("tabAction","onTabReselected" + tab.getText().toString());
            }
        });


    }

    public void setupViewForTab(TabLayout.Tab tab) {
        posterGridAdapter = null;
        posterGridAdapter = new PosterGridAdapter(getContext(), showsdata.get(tabNameApiParamMapping.get(tab.getText())));
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("CURRENT_TABID", CURRENT_TABID);
        outState.putSerializable("showsdata", showsdata);
    }

    public void dataFetchingDone() {
        TabLayout.Tab tab = tabLayout.getTabAt(CURRENT_TABID);
        setupViewForTab(tab);

        posterGridAdapter = new PosterGridAdapter(getContext(), showsdata.get(tabNameApiParamMapping.get((tabSetup[CURRENT_TABID]))));
        gridView.setAdapter(posterGridAdapter);
    }

    private class FetchMovieData extends AsyncTask<String, String, HashMap<String, ArrayList<Movie>>> {
        String LOG_TAG = this.getClass().getSimpleName();
        @Override
        protected HashMap<String, ArrayList<Movie>> doInBackground(String... params) {
            Log.d("MovieGridFragment", "Starting callout");
            Thread.currentThread();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            HashMap<String, ArrayList<Movie>> fullMovieMap = new HashMap<String, ArrayList<Movie>>();
            String [] api_params;


            if (mShowType == SHOWTYPE_VALUE_TV) {
                api_params = getResources().getStringArray(R.array.api_params_tv);

            } else if (mShowType == SHOWTYPE_VALUE_MOVIE) {
                api_params = getResources().getStringArray(R.array.api_params_movie);
            } else {
                api_params = new String[]{};
            }

            for (String param : api_params) {
                Log.d("mShowType", param);
                fullMovieMap.put(param, null);
            }

            for (String key : fullMovieMap.keySet()) {
                Log.d("forKey", key);

                String api_param = "";

                ArrayList<Movie> movieList = null;
                String jsonStr = "";
                try {
                    String BASE_URL = "http://api.themoviedb.org/3/" + mShowType + "/" + key;
                    String API_KEY = "2045aa6a6dcedbc160541e810c9dec7f";

                    Uri endPointUri = Uri.parse(BASE_URL).buildUpon()
                            .appendQueryParameter("api_key", API_KEY)
                            .appendQueryParameter("year", "2016")
                            .build();
                    Log.d(LOG_TAG, "endpointuri = " + endPointUri.toString());
                    URL url = new URL(endPointUri.toString());

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    //Read the input stream into a string
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer stringBuffer = new StringBuffer();

                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line + "\n");
                    }

                    if (stringBuffer.length() == 0) {
                        return null;
                    }

                    jsonStr = stringBuffer.toString();

                    Log.d(LOG_TAG, jsonStr);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            Log.e("PlaceholderFragment", "Error ", e);
                        }
                    }

                    try {
                        if (mShowType == SHOWTYPE_VALUE_MOVIE) {
                            Log.d("dfp", "showtype is movie");

                            fullMovieMap.put(key, MovieDataParser.getMovieData(jsonStr));

                        } else if (mShowType == SHOWTYPE_VALUE_TV) {
                            Log.d("dfp", "showtype is tv");
                            fullMovieMap.put(key, MovieDataParser.getTvShowData(jsonStr));
                        } else {
                            Log.d("dfp", "showtype is nothing");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d("forKey", Integer.toString(fullMovieMap.size()));
            return fullMovieMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, ArrayList<Movie>> shows) {
            if (showsdata != null) {
                if (showsdata.values().size() > 0) {
                    showsdata.clear();
                }
            }
            Log.d("dfp", "ArrayLists:" + shows.values().size());
            showsdata = shows;
            Log.d("forKey", Integer.toString(showsdata.size()));

            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            dataFetchingDone();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mShowType == "movie") {
            ((StartActivity)getActivity()).getSupportActionBar().setTitle("Movies");
        } else {
            ((StartActivity)getActivity()).getSupportActionBar().setTitle("TV Series");

        }
    }
}
