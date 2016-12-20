package com.buqi.app.main.adapter;

import android.view.View;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.Calendar;

/**
 * Created by smacr on 2016/11/15.
 */
public abstract class NoDoubleClickListener implements RecyclerArrayAdapter.OnItemClickListener, View.OnClickListener {
    final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onItemClick(int position) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(position);
        }
    }

    @Override
    public void onClick(View view) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(0);
        }
    }

    public abstract void onNoDoubleClick(int position);
}
