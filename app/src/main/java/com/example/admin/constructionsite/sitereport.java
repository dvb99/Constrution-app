package com.example.admin.constructionsite;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.admin.constructionsite.Login.login;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class sitereport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Date currentDate = new Date();
    private String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);

    String[] siteTypes = {"Pipeline", "Watertank", "Roadpavement", "Buildingconstru"};
    int construtionPhoto[] = {R.drawable.pipelinecopy, R.drawable.watertankconstructioncopy, R.drawable.roadpavementcopy, R.drawable.buildingconstructioncopy};

    BoomMenuButton bmb;
    private ImageView imageView;
    private EditText rpttitle, rptdescription, sitelocation, sinceprevious, uptodate;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    ArrayList<Integer> imageresourceid;
    ArrayList<String> stringresourceid;
    String currentImagePath;
    private static final int IMAGE_REQUEST = 1;
    LinkedHashMap<String, String> additiontoreport = new LinkedHashMap<>();
    private String selected;
    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference,equipmentdata;
    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitereport);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.simpleSpinner_in_sitereport);
        spin.setOnItemSelectedListener(this);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), construtionPhoto, siteTypes);
        spin.setAdapter(customSpinnerAdapter);

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
        rpttitle = findViewById(R.id.ent_rpt_title);
        rptdescription = findViewById(R.id.ent_rpt_description);
        sitelocation = findViewById(R.id.ent_rpt_sitelocation);
        sinceprevious = findViewById(R.id._item_sinceprevious);
        uptodate = findViewById(R.id.item_uptodate);

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        equipmentdata = FirebaseDatabase.getInstance().getReference("User");


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
                                    captureImage();
                                    break;
                                }
                                case 1: {
                                    //Create report i.e create pdf
                                    //call createPDF(v)

                                    if (variouscheck()) {
                                        try {
                                            createPDF();
                                        } catch (FileNotFoundException e) {
                                            Toast.makeText(sitereport.this, "File not found I am in case 2", Toast.LENGTH_SHORT).show();
                                        } catch (DocumentException e) {
                                            Toast.makeText(sitereport.this, "Document Exception I am in case 2", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    break;
                                }

//                                case 2: {
//                                    //View report
//                                }
                                case 2:
//                                    equipmentdata.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if(dataSnapshot.child(login.usname).child(dateLong).hasChild())
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
                                    //Send to admin
                                    getPDF();
                                    break;
                            }

                        }
                    });

            bmb.addBuilder(builder);
        }


    }

    public void setdata() {
        imageresourceid.add(R.drawable.ic_add_a_photo_pink_a400_24dp);
        imageresourceid.add(R.drawable.report);
        //  imageresourceid.add(R.drawable.ic_picture_as_pdf_light_green_a700_24dp);
        imageresourceid.add(R.drawable.ic_send_purple_a200_24dp);
        stringresourceid.add("Take today's progress photo");
        stringresourceid.add("Create a report");
        //I will add functionality of viewing report in updates like 1.1
        //   stringresourceid.add("View report");
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
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                uploadFile(data.getData());
            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createPDF() throws FileNotFoundException, DocumentException {


        Document doc = new Document();
        doc.setPageSize(PageSize.A3);
        doc.setMargins(2, 0, 2, 2);

        String pdfName = "/";
        pdfName = pdfName + login.usname + dateLong;
        pdfName = pdfName + ".pdf";


        String outpath = Environment.getExternalStorageDirectory() + pdfName;
        try {

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outpath));
            YellowBorder event = new YellowBorder();
            writer.setPageEvent(event);
//            Background event = new Background();
//            writer.setPageEvent(event);
            doc.open();
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.UNDERLINE, BaseColor.RED);
            Font fontdate = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC, BaseColor.GREEN);


            Paragraph preface = new Paragraph("Site Report", font);
            preface.setAlignment(Element.ALIGN_CENTER);
            doc.add(preface);

            Paragraph date = new Paragraph("\n" + "                " + dateLong + "\n\n", fontdate);
            date.setAlignment(Element.ALIGN_LEFT);
            doc.add(date);

            additiontoreport.put("Name Of Engineer", login.usname);
            additiontoreport.put("Report Title", rpttitle.getText().toString());
            additiontoreport.put("Report Description", rptdescription.getText().toString());
            additiontoreport.put("Site Location", sitelocation.getText().toString());
            if (selected.equals("Pipeline")) {
                additiontoreport.put("Since Previous", sinceprevious.getText().toString());
                additiontoreport.put("UpToDate", uptodate.getText().toString());

            }
            SharedPreferences sharedPre = getSharedPreferences("wholesiteinfo", Context.MODE_PRIVATE);

            additiontoreport.put("Labor Count", sharedPre.getString("cnt", ""));
            additiontoreport.put("Task From Admin", sharedPre.getString("taskfromAdmin", ""));
            additiontoreport.put("Requirement From Engineer", sharedPre.getString("tdrequirementfromengineer", ""));

            doc.add(addtoreport(additiontoreport));

