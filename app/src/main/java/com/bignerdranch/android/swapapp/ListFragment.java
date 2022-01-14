package com.bignerdranch.android.swapapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment{
    private Button mEmptyViewAddButton,mProfileButton;
    private DBHelper mDatabase;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_list, container,false);
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.listRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mDatabase = new DBHelper(getActivity());
        ArrayList<Item> allItem = mDatabase.getListItem();
        boolean empty=true;
        for(Item i:allItem){
            if(i.isTrade()==0){
                empty=false;
            }
        }
        if (!empty) {
            recyclerView.setVisibility(View.VISIBLE);
            ListAdapter mAdapter = new ListAdapter(allItem,getActivity());
            recyclerView.setAdapter(mAdapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Seem like there's nothing in for trade.", Toast.LENGTH_LONG).show();
        }

        mEmptyViewAddButton = (Button) view.findViewById(R.id.empty_view_add_button);
        mEmptyViewAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Add.class);
                startActivity(intent);
            }
        });

        //TODO: bug here, starting profile activity will ask for intent with extra
        mProfileButton=(Button) view.findViewById(R.id.profile_button);
        mProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ProfileActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

