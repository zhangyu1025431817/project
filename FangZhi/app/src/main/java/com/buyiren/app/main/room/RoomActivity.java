package com.buyiren.app.main.room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyiren.app.R;
import com.buyiren.app.bean.Order;
import com.buyiren.app.bean.RoomProduct;
import com.buyiren.app.bean.RoomProductType;
import com.buyiren.app.bean.Scene;
import com.buyiren.app.main.adapter.PartAdapter;
import com.buyiren.app.main.adapter.ProductTypeAdapter;
import com.buyiren.app.main.list.ListOrderActivity;
import com.buyiren.app.main.room.search.ProductSearchActivity;
import com.buyiren.app.view.DialogDelegate;
import com.buyiren.app.view.SweetAlertDialogDelegate;
import com.buyiren.app.view.loading.AVLoadingIndicatorView;
import com.buyiren.app.view.loading.BallSpinFadeLoaderIndicator;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by smacr on 216/9/12.
 */
public class RoomActivity extends AppCompatActivity {
    /**
     * 部件类型
     */
    EasyRecyclerView typeRecyclerView;
    /**
     * 部件
     */
    EasyRecyclerView productRecyclerView;
    /**
     * 产品详情
     */
    TextView tvProductDetail;
    @Bind(R.id.view_loading)
    View layoutLoading;
    @Bind(R.id.avi)
    AVLoadingIndicatorView aviLoading;
    @Bind(R.id.view_product)
    ProductView productView;
    @Bind(R.id.layout_product_detail)
    AutoRelativeLayout layoutProductDetail;
    @Bind(R.id.tv_name)
    TextView tvDetailName;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    List<ProductDetail> mListDetailUrls = new ArrayList<>();
    //当前图层urls
    private Map<Integer, String> mapUrl = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });

    private Map<Integer, Order> productMap = new HashMap<>();
    private Map<Integer, Integer> mapIdToOrder = new HashMap<>();
    private PartAdapter partAdapter;
    private ProductTypeAdapter productTypeAdapter;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room3);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initView() {
        typeRecyclerView = productView.getTypeRecyclerView();
        productRecyclerView = productView.getProductRecyclerView();
        tvProductDetail = productView.getTvProductDetail();
        productView.getTvSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomProduct> list = (ArrayList<RoomProduct>) partAdapter.getAllData();
                if (list == null || list.isEmpty()) {
                    return;
                }
                Intent intent = new Intent(RoomActivity.this, ProductSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("parts", list);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        tvProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutProductDetail.setVisibility(View.VISIBLE);
                mListDetailUrls.clear();
                int position = indexMap.get(mCurrentIndex);
                RoomProduct product = partAdapter.getItem(position);
                String[] urls = product.getPart_img_short().split(";");
                for (String url : urls) {
                    ProductDetail detail = new ProductDetail(product.getPart_name(), url);
                    mListDetailUrls.add(detail);
                }
                //去掉第一张展示图
                mListDetailUrls.remove(0);
                pagerAdapter.notifyDataSetChanged();
                tvDetailName.setText(mListDetailUrls.get(0).name +
                        "(1/" + mListDetailUrls.size() + ")");
            }
        });
        BallSpinFadeLoaderIndicator indicator = new BallSpinFadeLoaderIndicator();
        aviLoading.setIndicator(indicator);
        productView.setVisibility(View.GONE);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bgUrl = bundle.getString("bg");
        //首先放置背景
        mapUrl.put(0, bgUrl);
        //视图摆放位置
        position = bundle.getInt("position");
        //默认选中类型
        mDefaultSelectTypeId = bundle.getString("select_type_id", "-1");
        //默认选中部件
        mDefaultSelectProductId = bundle.getString("select_product_id", "-1");
        ArrayList<Scene.Part> list = (ArrayList<Scene.Part>) bundle.getSerializable("parts");
        ArrayList<RoomProductType> partTypeList = (ArrayList<RoomProductType>) bundle.getSerializable("types");
        if (list != null) {
            for (Scene.Part part : list) {
                mapUrl.put(part.getOrder_num(), part.getPart_img());
                mapIdToOrder.put(part.getType_id(), part.getOrder_num());
                partToOrder(part);
            }
        }
        //初始化的时候有窗帘和帘头、帘身、帘沙，则需要互斥选择
        //3-->窗帘 12-->帘头 13-->帘身 14-->帘沙
        if (mapIdToOrder.containsKey(3) && (mapIdToOrder.containsKey(12)
                || mapIdToOrder.containsKey(13)
                || mapIdToOrder.containsKey(14))) {
            if ("12".equals(mDefaultSelectTypeId) ||
                    "13".equals(mDefaultSelectTypeId) ||
                    "14".equals(mDefaultSelectTypeId)) {
                if (mapUrl.containsKey(mapIdToOrder.get(3))) {
                    mapUrl.put(mapIdToOrder.get(3), null);
                    productMap.put(mapIdToOrder.get(3), null);
                }
            } else {
                if (mapUrl.containsKey(mapIdToOrder.get(12))) {
                    mapUrl.put(mapIdToOrder.get(12), null);
                    productMap.put(mapIdToOrder.get(12), null);
                }
                if (mapUrl.containsKey(mapIdToOrder.get(13))) {
                    mapUrl.put(mapIdToOrder.get(13), null);
                    productMap.put(mapIdToOrder.get(13), null);
                }
                if (mapUrl.containsKey(mapIdToOrder.get(14))) {
                    mapUrl.put(mapIdToOrder.get(14), null);
                    productMap.put(mapIdToOrder.get(14), null);
                }
            }
        }

        dialogDelegate = new SweetAlertDialogDelegate(this);
        downLoadImageService = new DownLoadImageService(mapUrl, this, new DownLoadImageService.OnDrawListener() {
            @Override
            public void onDrawSucceed(Bitmap bitmap) {
                if (ivShow == null) {
                    return;
                }
                ivShow.setImageBitmap(bitmap);
                layoutLoading.setVisibility(View.INVISIBLE);
            }
        }, false);

        initRecyclerView(partTypeList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int index = position + 1;
                tvDetailName.setText(mListDetailUrls.get(position).name +
                        "(" + index + "/" + mListDetailUrls.size() + ")");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        productRecyclerView.setLayoutManager(linearLayoutManager);
        partAdapter = new PartAdapter(this);
        partAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPart(position,false);
            }
        });
        productRecyclerView.setAdapter(partAdapter);
        productTypeAdapter = new ProductTypeAdapter(this);
        productTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectType(position);
            }
        });
        typeRecyclerView.setAdapter(productTypeAdapter);
        showRoomProductTypes(partTypeList, position);
    }

    //帘头
    private Bitmap mBitmapWindow0ne;
    private Order orderWindowOne;
    //帘身
    private Bitmap mBitmapWindowTwo;
    private Order orderWindowTwo;
    //帘沙
    private Bitmap mBitmapWindowThree;
    private Order orderWindowThree;

    private void selectType(int position) {
        //修改选中状态
        List<RoomProductType> roomProductTypeList = productTypeAdapter.getAllData();
        for (RoomProductType type : roomProductTypeList) {
            type.setSelected(false);
        }
        RoomProductType mCurrentProductType = roomProductTypeList.get(position);
        mCurrentProductType.setSelected(true);
        //修改选中数据
        List<RoomProduct> list = (List<RoomProduct>) mCurrentProductType.getSonList();
        partAdapter.clear();
        partAdapter.addAll(list);
        mCurrentIndex = mCurrentProductType.getOrder_num();
        productTypeAdapter.notifyDataSetChanged();

        int partPosition = indexMap.get(mCurrentIndex);
        //切换就隐藏
        tvProductDetail.setVisibility(View.INVISIBLE);
        if(partPosition  != -1) {
            selectPart(partPosition, true);
        }
    }

    private void selectPart(int position,boolean isFromType) {
        int lastPosition = indexMap.get(mCurrentIndex);
        if(lastPosition == -1 && position == -1){
            return;
        }
        if (lastPosition != position && lastPosition != -1) {
            RoomProduct lastProduct = partAdapter.getItem(lastPosition);
            lastProduct.setSelected(false);
            partAdapter.notifyItemChanged(lastPosition);
        }
        RoomProduct product = partAdapter.getItem(position);

        /**添加 部件详情功能*/
        String partImage = product.getPart_img_short();
        String[] urls = partImage.split(";");
        if (urls.length > 1 && !product.isSelected()) {
            tvProductDetail.setVisibility(View.VISIBLE);
        } else {
            tvProductDetail.setVisibility(View.INVISIBLE);
        }
        if(isFromType && product.isSelected()){
            if(urls.length > 1){
                tvProductDetail.setVisibility(View.VISIBLE);
            }
            return;
        }
        /**添加窗帘、帘头、帘身、帘沙选中规则*/
        if (mapIdToOrder.containsKey(3) && (mapIdToOrder.containsKey(12)
                || mapIdToOrder.containsKey(13)
                || mapIdToOrder.containsKey(14))) {
            Map<Integer, Bitmap> bitmapMap = downLoadImageService.getBitmapMap();
            if (product.getType_id() == 3 && mapIdToOrder.containsKey(3)) {
                //选中窗帘 清除帘头、帘身、帘沙
                if (mapIdToOrder.containsKey(12)) {
                    int order = mapIdToOrder.get(12);
                    if (bitmapMap.containsKey(order)) {
                        mBitmapWindow0ne = bitmapMap.get(order);
                        orderWindowOne = productMap.get(order);
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
                if (mapIdToOrder.containsKey(13)) {
                    int order = mapIdToOrder.get(13);
                    if (bitmapMap.containsKey(order)) {
                        mBitmapWindowTwo = bitmapMap.get(order);
                        orderWindowTwo = productMap.get(order);
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
                if (mapIdToOrder.containsKey(14)) {
                    int order = mapIdToOrder.get(14);
                    if (bitmapMap.containsKey(order)) {
                        mBitmapWindowThree = bitmapMap.get(order);
                        orderWindowThree = productMap.get(order);
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
            } else if (product.getType_id() == 12 && mapIdToOrder.containsKey(12)) {
                // 帘头
                if (mapIdToOrder.containsKey(3)) {
                    int order = mapIdToOrder.get(3);
                    if (bitmapMap.containsKey(order)) {
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
                if (mapIdToOrder.containsKey(13)) {
                    int order = mapIdToOrder.get(13);
                    if (mBitmapWindowTwo != null) {
                        bitmapMap.put(order, mBitmapWindowTwo);
                    }
                    if (orderWindowTwo != null) {
                        productMap.put(order, orderWindowTwo);
                    }
                }
                if (mapIdToOrder.containsKey(14)) {
                    int order = mapIdToOrder.get(14);
                    if (mBitmapWindowThree != null) {
                        bitmapMap.put(order, mBitmapWindowThree);
                    }
                    if (orderWindowThree != null) {
                        productMap.put(order, orderWindowThree);
                    }
                }

            } else if (product.getType_id() == 13) {
                // 帘身
                if (mapIdToOrder.containsKey(3)) {
                    int order = mapIdToOrder.get(3);
                    if (bitmapMap.containsKey(order)) {
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
                if (mapIdToOrder.containsKey(12)) {
                    int order = mapIdToOrder.get(12);
                    if (mBitmapWindow0ne != null) {
                        bitmapMap.put(order, mBitmapWindow0ne);
                    }
                    if (orderWindowOne != null) {
                        productMap.put(order, orderWindowOne);
                    }
                }
                if (mapIdToOrder.containsKey(14)) {
                    int order = mapIdToOrder.get(14);
                    if (mBitmapWindowThree != null) {
                        bitmapMap.put(order, mBitmapWindowThree);
                    }
                    if (orderWindowThree != null) {
                        productMap.put(order, orderWindowThree);
                    }
                }
            } else if (product.getType_id() == 14) {
                // 帘纱
                if (mapIdToOrder.containsKey(3)) {
                    int order = mapIdToOrder.get(3);
                    if (bitmapMap.containsKey(order)) {
                        bitmapMap.remove(order);
                        productMap.remove(order);
                    }
                }
                if (mapIdToOrder.containsKey(12)) {
                    int order = mapIdToOrder.get(12);
                    if (mBitmapWindow0ne != null) {
                        bitmapMap.put(order, mBitmapWindow0ne);
                    }
                    if (orderWindowOne != null) {
                        productMap.put(order, orderWindowOne);
                    }
                }
                if (mapIdToOrder.containsKey(13)) {
                    int order = mapIdToOrder.get(13);
                    if (mBitmapWindowTwo != null) {
                        bitmapMap.put(order, mBitmapWindowTwo);
                    }
                    if (orderWindowTwo != null) {
                        productMap.put(order, orderWindowTwo);
                    }
                }
            }
        }
        /**************************************************************/
        if (product.isSelected()) {
            //清空对应图层
            change(mCurrentIndex, null, true);
            mapUrl.remove(mCurrentIndex);
            if (productMap.containsKey(mCurrentIndex)) {
                productMap.remove(mCurrentIndex);
            }
            if (product.getType_id() == 12) {
                mBitmapWindow0ne = null;
            } else if (product.getType_id() == 13) {
                mBitmapWindowTwo = null;
            } else if (product.getType_id() == 14) {
                mBitmapWindowThree = null;
            }
            indexMap.put(mCurrentIndex, -1);
        } else {
            //添加对应图层
            change(mCurrentIndex, product.getPart_img(), false);
            mapUrl.put(mCurrentIndex, product.getPart_img());
            productToOrder(product);
            indexMap.put(mCurrentIndex, position);
        }

        product.setSelected(!product.isSelected());
        partAdapter.notifyItemChanged(position);
        if (position > 7)
            productRecyclerView.scrollToPosition(position);
    }

    private Map<Integer, Integer> indexMap = new HashMap<>();

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

    public void showRoomProductTypes(List<RoomProductType> list, int position) {
        //展示位置
        switch (position) {
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
        if (list != null && !list.isEmpty()) {
            productTypeAdapter.clear();
            productTypeAdapter.addAll(list);
            int defaultTypePosition = 0;
            int defaultPartPosition = 0;
            for (int i = 0; i < list.size(); i++) {
                //第一次默认选中类型
                RoomProductType type = list.get(i);
                if (mDefaultSelectTypeId.equals(type.getType_id())) {
                    defaultTypePosition = i;
                    //默认选中部件
                    ArrayList<RoomProduct> sonList = (ArrayList<RoomProduct>) type.getSonList();
                    if (sonList != null && !sonList.isEmpty()) {
                        for (int j = 0; j < sonList.size(); j++) {
                            RoomProduct roomProduct = sonList.get(j);
                            if (mDefaultSelectProductId.equals(roomProduct.getId())) {
                                defaultPartPosition = j;
                                break;
                            }
                        }
                        indexMap.put(type.getOrder_num(), defaultPartPosition);
                        continue;
                    }
                }
                indexMap.put(type.getOrder_num(), 0);
            }
            selectType(defaultTypePosition);
        }
    }

    @OnClick(R.id.iv_home)
    public void onHome() {
        productView.setVisibility(View.VISIBLE);
    }

    private boolean isClear = true;

    @OnClick(R.id.iv_eye)
    public void onEye() {
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

    @OnClick(R.id.layout_product_detail)
    public void onLayoutClick() {

    }

    @OnClick(R.id.iv_frame_close)
    public void onFrameClose() {
        layoutProductDetail.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_calculate)
    public void onShowOrder() {
        ArrayList<Order> list = new ArrayList<>();
        for (int key : productMap.keySet()) {
            Order order = productMap.get(key);
            if (order != null) {
                list.add(order);
            }
        }
        Intent intent = new Intent();
        intent.putExtra("list", list);
        intent.setClass(this, ListOrderActivity.class);
        startActivity(intent);
    }

    /**
     * 选中产品转换为清单
     */
    public void productToOrder(RoomProduct product) {
        Order order = new Order();
        order.setId(product.getId());
        order.setPart_img_short(product.getPart_img_short());
        order.setPart_brand(product.getPart_brand());
        order.setPart_name(product.getPart_name());
        order.setType(product.getType_name());
        order.setPart_code(product.getPart_name());
        order.setPrice("");
        order.setCountNumber("");
        order.setTotalMoney("0.0");
        order.setPart_unit(product.getPart_unit());
        productMap.put(mCurrentIndex, order);
    }

    /**
     * 默认场景部件转换为清单
     */
    private void partToOrder(Scene.Part part) {
        Order order = new Order();
        order.setId(part.getId());
        order.setPart_img_short(part.getPart_img_short());
        order.setPart_brand(part.getPart_brand());
        order.setPart_name(part.getPart_name());
        order.setType(part.getType_name());
        order.setPart_code(part.getPart_name());
        order.setPrice("");
        order.setCountNumber("");
        order.setTotalMoney("0.0");
        order.setPart_unit(part.getPart_unit());
        productMap.put(part.getOrder_num(), order);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt("index");
            if (position != -1) {
                selectPart(position,false);
            }
        }
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

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mListDetailUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = mListDetailUrls.get(position).url;
            ImageView imageView = new ImageView(RoomActivity.this);
            Glide.with(RoomActivity.this)
                    .load(url)
                    .asBitmap()
                    .placeholder(R.drawable.bg_image_placeholder)
                    .fitCenter()
                    .into(imageView);
            container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }
    };

    class ProductDetail {
        String name;
        String url;

        ProductDetail(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }
}
