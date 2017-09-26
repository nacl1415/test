package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.app.myapplication.R.id.editAddr;
import static com.app.myapplication.R.id.editName;
import static com.app.myapplication.R.id.editPhone;

public class PayPage extends AppCompatActivity {

    MemberMgr mMemberMgr = MemberMgr.getInstance();
    AlertDialog.Builder mDialog;
    ProgressDialog mProgress;
    EditText mEditName;
    EditText mEditPhone;
    EditText mEditAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_page);

        //============================================

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("確認訂單");

        mEditName = (EditText)findViewById(editName);
        mEditPhone = (EditText)findViewById(editPhone);
        mEditAddr = (EditText)findViewById(editAddr);
        TextView totalPriceView = (TextView)findViewById(R.id.totalPriceView);

        mEditName.setText(mMemberMgr.getName());
        mEditPhone.setText(mMemberMgr.getPhone());
        mEditAddr.setText(mMemberMgr.getAddr());
        totalPriceView.setText("總價:" + mMemberMgr.getTotalPrice());

        ArrayList<FoodObj> list = mMemberMgr.getFoodList();
        String[] item = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            FoodObj obj = list.get(i);
            item[i] = obj.getName() + " ($" + obj.getPrice() + ")  x" + obj.getAmount() + " = " + obj.getTotalPrice();
        }

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
        listView.setAdapter(adapter);

        mDialog = new AlertDialog.Builder(PayPage.this);
        mDialog.setTitle("最後一步");
        mDialog.setMessage("是否確認購買商品?");
        mDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgress = new ProgressDialog(PayPage.this);
                mProgress.setTitle("訂單成立中");
                mProgress.setMessage("請稍後...");
                mProgress.setCancelable(false);
                mProgress.show();

                ConnDB.getInstance().sendOrder(PayPage.this,
                        mMemberMgr.getAccount(),
                        mMemberMgr.getSelectShopID(),
                        mMemberMgr.getTotalPrice() + "",
                        mEditName.getText().toString(),
                        mEditPhone.getText().toString(),
                        mEditAddr.getText().toString(),
                        mMemberMgr.getFoodList());
            }
        });
        mDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        Button buyBtn = (Button)findViewById(R.id.buyBtn);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    public void onSendOrderSucc()
    {
        mProgress.dismiss();
        mMemberMgr.setIsNeedLoadBuyerOrder(true);
        Toast.makeText(this, "SUCC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PayPage.this, CartPage.class);
        startActivity(intent);
    }

    public void onSendOrderFail()
    {
        mProgress.dismiss();
        Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
