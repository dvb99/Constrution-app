package com.example.admin.constructionsite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

public class sitereport extends AppCompatActivity {

    BoomMenuButton bmb ;
    ArrayList<Integer> imageresourceid ;
    ArrayList<String> stringresourceid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitereport);

         bmb = (BoomMenuButton) findViewById(R.id.bmb);
         imageresourceid =new ArrayList<>();
         stringresourceid =new ArrayList<>();
         setdata();

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(imageresourceid.get(i))
                    .normalText(stringresourceid.get(i))
                    .textSize(18)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(sitereport.this, index+"", Toast.LENGTH_SHORT).show();

                        }
                    });

            bmb.addBuilder(builder);
        }


    }
    public  void setdata()
    {
        imageresourceid.add(R.drawable.ic_take_a_photo_grey_600_24dp);
        imageresourceid.add(R.drawable.ic_insert_photo_light_green_a700_24dp);
        imageresourceid.add(R.drawable.report);
        imageresourceid.add(R.drawable.ic_send_purple_a200_24dp);
        stringresourceid.add("Take a photo");
        stringresourceid.add("Add photo to report");
        stringresourceid.add("Create a report");
        stringresourceid.add("Send to Admin");
    }
}
