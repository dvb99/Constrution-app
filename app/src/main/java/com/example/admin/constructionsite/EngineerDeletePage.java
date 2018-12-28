package com.example.admin.constructionsite;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EngineerDeletePage extends AppCompatActivity {
    ArrayList<String> optionsList = new ArrayList<String>();
    PopupWindow popupWindowoption;
    View temp1;
    int position1;
    ArrayList<String> engilist;
    Engineer_delete_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_engineer);
        optionsList.add("Delete Engineer::1");
        optionsList.add("Add to Site::2");
        engilist = (ArrayList<String>) getIntent().getSerializableExtra("listofengineer");
        adapter = new Engineer_delete_Adapter(this, engilist);
        final ListView listView = findViewById(R.id.dele_engi);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // show the list view as dropdown
                // initialize pop up window
                popupWindowoption = popupWindow();

                final View view1 = popupWindowoption.getContentView();
                view1.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popupWindowoption.showAsDropDown(view, view.getWidth() - view1.getMeasuredWidth(), 0);

                temp1 = view;
                position1 = position;


            }
        });
    }

    public PopupWindow popupWindow() {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewDogs = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewDogs.setAdapter(optionsAdapter(optionsList));

        // set the item click listener
        listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener());

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_register));
        // set the list view as pop up window content
        popupWindow.setContentView(listViewDogs);

        return popupWindow;
    }

    /*
     * adapter where the list values will be set
     */
    private ArrayAdapter<String> optionsAdapter(ArrayList dogsArray) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // setting the ID and text for every items in the list
                String item = getItem(position);
                String[] itemArr = item.split("::");
                String text = itemArr[0];
                String id = itemArr[1];

                // visual settings for the list item
                TextView listItem = new TextView(EngineerDeletePage.this);

                listItem.setText(text);
                listItem.setTag(id);
                listItem.setTextSize(15);
                listItem.setPadding(10, 10, 10, 10);
                listItem.setTextColor(Color.WHITE);
                listItem.setGravity(Gravity.CENTER);

                return listItem;
            }
        };

        return adapter;
    }

    public class DogsDropdownOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {

//            // get the context and main activity to access variables
            Context mContext = v.getContext();
            EngineerDeletePage mainActivity = ((EngineerDeletePage) mContext);

//            // add some animation when a list item was clicked
//            Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
//            fadeInAnimation.setDuration(10);
//            v.startAnimation(fadeInAnimation);

//             dismiss the pop up
            mainActivity.popupWindowoption.dismiss();

            // get the id
            String selectedItemTag = v.getTag().toString();

            switch (selectedItemTag)
            {
                case "1":
                    dialogBox();
                    break;
                case "2":
                    Intent intent0 = new Intent(EngineerDeletePage.this, AddSite.class);
                    intent0.putExtra("engineeradd", engilist.get(position1));
                    startActivity(intent0);
                    break;
            }

        }


    }

    @Override
    public void onBackPressed() {
        finish();

    }

    private void dialogBox() {
        try{
            new AlertDialog.Builder(this, R.style.CustomVerifyTheme)
                    .setTitle("Conform")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete();
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
        }
    }
    public void delete()
    {
        DatabaseReference engidele = FirebaseDatabase.getInstance().getReference("User").child("Engineer").child(engilist.get(position1));
        engidele.removeValue();
        engilist.remove(position1);
        adapter.notifyDataSetChanged();
    }
}
