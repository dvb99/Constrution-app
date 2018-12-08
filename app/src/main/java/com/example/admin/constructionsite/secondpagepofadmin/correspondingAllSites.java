package com.example.admin.constructionsite.secondpagepofadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.constructionsite.Equipment;
import com.example.admin.constructionsite.Labor;
import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.Requirement;
import com.example.admin.constructionsite.ToDoList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class correspondingAllSites extends AppCompatActivity implements
        siteadapter.customButtonListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();

    Date currentDate = new Date();
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floodedwithsite);
        ArrayList<SiteObject> siteList = (ArrayList<SiteObject>) getIntent().getSerializableExtra("showThisKindOfSites");

        siteadapter adapter = new siteadapter(this, siteList);
        ListView listView = findViewById(R.id.list);
        adapter.setCustomButtonListner(correspondingAllSites.this);
        listView.setAdapter(adapter);

    }


        @Override
        public void onButtonClickListner(int index,String supervisorName) {
            switch (index)
            {
                case 1:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Labor.class);
                intent.putExtra("forlabor", supervisorName);
                startActivity(intent);
                break;
                }
                case 2:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Equipment.class);
                    intent.putExtra("forequip", supervisorName);
                    startActivity(intent);
                    break;
                }
                case 3:
                {
                    Intent intent = new Intent(correspondingAllSites.this, ToDoList.class);
                    intent.putExtra("todolist", supervisorName);
                    startActivity(intent);
                    break;
                }

                case 4:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Requirement.class);
                    intent.putExtra("anyrequirement", supervisorName);
                    startActivity(intent);
                    break;
                }
                case 5:
                {

                    tableuser.child("uploads").child("People").child(supervisorName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                //Opening the upload file in browser using the upload url
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(dataSnapshot.child(dateLong).child("Report").getValue().toString()));
                                startActivity(intent);

                            } catch (NullPointerException e) {
                                Toast.makeText(correspondingAllSites.this, "Can't open file", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }

        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SiteObject sb=(SiteObject)parent.getItemAtPosition(position);
//                Intent intent = new Intent(correspondingAllSites.this, Labor.class);
//                intent.putExtra("forlabor", sb.getSupervisorName());
//                startActivity(intent);
//
//            }
//        });


    }


