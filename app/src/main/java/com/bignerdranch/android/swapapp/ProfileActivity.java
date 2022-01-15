package com.bignerdranch.android.swapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private TextView mUsernameView;
    private TextView mEmailView;
    private TextView mNoPost;
    private Button mHomeButton,mLogoutButton;
    private ListView mPostedList;
    private static String username="", email="";
    private DBHelper mDatabase;


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

        mDatabase = new DBHelper(getApplicationContext());
        ArrayList<Item> allItem = mDatabase.getListItem();
        ArrayList<Item> postedItem=new ArrayList<Item>();
        for(Item i:allItem){
            if(i.getSellerEmail().equals(email)){
                postedItem.add(i);
            }
        }
        if(postedItem.size()>0){
            mPostedList=(ListView)findViewById(R.id.posted_list);
            postListAdapter customAdapter = new postListAdapter(this, R.layout.posted_item_row,postedItem);
            mPostedList.setAdapter(customAdapter);
        }
        else{
            mNoPost=(TextView)findViewById(R.id.noItem);
            mNoPost.setText("You have not posted anything yet");
        }

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
