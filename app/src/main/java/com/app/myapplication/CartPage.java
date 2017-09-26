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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
	ListView mListView;
	ProgressDialog mProgress;
	ArrayList<String> mItemList = new ArrayList<>();
	ArrayList<String> mContectList = new ArrayList<>();
	ArrayAdapter<String> mArrayAdapter;
	MemberMgr mMemberMgr = MemberMgr.getInstance();
	Context mMyContext = CartPage.this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_page);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		//==========================================================================

		getSupportActionBar().setTitle("訂單");

		mListView = (ListView) findViewById(R.id.listView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(CartPage.this, DetailPage.class);

				//new一個Bundle物件，並將要傳遞的資料傳入
				Bundle bundle = new Bundle();
				bundle.putString("contect", mContectList.get(position));
				intent.putExtras(bundle);

				//切換Activity
				startActivity(intent);
			}
		});
	}

	public void createOrder(String saleID, String date, String name, String phone, String addr,
							String tPrice, String shopID, String contect)
	{
		LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		ListView listView = new ListView(this);
		listView.setLayoutParams(listParams);

		String shopName = "麥當勞";
		switch (Integer.valueOf(shopID))
		{
			case 1:
				shopName = "麥當勞";
				break;
			case 2:
				shopName = "舊式炭烤";
				break;
			case 3:
				shopName = "批薩工廠";
				break;
			case 4:
				shopName = "意大利冰淇淋";
				break;
			case 5:
				shopName = "茶湯會";
				break;
			case 6:
				shopName = "春水堂";
				break;
			case 7:
				shopName = "十勝生乳卷";
				break;
			case 8:
				shopName = "摩斯漢堡";
				break;
		}

		String listStr = "\n" + "訂單號碼: " + saleID + "\n" +
				"訂單日期: " + date + "\n" +
				"商家: " + shopName + "\n" +
				"總金額: " + tPrice + "\n" +
				"收件人: " + name + "\n" +
				"手機: " + phone + "\n" +
				"地址: " + addr + "\n";

		mItemList.add(listStr);
		mContectList.add(contect);

//		String[] mainArr = contect.split("&");
//		String[] item = new String[10];
//		String[] item = new String[mainArr.length];
//		for(int i = 0; i < mainArr.length; i++)
//		{
//			String obj = mainArr[i];
//			String[] objArr = obj.split("!");
//			String objName = objArr[0];
//			String objAmount = objArr[1];
//			item[i] = objName + " x" + objAmount;
//		}
//		for(int i = 0; i < 10; i++)
//		{
//			item[i] = "超級達無敵大漢堡" + " x" + i + "\n" + "DUDUDDUDU";
//		}
	}

	public void createOrder2(String saleID, String date, String name, String phone, String addr,
							String tPrice, String shopID, String contect)
	{
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.VERTICAL);
//		mMainLayout.addView(layout);

		TextView saleIDView = new TextView(this);
		saleIDView.setText("訂單號碼: " + saleID);
		layout.addView(saleIDView);

		TextView dateView = new TextView(this);
		dateView.setText("訂單日期: " + date);
		layout.addView(dateView);

		String shopName = "麥當勞";
		switch (Integer.valueOf(shopID))
		{
			case 1:
				shopName = "麥當勞";
				break;
			case 2:
				shopName = "舊式炭烤";
				break;
			case 3:
				shopName = "批薩工廠";
				break;
			case 4:
				shopName = "意大利冰淇淋";
				break;
			case 5:
				shopName = "茶湯會";
				break;
			case 6:
				shopName = "春水堂";
				break;
			case 7:
				shopName = "十勝生乳卷";
				break;
			case 8:
				shopName = "摩斯漢堡";
				break;
		}

		TextView shopView = new TextView(this);
		shopView.setText(shopName);
		layout.addView(shopView);

		TextView priceView = new TextView(this);
		priceView.setText("總金額: " + tPrice);
		layout.addView(priceView);

		TextView nameView = new TextView(this);
		nameView.setText("收件人: " + name);
		layout.addView(nameView);

		TextView phoneView = new TextView(this);
		phoneView.setText("手機: " + phone);
		layout.addView(phoneView);

		TextView addrView = new TextView(this);
		addrView.setText("地址: " + addr);
		layout.addView(addrView);

		LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		listParams.height = 200;
		ListView listView = new ListView(this);
		listView.setLayoutParams(params);

		String[] mainArr = contect.split("&");
		String[] item = new String[10];
//		String[] item = new String[mainArr.length];
//		for(int i = 0; i < mainArr.length; i++)
//		{
//			String obj = mainArr[i];
//			String[] objArr = obj.split("!");
//			String objName = objArr[0];
//			String objAmount = objArr[1];
//			item[i] = objName + " x" + objAmount;
//		}
		for(int i = 0; i < 10; i++)
		{
			item[i] = "超級達無敵大漢堡" + " x" + i + "\n" + "DUDUDDUDU";
		}

		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
		listView.setAdapter(adapter);
		layout.addView(listView);
	}

	private void showOrderList(String json)
	{
		try
		{
			mItemList.clear();
			mContectList.clear();

			JSONObject jsonObj = new JSONObject(json);
			JSONArray mJsonArray = jsonObj.getJSONArray("result");

			for(int i = 0; i < mJsonArray.length(); i++)
			{
				JSONObject c = mJsonArray.getJSONObject(i);
				String id = c.getString("sale_id");
				String date = c.getString("sale_date");
				String shopID = c.getString("shop_id");
				String price = c.getString("total_price");
				String name = c.getString("name");
				String phone = c.getString("phone");
				String addr = c.getString("addr");
				String contect = c.getString("contect");

				createOrder(id, date, name, phone, addr, price, shopID, contect);
			}

			mArrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1,
					mItemList);
			mListView.setAdapter(mArrayAdapter);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public void onGetOrderSucc(String json)
	{
		mProgress.dismiss();
		showOrderList(json);
	}

	public void onGetOrderFail()
	{
		mProgress.dismiss();
		Toast.makeText(this, "Load Fail", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if(mMemberMgr.getWho() == Values.who.BUYER)
		{
			if (mMemberMgr.isNeedLoadBuyerOrder())
			{
				mMemberMgr.setIsNeedLoadBuyerOrder(false);
				mProgress = new ProgressDialog(CartPage.this);
				mProgress.setTitle("訂單讀取中");
				mProgress.setMessage("請稍後...");
				mProgress.setCancelable(false);
				mProgress.show();

				ConnDB.getInstance().getOrder(CartPage.this, "acc", mMemberMgr.getAccount());
			}
		}
		else
		{
			if (mMemberMgr.isNeedLoadSaleOrder())
			{
				mMemberMgr.setIsNeedLoadSaleOrder(false);
				mProgress = new ProgressDialog(CartPage.this);
				mProgress.setTitle("訂單讀取中");
				mProgress.setMessage("請稍後...");
				mProgress.setCancelable(false);
				mProgress.show();

				ConnDB.getInstance().getOrder(CartPage.this, "shop_id", mMemberMgr.getShopID());
			}
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
		getMenuInflater().inflate(R.menu.cart_page, menu);
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
