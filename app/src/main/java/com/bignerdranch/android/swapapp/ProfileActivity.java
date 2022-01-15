package com.bignerdranch.android.swapapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {
    private TextView mUsernameView;
    private TextView mEmailView;
    private Button mHomeButton,mLogoutButton;
    private FeedReaderDbHelper mDbHelper;
    private static String username="", email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up profile info
        mUsernameView=(TextView)findViewById(R.id.username);
        mEmailView=(TextView)findViewById(R.id.email);
        mHomeButton=(Button)findViewById(R.id.home_button);
        mLogoutButton=(Button)findViewById(R.id.logout_button);
        Intent intent = getIntent();
        if(intent.hasExtra("key_name")&&intent.hasExtra("key_email")){
            username = intent.getExtras().getString("key_name");
            email = intent.getExtras().getString("key_email");
        }
        mUsernameView.setText(username);
        mEmailView.setText(email);


        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
