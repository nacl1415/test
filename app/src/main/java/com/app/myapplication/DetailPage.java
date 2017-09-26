package com.app.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailPage extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> mItemList = new ArrayList<>();
    ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        //==========================================================

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("明細");

        mListView = (ListView) findViewById(R.id.listView);

        Bundle bundle = getIntent().getExtras();
        String contect = bundle.getString("contect");
        String[] mainArr = contect.split("&");
		for(int i = 0; i < mainArr.length; i++)
		{
			String obj = mainArr[i];
			String[] objArr = obj.split("!");
			String objName = objArr[0];
			String objAmount = objArr[1];
            mItemList.add(objName + " x" + objAmount);
		}
        mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                mItemList);
        mListView.setAdapter(mArrayAdapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
