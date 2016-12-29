package com.buyiren.app.main.ddd;

import com.buyiren.app.base.BaseModel;
import com.buyiren.app.base.BasePresenter;
import com.buyiren.app.base.BaseView;
import com.buyiren.app.bean.DDDTypeResponseBean;
import com.buyiren.app.bean.FitmentTypeResponseBean;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by smacr on 2016/9/21.
 */
public interface ThreeDimensionalContract {
    interface Model extends BaseModel {
        Observable<FitmentTypeResponseBean> getCaseTypeList(String token);

        Observable<DDDTypeResponseBean> getCaseList(String token,String id);
    }

    interface View extends BaseView {
        String getToken();

        String getCaseTypeId();

        void showFitmentTypes(ArrayList<FitmentTypeResponseBean.FitmentType> caseTypeList);

        void showDDDTypes(ArrayList<DDDTypeResponseBean.DDDType> caseList);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getCaseTypeList();

        public abstract void getCaseList();
    }
}
