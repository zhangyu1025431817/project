package com.buqi.app.main;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.buqi.app.R;
import com.buqi.app.bean.BannerMain;
import com.buqi.app.login.LoginActivity;
import com.buqi.app.main.adapter.BannerAdapter;
import com.buqi.app.main.adapter.NoDoubleClickListener;
import com.buqi.app.main.ddd.DDDWebView;
import com.buqi.app.main.ddd.ThreeDimensionalActivity;
import com.buqi.app.main.decoration.SellPartActivity;
import com.buqi.app.main.house.HouseActivity;
import com.buqi.app.main.parent.ParentActivity;
import com.buqi.app.main.sample.SampleActivity;
import com.buqi.app.main.scenestyle.SceneStyleActivity;
import com.buqi.app.manager.AccountManager;
import com.buqi.app.tools.ActivityTaskManager;
import com.buqi.app.tools.DensityUtils;
import com.buqi.app.tools.ScreenUtils;
import com.buqi.app.view.DialogContactUs;
import com.buqi.app.view.DialogDelegate;
import com.buqi.app.view.SweetAlertDialogDelegate;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by smacr on 2016/11/25.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.roll_pager_view)
    RollPagerView rollPagerView;
    @Bind(R.id.tv_title)
    TextView textViewTitle;
    List<String> mListImages = new ArrayList<>();
    BannerAdapter mBannerAdapter;
    DialogDelegate dialogDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rollPagerView.setHintView(new ColorPointHintView(this, Color.GREEN, Color.GRAY));
        rollPagerView.setHintPadding(0, 0, 0, DensityUtils.dp2px(this, 8));
        rollPagerView.setPlayDelay(2000);
        rollPagerView.setAdapter(mBannerAdapter = new BannerAdapter(this, mListImages,  new NoDoubleClickListener()  {
            @Override
            public void onNoDoubleClick(int position) {
                    if(mBannerList != null){
                        BannerMain bannerMain =  mBannerList.get(position);
                        String url = bannerMain.getURL();
                        if(url != null && !url.isEmpty()){
                            Intent intent = new Intent();
                            intent.putExtra("url",url);
                            intent.setClass(MainActivity.this, DDDWebView.class);
                            startActivity(intent);
                        }
                    }
            }
        }));

        setData(AccountManager.getInstance().getCurrentBannerList());
        AccountManager.getInstance().addOnParentChangeListener(new AccountManager.OnParentChangeListener() {
            @Override
            public void onChanged() {
                setData(AccountManager.getInstance().getCurrentBannerList());
            }
        });
        dialogDelegate = new SweetAlertDialogDelegate(this);
    }

    List<BannerMain> mBannerList ;
    private void setData(List<BannerMain> list) {
        textViewTitle.setText(AccountManager.getInstance().getCurrentParentName());
        if (list != null && !list.isEmpty()) {
            mBannerList = list;
            mListImages.clear();
            for (BannerMain bean : list) {
                mListImages.add(bean.getIMG_URL());
            }
            mBannerAdapter.notifyDataSetChanged();
        }
    }

    public void pressDown(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "shrink", 1.0F, 0.95F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    public void pressUp(final View view) {
        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "zoom", 0.95F, 1.0F)//
                .setDuration(500);//
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    @OnTouch({R.id.layout_house_material, R.id.layout_scene_style,
            R.id.layout_house_location, R.id.layout_3d, R.id.layout_sample})
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            pressUp(v);
            switch (v.getId()) {
                case R.id.layout_house_material:
                    startActivity(new Intent(this, SellPartActivity.class));
                    break;
                case R.id.layout_scene_style:
                    startActivity(new Intent(this, SceneStyleActivity.class));
                    break;
                case R.id.layout_house_location:
                    startActivity(new Intent(this, HouseActivity.class));
                    break;
                case R.id.layout_3d:
                    startActivity(new Intent(this, ThreeDimensionalActivity.class));
                    break;
                case R.id.layout_sample:
                    startActivity(new Intent(this, SampleActivity.class));
                    break;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressDown(v);
        }
        //返回false后up无效
        return true;
    }

    private PopupWindow popupWindow;



    @OnClick(R.id.tv_more)
    public void onMore(View view) {
        showPopupWindow(view);
    }

    private void showPopupWindow(View parent) {
        TextView btnLogout;
        TextView btnChangeParent;
        TextView btnContactUs;
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.view_popup_window, null);
            btnLogout = (TextView) view.findViewById(R.id.btn_logout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onExit();
                }
            });

            btnContactUs = (TextView) view.findViewById(R.id.btn_contact_us);
            btnContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onContactUs();
                }
            });

            btnChangeParent = (TextView) view.findViewById(R.id.btn_change_parent);
            btnChangeParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChangeParent();
                    popupWindow.dismiss();
                }
            });
            if (AccountManager.getInstance().getParentList() == null ||
                    AccountManager.getInstance().getParentList().size() <= 1) {
                // btnChangeParent.setVisibility(View.GONE);
                btnChangeParent.setTextColor(getResources().getColor(R.color.gray));
                btnChangeParent.setClickable(false);
            } else {
                //  btnChangeParent.setVisibility(View.VISIBLE);
                btnChangeParent.setTextColor(getResources().getColor(R.color.black_semi_transparent));
                btnChangeParent.setClickable(true);
            }
            popupWindow = new PopupWindow(view, ScreenUtils.getScreenWidth(this) / 8, AutoLinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        int xPos = -popupWindow.getWidth() / 2;

        popupWindow.showAsDropDown(parent, xPos, 4);

    }

    private void onContactUs() {
        new DialogContactUs(this).show();
    }

    private void onChangeParent() {
        startActivity(new Intent(this, ParentActivity.class));
    }

    public void onExit() {
        dialogDelegate.showWarningDialog("退出登录", "确定退出当前账号？", new DialogDelegate.OnDialogListener() {
            @Override
            public void onClick() {
                dialogDelegate.clearDialog();
                popupWindow.dismiss();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityTaskManager.getActivityTaskManager().finishActivity();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
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
