package com.example.admin.constructionsite.secondpagepofadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.constructionsite.R;

import java.util.ArrayList;

public class siteadapter extends ArrayAdapter<SiteObject> {


    private int mColorResourceId;

    public siteadapter(Context context, ArrayList<SiteObject> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_itemofsecondpage, parent, false);
        }


        SiteObject currentWord = getItem(position);


        TextView catTextView =  listItemView.findViewById(R.id.txtforcategory);
        catTextView.setText(currentWord.getCategoryName());


        TextView namofsTextView =  listItemView.findViewById(R.id.siname);
        namofsTextView.setText(currentWord.getNameOfSite());


        TextView areTextView =  listItemView.findViewById(R.id.aname);
        areTextView.setText(currentWord.getArea());


        TextView supTextView =  listItemView.findViewById(R.id.supername);
        supTextView.setText(currentWord.getSupervisorName());

        return listItemView;
    }
}
