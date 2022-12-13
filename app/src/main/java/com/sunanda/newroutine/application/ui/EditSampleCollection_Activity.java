package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.AutoText_Adapter;
import com.sunanda.newroutine.application.adapter.BigDiaTubeWellNumber_Adapter;
import com.sunanda.newroutine.application.adapter.Block_Adapter;
import com.sunanda.newroutine.application.adapter.DescriptionLocation_Adapter;
import com.sunanda.newroutine.application.adapter.HabitationName_Adapter;
import com.sunanda.newroutine.application.adapter.HealthFacility_Adapter;
import com.sunanda.newroutine.application.adapter.Master_Adapter;
import com.sunanda.newroutine.application.adapter.Panchayat_Adapter;
import com.sunanda.newroutine.application.adapter.Scheme_Adapter;
import com.sunanda.newroutine.application.adapter.Town_Adapter;
import com.sunanda.newroutine.application.adapter.VillageName_Adapter;
import com.sunanda.newroutine.application.adapter.Ward_Adapter;
import com.sunanda.newroutine.application.adapter.Zone_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.newactivity.EditSchoolNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.OMASNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.RoutineNewSample_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditSampleCollection_Activity extends NavigationBar_Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    boolean isZoom = false;

    @SuppressLint("RestrictedApi")
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.INTERVAL);
        mLocationRequest.setFastestInterval(Constants.FIRST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    DatabaseHandler databaseHandler;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplecollection_activity);
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(EditSampleCollection_Activity.this)
                .addConnectionCallbacks(EditSampleCollection_Activity.this)
                .addOnConnectionFailedListener(EditSampleCollection_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        databaseHandler = new DatabaseHandler(EditSampleCollection_Activity.this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(EditSampleCollection_Activity.this, mGoogleApiClient);

        spSelectSourceSite.setEnabled(false);
        spTypeOfLocality.setEnabled(false);
        spWaterSourceType.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    TextView tvDate, tvTime, tvDistrict, tvLatitude, tvLongitude, tvResidualChlorineResultOMAS, tvResidualChlorineDescriptionOMAS;

    Spinner spSelectSourceSite, spIsItSpecialDrive, spNameSpecialDrive, spTypeOfLocality, spWaterSourceType,
            spBlock, spPanchayat, spVillageName, spHabitation, spNameTown, spWardNumber, spHandPumpCategory,
            spDescriptionLocation, spHealthFacility, spIsItNewLocation, spSelectScheme, spSelectSource,
            spZoneNumber, spBigDiaTubeWellNumber, spCollectingSample, spResidualChlorineRoutine, spResidualChlorineTested,
            spSubSourceType, spConditionOfSource, spChamberAvailable;

    TextInputEditText tietZoneNumber, tietBigDiaTubeWellNumber, tietLocationDescription, tietPipesTubeWell, tietSampleBottleNumber,
            tietResidualChlorineRoutine, tietSchemeName, tietSubSourceTypeOther, tietWaterLevel, tietPinCode;

    AutoCompleteTextView actvResidualChlorineInputOMAS;

    ImageView ivPicture, ivPictureSampleBottle;

    Button btnTakeImage, btnDeviceLocation, btnNext, btnTakeImageSampleBottle;

    LinearLayout llNameSpecialDrive, llNameTown, llWardNumber, llBlock, llPanchayat, llVillageName, llHabitation,
            llIsItNewLocation, llHealthFacility, llDescriptionLocation, llHandPumpCategory, llSelectScheme, llSelectSource,
            llZoneNumber, llBigDiaTubeWellNumber, llLocationDescription, llPipesTubeWell,
            llResidualChlorineRoutine, llResidualChlorineTested, llSubSourceType, llSchemeName, llSubSourceTypeOther,
            llConditionOfSource, llResidualChlorineInputOMAS, llResidualChlorineResultOMAS, llResidualChlorineDescriptionOMAS,
            llChamberAvailable, llWaterLevel;

    ArrayList<CommonModel> cmaSourceSite = new ArrayList<>();
    ArrayList<CommonModel> cmaSpecialDrive = new ArrayList<>();
    ArrayList<CommonModel> cmaSourceType = new ArrayList<>();
    ArrayList<CommonModel> cmaSubSourceType = new ArrayList<>();
    ArrayList<CommonModel> cmaBlock = new ArrayList<>();
    ArrayList<CommonModel> cmaPanchayat = new ArrayList<>();
    ArrayList<CommonModel> cmaVillageName = new ArrayList<>();
    ArrayList<CommonModel> cmaHabitationName = new ArrayList<>();
    ArrayList<CommonModel> cmaDescriptionLocation = new ArrayList<>();
    ArrayList<CommonModel> cmaTown = new ArrayList<>();
    ArrayList<CommonModel> cmaWord = new ArrayList<>();
    ArrayList<CommonModel> cmaHealthFacility = new ArrayList<>();
    ArrayList<CommonModel> cmaScheme = new ArrayList<>();
    ArrayList<CommonModel> cmaZone = new ArrayList<>();
    ArrayList<CommonModel> cmaBigDiaTubeWellNumber = new ArrayList<>();

    ArrayList<String> stringArrayIsItSpecialDrive = new ArrayList<>();
    ArrayList<String> stringArrayListLocality = new ArrayList<>();
    ArrayList<String> stringArrayCollectingSample = new ArrayList<>();
    ArrayList<String> stringArrayListResidualChlorine = new ArrayList<>();
    ArrayList<String> stringArrayListResidualChlorineTested = new ArrayList<>();
    ArrayList<String> stringArrayListConditionOfSource = new ArrayList<>();

    private static final int CAMERA_REQUEST = 11;
    private static final int CAMERA_REQUEST_1 = 12;
    private String mCurrentPhotoPath, mCurrentPhotoPath_1;
    SampleModel sampleModel;
    ProgressDialog progressdialog;

    private void init() {
        create("Source Identification(Edit)");

        progressdialog = new ProgressDialog(EditSampleCollection_Activity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        progressdialog.show();

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvResidualChlorineResultOMAS = findViewById(R.id.tvResidualChlorineResultOMAS);
        tvResidualChlorineDescriptionOMAS = findViewById(R.id.tvResidualChlorineDescriptionOMAS);

        spSelectSourceSite = findViewById(R.id.spSelectSourceSite);
        spIsItSpecialDrive = findViewById(R.id.spIsItSpecialDrive);
        spNameSpecialDrive = findViewById(R.id.spNameSpecialDrive);
        spTypeOfLocality = findViewById(R.id.spTypeOfLocality);
        spWaterSourceType = findViewById(R.id.spWaterSourceType);
        spBlock = findViewById(R.id.spBlock);
        spPanchayat = findViewById(R.id.spPanchayat);
        spVillageName = findViewById(R.id.spVillageName);
        spHabitation = findViewById(R.id.spHabitation);
        spNameTown = findViewById(R.id.spNameTown);
        spWardNumber = findViewById(R.id.spWardNumber);
        spHandPumpCategory = findViewById(R.id.spHandPumpCategory);
        spDescriptionLocation = findViewById(R.id.spDescriptionLocation);
        spHealthFacility = findViewById(R.id.spHealthFacility);
        spIsItNewLocation = findViewById(R.id.spIsItNewLocation);
        spSelectScheme = findViewById(R.id.spSelectScheme);
        spSelectSource = findViewById(R.id.spSelectSource);
        spZoneNumber = findViewById(R.id.spZoneNumber);
        spBigDiaTubeWellNumber = findViewById(R.id.spBigDiaTubeWellNumber);
        spCollectingSample = findViewById(R.id.spCollectingSample);
        spResidualChlorineRoutine = findViewById(R.id.spResidualChlorineRoutine);
        spResidualChlorineTested = findViewById(R.id.spResidualChlorineTested);
        spSubSourceType = findViewById(R.id.spSubSourceType);
        spConditionOfSource = findViewById(R.id.spConditionOfSource);
        spChamberAvailable = findViewById(R.id.spChamberAvailable);

        tietZoneNumber = findViewById(R.id.tietZoneNumber);
        tietBigDiaTubeWellNumber = findViewById(R.id.tietBigDiaTubeWellNumber);
        tietLocationDescription = findViewById(R.id.tietLocationDescription);
        tietPipesTubeWell = findViewById(R.id.tietPipesTubeWell);
        tietSampleBottleNumber = findViewById(R.id.tietSampleBottleNumber);
        tietResidualChlorineRoutine = findViewById(R.id.tietResidualChlorineRoutine);
        tietSchemeName = findViewById(R.id.tietSchemeName);
        tietSubSourceTypeOther = findViewById(R.id.tietSubSourceTypeOther);
        tietWaterLevel = findViewById(R.id.tietWaterLevel);
        tietPinCode = findViewById(R.id.tietPinCode);

        actvResidualChlorineInputOMAS = findViewById(R.id.actvResidualChlorineInputOMAS);

        ivPicture = findViewById(R.id.ivPicture);
        ivPictureSampleBottle = findViewById(R.id.ivPictureSampleBottle);

        btnTakeImage = findViewById(R.id.btnTakeImage);
        btnDeviceLocation = findViewById(R.id.btnDeviceLocation);
        btnNext = findViewById(R.id.btnNext);
        btnTakeImageSampleBottle = findViewById(R.id.btnTakeImageSampleBottle);

        llNameSpecialDrive = findViewById(R.id.llNameSpecialDrive);
        llNameTown = findViewById(R.id.llNameTown);
        llWardNumber = findViewById(R.id.llWardNumber);
        llBlock = findViewById(R.id.llBlock);
        llPanchayat = findViewById(R.id.llPanchayat);
        llVillageName = findViewById(R.id.llVillageName);
        llHabitation = findViewById(R.id.llHabitation);
        llIsItNewLocation = findViewById(R.id.llIsItNewLocation);
        llHealthFacility = findViewById(R.id.llHealthFacility);
        llDescriptionLocation = findViewById(R.id.llDescriptionLocation);
        llHandPumpCategory = findViewById(R.id.llHandPumpCategory);
        llSelectScheme = findViewById(R.id.llSelectScheme);
        llSelectSource = findViewById(R.id.llSelectSource);
        llZoneNumber = findViewById(R.id.llZoneNumber);
        llBigDiaTubeWellNumber = findViewById(R.id.llBigDiaTubeWellNumber);
        llLocationDescription = findViewById(R.id.llLocationDescription);
        llPipesTubeWell = findViewById(R.id.llPipesTubeWell);
        llResidualChlorineRoutine = findViewById(R.id.llResidualChlorineRoutine);
        llResidualChlorineTested = findViewById(R.id.llResidualChlorineTested);
        llSubSourceType = findViewById(R.id.llSubSourceType);
        llSchemeName = findViewById(R.id.llSchemeName);
        llSubSourceTypeOther = findViewById(R.id.llSubSourceTypeOther);
        llConditionOfSource = findViewById(R.id.llConditionOfSource);
        llChamberAvailable = findViewById(R.id.llChamberAvailable);
        llWaterLevel = findViewById(R.id.llWaterLevel);

        llResidualChlorineInputOMAS = findViewById(R.id.llResidualChlorineInputOMAS);
        llResidualChlorineResultOMAS = findViewById(R.id.llResidualChlorineResultOMAS);
        llResidualChlorineDescriptionOMAS = findViewById(R.id.llResidualChlorineDescriptionOMAS);

        llNameSpecialDrive.setVisibility(View.GONE);
        llNameTown.setVisibility(View.GONE);
        llWardNumber.setVisibility(View.GONE);
        llBlock.setVisibility(View.GONE);
        llPanchayat.setVisibility(View.GONE);
        llVillageName.setVisibility(View.GONE);
        llHabitation.setVisibility(View.GONE);
        llIsItNewLocation.setVisibility(View.GONE);
        llHealthFacility.setVisibility(View.GONE);
        llDescriptionLocation.setVisibility(View.GONE);
        llHandPumpCategory.setVisibility(View.GONE);
        llSelectScheme.setVisibility(View.GONE);
        llSelectSource.setVisibility(View.GONE);
        llZoneNumber.setVisibility(View.GONE);
        llBigDiaTubeWellNumber.setVisibility(View.GONE);
        llLocationDescription.setVisibility(View.GONE);
        llPipesTubeWell.setVisibility(View.GONE);
        llResidualChlorineTested.setVisibility(View.GONE);
        llResidualChlorineRoutine.setVisibility(View.GONE);
        llResidualChlorineInputOMAS.setVisibility(View.GONE);
        llResidualChlorineResultOMAS.setVisibility(View.GONE);
        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
        llSubSourceType.setVisibility(View.GONE);
        llSchemeName.setVisibility(View.GONE);
        llSubSourceTypeOther.setVisibility(View.GONE);
        llConditionOfSource.setVisibility(View.GONE);
        llChamberAvailable.setVisibility(View.GONE);
        llWaterLevel.setVisibility(View.GONE);

        btnTakeImage.setOnClickListener(new View.OnClickListener() {
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
                        Uri photoURI = FileProvider.getUriForFile(EditSampleCollection_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        btnTakeImageSampleBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;
                try {
                    f = setUpPhotoFile_1();
                    mCurrentPhotoPath_1 = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (Exception e) {
                    f = null;
                    mCurrentPhotoPath_1 = null;
                    e.printStackTrace();
                }
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_1);*/

                if (ContextCompat.checkSelfPermission(
                        EditSampleCollection_Activity.this,
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                    String[] PERMISSIONS = {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                    };
                    ActivityCompat.requestPermissions(
                            EditSampleCollection_Activity.this, PERMISSIONS, 0
                    );
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            File photoFile = setUpPhotoFile_1();
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(
                                        EditSampleCollection_Activity.this,
                                        "com.sunanda.newroutine.application.fileprovider",
                                        photoFile
                                );
                                //mCurrentPhotoPath = photoFile.getAbsolutePath();
                                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST_1);
                            }
                        } catch (Exception ex) {
                            mCurrentPhotoPath = null;
                            // Error occurred while creating the File
                            Toast.makeText(EditSampleCollection_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnDeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation == null) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setTitle("Location")
                            .setMessage("Please wait for Location...")
                            .setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    tvLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
                    tvLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
                    sAccuracy = String.valueOf(mLastLocation.getAccuracy());
                }
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditSampleCollection_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(EditSampleCollection_Activity.this, time_listener, hour,
                        minute, false).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditSampleCollection_Activity.this);
                builder1.setTitle("Water Quality");
                builder1.setMessage("Are you sure you want to save it?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        nextPageWithValidation();
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

        sampleModel = new SampleModel();
        int iId = CGlobal.getInstance().getPersistentPreference(EditSampleCollection_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        /*try {
            sTaskIdx = databaseHandler.getTaskIdOne();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String appName = CGlobal.getInstance().getPersistentPreference(EditSampleCollection_Activity.this)
                .getString(Constants.PREFS_APP_NAME, "");
        String sFCID = CGlobal.getInstance().getPersistentPreference(EditSampleCollection_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
        sampleModel = databaseHandler.getSampleEdit(iId, sFCID, appName);

        getSelectSourceSite();

        getIsItSpecialDrive();

        getNameSpecialDrive();

        getTypeOfLocality();

        getTown();

        getSourceType();

        CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sampleModel.getSource_type_q_6()).commit();

        getCollectingSample();

        getPanchayat(sampleModel.getBlock_q_8());

        getVillageName(sampleModel.getBlock_q_8(), sampleModel.getPanchayat_q_9());

        getHabitationName(sampleModel.getBlock_q_8(), sampleModel.getPanchayat_q_9(), sampleModel.getVillage_code());

        getSource();

        getHandPumpCategory();

        getDescriptionLocation(sampleModel.getBlock_q_8(), sampleModel.getPanchayat_q_9(), sampleModel.getVillage_code(), sampleModel.getHanitation_code());

        getConditionOfSource();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataBaseEditMode();
                progressdialog.dismiss();
            }
        }, 5000);

        tietResidualChlorineRoutine.setFilters(new InputFilter[]{new DigitsKeyListener(
                Boolean.FALSE, Boolean.TRUE) {
            int beforeDecimal = 1, afterDecimal = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String etText = tietResidualChlorineRoutine.getText().toString();
                if (etText.isEmpty()) {
                    return null;
                }
                String temp = tietResidualChlorineRoutine.getText() + source.toString();
                if (temp.equalsIgnoreCase(".")) {
                    return "0.";
                } else if (temp.toString().indexOf(".") == -1) {
                    // no decimal point placed yet
                    if (temp.length() > beforeDecimal) {
                        return "";
                    }
                } else {
                    int dotPosition;
                    int cursorPositon = tietResidualChlorineRoutine.getSelectionStart();
                    if (etText.indexOf(".") == -1) {
                        Log.i("First time Dot", etText.toString().indexOf(".") + " " + etText);
                        dotPosition = temp.indexOf(".");
                        Log.i("dot Positon", cursorPositon + "");
                        Log.i("dot Positon", etText + "");
                        Log.i("dot Positon", dotPosition + "");
                    } else {
                        dotPosition = etText.indexOf(".");
                        Log.i("dot Positon", cursorPositon + "");
                        Log.i("dot Positon", etText + "");
                        Log.i("dot Positon", dotPosition + "");
                    }
                    if (cursorPositon <= dotPosition) {
                        Log.i("cursor position", "in left");
                        String beforeDot = etText.substring(0, dotPosition);
                        if (beforeDot.length() < beforeDecimal) {
                            return source;
                        } else {
                            if (source.toString().equalsIgnoreCase(".")) {
                                return source;
                            } else {
                                return "";
                            }
                        }
                    } else {
                        Log.i("cursor position", "in right");
                        temp = temp.substring(temp.indexOf(".") + 1);
                        if (temp.length() > afterDecimal) {
                            return "";
                        }
                    }
                }
                return super.filter(source, start, end, dest, dstart, dend);
            }
        }});
    }

    /*String sSourceSite = "", sSourceSiteId = "", sIsItSpecialDrive = "", sSpecialDrive = "", sSpecialDriveId = "",
            sTypeOfLocality = "", sSourceType = "", sSourceTypeId = "", sBlockName = "", sBlockId = "", sPanchayatName = "",
            sPanchayatId = "", sVillageNameName = "", sVillageNameId = "", sHabitationNameName = "", sHabitationNameId = "",
            sSourceLocalityName = "", sSourceLocalityId = "", sTown = "", sTownId = "", sWard_no = "", sHealth_facility_name = "",
            sHealth_facility_Id = "", sHandPumpCategory = "", sIsItNewLocation = "", sScheme = "", sSchemeCode = "", sSchemeId = "",
            sSelectSource = "", sZone = "", sBig_dia_tube_well_no = "", sBig_dia_tube_well_Code = "", sCollectingSample = "",
            sResidualChlorine = "", sResidualChlorineTested = "", sResidualChlorineTestedValue = "", sSubSourceTypeId = "", sSubSourceType = "",
            sSubSchemeName = "";*/

    String sSourceSite = "", sIsItSpecialDrive = "", sSpecialDrive = "",
            sTypeOfLocality = "", sSourceType = "", sSourceTypeId = "", sBlockName = "", sBlockCode = "", sPanchayatName = "",
            sPanchayatCode = "", sVillageNameName = "", sVillageNameCode = "", sHabitationNameName = "", sHabitationNameCode = "",
            sSourceLocalityName = "", sTown = "", sWard_no = "", sHealth_facility_name = "",
            sHandPumpCategory = "", sIsItNewLocation = "", sScheme = "", sSchemeCode = "",
            sSelectSource = "", sZone = "", sBig_dia_tube_well_no = "", sBig_dia_tube_well_Code = "", sCollectingSample = "",
            sResidualChlorine = "", sResidualChlorineTested = "", sResidualChlorineTestedValue = "", sSubSourceType = "", sSubSourceTypeId = "",
            sSubSchemeName = "", sConditionOfSource = "", sTaskIdx = "", sArsenic_id = "", sChamberAvailable = "", sWaterLevel = "";

    private void getSelectSourceSite() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSourceSite = databaseHandler.getSourceSite("R");
        cmaSourceSite.add(0, commonModel);
        Master_Adapter sourceSiteMaster_adapter = new Master_Adapter(EditSampleCollection_Activity.this, cmaSourceSite);
        spSelectSourceSite.setAdapter(sourceSiteMaster_adapter);

        spSelectSourceSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceSite = cmaSourceSite.get(position).getName();
                if (sSourceSite.equalsIgnoreCase("Choose")) {
                    sSourceSite = "";
                    return;
                }
                if (sSourceSite.equalsIgnoreCase("Health Care Facility")) {
                    llHealthFacility.setVisibility(View.VISIBLE);
                    getSourceType();
                    getBlock();
                } else {
                    llHealthFacility.setVisibility(View.GONE);
                    sHealth_facility_name = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getIsItSpecialDrive() {
        stringArrayIsItSpecialDrive.add("Choose");
        stringArrayIsItSpecialDrive.add("Yes");
        stringArrayIsItSpecialDrive.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringArrayIsItSpecialDrive);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIsItSpecialDrive.setAdapter(adapter_IsItSpecialDrive);

        spIsItSpecialDrive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sIsItSpecialDrive = spIsItSpecialDrive.getSelectedItem().toString();
                switch (sIsItSpecialDrive) {
                    case "Choose":
                        sIsItSpecialDrive = "";
                        llNameSpecialDrive.setVisibility(View.GONE);
                        sSpecialDrive = "";
                        sChamberAvailable = "";
                        tietWaterLevel.setText("");
                        llChamberAvailable.setVisibility(View.GONE);
                        llWaterLevel.setVisibility(View.GONE);
                        return;
                    case "Yes":
                        llNameSpecialDrive.setVisibility(View.VISIBLE);
                        getNameSpecialDrive();
                        llChamberAvailable.setVisibility(View.GONE);
                        llWaterLevel.setVisibility(View.GONE);
                        break;
                    case "No":
                        llNameSpecialDrive.setVisibility(View.GONE);
                        sSpecialDrive = "";
                        sChamberAvailable = "";
                        tietWaterLevel.setText("");
                        llChamberAvailable.setVisibility(View.GONE);
                        llWaterLevel.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getNameSpecialDrive() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSpecialDrive = databaseHandler.getSpecialDrive("");
        cmaSpecialDrive.add(0, commonModel);
        Master_Adapter specialDriveMaster_adapter = new Master_Adapter(EditSampleCollection_Activity.this, cmaSpecialDrive);
        spNameSpecialDrive.setAdapter(specialDriveMaster_adapter);

        spNameSpecialDrive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSpecialDrive = cmaSpecialDrive.get(position).getName();
                if (sSpecialDrive.equalsIgnoreCase("Choose")) {
                    sSpecialDrive = "";
                    llChamberAvailable.setVisibility(View.GONE);
                    llWaterLevel.setVisibility(View.GONE);
                    sChamberAvailable = "";
                    tietWaterLevel.setText("");
                    return;
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    llChamberAvailable.setVisibility(View.VISIBLE);
                    llWaterLevel.setVisibility(View.GONE);
                    getChamberAvailable();
                } else {
                    sChamberAvailable = "";
                    tietWaterLevel.setText("");
                    llChamberAvailable.setVisibility(View.GONE);
                    llWaterLevel.setVisibility(View.GONE);
                }
                getBlock();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSpecial_drive_name_q_3())) {
            for (int i = 0; i < cmaSpecialDrive.size(); i++) {
                String specialdrive_id = cmaSpecialDrive.get(i).getName();
                if (!TextUtils.isEmpty(specialdrive_id)) {
                    if (specialdrive_id.equalsIgnoreCase(sampleModel.getSpecial_drive_name_q_3())) {
                        spNameSpecialDrive.setSelection(i);
                        sSpecialDrive = cmaSpecialDrive.get(i).getName();
                        if (sampleModel.getSpecial_drive_name_q_3().equalsIgnoreCase("ARSENIC TREND STATION")) {
                            llChamberAvailable.setVisibility(View.VISIBLE);
                            llWaterLevel.setVisibility(View.GONE);
                            getChamberAvailable();
                        } else {
                            sChamberAvailable = "";
                            tietWaterLevel.setText("");
                            llChamberAvailable.setVisibility(View.GONE);
                            llWaterLevel.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    private void getChamberAvailable() {
        ArrayList<String> stringArrayListChamberAvailable = new ArrayList<>();
        stringArrayListChamberAvailable.add("Choose");
        stringArrayListChamberAvailable.add("Yes");
        stringArrayListChamberAvailable.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                EditSampleCollection_Activity.this, android.R.layout.simple_spinner_item, stringArrayListChamberAvailable);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChamberAvailable.setAdapter(adapter_IsItSpecialDrive);

        spChamberAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sChamberAvailable = spChamberAvailable.getSelectedItem().toString();
                switch (sChamberAvailable) {
                    case "Choose":
                        sChamberAvailable = "";
                        tietWaterLevel.setText("");
                        llWaterLevel.setVisibility(View.GONE);
                        return;
                    case "Yes":
                        llWaterLevel.setVisibility(View.VISIBLE);
                        break;
                    case "No":
                        tietWaterLevel.setText("");
                        llWaterLevel.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getChamberAvailable())) {
            for (int i = 0; i < stringArrayListChamberAvailable.size(); i++) {
                String chamberAvailable = stringArrayListChamberAvailable.get(i);
                if (!TextUtils.isEmpty(chamberAvailable)) {
                    if (chamberAvailable.equalsIgnoreCase(sampleModel.getChamberAvailable())) {
                        spChamberAvailable.setSelection(i);
                        if (sampleModel.getChamberAvailable().equalsIgnoreCase("Yes")) {
                            llWaterLevel.setVisibility(View.VISIBLE);
                            tietWaterLevel.setText(sampleModel.getWaterLevel());
                        } else {
                            tietWaterLevel.setText("");
                            llWaterLevel.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    private void getTypeOfLocality() {
        stringArrayListLocality.add("Choose");
        stringArrayListLocality.add("RURAL");
        stringArrayListLocality.add("URBAN");

        ArrayAdapter<String> adapterLocality = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListLocality);
        adapterLocality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeOfLocality.setAdapter(adapterLocality);

        spTypeOfLocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTypeOfLocality = spTypeOfLocality.getSelectedItem().toString();
                switch (sTypeOfLocality) {
                    case "Choose":
                        sTypeOfLocality = "";
                        llNameTown.setVisibility(View.GONE);
                        llWardNumber.setVisibility(View.GONE);
                        llBlock.setVisibility(View.GONE);
                        llPanchayat.setVisibility(View.GONE);
                        llVillageName.setVisibility(View.GONE);
                        llHabitation.setVisibility(View.GONE);
                        return;
                    case "RURAL":
                        sTown = "";
                        sWard_no = "";

                        llNameTown.setVisibility(View.GONE);
                        llWardNumber.setVisibility(View.GONE);
                        llBlock.setVisibility(View.VISIBLE);
                        llPanchayat.setVisibility(View.VISIBLE);
                        llVillageName.setVisibility(View.VISIBLE);
                        llHabitation.setVisibility(View.VISIBLE);
                        spBlock.setVisibility(View.VISIBLE);
                        spPanchayat.setVisibility(View.GONE);
                        spVillageName.setVisibility(View.GONE);
                        spHabitation.setVisibility(View.GONE);
                        cmaSourceType = new ArrayList<>();
                        getSourceType();
                        getBlock();
                        break;
                    case "URBAN":
                        llNameTown.setVisibility(View.VISIBLE);
                        llWardNumber.setVisibility(View.VISIBLE);
                        llBlock.setVisibility(View.GONE);
                        llPanchayat.setVisibility(View.GONE);
                        llVillageName.setVisibility(View.GONE);
                        llHabitation.setVisibility(View.GONE);
                        spNameTown.setVisibility(View.VISIBLE);
                        spWardNumber.setVisibility(View.GONE);
                        cmaSourceType = new ArrayList<>();
                        getSourceType();
                        getTown();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getBlock();
    }

    private void getSourceType() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        /*if (sSpecialDrive.equalsIgnoreCase("IDCF") || sSourceSite.equalsIgnoreCase("Health Care Facility")) {
            cmaSourceType = databaseHandler.getSourceType();
        } else {
            cmaSourceType = databaseHandler.getSourceTypeNotIDCF();
        }*/
        cmaSourceType = databaseHandler.getSourceType();
        cmaSourceType.add(0, commonModel);
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(EditSampleCollection_Activity.this, cmaSourceType);
        spWaterSourceType.setAdapter(sourceTypeMaster_adapter);

        spWaterSourceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceType = cmaSourceType.get(position).getName();
                if (sSourceType.equalsIgnoreCase("Choose")) {
                    sSourceType = "";
                    sSourceTypeId = "";
                    return;
                }
                sSourceTypeId = cmaSourceType.get(position).getId();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sSourceType).commit();

                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else {
                        llSubSourceType.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    }
                } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                    if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        llSubSourceType.setVisibility(View.VISIBLE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else {
                        llSubSourceType.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSource_type_q_6())) {
            for (int i = 0; i < cmaSourceType.size(); i++) {
                String sourceTypeid = cmaSourceType.get(i).getName();
                if (!TextUtils.isEmpty(sourceTypeid)) {
                    if (sourceTypeid.equalsIgnoreCase(sampleModel.getSource_type_q_6())) {
                        spWaterSourceType.setSelection(i);
                        String sourceType = cmaSourceType.get(i).getName();
                        sSourceType = cmaSourceType.get(i).getName();
                        if (sampleModel.getSource_type_q_6().equalsIgnoreCase("RURAL")) {
                            if (sourceType.equalsIgnoreCase("TUBE WELL MARK II") || sourceType.equalsIgnoreCase("TUBE WELL ORDINARY")) {
                                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER") || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                                    sIsItNewLocation = "";
                                    llDescriptionLocation.setVisibility(View.VISIBLE);
                                    llLocationDescription.setVisibility(View.GONE);
                                    sIsItNewLocation = "No";
                                } else {
                                    sIsItNewLocation = "Yes";
                                    llDescriptionLocation.setVisibility(View.GONE);
                                    llLocationDescription.setVisibility(View.VISIBLE);
                                }
                            } else if (sourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT") ||
                                    sourceType.equalsIgnoreCase("OTHERS")) {
                                llSubSourceType.setVisibility(View.VISIBLE);
                                getSubSourceType(sSourceTypeId);
                                llSubSourceTypeOther.setVisibility(View.GONE);
                            } else {
                                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                                    sIsItNewLocation = "";
                                    llDescriptionLocation.setVisibility(View.VISIBLE);
                                    llLocationDescription.setVisibility(View.GONE);
                                    sIsItNewLocation = "No";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void getSubSourceType(String sourceTypeId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSubSourceType = databaseHandler.getChildSourceType(sourceTypeId);
        cmaSubSourceType.add(0, commonModel);
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(EditSampleCollection_Activity.this, cmaSubSourceType);
        spSubSourceType.setAdapter(sourceTypeMaster_adapter);

        spSubSourceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSubSourceType = cmaSubSourceType.get(position).getName();
                if (sSubSourceType.equalsIgnoreCase("Choose")) {
                    sSubSourceType = "";
                    sSubSourceTypeId = "";
                    return;
                }

                if (sSubSourceType.equalsIgnoreCase("OTHERS")) {
                    llSubSourceTypeOther.setVisibility(View.VISIBLE);
                } else {
                    llSubSourceTypeOther.setVisibility(View.GONE);
                }
                sSubSourceTypeId = cmaSubSourceType.get(position).getId();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSubSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sSubSourceType).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSub_source_type())) {
            for (int i = 0; i < cmaSubSourceType.size(); i++) {
                String subsourceType = cmaSubSourceType.get(i).getName();
                if (!TextUtils.isEmpty(subsourceType)) {
                    if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        if (!subsourceType.equalsIgnoreCase("TANKER WATER")) {
                            spSubSourceType.setSelection(i);
                            tietSubSourceTypeOther.setText(sampleModel.getSub_source_type());
                            llSubSourceTypeOther.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (subsourceType.equalsIgnoreCase(sampleModel.getSub_source_type())) {
                            spSubSourceType.setSelection(i);
                            //llSubSourceType.setVisibility(View.VISIBLE);
                            /*if (subsourceType.equalsIgnoreCase("OTHERS")) {
                                tietSubSourceTypeOther.setText(sampleModel.getSub_source_type());
                                //llSubSourceTypeOther.setVisibility(View.VISIBLE);
                            }*/
                        }
                    }
                }
            }
        }
    }

    private void getBlock() {
        CommonModel commonModel = new CommonModel();
        commonModel.setBlockname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaBlock = databaseHandler.getBlockRoster(sSpecialDrive);
        } else {
            cmaBlock = databaseHandler.getBlock();
        }

        cmaBlock.add(0, commonModel);
        Block_Adapter block_adapter = new Block_Adapter(EditSampleCollection_Activity.this, cmaBlock);
        spBlock.setAdapter(block_adapter);

        spBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sBlockName = cmaBlock.get(position).getBlockname();
                if (sBlockName.equalsIgnoreCase("Choose")) {
                    sBlockName = "";
                    sBlockCode = "";
                    spPanchayat.setVisibility(View.GONE);
                    spVillageName.setVisibility(View.GONE);
                    spHabitation.setVisibility(View.GONE);
                    return;
                }
                spPanchayat.setVisibility(View.VISIBLE);
                sBlockCode = cmaBlock.get(position).getBlockcode();
                getPanchayat(sBlockCode);
                if (sSourceSite.equalsIgnoreCase("Health Care Facility")) {
                    getHealthFacility(sBlockCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getBlock_q_8())) {
            for (int i = 0; i < cmaBlock.size(); i++) {
                String blockcode = cmaBlock.get(i).getBlockcode();
                if (!TextUtils.isEmpty(blockcode)) {
                    if (blockcode.equalsIgnoreCase(sampleModel.getBlock_q_8())) {
                        getSelectScheme(cmaBlock.get(i).getBlockname());
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getBlock_q_8())) {
            for (int i = 0; i < cmaBlock.size(); i++) {
                String block = cmaBlock.get(i).getBlockcode();
                if (!TextUtils.isEmpty(block)) {
                    if (block.equalsIgnoreCase(sampleModel.getBlock_q_8())) {
                        spBlock.setSelection(i);
                        if (sSourceSite.equalsIgnoreCase("Health Care Facility")) {
                            getHealthFacility(sampleModel.getBlock_q_8());
                        }
                    }
                }
            }
            llBlock.setVisibility(View.VISIBLE);
        } else {
            llBlock.setVisibility(View.GONE);
        }
    }

    private void getPanchayat(final String blockCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setPanchayatname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaPanchayat = databaseHandler.getPanchayatRoster(blockCode, sSpecialDrive);
        } else {
            cmaPanchayat = databaseHandler.getPanchayat(blockCode);
        }

        cmaPanchayat.add(0, commonModel);
        Panchayat_Adapter panchayat_adapter = new Panchayat_Adapter(EditSampleCollection_Activity.this, cmaPanchayat);
        spPanchayat.setAdapter(panchayat_adapter);

        spPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sPanchayatName = cmaPanchayat.get(position).getPanchayatname();
                if (sPanchayatName.equalsIgnoreCase("Choose")) {
                    sPanchayatName = "";
                    sPanchayatCode = "";
                    spVillageName.setVisibility(View.GONE);
                    spHabitation.setVisibility(View.GONE);
                    return;
                }
                spVillageName.setVisibility(View.VISIBLE);
                sPanchayatCode = cmaPanchayat.get(position).getPancode();
                getVillageName(blockCode, sPanchayatCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getPanchayat_q_9())) {
            for (int i = 0; i < cmaPanchayat.size(); i++) {
                String panchayat = cmaPanchayat.get(i).getPancode();
                if (!TextUtils.isEmpty(panchayat)) {
                    if (panchayat.equalsIgnoreCase(sampleModel.getPanchayat_q_9())) {
                        spPanchayat.setSelection(i);
                    }
                }
            }
            llPanchayat.setVisibility(View.VISIBLE);
            spPanchayat.setVisibility(View.VISIBLE);
        } else {
            llPanchayat.setVisibility(View.GONE);
        }
    }

    private void getVillageName(final String blockCode, final String panchayatCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setVillagename("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaVillageName = databaseHandler.getVillageNameRoster(blockCode, panchayatCode, sSpecialDrive);
        } else {
            cmaVillageName = databaseHandler.getVillageName(blockCode, panchayatCode);
        }

        cmaVillageName.add(0, commonModel);
        VillageName_Adapter villageName_adapter = new VillageName_Adapter(EditSampleCollection_Activity.this, cmaVillageName);
        spVillageName.setAdapter(villageName_adapter);

        spVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sVillageNameName = cmaVillageName.get(position).getVillagename();
                if (sVillageNameName.equalsIgnoreCase("Choose")) {
                    sVillageNameName = "";
                    sVillageNameCode = "";
                    spHabitation.setVisibility(View.GONE);
                    return;
                }
                spHabitation.setVisibility(View.VISIBLE);
                sVillageNameCode = cmaVillageName.get(position).getVillagecode();
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    getHabitationName(blockCode, panchayatCode, sVillageNameName);
                } else {
                    getHabitationName(blockCode, panchayatCode, sVillageNameCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getVillage_name_q_10())) {
            for (int i = 0; i < cmaVillageName.size(); i++) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    String villageName = cmaVillageName.get(i).getVillagename();
                    if (!TextUtils.isEmpty(villageName)) {
                        if (villageName.equalsIgnoreCase(sampleModel.getVillage_name_q_10())) {
                            spVillageName.setSelection(i);
                        }
                    }
                } else {
                    String villageCode = cmaVillageName.get(i).getVillagecode();
                    if (!TextUtils.isEmpty(villageCode)) {
                        if (villageCode.equalsIgnoreCase(sampleModel.getVillage_code())) {
                            spVillageName.setSelection(i);
                            sVillageNameName = sampleModel.getVillage_name_q_10();
                        }
                    }
                }
            }
            llVillageName.setVisibility(View.VISIBLE);
            spVillageName.setVisibility(View.VISIBLE);
        } else {
            llVillageName.setVisibility(View.GONE);
        }
    }

    private void getHabitationName(final String blockCode, final String panchayatCode, final String villageNameCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setHabitationname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaHabitationName = databaseHandler.getHabitationNameRoster(blockCode, panchayatCode, villageNameCode, sSpecialDrive);
        } else {
            cmaHabitationName = databaseHandler.getHabitationName(blockCode, panchayatCode, villageNameCode);
        }

        cmaHabitationName.add(0, commonModel);
        HabitationName_Adapter habitationName_adapter = new HabitationName_Adapter(EditSampleCollection_Activity.this, cmaHabitationName);
        spHabitation.setAdapter(habitationName_adapter);

        spHabitation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHabitationNameName = cmaHabitationName.get(position).getHabitationname();
                if (sHabitationNameName.equalsIgnoreCase("Choose")) {
                    sHabitationNameName = "";
                    sHabitationNameCode = "";
                    return;
                }
                sHabitationNameCode = cmaHabitationName.get(position).getHabecode();
                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, villageNameCode, sHabitationNameName);
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.VISIBLE);
                        llZoneNumber.setVisibility(View.VISIBLE);
                        llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, villageNameCode, sHabitationNameName);
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }

                        getSelectScheme(sBlockName);
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName);
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName);
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        getSelectScheme(sBlockName);
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER") || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                            llIsItNewLocation.setVisibility(View.GONE);
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        llPipesTubeWell.setVisibility(View.VISIBLE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, villageNameCode, sHabitationNameName);
                        } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, villageNameCode, sHabitationNameCode);
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        getHandPumpCategory();
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getHabitation_q_11())) {
            for (int i = 0; i < cmaHabitationName.size(); i++) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    String habitation = cmaHabitationName.get(i).getHabitationname();
                    if (!TextUtils.isEmpty(habitation)) {
                        if (habitation.equalsIgnoreCase(sampleModel.getHabitation_q_11())) {
                            spHabitation.setSelection(i);
                            sHabitationNameName = sampleModel.getHabitation_q_11();
                        }
                    }
                } else {
                    String habitationCode = cmaHabitationName.get(i).getHabecode();
                    if (!TextUtils.isEmpty(habitationCode)) {
                        if (habitationCode.equalsIgnoreCase(sampleModel.getHanitation_code())) {
                            spHabitation.setSelection(i);
                            sHabitationNameName = sampleModel.getHabitation_q_11();
                        }
                    }
                }
            }
            llHabitation.setVisibility(View.VISIBLE);
            spHabitation.setVisibility(View.VISIBLE);
        } else {
            llHabitation.setVisibility(View.GONE);
        }
    }

    private void getDescriptionLocation(String blockCode, String panchayatCode, String villageCode, String habitationCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setSourcelocalityname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaDescriptionLocation = databaseHandler.getRoster(blockCode, panchayatCode, villageCode, habitationCode, sSpecialDrive);
        } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
            cmaDescriptionLocation = databaseHandler.getArsenic(blockCode, panchayatCode, villageCode, habitationCode);
        }

        cmaDescriptionLocation.add(0, commonModel);
        DescriptionLocation_Adapter descriptionLocation_adapter = new DescriptionLocation_Adapter(EditSampleCollection_Activity.this, cmaDescriptionLocation);
        spDescriptionLocation.setAdapter(descriptionLocation_adapter);

        spDescriptionLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceLocalityName = cmaDescriptionLocation.get(position).getSourcelocalityname();
                if (sSourceLocalityName.equalsIgnoreCase("Choose")) {
                    sArsenic_id = "";
                    sSourceLocalityName = "";
                    return;
                }

                if (sSpecialDrive.equals("ARSENIC TREND STATION")) {
                    sArsenic_id = cmaDescriptionLocation.get(position).getArsenic_mid();
                } else {
                    sArsenic_id = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getExisting_location_q_13())) {
            for (int i = 0; i < cmaDescriptionLocation.size(); i++) {
                if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    String sourceloaclityid = cmaDescriptionLocation.get(i).getSourcelocalityname();
                    if (!TextUtils.isEmpty(sourceloaclityid)) {
                        if (sourceloaclityid.equalsIgnoreCase(sampleModel.getExisting_location_q_13())) {
                            spDescriptionLocation.setSelection(i);
                            sArsenic_id = sampleModel.getAts_id();
                        }
                    }
                    llDescriptionLocation.setVisibility(View.VISIBLE);
                    spDescriptionLocation.setVisibility(View.VISIBLE);
                } else if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    String sourceloaclityid = cmaDescriptionLocation.get(i).getSourcelocalityname();
                    if (!TextUtils.isEmpty(sourceloaclityid)) {
                        if (sourceloaclityid.equalsIgnoreCase(sampleModel.getExisting_location_q_13())) {
                            spDescriptionLocation.setSelection(i);
                        }
                    }
                    llDescriptionLocation.setVisibility(View.VISIBLE);
                    spDescriptionLocation.setVisibility(View.VISIBLE);
                } else {
                    llDescriptionLocation.setVisibility(View.GONE);
                    spDescriptionLocation.setVisibility(View.GONE);
                    llLocationDescription.setVisibility(View.VISIBLE);
                    tietLocationDescription.setVisibility(View.VISIBLE);
                    tietLocationDescription.setText(sampleModel.getExisting_location_q_13());
                }
            }
        }
    }

    private void getTown() {
        CommonModel commonModel = new CommonModel();
        commonModel.setTown_name("Choose");
        cmaTown = databaseHandler.getTown();
        cmaTown.add(0, commonModel);
        Town_Adapter town_adapter = new Town_Adapter(EditSampleCollection_Activity.this, cmaTown);
        spNameTown.setAdapter(town_adapter);

        spNameTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTown = cmaTown.get(position).getTown_name();
                if (sTown.equalsIgnoreCase("Choose")) {
                    spWardNumber.setVisibility(View.GONE);
                    sTown = "";
                    return;
                }
                spWardNumber.setVisibility(View.VISIBLE);
                getWardName(sTown);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getTown_q_7a())) {
            for (int i = 0; i < cmaTown.size(); i++) {
                String townid = cmaTown.get(i).getId();
                if (!TextUtils.isEmpty(townid)) {
                    if (townid.equalsIgnoreCase(sampleModel.getTown_q_7a())) {
                        getWardName(cmaTown.get(i).getTown_name());
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getTown_q_7a())) {
            for (int i = 0; i < cmaTown.size(); i++) {
                String townid = cmaTown.get(i).getId();
                if (!TextUtils.isEmpty(townid)) {
                    if (townid.equalsIgnoreCase(sampleModel.getTown_q_7a())) {
                        spNameTown.setSelection(i);
                    }
                }
            }
            llNameTown.setVisibility(View.VISIBLE);
            spNameTown.setVisibility(View.VISIBLE);
        } else {
            llNameTown.setVisibility(View.GONE);
        }
    }

    private void getWardName(String sTown) {
        CommonModel commonModel = new CommonModel();
        commonModel.setWard_no("Choose");
        cmaWord = databaseHandler.getWard(sTown);
        cmaWord.add(0, commonModel);
        Ward_Adapter ward_adapter = new Ward_Adapter(EditSampleCollection_Activity.this, cmaWord);
        spWardNumber.setAdapter(ward_adapter);

        spWardNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sWard_no = cmaWord.get(position).getWard_no();
                if (sWard_no.equalsIgnoreCase("Choose")) {
                    sWard_no = "";
                    return;
                }
                if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        getSelectScheme("");
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        getSelectScheme("");
                        getSource();
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        //getSelectScheme(sBlockName);
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        llPipesTubeWell.setVisibility(View.VISIBLE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        getHandPumpCategory();
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getWard_q_7b())) {
            for (int i = 0; i < cmaWord.size(); i++) {
                String word = cmaWord.get(i).getWard_no();
                if (!TextUtils.isEmpty(word)) {
                    if (word.equalsIgnoreCase(sampleModel.getWard_q_7b())) {
                        spWardNumber.setSelection(i);
                    }
                }
            }
            llWardNumber.setVisibility(View.VISIBLE);
            spWardNumber.setVisibility(View.VISIBLE);
        } else {
            llWardNumber.setVisibility(View.GONE);
        }
    }

    private void getHealthFacility(String blockCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setHealth_facility_name("Choose");
        cmaHealthFacility = databaseHandler.getHealthFacility(blockCode);
        cmaHealthFacility.add(0, commonModel);
        HealthFacility_Adapter ward_adapter = new HealthFacility_Adapter(EditSampleCollection_Activity.this, cmaHealthFacility);
        spHealthFacility.setAdapter(ward_adapter);

        spHealthFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHealth_facility_name = cmaHealthFacility.get(position).getHealth_facility_name();
                if (sHealth_facility_name.equalsIgnoreCase("Choose")) {
                    sHealth_facility_name = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getHealth_facility_q_1a())) {
            for (int i = 0; i < cmaHealthFacility.size(); i++) {
                String sHealthFacility = cmaHealthFacility.get(i).getHealth_facility_name();
                if (!TextUtils.isEmpty(sHealthFacility)) {
                    if (sHealthFacility.equalsIgnoreCase(sampleModel.getHealth_facility_q_1a())) {
                        spHealthFacility.setSelection(i);
                    }
                }
            }
            llHealthFacility.setVisibility(View.VISIBLE);
            spHealthFacility.setVisibility(View.VISIBLE);
        } else {
            llHealthFacility.setVisibility(View.GONE);
        }
    }

    private void getHandPumpCategory() {
        ArrayList<String> stringArrayHandPumpCategory = new ArrayList<>();
        stringArrayHandPumpCategory.add("Choose");
        stringArrayHandPumpCategory.add("PUBLIC PHE");
        stringArrayHandPumpCategory.add("PUBLIC PANCHAYAT");
        stringArrayHandPumpCategory.add("PUBLIC NGO");
        stringArrayHandPumpCategory.add("PUBLIC ISGP");
        stringArrayHandPumpCategory.add("PUBLIC OTHERS");
        stringArrayHandPumpCategory.add("PRIVATE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayHandPumpCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHandPumpCategory.setAdapter(adapter);

        spHandPumpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHandPumpCategory = spHandPumpCategory.getSelectedItem().toString();
                if (sHandPumpCategory.equalsIgnoreCase("Choose")) {
                    sHandPumpCategory = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getHand_pump_category_q_15())) {
            for (int i = 0; i < stringArrayHandPumpCategory.size(); i++) {
                String handPumpCategory = stringArrayHandPumpCategory.get(i);
                if (!TextUtils.isEmpty(handPumpCategory)) {
                    if (handPumpCategory.equalsIgnoreCase(sampleModel.getHand_pump_category_q_15())) {
                        spHandPumpCategory.setSelection(i);
                    }
                }
            }
            llHandPumpCategory.setVisibility(View.VISIBLE);
            spHandPumpCategory.setVisibility(View.VISIBLE);
        } else {
            llHandPumpCategory.setVisibility(View.GONE);
        }
    }

    private void getIsItNewLocation() {
        ArrayList<String> stringArrayIsItNewLocation = new ArrayList<>();
        stringArrayIsItNewLocation.add("Choose");
        stringArrayIsItNewLocation.add("Yes");
        stringArrayIsItNewLocation.add("No");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayIsItNewLocation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIsItNewLocation.setAdapter(adapter);

        spIsItNewLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sIsItNewLocation = spIsItNewLocation.getSelectedItem().toString();
                if (sIsItNewLocation.equalsIgnoreCase("Choose")) {
                    sIsItNewLocation = "";
                    llDescriptionLocation.setVisibility(View.GONE);
                    llLocationDescription.setVisibility(View.GONE);
                    return;
                } else if (sIsItNewLocation.equalsIgnoreCase("Yes")) {
                    llDescriptionLocation.setVisibility(View.GONE);
                    llLocationDescription.setVisibility(View.VISIBLE);
                } else if (sIsItNewLocation.equalsIgnoreCase("No")) {
                    llDescriptionLocation.setVisibility(View.VISIBLE);
                    llLocationDescription.setVisibility(View.GONE);
                    getDescriptionLocation(sBlockCode, sPanchayatCode, sVillageNameCode, sHabitationNameCode);
                    sLocationDescription = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getThis_new_location_q_12())) {
            for (int i = 0; i < stringArrayIsItNewLocation.size(); i++) {
                String isThisanewlocation = stringArrayIsItNewLocation.get(i);
                if (!TextUtils.isEmpty(isThisanewlocation)) {
                    if (isThisanewlocation.equalsIgnoreCase(sampleModel.getThis_new_location_q_12())) {
                        spIsItNewLocation.setSelection(i);
                    }
                }
            }
            llIsItNewLocation.setVisibility(View.VISIBLE);
            spIsItNewLocation.setVisibility(View.VISIBLE);
        } else {
            llIsItNewLocation.setVisibility(View.GONE);
        }
    }

    private void getSelectScheme(String sBlock) {
        cmaScheme = new ArrayList<>();
        if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")
                || sSourceType.equalsIgnoreCase("OTHERS")) {
            CommonModel commonModel = new CommonModel();
            commonModel.setPwssname("Choose");
            cmaScheme.add(commonModel);
            CommonModel commonModel1 = new CommonModel();
            commonModel1.setPwssname("NOT KNOWN");
            cmaScheme.add(commonModel1);
            CommonModel commonModel2 = new CommonModel();
            commonModel2.setPwssname("PROVIDE SCHEME NAME");
            cmaScheme.add(commonModel2);
        } else {
            cmaScheme = databaseHandler.getScheme(sBlock);
            CommonModel commonModel = new CommonModel();
            commonModel.setPwssname("Choose");
            cmaScheme.add(0, commonModel);
        }
        Scheme_Adapter scheme_adapter = new Scheme_Adapter(EditSampleCollection_Activity.this, cmaScheme);
        spSelectScheme.setAdapter(scheme_adapter);

        spSelectScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sScheme = cmaScheme.get(position).getPwssname();
                if (sScheme.equalsIgnoreCase("Choose")) {
                    sScheme = "";
                    sSchemeCode = "";
                    return;
                }
                if (sScheme.equalsIgnoreCase("NOT KNOWN")) {
                    llSchemeName.setVisibility(View.GONE);
                    sSubSchemeName = sScheme;
                    sSelectSource = "DISTRIBUTION SYSTEM";
                } else if (sScheme.equalsIgnoreCase("PROVIDE SCHEME NAME")) {
                    llSchemeName.setVisibility(View.VISIBLE);
                    sSelectSource = "DISTRIBUTION SYSTEM";
                } else {
                    llSchemeName.setVisibility(View.GONE);
                    sSchemeCode = cmaScheme.get(position).getSmcode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getScheme_q_11a())) {
            for (int i = 0; i < cmaScheme.size(); i++) {
                String schemeid = cmaScheme.get(i).getPwssname();
                if (!TextUtils.isEmpty(schemeid)) {
                    if (schemeid.equalsIgnoreCase(sampleModel.getScheme_q_11a())) {
                        getZoneNumber(cmaScheme.get(i).getSmcode());
                        if (!TextUtils.isEmpty(sampleModel.getZone_number_q_11c())) {
                            getBigDiaTubeWellNumber(cmaScheme.get(i).getSmcode(), sampleModel.getZone_number_q_11c());
                        }
                    }
                }
            }
        }
        if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")
                || sSourceType.equalsIgnoreCase("OTHERS")) {
            if (!TextUtils.isEmpty(sampleModel.getSub_scheme_name())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String subschemename = cmaScheme.get(i).getPwssname();
                    if (!TextUtils.isEmpty(subschemename)) {
                        if (subschemename.equalsIgnoreCase(sampleModel.getSub_scheme_name())) {
                            spSelectScheme.setSelection(i);
                        }
                    }
                }
                llSelectScheme.setVisibility(View.VISIBLE);
                spSelectScheme.setVisibility(View.VISIBLE);
            } else {
                llSelectScheme.setVisibility(View.GONE);
            }
        } else {
            if (!TextUtils.isEmpty(sampleModel.getScheme_q_11a())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String schemeid = cmaScheme.get(i).getPwssname();
                    if (!TextUtils.isEmpty(schemeid)) {
                        if (schemeid.equalsIgnoreCase(sampleModel.getScheme_q_11a())) {
                            spSelectScheme.setSelection(i);
                        }
                    }
                }
                llSelectScheme.setVisibility(View.VISIBLE);
                spSelectScheme.setVisibility(View.VISIBLE);
            } else {
                llSelectScheme.setVisibility(View.GONE);
            }
        }
    }

    private void getSource() {
        ArrayList<String> stringArraySource = new ArrayList<>();
        stringArraySource.add("Choose");
        stringArraySource.add("DISTRIBUTION SYSTEM");
        stringArraySource.add("PUMP HOUSE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArraySource);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSelectSource.setAdapter(adapter);

        spSelectSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSelectSource = spSelectSource.getSelectedItem().toString();
                if (sSelectSource.equalsIgnoreCase("Choose")) {
                    sSelectSource = "";
                    llZoneNumber.setVisibility(View.GONE);
                    llBigDiaTubeWellNumber.setVisibility(View.GONE);
                    return;
                }

                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                    } else {
                        llZoneNumber.setVisibility(View.VISIBLE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        getZoneNumber(sSchemeCode);
                    }
                } else {
                    if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                    } else {
                        llZoneNumber.setVisibility(View.VISIBLE);
                        llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);

                        spZoneNumber.setVisibility(View.GONE);
                        spBigDiaTubeWellNumber.setVisibility(View.GONE);

                        tietZoneNumber.setVisibility(View.VISIBLE);
                        tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSource_name_q_11d())) {
            for (int i = 0; i < stringArraySource.size(); i++) {
                String sourceName = stringArraySource.get(i);
                if (!TextUtils.isEmpty(sourceName)) {
                    if (sourceName.equalsIgnoreCase(sampleModel.getSource_name_q_11d())) {
                        spSelectSource.setSelection(i);
                    }
                }
            }
            if (sampleModel.getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
                llSelectSource.setVisibility(View.VISIBLE);
                spSelectSource.setVisibility(View.VISIBLE);
            } else {
                llSelectSource.setVisibility(View.GONE);
                spSelectSource.setVisibility(View.GONE);
            }
        } else {
            llSelectSource.setVisibility(View.GONE);
        }
    }

    private void getZoneNumber(final String sSchemeCode) {
        if (TextUtils.isEmpty(sSchemeCode)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please select Scheme")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }
        CommonModel commonModel = new CommonModel();
        commonModel.setZone("Choose");
        cmaZone = databaseHandler.getZone(sSchemeCode);
        if (!TextUtils.isEmpty(cmaZone.get(0).getZone())) {
            cmaZone.add(0, commonModel);
            tietZoneNumber.setVisibility(View.GONE);
            spZoneNumber.setVisibility(View.VISIBLE);
            Zone_Adapter zone_adapter = new Zone_Adapter(EditSampleCollection_Activity.this, cmaZone);
            spZoneNumber.setAdapter(zone_adapter);

            spZoneNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sZone = cmaZone.get(position).getZone();
                    if (sZone.equalsIgnoreCase("Choose")) {
                        sZone = "";
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        return;
                    }
                    llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    getBigDiaTubeWellNumber(sSchemeCode, sZone);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            llZoneNumber.setVisibility(View.VISIBLE);
            tietZoneNumber.setVisibility(View.VISIBLE);
            spZoneNumber.setVisibility(View.GONE);
            llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            spBigDiaTubeWellNumber.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getZone_number_q_11c())) {
            for (int i = 0; i < cmaZone.size(); i++) {
                String zone = cmaZone.get(i).getZone();
                if (!TextUtils.isEmpty(zone)) {
                    if (zone.equalsIgnoreCase(sampleModel.getZone_number_q_11c())) {
                        spZoneNumber.setSelection(i);
                    }
                    llZoneNumber.setVisibility(View.VISIBLE);
                    spZoneNumber.setVisibility(View.VISIBLE);
                } else {
                    tietZoneNumber.setText(sampleModel.getZone_number_q_11c());
                    tietBigDiaTubeWellNumber.setText(sampleModel.getBig_dia_tub_well_q_20());
                    tietZoneNumber.setVisibility(View.VISIBLE);
                    spZoneNumber.setVisibility(View.GONE);
                    llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    spBigDiaTubeWellNumber.setVisibility(View.GONE);
                }
            }
        }
    }

    private void getBigDiaTubeWellNumber(String sSchemeCode, String sZone) {
        CommonModel commonModel = new CommonModel();
        commonModel.setBig_dia_tube_well_no("Choose");
        cmaBigDiaTubeWellNumber = databaseHandler.getBigDiaTubeWellNo(sSchemeCode, sZone);
        if (cmaBigDiaTubeWellNumber.size() > 0) {
            if (!TextUtils.isEmpty(cmaBigDiaTubeWellNumber.get(0).getBig_dia_tube_well_no())) {
                tietBigDiaTubeWellNumber.setVisibility(View.GONE);
                spBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                cmaBigDiaTubeWellNumber.add(0, commonModel);
                BigDiaTubeWellNumber_Adapter bigDiaTubeWellNumber_adapter = new BigDiaTubeWellNumber_Adapter(EditSampleCollection_Activity.this, cmaBigDiaTubeWellNumber);
                spBigDiaTubeWellNumber.setAdapter(bigDiaTubeWellNumber_adapter);
                spBigDiaTubeWellNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sBig_dia_tube_well_no = cmaBigDiaTubeWellNumber.get(position).getBig_dia_tube_well_no();
                        if (sBig_dia_tube_well_no.equalsIgnoreCase("Choose")) {
                            sBig_dia_tube_well_no = "";
                            sBig_dia_tube_well_Code = "";
                            return;
                        }
                        sBig_dia_tube_well_Code = cmaBigDiaTubeWellNumber.get(position).getBig_dia_tube_well_code();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        } else {
            tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            spBigDiaTubeWellNumber.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getBig_dia_tub_well_q_20())) {
            if (cmaBigDiaTubeWellNumber.size() > 0) {
                for (int i = 0; i < cmaBigDiaTubeWellNumber.size(); i++) {
                    String bigDiaTubeWellNumber = cmaBigDiaTubeWellNumber.get(i).getBig_dia_tube_well_code();
                    if (!TextUtils.isEmpty(bigDiaTubeWellNumber)) {
                        if (bigDiaTubeWellNumber.equalsIgnoreCase(sampleModel.getBig_dia_tub_well_q_20())) {
                            spBigDiaTubeWellNumber.setSelection(i);
                        }
                    }
                }
                llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                tietBigDiaTubeWellNumber.setVisibility(View.GONE);
                spBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            } else {
                llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                spBigDiaTubeWellNumber.setVisibility(View.GONE);
                tietBigDiaTubeWellNumber.setText(sampleModel.getBig_dia_tub_well_q_20());
            }
        }
    }

    private void getCollectingSample() {
        stringArrayCollectingSample.add("Choose");
        stringArrayCollectingSample.add("FACILITATOR");
        stringArrayCollectingSample.add("LABORATORY STAFF");
        stringArrayCollectingSample.add("SAMPLING ASSISTANT");
        stringArrayCollectingSample.add("HEALTH PERSONNEL");
        stringArrayCollectingSample.add("MLV");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayCollectingSample);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCollectingSample.setAdapter(adapter);

        spCollectingSample.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCollectingSample = spCollectingSample.getSelectedItem().toString();
                if (sCollectingSample.equalsIgnoreCase("Choose")) {
                    sCollectingSample = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getConditionOfSource() {
        stringArrayListConditionOfSource = new ArrayList<>();
        stringArrayListConditionOfSource.add("Choose");
        stringArrayListConditionOfSource.add("FUNCTIONAL");
        stringArrayListConditionOfSource.add("DEFUNCT");

        ArrayAdapter<String> adapterConditionOfSource = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListConditionOfSource);
        adapterConditionOfSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spConditionOfSource.setAdapter(adapterConditionOfSource);

        spConditionOfSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sConditionOfSource = spConditionOfSource.getSelectedItem().toString();
                if (sConditionOfSource.equalsIgnoreCase("Choose")) {
                    sConditionOfSource = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (sSourceType.equalsIgnoreCase("TUBE WELL MARK II") || sSourceType.equalsIgnoreCase("TUBE WELL ORDINARY")) {
            if (!TextUtils.isEmpty(sampleModel.getCondition_of_source())) {
                for (int i = 0; i < stringArrayListConditionOfSource.size(); i++) {
                    String sConditionOfSourcename = stringArrayListConditionOfSource.get(i);
                    if (!TextUtils.isEmpty(sConditionOfSourcename)) {
                        if (sConditionOfSourcename.equalsIgnoreCase(sampleModel.getCondition_of_source())) {
                            spConditionOfSource.setSelection(i);
                            sConditionOfSource = sampleModel.getCondition_of_source();
                            llConditionOfSource.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } else {
                llConditionOfSource.setVisibility(View.GONE);
            }
        } else {
            llConditionOfSource.setVisibility(View.GONE);
        }
    }

    private void getResidualChlorineTested() {
        stringArrayListResidualChlorineTested = new ArrayList<>();
        stringArrayListResidualChlorineTested.add("Choose");
        stringArrayListResidualChlorineTested.add("Yes");
        stringArrayListResidualChlorineTested.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListResidualChlorineTested);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResidualChlorineTested.setAdapter(adapter_IsItSpecialDrive);

        spResidualChlorineTested.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sResidualChlorineTested = spResidualChlorineTested.getSelectedItem().toString();
                switch (sResidualChlorineTested) {
                    case "Choose":
                        sResidualChlorineTested = "";
                        llResidualChlorineRoutine.setVisibility(View.GONE);
                        llResidualChlorineInputOMAS.setVisibility(View.GONE);
                        llResidualChlorineResultOMAS.setVisibility(View.GONE);
                        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                        sResidualChlorineTestedValue = "";
                        return;
                    case "Yes":

                        /*if (sampleModel.getApp_name().equalsIgnoreCase("OMAS")) {
                            llResidualChlorineRoutine.setVisibility(View.GONE);
                            llResidualChlorineInputOMAS.setVisibility(View.VISIBLE);
                            llResidualChlorineResultOMAS.setVisibility(View.VISIBLE);
                            llResidualChlorineDescriptionOMAS.setVisibility(View.VISIBLE);
                            autoText();
                        } else {*/
                        llResidualChlorineRoutine.setVisibility(View.VISIBLE);
                        llResidualChlorineInputOMAS.setVisibility(View.GONE);
                        llResidualChlorineResultOMAS.setVisibility(View.GONE);
                        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                        getResidualChlorine();
                        //}

                        sResidualChlorineTestedValue = "1";
                        break;
                    case "No":
                        llResidualChlorineRoutine.setVisibility(View.GONE);
                        llResidualChlorineInputOMAS.setVisibility(View.GONE);
                        llResidualChlorineResultOMAS.setVisibility(View.GONE);
                        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                        sResidualChlorineTestedValue = "0";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*if (sampleModel.getApp_name().equalsIgnoreCase("OMAS")) {
            if (!TextUtils.isEmpty(sampleModel.getSource_type_q_6())) {
                for (int i = 0; i < cmaSourceType.size(); i++) {
                    String sourceTypeid = cmaSourceType.get(i).getName();
                    if (!TextUtils.isEmpty(sourceTypeid)) {
                        if (sourceTypeid.equals(sampleModel.getSource_type_q_6())) {
                            String sourceType = cmaSourceType.get(i).getName();
                            if (sourceType.equals("PIPED WATER SUPPLY") || sSourceType.equals("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                                    || sSourceType.equals("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                                if (!TextUtils.isEmpty(sampleModel.getResidual_chlorine_tested())) {
                                    for (int j = 0; j < stringArrayListResidualChlorineTested.size(); j++) {
                                        String rsidualChlorineTested = stringArrayListResidualChlorineTested.get(j);
                                        if (!TextUtils.isEmpty(rsidualChlorineTested)) {
                                            if (sampleModel.getResidual_chlorine_tested().equals("1") && rsidualChlorineTested.equals("Yes")) {
                                                spResidualChlorineTested.setSelection(j);
                                                llResidualChlorineTested.setVisibility(View.VISIBLE);
                                                llResidualChlorineRoutine.setVisibility(View.GONE);
                                                llResidualChlorineInputOMAS.setVisibility(View.VISIBLE);
                                                llResidualChlorineResultOMAS.setVisibility(View.VISIBLE);
                                                llResidualChlorineDescriptionOMAS.setVisibility(View.VISIBLE);

                                                actvResidualChlorineInputOMAS.setText(sampleModel.getResidual_chlorine_value());
                                                tvResidualChlorineDescriptionOMAS.setText(sampleModel.getResidual_chlorine_description());
                                                tvResidualChlorineResultOMAS.setText(sampleModel.getResidual_chlorine_result());
                                                autoText();

                                            } else if (sampleModel.getResidual_chlorine_tested().equals("0") && rsidualChlorineTested.equals("No")) {
                                                spResidualChlorineTested.setSelection(j);
                                                llResidualChlorineTested.setVisibility(View.VISIBLE);
                                                llResidualChlorineRoutine.setVisibility(View.GONE);
                                                llResidualChlorineInputOMAS.setVisibility(View.GONE);
                                                llResidualChlorineResultOMAS.setVisibility(View.GONE);
                                                llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);

                                                actvResidualChlorineInputOMAS.setText("");
                                                tvResidualChlorineDescriptionOMAS.setText("");
                                                tvResidualChlorineResultOMAS.setText("");
                                            }
                                        }
                                    }
                                }
                            } else {
                                llResidualChlorineTested.setVisibility(View.GONE);
                                llResidualChlorineRoutine.setVisibility(View.GONE);
                                llResidualChlorineInputOMAS.setVisibility(View.GONE);
                                llResidualChlorineResultOMAS.setVisibility(View.GONE);
                                llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        } else {*/

        if (!TextUtils.isEmpty(sampleModel.getSource_type_q_6())) {
            for (int i = 0; i < cmaSourceType.size(); i++) {
                String sourceTypeid = cmaSourceType.get(i).getName();
                if (!TextUtils.isEmpty(sourceTypeid)) {
                    if (sourceTypeid.equalsIgnoreCase(sampleModel.getSource_type_q_6())) {
                        String sourceType = cmaSourceType.get(i).getName();
                        if (sourceType.equalsIgnoreCase("PIPED WATER SUPPLY") || sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                                || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                            if (!TextUtils.isEmpty(sampleModel.getResidual_chlorine_tested())) {
                                for (int j = 0; j < stringArrayListResidualChlorineTested.size(); j++) {
                                    String rsidualChlorineTested = stringArrayListResidualChlorineTested.get(j);
                                    if (!TextUtils.isEmpty(rsidualChlorineTested)) {
                                        if (sampleModel.getResidual_chlorine_tested().equalsIgnoreCase("1") && rsidualChlorineTested.equalsIgnoreCase("Yes")) {
                                            spResidualChlorineTested.setSelection(j);
                                            llResidualChlorineTested.setVisibility(View.VISIBLE);
                                            if (sampleModel.getResidual_chlorine_tested().equalsIgnoreCase("1")) {
                                                llResidualChlorineRoutine.setVisibility(View.VISIBLE);
                                                llResidualChlorineInputOMAS.setVisibility(View.GONE);
                                                llResidualChlorineResultOMAS.setVisibility(View.GONE);
                                                llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                                                getResidualChlorine();
                                            } else {
                                                llResidualChlorineRoutine.setVisibility(View.GONE);
                                                llResidualChlorineInputOMAS.setVisibility(View.GONE);
                                                llResidualChlorineResultOMAS.setVisibility(View.GONE);
                                                llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                                            }

                                        } else if (sampleModel.getResidual_chlorine_tested().equalsIgnoreCase("0") && rsidualChlorineTested.equalsIgnoreCase("No")) {
                                            spResidualChlorineTested.setSelection(j);
                                            llResidualChlorineTested.setVisibility(View.VISIBLE);
                                            llResidualChlorineRoutine.setVisibility(View.GONE);
                                            llResidualChlorineInputOMAS.setVisibility(View.GONE);
                                            llResidualChlorineResultOMAS.setVisibility(View.GONE);
                                            llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        } else {
                            llResidualChlorineTested.setVisibility(View.GONE);
                            llResidualChlorineRoutine.setVisibility(View.GONE);
                            llResidualChlorineInputOMAS.setVisibility(View.GONE);
                            llResidualChlorineResultOMAS.setVisibility(View.GONE);
                            llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }

        //}
    }

    ArrayList<CommonModel> commonModelArrayListAutoText;

    private void autoText() {
        commonModelArrayListAutoText = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(EditSampleCollection_Activity.this);
        commonModelArrayListAutoText = databaseHandler.getResidualChlorineResult();
        AutoText_Adapter block_adapter = new AutoText_Adapter(EditSampleCollection_Activity.this, R.layout.samplecollection_activity,
                R.id.item, commonModelArrayListAutoText);
        actvResidualChlorineInputOMAS.setThreshold(1);
        actvResidualChlorineInputOMAS.setAdapter(block_adapter);

        actvResidualChlorineInputOMAS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                sResidualChlorineValue = commonModelArrayListAutoText.get(pos).getChlorine_Value();
                tvResidualChlorineDescriptionOMAS.setText(commonModelArrayListAutoText.get(pos).getResultDescription());
                tvResidualChlorineResultOMAS.setText(commonModelArrayListAutoText.get(pos).getResult());
            }
        });
    }

    private void getResidualChlorine() {
        stringArrayListResidualChlorine = new ArrayList<>();
        stringArrayListResidualChlorine.add("Choose");
        stringArrayListResidualChlorine.add("VISUAL COMPARATOMETRIC METHOD");
        //stringArrayListResidualChlorine.add("DPD1 TABLET WITH MANUAL COLOR COMPARISON");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListResidualChlorine);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResidualChlorineRoutine.setAdapter(adapter);

        spResidualChlorineRoutine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sResidualChlorine = spResidualChlorineRoutine.getSelectedItem().toString();
                if (sResidualChlorine.equalsIgnoreCase("Choose")) {
                    sResidualChlorine = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getResidual_chlorine())) {
            for (int k = 0; k < stringArrayListResidualChlorine.size(); k++) {
                String sResidualChlorine = stringArrayListResidualChlorine.get(k);
                if (!TextUtils.isEmpty(sResidualChlorine)) {
                    if (sResidualChlorine.equalsIgnoreCase(sampleModel.getResidual_chlorine())) {
                        spResidualChlorineRoutine.setSelection(k);
                    }
                }
            }
        } else{
            if (sampleModel.getApp_name().equalsIgnoreCase("OMAS")) {
                spResidualChlorineRoutine.setSelection(1);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(EditSampleCollection_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditSampleCollection_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EditSampleCollection_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mLastLocation == null) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please wait getting Location and Please click Restaurant/Pandal")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto();
        } else if (requestCode == CAMERA_REQUEST_1 && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto_1();
        }
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tvDate.setText(df.format(myCalendar.getTime()));
    }

    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            tvTime.setText(time1);
        }
    };

    private static final String JPEG_FILE_PREFIX = "img_source_";
    private static final String JPEG_FILE_SUFFIX = ".png";

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
        ivPicture.setImageBitmap(bitmap);
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
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//      check the rotation of the image and display it properly
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


    private static final String JPEG_FILE_PREFIX_1 = "img_sample_bottle_";

    private File setUpPhotoFile_1() {
        File f = null;
        try {
            f = createImageFile_1();
            mCurrentPhotoPath_1 = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    private File createImageFile_1() {
        // Create an image file name
        File imageF = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = JPEG_FILE_PREFIX_1 + timeStamp + "_";
            File albumF = getAlbumDir_1();
            imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageF;
    }

    private File getAlbumDir_1() {
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

    private void handleBigCameraPhoto_1() {
        if (mCurrentPhotoPath_1 != null) {
            setPic_1();
        }
    }

    private void setPic_1() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        compressImage(mCurrentPhotoPath_1);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        ivPictureSampleBottle.setImageBitmap(bitmap);
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


    String sData = "", sTime = "", sDistrict = "", sLatitude = "", sLongitude = "", sLocationDescription = "",
            sPipesTubeWell = "", sSampleBottleNumber = "", sZoneValue = "", sBigDiaTubeWellValue = "", sAccuracy = "",
            sResidualChlorineValue = "", sSampleId = "", sResidualChlorineResultOMAS = "", sResidualChlorineDescriptionOMAS = "",
            sMobileIMEI = "", sMobileSerialNo = "", sMobileModelNo = "", sPinCode = "";
    int iTotalDepth;

    private void nextPageWithValidation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        long unixTime = System.currentTimeMillis() / 1000L;

        sData = tvDate.getText().toString();
        sTime = tvTime.getText().toString();
        sDistrict = tvDistrict.getText().toString();
        sLatitude = tvLatitude.getText().toString();
        sLongitude = tvLongitude.getText().toString();
        sZoneValue = tietZoneNumber.getText().toString();
        sBigDiaTubeWellValue = tietBigDiaTubeWellNumber.getText().toString();
        sLocationDescription = tietLocationDescription.getText().toString();
        sPipesTubeWell = tietPipesTubeWell.getText().toString();
        sSampleBottleNumber = tietSampleBottleNumber.getText().toString();
        sWaterLevel = tietWaterLevel.getText().toString();
        sPinCode = tietPinCode.getText().toString();

        //if (sampleModel.getApp_name().equalsIgnoreCase("OMAS")) {

        //sResidualChlorineValue = actvResidualChlorineInputOMAS.getText().toString();
        sResidualChlorineDescriptionOMAS = "";
        sResidualChlorineResultOMAS = "";

        //} else {

        sResidualChlorineValue = tietResidualChlorineRoutine.getText().toString();

        //}

        if (sSourceType.equalsIgnoreCase("OTHERS")) {
            if (sSubSourceType.equalsIgnoreCase("OTHERS")) {
                sSubSourceType = tietSubSourceTypeOther.getText().toString();
            }
        }

        if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (sScheme.equalsIgnoreCase("PROVIDE SCHEME NAME")) {
                sSubSchemeName = tietSchemeName.getText().toString();
            }
        }

        String timeofdatacollection = sData + " " + sTime;

        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            sMobileModelNo = manufacturer + model;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                sMobileIMEI = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (mTelephony.getDeviceId() != null) {
                    sMobileIMEI = mTelephony.getDeviceId();
                    sMobileSerialNo = mTelephony.getSimSerialNumber();
                } else {
                    sMobileIMEI = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(sSourceSite) || sSourceSite.equalsIgnoreCase("Choose")) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Select Source Site")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sSourceSite.equalsIgnoreCase("Health Care Facility")) {
            if (TextUtils.isEmpty(sHealth_facility_name) || sHealth_facility_name.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please Select Health Facility")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        }

        if (sIsItSpecialDrive.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sIsItSpecialDrive)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please select special drive")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sIsItSpecialDrive.equalsIgnoreCase("Yes")) {
            if (TextUtils.isEmpty(sSpecialDrive) || sSpecialDrive.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select special drive Name")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        }

        if (TextUtils.isEmpty(sData)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Enter Date")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(sTime)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Enter Time")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sTypeOfLocality.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sTypeOfLocality)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please select Locality")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {

            if (TextUtils.isEmpty(sSourceTypeId) || sSourceType.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Water Source Type")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (sSourceType.equalsIgnoreCase("OTHERS") || sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                    || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(sBlockCode)) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Block")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sPanchayatCode)) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Panchayat")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                if (TextUtils.isEmpty(sVillageNameName)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationNameName)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Habitation Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(sVillageNameName)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationNameName)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Habitation Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }

            if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                    || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select/Enter Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                if (TextUtils.isEmpty(sScheme) || sScheme.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sSelectSource.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sSelectSource)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Source")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (sSelectSource.equalsIgnoreCase("PUMP HOUSE")) {
                    if (!TextUtils.isEmpty(cmaZone.get(0).getZone())) {
                        if (sZone.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sZone)) {
                            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                    .setMessage("Please select Zone")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return;
                        } else {
                            if (!TextUtils.isEmpty(sBigDiaTubeWellValue)) {
                                sBig_dia_tube_well_Code = sBigDiaTubeWellValue;
                            }
                        }
                    } else {
                        if (TextUtils.isEmpty(sZoneValue) || TextUtils.isEmpty(sBigDiaTubeWellValue)) {
                            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                    .setMessage("Please Enter Zone and Big Dia Tube Well Value")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return;

                        } else {
                            sZone = sZoneValue;
                            sBig_dia_tube_well_Code = sBigDiaTubeWellValue;
                        }
                    }

                    if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        if (!sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                            if (TextUtils.isEmpty(sBig_dia_tube_well_Code)) {
                                if (!TextUtils.isEmpty(sSchemeCode)) {
                                    String spl[] = new String[0];
                                    try {
                                        spl = sSchemeCode.split("SM/");
                                    } catch (Exception e) {
                                        Log.e("Sample Error", "");
                                    }

                                    sBig_dia_tube_well_Code = spl[1] + "_" + sZone + "_" + sBig_dia_tube_well_no;
                                }
                            }
                        }
                    }

                    if (sBig_dia_tube_well_Code.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sBig_dia_tube_well_Code)) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please select Big Dia Tube Well Number")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }

                if (sIsItNewLocation.equalsIgnoreCase("No")) {
                    if (TextUtils.isEmpty(sSourceLocalityName.trim()) || sSourceLocalityName.trim().equalsIgnoreCase("Choose")) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Enter Location Description")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(sLocationDescription.trim())) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Enter Location Description")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }
            } else if (sSourceType.equals("OTHERS")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else {
                if (sIsItNewLocation.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sIsItNewLocation)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Select Is It New Location")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                } else if (sIsItNewLocation.equalsIgnoreCase("Yes")) {
                    if (TextUtils.isEmpty(sLocationDescription.trim())) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Enter Location Description")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                } else if (sIsItNewLocation.equalsIgnoreCase("No")) {
                    if (TextUtils.isEmpty(sSourceLocalityName.trim()) || sSourceLocalityName.trim().equalsIgnoreCase("Choose")) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Select Source Locality")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }

                if (sHandPumpCategory.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sHandPumpCategory)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Select HandPump Category")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

               /* if (TextUtils.isEmpty(sPipesTubeWell)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter pipes are in the Tube Well")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }*/
            }

        }

        if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
            if (TextUtils.isEmpty(sSourceTypeId) || sSourceType.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Water Source Type")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (sSourceType.equalsIgnoreCase("OTHERS") || sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                    || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(sTown)) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Town")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sWard_no) || sWard_no.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please select Ward No")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                    || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select/Enter Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                /*if (sSelectSource.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please select Source")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sSelectSource.equalsIgnoreCase("Pump House")) {
                    if (TextUtils.isEmpty(sZoneValue) || TextUtils.isEmpty(sBigDiaTubeWellValue)) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Enter Zone and Big Dia Tube Well Value")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;

                    } else {
                        sZone = sZoneValue;
                        sBig_dia_tube_well_Code = sBigDiaTubeWellValue;
                    }
                }*/

            } else if (sSourceType.equals("OTHERS")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (sHandPumpCategory.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sHandPumpCategory)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Select HandPump Category")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                /*if (TextUtils.isEmpty(sPipesTubeWell)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter pipes are in the Tube Well")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }*/
            }
        }

        /*if (TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Capture Sample Bottle Image")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }*/

        if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
            if (sChamberAvailable.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sChamberAvailable)) {
                new AlertDialog.Builder(EditSampleCollection_Activity.this)
                        .setMessage("Please Select any special chamber available or not?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (sChamberAvailable.equalsIgnoreCase("Yes")) {
                if (TextUtils.isEmpty(sWaterLevel)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Enter Static water level")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }
        }

        if (TextUtils.isEmpty(sPinCode)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Enter PinCode")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sPinCode.length() != 6) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please enter valid PinCode")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(sSampleBottleNumber)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Enter Sample Bottle Number")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(mCurrentPhotoPath)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Capture Image")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(sLatitude)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please get Latitude")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(sLongitude)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please get Longitude")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sCollectingSample.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sCollectingSample)) {
            new AlertDialog.Builder(EditSampleCollection_Activity.this)
                    .setMessage("Please Enter Collecting Sample")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")
                || sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (sampleModel.getApp_name().equalsIgnoreCase("OMAS")) {

                if (TextUtils.isEmpty(sResidualChlorineTested)) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Select Residual Chlorine tested or not")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sResidualChlorineTested.equals("Yes")) {
                    if (TextUtils.isEmpty(sResidualChlorineValue)) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Select Residual Chlorine Value")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }

            } else {

                if (TextUtils.isEmpty(sResidualChlorineTested) || sResidualChlorineTested.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("Please Select Residual Chlorine tested or not")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sResidualChlorineTested.equalsIgnoreCase("Yes")) {
                    if (sResidualChlorine.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sResidualChlorine)) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Select Residual Chlorine")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }

                    if (TextUtils.isEmpty(sResidualChlorineValue)) {
                        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                                .setMessage("Please Select Residual Chlorine Value")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }

            }
        }

        if (!TextUtils.isEmpty(sPipesTubeWell)) {
            iTotalDepth = Integer.parseInt(sPipesTubeWell) * 20;
        }

        int iId = CGlobal.getInstance().getPersistentPreference(EditSampleCollection_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            databaseHandler.UpdateSampleCollection(iId, sData, sSourceSiteId, sIsItSpecialDrive, sSpecialDriveId,
                    timeofdatacollection, sTime, sTypeOfLocality, sSourceTypeId, sDistrictId, sBlockId, sPanchayatId, sVillageNameName,
                    sHabitationNameName, sIsItNewLocation, sLocationDescription, sSourceLocalityId, sHandPumpCategory, sPipesTubeWell,
                    sSampleBottleNumber, compressImage(mCurrentPhotoPath), "", sLatitude, sLongitude, sSampleCollectorId,
                    sTownId, sWard_no, sSchemeId, sZone, sBig_dia_tube_well_no, sSelectSource, sHealth_facility_Id, sCollectingSample,
                    sMobileSerialNo, sMobileModelNo, sMobileIMEI, String.valueOf(unixTime), sAccuracy, String.valueOf(iTotalDepth), versionName,
                    sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, sSampleId);
        } else {
            databaseHandler.UpdateSampleCollection(iId, sData, sSourceSiteId, sIsItSpecialDrive, sSpecialDriveId,
                    timeofdatacollection, sTime, sTypeOfLocality, sSourceTypeId, sDistrictId, sBlockId, sPanchayatId, sVillageNameId,
                    sHabitationNameId, sIsItNewLocation, sLocationDescription, sSourceLocalityId, sHandPumpCategory, sPipesTubeWell,
                    sSampleBottleNumber, compressImage(mCurrentPhotoPath), "", sLatitude, sLongitude, sSampleCollectorId,
                    sTownId, sWard_no, sSchemeId, sZone, sBig_dia_tube_well_no, sSelectSource, sHealth_facility_Id, sCollectingSample,
                    sMobileSerialNo, sMobileModelNo, sMobileIMEI, String.valueOf(unixTime), sAccuracy, String.valueOf(iTotalDepth), versionName,
                    sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, sSampleId);
        }*/


        if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, sSourceLocalityName, "",
                            "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "",
                            "", "", "", "",
                            "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();

                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Dug well/Spring/water treatment unit.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription,
                            "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "",
                            "", "", "",
                            "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }

            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {

                /*if (!sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                    if (TextUtils.isEmpty(sBig_dia_tube_well_Code)) {
                        if (!TextUtils.isEmpty(sSchemeCode)) {
                            String spl[] = new String[0];
                            try {
                                spl = sSchemeCode.split("SM/");
                            } catch (Exception e) {
                                Log.e("Sample Error", "");
                            }

                            sBig_dia_tube_well_Code = spl[1] + "_" + sZone + "_" + sBig_dia_tube_well_no;
                        }
                    }
                }*/

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,sSchemeCode,
                            "", sZone, sSelectSource,
                            sIsItNewLocation, sSourceLocalityName, "",
                            "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS,
                            "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Piped water supply")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,sSchemeCode,
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription,
                            "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS,
                            "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }

            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, sSourceLocalityName, "", "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "",
                            "", "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);

                    finish();

                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Dug well/Spring/water treatment unit.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "",
                            "", "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, sSourceLocalityName, "", "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue,
                            sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();

                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Dug well/Spring/water treatment unit.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue,
                            sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, sSourceLocalityName, "", "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue
                            , sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();

                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Dug well/Spring/water treatment unit.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue
                            , sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription, sHandPumpCategory,
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", sConditionOfSource,
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "", "",
                            "", sArsenic_id, "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription, sHandPumpCategory,
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", sConditionOfSource,
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "", "",
                            "", sArsenic_id, sChamberAvailable, sWaterLevel, sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, sBlockCode,
                            sPanchayatCode, sVillageNameName, sHabitationNameName, "",
                            "", sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, sSourceLocalityName, sLocationDescription, sHandPumpCategory,
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", sConditionOfSource,
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "", "",
                            "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }

            }

        } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "",
                            "", "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }

            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme,sSchemeCode,
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue,
                            sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }

            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, "", "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "",
                            "", "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue,
                            sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme,"",
                            "", sZone, sSelectSource,
                            sIsItNewLocation, "", sLocationDescription, "",
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            sBig_dia_tube_well_Code, "", "",
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            sSubSourceType, sSubSchemeName, "",
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue,
                            sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS, "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            } else {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(EditSampleCollection_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.UpdateSampleCollection(iId, sSourceSite, sIsItSpecialDrive,
                            sSpecialDrive, sData, sTime,
                            sTypeOfLocality, sSourceType, "",
                            "", "", "", sTown,
                            sWard_no, sHealth_facility_name, "","",
                            "", "", "",
                            sIsItNewLocation, sSourceLocalityName, sLocationDescription, sHandPumpCategory,
                            sSampleBottleNumber, mCurrentPhotoPath, sLatitude,
                            sLongitude, sAccuracy, sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                            "", "", sConditionOfSource,
                            sVillageNameCode, sHabitationNameCode, sCollectingSample, sMobileModelNo,
                            "", "", "", "",
                            "", "", "", "", sPinCode);
                    Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
        CGlobal.getInstance().getPersistentPreferenceEditor(EditSampleCollection_Activity.this)
                .putBoolean(Constants.PREFS_RESTART_FRAGMENT_EDIT, true).commit();
        mCurrentPhotoPath = null;
        mCurrentPhotoPath_1 = null;

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditSampleCollection_Activity.this)
                .setMessage("Do you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditSampleCollection_Activity.this, UserSelection_Activity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void getDataBaseEditMode() {

        String sDistrict = databaseHandler.getDistrictNameSample(sampleModel.getDistrict_q_7());
        tvDistrict.setText(sDistrict);

        btnNext.setText("SAVE");

        spTypeOfLocality.setEnabled(false);
        spWaterSourceType.setEnabled(false);

        tvDate.setText(sampleModel.getCollection_date_q_4a());

        tietPinCode.setText(sampleModel.getPin_code());

        tvTime.setText(sampleModel.getTime_q_4b());

        tvLatitude.setText(sampleModel.getLatitude_q_18a());
        tvLongitude.setText(sampleModel.getLongitude_q_18b());

        mCurrentPhotoPath = sampleModel.getSource_image_q_17();

        //if (sampleModel.getApp_name().equalsIgnoreCase("Routine")) {
            tietResidualChlorineRoutine.setText(sampleModel.getResidual_chlorine_value());
        //}

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPicture.setImageBitmap(bitmap);

        /*mCurrentPhotoPath_1 = sampleModel.getImg_sample_bottle();

        setPic_1();*/

        getResidualChlorineTested();

        if (!TextUtils.isEmpty(sampleModel.getSource_site_q_1())) {
            for (int i = 0; i < cmaSourceSite.size(); i++) {
                String sourceSite = cmaSourceSite.get(i).getName();
                if (!TextUtils.isEmpty(sourceSite)) {
                    if (sourceSite.trim().equalsIgnoreCase(sampleModel.getSource_site_q_1())) {
                        spSelectSourceSite.setSelection(i);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getSpecial_drive_q_2())) {
            for (int i = 0; i < stringArrayIsItSpecialDrive.size(); i++) {
                String isItSpecialDrive = stringArrayIsItSpecialDrive.get(i);
                if (!TextUtils.isEmpty(isItSpecialDrive)) {
                    if (isItSpecialDrive.equalsIgnoreCase(sampleModel.getSpecial_drive_q_2())) {
                        spIsItSpecialDrive.setSelection(i);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getType_of_locality_q_5())) {
            for (int i = 0; i < stringArrayListLocality.size(); i++) {
                String typeofLocality = stringArrayListLocality.get(i);
                if (!TextUtils.isEmpty(typeofLocality)) {
                    if (typeofLocality.equalsIgnoreCase(sampleModel.getType_of_locality_q_5())) {
                        spTypeOfLocality.setSelection(i);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getSamplecollectortype())) {
            for (int i = 0; i < stringArrayCollectingSample.size(); i++) {
                String samplecollectortype = stringArrayCollectingSample.get(i);
                if (!TextUtils.isEmpty(samplecollectortype)) {
                    if (samplecollectortype.equalsIgnoreCase(sampleModel.getSamplecollectortype())) {
                        spCollectingSample.setSelection(i);
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getNew_location_q_14())) {
            tietLocationDescription.setText(sampleModel.getNew_location_q_14());
            llLocationDescription.setVisibility(View.VISIBLE);
        } else {
            llLocationDescription.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getHow_many_pipes_q_21())) {
            tietPipesTubeWell.setText(sampleModel.getHow_many_pipes_q_21());
            llPipesTubeWell.setVisibility(View.VISIBLE);
        } else {
            llPipesTubeWell.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getSample_bottle_number_q_16())) {
            tietSampleBottleNumber.setText(sampleModel.getSample_bottle_number_q_16());
        }

        /*sData = sampleModel.getCreatedDate();
        sSourceSiteId = sampleModel.getSourceSiteId();
        sIsItSpecialDrive = sampleModel.getIsitaspecialdrive();
        sSpecialDriveId = sampleModel.getSpecialdriveId();
        sTypeOfLocality = sampleModel.getTypeofLocality();
        sSourceTypeId = sampleModel.getSourceTypeId();
        sBlockId = sampleModel.getBlockID();
        sPanchayatId = sampleModel.getPanchayatID();
        sVillageNameId = sampleModel.getVillageID();
        sHabitationNameId = sampleModel.getHabitationID();
        sIsItNewLocation = sampleModel.getIsThisanewlocation();
        sLocationDescription = sampleModel.getNewLocationDescription();
        sSourceLocalityId = sampleModel.getExistingLocationID();
        sHandPumpCategory = sampleModel.getHandPumpCategory();
        sPipesTubeWell = sampleModel.getHowmanyPipesareinthetubewell();
        sSampleBottleNumber = sampleModel.getSampleBottleNumber();
        sTownId = sampleModel.getSampleBottleNumber();
        sSampleBottleNumber = sampleModel.getSampleBottleNumber();
        sSampleBottleNumber = sampleModel.getSampleBottleNumber();
        sSampleBottleNumber = sampleModel.getSampleBottleNumber();*/

    }
}
