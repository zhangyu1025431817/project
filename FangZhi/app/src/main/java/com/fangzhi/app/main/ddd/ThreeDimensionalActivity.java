package com.fangzhi.app.main.ddd;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.DDDTypeResponseBean;
import com.fangzhi.app.bean.FitmentTypeResponseBean;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.main.adapter.DDDImageAdapter;
import com.fangzhi.app.main.adapter.DDDTypeAdapter;
import com.fangzhi.app.main.adapter.NoDoubleClickListener;
import com.fangzhi.app.main.adapter.ThreeDimensionFitmentAdapter;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.ScreenUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogContactUs;
import com.fangzhi.app.view.SpinnerPopWindow;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/15.
 */
public class ThreeDimensionalActivity extends BaseActivity<ThreeDimensionalPresenter, ThreeDimensionalModel>
        implements ThreeDimensionalContract.View {

    @Bind(R.id.recycler_view_type03)
    EasyRecyclerView recyclerViewTypeBottom;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cb_left)
    CheckBox cbLeft;
    @Bind(R.id.cb_right)
     CheckBox cbRight;
    ThreeDimensionFitmentAdapter threeDimensionFitmentAdapter;
    DDDTypeAdapter dddTypeAdapter;
    DDDImageAdapter dddImageAdapter;
    private int mCaseTypeId;
    SpinnerPopWindow spinnerPopWindowFitment;
    SpinnerPopWindow spinnerPopWindow3D;
    @Override
    public int getLayoutId() {
        return R.layout.activity_three_dimensional;
    }

    @Override
    public void initView() {
        tvTitle.setText("3D场景");

        recyclerViewTypeBottom.setLayoutManager(new GridLayoutManager(this, 3));
        dddImageAdapter = new DDDImageAdapter(this);
        dddImageAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                ArrayList<Map<String,String>> mapArrayList = new ArrayList<>();
                //首先构建当前数据
                DDDTypeResponseBean.DDDType.DDD ddd =  dddImageAdapter.getItem(position);
                if("0".equals(ddd.getImage_type())){
                    mapArrayList.add(makePictureData(ddd));
                }

                List<DDDTypeResponseBean.DDDType.DDD> list= dddImageAdapter.getAllData();
                for(DDDTypeResponseBean.DDDType.DDD bean : list){
                    if(bean == ddd){
                        continue;
                    }
                    if("0".equals(bean.getImage_type())){
                        mapArrayList.add(makePictureData(bean));
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("name", ddd.getCase_name());
                if ("1".equals(ddd.getImage_type())) {
                    intent.putExtra("url", dddImageAdapter.getItem(position).getCase_url());
                    intent.setClass(ThreeDimensionalActivity.this, DDDWebView.class);
                } else {
                    intent.putExtra("url", mapArrayList);
                    intent.setClass(ThreeDimensionalActivity.this, DDView2.class);
                }
                startActivity(intent);

            }
        });
        recyclerViewTypeBottom.setAdapter(dddImageAdapter);
        //工装家装
        threeDimensionFitmentAdapter = new ThreeDimensionFitmentAdapter(this);
        threeDimensionFitmentAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                chooseThreeDimensionFitment(position);
                spinnerPopWindowFitment.dismiss();
            }
        });
        spinnerPopWindowFitment = new SpinnerPopWindow(this,2);
        spinnerPopWindowFitment.setAdapter(threeDimensionFitmentAdapter);
        spinnerPopWindowFitment.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cbLeft.setChecked(false);
                cbRight.setChecked(false);
            }
        });
        spinnerPopWindowFitment.setWidth(ScreenUtils.getScreenWidth(this));
        spinnerPopWindowFitment.setHeight(ScreenUtils.getScreenHeight(this)/6);
        //3D2D
        dddTypeAdapter = new DDDTypeAdapter(this);
        dddTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                chooseDDDType(position);
                spinnerPopWindow3D.dismiss();
            }
        });
        spinnerPopWindow3D = new SpinnerPopWindow(this,2);
        spinnerPopWindow3D.setAdapter(dddTypeAdapter);
        spinnerPopWindow3D.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cbLeft.setChecked(false);
                cbRight.setChecked(false);

            }
        });
        spinnerPopWindow3D.setWidth(ScreenUtils.getScreenWidth(this));
        spinnerPopWindow3D.setHeight(ScreenUtils.getScreenHeight(this)/6);
        mPresenter.getCaseTypeList();
    }


    private Map<String ,String > makePictureData(DDDTypeResponseBean.DDDType.DDD ddd){
        Map<String ,String> map = new HashMap<>();
        String firstUrl = ddd.getCase_url();
        if(firstUrl != null && !firstUrl.isEmpty()){
            String[] images = firstUrl.split(";");
            int length = images.length;
            for(int i =0;i<length;i++){
                map.put("name",ddd.getCase_name());
                map.put("url",images[i]);
                map.put("position",(i+1)+"");
                map.put("count",length+"");
            }
        }
        return map;
    }
    @Override
    public void tokenInvalid(String msg) {

    }

    @OnCheckedChanged(R.id.cb_left)
    public void onCheckLeft(CompoundButton button,boolean isSelected){
        if(isSelected){
            spinnerPopWindowFitment.showAsDropDown(button);
        }else {
            spinnerPopWindowFitment.dismiss();
        }
    }

    @OnCheckedChanged(R.id.cb_right)
    public void onCheckRight(CompoundButton button,boolean isSelected){
        if(isSelected){
            spinnerPopWindow3D.showAsDropDown(button);
        }else {
            spinnerPopWindow3D.dismiss();

        }
    }
    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        finish();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getCaseTypeId() {
        return mCaseTypeId + "";
    }

    @Override
    public void showFitmentTypes(ArrayList<FitmentTypeResponseBean.FitmentType> caseTypeList) {
        threeDimensionFitmentAdapter.addAll(caseTypeList);
        if (caseTypeList != null && !caseTypeList.isEmpty()) {
            chooseThreeDimensionFitment(0);
        }
    }

    @Override
    public void showDDDTypes(ArrayList<DDDTypeResponseBean.DDDType> caseList) {
        if (caseList == null || caseList.isEmpty()) {
            return;
        }
        //构建全部数据
        DDDTypeResponseBean.DDDType typeAll = new DDDTypeResponseBean.DDDType();
        typeAll.setImage_type("-1");
        typeAll.setImage_type_name("全部");
        ArrayList<DDDTypeResponseBean.DDDType.DDD> sonList = new ArrayList<>();
        for(DDDTypeResponseBean.DDDType dddType : caseList){
            if(dddType.getSonList() != null){
                sonList.addAll(dddType.getSonList());
            }

        }
        typeAll.setSonList(sonList);
        caseList.add(0,typeAll);

        dddTypeAdapter.clear();
        dddTypeAdapter.addAll(caseList);
            chooseDDDType(0);
    }

    private void chooseThreeDimensionFitment(int position) {
        FitmentTypeResponseBean.FitmentType fitmentType = threeDimensionFitmentAdapter.getItem(position);
        if (fitmentType.isSelected()) {
            return;
        }
        for (FitmentTypeResponseBean.FitmentType bean : threeDimensionFitmentAdapter.getAllData()) {
            bean.setSelected(false);
        }
        fitmentType.setSelected(true);
        cbLeft.setText(fitmentType.getType_name());
        mCaseTypeId = fitmentType.getCase_type();
        threeDimensionFitmentAdapter.notifyDataSetChanged();
        mPresenter.getCaseList();
    }

    private void chooseDDDType(int position) {
        DDDTypeResponseBean.DDDType dddType = dddTypeAdapter.getItem(position);
        if (dddType.isSelected()) {
            return;
        }
        for (DDDTypeResponseBean.DDDType bean : dddTypeAdapter.getAllData()) {
            bean.setSelected(false);
        }
        dddType.setSelected(true);
        cbRight.setText(dddType.getImage_type_name());
        dddTypeAdapter.notifyDataSetChanged();
        dddImageAdapter.clear();
        dddImageAdapter.addAll(dddType.getSonList());
    }
    @OnClick(R.id.tv_btn)
    public void onShow3D(){
        new DialogContactUs(this).show();
    }

}
