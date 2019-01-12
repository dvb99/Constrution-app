package com.example.admin.constructionsite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;
import com.example.admin.constructionsite.secondpagepofadmin.correspondingAllSites;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class namewithprogress extends AppCompatActivity {
    private ListView listView;

   private ArrayList<info> siteInfo = new ArrayList<>();
    final ArrayList<SiteObject> pipeline = new ArrayList<>();
    private namewithprogressadapter adapter;

    FirebaseDatabase databaseforPeople = FirebaseDatabase.getInstance();
    private DatabaseReference sitename = databaseforPeople.getReference("ConstructionSite");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        listView = findViewById(R.id.dele_engi);
        final String title = getIntent().getStringExtra("category");
        final String cityname = getIntent().getStringExtra("city");
        getSupportActionBar().setTitle(cityname);

        sitename.child(title).child(cityname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                siteInfo.clear();
                pipeline.clear();

                for (DataSnapshot dssite : dataSnapshot.getChildren()) {
                    Integer pipesmtr = 0;
                    Integer fittingcount = 0;
                    for (DataSnapshot individual : dssite.getChildren())
                    {
                        try {
                            pipesmtr = pipesmtr + Integer.parseInt(individual.child("Pipes").getValue().toString());

                        }
                        catch (NumberFormatException |NullPointerException n)
                        {
                            pipesmtr=pipesmtr+0;
                        }
                        try {
                            fittingcount  = fittingcount  + Integer.parseInt(individual.child("Fitting").getValue().toString());

                        }
                        catch (NumberFormatException |NullPointerException n)
                        {
                            fittingcount =fittingcount +0;
                        }
                        namewithprogress.this.pipeline.add(new SiteObject(cityname,dssite.getKey(),individual.getKey()));
                    }
                    siteInfo.add(new info(dssite.getKey(),pipesmtr,fittingcount));
                }
                adapter = new namewithprogressadapter(namewithprogress.this, siteInfo);
                listView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(namewithprogress.this, correspondingAllSites.class);
                intent.putExtra("showThisKindOfSites", pipeline);
                intent.putExtra("category", "Pipeline");
                startActivity(intent);
            }
        });




    }
    public class info{
        String sitename;
        int totalpipeslen;
        int totalfittingcnt;


        public info(String sitename ,int totalpipeslen,int totalfittingcnt) {
            this.sitename = sitename;
            this.totalpipeslen = totalpipeslen;
            this.totalfittingcnt=totalfittingcnt;
        }
    }
}
