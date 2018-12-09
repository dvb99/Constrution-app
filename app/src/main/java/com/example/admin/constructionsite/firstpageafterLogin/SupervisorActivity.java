package com.example.admin.constructionsite.firstpageafterLogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.admin.constructionsite.Equipment;
import com.example.admin.constructionsite.Labor;
import com.example.admin.constructionsite.R;
import com.example.admin.constructionsite.Requirement;
import com.example.admin.constructionsite.ToDoList;
import com.example.admin.constructionsite.sitereport;

import java.util.ArrayList;

public class SupervisorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterlogin_thislayout_specific_for_supervisor);

        ArrayList<Card> cardTile = new ArrayList<>();
        cardTile.add(new Card("Labor","https://firebasestorage.googleapis.com/v0/b/construtionapp.appspot.com/o/EngineerImages%2Flaborcopy.jpg?alt=media&token=6aedda2c-a5d5-4f91-abbe-5f8222672cd8", Color.parseColor("#fde0dc")));
        cardTile.add(new Card("Equipment","https://firebasestorage.googleapis.com/v0/b/construtionapp.appspot.com/o/EngineerImages%2Fequipmentcopy.jpg?alt=media&token=69e12378-02db-4cdc-afe3-66d29fb5cb2f", Color.parseColor("#a6baff")));
        cardTile.add(new Card("Tasks", "https://firebasestorage.googleapis.com/v0/b/construtionapp.appspot.com/o/EngineerImages%2Ftaskcopy.png?alt=media&token=cf022efb-fb89-4683-bf4f-00276d4e0685", Color.parseColor("#42bd41")));
        cardTile.add(new Card("Requirement", "https://firebasestorage.googleapis.com/v0/b/construtionapp.appspot.com/o/EngineerImages%2Frequirementcopy.jpg?alt=media&token=7af46c58-ebad-44f1-a4be-4ecc108cf505", Color.parseColor("#fdd835")));
        cardTile.add(new Card("Report", "https://firebasestorage.googleapis.com/v0/b/construtionapp.appspot.com/o/EngineerImages%2Freportcardcopy.jpg?alt=media&token=111748ba-5877-4f6a-b337-e6e7ec7ffd8f", Color.parseColor("#90a4ae")));
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
                        break;
                        }
                    case "Equipment":{
                        Intent intent = new Intent(SupervisorActivity.this, Equipment.class);
                        intent.putExtra("forequip", "2");
                        startActivity(intent);
                        break;
                    }
                    case "Tasks":{
                        Intent intent = new Intent(SupervisorActivity.this, ToDoList.class);
                        intent.putExtra("todolist", "3");
                        startActivity(intent);
                        break;
                    }

                    case "Requirement":{
                        Intent intent = new Intent(SupervisorActivity.this, Requirement.class);
                        intent.putExtra("anyrequirement", "4");
                        startActivity(intent);
                        break;
                    }

                    case "Report":
                    {
                        Intent intent = new Intent(SupervisorActivity.this, sitereport.class);
                        startActivity(intent);
                        break;
                    }

                }
            }
        });
    }
}

