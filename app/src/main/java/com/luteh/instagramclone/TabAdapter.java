package com.luteh.instagramclone;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Luthfan Maftuh on 01/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                return new UsersFragment();
            case 2:
                return new SharePictureFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share Picture";
            default:
                return null;
        }
    }
}
