package com.example.admin.constructionsite;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    Date currentDate = new Date();
//    Date tomorrow = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
//    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(tomorrow);
    String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);
    LinearLayout layoutparent;
    RelativeLayout rl;
    ScrollView sv;
    int j = 0;
    int i = 1;
    EditText edtxteqipmentType, edtxtininitialReading, edtxtfinalReading;
    ArrayList<equipmentInfo> eInfo = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference tableuser = database.getReference();
     boolean haschild;

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
        layoutparent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutparent.setOrientation(LinearLayout.VERTICAL);
        layoutparent.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        final View v = new View(this);


        final String supervisor_name = getIntent().getStringExtra("forequip");
        if (supervisor_name.equals("2")) {
            // call is from SupervisorActivity
            tmpbtn1.setVisibility(View.VISIBLE);
            tmpbtn2.setVisibility(View.VISIBLE);

            sv.addView(addmoreforsupervisor(v));

            tmpbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tableuser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            decide(dataSnapshot.child("People").child(login.usname).child(dateLong).hasChild("EquipmentInfo"));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
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
                        Toast toast = Toast.makeText(Equipment.this,"Engineer has not updated reading yet " +"\ud83d\ude14"+"\ud83d\ude14", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        //To change the Background of Toast
                        view.setBackgroundColor(Color.parseColor("#bf360c"));
                        toast.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        rl.addView(sv);


    }

    public void sendtoAdmin() {
        wholesiteinfo.equipmentInfoforreportcreation.add(new equipmentInfo(edtxteqipmentType.getText().toString(), edtxtininitialReading.getText().toString(), edtxtfinalReading.getText().toString()));
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
            wholesiteinfo.equipmentInfoforreportcreation.add(new equipmentInfo(edtxteqipmentType.getText().toString(), edtxtininitialReading.getText().toString(), edtxtfinalReading.getText().toString()));
            eInfo.add(new equipmentInfo(edtxteqipmentType.getText().toString(), edtxtininitialReading.getText().toString(), edtxtfinalReading.getText().toString()));

        }
        layoutparent.addView(getChild());
        return layoutparent;
    }


    public ViewGroup getChild() {

        LinearLayout layoutchild1 = new LinearLayout(this);
        layoutchild1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild1.setOrientation(LinearLayout.VERTICAL);
        layoutchild1.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);


        //first child
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(16,10,0,5);


        LinearLayout layoutchild11 = new LinearLayout(this);
        layoutchild11.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild11.setOrientation(LinearLayout.VERTICAL);

        TextView txteqipmentType = new TextView(this);
        txteqipmentType.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txteqipmentType.setText(R.string.vehicle);
        txteqipmentType.setTextColor(Color.BLACK);
        txteqipmentType.setTypeface(null, Typeface.BOLD);
        txteqipmentType.setLayoutParams(params);
        layoutchild11.addView(txteqipmentType);

        edtxteqipmentType = new EditText(this);
        edtxteqipmentType.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxteqipmentType.setTextColor(Color.RED);
        edtxteqipmentType.setHint(R.string.hint_to_add_vehicle_used);
        edtxteqipmentType.setGravity(1);
        layoutchild11.addView(edtxteqipmentType);
        layoutchild1.addView(layoutchild11);


        LinearLayout layoutchild12 = new LinearLayout(this);
        layoutchild12.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild12.setOrientation(LinearLayout.VERTICAL);

        TextView txtviewinitialreading = new TextView(this);
        txtviewinitialreading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtviewinitialreading.setText(R.string.initialreading);
        txtviewinitialreading.setTextColor(Color.BLACK);
        txtviewinitialreading.setTypeface(null, Typeface.BOLD);
        txtviewinitialreading.setLayoutParams(params);
        layoutchild12.addView(txtviewinitialreading);

        edtxtininitialReading = new EditText(this);
        edtxtininitialReading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxtininitialReading.setHint(R.string.hint_to_add_initial_reading);
        edtxtininitialReading.setTextColor(Color.RED);
        edtxtininitialReading.setGravity(1);
        layoutchild12.addView(edtxtininitialReading);
        layoutchild1.addView(layoutchild12);


        LinearLayout layoutchild13 = new LinearLayout(this);
        layoutchild13.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutchild13.setOrientation(LinearLayout.VERTICAL);

        TextView txtviewfinalreading = new TextView(this);
        txtviewfinalreading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtviewfinalreading.setText(R.string.finalreading);
        txtviewfinalreading.setTextColor(Color.BLACK);
        txtviewfinalreading.setTypeface(null, Typeface.BOLD);
        txtviewfinalreading.setLayoutParams(params);
        layoutchild13.addView(txtviewfinalreading);

        edtxtfinalReading = new EditText(this);
        edtxtfinalReading.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtxtfinalReading.setHint(R.string.hint_to_add_final_reading);
        edtxtfinalReading.setTextColor(Color.RED);
        edtxtfinalReading.setGravity(1);
        layoutchild13.addView(edtxtfinalReading);
        layoutchild1.addView(layoutchild13);


        return layoutchild1;


    }

    private void youhavealreadysentdata() {
        try {
            new AlertDialog.Builder(this, R.style.CustomAlertTheme)
                    .setTitle("Alert"+"\u2705"+"\u2705")
                    .setMessage(" You have already sent today's vehicle reading.")
                    .show();
        }
        catch (WindowManager.BadTokenException e)
        {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }

    }
    private void dialogBox()
    {
        try {
            new AlertDialog.Builder(this, R.style.CustomVerifyTheme)
                    .setTitle("Verification")
                    .setMessage("This will be treated as final reading.You won't be able to change it later."+"\ud83d\udc48")
                    .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendtoAdmin();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        catch (WindowManager.BadTokenException e)
        {
//        Added because if I delete data from firebase then
//        it unexpectedly terminated my application.
//        Now onwards If I delete previous day's info then app
//        don't get terminated.
        }
    }
    private void decide(boolean haschd)
    {
        if (haschd)
        {
            youhavealreadysentdata();
        }
        else
        {
            dialogBox();
        }
    }

}
