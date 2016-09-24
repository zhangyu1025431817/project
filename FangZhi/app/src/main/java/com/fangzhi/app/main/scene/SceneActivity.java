package com.fangzhi.app.main.scene;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Scene;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivityNew;
import com.fangzhi.app.main.adapter.SceneAdapter;
import com.fangzhi.app.main.room.RoomActivity;
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
 * Created by smacr on 2016/9/22.
 */
public class SceneActivity extends BaseActivity<ScenePresenter,SceneModel> implements SceneContract.View,
        RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    private SceneAdapter mAdapter;
    private String mHotType = "30011002";
    DialogDelegate dialogDelegate;
    @Override
    public int getLayoutId() {
        return R.layout.activity_scene_list;
    }

    @Override
    public void initView() {
        tvTitle.setText("选择场景");
        Intent intent = getIntent();
        if(intent.hasExtra("type")){
            mHotType = intent.getStringExtra("type");
        }
        recyclerView.setRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new SceneAdapter(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Scene scene = mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("parts", scene.getSonList());
                intent.putExtra("bg",scene.getHl_img());
                intent.putExtra("hotType",mHotType);
                intent.putExtra("sceneId",scene.getHl_code());
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
        return SPUtils.getString(this, SpKey.TOKEN,"");
    }

    @Override
    public String getHouseHotTypeId() {
        return mHotType;
    }

    @Override
    public String getUserId() {
        return SPUtils.getString(this,SpKey.USER_ID,"");
    }

    @Override
    public void showScene(List<Scene> list) {
        mAdapter.addAll(list);
        recyclerView.setRefreshing(false);
    }
    @OnClick(R.id.iv_back)
    public void onFinish(){
        finish();
    }

    @Override
    public void onLoadMore() {
        mPresenter.getScenes();
    }

    @Override
    public void onRefresh() {
        recyclerView.setRefreshing(true);
        mAdapter.clear();
        onLoadMore();
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(SceneActivity.this, LoginActivityNew.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(SceneActivity.this, LoginActivityNew.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this,msg);
    }
}
