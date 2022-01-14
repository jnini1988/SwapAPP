package com.bignerdranch.android.swapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {
    private TextView mUsernameView;
    private TextView mEmailView;
    private ImageView mProfileView;
    //Todo: allow user to change profile, need to add profile to user database
    //maybe have loginactivity pass userid, then in profile page, use readable database each time
    private Button mHomeButton;
    FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this);

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
        Intent intent = getIntent();
        String username = intent.getExtras().getString("key_name");
        String email = intent.getExtras().getString("key_email");
        mUsernameView.setText(username);
        mEmailView.setText(email);

        mHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
