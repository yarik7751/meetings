package com.elatesoftware.meetings.ui.adapter.view_pager.page_photo;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PageAdapter extends PagerAdapter {

    List<View> pages = null;

    public PageAdapter(List<View> pages) {
        this.pages = pages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = pages.get(position);
        ((ViewPager) container).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }

    @Override
    public int getCount() {
        return pages == null ? 0 : pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void addView(View v) {
        pages.add(v);
        notifyDataSetChanged();
    }
}
