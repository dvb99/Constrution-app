package com.example.admin.constructionsite;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class sitereport extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Date currentDate = new Date();
    private String dateLong = DateFormat.getDateInstance(DateFormat.MEDIUM).format(currentDate);

    String[] siteTypes = {"Pipeline", "Watertank", "Roadpavement", "Buildingconstru"};
    int construtionPhoto[] = {R.drawable.pipelinecopy, R.drawable.watertankconstructioncopy, R.drawable.roadpavementcopy, R.drawable.buildingconstructioncopy};

    String[] materialtype = {"Pipes", "Fitting"};
    String materialselected;
    String[] Pipes = {"DI", "HDPE", "PVC"};
    String[] Fitting = {"Coupler", "Tee"};
    String specificmaterial;
    String temp1,temp2;

    BoomMenuButton bmb;
    FloatingActionButton fab;
    private ImageView imageView;
    private EditText rptdescription, sitelocation,diameter, today;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    ArrayList<Integer> imageresourceid;
    ArrayList<String> stringresourceid;
    String currentImagePath;
    private static final int IMAGE_REQUEST = 1;
    private String selected;
    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    //this is the pic pdf code used in file chooser
    final static int PICK_PDF_CODE = 2342;
    File imageFile, fl;
    SharedPreferences sharedp;
    TableLayout tl;
    private int j;
    ArrayList<workInfo> wkinfo = new ArrayList<>();
    ArrayList<workInfo> wk;
    ArrayList<String> unique = new ArrayList<>();
    HashMap<String,Integer> values = new HashMap<>();
    private EditText uptodate;
    private TextView labelmt;
    private TextView labelsp_mt;
    private ArrayList<equipmentInfo> equipmentInfoArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitereport);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.simpleSpinner_in_sitereport);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), construtionPhoto, siteTypes);
        spin.setAdapter(customSpinnerAdapter);
        spin.setOnItemSelectedListener(this);


        //Getting the instance of Spinner for Material and applying OnItemSelectedListener on it
        final Spinner material = findViewById(R.id.simpleSpinner_material);
        CustomSpinnerAdapter materialAdapter = new CustomSpinnerAdapter(getApplicationContext(), materialtype);
        material.setAdapter(materialAdapter);
        material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                materialselected = materialtype[position];
                TextView txtspecificmaterial = findViewById(R.id.specificmaterial);
                txtspecificmaterial.setText(materialselected);

                //Getting the instance of Spinner for specific Material and applying OnItemSelectedListener on it
                final Spinner spin_specificmaterial = findViewById(R.id.simpleSpinner_specificmaterial);

                switch (materialselected) {

                    case "Pipes": {
                        CustomSpinnerAdapter pipematerialAdapter = new CustomSpinnerAdapter(getApplicationContext(), Pipes);
                        spin_specificmaterial.setAdapter(pipematerialAdapter);
                        spin_specificmaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                specificmaterial = Pipes[position];

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    }

                    case "Fitting":
                        CustomSpinnerAdapter fittingmaterialAdapter = new CustomSpinnerAdapter(getApplicationContext(), Fitting);
                        spin_specificmaterial.setAdapter(fittingmaterialAdapter);
                        spin_specificmaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                specificmaterial = Fitting[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        fab = findViewById(R.id.floatingActionButton);
        imageresourceid = new ArrayList<>();
        stringresourceid = new ArrayList<>();
        setdata();
        bmb.clearBuilders();
        try {
            createPdfWrapper();
        } catch (FileNotFoundException e) {
            Toast.makeText(sitereport.this, "File not found in oncreate()", Toast.LENGTH_SHORT).show();

        } catch (DocumentException e) {
            Toast.makeText(sitereport.this, "Document exception I am in oncreate()", Toast.LENGTH_SHORT).show();

        }

        imageView = (ImageView) findViewById(R.id.sitereportimage);
        rptdescription = findViewById(R.id.ent_rpt_description);
        sitelocation = findViewById(R.id.ent_rpt_sitelocation);
        tl = (TableLayout) findViewById(R.id.tl);

        sharedp = getSharedPreferences("uptodate", Context.MODE_PRIVATE);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
        }

       final SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());


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
                                    final Gson gson = new Gson();
                                    final String json = appSharedPrefs.getString("MyObject", "");

                                    final Type type = new TypeToken<List<equipmentInfo>>() {
                                    }.getType();
                                    equipmentInfoArrayList = gson.fromJson(json, type);

                                    if (variouscheck()) {
                                        try {
                                            createPDF();
                                        } catch (FileNotFoundException e) {
                                            Toast.makeText(sitereport.this, "File not found ", Toast.LENGTH_SHORT).show();
                                        } catch (DocumentException e) {
                                            Toast.makeText(sitereport.this, "Document Exception ", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    break;
                                }

//                                case 2: {
//                                    //View report
//                                }
                                case 2:
                                    //Send to admin
                                    uploadFile();
                                    break;
                            }

                        }
                    });

            bmb.addBuilder(builder);
        }
        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


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

