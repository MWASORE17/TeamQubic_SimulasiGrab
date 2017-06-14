package com.qubic.grabsimulation.view.fragment.location;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.adapter.VPLocationFragmentAdapter;

/**
 * Created by ferr on 17/04/17.
 */

public class ViewpagerLocationFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_location, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.location_viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.viewpager_tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabs();

        return view;
    }

    private void createViewPager(ViewPager viewPager) {
        VPLocationFragmentAdapter vpAdapter = new VPLocationFragmentAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
    }

    private void createTabs() {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_location_tab, null);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person_outline_grey_500_24dp, 0, 0);
        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_location_tab, null);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_grey_600_24dp, 0, 0);

        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }
}
