package com.buyiren.app.main.house.scene;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.buyiren.app.R;
import com.buyiren.app.base.BaseActivity;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.bean.Scene;
import com.buyiren.app.config.SpKey;
import com.buyiren.app.login.LoginActivity;
import com.buyiren.app.main.MainActivity;
import com.buyiren.app.main.adapter.NoDoubleClickListener;
import com.buyiren.app.main.adapter.SceneAdapter;
import com.buyiren.app.main.room.RoomActivity;
import com.buyiren.app.tools.SPUtils;
import com.buyiren.app.tools.T;
import com.buyiren.app.view.DialogDelegate;
import com.buyiren.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
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
    private String mHlCode;
    private String mSceneId;
    private String mBgHlImage;
    DialogDelegate dialogDelegate;
    private ArrayList<Scene.Part> mDefaultPart = new ArrayList<>();

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
                ArrayList<Scene.Part> sonList = scene.getSonList();
                if (sonList == null || sonList.isEmpty()) {
                    T.show(SceneActivity.this, "暂无场景", Toast.LENGTH_SHORT);
                } else {
                    mHlCode = scene.getHl_code();
                    mSceneId = scene.getScene_id();
                    mBgHlImage = scene.getHl_img();
                    mPresenter.getRoomPartTypeList();
                    dialogDelegate.showProgressDialog(false, "初始化场景...");
                    mDefaultPart.clear();
                    mDefaultPart.addAll(sonList);
                }
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
    public String getHotType() {
        return mHotType;
    }

    @Override
    public String getSceneId() {
        return mSceneId;
    }

    @Override
    public String getHlCode() {
        return mHlCode;
    }

    @Override
    public void showScene(List<Scene> list) {
        mAdapter.addAll(list);
        recyclerView.setRefreshing(false);
    }
    @OnClick(R.id.iv_home)
    public void onHome(){
        Intent intent = new Intent(SceneActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    @Override
    public void showRoomProductTypes(ArrayList<RoomProductType> list, int position) {
        if(list == null || list.isEmpty()){
            dialogDelegate.stopProgressWithFailed("初始化场景","场景数据为空!");
        }else {
            Intent intent = new Intent(this, RoomActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("parts", mDefaultPart);
            bundle.putSerializable("types", list);
            bundle.putString("bg",mBgHlImage);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            dialogDelegate.clearDialog();
            startActivity(intent);
        }
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
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }
}
