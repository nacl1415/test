package com.app.myapplication;

import java.util.HashMap;

/**
 * Created by nacl on 2017/9/20.
 */

public class MemberMgr {
    private static MemberMgr instance;

    public static MemberMgr getInstance()
    {
        if (instance == null) {
            instance = new MemberMgr();
        }
        return instance;
    }

    String mAccount = "test00";
    String mPwd = "123";
    String mShopID = "1";
    String mSelectShopID = "1";
    boolean mIsLogin = false;
    int mCurrPage = 0;

    HashMap<String, FoodObj> mBuyFoodList = new HashMap<>();

    private MemberMgr()
    {
    }

    public void login(String acc, String pwd)
    {

    }

    public void logout()
    {

    }

    public String getAccount()
    {
        return mAccount;
    }

    public String getShopID()
    {
        return mShopID;
    }

    public boolean isLogin()
    {
        return mIsLogin;
    }

    public void setSelectShopID(String shopID)
    {
        mSelectShopID = shopID;
    }

    public String getSelectShopID()
    {
        return mSelectShopID;
    }

    public void addBuyFoodObj(String prodID, String price)
    {
        FoodObj obj = new FoodObj(prodID, price);
        if(mBuyFoodList.get(prodID) == null) {
            mBuyFoodList.put(prodID, obj);
        }
    }

    public void addFoodObj(FoodObj foodObj)
    {
        String prodID = foodObj.getProdID();
        if(mBuyFoodList.get(prodID) == null)
        {
            mBuyFoodList.put(prodID, foodObj);
        }
    }

    public void removeFoodObj(String prodID)
    {
        if(mBuyFoodList.get(prodID) == null)
            return;

        mBuyFoodList.remove(prodID);
    }

    public int getTotalPrice()
    {
        int totalPrice = 0;
        for (Object key : mBuyFoodList.keySet()) {
            totalPrice = totalPrice + mBuyFoodList.get(key).getPrice();
        }

        return totalPrice;
    }
}
