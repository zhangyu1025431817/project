package com.fangzhi.app.main.decoration.product;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.CategoryPart;
import com.fangzhi.app.bean.CategoryPartRoomBean;
import com.fangzhi.app.bean.SearchPartBean;

import java.util.List;

import rx.Observable;

/**
 * Created by smacr on 2016/10/21.
 */
public interface ProductContract {
    interface Model extends BaseModel {
        Observable<CategoryPart> getPartList(String token, String categoryID, String userID);

        Observable<SearchPartBean> searchPart(String token,String key,String userId,String typeId,String cateId);
        Observable<CategoryPartRoomBean> getScene(String token,String userId,String partId);
    }

    interface View extends BaseView {
        String getToken();

        String getUserId();

        String getCategoryId();

        String getKeyword();
        String getTypeId();
        String getPartId();

        void showCategoryList(List<CategoryPart.HotType> list);
        void showCategoryPartList(List<CategoryPart.Part> list);
        void showSceneSucceed(CategoryPartRoomBean categoryPartRoomBean);
        void showSceneFailed(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        abstract void getPartList();
        abstract void search();
        abstract void getScene();
    }
}
