package com.example.admin.constructionsite.secondpagepofadmin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.constructionsite.Labor;
import com.example.admin.constructionsite.R;

import java.util.ArrayList;

public class correspondingAllSites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floodedwithsite);
        ArrayList<SiteObject> siteList = (ArrayList<SiteObject>) getIntent().getSerializableExtra("showThisKindOfSites");

        siteadapter adapter = new siteadapter(this, siteList, Color.YELLOW);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SiteObject sb=(SiteObject)parent.getItemAtPosition(position);
                Intent intent = new Intent(correspondingAllSites.this, Labor.class);
                intent.putExtra("forlabor", sb.getSupervisorName());
                startActivity(intent);

            }
        });


    }

}
