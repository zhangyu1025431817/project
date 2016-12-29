package com.buyiren.app.main.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.buyiren.app.R;
import com.buyiren.app.bean.RoomProduct;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.tools.ScreenUtils;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smacr on 2016/9/23.
 */
public class MyRadioGroup extends RadioGroup {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    private int i = 0;
    private Map<Integer, RoomProductType> mapType = new HashMap<>();
    private Map<Integer, RadioButton> mapRadio = new HashMap<>();

    public MyRadioGroup(Context context) {
        super(context);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addList(String defaultSelectTypeId, String defaultSelectProductId, List<RoomProductType> list, Map<Integer, Integer> indexMap, final OnCheckedListener listener) {
        int checkedId = 0;
        for (RoomProductType type : list) {
            RadioButton tempButton = new RadioButton(getContext());
            tempButton.setId(i);
            if (type.getType_id().equals(defaultSelectTypeId)) {
                checkedId = i;
            }
            List<RoomProduct> sonList = (List<RoomProduct>) type.getSonList();
            if (sonList != null && !sonList.isEmpty()) {
                if (type.getType_id().equals(defaultSelectTypeId)) {
                    for (int i = 0; i < sonList.size(); i++) {
                        if (sonList.get(i).getId().equals(defaultSelectProductId)) {
                            indexMap.put(type.getOrder_num(), i);
                            sonList.get(i).setSelected(true);
                            break;
                        }
                    }
                } else {
                    sonList.get(0).setSelected(true);
                    indexMap.put(type.getOrder_num(), 0);
                }
            }
            tempButton.setBackgroundResource(R.drawable.radio_room_selector);   // 设置RadioButton的背景图片
            tempButton.setButtonDrawable(android.R.color.transparent);           // 设置按钮的样式
            tempButton.setText(type.getType_name());
            tempButton.setPadding(0, 4, 0, 4);
            tempButton.setTextSize(12);
            tempButton.setGravity(Gravity.CENTER);
            tempButton.setTextColor(getResources().getColor(R.color.white));
            tempButton.setMinWidth(ScreenUtils.getScreenWidth(getContext()) / 13);
            tempButton.setMinHeight(ScreenUtils.getScreenHeight(getContext()) / 13);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.topMargin = 10;
            mapType.put(i, type);
            mapRadio.put(i, tempButton);
            i++;
            addView(tempButton, layoutParams);
        }
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (listener != null) {
                    listener.onChecked(mapType.get(checkedId));
                }
            }
        });
        //mapRadio.get(0).setChecked(true);
        mapRadio.get(checkedId).setChecked(true);

    }

    public interface OnCheckedListener {
        void onChecked(RoomProductType roomProductType);
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
