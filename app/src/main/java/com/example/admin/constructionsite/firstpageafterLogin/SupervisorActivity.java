package com.example.admin.constructionsite.firstpageafterLogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.admin.constructionsite.Labor;
import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.sitereport;

import java.util.ArrayList;

public class SupervisorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin_thislayout_specific_for_supervisor);

        ArrayList<Card> cardTile = new ArrayList<>();
        cardTile.add(new Card("Labor", R.drawable.laborcopy, Color.parseColor("#fde0dc")));
        cardTile.add(new Card("Equipment", R.drawable.equipmentcopy, Color.parseColor("#a6baff")));
        cardTile.add(new Card("Tasks", R.drawable.taskcopy, Color.parseColor("#42bd41")));
        cardTile.add(new Card("Requirement", R.drawable.requirementcopy, Color.parseColor("#fdd835")));
        cardTile.add(new Card("Report", R.drawable.reportcardcopy, Color.parseColor("#90a4ae")));
        firstpageadapter adapter = new firstpageadapter(this, cardTile);
        GridView gridView = findViewById(R.id.firstopening);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Card card = (Card) adapterView.getItemAtPosition(position);
                switch (card.getCdtitle()) {
                    case "Labor": {
                        Intent intent = new Intent(SupervisorActivity.this, Labor.class);
                        intent.putExtra("forlabor", "1");
                        startActivity(intent);



                    }
                    case "Report":
                    {
                        Intent intent = new Intent(SupervisorActivity.this, sitereport.class);
                        startActivity(intent);
                    }

                }
            }
        });
    }
}

