package com.fangzhi.app.main.parent;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.bean.LoginNewBean;
import com.fangzhi.app.config.FactoryListInfo;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.main.MainActivity;
import com.fangzhi.app.main.custom.CustomActivity;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.Network;
import com.fangzhi.app.network.http.api.ErrorCode;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/10/26.
 */
public class ParentActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    EasyRecyclerView easyRecyclerView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    ParentAdapter mAdapter;
    DialogDelegate dialogDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_parent);
        ButterKnife.bind(this);

        mAdapter = new ParentAdapter(this);
        mAdapter.addAll(FactoryListInfo.parentList);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (LoginNewBean.Parent parent : mAdapter.getAllData()) {
                    parent.setSelected(false);
                }
                mAdapter.getItem(position).setSelected(true);
                mAdapter.notifyDataSetChanged();
                dialogDelegate.showProgressDialog(true,"正在提交...");
                loginParent(mAdapter.getItem(position).getID());
            }
        });
        tvTitle.setText("厂商选择");
        easyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        easyRecyclerView.setAdapter(mAdapter);
        dialogDelegate = new SweetAlertDialogDelegate(this);
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        finish();
    }

    private void loginParent(String parentId) {
        String token = SPUtils.getString(this, SpKey.TOKEN, "");
        String userId = SPUtils.getString(this, SpKey.USER_ID, "");
        Network.getApiService().loginParent(token, parentId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            SPUtils.put(ParentActivity.this,SpKey.TOKEN,loginBean.getToken());
                            dialogDelegate.clearDialog();
                            Intent intent = new Intent();
                            if(loginBean.getImg() == null || loginBean.getImg().isEmpty()){
                                intent.setClass(ParentActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            }else{
                                intent.putExtra("url", loginBean.getImg());
                                intent.setClass(ParentActivity.this, CustomActivity.class);
                            }
                            startActivity(intent);
                            ParentActivity.this.finish();
                        } else {
                            dialogDelegate.stopProgressWithFailed("提交失败",loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogDelegate.stopProgressWithFailed("提交失败","服务器连接失败！");
                    }
                });
    }
}
