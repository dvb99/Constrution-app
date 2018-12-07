package com.example.admin.constructionsite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ToDoList extends AppCompatActivity {


    Date currentDate = new Date();
    //    Date tomorrow = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
//    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tomorrow);
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
    private EditText edttxt;
    private TextView txtview;
    private Button btncommunicate;
    String tosupervisor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);
        LinearLayout ll = findViewById(R.id.todoLL);
        edttxt = findViewById(R.id.chattype);
        btncommunicate = findViewById(R.id.btnchatsend);
        txtview = findViewById(R.id.chatshow);
        tosupervisor = getIntent().getStringExtra("todolist");
        if (tosupervisor.equals("3")) {
            // call is from SupervisorActivity
            tableuser.child("People").child(login.usname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_task = dataSnapshot.child(dateLong).child("Today's Task").getValue().toString();
                        txtview.setText(today_task);


                    } catch (NullPointerException e) {
                        Toast.makeText(ToDoList.this, "No specific task from Admin", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            //call is from admin's second page
            ll.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("To" + tosupervisor);

            // below code added because after sending task to supervisor
            // admin can see what he has told supervisor to do for that day.
            tableuser.child("People").child(tosupervisor).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_task = dataSnapshot.child(dateLong).child("Today's Task").getValue().toString();
                        txtview.setText(today_task);


                    } catch (NullPointerException e) {
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            btncommunicate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String temp = txtview.getText().toString();

                    if (temp.length() == 0) {
                        if (edttxt.getText().toString().length() == 0)
                            dialogBox("No specific task today");
                        else
                            dialogBox("Typed task");
                    } else {
                        //You have already sent data;
                        youhavealreadysentdata();
                    }


                }


            });


        }

    }

    private void send() {
        SharedPreferences sharedPre =getSharedPreferences("wholesiteinfo", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPre.edit();


        tableuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (null == dataSnapshot.child("People").child(tosupervisor).child(dateLong).child("Today's Task").getValue()) {
                    editor.putString("taskfromAdmin",edttxt.getText().toString());
                    editor.apply();
                    tableuser.child("People").child(tosupervisor).child(dateLong).child("Today's Task").setValue(edttxt.getText().toString());
                    Toast toast = Toast.makeText(ToDoList.this, "Today's work submitted" +("\ud83d\udc4d") + ("\ud83d\udc4d"), Toast.LENGTH_LONG);
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

    private void youhavealreadysentdata() {
        try {
            new AlertDialog.Builder(this, R.style.CustomAlertTheme)
                    .setTitle("Alert"+"\u2705"+"\u2705")
                    .setMessage(" You have already sent today's work.")
                    .show();
        } catch (WindowManager.BadTokenException e) {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }

    private void dialogBox(String s) {
        try {
            new AlertDialog.Builder(this, R.style.CustomVerifyTheme)
                    .setTitle("Verification")
                    .setMessage(s + " will be treated as final work.You won't be able to change it later."+"\ud83d\udc48")
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
        } catch (WindowManager.BadTokenException e) {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }
}