package com.buyiren.app.main.ddd;

import com.buyiren.app.bean.DDDTypeResponseBean;
import com.buyiren.app.bean.FitmentTypeResponseBean;
import com.buyiren.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/11/15.
 */
public class ThreeDimensionalModel implements ThreeDimensionalContract.Model {

    @Override
    public Observable<FitmentTypeResponseBean> getCaseTypeList(String token) {
        return Network.getApiService().getFitmentTypes(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DDDTypeResponseBean> getCaseList(String token, String id) {
        return Network.getApiService().getDDDtypes(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
