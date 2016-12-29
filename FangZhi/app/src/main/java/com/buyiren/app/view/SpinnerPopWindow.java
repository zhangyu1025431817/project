package com.buyiren.app.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.buyiren.app.R;
import com.jude.easyrecyclerview.EasyRecyclerView;

/**
 * Created by smacr on 2016/11/30.
 */
public class SpinnerPopWindow extends PopupWindow {
    private Context mContext;
    private EasyRecyclerView recyclerView;


    public SpinnerPopWindow(Context context,int count)
    {
        super(context);
        mContext = context;
        init(count);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }


    private void init(int count)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_popup_window_3d, null);
        setContentView(view);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,count));

    }

}
