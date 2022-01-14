package com.bignerdranch.android.swapapp;

import android.graphics.Bitmap;

public class User {
    private String username, email, password;
    private Bitmap mBitmap;

    public User(String name, String mEmail, String mPassword){
        username=name;
        email=mEmail;
        password=mPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }
}
