package com.example.admin.constructionsite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Engineer_delete_Adapter extends ArrayAdapter<String> {


    public Engineer_delete_Adapter(Context context, ArrayList<String> dataItem) {
       //layout of single file
        super(context, R.layout.list_item_engi_delete, dataItem);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_engi_delete, parent, false);
        }

        TextView nameofengi = (TextView) listItemView.findViewById(R.id.textView2);

        nameofengi.setText(getItem(position));
        return listItemView;
    }
}
