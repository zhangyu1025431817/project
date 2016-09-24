package com.fangzhi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fangzhi.app.R;

/**
 * Created by smacr on 2016/9/24.
 */
public class DialogInput extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText editText;
    private TextView tvTitle;
    private int number = 0;
    private String type;
    private ClickListenerInterface clickListenerInterface;


    public interface ClickListenerInterface {

         void doConfirm(String money);
    }

    public DialogInput(Context context,String type,ClickListenerInterface clickListenerInterface) {
        super(context);
        this.type = type;
        this.clickListenerInterface = clickListenerInterface;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_dialog, null);
        setContentView(view);

        editText = (EditText) view.findViewById(R.id.et_input);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        editText.setText("");
        tvTitle.setText(type);
        view.findViewById(R.id.btn_sure).setOnClickListener(this);
        view.findViewById(R.id.iv_close).setOnClickListener(this);
        view.findViewById(R.id.iv_add).setOnClickListener(this);
        view.findViewById(R.id.iv_delete).setOnClickListener(this);


        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_sure:
                clickListenerInterface.doConfirm(editText.getText().toString().trim());
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_add:
                editText.setText(++number + "");
                break;
            case R.id.iv_delete:
                number = number< 1 ? 0 : number--;
                editText.setText(number+"");
                break;

        }
    }
}
