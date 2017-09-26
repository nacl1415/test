package com.app.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MemberEditPage extends AppCompatActivity {

    MemberMgr mMemberMgr = MemberMgr.getInstance();
    EditText mEditAcc;
    EditText mEditOldPwd;
    EditText mEditNewPwd;
    EditText mEditName;
    EditText mEditPhone;
    EditText mEditAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_edit_page);

        //============================================================

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("會員資料修改");

        mEditAcc = (EditText)findViewById(R.id.editAcc);
        mEditAcc.setText(mMemberMgr.getAccount());
        mEditAcc.setEnabled(false);

        mEditOldPwd = (EditText)findViewById(R.id.editOldPwd);
        mEditNewPwd = (EditText)findViewById(R.id.editNewPwd);

        mEditName = (EditText)findViewById(R.id.editName);
        mEditName.setText(mMemberMgr.getName());

        mEditPhone = (EditText)findViewById(R.id.editPhone);
        mEditPhone.setText(mMemberMgr.getPhone());

        mEditAddr = (EditText)findViewById(R.id.editAddr);
        mEditAddr.setText(mMemberMgr.getAddr());

        Button btn = (Button)findViewById(R.id.editBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
