package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MemberMgr mMemberMgr = MemberMgr.getInstance();
    Context mMyContext = LoginPage.this;
    EditText mAccEdit;
    EditText mPwdEdit;
    TextView mInfoView;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
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

        //=====================================================================================

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

                mProgress = new ProgressDialog(LoginPage.this);
                mProgress.setTitle("登入中");
                mProgress.setMessage("請稍後...");
                mProgress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                mProgress.show();

                ConnDB conn = ConnDB.getInstance();
                conn.login(LoginPage.this, acc, pwd, false);
            }
        });

        btn = (Button)findViewById(R.id.login_registerBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegPage.class);
                startActivity(intent);
            }
        });
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

        mMemberMgr.gotoPage(mMyContext, Values.PageIndex.Index);
    }

    public void onLoginFail()
    {
        mProgress.dismiss();
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
        mInfoView.setText("登入失敗");
    }

    private void hideKeyboard() {
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
        }
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
        getMenuInflater().inflate(R.menu.login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            mMemberMgr.exitApp(mMyContext);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}