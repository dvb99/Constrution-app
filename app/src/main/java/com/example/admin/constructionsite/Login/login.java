package com.example.admin.constructionsite.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.admin.constructionsite.R;

public class login extends AppCompatActivity {
    Button button;
    EditText username, password;
    static public String usname;
    RadioButton adminradiobutton, engineerradiobutton;
    private View button1;
    private View button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        button = findViewById(R.id.login_button);
//        username = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//
//        // Figure out if the user has checked admin Radiobutton
//        adminradiobutton = findViewById(R.id.admin);
//
//        // Figure out if the user has checked supervisor RadioButton
//        engineerradiobutton = findViewById(R.id.engineer);
//
//        adminradiobutton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    adminradiobutton.setChecked(true);
//                    engineerradiobutton.setChecked(false);
////          First I thought I will separate engineer and admin with
////          username and email but that seems boring.I will login with
////          phone number .Hence removing that constraint on TextInputLayout
//
////          TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.inputlayout_email_or_username);
////          textInputLayout.setHint("email");
//
//            }
//        });
//        engineerradiobutton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    engineerradiobutton.setChecked(true);
//                    adminradiobutton.setChecked(false);
////          First I thought I will separate engineer and admin with
////          username and email but that seems boring.I will login with
////          phone number .Hence removing that constraint on TextInputLayout
//
////          TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.inputlayout_email_or_username);
////          textInputLayout.setHint("username");
//
//
//            }
//        });
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference tableuser = database.getReference("User");
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (username.getText().toString().length() == 0) {
//                    Animation shake = AnimationUtils.loadAnimation(login.this, R.anim.shake);
//                    username.startAnimation(shake);
//                } else if (password.getText().toString().length() == 0) {
//                    Animation shake = AnimationUtils.loadAnimation(login.this, R.anim.shake);
//                    password.startAnimation(shake);
//                } else {
//                    tableuser.addValueEventListener(new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            if (adminradiobutton.isChecked()) {
//
//                                if (dataSnapshot.child("Admin").hasChild(username.getText().toString().trim())) {
//                                    if (dataSnapshot.child("Admin").child(username.getText().toString()).child("password").getValue().equals(password.getText().toString().trim())) {
//                                        // Toast.makeText(login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(login.this, AdminActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Toast.makeText(login.this, "You are not registered Admin ", Toast.LENGTH_SHORT).show();
//
//                                }
//
//
//                            }
//                            if (engineerradiobutton.isChecked()) {
//
//                                usname = username.getText().toString();
//
//                                if (dataSnapshot.child("Engineer").hasChild(username.getText().toString())) {
//                                    if (dataSnapshot.child("Engineer").child(username.getText().toString()).child("password").getValue().equals(password.getText().toString())) {
//                                        Toast.makeText(login.this, "Welcome Engineer", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(login.this, SupervisorActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        Toast.makeText(login.this, "Wrong password", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                } else {
//                                    Toast.makeText(login.this, "You are not registered Engineer ", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        });
//        getSupportActionBar().setTitle("LogIn");

    }
}
