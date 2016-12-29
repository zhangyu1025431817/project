package com.buyiren.app.main.decoration.product;

import com.buyiren.app.bean.CategoryPart;
import com.buyiren.app.bean.CategoryPartRoomBean;
import com.buyiren.app.bean.SearchPartBean;
import com.buyiren.app.network.MySubscriber;
import com.buyiren.app.network.http.api.ErrorCode;

/**
 * Created by smacr on 2016/10/26.
 */
public class ProductPresenter extends ProductContract.Presenter {
    @Override
    void getPartList() {
        mRxManager.add(mModel.getPartList(mView.getToken(),mView.getCategoryId(),mView.getUserId())
        .subscribe(new MySubscriber<CategoryPart>(){
            @Override
            public void onNext(CategoryPart categoryPart) {
                if (ErrorCode.TOKEN_INVALID.equals(categoryPart.getError_code())) {
                    mView.tokenInvalid(categoryPart.getMsg());
                } else if (ErrorCode.SERVER_EXCEPTION.equals(categoryPart.getError_code())) {
                    mView.onError(categoryPart.getMsg());
                } else if (ErrorCode.SUCCEED.equals(categoryPart.getError_code())) {
                    mView.showCategoryList(categoryPart.getPartList());
                } else {
                    mView.onError(categoryPart.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
              //  mView.onError(e.getMessage());
                mView.onError("服务器连接失败!");
            }
        }));
    }

    @Override
    void search() {
        mRxManager.add(mModel.searchPart(mView.getToken(),mView.getKeyword(),mView.getUserId(),
                mView.getTypeId(),mView.getCategoryId()).
                subscribe(new MySubscriber<SearchPartBean>(){
                    @Override
                    public void onNext(SearchPartBean searchPartBean) {
                        if (ErrorCode.TOKEN_INVALID.equals(searchPartBean.getError_code())) {
                            mView.tokenInvalid(searchPartBean.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(searchPartBean.getError_code())) {
                            mView.onError(searchPartBean.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(searchPartBean.getError_code())) {
                            mView.showCategoryPartList(searchPartBean.getPartList());
                        } else {
                            mView.onError(searchPartBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError("服务器连接失败!");
                    }
                }));
    }

    @Override
    void getScene() {
        mRxManager.add(mModel.getScene(mView.getToken(),mView.getUserId(),mView.getPartId()).
                subscribe(new MySubscriber<CategoryPartRoomBean>(){
                    @Override
                    public void onNext(CategoryPartRoomBean categoryPartRoomBean) {
                        if (ErrorCode.TOKEN_INVALID.equals(categoryPartRoomBean.getError_code())) {
                            mView.tokenInvalid(categoryPartRoomBean.getMsg());
                        } else if (ErrorCode.SERVER_EXCEPTION.equals(categoryPartRoomBean.getError_code())) {
                            mView.showSceneFailed(categoryPartRoomBean.getMsg());
                        } else if (ErrorCode.SUCCEED.equals(categoryPartRoomBean.getError_code())) {
                            mView.showSceneSucceed(categoryPartRoomBean);
                        } else {
                            mView.showSceneFailed(categoryPartRoomBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSceneFailed("服务器连接失败!");
                    }
                }));
    }

    @Override
    public void onStart() {

    }
}
