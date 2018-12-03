package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class Requirement extends AppCompatActivity {

    Date currentDate = new Date();
    //    Date tomorrow = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
//    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tomorrow);
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
    private EditText edttxt;
    private TextView txtview;
    private Button btncommunicate;
    String toadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);
        LinearLayout ll = findViewById(R.id.todoLL);
        edttxt = findViewById(R.id.chattype);
        btncommunicate=findViewById(R.id.btnchatsend);
        txtview = findViewById(R.id.chatshow);

        toadmin = getIntent().getStringExtra("anyrequirement");
        if(toadmin.equals("4"))
        {
            //call is from supervisor activity
            ll.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle("To " + "admin");

            btncommunicate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tableuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (null == dataSnapshot.child("People").child(login.usname).child(dateLong).child("Today's Requirement").getValue()) {
                                tableuser.child("People").child(login.usname).child(dateLong).child("Today's Requirement").setValue(edttxt.getText().toString());
                                Toast.makeText(Requirement.this, "Please send these materials", Toast.LENGTH_SHORT).show();

                            }
//                  I will display 1 alert box telling once you send data , it won't be changed again for today's date.
//                hence removing Toast.
//                   else
//                   {
//                       Toast.makeText(Labor.this, "Can't send Labor Count again", Toast.LENGTH_SHORT).show();
//                   }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    finish();
                }


            });

            // below code added because after sending com.example.admin.constructionsite.requirement to admin
            // supervisor can see what are the things he has asked to admin do for that day.
            tableuser.child("People").child(login.usname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_requirement = dataSnapshot.child(dateLong).child("Today's Requirement").getValue().toString();
                        txtview.setText(today_requirement);


                    } catch (NullPointerException e) {
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else
        {
            //call is from admin's second page
            getSupportActionBar().setTitle("From" + toadmin);

            tableuser.child("People").child(toadmin).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_requirement = dataSnapshot.child(dateLong).child("Today's Requirement").getValue().toString();
                        txtview.setText(today_requirement);


                    } catch (NullPointerException e) {
                        Toast.makeText(Requirement.this, "No requirement from "+toadmin+" today", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }
}
