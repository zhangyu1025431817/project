package com.fangzhi.app.main.window_type;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.WindowType;
import com.fangzhi.app.main.adapter.WindowTypeAdapter;
import com.fangzhi.app.main.scene.SceneActivity;
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
    WindowTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<WindowType> list = (ArrayList<WindowType>) bundle.getSerializable("window_types");
        final String hotType = bundle.getString("type");
        tvTitle.setText("窗型");
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new WindowTypeAdapter(this);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
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
        adapter.addAll(list);
        recyclerView.setAdapter(adapter);

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
