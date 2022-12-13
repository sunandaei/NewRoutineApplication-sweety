package com.sunanda.newroutine.application.newactivity;

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
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.AnganwadiData_Adapter;
import com.sunanda.newroutine.application.adapter.BigDiaTubeWellNumber_Adapter;
import com.sunanda.newroutine.application.adapter.Block_Adapter;
import com.sunanda.newroutine.application.adapter.DescriptionLocation_Adapter;
import com.sunanda.newroutine.application.adapter.HabitationName_Adapter;
import com.sunanda.newroutine.application.adapter.Master_Adapter;
import com.sunanda.newroutine.application.adapter.Panchayat_Adapter;
import com.sunanda.newroutine.application.adapter.Scheme_Adapter;
import com.sunanda.newroutine.application.adapter.SchoolData_Adapter;
import com.sunanda.newroutine.application.adapter.Town_Adapter;
import com.sunanda.newroutine.application.adapter.VillageName_Adapter;
import com.sunanda.newroutine.application.adapter.Zone_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.UserSelection_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditSchoolOMASNewSample_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;

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
        setContentView(R.layout.visit_school_activity);
        checkPlayServices();
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(EditSchoolOMASNewSample_Activity.this)
                .addConnectionCallbacks(EditSchoolOMASNewSample_Activity.this)
                .addOnConnectionFailedListener(EditSchoolOMASNewSample_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        databaseHandler = new DatabaseHandler(EditSchoolOMASNewSample_Activity.this);

        init();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(EditSchoolOMASNewSample_Activity.this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(EditSchoolOMASNewSample_Activity.this, result,
                        101).show();
            }

            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(EditSchoolOMASNewSample_Activity.this, mGoogleApiClient);

        btnNext.setText("SAVE");

        spSelectSourceSite.setEnabled(false);
        spTypeOfLocality.setEnabled(false);
        spWaterSourceType.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    TextView tvDate, tvTime, tvDistrict, tvLatitude, tvLongitude, tvSampleId, tvAddress, tvListShow;

    Spinner spSelectSourceSite, spIsItSpecialDrive, spNameSpecialDrive, spTypeOfLocality, spWaterSourceType,
            spBlock, spPanchayat, spVillageName, spHabitation, spNameTown, /*spWardNumber,*/
            spHandPumpCategory,
            spDescriptionLocation, spSelectScheme, spSelectSource, spZoneNumber, spBigDiaTubeWellNumber,
            spCollectingSample, spSchool, spAnganwadi, spAnganwadiPremisses, spResidualChlorine, spResidualChlorineTested,
            spSharedSource, spSharedWith, spListShow, spSubSourceType, spConditionOfSource;

    TextInputEditText tietZoneNumber, tietBigDiaTubeWellNumber, tietLocationDescription, tietPipesTubeWell, tietSampleBottleNumber,
            tietResidualChlorine, tietOthersType, tietSchemeName, tietSubSourceTypeOther, tietPinCode;

    ImageView ivPicture, ivPictureSampleBottle;

    EditText etOthersSchool, etOthersAnganwadi;

    Button btnTakeImage, btnDeviceLocation, btnNext;

    LinearLayout llNameSpecialDrive, llNameTown, /*llWardNumber,*/
            llBlock, llPanchayat, llVillageName,
            llHabitation, llDescriptionLocation, llHandPumpCategory, llSelectScheme,
            llSelectSource, llZoneNumber, llBigDiaTubeWellNumber, llLocationDescription, llPipesTubeWell,
            llSchool, llAnganwadi, llAnganwadiPremisses, llResidualChlorine, llResidualChlorineTested,
            llSharedWith, llOthersType, llListShow, llSubSourceType, llSchemeName, llSubSourceTypeOther, llConditionOfSource;

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
    //ArrayList<CommonModel> cmaWord = new ArrayList<>();
    ArrayList<CommonModel> cmaScheme;
    ArrayList<CommonModel> cmaZone = new ArrayList<>();
    ArrayList<CommonModel> cmaBigDiaTubeWellNumber = new ArrayList<>();
    ArrayList<CommonModel> cmaSchoolData = new ArrayList<>();
    ArrayList<CommonModel> cmaAnganwadiData = new ArrayList<>();

    ArrayList<String> stringArrayIsItSpecialDrive = new ArrayList<>();
    ArrayList<String> stringArrayListLocality = new ArrayList<>();
    ArrayList<String> stringArrayCollectingSample = new ArrayList<>();
    ArrayList<String> stringArrayListResidualChlorine = new ArrayList<>();
    ArrayList<String> stringArrayListResidualChlorineTested = new ArrayList<>();
    ArrayList<String> stringArrayListSharedSource;
    ArrayList<String> stringArrayListConditionOfSource = new ArrayList<>();

    private static final int CAMERA_REQUEST = 1101;
    private String mCurrentPhotoPath;
    SampleModel sampleModel;
    ProgressDialog progressdialog;

    private void init() {
        progressdialog = new ProgressDialog(EditSchoolOMASNewSample_Activity.this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        progressdialog.show();

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvSampleId = findViewById(R.id.tvSampleId);
        tvAddress = findViewById(R.id.tvAddress);
        tvListShow = findViewById(R.id.tvListShow);

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
        //spWardNumber = findViewById(R.id.spWardNumber);
        spHandPumpCategory = findViewById(R.id.spHandPumpCategory);
        spDescriptionLocation = findViewById(R.id.spDescriptionLocation);
        //spIsItNewLocation = findViewById(R.id.spIsItNewLocation);
        spSelectScheme = findViewById(R.id.spSelectScheme);
        spSelectSource = findViewById(R.id.spSelectSource);
        spZoneNumber = findViewById(R.id.spZoneNumber);
        spBigDiaTubeWellNumber = findViewById(R.id.spBigDiaTubeWellNumber);
        spCollectingSample = findViewById(R.id.spCollectingSample);
        spSchool = findViewById(R.id.spSchool);
        spAnganwadi = findViewById(R.id.spAnganwadi);
        spAnganwadiPremisses = findViewById(R.id.spAnganwadiPremisses);
        spResidualChlorine = findViewById(R.id.spResidualChlorine);
        spResidualChlorineTested = findViewById(R.id.spResidualChlorineTested);
        spSharedSource = findViewById(R.id.spSharedSource);
        spSharedWith = findViewById(R.id.spSharedWith);
        spListShow = findViewById(R.id.spListShow);
        spSubSourceType = findViewById(R.id.spSubSourceType);
        spConditionOfSource = findViewById(R.id.spConditionOfSource);

        tietZoneNumber = findViewById(R.id.tietZoneNumber);
        tietBigDiaTubeWellNumber = findViewById(R.id.tietBigDiaTubeWellNumber);
        tietLocationDescription = findViewById(R.id.tietLocationDescription);
        tietPipesTubeWell = findViewById(R.id.tietPipesTubeWell);
        tietSampleBottleNumber = findViewById(R.id.tietSampleBottleNumber);
        tietResidualChlorine = findViewById(R.id.tietResidualChlorine);
        tietOthersType = findViewById(R.id.tietOthersType);
        tietSchemeName = findViewById(R.id.tietSchemeName);
        tietSubSourceTypeOther = findViewById(R.id.tietSubSourceTypeOther);
        tietPinCode = findViewById(R.id.tietPinCode);

        etOthersSchool = findViewById(R.id.etOthersSchool);
        etOthersAnganwadi = findViewById(R.id.etOthersAnganwadi);

        ivPicture = findViewById(R.id.ivPicture);
        ivPictureSampleBottle = findViewById(R.id.ivPictureSampleBottle);

        btnTakeImage = findViewById(R.id.btnTakeImage);
        btnDeviceLocation = findViewById(R.id.btnDeviceLocation);
        btnNext = findViewById(R.id.btnNext);

        llNameSpecialDrive = findViewById(R.id.llNameSpecialDrive);
        llNameTown = findViewById(R.id.llNameTown);
        //llWardNumber = findViewById(R.id.llWardNumber);
        llBlock = findViewById(R.id.llBlock);
        llPanchayat = findViewById(R.id.llPanchayat);
        llVillageName = findViewById(R.id.llVillageName);
        llHabitation = findViewById(R.id.llHabitation);
        //llIsItNewLocation = findViewById(R.id.llIsItNewLocation);
        llDescriptionLocation = findViewById(R.id.llDescriptionLocation);
        llHandPumpCategory = findViewById(R.id.llHandPumpCategory);
        llSelectScheme = findViewById(R.id.llSelectScheme);
        llSelectSource = findViewById(R.id.llSelectSource);
        llZoneNumber = findViewById(R.id.llZoneNumber);
        llBigDiaTubeWellNumber = findViewById(R.id.llBigDiaTubeWellNumber);
        llLocationDescription = findViewById(R.id.llLocationDescription);
        llPipesTubeWell = findViewById(R.id.llPipesTubeWell);
        llSchool = findViewById(R.id.llSchool);
        llAnganwadi = findViewById(R.id.llAnganwadi);
        llAnganwadiPremisses = findViewById(R.id.llAnganwadiPremisses);
        llResidualChlorine = findViewById(R.id.llResidualChlorine);
        llResidualChlorineTested = findViewById(R.id.llResidualChlorineTested);
        llSharedWith = findViewById(R.id.llSharedWith);
        llOthersType = findViewById(R.id.llOthersType);
        llListShow = findViewById(R.id.llListShow);
        llSubSourceType = findViewById(R.id.llSubSourceType);
        llSchemeName = findViewById(R.id.llSchemeName);
        llSubSourceTypeOther = findViewById(R.id.llSubSourceTypeOther);
        llConditionOfSource = findViewById(R.id.llConditionOfSource);

        tvAddress.setVisibility(View.GONE);
        llNameSpecialDrive.setVisibility(View.GONE);
        llNameTown.setVisibility(View.GONE);
        //llWardNumber.setVisibility(View.GONE);
        llBlock.setVisibility(View.GONE);
        llPanchayat.setVisibility(View.GONE);
        llVillageName.setVisibility(View.GONE);
        llHabitation.setVisibility(View.GONE);
        //llIsItNewLocation.setVisibility(View.GONE);
        llDescriptionLocation.setVisibility(View.GONE);

        llHandPumpCategory.setVisibility(View.GONE);
        llSelectScheme.setVisibility(View.GONE);
        llSelectSource.setVisibility(View.GONE);
        llZoneNumber.setVisibility(View.GONE);
        llBigDiaTubeWellNumber.setVisibility(View.GONE);
        llLocationDescription.setVisibility(View.GONE);
        llPipesTubeWell.setVisibility(View.GONE);
        llSchool.setVisibility(View.GONE);
        llAnganwadi.setVisibility(View.GONE);
        llAnganwadiPremisses.setVisibility(View.GONE);
        llResidualChlorine.setVisibility(View.GONE);
        llResidualChlorineTested.setVisibility(View.GONE);
        llOthersType.setVisibility(View.GONE);
        llSharedWith.setVisibility(View.GONE);
        llListShow.setVisibility(View.GONE);
        llSubSourceType.setVisibility(View.GONE);
        llSchemeName.setVisibility(View.GONE);
        llSubSourceTypeOther.setVisibility(View.GONE);
        llConditionOfSource.setVisibility(View.GONE);
        etOthersSchool.setVisibility(View.GONE);
        etOthersAnganwadi.setVisibility(View.GONE);

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
                        Uri photoURI = FileProvider.getUriForFile(EditSchoolOMASNewSample_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        btnDeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation == null) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                new DatePickerDialog(EditSchoolOMASNewSample_Activity.this, date, myCalendar
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
                new TimePickerDialog(EditSchoolOMASNewSample_Activity.this, time_listener, hour,
                        minute, false).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this);
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
        int iId = CGlobal.getInstance().getPersistentPreference(EditSchoolOMASNewSample_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        sampleModel = databaseHandler.getSchoolAppDataCollectionEdit(iId);

        getSelectSourceSite();

        getIsItSpecialDrive();

        getNameSpecialDrive();

        getTypeOfLocality();

        getTown();

        getSourceType();

        CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sampleModel.getSource_type_q_6()).commit();

        getCollectingSample();

        getPanchayat(sampleModel.getBlock_q_8());

        getVillageName(sampleModel.getPanchayat_q_9());

        getHabitationName(sampleModel.getVillage_code());

        getSource();

        getHandPumpCategory();

        getSharedSource();

       /* getDescriptionLocation(sampleModel.getBlockID_q_8(), sampleModel.getPanchayatID_q_9(), sampleModel.getVillageID_q_10(),
                sampleModel.getHabitationID_q_11());*/

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataBaseEditMode();
                progressdialog.dismiss();
            }
        }, 5000);

        tietResidualChlorine.setFilters(new InputFilter[]{new DigitsKeyListener(
                Boolean.FALSE, Boolean.TRUE) {
            int beforeDecimal = 1, afterDecimal = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String etText = tietResidualChlorine.getText().toString();
                if (etText.isEmpty()) {
                    return null;
                }
                String temp = tietResidualChlorine.getText() + source.toString();
                if (temp.equalsIgnoreCase(".")) {
                    return "0.";
                } else if (temp.toString().indexOf(".") == -1) {
                    // no decimal point placed yet
                    if (temp.length() > beforeDecimal) {
                        return "";
                    }
                } else {
                    int dotPosition;
                    int cursorPositon = tietResidualChlorine.getSelectionStart();
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

    String sSourceSite = "", sIsItSpecialDrive = "", sSpecialDrive = "", sTypeOfLocality = "", sSourceTypeId = "",
            sSourceType = "", sBlockName = "", sBlockCode = "", sPanchayatName = "", sPanchayatCode = "", sVillageNameName = "",
            sVillageNameCode = "", sHabitationNameName = "", sHabitationNameCode = "", sSourceLocalityName = "", sTown = "",
            sTownCode = "", sHandPumpCategory = "", sScheme = "", sSchemeCode = "", sSelectSource = "", sZone = "",
            sBig_dia_tube_well_no = "", sBig_dia_tube_well_Code = "", sCollectingSample = "", sSchoolName = "", udise_code = "",
            sAnganwadiName = "", sAnganwadiCode = "", sSectorCode = "", sSectorName = "", sAnganwadiPremisses = "", sResidualChlorine = "",
            sResidualChlorineTested = "", sResidualChlorineTestedValue = "", sSharedSource = "", sSharedWith = "",
            sSchool_Anganwadi_Name_SharedWith = "", sSubSourceType = "", sSubSourceTypeId = "", sSubSchemeName = "", sConditionOfSource = "";

    private void getSelectSourceSite() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSourceSite = databaseHandler.getSourceSite("S");
        cmaSourceSite.add(0, commonModel);
        Master_Adapter sourceSiteMaster_adapter = new Master_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSourceSite);
        spSelectSourceSite.setAdapter(sourceSiteMaster_adapter);

        spSelectSourceSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceSite = cmaSourceSite.get(position).getName();
                if (sSourceSite.equalsIgnoreCase("Choose")) {
                    sSourceSite = "";
                    return;
                }

                CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                        .putString(Constants.PREFS_SOURCE_SITE_SCHOOL, sSourceSite).commit();

                stringArrayListLocality = new ArrayList<>();
                if (sSourceSite.equalsIgnoreCase("SCHOOL")) {
                    stringArrayListLocality.add("Choose");
                    stringArrayListLocality.add("RURAL");

                    llSchool.setVisibility(View.VISIBLE);
                    llAnganwadi.setVisibility(View.GONE);

                } else if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                    stringArrayListLocality.add("Choose");
                    stringArrayListLocality.add("RURAL");
                    stringArrayListLocality.add("URBAN");

                    llSchool.setVisibility(View.GONE);
                    llAnganwadi.setVisibility(View.VISIBLE);

                }

                if (sSourceSite.equalsIgnoreCase("SCHOOL")) {
                    getSchoolData(sampleModel.getBlock_q_8(), sampleModel.getPanchayat_q_9());
                }

                if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                    if (sampleModel.getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
                        getAwsDataSourceRural(sampleModel.getBlock_q_8(), sampleModel.getPanchayat_q_9());
                    } else {
                        getAwsDataSourceUrban(sampleModel.getTown_q_7a());
                    }
                }

                getTypeOfLocality();

                getSharedSource();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSource_site_q_1())) {
            for (int i = 0; i < cmaSourceSite.size(); i++) {
                String sourceSiteName = cmaSourceSite.get(i).getName();
                if (!TextUtils.isEmpty(sourceSiteName)) {
                    if (sourceSiteName.equalsIgnoreCase(sampleModel.getSource_site_q_1())) {
                        spSelectSourceSite.setSelection(i);

                        stringArrayListLocality = new ArrayList<>();
                        if (cmaSourceSite.get(i).getName().equalsIgnoreCase("SCHOOL")) {
                            sSourceSite = "SCHOOL";
                            stringArrayListLocality.add("Choose");
                            stringArrayListLocality.add("RURAL");
                            llSchool.setVisibility(View.VISIBLE);
                            llAnganwadi.setVisibility(View.GONE);
                        } else if (cmaSourceSite.get(i).getName().equalsIgnoreCase("ANGANWADI")) {
                            sSourceSite = "ANGANWADI";
                            stringArrayListLocality.add("Choose");
                            stringArrayListLocality.add("RURAL");
                            stringArrayListLocality.add("URBAN");
                            llSchool.setVisibility(View.GONE);
                            llAnganwadi.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    private void getIsItSpecialDrive() {
        stringArrayIsItSpecialDrive.add("Choose");
        stringArrayIsItSpecialDrive.add("Yes");
        stringArrayIsItSpecialDrive.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayIsItSpecialDrive);
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
                        return;
                    case "Yes":
                        llNameSpecialDrive.setVisibility(View.VISIBLE);
                        getNameSpecialDrive();
                        break;
                    case "No":
                        llNameSpecialDrive.setVisibility(View.GONE);
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
        Master_Adapter specialDriveMaster_adapter = new Master_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSpecialDrive);
        spNameSpecialDrive.setAdapter(specialDriveMaster_adapter);

        spNameSpecialDrive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSpecialDrive = cmaSpecialDrive.get(position).getName();
                if (sSpecialDrive.equalsIgnoreCase("Choose")) {
                    sSpecialDrive = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSpecial_drive_name_q_3())) {
            for (int i = 0; i < cmaSpecialDrive.size(); i++) {
                String specialdriveName = cmaSpecialDrive.get(i).getName();
                if (!TextUtils.isEmpty(specialdriveName)) {
                    if (specialdriveName.equalsIgnoreCase(sampleModel.getSpecial_drive_name_q_3())) {
                        spNameSpecialDrive.setSelection(i);
                        sSpecialDrive = cmaSpecialDrive.get(i).getName();
                    }
                }
            }
        }
    }

    private void getTypeOfLocality() {

        stringArrayListLocality = new ArrayList<>();
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
                        //llWardNumber.setVisibility(View.GONE);
                        llBlock.setVisibility(View.GONE);
                        llPanchayat.setVisibility(View.GONE);
                        llVillageName.setVisibility(View.GONE);
                        llHabitation.setVisibility(View.GONE);
                        return;
                    case "RURAL":
                        llNameTown.setVisibility(View.GONE);
                        //llWardNumber.setVisibility(View.GONE);
                        llBlock.setVisibility(View.VISIBLE);
                        llPanchayat.setVisibility(View.VISIBLE);
                        llVillageName.setVisibility(View.VISIBLE);
                        llHabitation.setVisibility(View.VISIBLE);
                        spBlock.setVisibility(View.VISIBLE);
                        spPanchayat.setVisibility(View.GONE);
                        spVillageName.setVisibility(View.GONE);
                        spHabitation.setVisibility(View.GONE);
                        getBlock();
                        break;
                    case "URBAN":
                        llNameTown.setVisibility(View.VISIBLE);
                        //llWardNumber.setVisibility(View.VISIBLE);
                        llBlock.setVisibility(View.GONE);
                        llPanchayat.setVisibility(View.GONE);
                        llVillageName.setVisibility(View.GONE);
                        llHabitation.setVisibility(View.GONE);
                        spNameTown.setVisibility(View.VISIBLE);
                        //spWardNumber.setVisibility(View.GONE);
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
        cmaSourceType = databaseHandler.getSourceType();
        cmaSourceType.add(0, commonModel);
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSourceType);
        spWaterSourceType.setAdapter(sourceTypeMaster_adapter);

        spWaterSourceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceType = cmaSourceType.get(position).getName();
                if (sSourceType.equalsIgnoreCase("Choose")) {
                    sSourceType = "";
                    return;
                }
                sSourceTypeId = cmaSourceType.get(position).getId();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sSourceType).commit();

                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        //llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                        } else {
                            llDescriptionLocation.setVisibility(View.GONE);

                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.VISIBLE);
                        llZoneNumber.setVisibility(View.VISIBLE);
                        llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                        //llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                        } else {
                            llDescriptionLocation.setVisibility(View.GONE);

                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llSubSourceType.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llSubSourceType.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llResidualChlorineTested.setVisibility(View.GONE);
                        llResidualChlorine.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
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
                        //llIsItNewLocation.setVisibility(View.VISIBLE);
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        llPipesTubeWell.setVisibility(View.VISIBLE);

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                        } else {
                            llDescriptionLocation.setVisibility(View.GONE);

                            llLocationDescription.setVisibility(View.VISIBLE);
                        }

                        llResidualChlorineTested.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                    }
                } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        //llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.VISIBLE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        //llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        llSubSourceType.setVisibility(View.GONE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        llSubSourceType.setVisibility(View.GONE);
                        getResidualChlorineTested();
                        llSubSourceType.setVisibility(View.VISIBLE);
                        llSchemeName.setVisibility(View.GONE);
                        getSubSourceType(sSourceTypeId);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        sHandPumpCategory = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llResidualChlorineTested.setVisibility(View.GONE);
                        llResidualChlorine.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
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
                        //llIsItNewLocation.setVisibility(View.VISIBLE);
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        llPipesTubeWell.setVisibility(View.VISIBLE);
                        llDescriptionLocation.setVisibility(View.GONE);

                        llLocationDescription.setVisibility(View.VISIBLE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSource_type_q_6())) {
            for (int i = 0; i < cmaSourceType.size(); i++) {
                String sourceTypeName = cmaSourceType.get(i).getName();
                if (!TextUtils.isEmpty(sourceTypeName)) {
                    if (sourceTypeName.equalsIgnoreCase(sampleModel.getSource_type_q_6())) {
                        spWaterSourceType.setSelection(i);
                        String sourceType = cmaSourceType.get(i).getName();
                        if (sampleModel.getType_of_locality_q_5().equalsIgnoreCase("RURAL")) {
                            if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                                llDescriptionLocation.setVisibility(View.VISIBLE);

                                llLocationDescription.setVisibility(View.GONE);
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
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSubSourceType);
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
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSubSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
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
                            llSubSourceType.setVisibility(View.VISIBLE);
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
        Block_Adapter block_adapter = new Block_Adapter(EditSchoolOMASNewSample_Activity.this, cmaBlock);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getBlock_q_8())) {
            for (int i = 0; i < cmaBlock.size(); i++) {
                String blockid = cmaBlock.get(i).getBlockcode();
                if (!TextUtils.isEmpty(blockid)) {
                    if (blockid.equalsIgnoreCase(sampleModel.getBlock_q_8())) {
                        getSelectScheme(cmaBlock.get(i).getBlockname(), sampleModel.getType_of_locality_q_5());
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
                    }
                }
            }
            llBlock.setVisibility(View.VISIBLE);
        } else {
            llBlock.setVisibility(View.GONE);
        }
    }

    private void getPanchayat(final String sBlockCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setPanchayatname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaPanchayat = databaseHandler.getPanchayatRoster(sBlockCode, sSpecialDrive);
        } else {
            cmaPanchayat = databaseHandler.getPanchayat(sBlockCode);
        }

        cmaPanchayat.add(0, commonModel);
        Panchayat_Adapter panchayat_adapter = new Panchayat_Adapter(EditSchoolOMASNewSample_Activity.this, cmaPanchayat);
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
                getVillageName(sPanchayatCode);

                if (sSourceSite.equalsIgnoreCase("SCHOOL")) {
                    getSchoolData(sBlockCode, sPanchayatCode);
                }

                if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                    getAwsDataSourceRural(sBlockCode, sPanchayatCode);
                }

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

    private void getVillageName(String sPanchayatcode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setVillagename("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaVillageName = databaseHandler.getVillageNameRoster(sBlockCode, sPanchayatcode, sSpecialDrive);
        } else {
            cmaVillageName = databaseHandler.getVillageName(sBlockCode, sPanchayatcode);
        }

        cmaVillageName.add(0, commonModel);
        VillageName_Adapter villageName_adapter = new VillageName_Adapter(EditSchoolOMASNewSample_Activity.this, cmaVillageName);
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
                    getHabitationName(sVillageNameName);
                } else {
                    getHabitationName(sVillageNameCode);
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
                    String villageName = cmaVillageName.get(i).getVillagename();
                    if (!TextUtils.isEmpty(villageName)) {
                        if (villageName.equalsIgnoreCase(sampleModel.getVillage_name_q_10())) {
                            spVillageName.setSelection(i);
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

    private void getHabitationName(final String sVillageNameId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setHabitationname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaHabitationName = databaseHandler.getHabitationNameRoster(sBlockCode, sPanchayatCode, sVillageNameId, sSpecialDrive);
        } else {
            cmaHabitationName = databaseHandler.getHabitationName(sBlockCode, sPanchayatCode, sVillageNameId);
        }

        cmaHabitationName.add(0, commonModel);
        HabitationName_Adapter habitationName_adapter = new HabitationName_Adapter(EditSchoolOMASNewSample_Activity.this, cmaHabitationName);
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
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                            getDescriptionLocation(sBlockCode, sPanchayatCode, sVillageNameId, sHabitationNameName);
                        }
                        llConditionOfSource.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName, sTypeOfLocality);
                        getSource();
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                            getDescriptionLocation(sBlockCode, sPanchayatCode, sVillageNameId, sHabitationNameName);
                        }
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName, sTypeOfLocality);
                        getSource();
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName, sTypeOfLocality);
                        getSource();
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        llSelectScheme.setVisibility(View.VISIBLE);
                        getSelectScheme(sBlockName, sTypeOfLocality);
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else {
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            llDescriptionLocation.setVisibility(View.VISIBLE);

                            llLocationDescription.setVisibility(View.GONE);
                            getDescriptionLocation(sBlockCode, sPanchayatCode, sVillageNameId, sHabitationNameName);
                        }
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                        getHandPumpCategory();

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
                        }
                    }
                } else {
                    String habitation = cmaHabitationName.get(i).getHabitationname();
                    if (!TextUtils.isEmpty(habitation)) {
                        if (habitation.equalsIgnoreCase(sampleModel.getHabitation_q_11())) {
                            spHabitation.setSelection(i);
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

    private void getSchoolData(String sBlockId, String sPanchayatId) {
        cmaSchoolData = databaseHandler.getSchoolDataSheet(sBlockId, sPanchayatId);
        CommonModel commonModel = new CommonModel();
        commonModel.setSchool_name("Choose");
        cmaSchoolData.add(0, commonModel);

        CommonModel commonModel1 = new CommonModel();
        commonModel1.setSchool_name("OTHER");
        cmaSchoolData.add(cmaSchoolData.size(), commonModel1);

        SchoolData_Adapter schoolData_Adapter = new SchoolData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSchoolData);
        spSchool.setAdapter(schoolData_Adapter);

        spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSchoolName = cmaSchoolData.get(position).getSchool_name();
                if (sSchoolName.equalsIgnoreCase("Choose")) {
                    sSchoolName = "";
                    udise_code = "";
                    etOthersSchool.setVisibility(View.GONE);
                    etOthersSchool.setText("");
                    return;
                } else if (sSchoolName.equals("OTHER")) {
                    etOthersSchool.setVisibility(View.VISIBLE);
                    udise_code = sSchoolName;
                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_UDISE_CODE_SCHOOL, udise_code).commit();
                } else {
                    etOthersSchool.setVisibility(View.GONE);
                    etOthersSchool.setText("");
                    udise_code = cmaSchoolData.get(position).getUdise_code();
                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_UDISE_CODE_SCHOOL, udise_code).commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSchooludisecode_q_12())) {
            for (int i = 0; i < cmaSchoolData.size(); i++) {
                String sUdiseCode = cmaSchoolData.get(i).getUdise_code();
                if (sampleModel.getSchooludisecode_q_12().equalsIgnoreCase("OTHER")) {
                    spSchool.setSelection(i);
                    etOthersSchool.setVisibility(View.VISIBLE);
                    etOthersSchool.setText(sampleModel.getOtherSchoolName());
                } else {
                    if (!TextUtils.isEmpty(sUdiseCode)) {
                        if (sUdiseCode.equalsIgnoreCase(sampleModel.getSchooludisecode_q_12())) {
                            spSchool.setSelection(i);
                        }
                    }
                }
            }
            llSchool.setVisibility(View.VISIBLE);
            spSchool.setVisibility(View.VISIBLE);
        } else {
            llSchool.setVisibility(View.GONE);
        }
    }

    private void getAwsDataSourceRural(String sBlockId, String panchayetId) {
        cmaAnganwadiData = databaseHandler.getAwsDataSourceMasterRural(sBlockId, panchayetId);

        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData.add(0, commonModel);

        CommonModel commonModel1 = new CommonModel();
        commonModel1.setAwcname("OTHER");
        cmaAnganwadiData.add(cmaAnganwadiData.size(), commonModel1);

        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaAnganwadiData);
        spAnganwadi.setAdapter(anganwadiData_Adapter);

        spAnganwadi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAnganwadiName = cmaAnganwadiData.get(position).getAwcname();
                if (sAnganwadiName.equalsIgnoreCase("Choose")) {
                    sAnganwadiName = "";
                    sAnganwadiCode = "";
                    sSectorCode = "";
                    sSectorName = "";
                    etOthersAnganwadi.setVisibility(View.GONE);
                    etOthersAnganwadi.setText("");
                    return;
                } else if (sAnganwadiName.equals("OTHER")) {
                    etOthersAnganwadi.setVisibility(View.VISIBLE);
                    sAnganwadiCode = sAnganwadiName;
                    sSectorCode = "";
                    sSectorName = "";
                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_ANGANWADI_CODE_SCHOOL, sAnganwadiCode).commit();
                } else {
                    etOthersAnganwadi.setText("");
                    etOthersAnganwadi.setVisibility(View.GONE);
                    sAnganwadiCode = cmaAnganwadiData.get(position).getAwccode();
                    sSectorCode = cmaAnganwadiData.get(position).getSectorcode();
                    sSectorName = cmaAnganwadiData.get(position).getSectorname();

                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_ANGANWADI_CODE_SCHOOL, sAnganwadiCode).commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getAnganwadicode_q_12c())) {
            for (int i = 0; i < cmaAnganwadiData.size(); i++) {
                String sAnganwadicode = cmaAnganwadiData.get(i).getAwccode();
                if (sampleModel.getAnganwadicode_q_12c().equalsIgnoreCase("OTHER")) {
                    spAnganwadi.setSelection(i);
                    etOthersAnganwadi.setVisibility(View.VISIBLE);
                    etOthersAnganwadi.setText(sampleModel.getOtherAnganwadiName());
                } else {
                    if (!TextUtils.isEmpty(sAnganwadicode)) {
                        if (sAnganwadicode.equalsIgnoreCase(sampleModel.getAnganwadicode_q_12c())) {
                            spAnganwadi.setSelection(i);
                        }
                    }
                }
            }
            llAnganwadi.setVisibility(View.VISIBLE);
            spAnganwadi.setVisibility(View.VISIBLE);
        } else {
            llAnganwadi.setVisibility(View.GONE);
        }
    }

    private void getAwsDataSourceUrban(String townCode) {
        cmaAnganwadiData = databaseHandler.getAwsDataSourceMasterUrban(townCode);

        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData.add(0, commonModel);

        CommonModel commonModel1 = new CommonModel();
        commonModel1.setAwcname("OTHER");
        cmaAnganwadiData.add(cmaAnganwadiData.size(), commonModel1);

        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaAnganwadiData);
        spAnganwadi.setAdapter(anganwadiData_Adapter);

        spAnganwadi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAnganwadiName = cmaAnganwadiData.get(position).getAwcname();
                if (sAnganwadiName.equalsIgnoreCase("Choose")) {
                    sAnganwadiName = "";
                    sAnganwadiCode = "";
                    sSectorCode = "";
                    sSectorName = "";
                    etOthersAnganwadi.setVisibility(View.GONE);
                    etOthersAnganwadi.setText("");
                    return;
                }else if (sAnganwadiName.equals("OTHER")) {
                    etOthersAnganwadi.setVisibility(View.VISIBLE);
                    sAnganwadiCode = sAnganwadiName;
                    sSectorCode = "";
                    sSectorName = "";
                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_ANGANWADI_CODE_SCHOOL, sAnganwadiCode).commit();
                } else {
                    etOthersAnganwadi.setText("");
                    etOthersAnganwadi.setVisibility(View.GONE);
                    sAnganwadiCode = cmaAnganwadiData.get(position).getAwccode();
                    sSectorCode = cmaAnganwadiData.get(position).getSectorcode();
                    sSectorName = cmaAnganwadiData.get(position).getSectorname();

                    CGlobal.getInstance().getPersistentPreferenceEditor(EditSchoolOMASNewSample_Activity.this)
                            .putString(Constants.PREFS_ANGANWADI_CODE_SCHOOL, sAnganwadiCode).commit();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getAnganwadicode_q_12c())) {
            for (int i = 0; i < cmaAnganwadiData.size(); i++) {
                String sAnganwadicode = cmaAnganwadiData.get(i).getAwccode();
                if (!TextUtils.isEmpty(sAnganwadicode)) {
                    if (sAnganwadicode.equalsIgnoreCase(sampleModel.getAnganwadicode_q_12c())) {
                        spAnganwadi.setSelection(i);
                    }
                }
            }
            llAnganwadi.setVisibility(View.VISIBLE);
            spAnganwadi.setVisibility(View.VISIBLE);
        } else {
            llAnganwadi.setVisibility(View.GONE);
        }
    }

    private void getDescriptionLocation(String sCityId, String sPanchayatId, String sVillageId, String SHabitationId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setSourcelocalityname("Choose");
        cmaDescriptionLocation = databaseHandler.getRoster(sCityId, sPanchayatId, sVillageId, SHabitationId, sSpecialDrive);

        cmaDescriptionLocation.add(0, commonModel);
        DescriptionLocation_Adapter descriptionLocation_adapter = new DescriptionLocation_Adapter(EditSchoolOMASNewSample_Activity.this, cmaDescriptionLocation);
        spDescriptionLocation.setAdapter(descriptionLocation_adapter);

        spDescriptionLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sLocationDescription = cmaDescriptionLocation.get(position).getSourcelocalityname();
                if (sLocationDescription.equalsIgnoreCase("Choose")) {
                    sLocationDescription = "";
                    return;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getNew_location_q_14())) {
            for (int i = 0; i < cmaDescriptionLocation.size(); i++) {
                String sourcelocalityname = cmaDescriptionLocation.get(i).getSourcelocalityname();
                if (!TextUtils.isEmpty(sourcelocalityname)) {
                    if (sourcelocalityname.equalsIgnoreCase(sampleModel.getNew_location_q_14())) {
                        spDescriptionLocation.setSelection(i);
                    }
                }
            }

            llDescriptionLocation.setVisibility(View.VISIBLE);
            spDescriptionLocation.setVisibility(View.VISIBLE);
        } else {

            llDescriptionLocation.setVisibility(View.GONE);
        }
    }

    private void getTown() {
        CommonModel commonModel = new CommonModel();
        commonModel.setTown_name("Choose");
        cmaTown = databaseHandler.getTown();
        cmaTown.add(0, commonModel);
        Town_Adapter town_adapter = new Town_Adapter(EditSchoolOMASNewSample_Activity.this, cmaTown);
        spNameTown.setAdapter(town_adapter);

        spNameTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTown = cmaTown.get(position).getTown_name();
                if (sTown.equalsIgnoreCase("Choose")) {
                    sTown = "";
                    sTownCode = "";
                    //spWardNumber.setVisibility(View.GONE);
                    return;
                }
                sTownCode = cmaTown.get(position).getTown_code();

                //spWardNumber.setVisibility(View.VISIBLE);
                //getWardName(sTown);

                if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        llHandPumpCategory.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        //getSelectScheme(sBlockName, sTypeOfLocality);
                        getSource();
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        //getSelectScheme("");
                        getSource();
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        //getSelectScheme("");
                        getSource();
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        getSubSourceType(sSourceTypeId);
                        llConditionOfSource.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                    } else {
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                        //getIsItNewLocation();
                        llHandPumpCategory.setVisibility(View.VISIBLE);
                        getHandPumpCategory();
                    }
                }

                if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                    getAwsDataSourceUrban(sTownCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getTown_q_7a())) {
            for (int i = 0; i < cmaTown.size(); i++) {
                String townid = cmaTown.get(i).getTown_code();
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

    private void getHandPumpCategory() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("PUBLIC PHE");
        stringArrayList.add("PUBLIC PANCHAYAT");
        stringArrayList.add("PUBLIC NGO");
        stringArrayList.add("PUBLIC ISGP");
        stringArrayList.add("PUBLIC OTHERS");
        stringArrayList.add("PRIVATE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
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
            for (int i = 0; i < stringArrayList.size(); i++) {
                String handPumpCategory = stringArrayList.get(i);
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

    private void getSelectScheme(String sBlock, String typeOfLocality) {
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
            cmaScheme = databaseHandler.getSchemeSchool(sBlock, typeOfLocality);
            CommonModel commonModel = new CommonModel();
            commonModel.setPwssname("Choose");
            cmaScheme.add(0, commonModel);
        }
        Scheme_Adapter scheme_adapter = new Scheme_Adapter(EditSchoolOMASNewSample_Activity.this, cmaScheme);
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


        if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")
                || sSourceType.equalsIgnoreCase("OTHERS")) {
            if (!TextUtils.isEmpty(sampleModel.getSub_scheme_name())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String subschemename = cmaScheme.get(i).getPwssname();
                    if (!TextUtils.isEmpty(subschemename)) {
                        if (sampleModel.getSub_scheme_name().equalsIgnoreCase("NOT KNOWN")) {
                            spSelectScheme.setSelection(1);
                            llSchemeName.setVisibility(View.GONE);
                        } else {
                            llSchemeName.setVisibility(View.VISIBLE);
                            spSelectScheme.setSelection(2);
                            tietSchemeName.setText(sampleModel.getSub_scheme_name());
                        }
                    }
                }
                llSelectScheme.setVisibility(View.VISIBLE);
                spSelectScheme.setVisibility(View.VISIBLE);
            } else {
                llSelectScheme.setVisibility(View.GONE);
            }
        } else {
            if (!TextUtils.isEmpty(sampleModel.getSchemeid_q_13a())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String schemeid = cmaScheme.get(i).getSmcode();
                    if (!TextUtils.isEmpty(schemeid)) {
                        if (schemeid.equalsIgnoreCase(sampleModel.getSchemeid_q_13a())) {
                            spSelectScheme.setSelection(i);
                            sSchemeCode = cmaScheme.get(i).getSmcode();
                        }
                    }
                }
                llSelectScheme.setVisibility(View.VISIBLE);
                spSelectScheme.setVisibility(View.VISIBLE);
            } else {
                llSelectScheme.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(sampleModel.getSchemeid_q_13a())) {
            for (int i = 0; i < cmaScheme.size(); i++) {
                String schemeid = cmaScheme.get(i).getSmcode();
                if (!TextUtils.isEmpty(schemeid)) {
                    if (schemeid.equalsIgnoreCase(sampleModel.getSchemeid_q_13a())) {
                        getZoneNumber(cmaScheme.get(i).getSmcode());
                        if (!TextUtils.isEmpty(sampleModel.getZonenumber_q_13c())) {
                            getBigDiaTubeWellNumber(cmaScheme.get(i).getSmcode(), sampleModel.getZonenumber_q_13c());
                        }
                    }
                }
            }
        }
    }

    private void getSource() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("DISTRIBUTION SYSTEM");
        stringArrayList.add("PUMP HOUSE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
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
                if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                    llZoneNumber.setVisibility(View.GONE);
                    llBigDiaTubeWellNumber.setVisibility(View.GONE);
                } else {
                    llZoneNumber.setVisibility(View.VISIBLE);
                    llBigDiaTubeWellNumber.setVisibility(View.GONE);
                    getZoneNumber(sSchemeCode);
                }

                if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                    if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                        llAnganwadiPremisses.setVisibility(View.VISIBLE);
                        getAnganwadiPremisses();
                    } else {
                        llAnganwadiPremisses.setVisibility(View.GONE);
                    }
                } else {
                    llAnganwadiPremisses.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSourcename_q_13d())) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                String sourceName = stringArrayList.get(i);
                if (!TextUtils.isEmpty(sourceName)) {
                    if (sourceName.equalsIgnoreCase(sampleModel.getSourcename_q_13d())) {
                        spSelectSource.setSelection(i);
                    }
                }
            }
            llSelectSource.setVisibility(View.VISIBLE);
            spSelectSource.setVisibility(View.VISIBLE);

        } else {
            llSelectSource.setVisibility(View.GONE);
        }
    }

    private void getAnganwadiPremisses() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("WITHIN PREMISSES");
        stringArrayList.add("OUTSIDE PREMISSES");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnganwadiPremisses.setAdapter(adapter);

        spAnganwadiPremisses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAnganwadiPremisses = spAnganwadiPremisses.getSelectedItem().toString();
                if (sAnganwadiPremisses.equalsIgnoreCase("Choose")) {
                    sAnganwadiPremisses = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getStandpostsituated_q_13e())) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                String standpostsituated = stringArrayList.get(i);
                if (!TextUtils.isEmpty(standpostsituated)) {
                    if (standpostsituated.equalsIgnoreCase(sampleModel.getStandpostsituated_q_13e())) {
                        spAnganwadiPremisses.setSelection(i);
                    }
                }
            }
            llAnganwadiPremisses.setVisibility(View.VISIBLE);
            spAnganwadiPremisses.setVisibility(View.VISIBLE);
        } else {
            llAnganwadiPremisses.setVisibility(View.GONE);
        }
    }

    private void getZoneNumber(final String sSchemeCode) {
        if (TextUtils.isEmpty(sSchemeCode)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            Zone_Adapter zone_adapter = new Zone_Adapter(EditSchoolOMASNewSample_Activity.this, cmaZone);
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
            tietZoneNumber.setVisibility(View.VISIBLE);
            spZoneNumber.setVisibility(View.GONE);
            llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            spBigDiaTubeWellNumber.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getZonenumber_q_13c())) {
            for (int i = 0; i < cmaZone.size(); i++) {
                String zone = cmaZone.get(i).getZone();
                if (!TextUtils.isEmpty(zone)) {
                    if (zone.equalsIgnoreCase(sampleModel.getZonenumber_q_13c())) {
                        spZoneNumber.setSelection(i);
                    }
                    llZoneNumber.setVisibility(View.VISIBLE);
                    spZoneNumber.setVisibility(View.VISIBLE);
                } else {
                    tietZoneNumber.setText(sampleModel.getZonenumber_q_13c());
                    tietBigDiaTubeWellNumber.setText(sampleModel.getBig_dia_tub_well_q_20());
                    tietZoneNumber.setVisibility(View.VISIBLE);
                    spZoneNumber.setVisibility(View.GONE);
                    llBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                    spBigDiaTubeWellNumber.setVisibility(View.GONE);
                }
            }
        } else {
            llZoneNumber.setVisibility(View.GONE);
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
                BigDiaTubeWellNumber_Adapter bigDiaTubeWellNumber_adapter = new BigDiaTubeWellNumber_Adapter(EditSchoolOMASNewSample_Activity.this, cmaBigDiaTubeWellNumber);
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
            } else {
                tietBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
                spBigDiaTubeWellNumber.setVisibility(View.GONE);
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
                llConditionOfSource.setVisibility(View.VISIBLE);
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
                        llResidualChlorine.setVisibility(View.GONE);
                        sResidualChlorineTestedValue = "";
                        return;
                    case "Yes":
                        llResidualChlorine.setVisibility(View.VISIBLE);
                        sResidualChlorineTestedValue = "1";
                        getResidualChlorine();
                        break;
                    case "No":
                        llResidualChlorine.setVisibility(View.GONE);
                        sResidualChlorineTestedValue = "0";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSource_type_q_6())) {
            for (int i = 0; i < cmaSourceType.size(); i++) {
                String sourceTypeName = cmaSourceType.get(i).getName();
                if (!TextUtils.isEmpty(sourceTypeName)) {
                    if (sourceTypeName.equalsIgnoreCase(sampleModel.getSource_type_q_6())) {
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
                                                llResidualChlorine.setVisibility(View.VISIBLE);
                                                getResidualChlorine();
                                            } else {
                                                llResidualChlorine.setVisibility(View.GONE);
                                            }

                                        } else if (sampleModel.getResidual_chlorine_tested().equalsIgnoreCase("0") && rsidualChlorineTested.equalsIgnoreCase("No")) {
                                            spResidualChlorineTested.setSelection(j);
                                            llResidualChlorineTested.setVisibility(View.VISIBLE);
                                            llResidualChlorine.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        } else {
                            llResidualChlorineTested.setVisibility(View.GONE);
                            llResidualChlorine.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    private void getResidualChlorine() {
        stringArrayListResidualChlorine.add("Choose");
        stringArrayListResidualChlorine.add("VISUAL COMPARATOMETRIC METHOD");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListResidualChlorine);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spResidualChlorine.setAdapter(adapter);

        spResidualChlorine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sResidualChlorine = spResidualChlorine.getSelectedItem().toString();
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
                        spResidualChlorine.setSelection(k);
                    }
                }
            }
        }
    }

    private void getSharedSource() {
        stringArrayListSharedSource = new ArrayList<>();
        stringArrayListSharedSource.add("Choose");
        stringArrayListSharedSource.add("Yes");
        stringArrayListSharedSource.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListSharedSource);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSharedSource.setAdapter(adapter_IsItSpecialDrive);

        spSharedSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSharedSource = spSharedSource.getSelectedItem().toString();
                if (sSharedSource.equalsIgnoreCase("Choose")) {
                    sSharedSource = "";
                    llOthersType.setVisibility(View.GONE);
                    llSharedWith.setVisibility(View.GONE);
                    llListShow.setVisibility(View.GONE);
                    sSharedWith = "";
                    sSchool_Anganwadi_Name_SharedWith = "";
                    return;
                } else if (sSharedSource.equalsIgnoreCase("Yes")) {
                    llOthersType.setVisibility(View.GONE);
                    llSharedWith.setVisibility(View.VISIBLE);
                    llListShow.setVisibility(View.GONE);
                    getSharedWith();
                } else {
                    sSharedWith = "";
                    sSchool_Anganwadi_Name_SharedWith = "";
                    llOthersType.setVisibility(View.GONE);
                    llSharedWith.setVisibility(View.GONE);
                    llListShow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    ArrayList<String> stringArrayListSharedWith;

    private void getSharedWith() {
        stringArrayListSharedWith = new ArrayList<>();
        stringArrayListSharedWith.add("Choose");
        if (sSourceSite.equalsIgnoreCase("SCHOOL")) {
            stringArrayListSharedWith.add("ANGANWADI");
            stringArrayListSharedWith.add("OTHERS");
        } else if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
            stringArrayListSharedWith.add("SCHOOL");
            stringArrayListSharedWith.add("OTHERS");
        }

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayListSharedWith);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSharedWith.setAdapter(adapter_IsItSpecialDrive);

        spSharedWith.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSharedWith = spSharedWith.getSelectedItem().toString();
                switch (sSharedWith) {
                    case "Choose":
                        sSharedWith = "";
                        llOthersType.setVisibility(View.GONE);
                        tietOthersType.setVisibility(View.GONE);
                        llListShow.setVisibility(View.GONE);
                        tietOthersType.setText("");
                        sSchool_Anganwadi_Name_SharedWith = "";
                        return;

                    case "ANGANWADI":
                        tvListShow.setText("Select Anganwadi Name");

                        tietOthersType.setText("");

                        llOthersType.setVisibility(View.GONE);
                        tietOthersType.setVisibility(View.GONE);
                        llListShow.setVisibility(View.VISIBLE);

                        if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                            getAwsDataSourceRural_SharedWith(sBlockCode, sPanchayatCode);
                        } else {
                            getAwsDataSourceUrban_SharedWith(sTownCode);
                        }

                        break;
                    case "SCHOOL":
                        tvListShow.setText("Select School Name");

                        tietOthersType.setText("");
                        llOthersType.setVisibility(View.GONE);
                        tietOthersType.setVisibility(View.GONE);
                        llListShow.setVisibility(View.VISIBLE);

                        getSchoolData_SharedWith(sBlockCode, sPanchayatCode);

                        break;
                    case "OTHERS":
                        llOthersType.setVisibility(View.VISIBLE);
                        tietOthersType.setVisibility(View.VISIBLE);
                        llListShow.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getShared_with())) {
            for (int i = 0; i < stringArrayListSharedWith.size(); i++) {
                String sharedWith = stringArrayListSharedWith.get(i);
                if (!TextUtils.isEmpty(sharedWith)) {
                    if (sharedWith.equalsIgnoreCase(sampleModel.getShared_with())) {
                        spSharedWith.setSelection(i);

                        if (sampleModel.getShared_with().equalsIgnoreCase("OTHERS")) {
                            llOthersType.setVisibility(View.VISIBLE);
                            tietOthersType.setVisibility(View.VISIBLE);
                            tietOthersType.setText(sampleModel.getSchool_aws_shared_with());
                        } else if (sampleModel.getShared_with().equalsIgnoreCase("ANGANWADI")) {
                            tvListShow.setText("Select Anganwadi Name");

                            tietOthersType.setText("");

                            llOthersType.setVisibility(View.GONE);
                            tietOthersType.setVisibility(View.GONE);
                            llListShow.setVisibility(View.VISIBLE);

                            if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                                getAwsDataSourceRural_SharedWith(sBlockCode, sPanchayatCode);
                            } else {
                                getAwsDataSourceUrban_SharedWith(sTownCode);
                            }
                        } else if (sampleModel.getShared_with().equalsIgnoreCase("SCHOOL")) {
                            tvListShow.setText("Select School Name");

                            tietOthersType.setText("");
                            llOthersType.setVisibility(View.GONE);
                            tietOthersType.setVisibility(View.GONE);
                            llListShow.setVisibility(View.VISIBLE);

                            getSchoolData_SharedWith(sBlockCode, sPanchayatCode);
                        }
                    }
                }
            }
        }
    }

    ArrayList<CommonModel> cmaSchoolData_SharedWith = new ArrayList<>();
    ArrayList<CommonModel> cmaAnganwadiData_SharedWith = new ArrayList<>();

    private void getSchoolData_SharedWith(String sBlockId, String sPanchayatId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setSchool_name("Choose");
        cmaSchoolData_SharedWith = databaseHandler.getSchoolDataSheet(sBlockId, sPanchayatId);
        cmaSchoolData_SharedWith.add(0, commonModel);
        SchoolData_Adapter schoolData_Adapter = new SchoolData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaSchoolData_SharedWith);
        spListShow.setAdapter(schoolData_Adapter);

        spListShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSchool_Anganwadi_Name_SharedWith = cmaSchoolData_SharedWith.get(position).getSchool_name();
                if (sSchool_Anganwadi_Name_SharedWith.equalsIgnoreCase("Choose")) {
                    sSchool_Anganwadi_Name_SharedWith = "";
                    return;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSchool_aws_shared_with())) {
            for (int i = 0; i < cmaSchoolData_SharedWith.size(); i++) {
                String sharedWith = cmaSchoolData_SharedWith.get(i).getSchool_name();
                if (!TextUtils.isEmpty(sharedWith)) {
                    if (sharedWith.equalsIgnoreCase(sampleModel.getSchool_aws_shared_with())) {
                        spListShow.setSelection(i);
                    }
                }
            }
        }
    }

    private void getAwsDataSourceRural_SharedWith(String sBlockId, String panchyetId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData_SharedWith = databaseHandler.getAwsDataSourceMasterRural(sBlockId, panchyetId);
        cmaAnganwadiData_SharedWith.add(0, commonModel);
        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaAnganwadiData_SharedWith);
        spListShow.setAdapter(anganwadiData_Adapter);

        spListShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSchool_Anganwadi_Name_SharedWith = cmaAnganwadiData_SharedWith.get(position).getAwcname();
                if (sSchool_Anganwadi_Name_SharedWith.equalsIgnoreCase("Choose")) {
                    sSchool_Anganwadi_Name_SharedWith = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSchool_aws_shared_with())) {
            for (int i = 0; i < cmaAnganwadiData_SharedWith.size(); i++) {
                String sharedWith = cmaAnganwadiData_SharedWith.get(i).getAwcname();
                if (!TextUtils.isEmpty(sharedWith)) {
                    if (sharedWith.equalsIgnoreCase(sampleModel.getSchool_aws_shared_with())) {
                        spListShow.setSelection(i);
                    }
                }
            }
        }
    }

    private void getAwsDataSourceUrban_SharedWith(String townCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData_SharedWith = databaseHandler.getAwsDataSourceMasterUrban(townCode);
        cmaAnganwadiData_SharedWith.add(0, commonModel);
        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(EditSchoolOMASNewSample_Activity.this, cmaAnganwadiData_SharedWith);
        spListShow.setAdapter(anganwadiData_Adapter);

        spListShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSchool_Anganwadi_Name_SharedWith = cmaAnganwadiData_SharedWith.get(position).getAwcname();
                if (sSchool_Anganwadi_Name_SharedWith.equalsIgnoreCase("Choose")) {
                    sSchool_Anganwadi_Name_SharedWith = "";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(sampleModel.getSchool_aws_shared_with())) {
            for (int i = 0; i < cmaAnganwadiData_SharedWith.size(); i++) {
                String sharedWith = cmaAnganwadiData_SharedWith.get(i).getAwcname();
                if (!TextUtils.isEmpty(sharedWith)) {
                    if (sharedWith.equalsIgnoreCase(sampleModel.getSchool_aws_shared_with())) {
                        spListShow.setSelection(i);
                    }
                }
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(EditSchoolOMASNewSample_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EditSchoolOMASNewSample_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, EditSchoolOMASNewSample_Activity.this);
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto();
        }
    }

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
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

    private File setUpPhotoFile() {
        File f = null;
        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

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

    String sData = "", sTime = "", sDistrict = "", sLatitude = "", sLongitude = "", sLocationDescription = "",
            sPipesTubeWell = "", sSampleBottleNumber = "", sZoneValue = "", sBigDiaTubeWellValue = "", sAccuracy = "",
            versionName = "", sResidualChlorineValue = "", sSampleId = "", sMobileIMEI = "", sMobileSerialNo = "",
            sMobileModelNo = "", sPinCode = "", sOthersSchool = "", sOthersAnganwadi = "";
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
        if (TextUtils.isEmpty(sLocationDescription.trim())) {
            sLocationDescription = tietLocationDescription.getText().toString();
        }
        sPipesTubeWell = tietPipesTubeWell.getText().toString();
        sSampleBottleNumber = tietSampleBottleNumber.getText().toString();
        sResidualChlorineValue = tietResidualChlorine.getText().toString();
        sSampleId = tvSampleId.getText().toString();
        sSampleId = tvSampleId.getText().toString();
        sPinCode = tietPinCode.getText().toString();

        sOthersSchool = etOthersSchool.getText().toString();
        sOthersAnganwadi = etOthersAnganwadi.getText().toString();

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

        sLocationDescription = sLocationDescription.replace("'", "");

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

        if (sSharedWith.equalsIgnoreCase("OTHERS")) {
            sSchool_Anganwadi_Name_SharedWith = tietOthersType.getText().toString();
        }

        if (TextUtils.isEmpty(sSourceSite) || sSourceSite.equalsIgnoreCase("Choose")) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please Select Source Site")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sIsItSpecialDrive.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sIsItSpecialDrive)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

            if (TextUtils.isEmpty(sSourceType) || sSourceType.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

            if (TextUtils.isEmpty(sBlockCode) || sBlockName.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please select Block")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sPanchayatCode) || sPanchayatName.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sVillageNameName) || sVillageNameName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationNameName) || sHabitationNameName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sVillageNameName) || sVillageNameName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationNameName) || sHabitationNameName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

            if (sSourceSite.equalsIgnoreCase("SCHOOL")) {

                if (TextUtils.isEmpty(sSchoolName) || sSchoolName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select School Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(udise_code)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select School Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (udise_code.equalsIgnoreCase("OTHER")) {
                    if (TextUtils.isEmpty(etOthersSchool.getText().toString().trim())) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Enter School Name")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }

            } else if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {

                if (TextUtils.isEmpty(sAnganwadiName) || sAnganwadiName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select Anganwadi Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sAnganwadiCode)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select Anganwadi Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (!sAnganwadiName.equalsIgnoreCase("OTHER")) {
                    if (TextUtils.isEmpty(sSectorCode)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Anganwadi Name")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }

                    if (TextUtils.isEmpty(sSectorName)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Anganwadi Name")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }
                if (sAnganwadiName.equalsIgnoreCase("OTHER")) {
                    if (TextUtils.isEmpty(etOthersAnganwadi.getText().toString().trim())) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Enter Anganwadi Name")
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

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please select Source")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                    if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                        if (sAnganwadiPremisses.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sAnganwadiPremisses)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please Select Stand Post Situated?")
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

                if (sSelectSource.equalsIgnoreCase("PUMP HOUSE")) {
                    if (!TextUtils.isEmpty(cmaZone.get(0).getZone())) {
                        if (sZone.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sZone)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

            } else if (sSourceType.equals("OTHERS")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                /*if (sIsItNewLocation.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select Is It New Location")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                } else if (sIsItNewLocation.equalsIgnoreCase("Yes")) {*/
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                /*} else if (sIsItNewLocation.equalsIgnoreCase("No")) {
                    if (TextUtils.isEmpty(sSourceLocalityId)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Source Locality")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }*/

                if (sHandPumpCategory.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sHandPumpCategory)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            if (TextUtils.isEmpty(sSourceType) || sSourceType.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

            if (TextUtils.isEmpty(sTownCode)) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please select Town")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            /*if (TextUtils.isEmpty(sWard_no)) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please select Ward No")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }*/

            if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                /*if (TextUtils.isEmpty(sSchemeId)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please select Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }*/

                if (sSelectSource.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sSelectSource)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please select Source")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (sSelectSource.equalsIgnoreCase("DISTRIBUTION SYSTEM")) {
                    if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {
                        if (sAnganwadiPremisses.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sAnganwadiPremisses)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please Select Stand Post Situated?")
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

                /*if (sSelectSource.equalsIgnoreCase("PUMP HOUSE")) {
                    if (!TextUtils.isEmpty(cmaZone.get(0).getZone())) {
                        if (sZone.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sZone)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please select Zone")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return;
                        }
                    } else {
                        if (TextUtils.isEmpty(sZoneValue) || TextUtils.isEmpty(sBigDiaTubeWellValue)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    if (sBig_dia_tube_well_Code.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sBig_dia_tube_well_Code)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please select Big Dia Tube Well Number")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }

                }*/
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

            } else if (sSourceType.equals("OTHERS")) {
                if (TextUtils.isEmpty(sLocationDescription)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                /*if (sIsItNewLocation.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Select Is It New Location")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                } else if (sIsItNewLocation.equalsIgnoreCase("Yes")) {*/
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                /*} else if (sIsItNewLocation.equalsIgnoreCase("No")) {
                    if (TextUtils.isEmpty(sSourceLocalityId)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Source Locality")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }*/

                if (sSourceSite.equalsIgnoreCase("ANGANWADI")) {

                    if (TextUtils.isEmpty(sAnganwadiName) || sAnganwadiName.equalsIgnoreCase("Choose")) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Anganwadi Name")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }

                    if (TextUtils.isEmpty(sAnganwadiCode)) {
                        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                .setMessage("Please Select Anganwadi Name")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                    if (!sAnganwadiName.equalsIgnoreCase("OTHER")) {
                        if (TextUtils.isEmpty(sSectorCode)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please Select Anganwadi Name")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return;
                        }

                        if (TextUtils.isEmpty(sSectorName)) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please Select Anganwadi Name")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                            return;
                        }
                    }
                    if (sAnganwadiName.equalsIgnoreCase("OTHER")) {
                        if (TextUtils.isEmpty(etOthersAnganwadi.getText().toString().trim())) {
                            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                                    .setMessage("Please Enter Anganwadi Name")
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

                if (sHandPumpCategory.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sHandPumpCategory)) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

        if (TextUtils.isEmpty(sSharedSource) || sSharedSource.equalsIgnoreCase("Choose")) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please Select Shared Source")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sSharedSource.equalsIgnoreCase("Yes")) {
            if (TextUtils.isEmpty(sSharedWith) || sSharedWith.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please Select Shared Source with")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sSchool_Anganwadi_Name_SharedWith)
                    || sSchool_Anganwadi_Name_SharedWith.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please Select/Enter Shared Source Name")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        }

        /*if (TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please Capture Sample Bottle Image")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }*/

        if (TextUtils.isEmpty(mCurrentPhotoPath)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please get Longitude")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(sPinCode)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please Enter Sample Bottle Number")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sCollectingSample.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sCollectingSample)) {
            new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                    .setMessage("Please Select Collecting Sample")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sSourceType.equalsIgnoreCase("TUBE WELL MARK II") || sSourceType.equalsIgnoreCase("TUBE WELL ORDINARY")) {
            if (sConditionOfSource.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sConditionOfSource)) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                        .setMessage("Please Select Condition of the Source")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        }

        if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY") || sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (TextUtils.isEmpty(sResidualChlorineTested) || sResidualChlorineTested.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
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

        if (!TextUtils.isEmpty(sPipesTubeWell)) {
            iTotalDepth = Integer.parseInt(sPipesTubeWell) * 20;
        }

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int iId = CGlobal.getInstance().getPersistentPreference(EditSchoolOMASNewSample_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);

        if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", "", "",
                            "", "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            "", "", mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                } else {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", "", "",
                            "", "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            "", "", mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
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

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", sSchemeCode, "",
                            sZone, sSelectSource, sAnganwadiPremisses,
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                            "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                            sResidualChlorine, sResidualChlorineValue, sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                } else {
                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", sSchemeCode, "",
                            sZone, sSelectSource, sAnganwadiPremisses,
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                            "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                            sResidualChlorine, sResidualChlorineValue, sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }

            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, sBlockCode, sPanchayatCode,
                        sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, "",
                        "", "", "",
                        "", "", "",
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, "",
                        "", "", mCurrentPhotoPath, "",
                        "", "", sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, "",
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, sBlockCode, sPanchayatCode,
                        sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, "",
                        "", "", "",
                        "", "", sAnganwadiPremisses,
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                        "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                        sResidualChlorine, sResidualChlorineValue, sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, sSubSchemeName,
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, sBlockCode, sPanchayatCode,
                        sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, "",
                        "", "", "",
                        "", "", sAnganwadiPremisses,
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                        "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                        sResidualChlorine, sResidualChlorineValue, sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, sSubSchemeName,
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", "", "",
                            "", "", "",
                            sLocationDescription, sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            sPipesTubeWell, String.valueOf(iTotalDepth), mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, sConditionOfSource, sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                } else {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, sBlockCode, sPanchayatCode,
                            sVillageNameName, sHabitationNameName, udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, "",
                            "", "", "",
                            "", "", "",
                            sLocationDescription, sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            sPipesTubeWell, String.valueOf(iTotalDepth), mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, sConditionOfSource, sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }

            }

        } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, "", "",
                            "", "", udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, sTownCode,
                            "", "", "",
                            "", "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            "", "", mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, "", "",
                            "", "", udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, sTownCode,
                            "", sSchemeCode, "",
                            sZone, sSelectSource, sAnganwadiPremisses,
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                            "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                            sResidualChlorine, sResidualChlorineValue, sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, "", "",
                        "", "", udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, sTownCode,
                        "", "", "",
                        "", "", "",
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, "",
                        "", "", mCurrentPhotoPath, "",
                        "", "", sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, "",
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, "", "",
                        "", "", udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, sTownCode,
                        "", "", "",
                        "", "", sAnganwadiPremisses,
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                        "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                        sResidualChlorine, sResidualChlorineValue, sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, sSubSchemeName,
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

                databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                        sData, sTime, sTypeOfLocality,
                        sSourceType, "", "",
                        "", "", udise_code, sAnganwadiName,
                        sAnganwadiCode, sSectorCode, sTownCode,
                        "", "", "",
                        "", "", sAnganwadiPremisses,
                        sLocationDescription, "", sSampleBottleNumber,
                        mCurrentPhotoPath, sLatitude, sLongitude,
                        sAccuracy, sCollectingSample, sBig_dia_tube_well_Code,
                        "", "", mCurrentPhotoPath, sResidualChlorineTestedValue,
                        sResidualChlorine, sResidualChlorineValue, sSharedSource,
                        sSharedWith, sSchool_Anganwadi_Name_SharedWith, sSubSourceType, sSubSchemeName,
                        sVillageNameCode, sHabitationNameCode, "", sPinCode, sOthersSchool, sOthersAnganwadi);
                Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            } else {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.updateSchoolAppDataCollection(iId, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality,
                            sSourceType, "", "",
                            "", "", udise_code, sAnganwadiName,
                            sAnganwadiCode, sSectorCode, sTownCode,
                            "", "", "",
                            "", "", "",
                            sLocationDescription, sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            sAccuracy, sCollectingSample, "",
                            sPipesTubeWell, String.valueOf(iTotalDepth), mCurrentPhotoPath, "",
                            "", "", sSharedSource,
                            sSharedWith, sSchool_Anganwadi_Name_SharedWith, "", "",
                            sVillageNameCode, sHabitationNameCode, sConditionOfSource, sPinCode, sOthersSchool, sOthersAnganwadi);
                    Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            }
        }
        mCurrentPhotoPath = null;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditSchoolOMASNewSample_Activity.this)
                .setMessage("Filled data will lost permanently")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(EditSchoolOMASNewSample_Activity.this, UserSelection_Activity.class);
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

        tvDate.setText(sampleModel.getCollection_date_q_4a());

        tvTime.setText(sampleModel.getTime_q_4b());

        tietPinCode.setText(sampleModel.getPin_code());

        tvLatitude.setText(sampleModel.getLatitude_q_18a());
        tvLongitude.setText(sampleModel.getLongitude_q_18b());

        mCurrentPhotoPath = sampleModel.getSource_image_q_17();

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

        tietResidualChlorine.setText(sampleModel.getResidual_chlorine_value());

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

        if (!TextUtils.isEmpty(sampleModel.getWho_collecting_sample_q_19())) {
            for (int i = 0; i < stringArrayCollectingSample.size(); i++) {
                String samplecollectortype = stringArrayCollectingSample.get(i);
                if (!TextUtils.isEmpty(samplecollectortype)) {
                    if (samplecollectortype.equalsIgnoreCase(sampleModel.getWho_collecting_sample_q_19())) {
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

        if (!TextUtils.isEmpty(sampleModel.getShared_source())) {
            for (int i = 0; i < stringArrayListSharedSource.size(); i++) {
                String sharedSource = stringArrayListSharedSource.get(i);
                if (!TextUtils.isEmpty(sharedSource)) {
                    if (sharedSource.equalsIgnoreCase(sampleModel.getShared_source())) {
                        spSharedSource.setSelection(i);

                        if (sampleModel.getShared_source().equalsIgnoreCase("Yes")) {
                            llSharedWith.setVisibility(View.VISIBLE);
                            getSharedWith();
                        }
                    }
                }
            }
        }

    }
}
