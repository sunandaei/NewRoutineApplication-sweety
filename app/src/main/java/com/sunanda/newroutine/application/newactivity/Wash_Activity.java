package com.sunanda.newroutine.application.newactivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;
import com.sunanda.newroutine.application.ui.OldSampleCollection_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wash_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        init();
    }

    String sSourceSite;

    @Override
    protected void onResume() {
        super.onResume();

        sSourceSite = CGlobal.getInstance().getPersistentPreference(Wash_Activity.this)
                .getString(Constants.PREFS_SOURCE_SITE_SCHOOL, "");

        if (!TextUtils.isEmpty(sSourceSite)) {
            if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                tvEdit5.setText(getString(R.string.is_the_water_quality_test_report_shared_with_the_anganwadi_authority));
                tvEdit6.setText(getString(R.string.is_toilet_facility_available_in_the_anganwadi));
                tvEdit7.setText(getString(R.string.number_of_general_toilets_anganwadi));
                tvEdit8.setText(getString(R.string.is_hand_washing_facility_available_in_the_anganwadi));
            } else {
                tvEdit5.setText(getString(R.string.is_the_water_quality_test_report_shared_with_the_school_authority));
                tvEdit6.setText(getString(R.string.is_toilet_facility_available_in_the_school));
                tvEdit7.setText(getString(R.string.number_of_general_toilets_school));
                tvEdit8.setText(getString(R.string.is_hand_washing_facility_available_in_the_school));
            }
        }
    }

    TextView tvEdit5, tvEdit6, tvEdit7, tvEdit8;
    Toolbar toolbar;
    LinearLayout llTestedBefore, llLastTested, llSchoolAuthority, llDisinfected, llToiletSchool,
            llRunningWater, llSeparateToilets, llNumberToiletsBoys, llNumberToiletsGirls, llGeneralToilets,
            llTeacherToilets, llNumberToiletsTeachers, llSanitationFacility, llWashingFacility,
            llHandWashing, llWashBasin, llWashBasinPicture, llWaterKitchen;

    RadioButton rbTestedBeforeYes, rbTestedBeforeNo, rbMoreThanYearAgo, rbWithinTheLastMonths,
            rbSchoolAuthorityYes, rbSchoolAuthorityNo, rbWithinWeek, rbWithinMonth, rbNotFixed,
            rbToiletSchoolYes, rbToiletSchoolNo, rbRunningWaterYes, rbRunningWaterNo, rbSeparateToiletsYes,
            rbSeparateToiletsNo, rbTeacherToiletsYes, rbTeacherToiletsNo, rbWashingFacilityYes, rbWashingFacilityNo,
            rbHandWashingYes, rbHandWashingNo, rbWashBasinYes, rbWashBasinNo, rbWaterKitchenYes, rbWaterKitchenNo;

    EditText etNumberToiletsBoys, etNumberToiletsGirls, etGeneralToilets, etNumberToiletsTeachers;

    ImageView ivPictureSanitationFacility, ivPictureWashBasin;

    Button btnTakeImageSanitationFacility, btnTakeImageWashBasin, btnSave;

    private static final int CAMERA_REQUEST = 1112;
    private String mCurrentPhotoPath;

    private static final int CAMERA_REQUEST_1 = 1113;
    private String mCurrentPhotoPath_1;

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private static final String JPEG_FILE_PREFIX = "sch_img_sanitation_";
    private static final String JPEG_FILE_PREFIX_1 = "sch_img_wash_";
    private static final String JPEG_FILE_SUFFIX = ".png";

    String sTestedBefore = "", sLastTested = "", sSchoolAuthority = "", sBacteriologicallyContaminated = "",
            sToiletSchool = "", sRunningWater = "", sSeparateToilets = "", sTeacherToilets = "", sWashingFacility = "",
            sHandWashing = "", sWashBasin = "", sWaterKitchen = "";

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("W.A.S.H");

        tvEdit5 = findViewById(R.id.tvEdit5);
        tvEdit6 = findViewById(R.id.tvEdit6);
        tvEdit7 = findViewById(R.id.tvEdit7);
        tvEdit8 = findViewById(R.id.tvEdit8);

        llTestedBefore = findViewById(R.id.llTestedBefore);
        llLastTested = findViewById(R.id.llLastTested);
        llSchoolAuthority = findViewById(R.id.llSchoolAuthority);
        llDisinfected = findViewById(R.id.llDisinfected);
        llToiletSchool = findViewById(R.id.llToiletSchool);
        llRunningWater = findViewById(R.id.llRunningWater);
        llSeparateToilets = findViewById(R.id.llSeparateToilets);
        llNumberToiletsBoys = findViewById(R.id.llNumberToiletsBoys);
        llNumberToiletsGirls = findViewById(R.id.llNumberToiletsGirls);
        llGeneralToilets = findViewById(R.id.llGeneralToilets);
        llTeacherToilets = findViewById(R.id.llTeacherToilets);
        llNumberToiletsTeachers = findViewById(R.id.llNumberToiletsTeachers);
        llSanitationFacility = findViewById(R.id.llSanitationFacility);
        llWashingFacility = findViewById(R.id.llWashingFacility);
        llHandWashing = findViewById(R.id.llHandWashing);
        llWashBasin = findViewById(R.id.llWashBasin);
        llWashBasinPicture = findViewById(R.id.llWashBasinPicture);
        llWaterKitchen = findViewById(R.id.llWaterKitchen);

        rbTestedBeforeYes = findViewById(R.id.rbTestedBeforeYes);
        rbTestedBeforeNo = findViewById(R.id.rbTestedBeforeNo);

        rbMoreThanYearAgo = findViewById(R.id.rbMoreThanYearAgo);
        rbWithinTheLastMonths = findViewById(R.id.rbWithinTheLastMonths);

        rbSchoolAuthorityYes = findViewById(R.id.rbSchoolAuthorityYes);
        rbSchoolAuthorityNo = findViewById(R.id.rbSchoolAuthorityNo);

        rbWithinWeek = findViewById(R.id.rbWithinWeek);
        rbWithinMonth = findViewById(R.id.rbWithinMonth);
        rbNotFixed = findViewById(R.id.rbNotFixed);

        rbToiletSchoolYes = findViewById(R.id.rbToiletSchoolYes);
        rbToiletSchoolNo = findViewById(R.id.rbToiletSchoolNo);

        rbRunningWaterYes = findViewById(R.id.rbRunningWaterYes);
        rbRunningWaterNo = findViewById(R.id.rbRunningWaterNo);

        rbSeparateToiletsYes = findViewById(R.id.rbSeparateToiletsYes);
        rbSeparateToiletsNo = findViewById(R.id.rbSeparateToiletsNo);

        rbTeacherToiletsYes = findViewById(R.id.rbTeacherToiletsYes);
        rbTeacherToiletsNo = findViewById(R.id.rbTeacherToiletsNo);

        rbWashingFacilityYes = findViewById(R.id.rbWashingFacilityYes);
        rbWashingFacilityNo = findViewById(R.id.rbWashingFacilityNo);

        rbHandWashingYes = findViewById(R.id.rbHandWashingYes);
        rbHandWashingNo = findViewById(R.id.rbHandWashingNo);

        rbWashBasinYes = findViewById(R.id.rbWashBasinYes);
        rbWashBasinNo = findViewById(R.id.rbWashBasinNo);

        rbWaterKitchenYes = findViewById(R.id.rbWaterKitchenYes);
        rbWaterKitchenNo = findViewById(R.id.rbWaterKitchenNo);

        etNumberToiletsBoys = findViewById(R.id.etNumberToiletsBoys);
        etNumberToiletsGirls = findViewById(R.id.etNumberToiletsGirls);
        etGeneralToilets = findViewById(R.id.etGeneralToilets);
        etNumberToiletsTeachers = findViewById(R.id.etNumberToiletsTeachers);

        ivPictureSanitationFacility = findViewById(R.id.ivPictureSanitationFacility);
        ivPictureWashBasin = findViewById(R.id.ivPictureWashBasin);

        btnTakeImageSanitationFacility = findViewById(R.id.btnTakeImageSanitationFacility);
        btnTakeImageWashBasin = findViewById(R.id.btnTakeImageWashBasin);
        btnSave = findViewById(R.id.btnSave);

        llTestedBefore.setVisibility(View.VISIBLE);
        llToiletSchool.setVisibility(View.VISIBLE);
        llWashingFacility.setVisibility(View.VISIBLE);
        llWaterKitchen.setVisibility(View.VISIBLE);

        llLastTested.setVisibility(View.GONE);
        llSchoolAuthority.setVisibility(View.GONE);
        llDisinfected.setVisibility(View.GONE);

        llRunningWater.setVisibility(View.GONE);
        llSeparateToilets.setVisibility(View.GONE);
        llNumberToiletsBoys.setVisibility(View.GONE);
        llNumberToiletsGirls.setVisibility(View.GONE);
        llGeneralToilets.setVisibility(View.GONE);
        llTeacherToilets.setVisibility(View.GONE);
        llNumberToiletsTeachers.setVisibility(View.GONE);
        llSanitationFacility.setVisibility(View.GONE);

        llHandWashing.setVisibility(View.GONE);
        llWashBasin.setVisibility(View.GONE);
        llWashBasinPicture.setVisibility(View.GONE);

        //-----------------111------------------------//
        rbTestedBeforeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTestedBefore = "1";

                rbTestedBeforeYes.setChecked(true);
                rbTestedBeforeNo.setChecked(false);

                llLastTested.setVisibility(View.VISIBLE);
                llSchoolAuthority.setVisibility(View.VISIBLE);
                llDisinfected.setVisibility(View.VISIBLE);
            }
        });

        rbTestedBeforeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTestedBefore = "0";

                rbTestedBeforeYes.setChecked(false);
                rbTestedBeforeNo.setChecked(true);

                llLastTested.setVisibility(View.GONE);
                llSchoolAuthority.setVisibility(View.GONE);
                llDisinfected.setVisibility(View.GONE);
            }
        });

        rbMoreThanYearAgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sLastTested = "1";

                rbMoreThanYearAgo.setChecked(true);
                rbWithinTheLastMonths.setChecked(false);
            }
        });

        rbWithinTheLastMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sLastTested = "2";

                rbMoreThanYearAgo.setChecked(false);
                rbWithinTheLastMonths.setChecked(true);
            }
        });

        rbSchoolAuthorityYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSchoolAuthority = "1";

                rbSchoolAuthorityYes.setChecked(true);
                rbSchoolAuthorityNo.setChecked(false);
            }
        });

        rbSchoolAuthorityNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSchoolAuthority = "0";

                rbSchoolAuthorityYes.setChecked(false);
                rbSchoolAuthorityNo.setChecked(true);
            }
        });

        rbWithinWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "4";

                rbWithinWeek.setChecked(true);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(false);
            }
        });

        rbWithinMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "5";

                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(true);
                rbNotFixed.setChecked(false);
            }
        });

        rbNotFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "6";

                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(true);
            }
        });
        //-----------------111------------------------//

        //-----------------222------------------------//
        sSourceSite = CGlobal.getInstance().getPersistentPreference(Wash_Activity.this)
                .getString(Constants.PREFS_SOURCE_SITE_SCHOOL, "");

        rbToiletSchoolYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sToiletSchool = "1";

                rbToiletSchoolYes.setChecked(true);
                rbToiletSchoolNo.setChecked(false);

                if (!TextUtils.isEmpty(sSourceSite)) {
                    if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                        llRunningWater.setVisibility(View.VISIBLE);
                        llSeparateToilets.setVisibility(View.GONE);
                        llNumberToiletsBoys.setVisibility(View.GONE);
                        llNumberToiletsGirls.setVisibility(View.GONE);
                        llGeneralToilets.setVisibility(View.VISIBLE);
                        llTeacherToilets.setVisibility(View.GONE);
                        llNumberToiletsTeachers.setVisibility(View.GONE);
                        llSanitationFacility.setVisibility(View.VISIBLE);
                    } else {
                        llRunningWater.setVisibility(View.VISIBLE);
                        llSeparateToilets.setVisibility(View.VISIBLE);
                        llNumberToiletsBoys.setVisibility(View.GONE);
                        llNumberToiletsGirls.setVisibility(View.GONE);
                        llGeneralToilets.setVisibility(View.GONE);
                        llTeacherToilets.setVisibility(View.VISIBLE);
                        llNumberToiletsTeachers.setVisibility(View.GONE);
                        llSanitationFacility.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        rbToiletSchoolNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sToiletSchool = "0";

                rbToiletSchoolYes.setChecked(false);
                rbToiletSchoolNo.setChecked(true);

                llRunningWater.setVisibility(View.GONE);
                llSeparateToilets.setVisibility(View.GONE);
                llNumberToiletsBoys.setVisibility(View.GONE);
                llNumberToiletsGirls.setVisibility(View.GONE);
                llGeneralToilets.setVisibility(View.GONE);
                llTeacherToilets.setVisibility(View.GONE);
                llNumberToiletsTeachers.setVisibility(View.GONE);
                llSanitationFacility.setVisibility(View.GONE);
            }
        });

        rbRunningWaterYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRunningWater = "1";

                rbRunningWaterYes.setChecked(true);
                rbRunningWaterNo.setChecked(false);
            }
        });

        rbRunningWaterNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRunningWater = "0";

                rbRunningWaterYes.setChecked(false);
                rbRunningWaterNo.setChecked(true);
            }
        });

        rbSeparateToiletsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSeparateToilets = "1";

                rbSeparateToiletsYes.setChecked(true);
                rbSeparateToiletsNo.setChecked(false);

                llNumberToiletsBoys.setVisibility(View.VISIBLE);
                llNumberToiletsGirls.setVisibility(View.VISIBLE);
                llGeneralToilets.setVisibility(View.GONE);
            }
        });

        rbSeparateToiletsNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSeparateToilets = "0";

                rbSeparateToiletsYes.setChecked(false);
                rbSeparateToiletsNo.setChecked(true);

                llNumberToiletsBoys.setVisibility(View.GONE);
                llNumberToiletsGirls.setVisibility(View.GONE);
                llGeneralToilets.setVisibility(View.VISIBLE);
            }
        });

        rbTeacherToiletsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTeacherToilets = "1";

                rbTeacherToiletsYes.setChecked(true);
                rbTeacherToiletsNo.setChecked(false);

                llNumberToiletsTeachers.setVisibility(View.VISIBLE);
            }
        });

        rbTeacherToiletsNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTeacherToilets = "0";

                rbTeacherToiletsYes.setChecked(false);
                rbTeacherToiletsNo.setChecked(true);

                llNumberToiletsTeachers.setVisibility(View.GONE);

            }
        });
        //-----------------222------------------------//

        //-----------------333------------------------//
        rbWashingFacilityYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashingFacility = "1";

                rbWashingFacilityYes.setChecked(true);
                rbWashingFacilityNo.setChecked(false);

                llHandWashing.setVisibility(View.VISIBLE);
                llWashBasin.setVisibility(View.VISIBLE);
                llWashBasinPicture.setVisibility(View.VISIBLE);
            }
        });

        rbWashingFacilityNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashingFacility = "0";

                rbWashingFacilityYes.setChecked(false);
                rbWashingFacilityNo.setChecked(true);

                llHandWashing.setVisibility(View.GONE);
                llWashBasin.setVisibility(View.GONE);
                llWashBasinPicture.setVisibility(View.GONE);
            }
        });

        rbHandWashingYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sHandWashing = "1";

                rbHandWashingYes.setChecked(true);
                rbHandWashingNo.setChecked(false);
            }
        });

        rbHandWashingNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sHandWashing = "0";

                rbHandWashingYes.setChecked(false);
                rbHandWashingNo.setChecked(true);
            }
        });

        rbWashBasinYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashBasin = "1";

                rbWashBasinYes.setChecked(true);
                rbWashBasinNo.setChecked(false);
            }
        });

        rbWashBasinNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashBasin = "0";

                rbWashBasinYes.setChecked(false);
                rbWashBasinNo.setChecked(true);
            }
        });

        //-----------------333------------------------//

        //-----------------444------------------------//
        rbWaterKitchenYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWaterKitchen = "1";

                rbWaterKitchenYes.setChecked(true);
                rbWaterKitchenNo.setChecked(false);
            }
        });

        rbWaterKitchenNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWaterKitchen = "0";

                rbWaterKitchenYes.setChecked(false);
                rbWaterKitchenNo.setChecked(true);
            }
        });
        //-----------------444------------------------//

        btnTakeImageSanitationFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(Wash_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        btnTakeImageWashBasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile_1();
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(Wash_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST_1);
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Wash_Activity.this);
                builder1.setTitle("Water Quality");
                builder1.setMessage("Are you sure you want to save it?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkSave();
                        dialog.cancel();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    private void checkSave(){
        int id = CGlobal.getInstance().getPersistentPreference(Wash_Activity.this)
                .getInt(Constants.PREFS_LAST_INDEX_SCHOOL_SAMPLE_COLLECTION_ID_SCHOOL, 0);
        String sNumberToiletsBoys = etNumberToiletsBoys.getText().toString();
        String sNumberToiletsGirls = etNumberToiletsGirls.getText().toString();
        String sGeneralToilets = etGeneralToilets.getText().toString();
        String sNumberToiletsTeachers = etNumberToiletsTeachers.getText().toString();

        DatabaseHandler databaseHandler = new DatabaseHandler(Wash_Activity.this);

        if (TextUtils.isEmpty(mCurrentPhotoPath) && TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, "", sWashingFacility,
                    sHandWashing, sWashBasin, "", sWaterKitchen, "", "");
        } else if (!TextUtils.isEmpty(mCurrentPhotoPath) && TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, mCurrentPhotoPath, sWashingFacility,
                    sHandWashing, sWashBasin, "", sWaterKitchen,
                    mCurrentPhotoPath, "");
        } else if (TextUtils.isEmpty(mCurrentPhotoPath) && !TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, "", sWashingFacility,
                    sHandWashing, sWashBasin, mCurrentPhotoPath_1, sWaterKitchen,
                    "", mCurrentPhotoPath_1);
        } else {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, mCurrentPhotoPath, sWashingFacility,
                    sHandWashing, sWashBasin, mCurrentPhotoPath_1, sWaterKitchen,
                    mCurrentPhotoPath, mCurrentPhotoPath_1);
        }

        new AlertDialog.Builder(Wash_Activity.this)
                .setMessage("Successfully save")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Wash_Activity.this, DashBoard_Facilitator_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        finish();
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto();
        } else if (requestCode == CAMERA_REQUEST_1 && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto_1();
        } else {
            mCurrentPhotoPath = null;
            ivPictureSanitationFacility.setImageBitmap(null);
            ivPictureWashBasin.setImageBitmap(null);
        }
    }

    /*private File setUpPhotoFile() {
        File f = null;
        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File getAlbumDir() {
        File storageDir = null;
        try {
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {

                storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

                if (storageDir != null) {
                    if (!storageDir.mkdirs()) {
                        if (!storageDir.exists()) {
                            Log.d("CameraSample", "failed to create directory");
                            return null;
                        }
                    }
                }

            } else {
                Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            setPic();
        }
    }

    private void setPic() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        compressImage(mCurrentPhotoPath);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPictureSanitationFacility.setImageBitmap(bitmap);
    }

    public String compressImage(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        try {
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename(imageUri);
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename(String imageUri) {
        File file = new File(imageUri);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath());
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /*private File setUpPhotoFile_1() {
        File f = null;
        try {
            f = createImageFile_1();
            mCurrentPhotoPath_1 = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }*/

    private File createImageFile_1() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX_1 + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath_1 = image.getAbsolutePath();
        return image;
    }

    private File getAlbumDir_1() {
        File storageDir = null;
        try {
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {

                storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName_1());

                if (storageDir != null) {
                    if (!storageDir.mkdirs()) {
                        if (!storageDir.exists()) {
                            Log.d("CameraSample", "failed to create directory");
                            return null;
                        }
                    }
                }

            } else {
                Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storageDir;
    }

    private String getAlbumName_1() {
        return getString(R.string.album_name);
    }

    private void handleBigCameraPhoto_1() {
        if (mCurrentPhotoPath_1 != null) {
            setPic_1();
        }
    }

    private void setPic_1() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        compressImage_1(mCurrentPhotoPath_1);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        ivPictureWashBasin.setImageBitmap(bitmap);
    }

    public String compressImage_1(String imageUri) {
        String filePath = getRealPathFromURI_1(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        try {
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize_1(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename_1(imageUri);
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename_1(String imageUri) {
        File file = new File(imageUri);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath());
        return uriSting;

    }

    private String getRealPathFromURI_1(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize_1(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(Wash_Activity.this)
                .setMessage("Please Click Save and Exit Button")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
