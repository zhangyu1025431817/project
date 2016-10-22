package com.fangzhi.app.main.sell_part;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.SellType;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.main.adapter.HomeCategoryAdapter;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/10/21.
 */
public class SellPartActivity extends BaseActivity<SellPartPresenter, SellPartModel> implements SellPartContract.View {
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    DialogDelegate dialogDelegate;
    private HomeCategoryAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sell_part;
    }

    @Override
    public void initView() {
        tvTitle.setText("家装建材");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new HomeCategoryAdapter(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        dialogDelegate = new SweetAlertDialogDelegate(this);
        recyclerView.setAdapterWithProgress(mAdapter);
        recyclerView.setRefreshing(true);
        mPresenter.getSellCateGory();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getUserId() {
        return SPUtils.getString(this, SpKey.USER_ID, "");
    }

    @Override
    public void showCategoryList(List<SellType.Category> list) {
        int i = 0;
        for (SellType.Category category : list) {
            if (category.getIs_use() == 1) {
                i++;
            }
        }
        if (i == 1) {
            //直接跳转
            mAdapter.addAll(list);
            recyclerView.setRefreshing(false);
       //     finish();
        } else {
            mAdapter.addAll(list);
            recyclerView.setRefreshing(false);
        }
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(SellPartActivity.this, LoginActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(SellPartActivity.this, LoginActivityNew.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        finish();
    }
}
