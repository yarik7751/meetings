package com.elatesoftware.meetings.ui.activity.man;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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

import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.ui.adapter.view_pager.ViewPagerAdapter;
import com.elatesoftware.meetings.ui.view.CustomEditText;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.elatesoftware.meetings.util.DateUtils;
import com.elatesoftware.meetings.util.StringUtils;
import com.elatesoftware.meetings.api.pojo.Meeting;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;

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
    @BindView(R.id.ll_place) LinearLayout llPlace;
    @BindView(R.id.rsb_age) RangeSeekBar rsbAge;
    @BindView(R.id.rsb_height) RangeSeekBar rsbHeight;
    @BindView(R.id.rsb_weight) RangeSeekBar rsbWeight;
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
    @BindView(R.id.cet_etc) CustomEditText cetEtc;
    @BindView(R.id.erl_features) ExpandableRelativeLayout llFeatures;
    @BindView(R.id.erl_time) ExpandableRelativeLayout llTime;
    @BindView(R.id.erl_location) ExpandableRelativeLayout llLocation;
    @BindView(R.id.erl_present) ExpandableRelativeLayout llPresent;
    @BindView(R.id.ll_features_title) LinearLayout llFeaturesTitle;
    @BindView(R.id.ll_time_title) LinearLayout llTimeTitle;
    @BindView(R.id.ll_location_title) LinearLayout llLocationTitle;
    @BindView(R.id.ll_present_title) LinearLayout llPresentTitle;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Place place;
    private Date dateStart = null, dateEnd = null;
    private ViewPagerAdapter vpHairColorAdapter;
    private boolean isPresentIterator = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dale);

        llFeatures.collapse();
        llTime.collapse();
        llLocation.collapse();
        llPresent.collapse();

        setKeyboardListener();
        initMap();
        setHairColors();

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
    }

    private void setKeyboardListener() {
        cetPresent.setKeyboardListener(this);
        cetEtc.setKeyboardListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case REQUEST_PLACE_PICKER:
                    flMap.setVisibility(View.VISIBLE);
                    llPlace.setBackgroundResource(R.drawable.map_bg);
                    place = PlacePicker.getPlace(data, this);
                    CharSequence name = place.getName();
                    CharSequence address = place.getAddress();
                    String attributions = PlacePicker.getAttributions(data);
                    if (attributions == null) {
                        attributions = "";
                    }
                    String placeStr = name + "\n" + address;
                    placeStr = placeStr.replace("\"", " ");
                    tvPlaceTitle.setText(placeStr);

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
            startActivityForResult(PreviewActivity.getIntent(this), CLOSE);
        }
    }

    @OnClick(R.id.ll_place)
    public void clickChoosePlace() {
        сhoicePlace();
    }

    @OnClick(R.id.rl_start_time)
    public void clickRlStartTime() {
        AndroidUtils.hideKeyboard(this);
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
        AndroidUtils.hideKeyboard(this);
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
        clickExpRelativeLayout(llFeaturesTitle, llFeatures);

    }

    @OnClick(R.id.ll_time_title)
    public void clickTimeTitle() {
        clickExpRelativeLayout(llTimeTitle, llTime);
    }

    @OnClick(R.id.ll_location_title)
    public void clickLocationTitle() {
        clickExpRelativeLayout(llLocationTitle, llLocation);
    }

    @OnClick(R.id.ll_present_title)
    public void clickPresentTitle() {
        clickExpRelativeLayout(llPresentTitle, llPresent);
    }

    private void clickExpRelativeLayout(LinearLayout llTitle, ExpandableRelativeLayout layout) {
        ImageView ivInd = (ImageView) llTitle.getChildAt(1);
        if(layout.isExpanded()) {
            layout.toggle();
            ivInd.setImageResource(R.drawable.ic_keyboard_arrow_left_black_24dp);
        } else {
            layout.expand();
            ivInd.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
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
        String amount = cetPresent.getEditText().getText().toString();
        amount = amount.replace("$", "");
        meeting.setAmount(Integer.parseInt(amount));
        meeting.setHairColor(vpHairColor.getCurrentItem());
        meeting.setLatitude(place.getLatLng().latitude);
        meeting.setLongitude(place.getLatLng().longitude);
        meeting.setPlace(place.getName() + "\n" + place.getAddress());

        meeting.setPrefAgeStart(rsbAge.getSelectedMinValue().intValue());
        meeting.setPrefAgeEnd(rsbAge.getSelectedMaxValue().intValue());

        meeting.setPrefHeightStart(rsbHeight.getSelectedMinValue().intValue());
        meeting.setPrefHeightEnd(rsbHeight.getSelectedMaxValue().intValue());

        meeting.setPrefWeightStart(rsbWeight.getSelectedMinValue().intValue());
        meeting.setPrefWeightEnd(rsbWeight.getSelectedMaxValue().intValue());

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
        if(dateStart == null || dateEnd == null) {
            msgError += getString(R.string.entry_time) + "\n";
        }
        if(place == null) {
            msgError += getString(R.string.entry_place) + "\n";
        }
        String present = cetPresent.getEditText().getText().toString();
        present = present.replace("$", "");
        if(TextUtils.isEmpty(present)) {
            msgError += getString(R.string.entry_present);
        }
        if(!TextUtils.isEmpty(present) && Integer.parseInt(present) <= 0) {
            msgError += getString(R.string.present_more_zero);
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
