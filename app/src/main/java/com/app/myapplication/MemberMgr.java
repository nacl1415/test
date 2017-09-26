package com.app.myapplication;

import android.content.Context;
import android.content.Intent;

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
    int mWho = Values.who.NONE;
    boolean mIsNeedLoadBuyerOrder = true;
    boolean mIsNeedLoadSaleOrder = true;

    HashMap<String, FoodObj> mBuyFoodList = new HashMap<>();

    private MemberMgr()
    {
    }

    public void login(String acc, String pwd, String name, String phone, String addr, String shopID)
    {
        mAcc = acc;
        mPwd = pwd;
        mName = name;
        mPhone = phone;
        mAddr = addr;
        mShopID = shopID;
        mIsLogin = true;
    }

    public void setisLogin()
    {
        mIsLogin = true;
    }

    public void logout()
    {
        mAcc = "";
        mPwd = "";
        mName = "";
        mPhone = "";
        mAddr = "";
        mShopID = "";
        mSelectShopID = "";
        mIsLogin = false;
        mWho = Values.who.NONE;
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

    public void setWho(int who)
    {
        mWho = who;
    }

    public int getWho()
    {
        return mWho;
    }

    public void setIsNeedLoadBuyerOrder(boolean isNeedLoad)
    {
        mIsNeedLoadBuyerOrder = isNeedLoad;
    }

    public void setIsNeedLoadSaleOrder(boolean isNeedLoad)
    {
        mIsNeedLoadSaleOrder = isNeedLoad;
    }

    public boolean isNeedLoadBuyerOrder()
    {
        return mIsNeedLoadBuyerOrder;
    }

    public boolean isNeedLoadSaleOrder()
    {
        return mIsNeedLoadSaleOrder;
    }

    public void gotoPage(Context context, int index)
    {
        switch (index)
        {
            case Values.PageIndex.Index://shop
                context.startActivity(new Intent(context, IndexActivity.class));
                break;
            case Values.PageIndex.CART://cart
                setWho(Values.who.BUYER);
                if(mIsLogin)
                {
                    setIsNeedLoadBuyerOrder(true);
                    context.startActivity(new Intent(context, CartPage.class));
                }
                else
                {
                    context.startActivity(new Intent(context, LoginPage.class));
                }
                break;
            case Values.PageIndex.UPLOAD://upload
                if(mIsLogin)
                {
                    context.startActivity(new Intent(context, UploadPage.class));
                }
                else
                {
                    context.startActivity(new Intent(context, LoginPage.class));
                }
                break;
            case Values.PageIndex.ORDER://order (cart)
                setWho(Values.who.SALER);
                if(mIsLogin)
                {
                    setIsNeedLoadBuyerOrder(true);
                    context.startActivity(new Intent(context, CartPage.class));
                }
                else
                {
                    context.startActivity(new Intent(context, LoginPage.class));
                }
                break;
            case Values.PageIndex.MEMBER:
                if(mIsLogin)
                {
                    context.startActivity(new Intent(context, MemberPage.class));
                }
                else
                {
                    context.startActivity(new Intent(context, LoginPage.class));
                }
                break;
        }
    }
}
