package com.app.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nacl on 2017/9/19.
 */

public class ConnDB
{
    private static ConnDB instance;

    public static ConnDB getInstance()
    {
        if (instance == null) {
            instance = new ConnDB();
        }
        return instance;
    }

    Handler mHandler;

    private ConnDB()
    {
        //init internet for web
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = "";
                switch (msg.what) {
                    case Values.Web.LOGIN_SUCC:
                        ((LoginPage)msg.obj).onLoginSucc();
                        break;
                    case Values.Web.LOGIN_FAIL:
                        ((LoginPage)msg.obj).onLoginFail();
                        break;
                    case Values.Web.REG_SUCC:
                        ((RegPage)msg.obj).onRegSucc();
                        break;
                    case Values.Web.REG_CHECK_ACC:
                        ((RegPage)msg.obj).onCheckAccSucc();
                        break;
                    case Values.Web.REG_SAME_ACC:
                        ((RegPage)msg.obj).onSameAcc();
                        break;
                    case Values.Web.REG_CHECK_PHONE:
                        ((RegPage)msg.obj).onCheckPhoneSucc();
                        break;
                    case Values.Web.REG_SAME_PHONE:
                        ((RegPage)msg.obj).onSamePhone();
                        break;
                    case Values.Web.ADD_FOOD_SUCC:
                        ((AddFoodPage)msg.obj).onAddFoodSucc();
                        break;
                    case Values.Web.ADD_FOOD_FAIL:
                        ((AddFoodPage)msg.obj).onAddFoodFail();
                        break;
                    case Values.Web.LOAD_FOOD_SUCC:
                        json = msg.getData().getString("json");
                        ((UploadPage)msg.obj).onLoadFoodSucc(json);
                        break;
                    case Values.Web.LOAD_FOOD_FAIL:
                        ((UploadPage)msg.obj).onLoadFoodFail();
                        break;
                    case Values.Web.LOAD_SHOPFOOD_SUCC:
                        json = msg.getData().getString("json");
                        ((FoodPage)msg.obj).onLoadFoodSucc(json);
                        break;
                }
            }
        };

