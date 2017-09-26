package com.app.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
//	TextView mTextView;

	final int IMAGE_HEIGHT = 180;

	MemberMgr mMemberMgr = MemberMgr.getInstance();
	Context mMyContext = IndexActivity.this;
	JSONArray mJsonArray = null;
	String TAG_RESULT ="result";
	String TAG_ID = "id";
	String TAG_NAME = "Name";

	LinearLayout mMainLayout;
//	LinearLayout mLinearLayout;

	ImgCreater mImgCreater = ImgCreater.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
//				mTextView.setText("");
//				GetData();
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		//=============================================================================
        getSupportActionBar().setTitle("瀏覽商店");

		mMainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        createShop(R.drawable.shop00, "1");
        createShop(R.drawable.shop01, "2");
        createShop(R.drawable.shop02, "3");
		createShop(R.drawable.shop03, "4");
		createShop(R.drawable.shop04, "5");
		createShop(R.drawable.shop05, "6");
		createShop(R.drawable.shop06, "7");
//		createShop(R.drawable.shop07, "8");
	}

	private void createShop(final int imgID, final String shopID)
    {
		FrameLayout layout = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		mMainLayout.addView(layout);

        ImageView img = new ImageView(this);
		img.setImageBitmap(mImgCreater.decodeSampledBitmapFromResource(getResources(), imgID));
		img.setAdjustViewBounds(true);
        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
				MemberMgr.getInstance().setSelectShopID(shopID);
                Intent intent = new Intent(IndexActivity.this, FoodPage.class);
                startActivity(intent);
            }
        });
		layout.addView(img);

//		FrameLayout layoutBg = new FrameLayout(this);
//		params = new FrameLayout.LayoutParams(
//				FrameLayout.LayoutParams.MATCH_PARENT, 80);
//		params.gravity = Gravity.BOTTOM;
//		layoutBg.setLayoutParams(params);
//		layoutBg.setBackgroundColor(Color.BLACK);
//		layoutBg.setAlpha(0.7f);
//		layout.addView(layoutBg);
//
//		TextView textView = new TextView(this);
//		params = new FrameLayout.LayoutParams(
//				FrameLayout.LayoutParams.WRAP_CONTENT,
//				FrameLayout.LayoutParams.WRAP_CONTENT);
//		params.gravity = Gravity.BOTTOM|Gravity.CENTER;
//		textView.setLayoutParams(params);
//		layout.addView(textView);
//		textView.setTextSize(20);
//		textView.setTextColor(Color.WHITE);
//		textView.setText("MianDangLou");
    }

	protected void ShowNameList(String json)
	{
		try
		{
			JSONObject jsonObj = new JSONObject(json);
			mJsonArray = jsonObj.getJSONArray("result");

			for(int i = 0; i < mJsonArray.length(); i++)
			{
				JSONObject c = mJsonArray.getJSONObject(i);
				String acc = c.getString("acc");
				String pwd = c.getString("pwd");
				String name = c.getString("name");
				String phone = c.getString("phone");
				String addr = c.getString("addr");

//				mTextView.setText(
//					mTextView.getText().toString() + "acc:" + acc + ", pwd:" +
//					pwd +", Name:" + name + "\n");
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if(drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		} else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
//		if(id == R.id.action_settings)
//		{
//			return true;
//		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if(id == R.id.nav_camera)
		{
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
