package com.fangzhipro.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by smacr on 2016/11/19.
 */
public class AutoRadioGroup extends RadioGroup {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    public AutoRadioGroup(Context context) {
        super(context);
    }

    public AutoRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
