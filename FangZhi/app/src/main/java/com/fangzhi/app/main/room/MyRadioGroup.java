package com.fangzhi.app.main.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.RoomProduct;
import com.fangzhi.app.bean.RoomProductType;
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
    private Map<Integer,RoomProductType> mapType = new HashMap<>();
    private Map<Integer,RadioButton> mapRadio = new HashMap<>();
    public MyRadioGroup(Context context) {
        super(context);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public void addList(List<RoomProductType> list,Map<Integer,Integer> indexMap, final OnCheckedListener listener){
        for (RoomProductType type : list) {
            RadioButton tempButton = new RadioButton(getContext());
            tempButton.setId(i);
            List<RoomProduct> sonList =  type.getSonList();
            if(sonList != null && !sonList.isEmpty()){
                sonList.get(0).setSelected(true);
                indexMap.put(type.getOrder_num(),0);
            }
            tempButton.setBackgroundResource(R.drawable.radio_room_selector);   // 设置RadioButton的背景图片
            tempButton.setButtonDrawable(null);           // 设置按钮的样式
            tempButton.setPadding(0, 20, 0, 20);                 // 设置文字距离按钮四周的距离
            tempButton.setText(type.getType_name());
            tempButton.setGravity(Gravity.CENTER);
            tempButton.setTextColor(getResources().getColor(R.color.white));
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = 10;
            mapType.put(i,type);
            mapRadio.put(i,tempButton);
            i++;
            addView(tempButton, layoutParams);
        }
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(listener != null){
                    listener.onChecked(mapType.get(checkedId));
                }
            }
        });
        mapRadio.get(0).setChecked(true);

    }
    public interface OnCheckedListener{
        void onChecked(RoomProductType roomProductType);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
