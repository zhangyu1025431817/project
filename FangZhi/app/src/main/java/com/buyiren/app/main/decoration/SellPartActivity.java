package com.buyiren.app.main.decoration;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.buyiren.app.R;
import com.buyiren.app.base.BaseActivity;
import com.buyiren.app.bean.SellType;
import com.buyiren.app.config.SpKey;
import com.buyiren.app.login.LoginActivity;
import com.buyiren.app.main.adapter.HomeCategoryAdapter;
import com.buyiren.app.main.adapter.NoDoubleClickListener;
import com.buyiren.app.main.decoration.product.ProductActivity;
import com.buyiren.app.tools.SPUtils;
import com.buyiren.app.tools.T;
import com.buyiren.app.view.DialogDelegate;
import com.buyiren.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/10/21.家装建材
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
        mAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                SellType.Category category = mAdapter.getItem(position);
                if (category.getIs_use() == 1) {
                    Intent intent = new Intent(SellPartActivity.this, ProductActivity.class);
                    intent.putExtra("title", category.getCate_name());
                    intent.putExtra("categoryId", category.getId());
                    startActivity(intent);
                }
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
        SellType.Category onlyCategory = null;
        for (SellType.Category category : list) {
            if (category.getIs_use() == 1) {
                onlyCategory = category;
                i++;
            }
        }
        if (i == 1) {
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("title", onlyCategory.getCate_name());
            intent.putExtra("categoryId", onlyCategory.getId());
            startActivity(intent);
            //直接跳转
            mAdapter.addAll(list);
            finish();
        } else {
            mAdapter.addAll(list);
        }
        recyclerView.setRefreshing(false);
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(SellPartActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(String msg) {
        recyclerView.setRefreshing(false);
        T.showShort(this, msg);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        finish();
    }
}
