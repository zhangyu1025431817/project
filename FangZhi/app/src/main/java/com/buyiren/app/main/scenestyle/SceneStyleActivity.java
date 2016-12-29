package com.buyiren.app.main.scenestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.buyiren.app.R;
import com.buyiren.app.base.BaseActivity;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.bean.Scene;
import com.buyiren.app.bean.SceneStyleResponse;
import com.buyiren.app.config.SpKey;
import com.buyiren.app.main.adapter.NoDoubleClickListener;
import com.buyiren.app.main.adapter.SceneStylePartAdapter;
import com.buyiren.app.main.adapter.SceneStyleTypeAdapter;
import com.buyiren.app.main.room.RoomActivity;
import com.buyiren.app.tools.SPUtils;
import com.buyiren.app.tools.T;
import com.buyiren.app.view.DialogDelegate;
import com.buyiren.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/30.
 */
public class SceneStyleActivity extends BaseActivity<SceneStylePresenter,SceneStyleModel>
        implements SceneStyleContract.View{
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recycler_view_style_type)
    EasyRecyclerView recyclerViewStyleType;
    @Bind(R.id.recycler_view_style_part)
    EasyRecyclerView recyclerViewStylePart;
    SceneStyleTypeAdapter sceneStyleTypeAdapter;
    SceneStylePartAdapter sceneStylePartAdapter;
    private String mHotType;
    private String mDecorateId;
    private String mHlCode;
    private String mSceneId;
    private String mBgHlImage;
    DialogDelegate dialogDelegate;
    private ArrayList<Scene.Part> mDefaultPart = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_scene_style;
    }

    @Override
    public void initView() {
        //标题
        tvTitle.setText("场景风格");

        sceneStyleTypeAdapter = new SceneStyleTypeAdapter(this);
        sceneStyleTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                select(position);
            }
        });
        recyclerViewStyleType.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        recyclerViewStyleType.setAdapter(sceneStyleTypeAdapter);


        sceneStylePartAdapter = new SceneStylePartAdapter(this);
        sceneStylePartAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                Scene scene = sceneStylePartAdapter.getItem(position);
                ArrayList<Scene.Part> sonList = scene.getSonList();
                if (sonList == null || sonList.isEmpty()) {
                    T.show(SceneStyleActivity.this, "暂无场景", Toast.LENGTH_SHORT);
                } else {
                    mHotType = scene.getHot_type();
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
        recyclerViewStylePart.setLayoutManager(new GridLayoutManager(this,3));
        recyclerViewStylePart.setAdapterWithProgress(sceneStylePartAdapter);
        mPresenter.getStyleScenes();
    }

    private void select(int position){
        List<SceneStyleResponse.SceneStyle> list= sceneStyleTypeAdapter.getAllData();
        for(SceneStyleResponse.SceneStyle sceneStyle : list){
            sceneStyle.setSelected(false);
        }
        list.get(position).setSelected(true);
        sceneStyleTypeAdapter.notifyDataSetChanged();

        SceneStyleResponse.SceneStyle sceneStyle = sceneStyleTypeAdapter.getItem(position);
        sceneStylePartAdapter.clear();
        sceneStylePartAdapter.addAll(sceneStyle.getSceneList());
    }

    @OnClick(R.id.iv_back)
    public void onFinish(){
        finish();
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN,"");
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
    public void showScene(List<SceneStyleResponse.SceneStyle> styleList) {
        if(styleList == null || styleList.isEmpty()){
            onError("暂无场景");
        }else {
            SceneStyleResponse.SceneStyle sceneStyle = styleList.get(styleList.size() -1);
            styleList.remove(sceneStyle);
            styleList.add(0,sceneStyle);
            sceneStyleTypeAdapter.addAll(styleList);
            select(0);
        }
    }

    @Override
    public void showRoomProductTypes(ArrayList<RoomProductType> list, int position) {
        if(list == null || list.isEmpty()){
            dialogDelegate.stopProgressWithFailed("初始化场景","场景数据为空!");
        }else {
            Intent intent = new Intent(this,RoomActivity.class);
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

    @Override
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {
        T.showShort(this,msg);
    }
}
