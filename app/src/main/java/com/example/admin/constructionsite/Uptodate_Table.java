package com.example.admin.constructionsite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;

public class Uptodate_Table extends AppCompatActivity {
    private TableLayout tableLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        tableLyt=findViewById(R.id.table);
        HashMap<String , String> wk=(HashMap<String, String>) getIntent().getSerializableExtra("UpToDateSummary");
        getHeader();
        for (HashMap.Entry<String,String> st : wk.entrySet())
        {
            String[] key=st.getKey().split(",");
            String value = st.getValue();
            addDataToTable(key[0],key[1],key[2],value);

        }
    }

    private void getHeader() {
        TableRow tr_head = new TableRow(this);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView label_material = new TextView(this);
        label_material.setText(R.string.material);
        label_material.setTextColor(Color.WHITE);
        label_material.setPadding(2, 5, 2, 5);
        label_material.setGravity(1);
        tr_head.addView(label_material);// add the column to the table row here

        final TextView label_specific_mt = new TextView(this);
        label_specific_mt.setText(R.string.type); // set the text for the header
        label_specific_mt.setTextColor(Color.WHITE); // set the color
        label_specific_mt.setPadding(2, 5, 2, 5); // set the padding (if required)
        label_specific_mt.setGravity(1);
        tr_head.addView(label_specific_mt); // add the column to the table row here


        TextView label_diameter = new TextView(this);
        label_diameter.setText(R.string.diameter); // set the text for the header
        label_diameter.setTextColor(Color.WHITE); // set the color
        label_diameter.setPadding(2, 5, 2, 5); // set the padding (if required)
        label_diameter.setGravity(1);
        tr_head.addView(label_diameter); // add the column to the table row here

        TextView label_update = new TextView(this);
        label_update.setText(R.string.uptodate); // set the text for the header
        label_update.setTextColor(Color.WHITE); // set the color
        label_update.setPadding(2, 5, 2, 5); // set the padding (if required)
        label_update.setGravity(1);
        tr_head.addView(label_update); // add the column to the table row here


        tableLyt.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

    }


    private void addDataToTable(String material,String speMaterial,String lbdiameter,String lbuptodate) {
        // Create the table row
        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(getResources().getColor(R.color.amber_100));
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Create five columns to add as table data


        TextView  labelmt = new TextView(this);
        labelmt.setText(material);
        labelmt.setPadding(2, 10, 2, 10);
        labelmt.setTextColor(getResources().getColor(R.color.pink_400));
        labelmt.setGravity(1);
        tr.addView(labelmt);

        TextView labelsp_mt = new TextView(this);
        labelsp_mt.setText(speMaterial);
        labelsp_mt.setPadding(2, 10, 2, 10);
        labelsp_mt.setTextColor(getResources().getColor(R.color.pink_400));
        labelsp_mt.setGravity(1);
        tr.addView(labelsp_mt);

        TextView diameter = new TextView(this);
        diameter.setPadding(2, 10, 2, 10);
        diameter.setTextColor(getResources().getColor(R.color.pink_400));
        diameter.setGravity(1);
        diameter.setText(lbdiameter+"mm");
        diameter.setBackground(getResources().getDrawable(R.drawable.table_cell_bg));
        tr.addView(diameter);

        TextView uptodate = new TextView(this);
        uptodate.setPadding(2, 10, 2, 10);
        uptodate.setTextColor(getResources().getColor(R.color.pink_400));
        uptodate.setGravity(1);
        if(material.equals("Pipes"))
            uptodate.setText(lbuptodate+" Mtr");
        else
            uptodate.setText(lbuptodate+" Qtn");
        uptodate.setBackgroundColor(getResources().getColor(R.color.transparent_bg));
        uptodate.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tr.addView(uptodate);

        // finally add this to the table row
        tableLyt.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


    }
}
