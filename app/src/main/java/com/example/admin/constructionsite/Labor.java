package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor);

        final View sndbtn = findViewById(R.id.btnforsend);
        date_with_count = findViewById(R.id.txtlbr1);
        supervisor_name =  getIntent().getStringExtra("forlabor");
        if (supervisor_name.equals("1")) {
            // call is from SupervisorActivity
            sndbtn.setVisibility(View.VISIBLE);

            sndbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    send();

                }
            });

            //Supervisor will see what he has sent as today's
            //Labor count
            tableuser.child("People").child(login.usname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_labor_count = dataSnapshot.child(dateLong).child("LaborCount").getValue().toString();
                        String showtxtviewlbr =dataSnapshot.child(dateLong).getKey() + "   " + today_labor_count;
                        date_with_count.setText(showtxtviewlbr);

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
            // call is from admin"s 2 page

            sndbtn.setVisibility(View.INVISIBLE);
            getSupportActionBar().setTitle(supervisor_name);

            // I will try to customize this Textview R.id.txtlbr1 in the form of Listview
            // so that each list Item will show particular day and it's correspondind Worker count.


            manpower =  findViewById(R.id.manpowerused);


            tableuser.child("People").child(supervisor_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_labor_count = dataSnapshot.child(dateLong).child("LaborCount").getValue().toString();
                        String showtxtviewlbr =dataSnapshot.child(dateLong).getKey() + "   " + today_labor_count;
                        date_with_count.setText(showtxtviewlbr);
                        manpower.setText(today_labor_count);

                    } catch (NullPointerException e) {
                        Toast.makeText(Labor.this, R.string.has_not_updated_count_yet, Toast.LENGTH_SHORT).show();
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

    public void send ()
    {
        tableuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   if(null==dataSnapshot.child("People").child(login.usname).child(dateLong).child("LaborCount").getValue()) {
                       EditText edit_text =  findViewById(R.id.manpowerused);
                       tableuser.child("People").child(login.usname).child(dateLong).child("LaborCount").setValue(edit_text.getText().toString());
                       Toast.makeText(Labor.this, "Sent Labor count Successfully", Toast.LENGTH_SHORT).show();

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


}
