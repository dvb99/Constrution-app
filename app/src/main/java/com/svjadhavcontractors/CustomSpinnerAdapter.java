package com.svjadhavcontractors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private int constructionimages[];
    private String[] sitetyp;
    private LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, int[] constructionimages, String[] sitetyp) {
        this.context = applicationContext;
        this.constructionimages = constructionimages;
        this.sitetyp = sitetyp;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public CustomSpinnerAdapter(Context context, String[] sitetyp) {
        this.context = context;
        this.sitetyp = sitetyp;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return sitetyp.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = inflter.inflate(R.layout.custom_spinner_items,  parent,false);
        }
        ImageView icon =  listItemView.findViewById(R.id.imageView);
        TextView names =  listItemView.findViewById(R.id.textView);
        if(constructionimages!=null)
            icon.setImageResource(constructionimages[position]);
        names.setText(sitetyp[position]);
        return listItemView;
    }

}
