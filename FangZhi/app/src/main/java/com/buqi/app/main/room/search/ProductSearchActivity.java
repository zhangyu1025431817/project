package com.buqi.app.main.room.search;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.bean.RoomProduct;
import com.buqi.app.main.adapter.SearchPartAdapter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/11/1.
 */
public class ProductSearchActivity extends AppCompatActivity {
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    ArrayList<RoomProduct> partList;
    @Bind(R.id.recycler_view_product)
    EasyRecyclerView recyclerViewProduct;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    SearchPartAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_type);
        ButterKnife.bind(this);
        tvTitle.setText("搜索");

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        partList = (ArrayList<RoomProduct>) bundle.getSerializable("parts");
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new SearchPartAdapter(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                RoomProduct  roomProduct = (RoomProduct) mAdapter.getItem(position);
               int index =  partList.indexOf(roomProduct);
                Intent data = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("index",index);
                data.putExtras(bundle);
                setResult(1, data);
                finish();
            }
        });
        mAdapter.addAll(partList);
        recyclerViewProduct.setAdapter(mAdapter);
        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    tvCancel.setVisibility(View.VISIBLE);
                } else {
                    tvCancel.setVisibility(View.GONE);
                    closeKeyboard();
                }
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onFinish() {
        Intent data = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("index",-1);
        data.putExtras(bundle);
        setResult(1, data);
        finish();
    }
    @OnClick(R.id.tv_cancel)
    public void onClearEditText() {
        etKeyword.setText("");
        etKeyword.clearFocus();
        mAdapter.clear();
        mAdapter.addAll(partList);
        onWindowFocusChanged(true);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            if (!etKeyword.getText().toString().trim().isEmpty()) {
                mAdapter.clear();
                mAdapter.addAll(search(etKeyword.getText().toString().trim()));
                closeKeyboard();
                onWindowFocusChanged(true);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private ArrayList<RoomProduct> search(String key) {
        ArrayList<RoomProduct> list = new ArrayList<>();
        if (partList != null && !partList.isEmpty()) {
            for (RoomProduct bean : partList) {
                if (bean.getPart_name().contains(key)) {
                    list.add(bean);
                }
            }
        }
        return list;

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
}
