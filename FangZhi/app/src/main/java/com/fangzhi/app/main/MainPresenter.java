package com.fangzhi.app.main;

import com.fangzhi.app.bean.CountyHouses;
import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.bean.LoginBean;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/1.
 */
public class MainPresenter extends MainContract.Presenter {
    private boolean isShowCounty = true;

    public void changeShowCounty(boolean isShowCounty) {
        this.isShowCounty = isShowCounty;
    }

    @Override
    void getHousesList() {
        String areaCode = mView.getAreaCode();
        int pageSize = mView.getPageSize();
        int curPage = mView.getCurrentPage();
        String token = mView.getToken();
        mRxManager.add(mModel.getHousesList(token, areaCode, pageSize, curPage).subscribe(new MySubscriber<CountyHouses>() {
            @Override
            public void onNext(CountyHouses houses) {
                if (ErrorCode.TOKEN_INVALID.equals(houses.getError_code())) {
                    mView.tokenInvalid(houses.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(houses.getError_code())) {
                    mView.onError(houses.getMsg());
                } else if (ErrorCode.SUCCEED.equals(houses.getError_code())) {
                    if (isShowCounty) {
                        mView.showCountyList(houses.getCountyList());
                        isShowCounty = false;
                    }
                    mView.showHousesList(houses.getPremiseList());
                } else {
                    mView.showHousesList(null);
                }

            }

            @Override
            public void onError(Throwable e) {

                mView.showHousesList(null);
            }
        }));
    }

    @Override
    void getCountyHousesList() {
        String token = mView.getToken();
        String id = mView.getCountyId();
        mRxManager.add(mModel.getCountyHousesList(token, id).subscribe(new MySubscriber<Houses>() {
            @Override
            public void onNext(Houses houses) {
                if (ErrorCode.TOKEN_INVALID.equals(houses.getError_code())) {
                    mView.tokenInvalid(houses.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(houses.getError_code())) {
                    mView.onError(houses.getMsg());
                } else if (ErrorCode.SUCCEED.equals(houses.getError_code())) {
                    mView.showNoMoreHouseList(houses.getPremiseList());
                } else {
                    mView.showHousesList(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showHousesList(null);
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
        mRxManager.add(mModel.searchHouseList(token, areaCode, key, pageSize, curPage)
                .subscribe(new MySubscriber<CountyHouses>() {
                    @Override
                    public void onNext(CountyHouses houses) {
                        if (ErrorCode.TOKEN_INVALID.equals(houses.getError_code())) {
                            mView.tokenInvalid(houses.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(houses.getError_code())) {
                            mView.onError(houses.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(houses.getError_code())) {
                            mView.showHousesList(houses.getPremiseList());
                        } else {
                            mView.showHousesList(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showHousesList(null);
                    }
                }));
    }

    @Override
    void changeParent() {
        mRxManager.add(mModel.changeParent(mView.getToken(), mView.getUserId(), mView.getParentId())
                .subscribe(new MySubscriber<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (ErrorCode.SUCCEED.equals(loginBean.getError_code())) {
                            mView.changeSucceed(loginBean.getToken());
                        } else {
                            mView.changeFailed(loginBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.changeFailed("网络连接失败!");
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
