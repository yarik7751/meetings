package com.elatesoftware.meetings.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.view_pager.ViewPagerAdapter;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.api.pojo.Meeting;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddDateActivity extends BaseActivity implements OnMapReadyCallback {

    private static final String TAG = "AddDaleActivity_logs";
    public static final int REQUEST_PLACE_PICKER = 104;
    public static final int CLOSE = 105;
    public static final String IS_CLOSE = "IS_CLOSE";

    @BindView(R.id.ll_main) LinearLayout llMain;
    
    @BindView(R.id.crs_age) CrystalRangeSeekbar crsAge;
    @BindView(R.id.tv_first_age) TextView tvFirstAge;
    @BindView(R.id.tv_last_age) TextView tvLastAge;

    @BindView(R.id.crs_height) CrystalRangeSeekbar crsHeight;
    @BindView(R.id.tv_first_height) TextView tvFirstHeight;
    @BindView(R.id.tv_last_height) TextView tvLastHeight;

    @BindView(R.id.crs_weight) CrystalRangeSeekbar crsWeight;
    @BindView(R.id.tv_first_weight) TextView tvFirstWeight;
    @BindView(R.id.tv_last_weight) TextView tvLastWeight;
    
    @BindView(R.id.tv_place_title) TextView tvPlaceTitle;
    @BindView(R.id.cv_choose_place) CardView cvChoosePlace;
    @BindView(R.id.map) FrameLayout flMap;
    @BindView(R.id.rl_start_time) RelativeLayout rlStartTime;
    @BindView(R.id.rl_end_time) RelativeLayout rlEndTime;
    @BindView(R.id.tv_start_time) TextView tvStartTime;
    @BindView(R.id.tv_end_time) TextView tvEndTime;
    @BindView(R.id.img_left) ImageView imgLeft;
    @BindView(R.id.img_right) ImageView imgRight;
    @BindView(R.id.vp_hair_color) ViewPager vpHairColor;
    @BindView(R.id.cet_present) CustomEditText cetPresent;

    @BindView(R.id.erl_features) ExpandableRelativeLayout llFeatures;
    @BindView(R.id.erl_time) ExpandableRelativeLayout llTime;
    @BindView(R.id.erl_location) ExpandableRelativeLayout llLocation;
    @BindView(R.id.erl_present) ExpandableRelativeLayout llPresent;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Place place;
    private Date dateStart = null, dateEnd = null;
    private ViewPagerAdapter vpHairColorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dale);

        initMap();
        setHairColors();
        crsAge.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvFirstAge.setText(String.valueOf(minValue));
                tvLastAge.setText(String.valueOf(maxValue));
            }
        });
        crsHeight.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvFirstHeight.setText(String.valueOf(minValue));
                tvLastHeight.setText(String.valueOf(maxValue));
            }
        });

        crsWeight.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvFirstWeight.setText(String.valueOf(minValue));
                tvLastWeight.setText(String.valueOf(maxValue));
            }
        });
        vpHairColor.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                checkArrows();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case REQUEST_PLACE_PICKER:
                    flMap.setVisibility(View.VISIBLE);
                    place = PlacePicker.getPlace(data, this);
                    CharSequence name = place.getName();
                    CharSequence address = place.getAddress();
                    String attributions = PlacePicker.getAttributions(data);
                    if (attributions == null) {
                        attributions = "";
                    }
                    tvPlaceTitle.setText(name + "\n" + address);

                    map.clear();
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(place.getLatLng())
                            .zoom(16.0f)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    map.addMarker(new MarkerOptions()
                            .position(place.getLatLng()));
                    break;

                case CLOSE:
                    if(data.getBooleanExtra(IS_CLOSE, false)) {
                        finish();
                    }
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.img_right)
    public void clickImgRight() {
        vpHairColor.setCurrentItem(vpHairColor.getCurrentItem() + 1);
        checkArrows();
    }

    @OnClick(R.id.img_left)
    public void clickImgLeft() {
        vpHairColor.setCurrentItem(vpHairColor.getCurrentItem() - 1);
        checkArrows();
    }

    private void checkArrows() {
        if(vpHairColor.getCurrentItem() == vpHairColorAdapter.getCount() - 1) {
            imgRight.setVisibility(View.INVISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
        } else if(vpHairColor.getCurrentItem() == 0) {
            imgRight.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.INVISIBLE);
        } else {
            imgRight.setVisibility(View.VISIBLE);
            imgLeft.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.rl_back)
    public void clickImgBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_preview)
    public void clickBtnPreview() {
        if(checkInfo()) {
            saveInfoMeeting();
            Intent intent = new Intent(this, ShowDateActivity.class);
            intent.putExtra(ShowDateActivity.TITLE, getString(R.string.preview));
            intent.putExtra(ShowDateActivity.IS_SHOW_MAN_INFO, false);
            startActivityForResult(intent, CLOSE);
        }
    }

    @OnClick(R.id.ll_place)
    public void clickChoosePlace() {
        сhoicePlace();
    }

    @OnClick(R.id.rl_start_time)
    public void clickRlStartTime() {
        DateUtils.showSlideDateTimeDialog(getSupportFragmentManager(), new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet(Date date) {
                if(dateEnd != null && date.getTime() >= dateEnd.getTime()) {
                    showMessage(R.string.wrong_date);
                } else {
                    dateStart = new Date(date.getTime());
                    tvStartTime.setText(DateUtils.getDateByStr(date, DateUtils.DATE_FORMAT_OUTPUT));
                }
            }
        });
    }

    @OnClick(R.id.rl_end_time)
    public void clickRlEndTime() {
        DateUtils.showSlideDateTimeDialog(getSupportFragmentManager(), new SlideDateTimeListener() {
            @Override
            public void onDateTimeSet(Date date) {
                if(dateStart != null && date.getTime() <= dateStart.getTime()) {
                    showMessage(R.string.wrong_date);
                } else {
                    dateEnd = new Date(date.getTime());
                    tvEndTime.setText(DateUtils.getDateByStr(date, DateUtils.DATE_FORMAT_OUTPUT));
                }
            }
        });
    }

    @OnClick(R.id.ll_features_title)
    public void clickFeaturesTitle() {
        clickExpRelativeLayout(llFeatures);
    }

    @OnClick(R.id.ll_time_title)
    public void clickTimeTitle() {
        clickExpRelativeLayout(llTime);
    }

    @OnClick(R.id.ll_location_title)
    public void clickLocationTitle() {
        clickExpRelativeLayout(llLocation);
    }

    @OnClick(R.id.ll_present_title)
    public void clickPresentTitle() {
        clickExpRelativeLayout(llPresent);
    }

    private void clickExpRelativeLayout(ExpandableRelativeLayout layout) {
        if(layout.isExpanded()) {
            layout.collapse();
        } else {
            layout.expand();
        }
    }

    public void сhoicePlace() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            Log.d(TAG, e + "");
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d(TAG, e + "");
        }
    }

    private void initMap() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.map, mapFragment);
        fragmentTransaction.commit();
        if(mapFragment != null) {
            flMap.setVisibility(View.INVISIBLE);
            mapFragment.getMapAsync(this);
        }
    }

    private void setHairColors() {
        String[] colors = getResources().getStringArray(R.array.hair_colors);
        List<View> pages = new ArrayList<>();
        for(int i = 0; i < colors.length; i++) {
            View vItem = LayoutInflater.from(this).inflate(R.layout.item_view_pager, null);
            ((TextView) vItem.findViewById(R.id.tv_text)).setText(colors[i]);
            pages.add(vItem);
        }
        vpHairColorAdapter = new ViewPagerAdapter(pages);
        vpHairColor.setAdapter(vpHairColorAdapter);
        checkArrows();
    }

    private void saveInfoMeeting() {
        Meeting meeting = new Meeting();
        meeting.setAmount(Integer.parseInt(cetPresent.getEditText().getText().toString()));
        meeting.setHairColor(vpHairColor.getCurrentItem());
        meeting.setLatitude(place.getLatLng().latitude);
        meeting.setLongitude(place.getLatLng().longitude);
        meeting.setPlace(place.getName() + "\n" + place.getAddress());

        meeting.setPrefAgeStart(Integer.parseInt(tvFirstAge.getText().toString()));
        meeting.setPrefAgeEnd(Integer.parseInt(tvLastAge.getText().toString()));

        meeting.setPrefHeightStart(Integer.parseInt(tvFirstHeight.getText().toString()));
        meeting.setPrefHeightEnd(Integer.parseInt(tvLastHeight.getText().toString()));

        meeting.setPrefWeightStart(Integer.parseInt(tvFirstWeight.getText().toString()));
        meeting.setPrefWeightEnd(Integer.parseInt(tvLastWeight.getText().toString()));

        Log.d(TAG, "dateStart: " + dateStart.getTime());
        Log.d(TAG, "dateEnd  : " + dateEnd.getTime());
        meeting.setStartTime(dateStart.getTime() / 1000);
        meeting.setEndTime(dateEnd.getTime() / 1000);

        Log.d(TAG, "meeting.getStartTime(): " + meeting.getStartTime());
        Log.d(TAG, "meeting.getEndTime()  : " + meeting.getEndTime());
        Log.d(TAG, "valid  : " + (meeting.getEndTime() > meeting.getStartTime()));

        meeting.setWithPhoto(false);
        Meeting.setInstance(meeting);
    }

    private boolean checkInfo() {
        String msgError = "";
        if(dateStart == null && dateEnd == null) {
            msgError += getString(R.string.entry_time) + "\n";
        }
        if(place == null) {
            msgError += getString(R.string.entry_place) + "\n";
        }
        if(place == null) {
            msgError += getString(R.string.entry_present);
        }
        if(TextUtils.isEmpty(msgError)) {
            return true;
        } else {
            new AlertDialog.Builder(this)
                .setTitle(R.string.error_add_date)
                .setMessage(msgError)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
