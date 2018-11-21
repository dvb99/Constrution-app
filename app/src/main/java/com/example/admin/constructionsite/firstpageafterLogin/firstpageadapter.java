package com.example.admin.constructionsite.firstpageafterLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.constructionsite.R;

import java.util.ArrayList;

public class firstpageadapter extends ArrayAdapter<firstpage> {


    private int mColorResourceId;


    public firstpageadapter(Context context, ArrayList<firstpage> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        firstpage currentWord = getItem(position);


        TextView miwokTextView = listItemView.findViewById(R.id.txtforcard);

        miwokTextView.setText(currentWord.getCardtitle());

        ImageView imageView = listItemView.findViewById(R.id.imgforcard);
        imageView.setImageResource(currentWord.getMimageid());


        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.linearlayoutforcard);
        // Find the color that the resource ID maps to
       // int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(currentWord.getMcolor());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
