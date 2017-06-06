package com.elatesoftware.meetings.ui.fragment.woman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.SearchActivity;
import com.elatesoftware.meetings.ui.fragment.base.BaseFragment;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.Utils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchWomanFragment extends BaseFragment implements OnMapReadyCallback {

    @BindView(R.id.tv_map) TextView tvMap;
    @BindView(R.id.tv_list) TextView tvList;
    @BindView(R.id.fl_content) FrameLayout flContent;
    @BindView(R.id.fab_search) FloatingActionButton fabSearch;
    private RecyclerView rvDales;

    private GoogleMap map;
    private SupportMapFragment mapFragment;

    private static SearchWomanFragment searchWomanFragment;
    public static SearchWomanFragment getInstance() {
        if(searchWomanFragment == null) {
            searchWomanFragment = new SearchWomanFragment();
        }
        return searchWomanFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_woman, null);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickTvList();
    }

    @OnClick(R.id.fab_search)
    public void clickFabSearch() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }

    @OnClick(R.id.tv_map)
    public void clickTvMap() {
        Utils.setSelect(getContext(), tvMap, true);
        Utils.setSelect(getContext(), tvList, false);

        loadMap();
    }

    @OnClick(R.id.tv_list)
    public void clickTvList() {
        Utils.setSelect(getContext(), tvMap, false);
        Utils.setSelect(getContext(), tvList, true);

        loadList();
    }

    private void loadMap() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.fl_content, mapFragment).commit();
        if(mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void loadList() {
        flContent.removeAllViews();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );

        /*RecyclerView rvDales = new RecyclerView(getContext());
        rvDales.setLayoutManager(new LinearLayoutManager(getContext()));
        DalesRecyclerViewAdapter adapter = new DalesRecyclerViewAdapter(getContext());
        rvDales.setAdapter(adapter);
        rvDales.setPadding(0, AndroidUtils.dp(36), 0, 0);
        rvDales.setItemViewCacheSize(adapter.getItemCount());
        flContent.addView(rvDales, params);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
