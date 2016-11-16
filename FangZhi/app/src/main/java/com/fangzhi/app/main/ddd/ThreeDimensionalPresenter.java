package com.fangzhi.app.main.ddd;

import com.fangzhi.app.bean.DDDTypeResponseBean;
import com.fangzhi.app.bean.FitmentTypeResponseBean;
import com.fangzhi.app.network.MySubscriber;
import com.fangzhi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/11/15.
 */
public class ThreeDimensionalPresenter extends ThreeDimensionalContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    public void getCaseTypeList() {
        mRxManager.add(mModel.getCaseTypeList(mView.getToken())
                .subscribe(new MySubscriber<FitmentTypeResponseBean>() {
                    @Override
                    public void onNext(FitmentTypeResponseBean fitmentTypeResponseBean) {
                        if (ErrorCode.TOKEN_INVALID.equals(fitmentTypeResponseBean.getError_code())) {
                            mView.tokenInvalid(fitmentTypeResponseBean.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(fitmentTypeResponseBean.getError_code())) {
                            mView.onError(fitmentTypeResponseBean.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(fitmentTypeResponseBean.getError_code())) {
                            mView.showFitmentTypes(fitmentTypeResponseBean.getCaseTypeList());
                        } else {
                            mView.onError(fitmentTypeResponseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError("网络连接失败,请稍后再试！");
                    }
                }));
    }

    @Override
    public void getCaseList() {
        mRxManager.add(mModel.getCaseList(mView.getToken(),mView.getCaseTypeId())
        .subscribe(new MySubscriber<DDDTypeResponseBean>(){
            @Override
            public void onNext(DDDTypeResponseBean dddTypeResponseBean) {
                if (ErrorCode.TOKEN_INVALID.equals(dddTypeResponseBean.getError_code())) {
                    mView.tokenInvalid(dddTypeResponseBean.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(dddTypeResponseBean.getError_code())) {
                    mView.onError(dddTypeResponseBean.getMsg());
                } else if (ErrorCode.SUCCEED.equals(dddTypeResponseBean.getError_code())) {
                    mView.showDDDTypes(dddTypeResponseBean.getCaseList());
                } else {
                    mView.onError(dddTypeResponseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("网络连接失败,请稍后再试！");
            }
        }));
    }
}
