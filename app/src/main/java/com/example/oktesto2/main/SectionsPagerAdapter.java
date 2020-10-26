package com.example.oktesto2.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.oktesto2.R;
import com.example.oktesto2.ProfileTab1;
import com.example.oktesto2.ProfileTab2;
import com.example.oktesto2.ProfileTab3;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    Bundle pet_general;
    Bundle pet_notes;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Bundle general, Bundle notes) {
        super(fm);
        mContext = context;
        pet_general = general;
        pet_notes = notes;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new ProfileTab1();
                fragment.setArguments(pet_general);
                break;
            case 1:
                fragment = new ProfileTab2();
                fragment.setArguments(pet_notes);

                break;
            case 2:
                fragment = new ProfileTab3();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}