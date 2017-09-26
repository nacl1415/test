package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MemberMgr mMemberMgr = MemberMgr.getInstance();
    Context mMyContext = RegisterPage.this;
    ConnDB mConnDB = ConnDB.getInstance();
    EditText mAccEdit;
    EditText mPwdEdit;
    EditText mNameEdit;
    EditText mPhoneEdit;
    EditText mAddrEdit;
    CheckBox mRegisterCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //====================================================================================

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

                if(!mRegisterCheck.isChecked())
                {
                    mRegisterCheck.setError("");
                    Toast.makeText(RegisterPage.this, "請遵守會員守則", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String acc = mAccEdit.getText().toString();
                final String pwd = mPwdEdit.getText().toString();
                final String name = mNameEdit.getText().toString();
                final String phone = mPhoneEdit.getText().toString();
                final String addr = mAddrEdit.getText().toString();

                final ProgressDialog progress = new ProgressDialog(RegisterPage.this);
                progress.setTitle("注冊中");
                progress.setMessage("請稍後...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mConnDB.checkSQLHaveData(RegisterPage.this, Values.Web.REG_SAME_ACC, "acc", acc);
                        mConnDB.checkSQLHaveData(RegisterPage.this, Values.Web.REG_SAME_PHONE, "phone", phone);

                        //register suss!
                        mConnDB.register(RegisterPage.this, acc, pwd, name, phone, addr);
                        progress.dismiss();
                    }
                }, 100);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.home)
        {
            finish();
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_camera)
        {
            mMemberMgr.gotoPage(mMyContext, 0);
        }
        else if(id == R.id.nav_gallery)
        {
            mMemberMgr.gotoPage(mMyContext, 1);
        }
        else if(id == R.id.nav_slideshow)
        {
            mMemberMgr.gotoPage(mMyContext, 2);
        }
        else if(id == R.id.nav_manage)
        {
            mMemberMgr.gotoPage(mMyContext, 3);
        }
        else if(id == R.id.nav_share)
        {
            mMemberMgr.gotoPage(mMyContext, 4);
        }
        else if(id == R.id.nav_send)
        {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}