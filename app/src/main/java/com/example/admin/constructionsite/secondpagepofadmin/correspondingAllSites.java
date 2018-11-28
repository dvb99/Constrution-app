package com.example.admin.constructionsite.secondpagepofadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.admin.constructionsite.Labor;
import com.example.admin.constructionsite.R;

import java.util.ArrayList;

public class correspondingAllSites extends AppCompatActivity implements
        siteadapter.customButtonListener {

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


