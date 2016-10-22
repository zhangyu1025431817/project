package com.fangzhi.app.login.regist;

import com.fangzhi.app.base.BaseModel;
import com.fangzhi.app.base.BasePresenter;
import com.fangzhi.app.base.BaseView;
import com.fangzhi.app.bean.BaseResponseBean;

import rx.Observable;

/**
 * Created by smacr on 2016/8/30.
 */
public interface RegisterContract {
    interface Model extends BaseModel{
        Observable<BaseResponseBean> sendMsgCode(String phone);
        Observable<BaseResponseBean> register(String phone,String code,int type);
    }
    interface View extends BaseView{
        String getPhone();
        String getCode();
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        abstract void sendMsgCode();
        abstract void register();
    }
}
