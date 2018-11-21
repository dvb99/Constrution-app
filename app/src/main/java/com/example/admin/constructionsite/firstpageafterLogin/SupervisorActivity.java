package com.example.admin.constructionsite.firstpageafterLogin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.admin.constructionsite.R;

import java.util.ArrayList;

public class SupervisorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin_thislayout_specific_for_supervisor);

        ArrayList<firstpage> words = new ArrayList<>();
        words.add(new firstpage("Labor",R.drawable.laborcopy, Color.parseColor("#fde0dc")));
        words.add(new firstpage("Equipment",R.drawable.equipmentcopy,Color.parseColor("#a6baff")));
        words.add(new firstpage("Tasks",R.drawable.taskcopy,Color.parseColor("#42bd41")));
        words.add(new firstpage("Requirement",R.drawable.requirementcopy,Color.parseColor("#fdd835")));
        words.add(new firstpage("Stock",R.drawable.stockcopy,Color.parseColor("#ffb74d")));
        words.add(new firstpage("Report",R.drawable.reportcardcopy,Color.parseColor("#90a4ae")));
        firstpageadapter adapter = new firstpageadapter(this, words);
        GridView gridView =  findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);
    }
}

