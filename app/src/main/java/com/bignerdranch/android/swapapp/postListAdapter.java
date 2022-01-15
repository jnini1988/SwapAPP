package com.bignerdranch.android.swapapp;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import java.util.List;

public class postListAdapter extends ArrayAdapter<Item> {
    private int resourceLayout;
    private Context mContext;

    public postListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        Item i = getItem(position);
        TextView name = (TextView) v.findViewById(R.id.postName);
        TextView status = (TextView) v.findViewById(R.id.postStatus);
        TextView date = (TextView) v.findViewById(R.id.postDate);
        name.setText(i.getName());
        String tradeStatus="swapped";
        if(i.isTrade()==0){
            tradeStatus="not swapped";
        }
        status.setText("Status: "+tradeStatus);
        date.setText("Posted item on "+i.getDate());
        return v;
    }

}
