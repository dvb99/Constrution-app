package com.example.admin.constructionsite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.constructionsite.Login.login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Equipment extends AppCompatActivity {
    LinearLayout layoutparent;
    RelativeLayout rl;
    ScrollView sv;
    int j = 0;
    int i = 1;
    EditText edtxteqipmentType, edtxtininitialReading, edtxtfinalReading;
    ArrayList<equipmentInfo> eInfo = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
    Date currentDate = new Date();
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        final View tmpbtn1 = findViewById(R.id.addmoreequip);
        final View tmpbtn2 = findViewById(R.id.equipinfosend);
        rl = (RelativeLayout) findViewById(R.id.rl);

        sv = new ScrollView(this);
        sv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layoutparent = new LinearLayout(this);
        layoutparent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutparent.setOrientation(LinearLayout.VERTICAL);
        final View v = new View(this);


        final String supervisor_name = getIntent().getStringExtra("forequip");
        if (supervisor_name.equals("2")) {
            // call is from SupervisorActivity
            tmpbtn1.setVisibility(View.VISIBLE);
            tmpbtn2.setVisibility(View.VISIBLE);

            sv.addView(addmoreforsupervisor(v));
        } else {
            // call is from admin"s 2 page
            tmpbtn1.setVisibility(View.INVISIBLE);
            tmpbtn2.setVisibility(View.INVISIBLE);
            getSupportActionBar().setTitle(supervisor_name);
            tableuser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("People").child(supervisor_name).child(dateLong).child("EquipmentInfo").getChildrenCount() > 0) {

                        for (int k = 0; k < dataSnapshot.child("People").child(supervisor_name).child(dateLong).child("EquipmentInfo").getChildrenCount(); k++) {


                            LinearLayout lL = (LinearLayout) getChild();
                            String eqptype = dataSnapshot.child("People").child(supervisor_name).child(dateLong).child("EquipmentInfo").child(k + 1 + "").child("equipmentWithNumberplate").getValue().toString();
                            edtxteqipmentType.setText(eqptype);
                            edtxteqipmentType.setEnabled(false);


                            String inireading = dataSnapshot.child("People").child(supervisor_name).child(dateLong).child("EquipmentInfo").child(k + 1 + "").child("initialReading").getValue().toString();
                            edtxtininitialReading.setText(inireading);
                            edtxtininitialReading.setEnabled(false);

                            String finreading = dataSnapshot.child("People").child(supervisor_name).child(dateLong).child("EquipmentInfo").child(k + 1 + "").child("finalReading").getValue().toString();
                            edtxtfinalReading.setText(finreading);
                            edtxtfinalReading.setEnabled(false);


                            layoutparent.addView(lL);
                        }
                        sv.removeAllViews();
                        sv.addView(layoutparent);


                    }
                    else {
                        Toast.makeText(Equipment.this, "Supervisor has not updated Equipment data ", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        rl.addView(sv);


    }

    public void sendtoAdmin(View v) {
        eInfo.add(new equipmentInfo(edtxteqipmentType.getText().toString(), edtxtininitialReading.getText().toString(), edtxtfinalReading.getText().toString()));
        tableuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.child("People").child(login.usname).child(dateLong).hasChild("EquipmentInfo")) {

                    for (int k = 0; k < eInfo.size(); k++) {

                        tableuser.child("People").child(login.usname).child(dateLong).child("EquipmentInfo").child(k + 1 + "").setValue(eInfo.get(k));

                    }
                    Toast.makeText(Equipment.this, "Sent equipment data", Toast.LENGTH_SHORT).show();

                }

//             Here I will display 1 alert box telling once you send data , it won't be changed again for today's date
//              hence removing Toast.
//                else {
//                    Toast.makeText(Equipment.this, "Can't send Equipment data again", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        finish();
    }

    public View addmoreforsupervisor(View v) {

        if (j == 0) {
            j++;
        } else {
            edtxteqipmentType.setEnabled(false);
            edtxtininitialReading.setEnabled(false);
            edtxtfinalReading.setEnabled(false);
            eInfo.add(new equipmentInfo(edtxteqipmentType.getText().toString(), edtxtininitialReading.getText().toString(), edtxtfinalReading.getText().toString()));

        }
        layoutparent.addView(getChild());
        return layoutparent;
    }


    public ViewGroup getChild() {

        LinearLayout layoutchild1 = new LinearLayout(this);
        layoutchild1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild1.setOrientation(LinearLayout.VERTICAL);



        LinearLayout layoutchild11 = new LinearLayout(this);
        layoutchild11.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild11.setOrientation(LinearLayout.HORIZONTAL);

        TextView txteqipmentType = new TextView(this);
        txteqipmentType.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txteqipmentType.setText("Equipment Type");
        txteqipmentType.setTextSize(18);
        txteqipmentType.setTextColor(Color.BLACK);
        layoutchild11.addView(txteqipmentType);

        edtxteqipmentType = new EditText(this);
        edtxteqipmentType.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxteqipmentType.setHint("Enter equipment used with NumberPlate");
        edtxteqipmentType.setTextSize(18);
        edtxteqipmentType.setTextColor(Color.RED);
        edtxteqipmentType.setGravity(1);
        layoutchild11.addView(edtxteqipmentType);
        layoutchild1.addView(layoutchild11);


        LinearLayout layoutchild12 = new LinearLayout(this);
        layoutchild12.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild12.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtviewinitialreading = new TextView(this);
        txtviewinitialreading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtviewinitialreading.setText("Initial Reading");
        txtviewinitialreading.setTextSize(18);
        txtviewinitialreading.setTextColor(Color.BLACK);
        layoutchild12.addView(txtviewinitialreading);

        edtxtininitialReading = new EditText(this);
        edtxtininitialReading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxtininitialReading.setHint("Enter Initial Reading");
        edtxtininitialReading.setTextSize(18);
        edtxtininitialReading.setTextColor(Color.RED);
        edtxtininitialReading.setGravity(1);
        layoutchild12.addView(edtxtininitialReading);
        layoutchild1.addView(layoutchild12);


        LinearLayout layoutchild13 = new LinearLayout(this);
        layoutchild13.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild13.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtviewfinalreading = new TextView(this);
        txtviewfinalreading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtviewfinalreading.setText("Final Reading");
        txtviewfinalreading.setTextSize(18);
        txtviewfinalreading.setTextColor(Color.BLACK);
        layoutchild13.addView(txtviewfinalreading);

        edtxtfinalReading = new EditText(this);
        edtxtfinalReading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxtfinalReading.setHint("Enter Final Reading");
        edtxtfinalReading.setTextSize(18);
        edtxtfinalReading.setTextColor(Color.RED);
        edtxtfinalReading.setGravity(1);
        layoutchild13.addView(edtxtfinalReading);
        layoutchild1.addView(layoutchild13);


        return layoutchild1;


    }
}
