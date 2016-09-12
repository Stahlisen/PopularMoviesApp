package com.fredrikstahl.watchlisterapp;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by fredrikstahl on 30/8/2016.
 */
public class ShowsFragment extends Fragment {

    String showType;
    static String SHOWTYPE_PARAM = "showtype";
    HashMap<String, String> tabApiParamMapping;
    int CURRENT_TABID;
    /*

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabApiParamMapping = new HashMap<String, String>();

        if (getArguments() != null) {
            setShowType(getArguments().getString(SHOWTYPE_PARAM));
            Log.d("mShowType", getShowType());
        }

        if (getShowType() == "tv") {
            tabSetup = getResources().getStringArray(R.array.tab_values_shows_movie);

        } else if (getShowType() == getString(R.string.show_type_value_tv)) {
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

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showtype) {
        this.showType = showtype;
    }

    public static TabPagerFragment newInstance(String showType) {
        TabPagerFragment fragment = new TabPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SHOWTYPE_PARAM, showType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setupTabsAndApiParamMapping(String[] tabNames, String[] apiParams) {
        int i = 0;
        for (String s : tabNames ) {
            if (i == CURRENT_TABID) {
                Log.d("tabId", "tab to be selected is=" + Integer.toString(i));
                tabLayout.addTab(tabLayout.newTab().setText(s), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(s));
            }
            i++;
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeCurrentTab(tab);
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
*/

}
