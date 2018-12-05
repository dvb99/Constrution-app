package com.example.admin.constructionsite;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.constructionsite.Login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Labor extends AppCompatActivity  {

    Date currentDate = new Date();
//    Date tomorrow = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
//    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tomorrow);
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
    String supervisor_name;
    TextView date_with_count;
    EditText manpower;
    String today_labor_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor);
        final View sndbtn = findViewById(R.id.btnforsend);
        date_with_count = findViewById(R.id.txtlbr1);
        supervisor_name =  getIntent().getStringExtra("forlabor");
        manpower =  findViewById(R.id.manpowerused);
        if (supervisor_name.equals("1")) {
            // call is from SupervisorActivity
            sndbtn.setVisibility(View.VISIBLE);

            //Supervisor will see what he has sent as today's
            //Labor count
            tableuser.child("People").child(login.usname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        today_labor_count = dataSnapshot.child(dateLong).child("LaborCount").getValue().toString();
                        String showtxtviewlbr =dataSnapshot.child(dateLong).getKey() + "   " + today_labor_count;
                        date_with_count.setText(showtxtviewlbr);
                        // I will do something like remove listener .It is must
                       // tableuser.child("People").child(login.usname).removeEventListener();

                    } catch (NullPointerException e) {
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            sndbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temp =date_with_count.getText().toString();

                    if(temp.length()==0)
                    {
                        if (manpower.getText().toString().length() == 0) {
                            Animation shake = AnimationUtils.loadAnimation(Labor.this, R.anim.shake);
                            manpower.startAnimation(shake);
                        }
                        else
                            dialogBox(manpower.getText().toString());
                    }
                    else
                    {
                        //You have already sent data;
                        youhavealreadysentdata(today_labor_count);
                    }

                }
            });


        }
        else
        {
            // call is from admin"s 2 page

            sndbtn.setVisibility(View.INVISIBLE);
            getSupportActionBar().setTitle(supervisor_name);

            // I will try to customize this Textview R.id.txtlbr1 in the form of Listview
            // so that each list Item will show particular day and it's correspondind Worker count.


            tableuser.child("People").child(supervisor_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                         String today_labor_count = dataSnapshot.child(dateLong).child("LaborCount").getValue().toString();
                        String showtxtviewlbr =dataSnapshot.child(dateLong).getKey() + "   " + today_labor_count;
                        date_with_count.setText(showtxtviewlbr);
                        manpower.setText(today_labor_count);

                    } catch (NullPointerException e) {
                        Toast toast = Toast.makeText(Labor.this,"Engineer has not updated count yet " +"\ud83d\ude14"+"\ud83d\ude14", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        //To change the Background of Toast
                        view.setBackgroundColor(Color.parseColor("#bf360c"));
                        toast.show();
                    } finally {
                        manpower.setEnabled(false);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }


    private void send ()
    {
        tableuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(null==dataSnapshot.child("People").child(login.usname).child(dateLong).child("LaborCount").getValue()) {
                    EditText edit_text =  findViewById(R.id.manpowerused);
                    tableuser.child("People").child(login.usname).child(dateLong).child("LaborCount").setValue(edit_text.getText().toString());
                    wholesiteinfo.cnt = edit_text.getText().toString();
                    Toast toast = Toast.makeText(Labor.this, "Sent Labor count Successfully" +("\ud83d\udc4d") + ("\ud83d\udc4d"), Toast.LENGTH_LONG);
                    View view = toast.getView();
                    //To change the Background of Toast
                    view.setBackgroundColor(Color.parseColor("#558b2f"));
                    toast.show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finish();

    }

    private void youhavealreadysentdata(String s) {
        try {
            new AlertDialog.Builder(this, R.style.CustomAlertTheme)
                    .setTitle("Alert"+"\u2705"+"\u2705")
                    .setMessage(" You have already sent " + s + " as today's count.")
                    .show();
        }
        catch (WindowManager.BadTokenException e)
        {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }


    private void dialogBox(String s) {
        try{
        new AlertDialog.Builder(this, R.style.CustomVerifyTheme)
                .setTitle("Verification")
                .setMessage(s + " will be treated as final count.You won't be able to change it later."+"\ud83d\udc48")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        send();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
        catch (WindowManager.BadTokenException e)
        {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }



}
