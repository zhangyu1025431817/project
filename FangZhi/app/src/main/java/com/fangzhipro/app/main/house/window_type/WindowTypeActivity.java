package com.fangzhipro.app.main.house.window_type;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fangzhipro.app.R;
import com.fangzhipro.app.bean.HouseTypeDetails;
import com.fangzhipro.app.bean.WindowType;
import com.fangzhipro.app.main.adapter.NoDoubleClickListener;
import com.fangzhipro.app.main.adapter.WindowHotTypeAdapter;
import com.fangzhipro.app.main.adapter.WindowTypeAdapter;
import com.fangzhipro.app.main.house.scene.SceneActivity;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/2.
 */
public class WindowTypeActivity extends AppCompatActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.recycler_view_type)
    EasyRecyclerView recyclerViewType;
    WindowTypeAdapter adapter;
    WindowHotTypeAdapter windowHotTypeAdapter;
    String hotType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_type);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<HouseTypeDetails.HouseTypeDetail> list = (ArrayList<HouseTypeDetails.HouseTypeDetail>) bundle.getSerializable("window_types");
        hotType = bundle.getString("type");
        tvTitle.setText("窗型");
        recyclerViewType.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        windowHotTypeAdapter = new WindowHotTypeAdapter(this);
        windowHotTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                HouseTypeDetails.HouseTypeDetail data =  windowHotTypeAdapter.getItem(position);
                if(data.isSelected()){
                    return;
                }
                for(HouseTypeDetails.HouseTypeDetail bean : windowHotTypeAdapter.getAllData()){
                    bean.setSelected(false);
                }
                selectHotType(position);
                windowHotTypeAdapter.notifyDataSetChanged();
            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new WindowTypeAdapter(this);
        adapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                WindowType windowType = adapter.getItem(position);
                if (windowType.getIs_use() == 1) {
                    String decorateId = adapter.getItem(position).getDecorate_id();
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("type", hotType);
                    bundle.putString("decorateId", decorateId);
                    intent.putExtras(bundle);
                    intent.setClass(WindowTypeActivity.this, SceneActivity.class);
                    startActivity(intent);
                }
            }
        });
        //  adapter.addAll(list);
        windowHotTypeAdapter.addAll(list);
        recyclerViewType.setAdapter(windowHotTypeAdapter);
        recyclerView.setAdapter(adapter);
        if(list == null){
            return;
        }
        for(int i =0;i<list.size();i++){
            if(hotType.equals(list.get(i).getHot_type())){
                selectHotType(i);
                break;
            }
        }
    }

    private void selectHotType(int position){
        HouseTypeDetails.HouseTypeDetail houseTypeDetail =  windowHotTypeAdapter.getItem(position);
        houseTypeDetail.setSelected(true);
        hotType = houseTypeDetail.getHot_type();
        adapter.clear();
        adapter.addAll(houseTypeDetail.getSonList());
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        finish();
    }
}
