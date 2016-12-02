package com.fangzhi.app.main.sample;

import com.fangzhi.app.bean.CooperativeResponseBean;
import com.fangzhi.app.bean.SceneLabelResponseBean;
import com.fangzhi.app.network.Network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by smacr on 2016/9/22.
 */
public class SampleModel implements SampleContract.Model {

    @Override
    public Observable<CooperativeResponseBean> getCooperativeList(String token) {
        return Network.getApiService().getCooperativeList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SceneLabelResponseBean> getTagList(String token, String id) {
        return Network.getApiService().getTagList(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
