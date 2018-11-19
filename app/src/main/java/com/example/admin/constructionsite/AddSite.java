package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;

public class AddSite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsite);

    }
    public void createobj(View v)
    {
        EditText t1=findViewById(R.id.category);
        EditText t2=findViewById(R.id.Nameofsite);
        EditText t3=findViewById(R.id.Areaofsite);
        EditText t4=findViewById(R.id.Supervisorofsite);

        switch (t1.getText().toString())
        {
            case "Pipeline":
            {
                ArrayListOfObjects.pipeline.add(new SiteObject(t1.getText().toString(),t2.getText().toString(),t3.getText().toString(),t4.getText().toString()));
                Toast.makeText(AddSite.this, "SITE ADDED", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
