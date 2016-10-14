package com.fangzhi.app.main.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by smacr on 2016/10/14.
 */
public class ProductView extends AutoLinearLayout {
    /**
     * 标题
     */
    View viewTitle;
    /**
     * 产品
     */
    EasyRecyclerView easyRecyclerView;
    /**
     * 类型
     */
    RadioGroup radioGroup;
    /**
     * 数据布局
     */
    LinearLayout layoutData;
    /**
     * 透明遮罩
     */
    View viewCover;
    public ProductView(Context context) {
        super(context);
        init();
    }

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        viewCover = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(0,0);
    }
}
