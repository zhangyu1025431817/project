package com.fangzhi.app.main.room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.fangzhi.app.R;
import com.fangzhi.app.base.BaseActivity;
import com.fangzhi.app.bean.Order;
import com.fangzhi.app.bean.RoomProduct;
import com.fangzhi.app.bean.RoomProductType;
import com.fangzhi.app.bean.Scene;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.download.DownLoadImageService;
import com.fangzhi.app.download.DrawImageService;
import com.fangzhi.app.login.LoginActivity;
import com.fangzhi.app.main.adapter.PartAdapter;
import com.fangzhi.app.main.list.ListOrderActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.fangzhi.app.view.loading.AVLoadingIndicatorView;
import com.fangzhi.app.view.loading.BallSpinFadeLoaderIndicator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by smacr on 2016/9/12.
 */
public class RoomActivity extends BaseActivity<RoomPresenter, RoomModel> implements RoomContract.View {

    //  @Bind(R.id.gb_type)
    MyRadioGroup radioGroup;
    //  @Bind(R.id.recycler_view)
    EasyRecyclerView recyclerView;
    @Bind(R.id.view_loading)
    View layoutLoading;
    @Bind(R.id.avi)
    AVLoadingIndicatorView aviLoading;

    @Bind(R.id.view_product)
    ProductView productView;


