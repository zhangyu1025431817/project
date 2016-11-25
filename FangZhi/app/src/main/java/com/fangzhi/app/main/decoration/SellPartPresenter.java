package com.fangzhi.app.main.decoration;

import com.fangzhi.app.bean.SellType;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/10/21.
 */
public class SellPartPresenter extends SellPartContract.Presenter {
    @Override
    void getSellCateGory() {
        mRxManager.add(mModel.getSellCategory(mView.getToken(),mView.getUserId()).subscribe(new MySubscriber<SellType>(){
            @Override
            public void onNext(SellType sellType) {
                if (ErrorCode.TOKEN_INVALID.equals(sellType.getError_code())) {
                    mView.tokenInvalid(sellType.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(sellType.getError_code())) {
                    mView.onError(sellType.getMsg());
                } else if (ErrorCode.SUCCEED.equals(sellType.getError_code())) {
                    mView.showCategoryList(sellType.getCategoryList());
                } else {
                    mView.onError(sellType.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("服务器连接失败!");
            }
        }));
    }

    @Override
    public void onStart() {

    }
}
