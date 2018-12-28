package com.example.admin.constructionsite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.constructionsite.Login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class engineerassignedCity extends AppCompatActivity {

    ArrayList<String> assignedCity = new ArrayList<>();
    // if I declare assignedCity as global then it is creating problem.
    public static String selectedcity;
    Engineer_delete_Adapter adapterforassignedcity;

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    final DatabaseReference tableuserPpl = databaseforPeople.getReference("People");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        tableuserPpl.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          // I don't not want to write here but I didn't get any
          // clue how to do that
                engineerassignedCity.this.assignedCity.clear();
                for (DataSnapshot ds : dataSnapshot.child(login.usname).getChildren()) {
                    assignedCity.add(ds.getKey());
                engineerassignedCity.this.adapterforassignedcity = new Engineer_delete_Adapter(engineerassignedCity.this, assignedCity);
                ListView listView = findViewById(R.id.dele_engi);
                listView.setAdapter(engineerassignedCity.this.adapterforassignedcity);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedcity = assignedCity.get(position);
                        startActivity(new Intent(engineerassignedCity.this, eachSiteInEngineer.class));
                    }
                });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
