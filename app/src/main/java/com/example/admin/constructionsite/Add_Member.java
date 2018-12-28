package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Member extends AppCompatActivity {

    private Button buttonforaddMember;
    private EditText usernameforaddMember,passwordforaddMember;
    private RadioButton adminradiobutton, engineerradiobutton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference tableuseraddMember = database.getReference("User");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        buttonforaddMember = findViewById(R.id.addMember_button);
        usernameforaddMember = findViewById(R.id.usernameaddMember);
        passwordforaddMember = findViewById(R.id.passwordaddMember);

        // Figure out if the user has checked admin Radiobutton
        adminradiobutton = findViewById(R.id.adminaddMember);

        // Figure out if the user has checked supervisor RadioButton
        engineerradiobutton = findViewById(R.id.engineeraddMember);

        buttonforaddMember.setText(R.string.addMember);

        buttonforaddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameforaddMember.getText().toString().length() == 0) {
                    Animation shake = AnimationUtils.loadAnimation(Add_Member.this, R.anim.shake);
                    usernameforaddMember.startAnimation(shake);
                } else if (passwordforaddMember.getText().toString().length() == 0) {
                    Animation shake = AnimationUtils.loadAnimation(Add_Member.this, R.anim.shake);
                    passwordforaddMember.startAnimation(shake);
                }
                else if(adminradiobutton.isChecked()){
                    //I found the method which add ups only once
                    // Now I am not sucked for toast messages
                    // I have to use addListenerForSingleValue everywhere
                    tableuseraddMember.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("Admin").hasChild(usernameforaddMember.getText().toString().trim().toLowerCase()))
                                Toast.makeText(Add_Member.this, "This name Admin already exists", Toast.LENGTH_SHORT).show();
                            else {
                                tableuseraddMember.child("Admin").child(usernameforaddMember.getText().toString().toLowerCase()).child("password").setValue(passwordforaddMember.getText().toString());
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });





//                    tableuseraddMember.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.child("Admin").hasChild(usernameforaddMember.getText().toString().trim()))
//                                Toast.makeText(Add_Member.this, "This name Admin already exists", Toast.LENGTH_SHORT).show();
//                            else {
//                                tableuseraddMember.child("Admin").child(usernameforaddMember.getText().toString()).setValue(us);
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                        }
//                    });
                }
                else if(engineerradiobutton.isChecked()){
                    tableuseraddMember.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Engineer").hasChild(usernameforaddMember.getText().toString().trim().toLowerCase()))
                                Toast.makeText(Add_Member.this, "This name Engineer already exists", Toast.LENGTH_SHORT).show();
                            else {
                                tableuseraddMember.child("Engineer").child(usernameforaddMember.getText().toString().toLowerCase()).child("password").setValue(passwordforaddMember.getText().toString());
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


//                    tableuseraddMember.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child("Engineer").hasChild(usernameforaddMember.getText().toString().trim()))
//                                Toast.makeText(Add_Member.this, "This name Engineer already exists", Toast.LENGTH_SHORT).show();
//                            else {
//                                tableuseraddMember.child("Engineer").child(usernameforaddMember.getText().toString()).setValue(us);
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                        }
//                    });
                }


            }
        });

    }
}
