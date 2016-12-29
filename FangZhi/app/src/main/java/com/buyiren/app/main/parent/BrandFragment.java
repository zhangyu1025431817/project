package com.buyiren.app.main.parent;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.bean.LoginNewBean;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/12/2.
 */
public class BrandFragment extends Fragment {
    public interface MyListener
    {
         void chooseParent(LoginNewBean.Parent parent);
    }
    private MyListener myListener;
    static Shader shaderWhite;
    static Shader shaderBlue;
    private static final String ARG_DATA = "data";

    public static BrandFragment newInstance(ArrayList<LoginNewBean.Parent> list) {
        shaderWhite =new LinearGradient(0, 0, 0, 100, Color.WHITE, Color.WHITE, Shader.TileMode.CLAMP);
        shaderBlue =new LinearGradient(0, 0, 0, 100,
                0xFF0095d6, 0xFF005ead,
                Shader.TileMode.CLAMP);
        BrandFragment fragment = new BrandFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA,list);
        fragment.setArguments(args);
        return fragment;
    }

    public BrandFragment() {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        myListener = (MyListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_layout, container, false);
        TextView textView1 = (TextView) rootView.findViewById(R.id.tv_brand1);
        TextView textView2 = (TextView) rootView.findViewById(R.id.tv_brand2);
        ArrayList<LoginNewBean.Parent> list = (ArrayList<LoginNewBean.Parent>) getArguments().getSerializable(ARG_DATA);

        final LoginNewBean.Parent parent = list.get(0);
        textView1.setText(parent.getNAME());
        if (parent.isSelected()) {
            textView1.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_selected));
            textView1.getPaint().setShader(shaderWhite);
        } else {
            textView1.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_unselected));
            textView1.getPaint().setShader(shaderBlue);
        }
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.chooseParent(parent);
            }
        });
        try {
            final LoginNewBean.Parent parent2 = list.get(1);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(parent2.getNAME());
            if (parent2.isSelected()) {
                textView2.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_selected));
                textView2.getPaint().setShader(shaderWhite);
            } else {
                textView2.setBackground(getContext().getResources().getDrawable(R.drawable.btn_parent_unselected));
                textView2.getPaint().setShader(shaderBlue);
            }
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.chooseParent(parent2);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            textView2.setVisibility(View.GONE);
        }
        return rootView;
    }

}
