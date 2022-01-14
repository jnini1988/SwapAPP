package com.bignerdranch.android.swapapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;
    private ImageButton mImageView;
    private EditText mItemName,mItemDes,mEmail;
    private Button add,back;
    private DBHelper mDatabase;

    //TODO: shouldn't ask user to add email, should retreive email of this account (do after combining)
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        mDatabase = new DBHelper(this);

        mItemName=findViewById(R.id.edit_name);
        mItemDes=findViewById(R.id.edit_des);
        mEmail=findViewById(R.id.edit_email);

        mImageView=findViewById(R.id.itemImage);
        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent getIntent=new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent=new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent=Intent.createChooser(getIntent,"Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[] {pickIntent});

                startActivityForResult(chooserIntent,GET_FROM_GALLERY);
            }
        });

        add=findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=mItemName.getText().toString();
                final String des=mItemDes.getText().toString();
                final String email=mEmail.getText().toString();

                BitmapDrawable drawable=(BitmapDrawable)mImageView.getDrawable();
                Bitmap bitmap=drawable.getBitmap();

                if(name.trim().isEmpty() ||
                        des.trim().isEmpty() ||
                        email.trim().isEmpty()){
                    showMessage("Incomplete fields","Make sure you fill in all required fields");
                }
                else {
                    Item i = new Item(name,des,email,bitmap);
                    mDatabase.addItems(i);
                    finish();
                    startActivity(getIntent());
                    Intent u = new Intent(Add.this, MainActivity.class);
                    startActivity(u);
                }
            }
        });

        back=findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Add.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY) {
            try{
                InputStream inputStream=getContentResolver().openInputStream(data.getData());
                Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                BitmapDrawable bitmapDrawable=new BitmapDrawable((bitmap));
                mImageView.setImageDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
