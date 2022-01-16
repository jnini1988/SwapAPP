package com.bignerdranch.android.swapapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 4;
    private ImageButton mImageView;
    private EditText mItemName,mItemDes;
    private Button add,back;
    private DBHelper mDatabase;
    private boolean changeImage=false;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        mDatabase = new DBHelper(this);

        mItemName=findViewById(R.id.edit_name);
        mItemDes=findViewById(R.id.edit_des);

        mImageView=findViewById(R.id.itemImage);
        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeImage=true;
                Intent pickImage = new Intent(Intent.ACTION_GET_CONTENT);
                pickImage.setType("image/*");
                Intent chooser=Intent.createChooser(pickImage,"Pick Image");
                startActivityForResult(chooser, GET_FROM_GALLERY);
            }
        });

        add=findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=mItemName.getText().toString();
                final String des=mItemDes.getText().toString();

                if(name.trim().isEmpty() ||
                        des.trim().isEmpty() ||
                         changeImage==false){
                    showMessage("Incomplete fields","Make sure you fill in all required fields and add a picture");
                }
                else {
                    BitmapDrawable drawable=(BitmapDrawable)mImageView.getDrawable();
                    Bitmap bitmap=drawable.getBitmap();
                    String email=User.getInstance().getEmail();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
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

        public void showMessage (String title, String message)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }

    }
