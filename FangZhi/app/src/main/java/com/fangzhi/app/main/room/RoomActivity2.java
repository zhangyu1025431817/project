//package com.fangzhi.app.main.room;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.support.v7.widget.LinearLayoutManager;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.SimpleTarget;
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
//public class RoomActivity extends BaseActivity<RoomPresenter, RoomModel> implements RoomContract.View
//        , RecyclerArrayAdapter.OnLoadMoreListener {
//    @Bind(R.id.layout_frame)
//    FrameLayout frameLayout;
//    @Bind(R.id.layout_part)
//    LinearLayout layoutPart;
//    @Bind(R.id.gb_type)
//    MyRadioGroup radioGroup;
//    @Bind(R.id.recycler_view)
//    EasyRecyclerView recyclerView;
//
//
//    //图层map
//    private Map<Integer, ImageView> map = new HashMap<>();
//    private Map<Integer, Order> productMap = new HashMap<>();
//    private PartAdapter mAdapter;
//    private List<RoomProductType> mList = new ArrayList<>();
//    private List<RoomProduct> mDataList = new ArrayList<>();
//    private boolean isShow = true;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_room;
//    }
//
//    String mHotTypeId;
//    String mSceneId;
//    String mHlCode;
//    private int mLastSelectPosition = -1;
//    DialogDelegate dialogDelegate;
//    private int mCurrentIndex = 0;//当前图层
//    private String mCurrentIndexName;//当前图层类别 第一次加载全部图层应该有个对应类别 点击的时候改变当前图层类别并存下来，
//
//    @Override
//    public void initView() {
//        layoutPart.setVisibility(View.GONE);
//        Intent intent = getIntent();
//        String bgUrl = intent.getStringExtra("bg");
//        mHotTypeId = intent.getStringExtra("hotType");
//        mSceneId = intent.getStringExtra("sceneId");
//        mHlCode = intent.getStringExtra("hlCode");
//        //添加背景
//       // add(0, bgUrl);
//        List<Scene.Part> list = (List<Scene.Part>) intent.getSerializableExtra("parts");
//        loadShelf(list);
//        for (Scene.Part part : list) {
//            add(part.getOrder_num(), part.getPart_img());
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
//    private void add(int number, String url) {
//        final ImageView iv = new ImageView(this);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        map.put(number, iv);
//        frameLayout.addView(iv, ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this));
//        Glide.with(this)
//                .load(url)
//                .asBitmap()
//                .dontAnimate()
//                .fitCenter()
//                .into(new SimpleTarget<Bitmap>(1000, 749) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                        Log.e("onResourceReady",resource.getRowBytes()*resource.getHeight()+"");
//                        iv.setImageBitmap(resource); // Possibly runOnUiThread()
//                    }
//                });
////        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
////                .setProgressiveRenderingEnabled(true)
////                .build();
////        DraweeController controller = Fresco.newDraweeControllerBuilder()
////                .setImageRequest(request)
////                .build();
////        Picasso.with(this)
////                .load(url)
////                .memoryPolicy(MemoryPolicy.NO_CACHE)
////                .fit()
////                .into(iv);
//    }
//
//    private void loadShelf(final List<Scene.Part> list) {
//        if (list.size() == 0) {
//            return;
//        }
//        Glide.with(this)
//                .load(list.get(0).getPart_img())
//                .asBitmap()
//                .dontAnimate()
//                .fitCenter()
//                .into(new SimpleTarget<Bitmap>(ScreenUtils.getScreenWidth(this), ScreenUtils.getScreenHeight(this)) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                        Log.e("onResourceReady", resource.getRowBytes() * resource.getHeight() + "");
//                        ImageView iv = new ImageView(RoomActivity.this);
//                        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//                        iv.setImageBitmap(resource); // Possibly runOnUiThread()
//                        frameLayout.addView(iv, ScreenUtils.getScreenWidth(RoomActivity.this),
//                                ScreenUtils.getScreenHeight(RoomActivity.this));
//                        list.remove(0);
//                        loadShelf(list);
//                    }
//                });
//    }
//
//    private void initRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mAdapter = new PartAdapter(this);
//        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (mLastSelectPosition != -1 && mLastSelectPosition != position) {
//                    RoomProduct lastProduct = mAdapter.getItem(mLastSelectPosition);
//                    lastProduct.setSelected(false);
//                }
//                RoomProduct product = mAdapter.getItem(position);
//
//                if (map.containsKey(mCurrentIndex)) {
//                    final ImageView iv = map.get(mCurrentIndex);
//                    if (product.isSelected()) {
//                        //清空对应图层
//                        iv.setVisibility(View.INVISIBLE);
//                        if (productMap.containsKey(mCurrentIndex)) {
//                            productMap.remove(mCurrentIndex);
//                        }
//                    } else {
//                        //设置对应图层
//                        iv.setVisibility(View.VISIBLE);
//                        Glide.with(RoomActivity.this)
//                                .load(product.getPart_img())
//                                .asBitmap()
//                                .centerCrop()
//                                .dontAnimate()
//                                .into(new SimpleTarget<Bitmap>(ScreenUtils.getScreenWidth(RoomActivity.this),
//                                        ScreenUtils.getScreenHeight(RoomActivity.this)) {
//                                    @Override
//                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                        iv.setImageBitmap(resource); // Possibly runOnUiThread()
//                                    }
//                                });
//                        productToOrder(product);
//                    }
//                }
//
//                product.setSelected(!product.isSelected());
//                mLastSelectPosition = position;
//                mAdapter.notifyDataSetChanged();
//            }
//        });
//        recyclerView.setAdapterWithProgress(mAdapter);
//        onRefresh();
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
//                mCurrentIndexName = roomProductType.getType_name();
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
//    public void onLoadMore() {
//        mPresenter.getRoomPartTypeList();
//    }
//
//    public void onRefresh() {
//        recyclerView.setRefreshing(true);
//        mAdapter.clear();
//        onLoadMore();
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
//        recyclerView.setRefreshing(false);
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
