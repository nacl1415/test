package com.app.myapplication;

/**
 * Created by nacl on 2017/9/23.
 */

public class BuyFoodObj {

    String mProdID = "1";
    int mAmount = 0;
    int mPrice = 0;

    public BuyFoodObj(String prodID, String price)
    {
        mProdID = prodID;
        mPrice = Integer.valueOf(price);
    }
    
    public int setAmount(int value)
    {
        mAmount = value;

        return mAmount * mPrice;
    }

    public int modifyAmount(int value)
    {
        mAmount = mAmount + value;
        if(mAmount < 0)
            mAmount = 0;
        else if(mAmount > 100)
            mAmount = 100;

        return mAmount * mPrice;
    }
}