/////////////////////////////////////////////////////////////////////////////////////////////////////
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");

            Type type = new TypeToken<List<equipmentInfo>>() {
            }.getType();
            ArrayList<equipmentInfo> equipmentInfoArrayList = gson.fromJson(json, type);


            PdfPTable table = new PdfPTable(2); // 2 columns.
            float[] columnWidths = {1f, 2f};
            try {
                table.setWidths(columnWidths);
            } catch (DocumentException e) {
                Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
            }
            Font fontcontent = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);

            PdfPCell cell1 = new PdfPCell(new Paragraph("\n" + "Vehicle Info", fontcontent));
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.ALIGN_BOTTOM);
            cell1.setBorder(Rectangle.ALIGN_RIGHT);
            cell1.setPaddingBottom(5);
            cell1.setPaddingLeft(5);

            PdfPCell cell2 = new PdfPCell(new Paragraph("\n", fontcontent));
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell2.setBorder(Rectangle.ALIGN_BOTTOM);
            cell2.setPaddingBottom(5);
            cell2.setPaddingTop(5);
            cell2.setPaddingLeft(5);


            PdfPTable nestedTableHeader = new PdfPTable(3);
            float[] columnWidthsinnested = {1f, 1.5f, 1.5f};
            try {
                nestedTableHeader.setWidths(columnWidthsinnested);
            } catch (DocumentException e) {
                Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
            }
            nestedTableHeader.addCell(new Paragraph("Vehicle", fontcontent));
            nestedTableHeader.addCell(new Paragraph("Initial Reading", fontcontent));
            nestedTableHeader.addCell(new Paragraph("Final Reading", fontcontent));
            nestedTableHeader.setPaddingTop(2);
            cell2.addElement(nestedTableHeader);

            for (equipmentInfo obj : equipmentInfoArrayList)
            {
                PdfPTable nestedTable = new PdfPTable(3);
                try {
                    nestedTable.setWidths(columnWidthsinnested);
                } catch (DocumentException e) {
                    Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                }
                nestedTable.addCell(new Paragraph(obj.getEquipmentWithNumberplate(), fontcontent));
                nestedTable.addCell(new Paragraph(obj.getInitialReading(), fontcontent));
                nestedTable.addCell(new Paragraph(obj.getFinalReading(), fontcontent));
                nestedTable.setPaddingTop(2);
                cell2.addElement(nestedTable);
            }


            table.addCell(cell1);
            table.addCell(cell2);
/////////////////////////////////////////////////////////////////////////////////////////////////////

            PdfPCell cell3 = new PdfPCell(new Paragraph("\n" + "Today's Progress Photo", fontcontent));
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.ALIGN_BOTTOM);
            cell1.setBorder(Rectangle.ALIGN_RIGHT);
            cell1.setPaddingBottom(5);
            cell1.setPaddingLeft(5);

            Image image = null;
            try {
                image = Image.getInstance(currentImagePath);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to add image to pdf", Toast.LENGTH_SHORT).show();
            }
            PdfPCell cell4 = new PdfPCell(image, true);

            table.addCell(cell3);
            table.addCell(cell4);

