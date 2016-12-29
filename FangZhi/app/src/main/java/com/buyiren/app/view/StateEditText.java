package com.buyiren.app.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.buyiren.app.R;

/**
 * Created by smacr on 2016/9/2.
 */
public class StateEditText extends EditText {
    private String defaultHint;

    public StateEditText(Context context) {
        this(context, null);
    }

    public StateEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        defaultHint = getHint().toString();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             //   changeState(new StateEditText.EditState(R.drawable.shape_edit_bg, defaultHint, R.color.colorTextGrayBABABA, null));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setDefaultHint(String defaultHint) {
        this.defaultHint = defaultHint;
        setHint(defaultHint);
    }

    public void changeState(EditState state) {
        setBackgroundResource(state.backgroundResId);
        setHint(state.text);
        setHintTextColor(getResources().getColor(state.textColor));

        if (state.drawable != null) {
            state.drawable.setBounds(0, 0, state.drawable.getMinimumWidth(), state.drawable.getMinimumHeight());
            setCompoundDrawablePadding(10);
        }
        setCompoundDrawables(state.drawable, null, null, null);
    }

    public void changeState(String msg) {
        changeState(new StateEditText.EditState(R.drawable.bg_edit_error,
                msg, R.color.colorRedError, getResources().getDrawable(R.drawable.icon_edit_error)));
        startAnimation(StateEditText.shakeAnimation(3));
        clearFocus();
    }

    public static class EditState {
        int backgroundResId;
        int textColor;
        String text;

        Drawable drawable;

        public EditState(int backgroundResId, String text, int textColor, Drawable drawable) {
            this.backgroundResId = backgroundResId;
            this.text = text;
            this.drawable = drawable;
            this.textColor = textColor;
        }
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 5, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(300);
        return translateAnimation;
    }
}
