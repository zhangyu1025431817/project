package com.fangzhipro.app.main.decoration;

import com.fangzhipro.app.base.BaseModel;
import com.fangzhipro.app.base.BasePresenter;
import com.fangzhipro.app.base.BaseView;
import com.fangzhipro.app.bean.SellType;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/10/21.
 */
public interface SellPartContract {
    interface Model extends BaseModel {
        Observable<SellType> getSellCategory(String token, String userId);
    }

    interface View extends BaseView {
        String getToken();
        String getUserId();
        void showCategoryList(List<SellType.Category> list);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
       abstract void getSellCateGory();
    }
}
