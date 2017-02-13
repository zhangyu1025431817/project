package com.buqi.app.main.room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.buqi.app.R;
import com.buqi.app.base.BaseActivity;
import com.buqi.app.bean.Order;
import com.buqi.app.bean.RoomProduct;
import com.buqi.app.bean.RoomProductType;
import com.buqi.app.bean.Scene;
import com.buqi.app.config.SpKey;
import com.buqi.app.download.DownLoadImageService;
import com.buqi.app.download.DrawImageService;
import com.buqi.app.login.LoginActivity;
import com.buqi.app.main.adapter.PartAdapter;
import com.buqi.app.main.adapter.ProductTypeAdapter;
import com.buqi.app.main.list.ListOrderActivity;
import com.buqi.app.main.room.search.ProductSearchActivity;
import com.buqi.app.tools.SPUtils;
import com.buqi.app.tools.T;
import com.buqi.app.view.DialogDelegate;
import com.buqi.app.view.SweetAlertDialogDelegate;
import com.buqi.app.view.loading.AVLoadingIndicatorView;
import com.buqi.app.view.loading.BallSpinFadeLoaderIndicator;
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
    private Map<Integer, Integer> mapIdToOrder = new HashMap<>();
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
        //该功能与popWindow弹出背景半透明冲突
        setSwipeBackEnable(false);
        //loading
        typeRecyclerView = productView.getTypeRecyclerView();
        productRecyclerView = productView.getProductRecyclerView();
        productView.getTvSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RoomProduct> list = (ArrayList<RoomProduct>) partAdapter.getAllData();
                if(list == null || list.isEmpty()){
                    return;
                }
                Intent intent = new Intent(RoomActivity.this, ProductSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("parts", list);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
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
        mDefaultSelectTypeId = bundle.getString("select_type_id", "-1");
        mDefaultSelectProductId = bundle.getString("select_product_id", "-1");
        List<Scene.Part> list = (List<Scene.Part>) bundle.getSerializable("parts");
        ArrayList<RoomProductType> partTypeList = (ArrayList<RoomProductType>) bundle.getSerializable("types");
        //用于当背景的空bitmap
        if (list != null) {
            for (Scene.Part part : list) {
                mapUrl.put(part.getOrder_num(), part.getPart_img());
                mapIdToOrder.put(part.getType_id(), part.getOrder_num());
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
        }

        if(mapIdToOrder.containsKey(3) && (mapIdToOrder.containsKey(12)
                || mapIdToOrder.containsKey(13)
                || mapIdToOrder.containsKey(14))){
            if("12".equals(mDefaultSelectTypeId) ||
                    "13".equals(mDefaultSelectTypeId)||
                    "14".equals(mDefaultSelectTypeId)){
                if(mapUrl.containsKey(mapIdToOrder.get(3))){
                    mapUrl.put(mapIdToOrder.get(3),null);
                    productMap.put(mapIdToOrder.get(3),null);
                }
            }else {
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
        initRecyclerView(partTypeList);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        productRecyclerView.setLayoutManager(linearLayoutManager);
        partAdapter = new PartAdapter(this);
        partAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectPart(position);
            }
        });
        productRecyclerView.setAdapter(partAdapter);
        productTypeAdapter = new ProductTypeAdapter(this);
        productTypeAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (RoomProductType type : productTypeAdapter.getAllData()) {
                    type.setSelected(false);
                }
                RoomProductType roomProductType = productTypeAdapter.getAllData().get(position);
                roomProductType.setSelected(true);
                List<RoomProduct> list = (List<RoomProduct>) roomProductType.getSonList();
                partAdapter.clear();
                partAdapter.addAll(list);
                mCurrentIndex = roomProductType.getOrder_num();
                productTypeAdapter.notifyDataSetChanged();
                int selectPosition = indexMap.get(mCurrentIndex);
                if (selectPosition > 7) {
                    productRecyclerView.scrollToPosition(selectPosition);
                }
            }
        });
        typeRecyclerView.setAdapter(productTypeAdapter);
        if (partTypeList == null || partTypeList.size() == 0) {
            mPresenter.getRoomPartTypeList();
        } else {
            showRoomProductTypes(partTypeList, position);
        }

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

    private void selectPart(int position) {
        int lastPosition = indexMap.get(mCurrentIndex);
        if (lastPosition != position) {
            RoomProduct lastProduct = partAdapter.getItem(lastPosition);
            lastProduct.setSelected(false);
            partAdapter.notifyItemChanged(lastPosition);
        }
        RoomProduct product = partAdapter.getItem(position);
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
        for (RoomProductType type : list) {
            List<RoomProduct> sonList = (List<RoomProduct>) type.getSonList();
            if (sonList != null && !sonList.isEmpty()) {
                //产品是在当前type下面的
                if (type.getType_id().equals(mDefaultSelectTypeId)) {
                    type.setSelected(true);
                    mCurrentIndex = type.getOrder_num();
                    //默认选中
                    hasFind = true;
                    partAdapter.addAll(sonList);
                    for (int i = 0; i < sonList.size(); i++) {
                        if (sonList.get(i).getId().equals(mDefaultSelectProductId)) {
                            indexMap.put(type.getOrder_num(), i);
                            sonList.get(i).setSelected(true);
                            productRecyclerView.scrollToPosition(i);
                            break;
                        }
                    }
                } else {
                    sonList.get(0).setSelected(true);
                    indexMap.put(type.getOrder_num(), 0);
                }
            }
        }

        if (!hasFind) {//没找到默认选择第一个
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
    public void showRoomProductTypes(List<RoomProductType> list, int position) {
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
//            ArrayList<RoomProductType> tempList = new ArrayList<>();
//            for(RoomProductType type : list){
//                if(type.getSonList()!= null && type.getSonList().size() > 1){
//                    tempList.add(type);
//                }
//            }
//            //初始化右侧控件类型
//            if(tempList.size() > 0) {
//                addPartType(tempList);
//            }else{
            addPartType(list);
            //}
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


    @OnClick(R.id.iv_calculate)
    public void onShowOrder() {
        ArrayList<Order> list = new ArrayList<>();
        for (int key : productMap.keySet()) {
            Order order =  productMap.get(key);
            if(order != null) {
                list.add(order);
            }
        }
        Intent intent = new Intent();
        intent.putExtra("list", list);
        intent.setClass(this, ListOrderActivity.class);
        startActivity(intent);
    }

    @Bind(R.id.iv_scene_more)
    ImageView imageView;

    @OnClick(R.id.iv_scene_more)
    public void onSceneMore(){
        View view = LayoutInflater.from(this).inflate(R.layout.view_room_scene,null);

        PopupWindow popupWindow = new PopupWindow(view,200,120);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new popDismissListener());
        backgroundAlpha(0.6f);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layout_scene);

        for(int i=0;i< 3;i++) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_room_scene, null);
            linearLayout.addView(item);
        }
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);

        popupWindow.showAtLocation(imageView, Gravity.NO_GRAVITY, location[0]+imageView.getWidth(), location[1]);
        imageView.setBackground(getResources().getDrawable(R.drawable.icon_scene_more_p));
    }
    class popDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            imageView.setBackground(getResources().getDrawable(R.drawable.icon_scene_more_n));
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     * 注意：此时activity背景style 要设置为 <item name="android:windowIsTranslucent">false</item>
     * 因为activity滑动删除功能需要将android:windowIsTranslucent设置为true
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

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

    @Override
    public void tokenInvalid(String msg) {
        dialogDelegate.showErrorDialog(msg, msg, new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(RoomActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(String msg) {
        T.showShort(this, msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt("index");
            if (position != -1) {
                selectPart(position);
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
}
