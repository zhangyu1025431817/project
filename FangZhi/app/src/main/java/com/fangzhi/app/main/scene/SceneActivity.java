package com.fangzhi.app.main.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Scene;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.main.adapter.NoDoubleClickListener;
import com.fangzhi.app.main.adapter.SceneAdapter;
import com.fangzhi.app.main.room.RoomActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/22.
 */
public class SceneActivity extends BaseActivity<ScenePresenter, SceneModel> implements SceneContract.View, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;

    private SceneAdapter mAdapter;
    private String mHotType;
    private String mDecorateId;
    DialogDelegate dialogDelegate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scene_list;
    }

    @Override
    public void initView() {

        tvTitle.setText("选择场景");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mHotType = bundle.getString("type");
        mDecorateId = bundle.getString("decorateId", "");
        recyclerView.setRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new SceneAdapter(this);
        mAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                Scene scene = mAdapter.getItem(position);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("parts", scene.getSonList());
                //   intent.putExtra("parts", scene.getSonList());
                bundle.putString("bg", scene.getHl_img());
                bundle.putString("hotType", mHotType);
                bundle.putString("hlCode", scene.getHl_code());
                bundle.putString("sceneId", scene.getScene_id());
                bundle.putString("token", getToken());
                intent.putExtras(bundle);
                intent.setClass(SceneActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });
        dialogDelegate = new SweetAlertDialogDelegate(this);
        recyclerView.setAdapterWithProgress(mAdapter);
        onRefresh();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getHouseHotTypeId() {
        return mHotType;
    }

    @Override
    public String getDecorateId() {
        return mDecorateId;
    }

    @Override
    public String getUserId() {
        return SPUtils.getString(this, SpKey.USER_ID, "");
    }

    @Override
    public void showScene(List<Scene> list) {
        mAdapter.addAll(list);
        recyclerView.setRefreshing(false);
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        finish();
    }


    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        mAdapter.clear();
        mPresenter.getScenes();
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(SceneActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(SceneActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }
}
