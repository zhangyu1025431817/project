package com.buyiren.app.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import org.xwalk.core.XWalkView;

/**
 * Created by smacr on 2016/11/10.
 */
public class BackXWalkView extends XWalkView {
    public BackXWalkView(Context context) {
        super(context);
    }

    public BackXWalkView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackXWalkView(Context context, Activity activity) {
        super(context, activity);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return event.getAction() == KeyEvent.KEYCODE_BACK || super.dispatchKeyEvent(event);
    }
}
