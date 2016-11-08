package com.fangzhi.app.main.room;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.Space;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by smacr on 2016/10/14.
 */
public class ProductView extends LinearLayout {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
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
    EasyRecyclerView productRecyclerView;
    /**
     * 类型
     */
    EasyRecyclerView typeRecyclerView;
    LinearLayout layoutType;
    /**
     * 数据布局
     */
    LinearLayout layoutData;
    /**
     * 透明遮罩
     */
    View viewCover;
    View line;
    TextView tvSearch;
    AutoLinearLayout layoutSearch;
    HorizontalScrollView horizontalScrollView;
    ScrollView scrollView;

    public ProductView(Context context) {
        super(context);
        init();
    }

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        viewCover = new View(getContext());
        viewCover.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(View.INVISIBLE);
            }
        });
        LayoutParams layoutParamsCover = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsCover.weight = 7;

        layoutData = new LinearLayout(getContext());
        layoutData.setPadding(20, 20, 20, 20);
        layoutData.setOrientation(VERTICAL);
        layoutData.setBackgroundColor(getResources().getColor(R.color.alpha_gray_light));
        LayoutParams layoutParamsData = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsData.weight = 2;

        viewTitle = new TextView(getContext());
        viewTitle.setText("选择建材");
        viewTitle.setPadding(0, 30, 0, 0);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_choose_product);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示

        line = new View(getContext());
        line.setBackgroundColor(getResources().getColor(R.color.white));
        viewTitle.setCompoundDrawables(drawable, null, null, null);
        viewTitle.setCompoundDrawablePadding(12);
        viewTitle.setTextColor(getResources().getColor(R.color.white));

        layoutBottom = new LinearLayout(getContext());
        layoutBottom.setOrientation(HORIZONTAL);


        productRecyclerView = new EasyRecyclerView(getContext());
        LayoutParams layoutParamsRecyclerView = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsRecyclerView.weight = 3;

        horizontalScrollView = new HorizontalScrollView(getContext());
        scrollView = new ScrollView(getContext());

        layoutType = new LinearLayout(getContext());
        layoutType.setOrientation(VERTICAL);
        LayoutParams layoutTypeParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutTypeParams.weight = 2;

        typeRecyclerView = new EasyRecyclerView(getContext());
        LayoutParams layoutParamsRadioGroup = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParamsRadioGroup.weight = 10;

        layoutSearch = new AutoLinearLayout(getContext());
        layoutSearch.setBackgroundDrawable(getContext().getResources().
                getDrawable(R.drawable.shape_transparent_solid_white_stroke));
        layoutSearch.setGravity(Gravity.CENTER);
        tvSearch = new TextView(getContext());
        //  tvSearch.setPadding(30,30,30,30);
        tvSearch.setText("搜索");
        tvSearch.setGravity(Gravity.CENTER_VERTICAL);
        tvSearch.setTextSize(12);
        tvSearch.setTextColor(getContext().getResources().getColor(R.color.white));

        Drawable drawableSearch = getResources().getDrawable(R.drawable.icon_search);
        drawableSearch.setBounds(0, 0, drawableSearch.getMinimumWidth(), drawableSearch.getMinimumHeight());
        tvSearch.setCompoundDrawables(drawableSearch, null, null, null);

        layoutSearch.addView(tvSearch, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams layoutParamsTvSearch = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParamsTvSearch.weight = 1;
        layoutParamsTvSearch.rightMargin = 16;
        layoutParamsTvSearch.leftMargin = 16;
        layoutParamsTvSearch.topMargin = 6;
        layoutParamsTvSearch.bottomMargin = 20;


        layoutType.addView(typeRecyclerView, layoutParamsRadioGroup);
        Space space = new Space(getContext());
        LayoutParams spaceParams = new LayoutParams(20, 20);
        layoutType.addView(space, spaceParams);
        layoutType.addView(layoutSearch, layoutParamsTvSearch);

        layoutBottom.addView(productRecyclerView, layoutParamsRecyclerView);
        layoutBottom.addView(layoutType, layoutTypeParams);

        layoutData.addView(viewTitle, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutData.addView(line, LayoutParams.MATCH_PARENT, 2);
        layoutData.addView(layoutBottom, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        addView(viewCover, layoutParamsCover);
        addView(layoutData, layoutParamsData);

    }

    public EasyRecyclerView getProductRecyclerView() {
        return productRecyclerView;
    }

    public EasyRecyclerView getTypeRecyclerView() {
        return typeRecyclerView;
    }

    public AutoLinearLayout getTvSearch() {
        return layoutSearch;
    }

    public void changeBottom() {
        setOrientation(VERTICAL);
        LayoutParams layoutParamsCover = (LayoutParams) viewCover.getLayoutParams();
        layoutParamsCover.width = LayoutParams.MATCH_PARENT;
        layoutParamsCover.height = 0;
        layoutParamsCover.weight = 14;
        viewCover.setLayoutParams(layoutParamsCover);

        LayoutParams layoutParamsData = (LayoutParams) layoutData.getLayoutParams();
        layoutParamsData.width = LayoutParams.MATCH_PARENT;
        layoutParamsData.height = 0;
        layoutParamsData.weight = 5;
        layoutData.setLayoutParams(layoutParamsData);
        viewTitle.setVisibility(GONE);
        line.setVisibility(GONE);
        layoutData.setPadding(20, 0, 20, 0);
        StaggeredGridLayoutManager staggeredGridLayoutManager =  new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        productRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        LayoutParams layoutParamsRecyclerView = (LayoutParams) productRecyclerView.getLayoutParams();
        layoutParamsRecyclerView.width = LayoutParams.MATCH_PARENT;
        layoutParamsRecyclerView.height = 0;
        layoutParamsRecyclerView.weight = 7;
        productRecyclerView.setLayoutParams(layoutParamsRecyclerView);


        LayoutParams layoutParamsLayoutType = (LayoutParams) layoutType.getLayoutParams();
        layoutParamsLayoutType.width = LayoutParams.MATCH_PARENT;
        layoutParamsLayoutType.height = 0;
        layoutParamsLayoutType.weight = 3;
        layoutType.setLayoutParams(layoutParamsLayoutType);
        layoutType.setOrientation(HORIZONTAL);


        LayoutParams layoutParamsSearch = (LayoutParams) layoutSearch.getLayoutParams();
        layoutParamsSearch.width = 0;
        layoutParamsSearch.height = LayoutParams.MATCH_PARENT;
        layoutParamsSearch.weight = 1;
        layoutSearch.setLayoutParams(layoutParamsSearch);

        LayoutParams layoutParamsType = (LayoutParams) typeRecyclerView.getLayoutParams();
        layoutParamsType.width = 0;
        layoutParamsType.height = LayoutParams.MATCH_PARENT;
        layoutParamsType.weight = 10;
        typeRecyclerView.setLayoutParams(layoutParamsType);

        layoutBottom.setOrientation(VERTICAL);
        layoutBottom.bringChildToFront(productRecyclerView);
        typeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        bringChildToFront(layoutData);
    }

    public void changeRight() {
        setOrientation(HORIZONTAL);
        LayoutParams layoutParamsCover = (LayoutParams) viewCover.getLayoutParams();
        layoutParamsCover.width = 0;
        layoutParamsCover.height = LayoutParams.MATCH_PARENT;
        layoutParamsCover.weight = 7;
        viewCover.setLayoutParams(layoutParamsCover);

        LayoutParams layoutParamsData = (LayoutParams) layoutData.getLayoutParams();
        layoutParamsData.width = 0;
        layoutParamsData.height = LayoutParams.MATCH_PARENT;
        layoutParamsData.weight = 2;
        viewTitle.setVisibility(VISIBLE);
        line.setVisibility(VISIBLE);
        layoutData.setLayoutParams(layoutParamsData);
        layoutData.setPadding(20, 20, 20, 20);
        viewTitle.setPadding(0, 30, 0, 0);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LayoutParams layoutParamsRecyclerView = (LayoutParams) productRecyclerView.getLayoutParams();
        layoutParamsRecyclerView.width = 0;
        layoutParamsRecyclerView.height = LayoutParams.MATCH_PARENT;
        layoutParamsRecyclerView.weight = 5;
        productRecyclerView.setLayoutParams(layoutParamsRecyclerView);

        LayoutParams layoutParamsLayoutType = (LayoutParams) layoutType.getLayoutParams();
        layoutParamsLayoutType.width = 0;
        layoutParamsLayoutType.height = LayoutParams.MATCH_PARENT;
        layoutParamsLayoutType.weight = 3;
        layoutType.setLayoutParams(layoutParamsLayoutType);
        layoutType.setOrientation(VERTICAL);


        LayoutParams layoutParamsSearch = (LayoutParams) tvSearch.getLayoutParams();
        layoutParamsSearch.width = LayoutParams.MATCH_PARENT;
        layoutParamsSearch.height = 0;
        layoutParamsSearch.weight = 1;
        tvSearch.setLayoutParams(layoutParamsSearch);

        LayoutParams layoutParamsType = (LayoutParams) typeRecyclerView.getLayoutParams();
        layoutParamsType.width = LayoutParams.MATCH_PARENT;
        layoutParamsType.height = 0;
        layoutParamsType.weight = 10;
        typeRecyclerView.setLayoutParams(layoutParamsType);

        layoutBottom.setOrientation(HORIZONTAL);
        layoutBottom.bringChildToFront(typeRecyclerView);
        typeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bringChildToFront(layoutData);
    }

    public void changeTop() {
        setOrientation(VERTICAL);
        LayoutParams layoutParamsCover = (LayoutParams) viewCover.getLayoutParams();
        layoutParamsCover.width = LayoutParams.MATCH_PARENT;
        layoutParamsCover.height = 0;
        layoutParamsCover.weight = 14;
        viewCover.setLayoutParams(layoutParamsCover);

        LayoutParams layoutParamsData = (LayoutParams) layoutData.getLayoutParams();
        layoutParamsData.width = LayoutParams.MATCH_PARENT;
        layoutParamsData.height = 0;
        layoutParamsData.weight = 5;
        viewTitle.setVisibility(GONE);
        line.setVisibility(GONE);
        layoutData.setPadding(20, 0, 20, 0);
        layoutData.setLayoutParams(layoutParamsData);
        productRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        LayoutParams layoutParamsRecyclerView = (LayoutParams) productRecyclerView.getLayoutParams();
        layoutParamsRecyclerView.width = LayoutParams.MATCH_PARENT;
        layoutParamsRecyclerView.height = 0;
        layoutParamsRecyclerView.weight = 7;
        productRecyclerView.setLayoutParams(layoutParamsRecyclerView);

        LayoutParams layoutParamsRadioGroup = (LayoutParams) typeRecyclerView.getLayoutParams();
        layoutParamsRadioGroup.width = LayoutParams.MATCH_PARENT;
        layoutParamsRadioGroup.height = 0;
        layoutParamsRadioGroup.weight = 3;
        typeRecyclerView.setLayoutParams(layoutParamsRadioGroup);

        layoutBottom.setOrientation(VERTICAL);
        layoutBottom.bringChildToFront(productRecyclerView);
        typeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        bringChildToFront(viewCover);
    }

    public void changeLeft() {

    }

    @Override
    public AutoLinearLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoLinearLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
