package com.buqi.app.main.decoration.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.base.BaseActivity;
import com.buqi.app.base.RxBus;
import com.buqi.app.bean.CategoryPart;
import com.buqi.app.bean.CategoryPartRoomBean;
import com.buqi.app.bean.RoomProductType;
import com.buqi.app.bean.Scene;
import com.buqi.app.config.SpKey;
import com.buqi.app.login.LoginActivity;
import com.buqi.app.main.adapter.HomeCategoryTypePartAdapter;
import com.buqi.app.main.adapter.NoDoubleClickListener;
import com.buqi.app.main.room.RoomActivity;
import com.buqi.app.tools.SPUtils;
import com.buqi.app.tools.T;
import com.buqi.app.view.DialogDelegate;
import com.buqi.app.view.SweetAlertDialogDelegate;
import com.jude.easyrecyclerview.EasyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by smacr on 2016/10/22.
 */
public class ProductActivity extends BaseActivity<ProductPresenter, ProductModel> implements ProductContract.View {

    @Bind(R.id.recycler_view_product)
    EasyRecyclerView recyclerViewProduct;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    DialogDelegate dialogDelegate;
    private String mCategoryId;
    private String mCurrentPartId;
    private String mCurrentTypeId;
    String mHotType;
    CategoryPart.HotType mCurrentCategoryType;
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
        tvTitle.setText("商品");

        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 4));
        homeCategoryTypePartAdapter = new HomeCategoryTypePartAdapter(this);
        homeCategoryTypePartAdapter.setOnItemClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(int position) {
                dialogDelegate.showProgressDialog(true, "初始化场景...");
                mCurrentPartId = homeCategoryTypePartAdapter.getItem(position).getId();
                mCurrentTypeId = String.valueOf(homeCategoryTypePartAdapter.getItem(position).getType_id());

                mPresenter.getScene();
//                if(mLastSelectPosition == position){
//                    return;
//                }
//                if(mLastSelectPosition != -1) {
//                    CategoryPart.Part lastPart = homeCategoryTypePartAdapter.getItem(mLastSelectPosition);
//                    lastPart.setSelected(false);
//                    homeCategoryTypePartAdapter.notifyItemChanged(mLastSelectPosition);
//                }
//
//                mLastSelectPosition = position;
//                CategoryPart.Part part =  homeCategoryTypePartAdapter.getItem(position);
//                part.setSelected(true);
//                homeCategoryTypePartAdapter.notifyItemChanged(position);
            }
        });
        recyclerViewProduct.setAdapterWithProgress(homeCategoryTypePartAdapter);

        mPresenter.getPartList();
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
        dialogDelegate = new SweetAlertDialogDelegate(this);

        RxBus.$().register("partId")
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mCurrentPartId = (String) o;
                        dialogDelegate.showProgressDialog(true, "初始化场景...");
                        mPresenter.getScene();
                    }
                });
    }

    @OnClick(R.id.tv_cancel)
    public void onClearEditText() {
        etKeyword.setText("");
        etKeyword.clearFocus();
        homeCategoryTypePartAdapter.clear();
        homeCategoryTypePartAdapter.addAll(mPartList);
        onWindowFocusChanged(true);
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

    private List<CategoryPart.Part> mPartList = new ArrayList<>();
    @Override
    public void showCategoryList(List<CategoryPart.HotType> list) {
        if (list != null && list.size() > 0) {
           for(CategoryPart.HotType hotType : list){
               List<CategoryPart.Part> partList = (List<CategoryPart.Part>) hotType.getSonList();
               if(partList != null && !partList.isEmpty()){
                   mPartList.addAll(partList);
               }
           }
        }
        homeCategoryTypePartAdapter.addAll(mPartList);
    }

    private void selectType(int position) {
//        List<CategoryPart.HotType> list = homeCategoryTypeAdapter.getAllData();
//        CategoryPart.HotType type = list.get(position);
//        type.setSelected(true);
//        homeCategoryTypeAdapter.notifyDataSetChanged();
//        mCurrentCategoryType = type;
//        List<CategoryPart.Part> partList = (List<CategoryPart.Part>) type.getSonList();
//        homeCategoryTypePartAdapter.clear();
//        homeCategoryTypePartAdapter.addAll(partList);
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
        bundle.putString("hotType", scene.getHot_type());
        bundle.putString("select_type_id", mCurrentTypeId);
        bundle.putString("select_product_id", mCurrentPartId);
        bundle.putString("hlCode", scene.getHl_code());
        bundle.putString("sceneId", scene.getScene_id());
        bundle.putString("token", getToken());
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
           String key = etKeyword.getText().toString().trim();
            if (!key.isEmpty()) {
              //  mPresenter.search();
                List<CategoryPart.Part> tempList = new ArrayList<>();
                for(CategoryPart.Part part : mPartList){
                    if(part.getPart_name().toLowerCase().contains(key.toLowerCase()
                    )){
                        tempList.add(part);
                    }
                }
                homeCategoryTypePartAdapter.clear();
                homeCategoryTypePartAdapter.addAll(tempList);
                closeKeyboard();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
