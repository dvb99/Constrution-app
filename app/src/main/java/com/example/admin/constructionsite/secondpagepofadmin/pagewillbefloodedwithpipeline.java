package com.example.admin.constructionsite.secondpagepofadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.admin.constructionsite.ArrayListOfObjects;
import com.example.admin.constructionsite.R;

public class pagewillbefloodedwithpipeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floodedwithsite);

        pipelinepageadapter adapter = new pipelinepageadapter(this, ArrayListOfObjects.pipeline, Color.YELLOW);
        ListView gridView = findViewById(R.id.list);
        gridView.setAdapter(adapter);

    }
}
