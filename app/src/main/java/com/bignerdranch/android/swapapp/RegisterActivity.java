package com.bignerdranch.android.swapapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity{
    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mRePasswordView;
    private Button mRegisterButton;
    private Button mBackButton;
    private FeedReaderDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDbHelper = new FeedReaderDbHelper(this);

        mUsernameView = (EditText)findViewById(R.id.usernameEditText);
        mEmailView = (EditText)findViewById(R.id.emailEditText);
        mPasswordView = (EditText)findViewById(R.id.passwordEditText);
        mRePasswordView = (EditText)findViewById(R.id.repasswordEditText);
        mRegisterButton = (Button)findViewById(R.id.register_button);
        mBackButton = (Button)findViewById(R.id.back_button);

        //validate if account exist, if not write into database
        //show register success message and send user back to sign in page
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPasswordView.getText().toString().trim().isEmpty() ||
                        mEmailView.getText().toString().trim().isEmpty() ||
                        mUsernameView.getText().toString().trim().isEmpty()||
                        mRePasswordView.getText().toString().trim().isEmpty()){
                    showMessage("Incomplete fields","Make sure you fill in all required fields");
                }
                else {
                    //check if account already exists
                    SQLiteDatabase readDB = mDbHelper.getReadableDatabase();
                    Cursor cursor = readDB.rawQuery("SELECT * FROM UserDetails WHERE email='"
                            + mEmailView.getText().toString().trim() + "'", null);
                    if (!(cursor.getCount() <= 0)) {
                        cursor.close();
                        showMessage("Registration fail", "An account is already registered using this email");
                    }

                    //add user info into database
                    else {
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        String username = mUsernameView.getText().toString().trim();
                        String email = mEmailView.getText().toString().trim();
                        String password = mPasswordView.getText().toString().trim();
                        String confirm = mRePasswordView.getText().toString().trim();
                        if(password.equals(confirm)) {
                            values.put(FeedReaderContract.FeedEntry.COLUMN_USERNAME, username);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, email);
                            values.put(FeedReaderContract.FeedEntry.COLUMN_PASSWORD, password);
                            showMessage("Registration Success", "Your account has been registered!");
                            clearText();

                            long newRowId = db.insert(
                                    FeedReaderContract.FeedEntry.TABLE_NAME,
                                    FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE, values);
                            Intent u = new Intent(RegisterActivity.this, com.bignerdranch.android.swapapp.LoginActivity.class);
                            startActivity(u);
                        }
                        else{
                            showMessage("Registration fail", "Please make sure your passwords match");
                        }
                    }
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent u = new Intent(RegisterActivity.this, com.bignerdranch.android.swapapp.LoginActivity.class);
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
