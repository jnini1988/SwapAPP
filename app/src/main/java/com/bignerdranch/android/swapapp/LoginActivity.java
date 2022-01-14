package com.bignerdranch.android.swapapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.AppCompatTextView;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mSignInButton;
    private AppCompatTextView mRegister;
    private FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDbHelper = new FeedReaderDbHelper(this);
        mEmailView = (EditText)findViewById(R.id.emailEditText);
        mPasswordView = (EditText)findViewById(R.id.passwordEditText);
        mSignInButton = (Button)findViewById(R.id.sign_in_button);
        mRegister = (AppCompatTextView) findViewById(R.id.register_link);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getReadableDatabase();
                //check that all fields are entered
                if(mPasswordView.getText().toString().trim().isEmpty() ||
                        mEmailView.getText().toString().trim().isEmpty()){
                    showMessage("Incomplete Field","Please enter both email and password");

                }
                //check if email and password match, if yes then switch to user profile page
                else {
                    Cursor c = db.rawQuery("SELECT * FROM UserDetails WHERE email='"
                                    + mEmailView.getText().toString().trim() + "'" +
                                    "AND password='"+mPasswordView.getText().toString().trim()+"'"
                            , null);

                    if (c.moveToFirst()) {
                        String loginUsername = c.getString(1);
                        String loginEmail = c.getString(2);

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.putExtra("key_name", loginUsername);
                        intent.putExtra("key_email", loginEmail);
                        User user=new User(loginUsername,loginEmail,mPasswordView.getText().toString());
                        user.storeUser(user);
                        startActivity(intent);
                        clearText();
                    }
                    else {
                        showMessage("Error", "Email and password does not match");
                        clearText();
                    }
                }
            }
        });

        //switch to register page
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(u);
            }
        });
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        mEmailView.setText("");
        mPasswordView.setText("");
    }
}