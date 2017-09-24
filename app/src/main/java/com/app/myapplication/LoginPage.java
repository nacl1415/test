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

public class LoginPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView mAccEdit;
    TextView mPwdEdit;
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

        mAccEdit = (EditText)findViewById(R.id.login_editAcc);
        mPwdEdit = (EditText)findViewById(R.id.editPwd);

        Button btn = (Button)findViewById(R.id.login_loginBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                conn.login(LoginPage.this, acc, pwd);
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

    public void onLoginSucc()
    {
        Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();
        mProgress.dismiss();
    }

    public void onLoginFail()
    {
        Toast.makeText(this, "FAIL", Toast.LENGTH_SHORT).show();
        mProgress.dismiss();
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
