package com.app.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class AddFoodPage extends AppCompatActivity {

    final int SELECT_PICTURE = 1;
    ProgressDialog mProgress;
    EditText mEditName;
    EditText mEditPrice;
    ImageView mImageView;
    TextView mInfoText;
    String mImgName = "";

    private int serverResponseCode = 0;
    private String upLoadServerUri = null;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_page);

        //=================================================

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("編輯餐點");

        mEditName = (EditText) findViewById(R.id.editName);
        mEditPrice = (EditText) findViewById(R.id.editPrice);
        mEditPrice.addTextChangedListener(new TextWatcher()
        {
            private final Pattern sPattern = Pattern.compile("^[0-9]{1,9}$");
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
                    mEditPrice.setError(null);
                else
                    mEditPrice.setError("格式不正確");
            }
        });
        mImageView = (ImageView) findViewById(R.id.uploadImgView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        Button btn = (Button)findViewById(R.id.editBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String price = mEditPrice.getText().toString();
                mProgress = new ProgressDialog(AddFoodPage.this);
                mProgress.setTitle("上傳中");
                mProgress.setMessage("請稍後...");
                mProgress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                mProgress.show();
                ConnDB.getInstance().addFood(AddFoodPage.this, name, price, mImgName);
            }
        });

        mInfoText = (TextView) findViewById(R.id.infoText);
    }

    public void onAddFoodSucc()
    {
        mProgress.dismiss();
        Toast.makeText(this, "Succ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddFoodPage.this, UploadPage.class);
        startActivity(intent);
    }

    public void onAddFoodFail()
    {
        mProgress.dismiss();
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            String imagepath = getPath(selectedImageUri);
            Bitmap bitmap= BitmapFactory.decodeFile(imagepath);

            mImageView.setImageBitmap(bitmap);
            mImgName = getFileName(selectedImageUri);
            mInfoText.setText(mImgName);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        String[] array = result.split("\\.");

        return array[0];
    }
}
