package com.elatesoftware.meetings.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.elatesoftware.meetings.R;
import com.elatesoftware.meetings.ui.activity.base.BaseActivity;
import com.elatesoftware.meetings.util.AndroidUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddDaleActivity extends BaseActivity {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    private static final String TAG = "AddDaleActivity_logs";
    private static final int REQUEST_PLACE_PICKER = 102;
    @BindView(R.id.ll_main) LinearLayout llMain;
    private CrystalRangeSeekbar csbAge;
    private TextView tvAgeBegin, tvAgeEnd;
    private TreeNode treeNodeRoot;
    private AndroidTreeView tView;
    private Button btnInMap;
    private EditText etWhere;

    private List<TreeNode> children;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dale);

        treeNodeRoot = TreeNode.root();

        children = new ArrayList<>();
        setChild(R.drawable.ic_girl, getString(R.string.girl), R.layout.item_girl_settings);
        setChild(R.drawable.ic_location_on_black_24dp, getString(R.string.location), R.layout.item_location_settings);
        setChild(R.drawable.ic_attach_money_black_24dp, getString(R.string.presents), R.layout.item_present_settings);

        tView = new AndroidTreeView(this, treeNodeRoot);
        llMain.addView(tView.getView());
        tView.setDefaultAnimation(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    Log.d(TAG, "Place: " + place.getName());
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    // TODO: Handle the error.
                    Log.d(TAG, status.getStatusMessage());

                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                }
                break;

            case REQUEST_PLACE_PICKER:
                final Place place = PlacePicker.getPlace(data, this);

                String name = place.getName().toString();
                String address = place.getAddress().toString();
                String attributions = PlacePicker.getAttributions(data);
                if (attributions == null) {
                    attributions = "";
                }
                Log.d(TAG, "name: " + name);
                Log.d(TAG, "address: " + address);
                Log.d(TAG, "attributions: " + attributions);
                if(etWhere != null) {
                    etWhere.setText(name + ", " + address);
                }
                /*mViewName.setText(name);
                mViewAddress.setText(address);
                mViewAttributions.setText(Html.fromHtml(attributions));*/
                break;
        }
    }

    private void setChild(int icon, String text, int layout) {
        ParentHolder.IconTreeItem iconTreeItem = new ParentHolder.IconTreeItem(icon, text);
        TreeNode parent = new TreeNode(iconTreeItem).setViewHolder(new ParentHolder(this));
        TreeNode child = new TreeNode(null).setViewHolder(new ChildHolder(this, layout));
        children.add(child);
        final int lastNumber = children.size() - 1;
        parent.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Log.d(TAG, "lastNumber: " + lastNumber);
                hideChildren(lastNumber);
            }
        });
        parent.addChildren(child);
        treeNodeRoot.addChild(parent);
    }

    private void hideChildren(int exception) {
        for(int i = 0; i < children.size(); i++) {
            if(exception != i) {
                children.get(i).setExpanded(false);
            }
        }
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {

        } catch (GooglePlayServicesNotAvailableException e) {

        }
    }

    public void choosePlace() {
        try {
            PlacePicker.IntentBuilder intentBuilder =
                    new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_PLACE_PICKER);

        } catch (GooglePlayServicesRepairableException e) {
            // ...
        } catch (GooglePlayServicesNotAvailableException e) {
            // ...
        }
    }

    public static class ParentHolder extends TreeNode.BaseNodeViewHolder<ParentHolder.IconTreeItem> {

        Context context;

        public ParentHolder(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public View createNodeView(TreeNode node, IconTreeItem value) {
            View v = LayoutInflater.from(context).inflate(R.layout.layout_profile_node, null, false);
            ImageView imgIcon = ((ImageView) v.findViewById(R.id.img_icon));
            TextView tvTitle = ((TextView) v.findViewById(R.id.tv_title));
            imgIcon.setImageResource(value.icon);
            tvTitle.setText(value.text);
            v.setLayoutParams(new LinearLayout.LayoutParams(AndroidUtils.getWindowsSizeParams(context)[0], ViewGroup.LayoutParams.WRAP_CONTENT));
            return v;
        }

        public static class IconTreeItem {
            public int icon;
            public String text;

            public IconTreeItem(int icon, String text) {
                this.icon = icon;
                this.text = text;
            }
        }
    }

    public class ChildHolder extends TreeNode.BaseNodeViewHolder {

        private Context context;
        private int res;

        public ChildHolder(Context context, int res) {
            super(context);
            this.context = context;
            this.res = res;
        }

        @Override
        public View createNodeView(TreeNode node, Object value) {
            View v = LayoutInflater.from(context).inflate(res, null, false);
            switch (res) {
                case R.layout.item_girl_settings:
                    csbAge = (CrystalRangeSeekbar) v.findViewById(R.id.csb_age);
                    tvAgeBegin = (TextView) v.findViewById(R.id.tv_age_begin);
                    tvAgeEnd = (TextView) v.findViewById(R.id.tv_age_end);

                    csbAge.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                        @Override
                        public void valueChanged(Number minValue, Number maxValue) {
                            tvAgeBegin.setText(minValue + "");
                            tvAgeEnd.setText(maxValue + "");
                        }
                    });
                    break;

                case R.layout.item_location_settings:
                    etWhere = (EditText) v.findViewById(R.id.et_where);
                    btnInMap = (Button) v.findViewById(R.id.btn_in_map);
                    btnInMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            choosePlace();
                        }
                    });
                    break;
            }
            v.setLayoutParams(new LinearLayout.LayoutParams(AndroidUtils.getWindowsSizeParams(context)[0], ViewGroup.LayoutParams.WRAP_CONTENT));
            return v;
        }
    }
}
