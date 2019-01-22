package com.svjadhavcontractors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.svjadhavcontractors.secondpagepofadmin.SiteObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class namewithprogress extends AppCompatActivity {
    private ListView listView;

    private ArrayList<info> siteInfo = new ArrayList<>();

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
                for (DataSnapshot dssite : dataSnapshot.getChildren()) {
                    ArrayList<SiteObject> pipeline = new ArrayList<>();
                    HashMap<String, String> PIPE = new HashMap<>();
                    HashMap<String, String> FIT = new HashMap<>();

                    Integer pipesmtr = 0;
                    Integer fittingcount = 0;
                    for (DataSnapshot individual : dssite.getChildren()) {
                        if (individual.hasChildren()) {
                            for (DataSnapshot pip : individual.child("Pipes").getChildren()) {
                                if (PIPE.containsKey(pip.getKey())) {
                                    Integer integer = Integer.parseInt(PIPE.get(pip.getKey()));
                                    integer += Integer.parseInt(pip.getValue().toString());
                                    PIPE.put(pip.getKey(), integer.toString());
                                } else
                                    PIPE.put(pip.getKey(), pip.getValue().toString());


                                try {
                                    pipesmtr = pipesmtr + Integer.parseInt(pip.getValue().toString());
                                } catch (NumberFormatException | NullPointerException n) {
                                    pipesmtr = pipesmtr + 0;
                                }
                            }
                            for (DataSnapshot fit : individual.child("Fitting").getChildren()) {
                                if (FIT.containsKey(fit.getKey())) {
                                    Integer integer = Integer.parseInt(FIT.get(fit.getKey()));
                                    integer += Integer.parseInt(fit.getValue().toString());
                                    FIT.put(fit.getKey(), integer.toString());
                                } else
                                    FIT.put(fit.getKey(), fit.getValue().toString());

                                try {
                                    fittingcount = fittingcount + Integer.parseInt(fit.getValue().toString());

                                } catch (NumberFormatException | NullPointerException n) {
                                    fittingcount = fittingcount + 0;
                                }
                            }
                        }
                        pipeline.add(new SiteObject(cityname, dssite.getKey(), individual.getKey()));

                    }
                    siteInfo.add(new info(dssite.getKey(), pipesmtr,fittingcount, PIPE, FIT, pipeline));
                }
                adapter = new namewithprogressadapter(namewithprogress.this, siteInfo);
                listView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public class info implements Cloneable {
        String sitename;
        int totalpipeslen;
        int totalfittingcnt;
        HashMap<String, String> p = new HashMap<>();
        HashMap<String, String> f = new HashMap<>();
        ArrayList<SiteObject> pipeline = new ArrayList<>();


        public info(String sitename, int totalpipeslen, int totalfittingcnt, HashMap<String, String> p, HashMap<String, String> f, ArrayList<SiteObject> pipeline) {
            this.sitename = sitename;
            this.totalpipeslen = totalpipeslen;
            this.totalfittingcnt=totalfittingcnt;
            this.p = p;
            this.f = f;
            this.pipeline = pipeline;
        }
    }
}
