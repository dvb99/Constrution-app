package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.constructionsite.Login.User;
import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String selected;

    String[] siteTypes = {"Pipeline", "Watertank", "Roadpavement", "Buildingconstru"};
    int construtionPhoto[] = {R.drawable.pipelinecopy, R.drawable.watertankconstructioncopy, R.drawable.roadpavementcopy, R.drawable.buildingconstructioncopy};


    FirebaseDatabase databaseforConstructionSite = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserCS = databaseforConstructionSite.getReference("ConstructionSite");

    FirebaseDatabase databaseforUser = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserUs = databaseforUser.getReference("User").child("Supervisor");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsite);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), construtionPhoto, siteTypes);
        spin.setAdapter(customSpinnerAdapter);

    }

    public void createobj(View v)
    {
        final EditText t2 = findViewById(R.id.Nameofsite);
        final EditText t3 = findViewById(R.id.Areaofsite);
        final EditText t4 = findViewById(R.id.Supervisorofsite);
        final EditText forsupervisorpassword = findViewById(R.id.Supervisorpassword);

        tableuserCS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SiteObject sb = new SiteObject(t2.getText().toString(), t3.getText().toString(), t4.getText().toString());
                tableuserCS.child(selected).child(t4.getText().toString()).setValue(sb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddSite.this, "Failed to Add site", Toast.LENGTH_SHORT).show();
            }
        });


        tableuserUs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User us = new User(t4.getText().toString(), forsupervisorpassword.getText().toString());
                tableuserUs.child(t4.getText().toString()).setValue(us);
                Toast.makeText(AddSite.this, "Supervisor Added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Toast.makeText(AddSite.this, "Site ADDED", Toast.LENGTH_SHORT).show();
        finish();
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