    //当前图层urls
    private Map<Integer, String> mapUrl = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });

    private Map<Integer, Order> productMap = new HashMap<>();
    private PartAdapter mAdapter;
    private List<RoomProductType> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_room3;
    }

    String mHotTypeId;
    String mSceneId;
    String mHlCode;
    private int mLastSelectPosition = -1;
    DialogDelegate dialogDelegate;
    private int mCurrentIndex = 0;//当前图层

    @Bind(R.id.iv_show)
    ImageView ivShow;
    String bgUrl;
    DownLoadImageService downLoadImageService;
    DrawImageService drawImageService;
    ArrayList<RoomProductType> partTypeList;
    //菜单栏显示位置
    private int position;
    private String mDefaultSelectTypeId;
    private String mDefaultSelectProductId;
    private String token;
    @Override
    public void initView() {
        //loading
        radioGroup = productView.getRadioGroup();
        recyclerView = productView.getEasyRecyclerView();
        BallSpinFadeLoaderIndicator indicator = new BallSpinFadeLoaderIndicator();
        aviLoading.setIndicator(indicator);
        productView.setVisibility(View.GONE);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bgUrl = bundle.getString("bg");
        //首先放置背景
        mapUrl.put(0, bgUrl);
        mHotTypeId = bundle.getString("hotType");
        mSceneId = bundle.getString("sceneId");
        mHlCode = bundle.getString("hlCode");
        position = bundle.getInt("position");
        token = bundle.getString("token");
        mDefaultSelectTypeId = bundle.getString("select_type_id");
        mDefaultSelectProductId = bundle.getString("select_product_id");
        List<Scene.Part> list = (List<Scene.Part>) bundle.getSerializable("parts");
        partTypeList = (ArrayList<RoomProductType>) bundle.getSerializable("types");
        //用于当背景的空bitmap

        for (Scene.Part part : list) {
            mapUrl.put(part.getOrder_num(), part.getPart_img());
            Order order = new Order();
            order.setPart_img_short(part.getPart_img_short());
            order.setPart_brand(part.getPart_brand());
            order.setType(part.getType_name());
            order.setPart_code(part.getPart_name());
            order.setPrice("0.0");
            order.setCount("1");
            order.setTotalMoney("0.0");
            order.setPart_unit(part.getPart_unit());
            productMap.put(part.getOrder_num(), order);
        }
        dialogDelegate = new SweetAlertDialogDelegate(this);
        initRecyclerView();

        downLoadImageService = new DownLoadImageService(mapUrl, this, new DownLoadImageService.OnDrawListener() {
            @Override
            public void onDrawSucceed(Bitmap bitmap) {
                if (ivShow == null) {
                    return;
                }
                ivShow.setImageBitmap(bitmap);
                //    ivShow.setImage(ImageSource.bitmap(bitmap));
                layoutLoading.setVisibility(View.INVISIBLE);
            }
        }, false);
    }

    /**
     * 当前集合一修改就马上触发重绘制
     *
     * @param number
     * @param url
     * @param isCancel
     */
    private void change(int number, String url, boolean isCancel) {
        layoutLoading.setVisibility(View.VISIBLE);
        downLoadImageService.drawOne(number, url, isCancel);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PartAdapter(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mLastSelectPosition != -1 && mLastSelectPosition != position) {
                    RoomProduct lastProduct = mAdapter.getItem(mLastSelectPosition);
                    lastProduct.setSelected(false);
                    mAdapter.notifyItemChanged(mLastSelectPosition);
                }
                RoomProduct product = mAdapter.getItem(position);

                if (product.isSelected()) {
                    //清空对应图层
                    change(mCurrentIndex, null, true);
                    mapUrl.remove(mCurrentIndex);
                    if (productMap.containsKey(mCurrentIndex)) {
                        productMap.remove(mCurrentIndex);
                    }
                } else {
                    //添加对应图层
                    change(mCurrentIndex, product.getPart_img(), false);
                    mapUrl.put(mCurrentIndex, product.getPart_img());
                    productToOrder(product);
                }

                product.setSelected(!product.isSelected());
                mLastSelectPosition = position;
                indexMap.put(mCurrentIndex, mLastSelectPosition);
                mAdapter.notifyItemChanged(position);
            }
        });
        recyclerView.setAdapterWithProgress(mAdapter);
        if(partTypeList == null || partTypeList.size() == 0){
            mPresenter.getRoomPartTypeList();
        }else{
            showRoomProductTypes(partTypeList,position);
        }

    }

    private Map<Integer, Integer> indexMap = new HashMap<>();

    private void addPartType(List<RoomProductType> list) {
        radioGroup.addList(mDefaultSelectTypeId,mDefaultSelectProductId,list,indexMap, new MyRadioGroup.OnCheckedListener() {
            @Override
            public void onChecked(RoomProductType roomProductType) {

                mAdapter.clear();
                List<RoomProduct> list = roomProductType.getSonList();
                mAdapter.addAll(list);
                mCurrentIndex = roomProductType.getOrder_num();
                mLastSelectPosition = indexMap.get(roomProductType.getOrder_num());
                recyclerView.scrollToPosition(mLastSelectPosition);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDelegate.clearDialog();
        mapUrl = null;
        productMap = null;
        mAdapter = null;
        mList = null;
        downLoadImageService = null;
        drawImageService = null;
        System.gc();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getHotType() {
        return mHotTypeId;
    }

    @Override
    public String getUserId() {
        return SPUtils.getString(this, SpKey.USER_ID, "");
    }

    @Override
    public String getSceneId() {
        return mSceneId;
    }

    @Override
    public String getHlCode() {
        return mHlCode;
    }

    @Override
    public void showRoomProductTypes(List<RoomProductType> list,int position) {
        if (list != null && !list.isEmpty()) {
            switch (position){
                case 0:
                    productView.changeRight();
                    break;
                case 1:
                    productView.changeBottom();
                    break;
                case 2:
                    productView.changeLeft();
                    break;
                case 3:
                    productView.changeTop();
                    break;
                default:
                    productView.changeRight();
                    break;
            }
            //初始化右侧控件类型
            addPartType(list);
            mList.addAll(list);
        }
    }

    @OnClick(R.id.iv_home)
    public void onHome() {
        //   productView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        productView.setVisibility(View.VISIBLE);
    }

    private boolean isClear = true;

    @OnClick(R.id.iv_eye)
    public void onEye() {
        //  productMap.clear();
        if (isClear) {
            downLoadImageService.clearAll();
        } else {
            layoutLoading.setVisibility(View.VISIBLE);
            downLoadImageService.drawAll(mapUrl);
        }
        isClear = !isClear;

//        Intent intent = new Intent(this,ZoomActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("map", (Serializable) mapUrl);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    @OnClick(R.id.iv_close)
    public void onReturn() {
        finish();
    }



    @OnClick(R.id.iv_calculate)
    public void onShowOrder() {
        ArrayList<Order> list = new ArrayList<>();
        for (int key : productMap.keySet()) {
            list.add(productMap.get(key));
        }
        Intent intent = new Intent();
        intent.putExtra("list", list);
        intent.setClass(this, ListOrderActivity.class);
        startActivity(intent);
    }

    public void productToOrder(RoomProduct product) {
        Order order = new Order();
        order.setPart_img_short(product.getPart_img_short());
        order.setPart_brand(product.getPart_brand());
        order.setType(product.getType_name());
        order.setPart_code(product.getPart_name());
        order.setPrice("0.0");
        order.setCount("1");
        order.setTotalMoney("0.0");
        order.setPart_unit(product.getPart_unit());
        productMap.put(mCurrentIndex, order);
    }

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(RoomActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(new Intent(RoomActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }


}
