package com.buqi.app.manager;

import com.buqi.app.bean.BannerMain;
import com.buqi.app.bean.LoginNewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacr on 2016/11/28.
 */
public class AccountManager {
    private static AccountManager accountManager;
    private List<LoginNewBean.Parent> mListParent;
    private List<BannerMain> mListBanner;
    private List<BannerMain> mListCurrentBanner;
    private String mCurrentSelectParentId;
    private String mCurrentParentName;
    private List<OnParentChangeListener> mListListener = new ArrayList<>();

    public static AccountManager getInstance() {
        if (accountManager == null) {
            accountManager = new AccountManager();
        }
        return accountManager;
    }

    public List<LoginNewBean.Parent> getParentList() {
        if (mListParent == null) {
            mListParent = new ArrayList<>();
        }
        return mListParent;
    }

    public void setParentList(List<LoginNewBean.Parent> list) {
        if (mListParent == null) {
            mListParent = new ArrayList<>();
        }
        mListParent.clear();
        mListParent.addAll(list);
        mCurrentSelectParentId = "";
    }

    public void setCurrentSelectParentId(String id) {
        if(!id.equals(mCurrentSelectParentId)) {
            mCurrentSelectParentId = id;
            for(LoginNewBean.Parent bean : getParentList()){
                if(id.equals(bean.getID())){
                    bean.setSelected(true);
                }else{
                    bean.setSelected(false);
                }
            }
            if(mListCurrentBanner == null){
                mListCurrentBanner = new ArrayList<>();
            }
            mListCurrentBanner.clear();
            if (mListBanner != null) {
                for (BannerMain bannerMain : mListBanner) {
                    String parentId = bannerMain.getPARENT_USER_ID();
                    if (parentId == null || parentId.isEmpty()) {
                        continue;
                    }
                    if (parentId.equals(mCurrentSelectParentId)) {
                        mListCurrentBanner.add(bannerMain);
                    }
                }
            }
            for(OnParentChangeListener listener : mListListener){
                listener.onChanged();
            }
        }
    }

    public String getCurrentSelectParentId() {
        return mCurrentSelectParentId;
    }

    public String getCurrentParentName(){
        return mCurrentParentName;
    }

    public void setCurrentParentName(String name){
        mCurrentParentName = name;
    }
    public void setBannerList(List<BannerMain> list) {
        if (mListBanner == null) {
            mListBanner = new ArrayList<>();
        }
        mListBanner.clear();
        mListBanner.addAll(list);
    }

    public List<BannerMain> getCurrentBannerList() {
        return mListCurrentBanner;
    }

    public void addOnParentChangeListener(OnParentChangeListener listener){
        mListListener.add(listener);
    }

    public interface OnParentChangeListener{
         void onChanged();
    }


}
