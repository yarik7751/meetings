package com.elatesoftware.meetings.ui.adapter.view_pager.page_photo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elatesoftware.meetings.ui.fragment.all.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

public class PhotoFragmentPageAdapter extends FragmentStatePagerAdapter {

    List<Integer> photosInteger;
    List<PhotoFragment> fragments;
    private long userId;

    public PhotoFragmentPageAdapter(FragmentManager fm, List<Integer> photos, long userId) {
        super(fm);
        this.photosInteger = photos;
        this.userId = userId;
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        PhotoFragment fragment = PhotoFragment.getInstance(userId, photosInteger.get(position));
        fragments.add(fragment);
        return fragment;
    }

    public List<PhotoFragment> getFragments() {
        return fragments;
    }

    public List<Integer> getPhotos() {
        return photosInteger;
    }

    @Override
    public int getCount() {
        return photosInteger == null ? 0 : photosInteger.size();
    }

}
