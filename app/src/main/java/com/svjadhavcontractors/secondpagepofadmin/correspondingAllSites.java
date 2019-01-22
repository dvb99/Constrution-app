package com.svjadhavcontractors.secondpagepofadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.svjadhavcontractors.Equipment;
import com.svjadhavcontractors.Labor;
import com.svjadhavcontractors.R;
import com.svjadhavcontractors.Requirement;
import com.svjadhavcontractors.Table;
import com.svjadhavcontractors.ToDoList;
import com.svjadhavcontractors.workInfo;
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
        String title = getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(title);

        siteadapter adapter = new siteadapter(this, siteList);
        ListView listView = findViewById(R.id.list);
        adapter.setCustomButtonListner(correspondingAllSites.this);
        listView.setAdapter(adapter);

    }


        @Override
        public void onButtonClickListner(int index,String supervisorName) {
            switch (index)
            {
                case 0:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Labor.class);
                intent.putExtra("forlabor", supervisorName);
                startActivity(intent);
                break;
                }
                case 1:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Equipment.class);
                    intent.putExtra("forequip", supervisorName);
                    startActivity(intent);
                    break;
                }
                case 2:
                {
                    Intent intent = new Intent(correspondingAllSites.this, ToDoList.class);
                    intent.putExtra("todolist", supervisorName);
                    startActivity(intent);
                    break;
                }

                case 3:
                {
                    Intent intent = new Intent(correspondingAllSites.this, Requirement.class);
                    intent.putExtra("anyrequirement", supervisorName);
                    startActivity(intent);
                    break;
                }
                case 4:
                {

                    tableuser.child("People").child(supervisorName).child(siteadapter.Nameofsite).child(siteadapter.area).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            try {
                                //Opening the upload file in browser using the upload url
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(dataSnapshot.child(dateLong).child("Report").getValue().toString()));
                                startActivity(intent);

                            } catch (NullPointerException e) {
                                Toast.makeText(correspondingAllSites.this, "File has not uploaded yet by Engineer", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
                }
                case 5:
                {
                    final ArrayList<workInfo> wk=new ArrayList<>();

                    tableuser.child("People").child(supervisorName).child(siteadapter.Nameofsite).child(siteadapter.area).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            wk.clear();
                            if(dataSnapshot.child(dateLong).child("Material").hasChildren()) {
                                for (DataSnapshot ds : dataSnapshot.child(dateLong).child("Material").getChildren()) {
                                    wk.add(ds.getValue(workInfo.class));
                                }
                                Intent intent = new Intent(correspondingAllSites.this, Table.class);
                                intent.putExtra("Tbl", wk);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(correspondingAllSites.this, "Not uploaded yet by Engineer", Toast.LENGTH_SHORT).show();
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


