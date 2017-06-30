package com.elatesoftware.meetings.ui.activity.woman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.api.Api;
import com.elatesoftware.meetings.service.SearchDatesService;
import com.elatesoftware.meetings.ui.activity.all.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.recycler_view.dates.BaseDatesRecyclerViewAdapter;
import com.elatesoftware.meetings.ui.adapter.recycler_view.dates.SearchDatesAdapter;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.ImageHelper;
import com.elatesoftware.meetings.util.LocationUtils;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.util.Utils;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.elatesoftware.meetings.api.pojo.Result;
import com.elatesoftware.meetings.api.pojo.SearchDatesAnswer;
import com.elatesoftware.meetings.model.params.SearchDatesFilter;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.Date;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchManActivity extends BaseActivity implements OnMapReadyCallback {

    public static final String TAG = "SearchManActivity_log";

    @BindView(R.id.ts_switch) ToggleSwitch tsSwitch;
    @BindView(R.id.ll_tool) LinearLayout llTool;
    @BindView(R.id.fl_content) FrameLayout flContent;
    @BindView(R.id.fl_list_content) FrameLayout flListContent;
    @BindView(R.id.cet_present) CustomEditText cetPresent;
    @BindView(R.id.rl_start_time) RelativeLayout rlStartTime;
    @BindView(R.id.tv_start_time) TextView tvStartTime;
    @BindView(R.id.ll_filters) LinearLayout llFilters;
    @BindView(R.id.rl_main) CoordinatorLayout rlMain;
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.rl_back) RelativeLayout rlBack;
    private BottomSheetBehavior mBottomSheetBehavior;

    private RecyclerView rvDales;
    private SearchDateReceiver searchDateReceiver;
    private SearchDatesAnswer searchDatesAnswer;

    private GoogleMap map;
    private SupportMapFragment mapFragment;
    private BaseDatesRecyclerViewAdapter adapter;
    private boolean isPresentIterator = true;
    private boolean isShowFilter = false;
    private Date dateStart;
    private int datesView = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_man);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(0);

        loadMap();
        tsSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                datesView = position;
                if(datesView == 0) {
                    loadMap();
                } else if(datesView == 1) {
                    loadList();
                }
            }
        });
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                isPresentIterator = false;
                StringUtils.setMaskAmount(cetPresent.getEditText(), isOpen);
                cetPresent.getEditText().setSelection(cetPresent.getEditText().getText().length());
            }
        });

        cetPresent.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int maxLenght = 6;
                if(s.toString().length() > maxLenght && isPresentIterator) {
                    cetPresent.getEditText().setText(s.toString().substring(0, maxLenght));
                    cetPresent.getEditText().setSelection(cetPresent.getEditText().getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isPresentIterator = true;
            }
        });
        requestSearchDates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchDateReceiver = new SearchDateReceiver();
        registerReceiver(searchDateReceiver, Utils.getIntentFilter(SearchDatesService.ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(searchDateReceiver);
    }

    private void requestSearchDates() {
        setProgressDialogMessage(getString(R.string.dates_loading) + " ...");
        showProgressDialog();
        String amountStartStr = cetPresent.getEditText().getText().toString();
        amountStartStr =  amountStartStr.replace("$", "");
        double amountStart = TextUtils.isEmpty(amountStartStr) ? 0 : Double.parseDouble(amountStartStr);
        long startTime = dateStart == null ? 0 : dateStart.getTime() / 1000;
        SearchDatesFilter searchDatesFilter = new SearchDatesFilter(amountStart, startTime, 1);
        startService(SearchDatesService.getIntent(this, searchDatesFilter));
    }

    private void loadMap() {
        imgBack.setVisibility(View.GONE);
        rlBack.setOnClickListener(null);
        flContent.setVisibility(View.VISIBLE);
        flListContent.setVisibility(View.GONE);
        if(map == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.fl_content, mapFragment).commit();
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
    }

    private void loadList() {
        imgBack.setVisibility(View.VISIBLE);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        flContent.setVisibility(View.GONE);
        flListContent.setVisibility(View.VISIBLE);
        if(searchDatesAnswer != null) {
            setDatesInList();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if(searchDatesAnswer != null) {
            setDatesInMap();
        }
    }

    /*@OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }*/

    @OnClick(R.id.fab_filters)
    public void clickFabFilters() {
        if(!isShowFilter) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        else {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    requestSearchDates();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
        isShowFilter = !isShowFilter;
    }

    @OnClick(R.id.rl_start_time)
    public void clickRlStartTime() {
        AndroidUtils.hideKeyboard(this);
        DateUtils.showSlideDateTimeDialog(getSupportFragmentManager(), new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet(Date date) {
                dateStart = new Date(date.getTime());
                tvStartTime.setText(DateUtils.getDateByStr(date, DateUtils.DATE_FORMAT_OUTPUT));
            }
        });
    }

    private void setMyLocation() {
        LatLng myPosition = LocationUtils.getLastLocation(this);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(myPosition)
                .zoom(14.0f)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        View v = LayoutInflater.from(SearchManActivity.this).inflate(R.layout.incl_marker, null);
        CircleImageView imgMarker = (CircleImageView) v.findViewById(R.id.img_marker);
        imgMarker.setImageResource(R.drawable.marker_myself);
        imgMarker.setBorderWidth(0);
        map.addMarker(new MarkerOptions()
                .position(myPosition)
                .icon(BitmapDescriptorFactory.fromBitmap(ImageHelper.loadBitmapFromView(v)))
        );
    }

    private void setDatesInMap() {
        if(map != null) {
            map.clear();
            setMyLocation();
            for (int i = 0; i < searchDatesAnswer.getResult().size(); i++) {
                final Result result = searchDatesAnswer.getResult().get(i);
                final LatLng position = new LatLng(result.getDate().getLatitude(), result.getDate().getLongitude());
                final View v = LayoutInflater.from(SearchManActivity.this).inflate(R.layout.incl_marker, null);
                CircleImageView imgMarker = (CircleImageView) v.findViewById(R.id.img_marker);
                if(result.getCreatorId() != null && result.getCreatorPhotoId() != null) {
                    String url = StringUtils.getPhotoUrl(result.getCreatorId().intValue(), result.getCreatorPhotoId().intValue());
                    Picasso.with(SearchManActivity.this)
                            .load(url)
                            .resize(getResources().getDimensionPixelSize(R.dimen.marker_size), getResources().getDimensionPixelSize(R.dimen.marker_size))
                            .centerCrop()
                            .into(imgMarker, new Callback() {
                                @Override
                                public void onSuccess() {
                                    setMarker(position, v, result);
                                }

                                @Override
                                public void onError() {}
                            });
                } else {
                    imgMarker.setBorderWidth(0);
                    setMarker(position, v, result);
                }
            }
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Result result = ((Result) marker.getTag());
                    if(result != null) {
                        Meeting.setInstance(result.getDate());
                        startActivity(ShowSearchDateActivity.getIntent(SearchManActivity.this, result.getCreatorId().intValue()));
                    }
                    return false;
                }
            });
        }
    }

    private void setDatesInList() {
        if(adapter == null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            );
            params.rightMargin = AndroidUtils.dp(16);
            params.leftMargin = AndroidUtils.dp(16);

            rvDales = new RecyclerView(this);
            rvDales.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SearchDatesAdapter(this, searchDatesAnswer.getResult());
            rvDales.setAdapter(adapter);
            rvDales.setPadding(0, llTool.getHeight() + AndroidUtils.dp(16), 0, 0);
            flListContent.addView(rvDales, params);
        } else {
            adapter.update(searchDatesAnswer.getResult());
        }
    }

    private void setMarker(LatLng position, View v, Result result) {
        BitmapDescriptor photoDescriptor = BitmapDescriptorFactory.fromBitmap(ImageHelper.loadBitmapFromView(v));
        Marker marker = map.addMarker(new MarkerOptions()
                .position(position)
                .icon(photoDescriptor)
                .zIndex(17)
        );
        marker.setTag(result);
    }

    public class SearchDateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            SearchDatesAnswer response = intent.getParcelableExtra(Api.RESPONSE);
            hideProgressDialog();
            if(response != null) {
                Log.d(TAG, "registration 200");
                if(response.getSuccess()) {
                    searchDatesAnswer = response;
                    setDatesInMap();
                    setDatesInList();
                } else {
                    Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "registration FALSE " + response);
                }
            } else {
                Toast.makeText(context, R.string.request_error, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "registration error " + response);
            }
        }
    }
}
