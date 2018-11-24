package com.example.admin.constructionsite.secondpagepofadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

    }
}
