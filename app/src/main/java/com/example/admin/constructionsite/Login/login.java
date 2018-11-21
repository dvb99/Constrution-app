package com.example.admin.constructionsite.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.firstpageafterLogin.AdminActivity;
import com.example.admin.constructionsite.firstpageafterLogin.SupervisorActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button button;
    EditText username, password;
    RadioButton adminradiobutton, supervisorradiobutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = findViewById(R.id.login_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // Figure out if the user has checked admin Radiobutton
        adminradiobutton =  findViewById(R.id.admin);

        // Figure out if the user has checked supervisor RadioButton
        supervisorradiobutton =  findViewById(R.id.supervisor);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableuser = database.getReference("User");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableuser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (adminradiobutton.isChecked()) {
                            User Adminfirebase = dataSnapshot.child("Admin").getValue(User.class);
                            if (Adminfirebase.getUsername().equals(username.getText().toString())) {
                                if (Adminfirebase.getPassword().equals(password.getText().toString())) {
                                    Toast.makeText(login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(login.this, AdminActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(login.this, "You are not registered Admin ", Toast.LENGTH_SHORT).show();

                            }
                        }
                        if (supervisorradiobutton.isChecked()) {
                            User Supervisorfirebase = dataSnapshot.child("Supervisor").getValue(User.class);
                            if (Supervisorfirebase.getUsername().equals(username.getText().toString())) {
                                if (Supervisorfirebase.getPassword().equals(password.getText().toString())) {
                                    Toast.makeText(login.this, "Welcome Supervisor", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(login.this, SupervisorActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(login.this, "You are not registered Supervisor ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        getSupportActionBar().setTitle("LogIn" );

    }
}
