package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
	MemberMgr mMemberMgr = MemberMgr.getInstance();
	Context mMyContext = UploadPage.this;
	ProgressDialog mProgress;
	LinearLayout mMainLayout;
	ImgCreater mImgCreater = ImgCreater.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_page);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
				startActivity(new Intent(UploadPage.this, AddFoodPage.class));
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		//==============================================================================

		getSupportActionBar().setTitle("餐點上架");

		mMainLayout = (LinearLayout)findViewById(R.id.mainLayout);
//		mTextView = (TextView)findViewById(R.id.textView);
//		createFood("AA", "1", "a00", "1");
//		createFood("AA", "2", "a01", "1");
//		createFood("AA", "3", "a02", "1");
//		createFood("AA", "4", "a03", "1");
//		createFood("AA", "5", "a04", "1");
	}

	@Override
	protected void onResume() {
		super.onResume();

		mProgress = new ProgressDialog(UploadPage.this);
		mProgress.setTitle("資料讀取中");
		mProgress.setMessage("請稍後...");
		mProgress.setCancelable(false); // disable dismiss by tapping outside of the dialog
		mProgress.show();

		ConnDB.getInstance().getFoodList(UploadPage.this, MemberMgr.getInstance().getShopID(), false);
	}

	private void createFood(String name, String price, String imgName, String shopID)
	{
		int imgID = mImgCreater.getImgID(UploadPage.this, imgName);
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
		mMainLayout.addView(layout);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), imgID, options);

		ImageView img = new ImageView(this);
		img.setImageBitmap(mImgCreater.decodeSampledBitmapFromResource(getResources(), imgID));
		img.setAdjustViewBounds(true);
		img.setScaleType(ImageView.ScaleType.FIT_CENTER);
		layout.addView(img);

		LinearLayout layout2 = new LinearLayout(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.BOTTOM;
		params2.height = 70;
		layout2.setLayoutParams(params2);
		layout2.setOrientation(LinearLayout.HORIZONTAL);
		layout2.setBackgroundResource(R.drawable.objbg00);
		layout.addView(layout2);

		LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1);
		TextView nameText = new TextView(this);
		nameText.setLayoutParams(textParam);
		nameText.setText(name);
		nameText.setTextSize(20);
		nameText.setTextColor(Color.WHITE);
		layout2.addView(nameText);

		TextView priceText = new TextView(this);
		priceText.setLayoutParams(textParam);
		priceText.setText("$" + price);
		priceText.setTextSize(20);
		priceText.setTextColor(Color.WHITE);
		layout2.addView(priceText);
		priceText.setGravity(Gravity.RIGHT);
	}

	public void onLoadFoodSucc(String json)
	{
		mProgress.dismiss();
		showFoodList(json);
	}

	protected void showFoodList(String json)
	{
		try
		{
			JSONObject jsonObj = new JSONObject(json);
			JSONArray mJsonArray = jsonObj.getJSONArray("result");

			for(int i = 0; i < mJsonArray.length(); i++)
			{
				JSONObject c = mJsonArray.getJSONObject(i);
				String name = c.getString("prod_name");
				String price = c.getString("prod_price");
				String img = c.getString("img_name");
				String shopID = c.getString("shop_id");

				createFood(name, price, img, shopID);
//				mTextView.setText(
//					mTextView.getText().toString() + "N:" + name + ", P:" +
//							price +", I:" + img + "\n");
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public void onLoadFoodFail()
	{
		mProgress.dismiss();
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
		getMenuInflater().inflate(R.menu.upload_page, menu);
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
		if(id == R.id.action_settings)
		{
			return true;
		}

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
			mMemberMgr.gotoPage(mMyContext, 0);
		}
		else if(id == R.id.nav_gallery)
		{
			mMemberMgr.gotoPage(mMyContext, 1);
		}
		else if(id == R.id.nav_slideshow)
		{
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
