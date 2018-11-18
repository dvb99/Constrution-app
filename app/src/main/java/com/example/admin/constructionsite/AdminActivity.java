package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);

        ArrayList<firstpage> words = new ArrayList<>();
        words.add(new firstpage("Pipeline"));
        words.add(new firstpage("Water Tank"));
        words.add(new firstpage("Road Pavement"));
        words.add(new firstpage("Building"));
        firstpageadapter adapter = new firstpageadapter(this, words, R.color.pink_200);
        GridView gridView = findViewById(R.id.supervisor_main);
        gridView.setAdapter(adapter);


    }
}

