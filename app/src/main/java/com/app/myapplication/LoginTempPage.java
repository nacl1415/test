package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginTempPage extends AppCompatActivity {

    MemberMgr mMemberMgr = MemberMgr.getInstance();
    Context mMyContext = LoginTempPage.this;
    EditText mAccEdit;
    EditText mPwdEdit;
    TextView mInfoView;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp_page);

        //====================================

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("會員登入");

        mAccEdit = (EditText)findViewById(R.id.login_editAcc);
        mPwdEdit = (EditText)findViewById(R.id.editPwd);
        mInfoView = (TextView)findViewById(R.id.infoView);

        Button btn = (Button)findViewById(R.id.login_loginBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoView.setText("");
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

                hideKeyboard();

                final String acc = mAccEdit.getText().toString();
                final String pwd = mPwdEdit.getText().toString();
                //Toast.makeText(LoginPage.this, "Acc:" + acc + ", Pwd:" + pwd, Toast.LENGTH_SHORT).show();

                mProgress = new ProgressDialog(mMyContext);
                mProgress.setTitle("登入中");
                mProgress.setMessage("請稍後...");
                mProgress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                mProgress.show();

                ConnDB conn = ConnDB.getInstance();
                conn.login(mMyContext, acc, pwd, true);
            }
        });

        btn = (Button)findViewById(R.id.login_registerBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mMyContext, RegPage.class);
                startActivity(intent);
            }
        });
    }

    private void hideKeyboard() {
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
        }
    }

    public void onLoginSucc(String json)
    {
        mProgress.dismiss();
        Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();

        try
        {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray mJsonArray = jsonObj.getJSONArray("result");

            for(int i = 0; i < mJsonArray.length(); i++)
            {
                JSONObject c = mJsonArray.getJSONObject(i);
                String acc = c.getString("acc");
                String pwd = c.getString("pwd");
                String name = c.getString("name");
                String phone = c.getString("phone");
                String addr = c.getString("addr");
                String shopID = "1";

                mMemberMgr.login(acc, pwd, name, phone, addr, shopID);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        finish();
    }

    public void onLoginFail()
    {
        mProgress.dismiss();
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
        mInfoView.setText("登入失敗");
    }
}
