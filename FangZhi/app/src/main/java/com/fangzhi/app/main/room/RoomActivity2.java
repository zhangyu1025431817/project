//package com.fangzhi.app.main.room;
//
//import android.content.Intent;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.bumptech.glide.Glide;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.fangzhi.app.R;
//import com.fangzhi.app.base.BaseActivity;
//import com.fangzhi.app.bean.Order;
//import com.fangzhi.app.bean.RoomProduct;
//import com.fangzhi.app.bean.RoomProductType;
//import com.fangzhi.app.bean.Scene;
//import com.fangzhi.app.config.SpKey;
//import com.fangzhi.app.login.LoginActivityNew;
//import com.fangzhi.app.main.adapter.PartAdapter;
//import com.fangzhi.app.main.list.ListOrderActivity;
//import com.fangzhi.app.tools.SPUtils;
//import com.fangzhi.app.tools.ScreenUtils;
//import com.fangzhi.app.tools.T;
//import com.fangzhi.app.view.DialogDelegate;
//import com.fangzhi.app.view.SweetAlertDialogDelegate;
//import com.jude.easyrecyclerview.EasyRecyclerView;
//import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//
///**
// * Created by smacr on 2016/9/12.
// */
//public class RoomActivity extends BaseActivity<RoomPresenter, RoomModel> implements RoomContract.View {
//    @Bind(R.id.layout_frame)
//    RelativeLayout frameLayout;
//    @Bind(R.id.layout_part)
//    LinearLayout layoutPart;
//    @Bind(R.id.gb_type)
//    MyRadioGroup radioGroup;
//    @Bind(R.id.recycler_view)
//    EasyRecyclerView recyclerView;
//
//    //图层map
//    private Map<Integer, SimpleDraweeView > map = new HashMap<>();
//    //清单map
//    private Map<Integer, Order> productMap = new HashMap<>();
//    //控件
//    private PartAdapter mAdapter;
//    //控件种类
//    private List<RoomProductType> mList = new ArrayList<>();
//    //控件视图是否显示
//    private boolean isShow = true;
//    //热点类型
//    String mHotTypeId;
//    //场景id
//    String mSceneId;
//    //
//    String mHlCode;
//    //最后一次选择的控件种类
//    private int mLastSelectPosition = -1;
//    //等待框
//    DialogDelegate dialogDelegate;
//    //当前选中图层
//    private int mCurrentIndex = 0;
//    //屏幕宽度
//    private int mScreenWidth;
//    //屏幕高度
//    private int mScreenHeight;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_room;
//    }
//
//
//    @Override
//    public void initView() {
//        layoutPart.setVisibility(View.GONE);
//        Intent intent = getIntent();
//        String bgUrl = intent.getStringExtra("bg");
//        mHotTypeId = intent.getStringExtra("hotType");
//        mSceneId = intent.getStringExtra("sceneId");
//        mHlCode = intent.getStringExtra("hlCode");
//        mScreenWidth = ScreenUtils.getScreenWidth(this);
//        mScreenHeight = ScreenUtils.getScreenHeight(this);
//        //添加背景
//        addCoverage(0, bgUrl);
//        List<Scene.Part> list = (List<Scene.Part>) intent.getSerializableExtra("parts");
//        for (Scene.Part part : list) {
//            addCoverage(part.getOrder_num(), part.getPart_img());
//            Order order = new Order();
//            order.setPart_img_short(part.getPart_img_short());
//            order.setPart_brand(part.getPart_brand());
//            order.setType(part.getType_name());
//            order.setPart_code(part.getPart_name());
//            order.setPrice("0.0");
//            order.setCount("1");
//            order.setTotalMoney("0.0");
//            order.setPart_unit(part.getPart_unit());
//            productMap.put(part.getOrder_num(), order);
//        }
//        dialogDelegate = new SweetAlertDialogDelegate(this);
//        initRecyclerView();
//    }
//
//    /**
//     * 添加图层
//     *
//     * @param number 图层号
//     * @param url    图层url
//     */
//    private void addCoverage(int number, String url) {
//        SimpleDraweeView view = new SimpleDraweeView(this);
//        frameLayout.addView(view, ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
//        view.setImageURI(url);
////        ImageView iv = new ImageView(this);
////        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        map.put(number, view);
//
//      //  Glide.with(this).load(url).override(mScreenWidth, mScreenHeight).into(iv);
//    }
//
//    private void initRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new PartAdapter(this);
//        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (mLastSelectPosition != -1 && mLastSelectPosition != position) {
//                    //取消上一此选中状态
//                    RoomProduct lastProduct = mAdapter.getItem(mLastSelectPosition);
//                    lastProduct.setSelected(false);
//                }
//                RoomProduct product = mAdapter.getItem(position);
//                //定位到当前需要改变的图层
//                final ImageView iv = map.get(mCurrentIndex);
//                if (product.isSelected()) {
//                    //清空对应图层
//                    iv.setVisibility(View.INVISIBLE);
//                    productMap.remove(mCurrentIndex);
//                } else {
//                    //设置对应图层
//                    iv.setVisibility(View.VISIBLE);
//                    Glide.with(RoomActivity.this)
//                            .load(product.getPart_img())
//                            .override(mScreenWidth,mScreenHeight)
//                            .into(iv);
//                    productToOrder(product);
//                }
//
//                product.setSelected(!product.isSelected());
//                mLastSelectPosition = position;
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//        recyclerView.setAdapterWithProgress(mAdapter);
//        mPresenter.getRoomPartTypeList();
//    }
//
//    private void addPartType(List<RoomProductType> list) {
//        radioGroup.addList(list, new MyRadioGroup.OnCheckedListener() {
//            @Override
//            public void onChecked(RoomProductType roomProductType) {
//                if (mLastSelectPosition != -1) {
//                    mAdapter.getItem(mLastSelectPosition).setSelected(false);
//                }
//                mAdapter.clear();
//                mAdapter.addAll(roomProductType.getSonList());
//                mCurrentIndex = roomProductType.getOrder_num();
//                mLastSelectPosition = -1;
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dialogDelegate.clearDialog();
//        map = null;
//        productMap = null;
//        mAdapter = null;
//        mList = null;
//        System.gc();
//    }
//
//    @Override
//    public String getToken() {
//        return SPUtils.getString(this, SpKey.TOKEN, "");
//    }
//
//    @Override
//    public String getHotType() {
//        return mHotTypeId;
//    }
//
//    @Override
//    public String getUserId() {
//        return SPUtils.getString(this, SpKey.USER_ID, "");
//    }
//
//    @Override
//    public String getSceneId() {
//        return mSceneId;
//    }
//
//    @Override
//    public String getHlCode() {
//        return mHlCode;
//    }
//
//    @Override
//    public void showRoomProductTypes(List<RoomProductType> list) {
//        if (list != null && !list.isEmpty()) {
//            //初始化右侧控件类型
//            addPartType(list);
//            mList.addAll(list);
//        }
//    }
//
//    @OnClick(R.id.iv_home)
//    public void onHome() {
//        layoutPart.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
//        layoutPart.setVisibility(View.VISIBLE);
//    }
//
//    @OnClick(R.id.iv_eye)
//    public void onEye() {
//        // onHide();
//        for (int key : map.keySet()) {
//            if (key == 0) {
//                continue;
//            }
//            if (isShow) {
//                map.get(key).setVisibility(View.GONE);
//            } else {
//                map.get(key).setVisibility(View.VISIBLE);
//            }
//        }
//        isShow = !isShow;
//    }
//
//    @OnClick(R.id.iv_close)
//    public void onReturn() {
//        finish();
//    }
//
//    @OnClick({R.id.iv_cover, R.id.tv_hide})
//    public void onHide() {
//        if (layoutPart.getVisibility() == View.VISIBLE) {
//            layoutPart.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
//            layoutPart.setVisibility(View.GONE);
//        }
//
//    }
//
//    @OnClick(R.id.iv_calculate)
//    public void onShowOrder() {
//        ArrayList<Order> list = new ArrayList<>();
//        for (int key : productMap.keySet()) {
//            list.add(productMap.get(key));
//        }
//        Intent intent = new Intent();
//        intent.putExtra("list", list);
//        intent.setClass(this, ListOrderActivity.class);
//        startActivity(intent);
//    }
//
//    public void productToOrder(RoomProduct product) {
//        Order order = new Order();
//        order.setPart_img_short(product.getPart_img_short());
//        order.setPart_brand(product.getPart_brand());
//        order.setType(product.getType_name());
//        order.setPart_code(product.getPart_name());
//        order.setPrice("0.0");
//        order.setCount("1");
//        order.setTotalMoney("0.0");
//        order.setPart_unit(product.getPart_unit());
//        productMap.put(mCurrentIndex, order);
//    }
//
//    @Override
//    public void tokenInvalid(String msg) {
//        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
//            @Override
//            public void onClick() {
//                Intent intent = new Intent(RoomActivity.this, LoginActivityNew.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(new Intent(RoomActivity.this, LoginActivityNew.class));
//            }
//        });
//    }
//
//    @Override
//    public void onError(String msg) {
//        T.showShort(this, msg);
//    }
//
//}
