package com.buqi.app.main.sample;

import com.buqi.app.bean.CooperativeResponseBean;
import com.buqi.app.bean.SceneLabelResponseBean;
import com.buqi.app.network.MySubscriber;
import com.buqi.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/9/22.
 */
public class SamplePresenter extends SampleContract.Presenter {
    @Override
    public void onStart() {

    }

    @Override
    void getCooperativeList() {
        mRxManager.add(mModel.getCooperativeList(mView.getToken())
        .subscribe(new MySubscriber<CooperativeResponseBean>(){
            @Override
            public void onNext(CooperativeResponseBean cooperativeResponseBean) {
                if(ErrorCode.SUCCEED.equals(cooperativeResponseBean.getError_code())){
                    mView.showCooperativeList(cooperativeResponseBean.getCooperativeList()
                    ,cooperativeResponseBean.getTypeList());
                }else{
                    mView.onError(cooperativeResponseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("网络连接失败");
            }
        }));
    }

    @Override
    void getTagList() {
        mRxManager.add(mModel.getTagList(mView.getToken(),mView.getId())
        .subscribe(new MySubscriber<SceneLabelResponseBean>(){
            @Override
            public void onNext(SceneLabelResponseBean sceneLabelResponseBean) {
                if(ErrorCode.SUCCEED.equals(sceneLabelResponseBean.getError_code())){
                    mView.show3D2DList(sceneLabelResponseBean.getTypeList());
                    mView.showTagList(sceneLabelResponseBean.getLabelList());
                }else{
                    mView.onError(sceneLabelResponseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError("网络连接失败");
            }
        }));
    }


}
