package com.svjadhavcontractors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.svjadhavcontractors.secondpagepofadmin.correspondingAllSites;

import java.util.ArrayList;

public class namewithprogressadapter extends ArrayAdapter<namewithprogress.info> {
    public namewithprogressadapter(Context context, ArrayList<namewithprogress.info> sites) {
        super(context, 0, sites);
    }

    private Handler handlerpipes = new Handler();
    private Handler handlerfitting = new Handler();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View cardview = convertView;
        if (cardview == null) {
            cardview = LayoutInflater.from(getContext()).inflate(
                    R.layout.area_and_progress, parent, false);
        }
        final namewithprogress.info obj = getItem(position);

        TextView nameofsite= cardview.findViewById(R.id.textView2);

        nameofsite.setText(obj.sitename);

        //Attribute of corresponding to pipes
        TextView txtpipes= cardview.findViewById(R.id.txtPipes);
        txtpipes.setText("Pipes");

        final TextView txtprogresspipes= cardview.findViewById(R.id.txtprogresspipes);

        final ProgressBar progresspipes = cardview.findViewById(R.id.progressBar1);

        //Attribute of corresponding to fitting
        TextView txtfitting= cardview.findViewById(R.id.txtfitting);
        txtfitting.setText("Fitting");

        final TextView txtprogressfitting= cardview.findViewById(R.id.txtprogressfitting);

        final ProgressBar progressfitting = cardview.findViewById(R.id.progressBar2);



        // Start the lengthy operation in a background thread for pipes
        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int progressStatuspipes = 1;progressStatuspipes <= obj.totalpipeslen; progressStatuspipes++)
                {


                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    final int finalProgressStatuspipes = progressStatuspipes;
                    handlerpipes.post(new Runnable() {
                        @Override
                        public void run() {
                            progresspipes.setMax(obj.totalpipeslen);

                            progresspipes.setProgress(finalProgressStatuspipes);
                            // Show the progress on TextView
                            txtprogresspipes.setText(finalProgressStatuspipes +"");
                            // If task execution completed
                            if(finalProgressStatuspipes == obj.totalpipeslen){
                                // Set a message of completion
                                txtprogresspipes.setText(obj.totalpipeslen+" Mtr");

                            }

                        }
                    });

                }


                for(int progressStatusfitting = 1;progressStatusfitting <= obj.totalfittingcnt ; progressStatusfitting++)
                {

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    final int finalProgressStatusfitting = progressStatusfitting;
                    handlerfitting.post(new Runnable() {
                        @Override
                        public void run() {
                            progressfitting.setMax(obj.totalfittingcnt);

                            progressfitting.setProgress(finalProgressStatusfitting);
                            // Show the progress on TextView
                            txtprogressfitting.setText(finalProgressStatusfitting +"");
                            // If task execution completed
                            if(finalProgressStatusfitting == obj.totalfittingcnt){
                                // Set a message of completion
                                txtprogressfitting.setText(obj.totalfittingcnt+" Qtn");

                            }
                        }
                    });

                }

            }
        }).start(); // Start the operation

        LinearLayout llpip = cardview.findViewById(R.id.LLpip);

        llpip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Uptodate_Table.class);
                intent.putExtra("UpToDateSummary", obj.p);
                ContextCompat.startActivity(getContext(),intent, Bundle.EMPTY);
            }
        });


        LinearLayout llfit = cardview.findViewById(R.id.LLfit);
        llfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Uptodate_Table.class);
                intent.putExtra("UpToDateSummary", obj.f);
                ContextCompat.startActivity(getContext(),intent, Bundle.EMPTY);

            }
        });


        nameofsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), correspondingAllSites.class);
                intent.putExtra("showThisKindOfSites", obj.pipeline);
                intent.putExtra("category", "Pipeline");
                ContextCompat.startActivity(getContext(),intent, Bundle.EMPTY);

            }
        });



        return cardview;
    }
}
