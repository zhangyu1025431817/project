package com.fangzhi.app.main.decoration.product;

import com.fangzhi.app.bean.CategoryPart;
import com.fangzhi.app.bean.CategoryPartRoomBean;
import com.fangzhi.app.bean.SearchPartBean;
import com.fangzhi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/10/26.
 */
public class ProductModel implements ProductContract.Model {
    @Override
    public Observable<CategoryPart> getPartList(String token, String categoryID, String userID) {
        return Network.getApiService().getCategoryPartList(token, categoryID, userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SearchPartBean> searchPart(String token, String key, String userId, String typeId,String cateId) {
        return Network.getApiService().searchCategoryPart(token, key, userId, typeId,cateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CategoryPartRoomBean> getScene(String token, String userId, String partId) {
        return Network.getApiService().getCategoryScene(token, userId, partId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
