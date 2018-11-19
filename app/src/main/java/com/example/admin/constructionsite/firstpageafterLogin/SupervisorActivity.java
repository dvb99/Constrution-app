package com.example.admin.constructionsite.firstpageafterLogin;

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
        words.add(new firstpage("Labor"));
        words.add(new firstpage("Equipment"));
        words.add(new firstpage("inventory"));
        words.add(new firstpage("Tasks"));
        words.add(new firstpage("Requirement"));
        words.add(new firstpage("Emergency"));
        firstpageadapter adapter = new firstpageadapter(this, words, R.color.blue_200);
        GridView gridView =  findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);
    }
}

