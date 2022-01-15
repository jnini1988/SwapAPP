package com.bignerdranch.android.swapapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context context;
    private ArrayList<Item> listItems;

    public ListAdapter(ArrayList<Item> listItems, Context context) {
        this.context=context;
        this.listItems=listItems;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ListViewHolder holder=new ListViewHolder(view);
        return holder;
    }

    //TODO: trim description if it is too long
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final Item i=listItems.get(position);
        holder.mItemImage.setImageBitmap(i.getBitMap());
        holder.mItemDes.setText(i.getDescription());
        holder.mItemName.setText(i.getName());
        holder.mItemDate.setText(i.getDate());

        if(i.isTrade()==1){
            ViewGroup.LayoutParams params=holder.itemView.getLayoutParams();
            params.height=0;
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mItemName;
        TextView mItemDes;
        ImageView mItemImage;
        TextView mItemDate;

        public ListViewHolder(@NonNull View itemView){
            super(itemView);
            mItemName=itemView.findViewById(R.id.itemName);
            mItemDes=itemView.findViewById(R.id.itemDes);
            mItemImage=itemView.findViewById(R.id.itemImage);
            mItemDate=itemView.findViewById(R.id.itemDate);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            Bundle bundle= new Bundle();
            bundle.putInt("position",getAdapterPosition());
            AppCompatActivity activity=(AppCompatActivity) context;
            ItemFragment frag=new ItemFragment();
            frag.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.listscroll,frag).addToBackStack(null).commit();
        }
    }
}

