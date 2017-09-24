package com.app.myapplication;

/**
 * Created by nacl on 2017/9/23.
 */

public class FoodObj {

    String mProdID = "1";
    String mName = "測試食品";
    int mAmount = 0;
    int mPrice = 0;

    public FoodObj(String prodID, String name, String price)
    {
        mProdID = prodID;
        mName = name;
        mPrice = Integer.valueOf(price);
    }

    public String getProdID()
    {
        return mProdID;
    }

    public String getName()
    {
        return mName;
    }
    
    public int setAmount(int value)
    {
        mAmount = value;
        if(mAmount < 0)
            mAmount = 0;
        else if(mAmount > 100)
            mAmount = 100;

        return mAmount;
    }

    public int modifyAmount(int value)
    {
        mAmount = mAmount + value;
        if(mAmount < 0)
            mAmount = 0;
        else if(mAmount > 100)
            mAmount = 100;

        return mAmount;
    }

    public int getAmount()
    {
        return mAmount;
    }

    public int getPrice()
    {
        return mPrice;
    }

    public int getTotalPrice()
    {
        return mAmount * mPrice;
    }
}