//            Image image1 = Image.getInstance("watermark.png");
//            document.add(image1);

            doc.add(table);
            doc.close();

            Toast.makeText(sitereport.this, "success", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(sitereport.this, "Please give access for storage", Toast.LENGTH_SHORT).show();
            //  e.printStackTrace();
        } catch (DocumentException e1) {
            Toast.makeText(sitereport.this, "Document Exception" + e1, Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }
    }

    public void captureImage()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager())!=null)
        {
            File imageFile = null;
            try {
                imageFile = getimagefile();
            } catch (IOException e) {
                Toast.makeText(this, "I/O", Toast.LENGTH_SHORT).show();

            }

            if (imageFile!=null)
            {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }


    //Performing action onItemSelected selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selected = siteTypes[position];
        TableLayout tbl = findViewById(R.id.tl);
        switch (position) {
            case 0: {
                tbl.setVisibility(View.VISIBLE);
                break;
            }
            case 1: {
                tbl.setVisibility(View.INVISIBLE);
                break;

            }
            case 2: {
                tbl.setVisibility(View.INVISIBLE);
                break;

            }
            case 3: {
                tbl.setVisibility(View.INVISIBLE);
                break;

            }

        }

    }

    //Performing action onNothing selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //  Auto-generated method stub
    }

    private PdfPTable addtoreport(HashMap<String, String> additiontoreport) {
        int i = 0;
        PdfPTable table = new PdfPTable(2); // 2 columns.
        float[] columnWidths = {1f, 2f};
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
        }
        for (HashMap.Entry<String, String> entry : additiontoreport.entrySet()) {

            Font fontleft = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
            Font fontright = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);

            PdfPCell cell1 = new PdfPCell(new Paragraph("\n" + entry.getKey(), fontleft));
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.ALIGN_BOTTOM);
            cell1.setBorder(Rectangle.ALIGN_RIGHT);
            cell1.setPaddingBottom(5);
            cell1.setPaddingLeft(5);


            PdfPCell cell2 = new PdfPCell(new Paragraph("\n" + entry.getValue(), fontright));
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.ALIGN_BOTTOM);
            cell2.setPaddingBottom(5);
            cell2.setPaddingLeft(5);
            if (i == 0) {
                cell1.setBorder(Rectangle.ALIGN_TOP);
                cell2.setBorder(Rectangle.ALIGN_TOP);
                cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell2.setBackgroundColor(BaseColor.CYAN);
                i++;
            }

            table.addCell(cell1);
            table.addCell(cell2);

        }
        return table;

    }

    public boolean variouscheck() {
        if (rpttitle.getText().toString().length() == 0) {
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            rpttitle.startAnimation(shake);
            return false;
        } else if (rptdescription.getText().toString().length() == 0) {
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            rptdescription.startAnimation(shake);
            return false;
        } else if (sitelocation.getText().toString().length() == 0) {
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            sitelocation.startAnimation(shake);
            return false;
        } else if (imageView.getDrawable() == null) {
            Toast.makeText(sitereport.this, "Please take today's progress photo", Toast.LENGTH_SHORT).show();
            return false;
        } else if (selected.equals("Pipeline")) {
            if (sinceprevious.getText().toString().length() == 0) {
                Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
                sinceprevious.startAnimation(shake);
                return false;
            } else if (uptodate.getText().toString().length() == 0) {
                Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
                uptodate.startAnimation(shake);
                return false;
            }
        }

        return true;
    }

//    public class Background extends PdfPageEventHelper {
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            int pagenumber = writer.getPageNumber();
//            if (pagenumber % 2 == 1 && pagenumber != 1)
//                return;
//            PdfContentByte canvas = writer.getDirectContentUnder();
//            Rectangle rect = document.getPageSize();
//            canvas.setColorFill(pagenumber < 2 ? BaseColor.YELLOW : BaseColor.DARK_GRAY);
//            canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
//            canvas.fill();
//        }
//    }

    public class YellowBorder extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = document.getPageSize();
            rect.setBorder(Rectangle.BOX); // left, right, top, bottom border
            rect.setBorderWidth(9); // a width of 5 user units
            rect.setBorderColor(BaseColor.YELLOW); // a yellow border
            rect.setUseVariableBorders(true); // the full width will be visible
            canvas.rectangle(rect);
        }
    }

    public class Constants {
        static final String STORAGE_PATH_UPLOADS = "uploads/";
        static final String DATABASE_PATH_UPLOADS = "uploads";
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }

    private void uploadFile(Uri data) {
        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.show();
      //  final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + login.usname+dateLong + ".pdf");


        sRef.putFile(data).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Uri downloadUri = task.getResult();
                    String temp = downloadUri.toString();
                   // Toast.makeText(getApplicationContext(), downloadUri + "", Toast.LENGTH_LONG).show();

                    mDatabaseReference.child("People").child(login.usname).child(dateLong).child("Report").setValue(temp);

                } else {
                    Toast.makeText(sitereport.this, "upload failed: " , Toast.LENGTH_SHORT).show();
                }
            }
        });


        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                    }
                });

    }
}
