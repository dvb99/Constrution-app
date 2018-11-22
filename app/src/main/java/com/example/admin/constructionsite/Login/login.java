package com.example.admin.constructionsite.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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

import java.util.HashMap;

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
        adminradiobutton = findViewById(R.id.admin);

        // Figure out if the user has checked supervisor RadioButton
        supervisorradiobutton = findViewById(R.id.supervisor);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tableuser = database.getReference("User");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap hashMap = new HashMap();
                tableuser.addValueEventListener(new ValueEventListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                  for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                  String user1 = ds.child("password").getValue(String.class);
//                  String pass1 = ds.child("username").getValue(String.class);
//                  }
                        if (adminradiobutton.isChecked()) {

//                            firebase database User Admin's part has 0 child (i.e Only 1 Admin) USE BELOW FOR LOOP
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            (1) I can paste below if condition in multiple child's loop also but it takes time to evaluate
//                                hence I am not adding it.
//                            (2) condition of null check is for not allowing null entry to get added into hashmap.
//                                if (ds.child("username").getValue(String.class)!=null&&ds.child("password").getValue(String.class)!=null) {
//                                    hashMap.put(ds.child("username").getValue(String.class), ds.child("password").getValue(String.class));
//                                }
//                        }
//                            firebase database User Admin's part has multiple child (i.e multiple Admin) USE BELOW FOR LOOP
                            for (DataSnapshot ds : dataSnapshot.child("Admin").getChildren()) {
                                hashMap.put(ds.child("username").getValue(String.class), ds.child("password").getValue(String.class));
                            }


                            if (hashMap.containsKey(username.getText().toString())) {
                                if (hashMap.containsValue((password.getText().toString()))) {
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


//                            firebase database User Supervisor's part has 0 child (i.e Only 1 supervisor) USE BELOW FOR LOOP
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                             (1) I can paste below if condition in multiple child's loop also but it takes time to evaluate
//                                 hence I am not adding it.
//                             (2) condition of null check is for not allowing null entry to get added into hashmap.
//                                if (ds.child("username").getValue(String.class) != null && ds.child("password").getValue(String.class) != null) {
//                                    hashMap.put(ds.child("username").getValue(String.class), ds.child("password").getValue(String.class));
//                                }
//                            }

//                            firebase database User Supervisor's part has multiple child (i.e multiple Supervisor) USE BELOW FOR LOOP
                            for (DataSnapshot ds : dataSnapshot.child("Supervisor").getChildren()) {
                                hashMap.put(ds.child("username").getValue(String.class), ds.child("password").getValue(String.class));
                            }



                            if (hashMap.containsKey(username.getText().toString())) {
                                if (hashMap.containsValue(password.getText().toString())) {
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
        getSupportActionBar().setTitle("LogIn");

    }
}
