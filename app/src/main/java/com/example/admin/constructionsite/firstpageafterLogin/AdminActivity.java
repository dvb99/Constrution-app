package com.example.admin.constructionsite.firstpageafterLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.admin.constructionsite.ArrayListOfObjects;
import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;
import com.example.admin.constructionsite.secondpagepofadmin.pagewillbefloodedwithpipeline;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterloginthislayout);

        final ArrayList<firstpage> words = new ArrayList<>();
        words.add(new firstpage("Pipeline"));
        words.add(new firstpage("Water Tank"));
        words.add(new firstpage("Road Pavement"));
        words.add(new firstpage("Building"));
        firstpageadapter adapter = new firstpageadapter(this, words, R.color.pink_200);
        GridView gridView = findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                firstpage k1 = words.get(position);

                switch (k1.getTitle()) {
                    case "Pipeline": {
                        ArrayListOfObjects.pipeline.add(new SiteObject("Pipeline","Ganesh","Miraj","Kore"));
                        ArrayListOfObjects.pipeline.add(new SiteObject("Pipeline","Erica","Sangli","Patil"));
                        ArrayListOfObjects.pipeline.add(new SiteObject("Pipeline","Grand Hayat","Mumbai","Mohite"));
                        startActivity(new Intent(AdminActivity.this, pagewillbefloodedwithpipeline.class));
                    }
                }


            }
        });
    }
}

