package com.fangzhi.app.main.sell_part.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.CategoryPart;
import com.fangzhi.app.bean.CategoryPartRoomBean;
import com.fangzhi.app.bean.RoomProductType;
import com.fangzhi.app.bean.Scene;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.main.adapter.HomeCategoryTypeAdapter;
import com.fangzhi.app.main.adapter.HomeCategoryTypePartAdapter;
import com.fangzhi.app.main.adapter.NoDoubleClickListener;
import com.fangzhi.app.main.room.RoomActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.ClearEditText;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/10/22.
 */
public class ProductActivity extends BaseActivity<ProductPresenter, ProductModel> implements ProductContract.View {
    @Bind(R.id.recycler_view_type)
    EasyRecyclerView recyclerViewType;
    @Bind(R.id.recycler_view_product)
    EasyRecyclerView recyclerViewProduct;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_keyword)
    ClearEditText etKeyword;
    DialogDelegate dialogDelegate;
    private String mCategoryId;
    private String mCurrentPartId;
    private String mCurrentTypeId;
    CategoryPart.HotType mCurrentCategoryType;
    HomeCategoryTypeAdapter homeCategoryTypeAdapter;
    HomeCategoryTypePartAdapter homeCategoryTypePartAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_type;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mCategoryId = intent.getStringExtra("categoryId");
        String title = intent.getStringExtra("title");
        tvTitle.setText(title);

        recyclerViewType.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryTypeAdapter = new HomeCategoryTypeAdapter(this);
        homeCategoryTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CategoryPart.HotType data = homeCategoryTypeAdapter.getItem(position);
                if (data.isSelected()) {
                    return;
                }
                for (CategoryPart.HotType bean : homeCategoryTypeAdapter.getAllData()) {
                    bean.setSelected(false);
                }
                selectType(position);
            }
        });
        recyclerViewType.setAdapterWithProgress(homeCategoryTypeAdapter);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 4));
        homeCategoryTypePartAdapter = new HomeCategoryTypePartAdapter(this);
        homeCategoryTypePartAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                dialogDelegate.showProgressDialog(true, "初始化场景...");
                mCurrentPartId = homeCategoryTypePartAdapter.getItem(position).getId();
                mCurrentTypeId = String.valueOf(homeCategoryTypePartAdapter.getItem(position).getType_id());
                mPresenter.getScene();
            }
        });
        recyclerViewProduct.setAdapterWithProgress(homeCategoryTypePartAdapter);

        mPresenter.getPartList();
        etKeyword.addOnClearListener(new ClearEditText.OnClearListener() {
            @Override
            public void onClear() {
                if (mCurrentCategoryType != null) {
                    homeCategoryTypePartAdapter.clear();
                    homeCategoryTypePartAdapter.addAll(mCurrentCategoryType.getSonList());
                }
                closeKeyboard();
            }
        });
        dialogDelegate = new SweetAlertDialogDelegate(this);
    }

    @Override
    public String getToken() {
        return SPUtils.getString(this, SpKey.TOKEN, "");
    }

    @Override
    public String getUserId() {
        return SPUtils.getString(this, SpKey.USER_ID, "");
    }

    @Override
    public String getCategoryId() {
        return mCategoryId;
    }

    @Override
    public String getKeyword() {
        return etKeyword.getText().toString().trim();
    }

    @Override
    public String getTypeId() {
        if (mCurrentCategoryType != null) {
            return mCurrentCategoryType.getCode_id();
        } else {
            return "";
        }
    }

    @Override
    public String getPartId() {
        return mCurrentPartId;
    }

    @Override
    public void showCategoryList(List<CategoryPart.HotType> list) {

        if (list != null && list.size() > 0) {
            homeCategoryTypeAdapter.addAll(list);
            selectType(0);
        }

    }

    private void selectType(int position) {
        List<CategoryPart.HotType> list = homeCategoryTypeAdapter.getAllData();
        CategoryPart.HotType type = list.get(position);
        type.setSelected(true);
        homeCategoryTypeAdapter.notifyDataSetChanged();
        mCurrentCategoryType = type;
        List<CategoryPart.Part> partList = (List<CategoryPart.Part>) type.getSonList();
        homeCategoryTypePartAdapter.clear();
        homeCategoryTypePartAdapter.addAll(partList);
    }

    @Override
    public void showCategoryPartList(List<CategoryPart.Part> list) {
        homeCategoryTypePartAdapter.clear();
        homeCategoryTypePartAdapter.addAll(list);
    }

    @Override
    public void showSceneSucceed(CategoryPartRoomBean categoryPartRoomBean) {
        List<Scene> sceneList = categoryPartRoomBean.getSceneList();
        ArrayList<RoomProductType> partTypeList = categoryPartRoomBean.getPartTypeList();
        if (sceneList == null || sceneList.size() == 0) {
            dialogDelegate.stopProgressWithFailed("场景数据为空", "场景数据为空");
            return;
        }
        Scene scene = sceneList.get(0);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("parts", scene.getSonList());
        bundle.putSerializable("types", partTypeList);
        bundle.putInt("position", categoryPartRoomBean.getPosition());
        bundle.putString("bg", scene.getHl_img());
        bundle.putString("hotType", "");
        bundle.putString("select_type_id", mCurrentTypeId);
        bundle.putString("select_product_id", mCurrentPartId);
        bundle.putString("hlCode", scene.getHl_code());
        bundle.putString("sceneId", scene.getScene_id());
        intent.putExtras(bundle);
        intent.setClass(this, RoomActivity.class);
        dialogDelegate.clearDialog();
        startActivity(intent);
    }

    @Override
    public void showSceneFailed(String msg) {
        dialogDelegate.stopProgressWithFailed(msg, msg);
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(ProductActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }

    @OnClick(R.id.iv_back)
    public void onFinish() {
        this.finish();
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            if (!etKeyword.getText().toString().trim().isEmpty()) {
                mPresenter.search();
                closeKeyboard();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
