package com.buyiren.app.main.sample;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.base.BaseActivity;
import com.buyiren.app.bean.CooperativeResponseBean;
import com.buyiren.app.bean.DDDTypeResponseBean;
import com.buyiren.app.bean.SceneLabel;
import com.buyiren.app.config.SpKey;
import com.buyiren.app.main.adapter.CooperativeAdapter;
import com.buyiren.app.main.adapter.DDDImageAdapter;
import com.buyiren.app.main.adapter.DDDTypeAdapter;
import com.buyiren.app.main.adapter.NoDoubleClickListener;
import com.buyiren.app.main.adapter.TagAdapter;
import com.buyiren.app.main.ddd.DDDWebView;
import com.buyiren.app.main.ddd.DDView2;
import com.buyiren.app.tools.SPUtils;
import com.buyiren.app.tools.ScreenUtils;
import com.buyiren.app.view.SpinnerPopWindow;
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
 * Created by smacr on 2016/12/1.
 */
public class SampleActivity extends BaseActivity<SamplePresenter,SampleModel>
        implements SampleContract.View{
    @Bind(R.id.recycler_view_type)
    EasyRecyclerView recyclerViewTypeBottom;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cb_left)
    CheckBox cbLeft;
    @Bind(R.id.cb_right)
    CheckBox cbRight;
    @Bind(R.id.cb_middle)
    CheckBox cbMiddle;
    //合作商
    CooperativeAdapter cooperativeAdapter;
    //标签
    TagAdapter tagAdapter;
    //2D3D
    DDDTypeAdapter dddTypeAdapter;
    DDDImageAdapter dddImageAdapter;

    SpinnerPopWindow spinnerPopWindowCooperative;
    SpinnerPopWindow spinnerPopWindowTag;
    SpinnerPopWindow spinnerPopWindow3D;
    private String mCooperativeId;

    private List<DDDTypeResponseBean.DDDType.DDD> mAllList = new ArrayList<>();
    private List<DDDTypeResponseBean.DDDType.DDD> m2DList = new ArrayList<>();
    private List<DDDTypeResponseBean.DDDType.DDD> m3DList =  new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    public void initView() {
        tvTitle.setText("真实案例");

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
                    intent.setClass(SampleActivity.this, DDDWebView.class);
                } else {
                    intent.putExtra("url", mapArrayList);
                    intent.setClass(SampleActivity.this, DDView2.class);
                }
                startActivity(intent);

            }
        });
        recyclerViewTypeBottom.setAdapter(dddImageAdapter);
        //合作商
        cooperativeAdapter = new CooperativeAdapter(this);
        cooperativeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                chooseCooperative(position);
                hideAll();
            }
        });
        spinnerPopWindowCooperative = new SpinnerPopWindow(this,3);
        spinnerPopWindowCooperative.setWidth(ScreenUtils.getScreenWidth(this));
        spinnerPopWindowCooperative.setHeight(ScreenUtils.getScreenHeight(this)/6);
        spinnerPopWindowCooperative.setAdapter(cooperativeAdapter);
        spinnerPopWindowCooperative.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideAll();
            }
        });
       //标签
        tagAdapter = new TagAdapter(this);
        tagAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                chooseTag(position);
                hideAll();
            }
        });
        spinnerPopWindowTag = new SpinnerPopWindow(this,3);
        spinnerPopWindowTag.setWidth(ScreenUtils.getScreenWidth(this));
        spinnerPopWindowTag.setHeight(ScreenUtils.getScreenHeight(this)/6);
        spinnerPopWindowTag.setAdapter(tagAdapter);
        spinnerPopWindowTag.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideAll();
            }
        });
        //2D3D
        dddTypeAdapter = new DDDTypeAdapter(this);
        dddTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                choose3D2D(position);
                hideAll();
            }
        });
        spinnerPopWindow3D = new SpinnerPopWindow(this,3);
        spinnerPopWindow3D.setWidth(ScreenUtils.getScreenWidth(this));
        spinnerPopWindow3D.setHeight(ScreenUtils.getScreenHeight(this)/6);
        spinnerPopWindow3D.setAdapter(dddTypeAdapter);
        //构建2D3D选项，固定
        dddTypeAdapter.addAll(made2D3DType());
        spinnerPopWindow3D.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideAll();
            }
        });

        mPresenter.getCooperativeList();
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
    private List<DDDTypeResponseBean.DDDType> made2D3DType(){
        List<DDDTypeResponseBean.DDDType> list = new ArrayList<>();

        DDDTypeResponseBean.DDDType dddTypeAll = new DDDTypeResponseBean.DDDType();
        dddTypeAll.setImage_type_name("全部");
        dddTypeAll.setImage_type("3");
        list.add(dddTypeAll);

        DDDTypeResponseBean.DDDType dddType2D = new DDDTypeResponseBean.DDDType();
        dddType2D.setImage_type_name("2D");
        dddType2D.setImage_type("0");
        list.add(dddType2D);

        DDDTypeResponseBean.DDDType dddType3D = new DDDTypeResponseBean.DDDType();
        dddType3D.setImage_type_name("3D");
        dddType3D.setImage_type("1");
        list.add(dddType3D);
        return list;
    }

    private void hideAll(){
        cbLeft.setChecked(false);
        cbMiddle.setChecked(false);
        cbRight.setChecked(false);
    }
    @Override
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public String getToken() {
        return SPUtils.getString(this,SpKey.TOKEN,"");
    }

    @Override
    public String getId() {
        return mCooperativeId;
    }

    @Override
    public void showAll(ArrayList<DDDTypeResponseBean.DDDType> typeList) {

    }

    private ArrayList<DDDTypeResponseBean.DDDType> mAllTypeList = new ArrayList<>();

    @Override
    public void showCooperativeList(ArrayList<CooperativeResponseBean.Cooperative> cooperativeList
    ,ArrayList<DDDTypeResponseBean.DDDType> allList) {
        if(allList != null){
           // show3D2DList(allList);
            mAllTypeList.addAll(allList);
        }
        //手动添加全部
        CooperativeResponseBean.Cooperative all = new CooperativeResponseBean.Cooperative();
        all.setSelected(false);
        all.setCooperative_code("0");
        all.setCooperative_name("全部");
        all.setId("0");
        if(cooperativeList == null){
            cooperativeList = new ArrayList<>();
        }
        cooperativeList.add(0,all);
        cooperativeAdapter.addAll(cooperativeList);
        //默认选择第一项
        chooseCooperative(0);
    }

    private void chooseCooperative(int position){
        CooperativeResponseBean.Cooperative cooperative = cooperativeAdapter.getItem(position);
        if (cooperative.isSelected()) {
            return;
        }
        for (CooperativeResponseBean.Cooperative bean : cooperativeAdapter.getAllData()) {
            bean.setSelected(false);
        }
        cooperative.setSelected(true);
        cbLeft.setText(cooperative.getCooperative_name());
        mCooperativeId = cooperative.getId();
        cooperativeAdapter.notifyDataSetChanged();
        if(position == 0){
            //全部就在本地取数据
            show3D2DList(mAllTypeList);
            showTagList(null);
        }else {
            cbMiddle.setVisibility(View.VISIBLE);
            mPresenter.getTagList();
        }
    }
    @Override
    public void showTagList(ArrayList<SceneLabel> labelList) {
        if(labelList == null) {
            labelList = new ArrayList<>();
        }
        if("0".equals(mCooperativeId)) {
            //全部
            cbMiddle.setVisibility(View.INVISIBLE);
        }else{
            cbMiddle.setVisibility(View.VISIBLE);
        }
        SceneLabel sceneLabel = new SceneLabel();
        sceneLabel.setId("0");
        sceneLabel.setSelected(false);
        sceneLabel.setLabel_name("全部");
        labelList.add(0,sceneLabel);
        tagAdapter.clear();
        tagAdapter.addAll(labelList);
        chooseTag(0);
    }

    private void chooseTag(int position){
        SceneLabel sceneLabel = tagAdapter.getItem(position);
//        if (sceneLabel.isSelected()) {
//            return;
//        }
        for (SceneLabel bean: tagAdapter.getAllData()) {
            bean.setSelected(false);
        }
        sceneLabel.setSelected(true);
        cbMiddle.setText(sceneLabel.getLabel_name());
        tagAdapter.notifyDataSetChanged();
        if(position != 0 ) {
            ArrayList<DDDTypeResponseBean.DDDType.DDD> list = sceneLabel.getSonList();
            if (list != null) {
                mAllList.clear();
                m2DList.clear();
                m3DList.clear();
                for (DDDTypeResponseBean.DDDType.DDD ddd : list) {
                    if ("0".equals(ddd.getImage_type())) {
                        m2DList.add(ddd);
                    }else if("1".equals(ddd.getImage_type())){
                        m3DList.add(ddd);
                    }
                    mAllList.add(ddd);
                }
            }
        }
        if(position == 0 && !mCurrentDDDTypeList.isEmpty()){
            mAllList.clear();
            m2DList.clear();
            m3DList.clear();
            for(DDDTypeResponseBean.DDDType type : mCurrentDDDTypeList){
                if("0".equals(type.getImage_type())){
                    m2DList.addAll(type.getSonList());
                }else if("1".equals(type.getImage_type())){
                    m3DList.addAll(type.getSonList());
                }
                mAllList.addAll(type.getSonList());
            }
        }
        choose3D2D(0);
    }

    ArrayList<DDDTypeResponseBean.DDDType> mCurrentDDDTypeList = new ArrayList<>();
    @Override
    public void show3D2DList(ArrayList<DDDTypeResponseBean.DDDType> list) {
        if(list == null){
            list = new ArrayList<>();
        }
        mCurrentDDDTypeList.clear();
        mCurrentDDDTypeList.addAll(list);
    }

    private void choose3D2D(int position){
        DDDTypeResponseBean.DDDType dddType = dddTypeAdapter.getItem(position);
        for(DDDTypeResponseBean.DDDType bean : dddTypeAdapter.getAllData()){
            bean.setSelected(false);
        }
        dddType.setSelected(true);
        cbRight.setText(dddType.getImage_type_name());
        dddTypeAdapter.notifyDataSetChanged();

        dddImageAdapter.clear();
        if(position == 0){
            //全部
            dddImageAdapter.addAll(mAllList);
        }else if(position == 1){
            dddImageAdapter.addAll(m2DList);
        }else{
            dddImageAdapter.addAll(m3DList);
        }
    }
    @OnCheckedChanged(R.id.cb_left)
    public void onCheckLeft(CompoundButton button, boolean isSelected){
        if(isSelected){
            spinnerPopWindowCooperative.showAsDropDown(button);
        }else {
            spinnerPopWindowCooperative.dismiss();
        }
    }
    @OnCheckedChanged(R.id.cb_middle)
    public void onCheckMiddle(CompoundButton button,boolean isSelected){
        if(isSelected){
            spinnerPopWindowTag.showAsDropDown(button);
        }else {
            spinnerPopWindowTag.dismiss();
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

    @OnClick(R.id.iv_back)
    public void onFinish(){
        finish();
    }
}
