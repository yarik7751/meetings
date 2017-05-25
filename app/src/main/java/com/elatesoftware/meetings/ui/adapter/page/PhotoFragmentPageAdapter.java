package com.elatesoftware.meetings.ui.adapter.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elatesoftware.meetings.ui.fragment.PhotoFragment;
import com.elatesoftware.meetings.util.api.pojo.Photo;

import java.util.List;

public class PhotoFragmentPageAdapter extends FragmentStatePagerAdapter {

    List<Photo> photos;

    public PhotoFragmentPageAdapter(FragmentManager fm, List<Photo> photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.getInstance(photos.get(position).getId());
    }

    @Override
    public int getCount() {
        return photos == null ? 0 : photos.size();
    }


}
