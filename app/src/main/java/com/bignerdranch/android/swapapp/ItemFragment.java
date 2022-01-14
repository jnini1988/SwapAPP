package com.bignerdranch.android.swapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemFragment extends Fragment {
    private TextView mViewName, mViewDes, mViewEmail,mViewDate;
    private Button trade, back;
    private ImageView mViewImage;
    private ArrayList<Item> mItemList;

    public ItemFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.view_item,container,false);
        DBHelper mDatabase=new DBHelper(getActivity());
        int position=getArguments().getInt("position");

        mItemList= mDatabase.getListItem();
        Item i=mItemList.get(position);
        mViewName=view.findViewById(R.id.view_name);
        mViewDes=view.findViewById(R.id.view_des);
        mViewEmail=view.findViewById(R.id.view_email);
        mViewDate=view.findViewById(R.id.view_date);
        mViewImage=view.findViewById(R.id.itemImage);

        mViewImage.setImageBitmap(i.getBitMap());
        mViewName.setText(i.getName());
        mViewDes.setText("Description: "+i.getDescription());
        mViewEmail.setText("Seller Email: "+i.getSellerEmail());
        mViewDate.setText("Posted on "+i.getDate());

        back = (Button) view.findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getFragmentManager().popBackStackImmediate();
            }
        });

        trade = (Button) view.findViewById(R.id.trade_button);
        trade.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                i.setTrade(1);
                mDatabase.tradeItem(i);
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

