package com.fangzhi.app.main.room;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhy.autolayout.AutoLinearLayout;

/**
 * Created by smacr on 2016/10/14.
 */
public class ProductView extends AutoLinearLayout {
    /**
     * 标题
     */
    TextView viewTitle;
    /**
     * 产品最外层
     */
    LinearLayout layoutBottom;
    /**
     * 产品
     */
    EasyRecyclerView easyRecyclerView;
    /**
     * 类型
     */
    MyRadioGroup radioGroup;
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
        setOrientation(HORIZONTAL);
        viewCover = new View(getContext());
        viewCover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.INVISIBLE);
            }
        });
        LayoutParams layoutParamsCover = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsCover.weight = 3;

        layoutData = new LinearLayout(getContext());
        layoutData.setPadding(20,20,20,20);
        layoutData.setOrientation(VERTICAL);
        layoutData.setBackgroundColor(getResources().getColor(R.color.alpha_gray_light));
        LayoutParams layoutParamsData =  new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsData.weight = 1;

        viewTitle = new TextView(getContext());
        viewTitle.setText("选择建材");
        Drawable drawable = getResources().getDrawable(R.drawable.icon_choose_product);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        viewTitle.setCompoundDrawables(drawable,null,null,null);
        viewTitle.setCompoundDrawablePadding(12);
        viewTitle.setTextColor(getResources().getColor(R.color.white));

        layoutBottom = new LinearLayout(getContext());
        layoutBottom.setOrientation(HORIZONTAL);


        easyRecyclerView = new EasyRecyclerView(getContext());
        LayoutParams layoutParamsRecyclerView = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsRecyclerView.weight = 2;

        radioGroup = new MyRadioGroup(getContext());
        radioGroup.setOrientation(VERTICAL);
        LayoutParams layoutParamsRadioGroup = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsRadioGroup.weight = 1;

        layoutBottom.addView(easyRecyclerView,layoutParamsRecyclerView);
        layoutBottom.addView(radioGroup,layoutParamsRadioGroup);
        layoutData.addView(viewTitle, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        layoutData.addView(layoutBottom,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        addView(viewCover,layoutParamsCover);
        addView(layoutData,layoutParamsData);

    }

    public EasyRecyclerView getEasyRecyclerView(){
        return easyRecyclerView;
    }
    public MyRadioGroup getRadioGroup(){
        return radioGroup;
    }
    public void changeBottom(){
        setGravity(VERTICAL);
        LayoutParams layoutParamsCover = (LayoutParams) viewCover.getLayoutParams();
        layoutParamsCover.width = LayoutParams.MATCH_PARENT;
        layoutParamsCover.height = 0;
        viewCover.setLayoutParams(layoutParamsCover);

        LayoutParams layoutParamsData = (LayoutParams) layoutData.getLayoutParams();
        layoutParamsData.width = LayoutParams.MATCH_PARENT;
        layoutParamsData.height = 0;
        layoutData.setLayoutParams(layoutParamsData);
    }
}
