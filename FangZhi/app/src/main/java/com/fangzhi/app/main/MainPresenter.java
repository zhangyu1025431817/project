package com.fangzhi.app.main;

import com.fangzhi.app.bean.Houses;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;

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
        mRxManager.add(mModel.getHousesList(token, areaCode, pageSize, curPage).subscribe(new MySubscriber<Houses>() {
            @Override
            public void onNext(Houses houses) {
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
    void searchHouseList() {
        String key = mView.getKey();
        String areaCode = mView.getAreaCode();
        int pageSize = mView.getPageSize();
        int curPage = mView.getCurrentPage();
        String token = mView.getToken();
        mRxManager.add(mModel.searchHouseList(token, areaCode, key, pageSize, curPage)
                .subscribe(new MySubscriber<Houses>() {
                    @Override
                    public void onNext(Houses houses) {
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
    public void onStart() {

    }
}
