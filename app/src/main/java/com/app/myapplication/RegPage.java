package com.app.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegPage extends AppCompatActivity {

    ConnDB mConnDB = ConnDB.getInstance();
    EditText mAccEdit;
    EditText mPwdEdit;
    EditText mNameEdit;
    EditText mPhoneEdit;
    EditText mAddrEdit;
    CheckBox mRegisterCheck;
    ProgressDialog mProgress;
    String mAcc = "";
    String mPwd = "";
    String mName = "";
    String mPhone = "";
    String mAddr = "";
    boolean mIsCheckAcc = false;
    boolean mIsCheckPhone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);

        //====================================================================================

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("會員注冊");

        mAccEdit = (EditText)findViewById(R.id.editAcc);
        mAccEdit.addTextChangedListener(new TextWatcher()
        {
            private final Pattern sPattern = Pattern.compile("^[a-z0-9_-]{3,16}$");
            private boolean isValid(CharSequence s)
            {
                return sPattern.matcher(s).matches();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                if (isValid(s))
                    mAccEdit.setError(null);
                else
                    mAccEdit.setError("格式不正確");
            }
        });
//        mAccEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                    actionId == EditorInfo.IME_ACTION_DONE ||
//                    actionId == EditorInfo.IME_ACTION_NEXT)
//                {
//                    if(checkSQLHaveData("acc", mAccEdit.getText().toString(), mAccEdit, "此帳號已被注冊"))
//                        return true;
//                }
//                return false;
//            }
//        });
        mPwdEdit = (EditText)findViewById(R.id.editPwd);
        mPwdEdit.addTextChangedListener(new TextWatcher()
        {
            private final Pattern sPattern = Pattern.compile("^[a-zA-Z0-9_-]{3,16}$");
            private boolean isValid(CharSequence s)
            {
                return sPattern.matcher(s).matches();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                if (isValid(s))
                    mPwdEdit.setError(null);
                else
                    mPwdEdit.setError("格式不正確");
            }
        });
        mNameEdit = (EditText)findViewById(R.id.editName);
        mPhoneEdit = (EditText)findViewById(R.id.editPhone);
        mPhoneEdit.addTextChangedListener(new TextWatcher()
        {
            private final Pattern sPattern = Pattern.compile("^09\\d{2}-?\\d{3}-?\\d{3}$");
            private boolean isValid(CharSequence s)
            {
                return sPattern.matcher(s).matches();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }
            @Override
            public void afterTextChanged(Editable s)
            {
                if (isValid(s))
                    mPhoneEdit.setError(null);
                else
                    mPhoneEdit.setError("格式不正確");
            }
        });
        mAddrEdit = (EditText)findViewById(R.id.editAddr);

        mRegisterCheck = (CheckBox) findViewById(R.id.registerCheck);

        Button btn = (Button)findViewById(R.id.editBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAcc = "";
                mPwd = "";
                mName = "";
                mPhone = "";
                mAddr = "";
                if(mAccEdit.getText().toString().trim().equals(""))
                {
                    mAccEdit.setError("請填寫帳號");
                    return;
                }
                if(mPwdEdit.getText().toString().trim().equals(""))
                {
                    mPwdEdit.setError("請填寫密碼");
                    return;
                }
                if(mNameEdit.getText().toString().trim().equals(""))
                {
                    mNameEdit.setError("請填寫姓名");
                    return;
                }
                if(mPhoneEdit.getText().toString().trim().equals(""))
                {
                    mPhoneEdit.setError("請填寫電話");
                    return;
                }
                if(mAddrEdit.getText().toString().trim().equals(""))
                {
                    mAddrEdit.setError("請填寫地址");
                    return;
                }
                if(!mAccEdit.getError().toString().equals("") ||
                   !mPwdEdit.getError().toString().equals("") ||
                   !mPhoneEdit.getError().toString().equals(""))
                {
                    mAddrEdit.setError("格式不正確");
                    return;
                }

                if(!mRegisterCheck.isChecked())
                {
                    mRegisterCheck.setError("");
                    Toast.makeText(RegPage.this, "請遵守會員守則", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAcc = mAccEdit.getText().toString();
                mPwd = mPwdEdit.getText().toString();
                mName = mNameEdit.getText().toString();
                mPhone = mPhoneEdit.getText().toString();
                mAddr = mAddrEdit.getText().toString();

                mProgress = new ProgressDialog(RegPage.this);
                mProgress.setTitle("注冊中");
                mProgress.setMessage("請稍後...");
                mProgress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                mProgress.show();

                mIsCheckAcc = false;
                mIsCheckPhone = false;
                mConnDB.checkSQLHaveData(RegPage.this, Values.Web.REG_CHECK_ACC, "acc", mAcc);
//                mConnDB.checkSQLHaveData(RegPage.this, Values.Web.REG_CHECK_PHONE, "phone", mPhone);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void onRegSucc()
    {
        mProgress.dismiss();
        Toast.makeText(RegPage.this, "注冊成功", Toast.LENGTH_SHORT).show();
    }

    public void onCheckAccSucc()
    {
        Log.e("REG", "AccSucc");
        mIsCheckAcc = true;
        checkIsCanReg();
    }

    public void onCheckPhoneSucc()
    {
        Log.e("REG", "PhoneSucc");
        mIsCheckPhone = true;
        checkIsCanReg();
    }

    private void checkIsCanReg()
    {
        if(mIsCheckAcc)// && mIsCheckPhone)
            mConnDB.register(RegPage.this, mAcc, mPwd, mName, mPhone, mAddr);
    }

    public void onSameAcc()
    {
        mProgress.dismiss();
        String msg = "此帳號已被注冊";
        mAccEdit.setError(msg);
        mAccEdit.requestFocus();
        Toast.makeText(RegPage.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onSamePhone()
    {
        String msg = "此手機已被注冊";
        mPhoneEdit.setError(msg);
        mPhoneEdit.requestFocus();
        Toast.makeText(RegPage.this, msg, Toast.LENGTH_SHORT).show();
    }

}
