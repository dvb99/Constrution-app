package com.example.admin.constructionsite;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class namewithprogressadapter extends ArrayAdapter<namewithprogress.info> {
    public namewithprogressadapter(Context context, ArrayList<namewithprogress.info> sites) {
        super(context, 0, sites);
    }

    private int progressStatuspipes = 0;
    private int progressStatusfitting = 0;
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

                while(progressStatuspipes < obj.totalpipeslen){
                    // Update the progress status
                    progressStatuspipes +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handlerpipes.post(new Runnable() {
                        @Override
                        public void run() {
                            progresspipes.setMax(obj.totalpipeslen);

                            progresspipes.setProgress(progressStatuspipes);
                            // Show the progress on TextView
                            txtprogresspipes.setText(progressStatuspipes +"");
                            // If task execution completed
                            if(progressStatuspipes == obj.totalpipeslen){
                                // Set a message of completion
                                txtprogresspipes.setText(obj.totalpipeslen+"Mtr");

                            }
                        }
                    });
                }


                while(progressStatusfitting < obj.totalfittingcnt){
                    // Update the progress status
                    progressStatusfitting +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handlerfitting.post(new Runnable() {
                        @Override
                        public void run() {
                            progressfitting.setMax(obj.totalfittingcnt);

                            progressfitting.setProgress(progressStatusfitting);
                            // Show the progress on TextView
                            txtprogressfitting.setText(progressStatusfitting +"");
                            // If task execution completed
                            if(progressStatusfitting == obj.totalfittingcnt){
                                // Set a message of completion
                                txtprogressfitting.setText(obj.totalfittingcnt+"");

                            }
                        }
                    });
                }
            }
        }).start(); // Start the operation

        return cardview;
    }
}
