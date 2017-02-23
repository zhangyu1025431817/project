package com.fangzhipro.app.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.fangzhipro.app.R;

/**
 * Created by zhangyu on 2016/8/15.
 */
public class ClearEditText extends EditText {
    private final static String TAG = "ClearEditText";
    private Drawable imgAble;
    private Drawable imgLeft;
    private Context mContext;

    public ClearEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        Drawable[] drawables =  getCompoundDrawables();

        imgLeft = drawables[0];
        imgAble = mContext.getResources().getDrawable(R.drawable.icon_edit_text_delete);
     //   imgLeft = mContext.getResources().getDrawable(R.drawable.icon_search);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        if(length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(imgLeft, null, null, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(imgLeft, null, imgAble, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 80;
            if(rect.contains(eventX, eventY)) {
                setText("");
                if(mListener != null){
                    mListener.onClear();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public interface OnClearListener{
        void onClear();
    }
    private OnClearListener mListener;
    public void addOnClearListener(OnClearListener listener){
        mListener = listener;
    }
}
