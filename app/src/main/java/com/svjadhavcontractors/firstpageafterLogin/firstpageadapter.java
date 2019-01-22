package com.svjadhavcontractors.firstpageafterLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svjadhavcontractors.R;

import java.util.ArrayList;

public class firstpageadapter extends ArrayAdapter<Card> {




    public firstpageadapter(Context context, ArrayList<Card> cardArrayList) {
        super(context, 0, cardArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View cardview = convertView;
        if (cardview == null) {
            cardview = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        Card currentcard = getItem(position);


        TextView cardtextview = cardview.findViewById(R.id.txtforcard);

        cardtextview.setText(currentcard.getCdtitle());

        ImageView imageView = cardview.findViewById(R.id.imgforcard);
        imageView.setImageResource(currentcard.getCdimageid());


        // Set the theme color for the list item
        View textContainer = cardview.findViewById(R.id.linearlayoutforcard);
        // Find the color that the resource ID maps to
       // int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(currentcard.getCdcolor());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return cardview;
    }
}
