package com.app.myapplication;

/**
 * Created by nacl on 2017/9/20.
 */

public class Values {

    public class Web {
        public static final int LOGIN_SUCC = 0;
        public static final int LOGIN_FAIL = 1;
        public static final int REG_SUCC = 2;
        public static final int REG_FAIL = 3;
        public static final int REG_CHECK_ACC = 4;
        public static final int REG_SAME_ACC = 5;
        public static final int REG_CHECK_PHONE = 6;
        public static final int REG_SAME_PHONE = 7;
        public static final int ADD_FOOD_SUCC = 8;
        public static final int ADD_FOOD_FAIL = 9;
        public static final int LOAD_FOOD_SUCC = 10;
        public static final int LOAD_FOOD_FAIL = 11;
        public static final int LOAD_SHOPFOOD_SUCC = 12;
        public static final int LOAD_SHOPFOOD_FAIL = 13;
        public static final int SEND_ORDER_SUCC = 14;
        public static final int SEND_ORDER_FAIL = 15;
        public static final int GET_ORDER_SUCC = 16;
        public static final int GET_ORDER_FAIL = 17;
        public static final int LOGIN_TEMP_SUCC = 18;
        public static final int LOGIN_TEMP_FAIL = 19;
    }

    public class who {
        public static final int NONE = 0;
        public static final int BUYER = 1;
        public static final int SALER = 2;
    }

    public class PageIndex {
        public static final int Index = 0;
        public static final int CART = 1;
        public static final int UPLOAD = 2;
        public static final int ORDER = 3;
        public static final int MEMBER = 4;
    }
}