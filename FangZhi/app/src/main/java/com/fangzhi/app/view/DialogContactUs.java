package com.fangzhi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.fangzhi.app.R;

/**
 * Created by smacr on 2016/11/18.
 */
public class DialogContactUs extends Dialog {
    public interface onCheckedListener {
        void onCheck(int id);
    }

    public DialogContactUs(Context context) {
        super(context, R.style.blurDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_contact_us, null);
        setContentView(view);
    }

}