//                Bitmap btmap = BitmapFactory.decodeFile(currentImagePath);
//                imageView.setImageBitmap(btmap);
                String screensize[] = getScreenResolution(this).split(",");
                imageView.setImageBitmap(
                        decodeSampledBitmapFromResource(getResources(), currentImagePath, Integer.parseInt(screensize[0]), Integer.parseInt(screensize[1])));
            }
        }

    }

    public void createPDF() throws FileNotFoundException, DocumentException {


        Document doc = new Document();
        doc.setPageSize(PageSize.A3);
        doc.setMargins(2, 0, 2, 2);

        String pdfName = "/";
        pdfName = pdfName + engineerassignedCity.selectedcity + "_" + eachSiteInEngineer.selectedsite + "_" + sitelocation.getText().toString() + dateLong;
        pdfName = pdfName + ".pdf";


        String outpath = Environment.getExternalStorageDirectory() + pdfName;
        try {
            fl = new File(outpath);
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

            doc.add(addtoreport("Name Of Engineer", login.usname));
            doc.add(addtoreport("Site Location", engineerassignedCity.selectedcity + "->" + eachSiteInEngineer.selectedsite + "->" + sitelocation.getText().toString()));
            doc.add(addtoreport("Report Description", rptdescription.getText().toString()));

            Font fontcontent = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.NORMAL);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PdfPTable table1 = new PdfPTable(2); // 2 columns. //table
            float[] columnWidths = {1f, 2f};
            try {
                table1.setWidths(columnWidths);
            } catch (DocumentException e) {
                Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
            }

            if (selected.equals("Pipeline")) {
                doc.add(new Paragraph("\n"+"\n"));

//             Table of TodayMaterialInfo
                PdfPCell cell_descr = new PdfPCell(new Paragraph("\n" + "Today", fontcontent));//21
                cell_descr.setVerticalAlignment(Element.ALIGN_CENTER);
                cell_descr.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_descr.setBorder(Rectangle.ALIGN_BOTTOM);
                cell_descr.setBorder(Rectangle.ALIGN_RIGHT);
                cell_descr.setPaddingBottom(5);
                cell_descr.setPaddingLeft(5);
                cell_descr.setBorder(Rectangle.ALIGN_TOP);
                cell_descr.setBackgroundColor(BaseColor.LIGHT_GRAY);

                PdfPTable innertable = new PdfPTable(1);
                PdfPTable descri_TableHeader = new PdfPTable(5);//innertable
                float[] descri_nested = {1f, 1f, 1f, 1f, 1f};
                try {
                    descri_TableHeader.setWidths(descri_nested);
                } catch (DocumentException e) {
                    Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                }
                descri_TableHeader.addCell(new Paragraph("Material", fontcontent));
                descri_TableHeader.addCell(new Paragraph("Type", fontcontent));
                descri_TableHeader.addCell(new Paragraph("Diameter", fontcontent));
                descri_TableHeader.addCell(new Paragraph("Today", fontcontent));
                descri_TableHeader.addCell(new Paragraph("UpToDate", fontcontent));


                descri_TableHeader.setPaddingTop(4);
                innertable.addCell(descri_TableHeader);
                if (!unique.contains(temp1 +"," + temp2 +","+ diameter.getText().toString())) {
                    unique.add(temp1 +","+ temp2 +","+ diameter.getText().toString());
                    try {
                        wkinfo.add(new workInfo(temp1, temp2, diameter.getText().toString()
                                , Integer.parseInt(today.getText().toString()),
                                uptodate.getText().toString()));
                    } catch (NumberFormatException nfe) {

                        today.setText(0 + "");
                        wkinfo.add(new workInfo(temp1, temp2, diameter.getText().toString()
                                , 0,
                                uptodate.getText().toString()));
                    }
                    try
                    {
                        values.put(temp1 +","+ temp2 +","+ diameter.getText().toString(),Integer.parseInt(uptodate.getText().toString()));

                    }
                    catch (NumberFormatException nfe)
                    {
                        values.put(temp1 +","+ temp2 +","+ diameter.getText().toString(),0);

                    }
                }
                ListIterator listIterator = wkinfo.listIterator();
                while (listIterator.hasNext()) {
                    workInfo temp = (workInfo) listIterator.next();
                    PdfPTable nestedTable = new PdfPTable(5);
                    try {
                        nestedTable.setWidths(descri_nested);
                    } catch (DocumentException e) {
                        Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                    }

                    nestedTable.addCell(new Paragraph(temp.getMaterial(), fontcontent));
                    nestedTable.addCell(new Paragraph(temp.getMaterial_type(), fontcontent));
                    nestedTable.addCell(new Paragraph(temp.getDiameter() + " mm", fontcontent));
                    if(temp.getMaterial().equals("Pipes")) {
                        nestedTable.addCell(new Paragraph(temp.getToday() + " Mtr", fontcontent));
                        nestedTable.addCell(new Paragraph(temp.getUptodate() + " Mtr", fontcontent));
                    }
                    else{
                        nestedTable.addCell(new Paragraph(temp.getToday() + " Qtn", fontcontent));
                        nestedTable.addCell(new Paragraph(temp.getUptodate() + " Qtn", fontcontent));

                    }
                    innertable.addCell(nestedTable);
                }
                table1.addCell(cell_descr);
                table1.addCell(innertable);
                doc.add(table1);
                doc.add(new Paragraph("\n"+"\n"));

                PdfPTable tableuptodate = new PdfPTable(2); // 2 columns. //table
                try {
                    tableuptodate.setWidths(columnWidths);
                } catch (DocumentException e) {
                    Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                }

                //Table of uptodateMaterialInfo
                PdfPCell cell_uptodate = new PdfPCell(new Paragraph("\n" + "UpToDate", fontcontent));//21
                cell_uptodate.setVerticalAlignment(Element.ALIGN_CENTER);
                cell_uptodate.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_uptodate.setBorder(Rectangle.ALIGN_BOTTOM);
                cell_uptodate.setBorder(Rectangle.ALIGN_RIGHT);
                cell_uptodate.setPaddingBottom(5);
                cell_uptodate.setPaddingLeft(5);
                cell_uptodate.setBorder(Rectangle.ALIGN_TOP);
                cell_uptodate.setBackgroundColor(BaseColor.LIGHT_GRAY);

                PdfPTable innertableuptodate = new PdfPTable(1);
                PdfPTable descri_TableHeaderuptodate = new PdfPTable(4);//innertable
                float[] descri_nesteduptodate = {1f, 1f, 1f, 1f};
                try {
                    descri_TableHeaderuptodate.setWidths(descri_nesteduptodate);
                } catch (DocumentException e) {
                    Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                }

                descri_TableHeaderuptodate.addCell(new Paragraph("Material", fontcontent));
                descri_TableHeaderuptodate.addCell(new Paragraph("Type", fontcontent));
                descri_TableHeaderuptodate.addCell(new Paragraph("Diameter", fontcontent));
                descri_TableHeaderuptodate.addCell(new Paragraph("UpToDate", fontcontent));

                descri_TableHeaderuptodate.setPaddingTop(4);
                innertableuptodate.addCell(descri_TableHeaderuptodate);

                Map<String, ?> allEntries = sharedp.getAll();
                TreeMap t= new TreeMap(new Mycomparator());
                for (TreeMap.Entry<String, ?> entry : allEntries.entrySet()) {
                    t.put(entry.getKey(),entry.getValue());
                }
                Set s1 = t.entrySet();
                Iterator itr = s1.iterator();
                while (itr.hasNext())
                {
                  Map.Entry m1 = (Map.Entry)itr.next();
                    String key = (String) m1.getKey();
                    String content[] = key.split(",");

                    PdfPTable nestedTableuptodate = new PdfPTable(4);
                    try {
                        nestedTableuptodate.setWidths(descri_nesteduptodate);
                    } catch (DocumentException e) {
                        Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
                    }
                    nestedTableuptodate.addCell(new Paragraph(content[0], fontcontent));
                    nestedTableuptodate.addCell(new Paragraph(content[1], fontcontent));
                    try {
                        nestedTableuptodate.addCell(new Paragraph(content[2] + " mm", fontcontent));
                    }
                    catch (ArrayIndexOutOfBoundsException AIOOBExp)
                    {
                        nestedTableuptodate.addCell(new Paragraph(0 + " mm", fontcontent));

                    }
                    if (content[0].equals("Pipes")) {
                        nestedTableuptodate.addCell(new Paragraph(m1.getValue() + " Mtr", fontcontent));
                    } else {
                        nestedTableuptodate.addCell(new Paragraph(m1.getValue() + " Qtn", fontcontent));
                    }
                    innertableuptodate.addCell(nestedTableuptodate);
                }
                tableuptodate.addCell(cell_uptodate);
                tableuptodate.addCell(innertableuptodate);
                doc.add(tableuptodate);
                doc.add(new Paragraph("\n"+"\n"));

            }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            SharedPreferences sharedPre = getSharedPreferences("wholesiteinfo", Context.MODE_PRIVATE);

            doc.add(addtoreport("Labor Count", sharedPre.getString("cnt", "")));
            doc.add(addtoreport("Task From Admin", sharedPre.getString("taskfromAdmin", "")));
            doc.add(addtoreport("Requirement From Engineer", sharedPre.getString("tdrequirementfromengineer", "")));

/////////////////////////////////////////////////////////////////////////////////////////////////////

            // Table of vehilce info
            PdfPTable innertable1 = new PdfPTable(1);

            PdfPTable table2 = new PdfPTable(2); // 2 columns.
            float[] columnWidths2 = {1f, 2f};
            try {
                table2.setWidths(columnWidths2);
            } catch (DocumentException e) {
                Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
            }
            PdfPCell cell1 = new PdfPCell(new Paragraph("\n" + "Vehicle Info", fontcontent));
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.ALIGN_BOTTOM);
            cell1.setBorder(Rectangle.ALIGN_RIGHT);
            cell1.setPaddingBottom(5);
            cell1.setPaddingLeft(5);
            cell1.setBorder(Rectangle.ALIGN_TOP);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);


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
            innertable1.addCell(nestedTableHeader);


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
                innertable1.addCell(nestedTable);
            }

            table2.addCell(cell1);
            table2.addCell(innertable1);
            doc.add(table2);

