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
import com.fangzhi.app.main.adapter.ProductTypeAdapter;
import com.fangzhi.app.main.list.ListOrderActivity;
import com.fangzhi.app.main.room.search.ProductSearchActivity;
import com.fangzhi.app.tools.SPUtils;
import com.fangzhi.app.tools.T;
import com.fangzhi.app.view.DialogDelegate;
import com.fangzhi.app.view.SweetAlertDialogDelegate;
import com.fangzhi.app.view.loading.AVLoadingIndicatorView;
import com.fangzhi.app.view.loading.BallSpinFadeLoaderIndicator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
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
    EasyRecyclerView typeRecyclerView;
    //  @Bind(R.id.recycler_view)
    EasyRecyclerView productRecyclerView;
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
    private PartAdapter partAdapter;
    private ProductTypeAdapter productTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_room3;
    }

    String mHotTypeId;
    String mSceneId;
    String mHlCode;
    DialogDelegate dialogDelegate;
    private int mCurrentIndex = 0;//当前图层

    @Bind(R.id.iv_show)
    ImageView ivShow;
    String bgUrl;
    DownLoadImageService downLoadImageService;
    DrawImageService drawImageService;

    //菜单栏显示位置
    private int position;
    private String mDefaultSelectTypeId;
    private String mDefaultSelectProductId;
    private String token;
    @Override
    public void initView() {
        //loading
        typeRecyclerView = productView.getTypeRecyclerView();
        productRecyclerView = productView.getProductRecyclerView();
        productView.getTvSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomProduct> list = (ArrayList<RoomProduct>) partAdapter.getAllData();
                Intent intent = new Intent(RoomActivity.this, ProductSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("parts",list);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
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
        mDefaultSelectTypeId = bundle.getString("select_type_id","-1");
        mDefaultSelectProductId = bundle.getString("select_product_id","-1");
        List<Scene.Part> list = (List<Scene.Part>) bundle.getSerializable("parts");
        ArrayList<RoomProductType> partTypeList = (ArrayList<RoomProductType>) bundle.getSerializable("types");
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
        initRecyclerView( partTypeList);

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

    private void initRecyclerView(ArrayList<RoomProductType> partTypeList) {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        partAdapter = new PartAdapter(this);
        partAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPart(position);
            }
        });
        productRecyclerView.setAdapterWithProgress(partAdapter);
        productTypeAdapter = new ProductTypeAdapter(this);
        productTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for(RoomProductType type : productTypeAdapter.getAllData()){
                    type.setSelected(false);
                }
                RoomProductType roomProductType = productTypeAdapter.getAllData().get(position);
                roomProductType.setSelected(true);

                partAdapter.clear();
                List<RoomProduct> list = (List<RoomProduct>) roomProductType.getSonList();
                partAdapter.addAll(list);
                mCurrentIndex = roomProductType.getOrder_num();
                int productPosition = indexMap.get(mCurrentIndex);
                productRecyclerView.scrollToPosition(productPosition);
                productTypeAdapter.notifyDataSetChanged();
            }
        });
        typeRecyclerView.setAdapter(productTypeAdapter);
        if(partTypeList == null || partTypeList.size() == 0){
            mPresenter.getRoomPartTypeList();
        }else{
            showRoomProductTypes(partTypeList,position);
        }

    }

    private void selectPart(int position){
        int lastPosition = indexMap.get(mCurrentIndex);
        if (lastPosition != position) {
            RoomProduct lastProduct = partAdapter.getItem(lastPosition);
            lastProduct.setSelected(false);
            partAdapter.notifyItemChanged(lastPosition);
        }
        RoomProduct product = partAdapter.getItem(position);

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
        indexMap.put(mCurrentIndex, position);
        partAdapter.notifyItemChanged(position);
        productRecyclerView.scrollToPosition(position);
    }

    private Map<Integer, Integer> indexMap = new HashMap<>();

    private void addPartType(List<RoomProductType> list) {
        boolean hasFind = false;
        //定位到当前选中项
        for(RoomProductType type : list){
            List<RoomProduct> sonList = (List<RoomProduct>) type.getSonList();
            if(sonList != null && !sonList.isEmpty()){
                //产品是在当前type下面的
                if(type.getType_id().equals(mDefaultSelectTypeId)){
                    type.setSelected(true);
                    mCurrentIndex = type.getOrder_num();
                    //默认选中
                    hasFind = true;
                    partAdapter.addAll(sonList);
                    for(int i =0;i<sonList.size();i++){
                        if(sonList.get(i).getId().equals(mDefaultSelectProductId)){
                            indexMap.put(type.getOrder_num(),i);
                            sonList.get(i).setSelected(true);
                            break;
                        }
                    }
                }else {
                    sonList.get(0).setSelected(true);
                    indexMap.put(type.getOrder_num(), 0);
                }
            }
        }

        if(!hasFind){//没找到
            partAdapter.addAll((Collection<? extends RoomProduct>) list.get(0).getSonList());
            list.get(0).setSelected(true);
            mCurrentIndex = list.get(0).getOrder_num();
        }
        productTypeAdapter.clear();
        productTypeAdapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogDelegate.clearDialog();
        mapUrl = null;
        productMap = null;
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt("index");
            if(position != -1) {
                selectPart(position);
            }
        }
    }
}
