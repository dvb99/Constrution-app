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
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);
    static int flag = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor);

        final View sndbtn = findViewById(R.id.btnforsend);

        final String supervisor_name =  getIntent().getStringExtra("forlabor");
        if (supervisor_name.equals("1")) {
            // call is from SupervisorActivity
            sndbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            // call is from admin"s 2 page

            sndbtn.setVisibility(View.INVISIBLE);
            getSupportActionBar().setTitle(supervisor_name);

            // I will try to customize this Textview R.id.txtlbr1 in the form of Listview
            // so that each list Item will show particular day and it's correspondind Worker count.
            final TextView date_with_count = findViewById(R.id.txtlbr1);


            final EditText manpower =  findViewById(R.id.manpowerused);


            tableuser.child("People").child(supervisor_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        String today_labor_count = dataSnapshot.child(dateLong).getValue().toString();
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

    public void send (View v)
    {
        if (flag == 0) {

            EditText edit_text =  findViewById(R.id.manpowerused);
            tableuser.child("People").child(login.usname).child(dateLong).setValue(edit_text.getText().toString());
            flag++;
            Toast.makeText(this, "Sent", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "can't send Number of Labor again", Toast.LENGTH_SHORT).show();

        }

        finish();

    }


}
