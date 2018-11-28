package com.circularfashion.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.circularfashion.R;
import com.circularfashion.fragments.ListFragment;
import com.circularfashion.fragments.MapFragment;

public class CustomAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CustomAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MapFragment();
        } else if (position == 1) {
            return new ListFragment();
        }
        return null;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.map);
            case 1:
                return mContext.getString(R.string.list);
            default:
                return null;
        }
    }



}
