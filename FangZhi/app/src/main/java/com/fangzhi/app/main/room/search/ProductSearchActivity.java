package com.fangzhi.app.main.room.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fangzhi.app.R;
import com.fangzhi.app.bean.RoomProductType;
import com.fangzhi.app.view.ClearEditText;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/1.
 */
public class ProductSearchActivity extends AppCompatActivity{
    @Bind(R.id.et_keyword)
    ClearEditText etKeyword;
    ArrayList<RoomProductType> partTypeList;
    @Bind(R.id.recycler_view_type)
    EasyRecyclerView recyclerViewType;
    @Bind(R.id.recycler_view_product)
    EasyRecyclerView recyclerViewProduct;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_type);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        partTypeList = (ArrayList<RoomProductType>) bundle.getSerializable("types");

    }

    @OnClick(R.id.iv_back)
    public void onFinish(){
        finish();
    }
}
