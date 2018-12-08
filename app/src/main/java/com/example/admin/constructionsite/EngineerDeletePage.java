package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EngineerDeletePage extends AppCompatActivity {
    final ArrayList<String> deleteengineer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        final ArrayList<String> engilist = (ArrayList<String>) getIntent().getSerializableExtra("listofengineer");
        final Engineer_delete_Adapter adapter = new Engineer_delete_Adapter(this, engilist);
        ListView listView = findViewById(R.id.dele_engi);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference engidele = FirebaseDatabase.getInstance().getReference("User").child("Engineer").child(engilist.get(position));
                engidele.removeValue();
                engilist.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
