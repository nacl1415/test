package com.app.myapplication;

import java.util.ArrayList;
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

    String mAcc = "test00";
    String mPwd = "123";
    String mName = "TEST";
    String mPhone = "0985000000";
    String mAddr = "台中市南區興大路145號";

    String mShopID = "1";
    String mSelectShopID = "1";
    boolean mIsLogin = false;
    int mCurrPage = 0;

    HashMap<String, FoodObj> mBuyFoodList = new HashMap<>();

    private MemberMgr()
    {
    }

    public void login(String acc, String pwd, String name, String phone, String addr)
    {
        mAcc = acc;
        mPwd = pwd;
        mName = name;
        mPhone = phone;
        mAddr = addr;
    }

    public void logout()
    {

    }

    public String getAccount()
    {
        return mAcc;
    }

    public String getName()
    {
        return mName;
    }

    public String getPhone()
    {
        return mPhone;
    }

    public String getAddr()
    {
        return mAddr;
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

    public void clearFoodObj()
    {
        mBuyFoodList.clear();
    }

    public int getTotalPrice()
    {
        int totalPrice = 0;
        for (Object key : mBuyFoodList.keySet()) {
            totalPrice = totalPrice + mBuyFoodList.get(key).getTotalPrice();
        }

        return totalPrice;
    }

    public ArrayList<FoodObj> getFoodList()
    {
        ArrayList<FoodObj> arrayList = new ArrayList<>();
        for (Object key : mBuyFoodList.keySet()) {
            arrayList.add(mBuyFoodList.get(key));
        }

        return arrayList;
    }
}
