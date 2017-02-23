package com.fangzhipro.app.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhipro.app.R;

/**
 * Created by smacr on 2016/9/24.
 */
public class DialogInput extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText editText;
    private TextView tvTitle;
    private float number = 0;
    private int type;
    private float mInitNumber;
    private ClickListenerInterface clickListenerInterface;


    public interface ClickListenerInterface {

        void doConfirm(String money);
    }

    public DialogInput(Context context, float mInitNumber, int type, ClickListenerInterface clickListenerInterface) {
        super(context);
        this.type = type;
        this.clickListenerInterface = clickListenerInterface;
        this.context = context;
        this.mInitNumber = mInitNumber;
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_dialog, null);
//        addContentView(view, new RadioGroup.LayoutParams(
//                RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        setContentView(view);
        editText = (EditText) view.findViewById(R.id.et_input);
        tvTitle = (TextView) view.findViewById(R.id.tv_type);

        if (type == 0) {
            tvTitle.setText("请输入单价");
            editText.setText(mInitNumber + "");
        } else {
            tvTitle.setText("请输入数量");
            editText.setText((int) mInitNumber + "");
        }

        view.findViewById(R.id.btn_sure).setOnClickListener(this);
        view.findViewById(R.id.iv_close).setOnClickListener(this);
        view.findViewById(R.id.iv_add).setOnClickListener(this);
        view.findViewById(R.id.iv_delete).setOnClickListener(this);


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.4); // 高度设置为屏幕的0.6
        lp.height = (int) (d.heightPixels * 0.4); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_sure:
                clickListenerInterface.doConfirm(type == 0 ? String.valueOf(getNumber()) : String.valueOf((int) getNumber()));
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_add:
                number = getNumber();
                if (type == 0) {
                    editText.setText(++number + "");
                } else {
                    editText.setText((int) (++number) + "");
                }

                break;
            case R.id.iv_delete:
                number = getNumber();
                number = number < 1 ? 0.0f : --number;
                if (type == 0) {
                    editText.setText(number + "");
                } else {
                    editText.setText((int) number + "");
                }

                break;

        }
    }

    private float getNumber() {
        String str = editText.getText().toString().trim();
        if (str.isEmpty()) {
            return 0;
        } else {
            return Float.parseFloat(str);
        }
    }
}
