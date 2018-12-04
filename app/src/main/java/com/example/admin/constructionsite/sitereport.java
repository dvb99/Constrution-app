package com.example.admin.constructionsite;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class sitereport extends AppCompatActivity {

    BoomMenuButton bmb;
    private ImageView imageView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    ArrayList<Integer> imageresourceid;
    ArrayList<String> stringresourceid;
    String currentImagePath;
    private static final int IMAGE_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitereport);

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        imageresourceid = new ArrayList<>();
        stringresourceid = new ArrayList<>();
        setdata();

        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            Toast.makeText(sitereport.this, "File not found in oncreate()", Toast.LENGTH_SHORT).show();

        } catch (DocumentException e) {
            Toast.makeText(sitereport.this, "Document exception I am in oncreate()", Toast.LENGTH_SHORT).show();

        }

        imageView = (ImageView) findViewById(R.id.sitereportimage);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(imageresourceid.get(i))
                    .normalText(stringresourceid.get(i))
                    .textSize(18)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                {
                                    //Take picture
                                    captureImage(); }
                                    break;
                                case 1:
                                {
                                    //Create report i.e create pdf
                                    //call createPDF(v)
                                    try {
                                        createPDF();
                                    } catch (FileNotFoundException e) {
                                        Toast.makeText(sitereport.this, "File not found I am in case 2", Toast.LENGTH_SHORT).show();
                                    } catch (DocumentException e) {
                                        Toast.makeText(sitereport.this, "Document Exception I am in case 2", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                case 2:
                                {
                                    //View report
                                }
                                case 3:
                                    //Send to admin

                            }

                        }
                    });

            bmb.addBuilder(builder);
        }


    }

    public void setdata() {
        imageresourceid.add(R.drawable.ic_add_a_photo_pink_a400_24dp);
        imageresourceid.add(R.drawable.report);
        imageresourceid.add(R.drawable.ic_picture_as_pdf_light_green_a700_24dp);
        imageresourceid.add(R.drawable.ic_send_purple_a200_24dp);
        stringresourceid.add("Take today's progress photo");
        stringresourceid.add("Create a report");
        stringresourceid.add("View report");
        stringresourceid.add("Send to Admin");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            }
            case 0: {
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Permission accepted
                } else {
                    // Permission Denied
                    Toast.makeText(this, "camera Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }

            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        }
                    };
                }


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {

                Bitmap btmap = BitmapFactory.decodeFile(currentImagePath);
                imageView.setImageBitmap(btmap);
            }
        }
    }
    public void createPDF() throws FileNotFoundException, DocumentException {


        Document doc = new Document();
        String pdfName = "/";
        pdfName = pdfName + "Dheeraj";
        pdfName = pdfName + ".pdf";

        String outpath = Environment.getExternalStorageDirectory() + pdfName;
        try {

            PdfWriter.getInstance(doc, new FileOutputStream(outpath));

            doc.open();

            doc.add(new Paragraph("firstline"));

            doc.close();

            Toast.makeText(sitereport.this, "success", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(sitereport.this, "Please give access for storage" , Toast.LENGTH_SHORT).show();
          //  e.printStackTrace();
        } catch (DocumentException e1) {
            Toast.makeText(sitereport.this, "" + e1, Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }
    }
    public void captureImage()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager())!=null)
        {
            File imageFile =null;
            try {
                imageFile=getimagefile();
            } catch (IOException e) {
                Toast.makeText(this, "I/O", Toast.LENGTH_SHORT).show();

            }

            if (imageFile!=null)
            {
                Uri imageUri= FileProvider.getUriForFile(this,"com.example.android.fileprovider",imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                try {
                    startActivityForResult(cameraIntent, IMAGE_REQUEST);
                }
                catch (java.lang.SecurityException SE)
                {
                    Toast.makeText(this, "Provide access for camera", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private File getimagefile()throws IOException
    {
        String timeStamp =new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName,".jpg",storageDir);
        currentImagePath=imageFile.getAbsolutePath();
        return imageFile;
    }

}
