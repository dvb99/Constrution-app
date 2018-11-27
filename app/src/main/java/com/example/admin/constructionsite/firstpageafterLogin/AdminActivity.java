package com.example.admin.constructionsite.firstpageafterLogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.admin.constructionsite.AddSite;
import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;
import com.example.admin.constructionsite.secondpagepofadmin.correspondingAllSites;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference tableuser = database.getReference("ConstructionSite");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificforadminnow);

        final ArrayList<Card> cardTile = new ArrayList<Card>();
        cardTile.add(new Card("Pipeline", R.drawable.pipelinecopy, Color.parseColor("#fde0dc")));
        cardTile.add(new Card("Watertank", R.drawable.watertankconstructioncopy, Color.parseColor("#a6baff")));
        cardTile.add(new Card("Roadpavement", R.drawable.roadpavementcopy, Color.parseColor("#42bd41")));
        cardTile.add(new Card("Buildingconstru", R.drawable.buildingconstructioncopy, Color.parseColor("#fdd835")));
        firstpageadapter adapter = new firstpageadapter(this, cardTile);
        GridView gridView = findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Card card = (Card)adapterView.getItemAtPosition(position);
                final ArrayList<SiteObject> pipeline = new ArrayList<SiteObject>();
                final ArrayList<SiteObject> watertank = new ArrayList<SiteObject>();
                final ArrayList<SiteObject> roadpavement = new ArrayList<SiteObject>();
                final ArrayList<SiteObject> buildingconstru = new ArrayList<SiteObject>();
                //Toast are sucking my life , hence removing


                switch (card.getCdtitle()) {
                    case "Pipeline": {
                        tableuser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //this 1 below line surpassed the problem (1st time opening of pipeline sites after a site has been created form create area) beautifully.
                                pipeline.clear();
                                for (DataSnapshot ds : dataSnapshot.child("Pipeline").getChildren()) {
                                    if (ds.child("area").getValue(String.class) != null && ds.child("nameOfSite").getValue(String.class) != null && ds.child("supervisorName").getValue(String.class) != null) {
                                        pipeline.add(new SiteObject(ds.child("area").getValue(String.class), ds.child("nameOfSite").getValue(String.class), ds.child("supervisorName").getValue(String.class)));
                                    }
                                }
                                Intent intent = new Intent(AdminActivity.this, correspondingAllSites.class);
                                intent.putExtra("showThisKindOfSites", pipeline);
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;

                    }
                    case "Watertank": {
                        tableuser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //this 1 below line surpassed the problem (1st time opening of watertank sites after a site has been created form create area) beautifully.
                                watertank.clear();
                                for (DataSnapshot ds : dataSnapshot.child("Watertank").getChildren()) {
                                    if (ds.child("area").getValue(String.class) != null && ds.child("nameOfSite").getValue(String.class) != null && ds.child("supervisorName").getValue(String.class) != null) {
                                        watertank.add(new SiteObject(ds.child("area").getValue(String.class), ds.child("nameOfSite").getValue(String.class), ds.child("supervisorName").getValue(String.class)));
                                    }
                                }
                                Intent intent = new Intent(AdminActivity.this, correspondingAllSites.class);
                                intent.putExtra("showThisKindOfSites", watertank);
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;

                    }
                    case "Roadpavement": {
                        tableuser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //this 1 below line surpassed the problem (1st time opening roadpavement sites after a site has been created form create area) beautifully.
                                roadpavement.clear();
                                for (DataSnapshot ds : dataSnapshot.child("Roadpavement").getChildren()) {
                                    if (ds.child("area").getValue(String.class) != null && ds.child("nameOfSite").getValue(String.class) != null && ds.child("supervisorName").getValue(String.class) != null) {
                                        roadpavement.add(new SiteObject(ds.child("area").getValue(String.class), ds.child("nameOfSite").getValue(String.class), ds.child("supervisorName").getValue(String.class)));
                                    }
                                }
                                Intent intent = new Intent(AdminActivity.this, correspondingAllSites.class);
                                intent.putExtra("showThisKindOfSites", roadpavement);
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;

                    }
                    case "Buildingconstru": {
                        tableuser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //this 1 below line surpassed the problem (1st time opening of buildingconstru sites after a site has been created form create area) beautifully.
                                buildingconstru.clear();
                                for (DataSnapshot ds : dataSnapshot.child("Buildingconstru").getChildren()) {
                                    if (ds.child("area").getValue(String.class) != null && ds.child("nameOfSite").getValue(String.class) != null && ds.child("supervisorName").getValue(String.class) != null) {
                                        buildingconstru.add(new SiteObject(ds.child("area").getValue(String.class), ds.child("nameOfSite").getValue(String.class), ds.child("supervisorName").getValue(String.class)));
                                    }
                                }
                                Intent intent = new Intent(AdminActivity.this, correspondingAllSites.class);
                                intent.putExtra("showThisKindOfSites", buildingconstru);
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;

                    }


                }


            }

        });



        dl = findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.addsite:
                        startActivity(new Intent(AdminActivity.this, AddSite.class));
                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
                    //Toast.makeText(AdminActivity.this, "SITE ADDED", Toast.LENGTH_SHORT).show();
                    case R.id.deletesite:
                        Toast.makeText(AdminActivity.this, "SITE DELETED", Toast.LENGTH_SHORT).show();
                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
                    case R.id.locate:
                        Toast.makeText(AdminActivity.this, "LOCATING USER", Toast.LENGTH_SHORT).show();
                        // Close the drawer as soon as possible
                        dl.closeDrawers();
                        break;
//                    default:
//                        return true;
                }

                dl.closeDrawers();



                return true;
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}