//        Thread thread =new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        thread.start();
    }

    public void login(final Context context, final String acc, final String pwd)
    {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> dataToSend = new HashMap<>();
                    dataToSend.put("acc", acc);
                    dataToSend.put("pwd", pwd);
                    String encodedStr = getEncodedData(dataToSend);

                    String urlString = "https://nacl.000webhostapp.com/login.php";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(encodedStr);
                    writer.flush();
                    writer.close();

                    Message msg = Message.obtain();
                    InputStream inputStream = connection.getInputStream();//傳送資料&抓取回傳值
                    String readStream = readStrem(inputStream);
                    if (readStream.equals("1")) {
                        msg.what = Values.Web.LOGIN_SUCC;
                    } else {
                        msg.what = Values.Web.LOGIN_FAIL;
                    }
                    msg.obj = context;
                    mHandler.sendMessage(msg);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void checkSQLHaveData(final Context context, final int webValue, final String type, final String value)
    {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Map<String,String> dataToSend = new HashMap<>();
                    dataToSend.put("type", type);
                    dataToSend.put("value", value);
                    String encodedStr = getEncodedData(dataToSend);

                    String urlString = "https://nacl.000webhostapp.com/regCheck.php";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(encodedStr);
                    writer.flush();
                    writer.close();

                    Message msg = Message.obtain();
                    InputStream inputStream = connection.getInputStream();//傳送資料&抓取回傳值
                    String readStream = readStrem(inputStream);
                    msg.what = webValue;
                    if(readStream.equals("1"))
                    {
                        if(webValue == Values.Web.REG_CHECK_ACC)
                            msg.what = Values.Web.REG_SAME_ACC;
                        else if(webValue == Values.Web.REG_CHECK_PHONE)
                            msg.what = Values.Web.REG_SAME_PHONE;
                    }
                    msg.obj = context;
                    mHandler.sendMessage(msg);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void register(final Context context, final String acc, final String pwd, final String name, final String phone, final String addr)
    {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Map<String,String> dataToSend = new HashMap<>();
                    dataToSend.put("acc", acc);
                    dataToSend.put("pwd", pwd);
                    dataToSend.put("name", name);
                    dataToSend.put("phone", phone);
                    dataToSend.put("addr", addr);
                    String encodedStr = getEncodedData(dataToSend);

                    String urlString = "https://nacl.000webhostapp.com/addSQL.php";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //Writing dataToSend to outputstreamwriter
                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(encodedStr);
                    writer.flush();
                    writer.close();

                    Message msg = Message.obtain();
                    InputStream inputStream = connection.getInputStream();//傳送資料&抓取回傳值
                    String readStream = readStrem(inputStream);
                    if(readStream.equals("1"))
                    {
                        msg.what = Values.Web.REG_SUCC;
                    }
                    else
                    {
                        msg.what = Values.Web.REG_FAIL;
                    }
                    msg.obj = context;
                    mHandler.sendMessage(msg);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private String getData()
    {
        String inputStr ="";
        String jsonStr = "";
        String urlString= "http://nacl.000webhostapp.com/getSQL.php";
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream is = connection.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((inputStr = streamReader.readLine()) != null){
                jsonStr = jsonStr + inputStr;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return jsonStr;
    }

    public String readStrem(InputStream inputStream)
    {
        StringBuilder sb= new StringBuilder();
        try
        {
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String nextLine="";
            while ((nextLine=reader.readLine())!=null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public String getEncodedData(Map<String,String> data)
    {
        StringBuilder sb = new StringBuilder();
        for(String key : data.keySet()) {
            String value = null;
            try
            {
                value = URLEncoder.encode(data.get(key),"UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if(sb.length()>0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }

    public void addFood(final Context context, final String name, final String price, final String imgName)
    {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    String shopID = MemberMgr.getInstance().getShopID();
                    Map<String,String> dataToSend = new HashMap<>();
                    dataToSend.put("name", name);
                    dataToSend.put("price", price);
                    dataToSend.put("img_name", imgName);
                    dataToSend.put("shop_id", shopID);
                    String encodedStr = getEncodedData(dataToSend);

                    String urlString = "https://nacl.000webhostapp.com/addFood.php";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //Writing dataToSend to outputstreamwriter
                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(encodedStr);
                    writer.flush();
                    writer.close();

                    Message msg = Message.obtain();
                    InputStream inputStream = connection.getInputStream();//傳送資料&抓取回傳值
                    String readStream = readStrem(inputStream);
                    if(readStream.equals("1"))
                    {
                        msg.what = Values.Web.ADD_FOOD_SUCC;
                    }
                    else
                    {
                        msg.what = Values.Web.ADD_FOOD_FAIL;
                    }
                    msg.obj = context;
                    mHandler.sendMessage(msg);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void getFoodList(final Context context, final String shopID, final boolean isShop)
    {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Map<String,String> dataToSend = new HashMap<>();
                    dataToSend.put("shop_id", shopID);

                    String encodedStr = getEncodedData(dataToSend);

                    String urlString = "https://nacl.000webhostapp.com/getFoodList.php";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //Writing dataToSend to outputstreamwriter
                    connection.setDoOutput(true);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                    writer.write(encodedStr);
                    writer.flush();
                    writer.close();

                    String inputStr ="";
                    String jsonStr = "";
                    Message msg = Message.obtain();
                    InputStream inputStream = connection.getInputStream();
                    String readStream = readStrem(inputStream);
//                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//                    while ((inputStr = streamReader.readLine()) != null){
//                        jsonStr = jsonStr + inputStr;
//                    }
                    msg.what = Values.Web.LOAD_FOOD_SUCC;
                    if(isShop)
                        msg.what = Values.Web.LOAD_SHOPFOOD_SUCC;

                    msg.obj = context;
                    Bundle bundle = new Bundle();
                    bundle.putString("json", readStream);
                    msg.setData(bundle);
                    mHandler.sendMessage(msg);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
