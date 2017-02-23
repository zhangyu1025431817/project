package com.fangzhipro.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fangzhipro.app.MyApplication;
import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.LoginNewBean;
import com.fangzhipro.app.config.SpKey;
import com.fangzhipro.app.tools.SPUtils;

import java.util.List;

/**
 * Created by smacr on 2016/11/18.
 */
public class DialogChooseParent extends Dialog {
    public interface onCheckedListener {
        void onCheck(int id);
    }

    public DialogChooseParent(Context context, final List<LoginNewBean.Parent> list, final onCheckedListener listener) {
        super(context, R.style.blurDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_choose_parent, null);
        setContentView(view);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);

        for (LoginNewBean.Parent bean : list) {
            RadioButton tempButton = new RadioButton(context);
            tempButton.setId(Integer.parseInt(bean.getID()));
            tempButton.setBackgroundResource(R.drawable.radio_parent_selector);   // 设置RadioButton的背景图片
            tempButton.setButtonDrawable(android.R.color.transparent);           // 设置按钮的样式
            tempButton.setText(bean.getNAME());
            tempButton.setTextSize(13);
            tempButton.setPadding(0,20,0,20);
            tempButton.setGravity(Gravity.CENTER);
            tempButton.setChecked(bean.isSelected());
            radioGroup.addView(tempButton, RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (LoginNewBean.Parent bean : list) {
                    if (Integer.parseInt(bean.getID()) == checkedId) {
                        bean.setSelected(true);
                        //联系厂商url
                        SPUtils.put(MyApplication.getContext(),
                                SpKey.FACTORY_ADDRESS, bean.getURL() == null ? "" : bean.getURL());
                    } else {
                        bean.setSelected(false);
                    }
                }
                listener.onCheck(checkedId);
                dismiss();
            }
        });
    }

}
