package com.fangzhi.app.main;

import com.fangzhi.app.MyApplication;
import com.fangzhi.app.bean.HousesResponseBean;
import com.fangzhi.app.config.SpKey;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.tools.SPUtils;

/**
 * Created by smacr on 2016/9/1.
 */
public class MainPresenter extends MainContract.Presenter {
    @Override
    void getHousesList(String areaCode, int pageSize, int curPage) {
        String token = (String) SPUtils.get(MyApplication.getContext(), SpKey.TOKEN, "");
            mRxManager.add(mModel.getHousesList(token,areaCode,pageSize,curPage).subscribe(new MySubscriber<HousesResponseBean>(){
                @Override
                public void onNext(HousesResponseBean housesResponseBean) {
                    mView.showHousesList(housesResponseBean.getHousingEstateList());
                }

                @Override
                public void onError(Throwable e) {

                }
            }));
    }

    @Override
    public void onStart() {

    }
}
