package com.elatesoftware.meetings.ui.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.elatesoftware.meetings.R;

public class CustomEditText extends FrameLayout {

    private static final String TAG = "CustomEditText_log";
    private Context context;
    private CardView cv;
    private EditText et;

    private int colorFrom = getResources().getColor(android.R.color.darker_gray);
    private int colorTo = getResources().getColor(R.color.white);
    private ValueAnimator colorAnimation, colorAnimationBack;

    int[] attrsArray = new int[] {
            android.R.attr.gravity,
            android.R.attr.hint
    };

    public CustomEditText(Context context) {
        super(context);
        init(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        setAttributes(attrs);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_edit_text, this, true);
        et = (EditText) v.findViewById(R.id.et);
        cv = (CardView) v.findViewById(R.id.cv);
        cv.setCardBackgroundColor(colorFrom);
        et.setBackgroundColor(colorFrom);

        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                et.setBackgroundColor((int) animator.getAnimatedValue());
                cv.setCardBackgroundColor((int) animator.getAnimatedValue());
            }

        });

        colorAnimationBack = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, colorFrom);
        colorAnimationBack.setDuration(250); // milliseconds
        colorAnimationBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                et.setBackgroundColor((int) animator.getAnimatedValue());
                cv.setCardBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    colorAnimation.start();
                } else {
                    colorAnimationBack.start();
                }
            }
        });

    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, attrsArray);
        int gravity = typedArray.getInt(0, Gravity.LEFT);
        et.setGravity(gravity);
        et.setHint(typedArray.getString(1));
        Log.d(TAG, "gravity: " + gravity);
    }

    public EditText getEditText() {
        return et;
    }

    public CardView getCardView() {
        return cv;
    }
}
