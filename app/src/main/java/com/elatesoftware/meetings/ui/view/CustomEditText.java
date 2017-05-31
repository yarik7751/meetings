package com.elatesoftware.meetings.ui.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.elatesoftware.meetings.R;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class CustomEditText extends FrameLayout {

    private static final String TAG = "CustomEditText_log";
    private Context context;
    private CardView cv;
    private EditText et;

    private int colorFrom = getResources().getColor(R.color.gray);
    private int colorTo = getResources().getColor(R.color.white);
    private int hintColor = getResources().getColor(R.color.white);
    private int hintColorFocus = getResources().getColor(R.color.black);
    private ValueAnimator colorAnimation, colorAnimationBack;

    int[] attrsArray = new int[] {
            android.R.attr.gravity,
            android.R.attr.hint,
            android.R.attr.maxLines,
            android.R.attr.inputType,
            android.R.attr.maxLength
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
                    et.setHintTextColor(hintColorFocus);
                    colorAnimation.start();
                } else {
                    et.setHintTextColor(hintColor);
                    colorAnimationBack.start();
                }
            }
        });

    }

    public void setKeyboardListener(Activity activity) {
        KeyboardVisibilityEvent.setEventListener(activity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(!isOpen) {
                    et.clearFocus();
                }
            }
        });
    }

    private void setAttributes(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, attrsArray);
        int gravity = typedArray.getInt(0, Gravity.LEFT);
        et.setGravity(gravity);
        et.setHint(typedArray.getString(1));
        int maxLines = typedArray.getInt(2, 1);
        et.setSingleLine(maxLines == 1);
        et.setMaxLines(maxLines);
        int inputType = typedArray.getInt(3, -1);
        if(inputType > 0) {
            et.setInputType(inputType);
        }
    }

    public EditText getEditText() {
        return et;
    }

    public CardView getCardView() {
        return cv;
    }
}
