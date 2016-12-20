package com.buqi.app.main.scenestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.base.BaseActivity;
import com.buqi.app.bean.Scene;
import com.buqi.app.bean.SceneStyleResponse;
import com.buqi.app.config.SpKey;
import com.buqi.app.main.adapter.NoDoubleClickListener;
import com.buqi.app.main.adapter.SceneStylePartAdapter;
import com.buqi.app.main.adapter.SceneStyleTypeAdapter;
import com.buqi.app.main.room.RoomActivity;
import com.buqi.app.tools.SPUtils;
import com.buqi.app.tools.T;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

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
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("parts", scene.getSonList());
                bundle.putString("bg", scene.getHl_img());
                bundle.putString("hotType", scene.getHot_type());
                bundle.putString("hlCode", scene.getHl_code());
                bundle.putString("sceneId", scene.getScene_id());
                bundle.putString("token", getToken());
                intent.putExtras(bundle);
                intent.setClass(SceneStyleActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });
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
    public void tokenInvalid(String msg) {

    }

    @Override
    public void onError(String msg) {
        T.showShort(this,msg);
    }
}
