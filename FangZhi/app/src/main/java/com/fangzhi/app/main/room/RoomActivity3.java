//package com.fangzhi.app.main.room;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.os.Handler;
//import android.support.v7.widget.LinearLayoutManager;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.fangzhi.app.R;
//import com.fangzhi.app.base.BaseActivity;
//import com.fangzhi.app.bean.Order;
//import com.fangzhi.app.bean.RoomProduct;
//import com.fangzhi.app.bean.RoomProductType;
//import com.fangzhi.app.bean.Scene;
//import com.fangzhi.app.config.SpKey;
//import com.fangzhi.app.download.DownLoadImageService;
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
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//
///**
// * Created by smacr on 2016/9/12.
// */
//public class RoomActivity3 extends BaseActivity<RoomPresenter, RoomModel> implements RoomContract.View
//        , RecyclerArrayAdapter.OnLoadMoreListener {
//
//    @Bind(R.id.layout_part)
//    LinearLayout layoutPart;
//    @Bind(R.id.gb_type)
//    MyRadioGroup radioGroup;
//    @Bind(R.id.recycler_view)
//    EasyRecyclerView recyclerView;
//
//    //当前图层urls
//    private Map<Integer, String> mapUrl = new TreeMap<>(new Comparator<Integer>() {
//        @Override
//        public int compare(Integer o1, Integer o2) {
//            return o1 - o2;
//        }
//    });
//    private Map<Integer, Order> productMap = new HashMap<>();
//    private PartAdapter mAdapter;
//    private List<RoomProductType> mList = new ArrayList<>();
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_room3;
//    }
//
//    String mHotTypeId;
//    String mSceneId;
//    String mHlCode;
//    private int mLastSelectPosition = -1;
//    DialogDelegate dialogDelegate;
//    private int mCurrentIndex = 0;//当前图层
//    /**
//     * 单线程列队执行
//     */
//    private static ExecutorService singleExecutor = null;
//    /**
//     * 多线程下载
//     */
//    private static ExecutorService cacheExecutor = null;
//
//    /**
//     * 执行单线程列队执行
//     */
//    public void runOnQueueSignle(Runnable runnable) {
//        if (singleExecutor == null) {
//            singleExecutor = Executors.newSingleThreadExecutor();
//
//        }
//        singleExecutor.submit(runnable);
//    }
//
//    /**
//     * 执行单线程列队执行
//     */
//    public void runOnQueueCache(Runnable runnable) {
//        if (cacheExecutor == null) {
//            cacheExecutor = Executors.newCachedThreadPool();
//
//        }
//        cacheExecutor.submit(runnable);
//    }
//
//    Handler handler = new Handler();
//    @Bind(R.id.iv_show)
//    ImageView ivShow;
//    Canvas canvas;
//    Bitmap resultBitmap;
//    int i = 0;//计数器
//    String bgUrl;
//    boolean isCanDraw = false;
//
//    /**
//     * 启动图片下载线程
//     */
//    private void onDownLoad(String url) {
//        DownLoadImageService service = new DownLoadImageService(
//                url,
//                new DownLoadImageService.ImageDownLoadCallBack() {
//
//                    @Override
//                    public void onDownLoadSuccess(final Bitmap bitmap) {
//                        i++;
//                        if (!isCanDraw) {//下载
//                            if (i == mapUrl.size()) {//所有下载完成
//                                isCanDraw = true;
//                                reDraw();
//                            }
//                        } else {//画图
//                            // 将bmp绘制在画布上
//                            Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 截取bmp1中的矩形区域
//                            Rect dstRect = new Rect(0, 0, ScreenUtils.getScreenWidth(RoomActivity3.this),
//                                    ScreenUtils.getScreenHeight(RoomActivity3.this));// bmp1在目标画布中的位置
//                            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
//                            if (i == mapUrl.size()) {
//                                // 在这里执行图片保存方法
//                                handler.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        ivShow.setImageBitmap(resultBitmap);
//                                        isCanDraw = false;
//                                    }
//                                });
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onDownLoadFailed() {
//                        i++;
//                        Log.e("onDownLoadSuccess", "onDownLoadFailed");
//                    }
//                });
//        if(isCanDraw) {
//            //画图
//            runOnQueueSignle(service);
//        }else{
//            //启动图片下载线程
//            runOnQueueCache(service);
//        }
//    }
//
//    @Override
//    public void initView() {
//        layoutPart.setVisibility(View.GONE);
//        Intent intent = getIntent();
//        bgUrl = intent.getStringExtra("bg");
//        //首先放置背景
//        mapUrl.put(0, bgUrl);
//        mHotTypeId = intent.getStringExtra("hotType");
//        mSceneId = intent.getStringExtra("sceneId");
//        mHlCode = intent.getStringExtra("hlCode");
//        List<Scene.Part> list = (List<Scene.Part>) intent.getSerializableExtra("parts");
//        //用于当背景的空bitmap
//        resultBitmap = Bitmap.createBitmap(ScreenUtils.getScreenWidth(this),
//                ScreenUtils.getScreenHeight(this), Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(resultBitmap);
//        for (Scene.Part part : list) {
//            mapUrl.put(part.getOrder_num(), part.getPart_img());
//            // onDownLoad(part.getPart_img());
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
//
//        reDraw();
//    }
//
//    /**
//     * 当前集合一修改就马上触发重绘制
//     *
//     * @param number
//     * @param url
//     * @param isCancel
//     */
//    private void change(int number, String url, boolean isCancel) {
//        if (isCancel) {
//            mapUrl.remove(number);
//        } else {
//            if (url.equals(mapUrl.get(number))) {
//                return;
//            }
//            mapUrl.put(number, url);
//        }
//        reDraw();
//    }
//
//    /**
//     * 重绘
//     */
//    private void reDraw() {
//        i = 0;
//        //清空画布
//        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//        for (Integer key : mapUrl.keySet()) {
//            onDownLoad(mapUrl.get(key));
//        }
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
//                if (product.isSelected()) {
//                    //清空对应图层
//                    change(mCurrentIndex, null, true);
//                    if (productMap.containsKey(mCurrentIndex)) {
//                        productMap.remove(mCurrentIndex);
//                    }
//                } else {
//                    //添加对应图层
//                    change(mCurrentIndex, product.getPart_img(), false);
//                    productToOrder(product);
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
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        dialogDelegate.clearDialog();
//        mapUrl = null;
//        productMap = null;
//        mAdapter = null;
//        mList = null;
//        resultBitmap.recycle();
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
//        mapUrl.clear();
//        productMap.clear();
//        mapUrl.put(0, bgUrl);
//        reDraw();
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
//                Intent intent = new Intent(RoomActivity3.this, LoginActivityNew.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(new Intent(RoomActivity3.this, LoginActivityNew.class));
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
