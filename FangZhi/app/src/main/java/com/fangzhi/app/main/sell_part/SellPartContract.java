package com.fangzhi.app.main.sell_part;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.SellType;

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
