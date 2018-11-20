package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.constructionsite.secondpagepofadmin.SiteObject;

public class AddSite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String selected;

    String[] siteTypes ={"PipeLine","WaterTank","RoadPavement","BuildingConstruction"};
    int construtionPhoto[] = {R.drawable.pipeline, R.drawable.watertankconstructioncopy, R.drawable.roadpavementcopy,R.drawable.buildingconstructioncopy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsite);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin =  findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);

        CustomAdapter customAdapter=new CustomAdapter(getApplicationContext(), construtionPhoto, siteTypes);
        spin.setAdapter(customAdapter);

    }
    public void createobj(View v)
    {
        EditText t2=findViewById(R.id.Nameofsite);
        EditText t3=findViewById(R.id.Areaofsite);
        EditText t4=findViewById(R.id.Supervisorofsite);

        switch (selected)
        {
            case "PipeLine":
            {
                ArrayListOfObjects.pipeline.add(new SiteObject(selected,t2.getText().toString(),t3.getText().toString(),t4.getText().toString()));
                Toast.makeText(AddSite.this, "SITE ADDED", Toast.LENGTH_SHORT).show();

            }

        }
    }


    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        selected= siteTypes[position];
        Toast.makeText(getApplicationContext(), siteTypes[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //  Auto-generated method stub
    }
}
