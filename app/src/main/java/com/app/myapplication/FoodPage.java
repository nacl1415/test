package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FoodPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

	ImgCreater mImgCreater = ImgCreater.getInstance();
	MemberMgr mMemberMgr = MemberMgr.getInstance();
	HashMap<String, FoodObj> mFoodList = new HashMap<>();

	ProgressDialog mProgress;
	LinearLayout mMainLayout;
	TextView mTotalPriceView;

	Context mMyContext = FoodPage.this;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_page);
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

		//==================================================

		getSupportActionBar().setTitle("瀏覽餐點");

		mMainLayout = (LinearLayout)findViewById(R.id.mainLayout);
		mTotalPriceView = (TextView) findViewById(R.id.tPriceView);
		Button payBtn = (Button)findViewById(R.id.payButton);
		payBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (Object key : mFoodList.keySet()) {
					FoodObj obj = mFoodList.get(key);
					if(obj.getAmount() > 0)
						mMemberMgr.addFoodObj(obj);
				}

				Intent intent = new Intent(mMyContext, PayPage.class);
				startActivity(intent);
			}
		});
//		createFood(R.drawable.a00);
//		createFood(R.drawable.a01);
//		createFood(R.drawable.a02);
//		createFood(R.drawable.a03);
//		createFood(R.drawable.a04);
//		createFood(R.drawable.a05);
//		createFood(R.drawable.a06);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mMemberMgr.clearFoodObj();
		mProgress = new ProgressDialog(FoodPage.this);
		mProgress.setTitle("資料讀取中");
		mProgress.setMessage("請稍後...");
		mProgress.setCancelable(false);
		mProgress.show();

		ConnDB.getInstance().getFoodList(FoodPage.this, MemberMgr.getInstance().getSelectShopID(), true);
	}

	private void createFood(final String prodID, String name, String price, String imgName, String shopID)
	{
		int imgID = mImgCreater.getImgID(FoodPage.this, imgName);
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
		TextView nameEdit = new TextView(this);
		nameEdit.setLayoutParams(textParam);
		nameEdit.setText(name);
		nameEdit.setTextSize(20);
		nameEdit.setTextColor(Color.WHITE);
		layout2.addView(nameEdit);

		TextView priceEdit = new TextView(this);
		priceEdit.setLayoutParams(textParam);
		priceEdit.setText("$" + price);
		priceEdit.setTextSize(20);
		priceEdit.setTextColor(Color.WHITE);
		layout2.addView(priceEdit);
		priceEdit.setGravity(Gravity.RIGHT);

		LinearLayout layout3 = new LinearLayout(this);
		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params3.height = 80;
		layout3.setLayoutParams(params3);
		layout3.setOrientation(LinearLayout.HORIZONTAL);
		layout3.setBackgroundResource(R.drawable.objbg01);
		layout.addView(layout3);

		final EditText amountEdit = new EditText(this);

		Button btn = new Button(this);
		btn.setLayoutParams(textParam);
        layout3.addView(btn);
		btn.setText("-");
		btn.setAlpha(0);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int amount = mFoodList.get(prodID).modifyAmount(-1);
				amountEdit.setText("" + amount);
				calcuTotalPrice();
			}
		});

		amountEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
		amountEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
		amountEdit.setText("0");
		amountEdit.setTextColor(Color.WHITE);
		amountEdit.setTextSize(20);
		amountEdit.setEms(6);
		amountEdit.setBackgroundColor(Color.TRANSPARENT);
		amountEdit.setGravity(Gravity.CENTER);
		layout3.addView(amountEdit);
		amountEdit.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				amountEdit.setText("");
				return false;
			}
		});
		amountEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textViewv, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT)
				{
					int amount = 0;
					try{
						amount = Integer.valueOf(textViewv.getText().toString());
					}catch (Exception e){
						amount = 0;
						textViewv.setText("0");
					}
					amount = mFoodList.get(prodID).setAmount(amount);
					textViewv.setText("" + amount);
					calcuTotalPrice();

					return false;
				}
				return false;
			}
		});

		btn = new Button(this);
		btn.setLayoutParams(textParam);
        layout3.addView(btn);
		btn.setText("+");
		btn.setAlpha(0);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int amount = mFoodList.get(prodID).modifyAmount(1);
				amountEdit.setText("" + amount);
				calcuTotalPrice();
			}
		});

		if(mFoodList.get(prodID) == null) {
			FoodObj foodObj = new FoodObj(prodID, name, price);
			mFoodList.put(prodID, foodObj);
		}
	}

	public void onLoadFoodSucc(String json)
	{
		mProgress.dismiss();
		showFoodList(json);
	}

	public void onLoadFoodFail()
	{
		mProgress.dismiss();
	}

	private void calcuTotalPrice()
	{
		int totalPrice = 0;
		for (Object key : mFoodList.keySet()) {
			totalPrice = totalPrice + mFoodList.get(key).getTotalPrice();
		}

		mTotalPriceView.setText("" + totalPrice);
	}

	private void showFoodList(String json)
	{
		try
		{
			JSONObject jsonObj = new JSONObject(json);
			JSONArray mJsonArray = jsonObj.getJSONArray("result");

			for(int i = 0; i < mJsonArray.length(); i++)
			{
				JSONObject c = mJsonArray.getJSONObject(i);
				String id = c.getString("prod_id");
				String name = c.getString("prod_name");
				String price = c.getString("prod_price");
				String img = c.getString("img_name");
				String shopID = c.getString("shop_id");

				createFood(id, name, price, img, shopID);
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
		getMenuInflater().inflate(R.menu.food_page, menu);
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
