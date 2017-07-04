package edu.bluejack16_2.familysdaily;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by fidel on 02-Jul-17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<String> titles;
    ArrayList<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public void addFragment(String title, Fragment fragment){
        titles.add(title);
        fragments.add(fragment);
    }

    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
