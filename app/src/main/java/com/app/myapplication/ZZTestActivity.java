package com.app.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ZZTestActivity extends AppCompatActivity
{
	float MAX_WIDTH = 550;
	float MAX_HEIGHT = 930;
	float MAX_SPEED = 20;
	int SOUND_INPUT = 9;

	SensorManager mSensor;
	SensorEventListener mSensorListener;
	Button mBtn;
	EditText mEditText;
	TextToSpeech mSpeech;
	TextView mTextView;
	Random mRandom = new Random(System.nanoTime());
	float mX = random(-20, 20);
	float mY = random(-20, 20);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zztest);

		ConstraintLayout main = (ConstraintLayout)findViewById(R.id.main);

		mSensor = (SensorManager)getSystemService(SENSOR_SERVICE);
		mSensorListener = new SensorEventListener()
		{
			@Override
			public void onSensorChanged(SensorEvent sensorEvent)
			{
				float speedModify = 5;
				float v0 = sensorEvent.values[0];
				float v1 = sensorEvent.values[1];
				float v2 = sensorEvent.values[2];

				mX = mX + random(-10, 10);
				if(mX > MAX_SPEED)
					mX = MAX_SPEED;
				else if(mX < -MAX_SPEED)
					mX = -MAX_SPEED;

				mY = mY + random(-10, 10);
				if(mY > MAX_SPEED)
					mY = MAX_SPEED;
				else if(mY < -MAX_SPEED)
					mY = -MAX_SPEED;

				mBtn.setX(mBtn.getX() - mX * speedModify);
				mBtn.setY(mBtn.getY() + mY * speedModify);

				if(mBtn.getX() > 0 && mBtn.getX() < MAX_WIDTH)
					mBtn.setX(mBtn.getX() - mX * speedModify);
				else if(mBtn.getX() < 0)
				{
					mBtn.setX(0);
					mX = mX - MAX_SPEED;
				}
				else if(mBtn.getX() > MAX_WIDTH)
				{
					mBtn.setX(MAX_WIDTH);
					mX = mX + MAX_SPEED;
				}

				if(mBtn.getY() > 0 && mBtn.getY() < MAX_HEIGHT)
					mBtn.setY(mBtn.getY() + mY * speedModify);
				else if(mBtn.getY() < 0)
				{
					mBtn.setY(0);
					mY = mY + MAX_SPEED;
				}
				else if(mBtn.getY() > MAX_HEIGHT)
				{
					mBtn.setY(MAX_HEIGHT);
					mY = mY - MAX_SPEED;
				}

//				Log.e("Main", "x= " + mBtn.getX() + ", y= " + mBtn.getY());

			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int i)
			{

			}
		};

		mBtn = (Button)findViewById(R.id.button);
		float w = mBtn.getLayoutParams().width;
		float h = mBtn.getLayoutParams().height;
		Log.e("main", "w = " + w + ", h = " + h);

		mBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Toast.makeText(ZZTestActivity.this, "GGG", Toast.LENGTH_SHORT).show();
			}
		});


		mEditText = (EditText)findViewById(R.id.editText);
		mEditText.setSingleLine(false);
//		mEditText.setHorizontallyScrolling(false);
		Button speakBtn = (Button)findViewById(R.id.speakBtn);
		speakBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String str = mEditText.getText().toString();
				mSpeech.speak(str, TextToSpeech.QUEUE_FLUSH, null);
			}
		});
		mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener()
		{
			@Override
			public void onInit(int i)
			{
				if(i != TextToSpeech.ERROR)
					mSpeech.setLanguage(Locale.TAIWAN);
			}
		});

		mTextView = (TextView)findViewById(R.id.textView2);
		Button soundBtn = (Button)findViewById(R.id.button2);
		soundBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
				try {
					startActivityForResult(intent, SOUND_INPUT);
				} catch (ActivityNotFoundException a) {
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && null != data) {
			ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String str = result.get(0);
			str.replaceAll("\\s", "");
			mEditText.setText(str);
		}
	}

	public int random(int min, int max)
	{
		return min + mRandom.nextInt(max - min + 1);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mSensor.registerListener(mSensorListener,
				mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				mSensor.SENSOR_DELAY_NORMAL
		);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mSensor.unregisterListener(mSensorListener);
	}

	@Override
	public void onBackPressed()
	{
		Toast.makeText(ZZTestActivity.this, "NO BACK", Toast.LENGTH_SHORT).show();
	}
}
