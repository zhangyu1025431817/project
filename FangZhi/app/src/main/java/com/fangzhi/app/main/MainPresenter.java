package com.fangzhi.app.main;

import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.network.MySubscriber;

import java.util.ArrayList;

/**
 * Created by smacr on 2016/9/1.
 */
public class MainPresenter extends MainContract.Presenter {
    @Override
    void getHousesList() {
        String areaCode = mView.getAreaCode();
        int pageSize = mView.getPageSize();
        int curPage = mView.getCurrentPage();
        String token = mView.getToken();
            mRxManager.add(mModel.getHousesList(token,areaCode,pageSize,curPage).subscribe(new MySubscriber<Houses>(){
                @Override
                public void onNext(Houses houses) {
                    mView.showHousesList(houses.getPremiseList());
                }

                @Override
                public void onError(Throwable e) {
                    mView.showHousesList(new ArrayList<Houses.House>());
                }
            }));
    }

    @Override
    void searchHouseList() {
        String key = mView.getKey();
        String areaCode = mView.getAreaCode();
        int pageSize = mView.getPageSize();
        int curPage = mView.getCurrentPage();
        String token = mView.getToken();
        mRxManager.add(mModel.searchHouseList(token,areaCode,key,pageSize,curPage)
        .subscribe(new MySubscriber<Houses>(){
            @Override
            public void onNext(Houses houses) {
                mView.showHousesList(houses.getPremiseList());
            }

            @Override
            public void onError(Throwable e) {
                mView.showHousesList(new ArrayList<Houses.House>());
            }
        }));
    }

    @Override
    public void onStart() {

    }
}
