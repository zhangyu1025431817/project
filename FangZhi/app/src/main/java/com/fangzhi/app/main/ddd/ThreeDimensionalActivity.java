package com.fangzhi.app.main.ddd;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.fangzhi.app.tools.T;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/15.
 */
public class ThreeDimensionalActivity extends BaseActivity<ThreeDimensionalPresenter, ThreeDimensionalModel>
        implements ThreeDimensionalContract.View {
    @Bind(R.id.recycler_view_type01)
    EasyRecyclerView recyclerViewTypeTop;
    @Bind(R.id.recycler_view_type02)
    EasyRecyclerView recyclerViewTypeMiddle;
    @Bind(R.id.recycler_view_type03)
    EasyRecyclerView recyclerViewTypeBottom;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    ThreeDimensionFitmentAdapter threeDimensionFitmentAdapter;
    DDDTypeAdapter dddTypeAdapter;
    DDDImageAdapter dddImageAdapter;
    private int mCaseTypeId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_three_dimensional;
    }

    @Override
    public void initView() {
        tvTitle.setText("3D场景");
        recyclerViewTypeTop.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        threeDimensionFitmentAdapter = new ThreeDimensionFitmentAdapter(this);
        threeDimensionFitmentAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                chooseThreeDimensionFitment(position);
            }
        });
        recyclerViewTypeTop.setAdapter(threeDimensionFitmentAdapter);
        mPresenter.getCaseTypeList();

        recyclerViewTypeMiddle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        dddTypeAdapter = new DDDTypeAdapter(this);
        dddTypeAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                chooseDDDType(position);
            }
        });
        recyclerViewTypeMiddle.setAdapter(dddTypeAdapter);

        recyclerViewTypeBottom.setLayoutManager(new GridLayoutManager(this, 3));
        dddImageAdapter = new DDDImageAdapter(this);
        dddImageAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                DDDTypeResponseBean.DDDType.DDD ddd = dddImageAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("url", ddd.getCase_url());
                intent.putExtra("name", ddd.getCase_name());
                if ("1".equals(ddd.getImage_type())) {
                    intent.setClass(ThreeDimensionalActivity.this, DDDWebView.class);
                } else {
                    intent.setClass(ThreeDimensionalActivity.this, DDView.class);
                }
                startActivity(intent);

            }
        });
        recyclerViewTypeBottom.setAdapter(dddImageAdapter);
    }

    @Override
    public void tokenInvalid(String msg) {

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
        dddTypeAdapter.clear();
        dddTypeAdapter.addAll(caseList);
        if (caseList != null && !caseList.isEmpty()) {
            chooseDDDType(0);
        }
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
        dddTypeAdapter.notifyDataSetChanged();
        dddImageAdapter.clear();
        dddImageAdapter.addAll(dddType.getSonList());
    }
}
