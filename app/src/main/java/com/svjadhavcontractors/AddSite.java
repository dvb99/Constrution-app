package com.svjadhavcontractors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String selected,temp;

    String[] siteTypes = {"Pipeline", "Watertank", "Roadpavement", "Buildingconstru"};
    int construtionPhoto[] = {R.drawable.pipelinecopy, R.drawable.watertankconstructioncopy, R.drawable.roadpavementcopy, R.drawable.buildingconstructioncopy};

    EditText t2,t3,t4;
    Button btn;

    FirebaseDatabase databaseforConstructionSite = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserCS = databaseforConstructionSite.getReference();

    FirebaseDatabase databaseforUser = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserUs = databaseforUser.getReference("User").child("Engineer");

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserPpl = databaseforPeople.getReference("People");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsite);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), construtionPhoto, siteTypes);
        spin.setAdapter(customSpinnerAdapter);

        t2 = findViewById(R.id.Nameofsite);
        t3 = findViewById(R.id.Areaofsite);
        t4 = findViewById(R.id.Engineerofsite);
        btn=findViewById(R.id.two_functionbutton);
        temp =  getIntent().getStringExtra("engineeradd");
        if(temp.equals("-1"))
        {
          //call is from navigation drawer add site
            getSupportActionBar().setTitle(R.string.addsite);

        }
        else
        {
            // call is from navigation drawer assign site
            t4.setText(temp);
            t4.setEnabled(false);
            btn.setText("assign");
            getSupportActionBar().setTitle("assign site ");


        }

    }

    public void createobj(View v)
    {
        tableuserUs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(t4.getText().toString().toLowerCase()))
                {
                    tableuserCS.child("ConstructionSite").child(selected).child(t3.getText().toString().toLowerCase()).child(t2.getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChild(t4.getText().toString().toLowerCase()))
                            {
                                tableuserCS.child("ConstructionSite").child(selected).child(t3.getText().toString().toLowerCase()).child(t2.getText().toString().toLowerCase()).child(t4.getText().toString().toLowerCase()).setValue("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    tableuserPpl.child(t4.getText().toString().toLowerCase()).child(t3.getText().toString().toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.hasChild(t2.getText().toString().toLowerCase()))
                            {
                                tableuserPpl.child(t4.getText().toString().toLowerCase()).child(t3.getText().toString().toLowerCase()).child(t2.getText().toString().toLowerCase()).setValue("");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                  //  Toast.makeText(AddSite.this, "Site ADDED", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Animation shake = AnimationUtils.loadAnimation(AddSite.this, R.anim.shake);
                    t4.startAnimation(shake);
                    Toast.makeText(AddSite.this, "Please add this engineer first", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    //Performing action onItemSelected selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selected = siteTypes[position];
    }

    //Performing action onNothing selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //  Auto-generated method stub
    }
}