/////////////////////////////////////////////////////////////////////////////////


            Image image = null;
            try {
                image = Image.getInstance(currentImagePath);
            } catch (IOException e) {
                Toast.makeText(this, "Failed to add image to pdf", Toast.LENGTH_SHORT).show();
            }
            PdfPTable table3 = new PdfPTable(1); // 1 columns.

            PdfPCell cell4 = new PdfPCell(image, true);


            table3.addCell(cell4);

//            Image image1 = Image.getInstance("watermark.png");
//            document.add(image1);

            doc.add(table3);
            Toast.makeText(sitereport.this, "success", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(sitereport.this, "Please give access for storage", Toast.LENGTH_SHORT).show();
            //  e.printStackTrace();
        } catch (DocumentException e1) {
            Toast.makeText(sitereport.this, "Document Exception" + e1, Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        } catch (NullPointerException npe) {
            fab.requestFocus();
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            fab.startAnimation(shake);

        } finally {
            doc.close();
            imageFile.delete();
            imageView.setImageDrawable(null);
            diameter=null;
            tl.removeAllViewsInLayout();
            j = 0;
            wk = new ArrayList<>(wkinfo);
            wkinfo.clear();
        }
    }

    public void captureImage() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = getimagefile();
            } catch (IOException e) {
                Toast.makeText(this, "I/O", Toast.LENGTH_SHORT).show();

            }

            if (imageFile != null) {
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                try {
                    startActivityForResult(cameraIntent, IMAGE_REQUEST);
                } catch (java.lang.SecurityException SE) {
                    Toast.makeText(this, "Provide access for camera", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private File getimagefile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }


    //Performing action onItemSelected selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selected = siteTypes[position];
        LinearLayout ll1 = findViewById(R.id.ll1);
        LinearLayout ll2 = findViewById(R.id.ll2);
        switch (position) {
            case 0: {
                fab.setVisibility(View.VISIBLE);
                tl.setVisibility(View.VISIBLE);
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                break;
            }
            default: {
                fab.setVisibility(View.INVISIBLE);
                tl.setVisibility(View.INVISIBLE);
                ll1.setVisibility(View.INVISIBLE);
                ll2.setVisibility(View.INVISIBLE);
            }

        }

    }

    //Performing action onNothing selected
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        //  Auto-generated method stub
    }

    private PdfPTable addtoreport(String s1, String s2) {
        PdfPTable table = new PdfPTable(2); // 2 columns.
        float[] columnWidths = {1f, 2f};
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            Toast.makeText(this, "column document exception", Toast.LENGTH_SHORT).show();
        }

            Font fontleft = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
            Font fontright = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);

            PdfPCell cell1 = new PdfPCell(new Paragraph("\n" + s1, fontleft));
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBorder(Rectangle.ALIGN_BOTTOM);
            cell1.setBorder(Rectangle.ALIGN_RIGHT);
            cell1.setPaddingBottom(5);
            cell1.setPaddingLeft(5);


            PdfPCell cell2 = new PdfPCell(new Paragraph("\n" + s2, fontright));
            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBorder(Rectangle.ALIGN_BOTTOM);
            cell2.setPaddingBottom(5);
            cell2.setPaddingLeft(5);

            cell1.setBorder(Rectangle.ALIGN_TOP);
            cell2.setBorder(Rectangle.ALIGN_TOP);
            cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell2.setBackgroundColor(BaseColor.CYAN);



            table.addCell(cell1);
            table.addCell(cell2);


        return table;

    }

    public boolean variouscheck() {
        if (sitelocation.getText().toString().length() == 0) {
            sitelocation.requestFocus();
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            sitelocation.startAnimation(shake);
            return false;
        }
         else if (rptdescription.getText().toString().length() == 0) {
            rptdescription.requestFocus();
            Animation shake = AnimationUtils.loadAnimation(sitereport.this, R.anim.shake);
            rptdescription.startAnimation(shake);
            return false;
        } else if (imageView.getDrawable() == null) {
            Toast.makeText(sitereport.this, "Please take today's progress photo", Toast.LENGTH_SHORT).show();
            return false;
        }else if(equipmentInfoArrayList==null)
        {
            Toast.makeText(sitereport.this, "Please upload equipment data first", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(sitereport.this, Equipment.class);
            intent.putExtra("forequip", "2");
            startActivity(intent);
            return false;
        }

        return true;
    }

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

    private void uploadFile() {
        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        //  final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + login.usname + dateLong + ".pdf");

        try {
            sRef.putFile(Uri.fromFile(fl)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                        SharedPreferences.Editor editor = sharedp.edit();
                        for (String obj : unique) {
                            editor.putInt(obj, values.get(obj));
                            editor.apply();
                        }

                        if (selected.equals("Pipeline")) {

                            Map<String, ?> allEntries = sharedp.getAll();
                            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                                if (entry.getKey().contains("Pipes")) {
                                    mDatabaseReference.child("ConstructionSite").child("Pipeline").child(engineerassignedCity.selectedcity).child(eachSiteInEngineer.selectedsite).child(login.usname).child("Pipes").child(entry.getKey()).setValue(entry.getValue()+"");

                                }else {
                                    mDatabaseReference.child("ConstructionSite").child("Pipeline").child(engineerassignedCity.selectedcity).child(eachSiteInEngineer.selectedsite).child(login.usname).child("Fitting").child(entry.getKey()).setValue(entry.getValue()+"");

                                }
                            }

                            int p = 1;
                            for (workInfo obj : wk) {
                                mDatabaseReference.child("People").child(login.usname).child(engineerassignedCity.selectedcity).child(eachSiteInEngineer.selectedsite).child(dateLong).child("Material").child(p + "").setValue(obj);
                                p++;
                            }

                        }
                        Uri downloadUri = task.getResult();
                        String temp = downloadUri.toString();
                        // Toast.makeText(getApplicationContext(), downloadUri + "", Toast.LENGTH_LONG).show();

                        mDatabaseReference.child("People").child(login.usname).child(engineerassignedCity.selectedcity).child(eachSiteInEngineer.selectedsite).child(dateLong).child("Report").setValue(temp);

                    } else {
                        Toast.makeText(sitereport.this, "upload failed: ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });


            sRef.putFile(Uri.fromFile(fl))
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
                            progressDialog.setProgress(((int) progress));
                            progressDialog.incrementProgressBy(10);
                            progressDialog.setCanceledOnTouchOutside(false);

                        }
                    });
        } catch (NullPointerException npe) {
            Toast.makeText(this, "Create report first and then send to admin", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        }

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, String resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId, options);

    }

    private static String getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return width + "," + height;
    }

    @Override
    protected void onDestroy() {
        if (!(imageFile == null))
            imageFile.delete();
        super.onDestroy();
    }


    public void gettable(View v) {

        if (j == 0) {
            j++;
        } else {
            diameter.setEnabled(false);
            today.setEnabled(false);
            uptodate.setEnabled(false);
            if (!unique.contains(temp1 +","+ temp2 +","+ diameter.getText().toString())) {
                unique.add(temp1 +","+ temp2 +","+ diameter.getText().toString());
                try {
                    wkinfo.add(new workInfo(temp1, temp2, diameter.getText().toString()
                            , Integer.parseInt(today.getText().toString()),
                            uptodate.getText().toString()));

                } catch (NumberFormatException nfe) {

                    today.setText(0 + "");
                    wkinfo.add(new workInfo(temp1, temp2, diameter.getText().toString()
                            , 0,
                            uptodate.getText().toString()));
                }
                try
                {
                    values.put(temp1 +","+ temp2 +","+ diameter.getText().toString(),Integer.parseInt(uptodate.getText().toString()));

                }
                catch (NumberFormatException nfe)
                {
                    values.put(temp1 +","+ temp2 +","+ diameter.getText().toString(),0);
                }
            }
        }
        addtable(v);

    }

    public void addtable(View view) {
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

        TextView label_today = new TextView(this);
        label_today.setText(R.string.today); // set the text for the header
        label_today.setTextColor(Color.WHITE); // set the color
        label_today.setPadding(2, 5, 2, 5); // set the padding (if required)
        label_today.setGravity(1);
        tr_head.addView(label_today); // add the column to the table row here

        TextView label_update = new TextView(this);
        label_update.setText(R.string.uptodate); // set the text for the header
        label_update.setTextColor(Color.WHITE); // set the color
        label_update.setPadding(2, 5, 2, 5); // set the padding (if required)
        label_update.setGravity(1);
        tr_head.addView(label_update); // add the column to the table row here


        tl.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        // Create the table row
        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(getResources().getColor(R.color.amber_100));
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        //Create five columns to add as table data


        labelmt = new TextView(this);
        labelmt.setText(materialselected);
        labelmt.setPadding(2, 10, 2, 10);
        labelmt.setTextColor(getResources().getColor(R.color.pink_400));
        labelmt.setGravity(1);
        tr.addView(labelmt);

        labelsp_mt = new TextView(this);
        labelsp_mt.setText(specificmaterial);
        labelsp_mt.setPadding(2, 10, 2, 10);
        labelsp_mt.setTextColor(getResources().getColor(R.color.pink_400));
        labelsp_mt.setGravity(1);
        tr.addView(labelsp_mt);

        diameter = new EditText(this);
        diameter.setPadding(2, 10, 2, 10);
        diameter.setTextColor(getResources().getColor(R.color.pink_400));
        diameter.setGravity(1);
        diameter.setHint("mm");
        diameter.setBackground(getResources().getDrawable(R.drawable.table_cell_bg));
        diameter.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tr.addView(diameter);

        today = new EditText(this);
        today.setPadding(2, 10, 2, 10);
        today.setTextColor(getResources().getColor(R.color.pink_400));
        today.setGravity(1);
        today.setBackground(getResources().getDrawable(R.drawable.table_cell_bg));
        today.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tr.addView(today);


        uptodate = new EditText(this);
        uptodate.setPadding(2, 10, 2, 10);
        uptodate.setTextColor(getResources().getColor(R.color.pink_400));
        uptodate.setGravity(1);
        uptodate.setBackgroundColor(getResources().getColor(R.color.transparent_bg));
        uptodate.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tr.addView(uptodate);

        // finally add this to the table row
        tl.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));


        // If text not changed then I am getting ArrayIndexOutOfBoundException
        // Hence I am assigning intially default value as 0
        temp1=labelmt.getText().toString();
        temp2=labelsp_mt.getText().toString();
        diameter.setText("0");
        diameter.requestFocus();

        today.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Integer integer = sharedp.getInt(temp1 +","+ temp2 +","+ diameter.getText().toString(), 0);

                try {

                    uptodate.setText(integer + Integer.parseInt(today.getText().toString()) + "");


                } catch (NumberFormatException NFE) {
                    if(temp1.equals("Pipes")) {
                        today.setHint("Mtr");
                        today.setHintTextColor(getResources().getColor(R.color.pink_400));
                    }
                  //  today.setText(0+"");
                    uptodate.setText(integer + "");

                } catch (NullPointerException npe) {
                    Toast.makeText(sitereport.this, "Select Pipeline as constructiontype", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public class Mycomparator implements Comparator
    {
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString();
            String s2= o2.toString();
            return s2.compareTo(s1);
        }
    }
}
