package com.sunanda.newroutine.application.newactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
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
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.sunanda.newroutine.application.R;
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
import com.sunanda.newroutine.application.ui.DashBoard_Facilitator_Activity;
import com.sunanda.newroutine.application.ui.DataUploadRoutine_Activity;
import com.sunanda.newroutine.application.ui.OldSampleCollection_Activity;
import com.sunanda.newroutine.application.ui.SanitarySurveyQuesAns_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.LocationAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RoutineNewSample_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    DatabaseHandler databaseHandler;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    String versionName = "";
    String sTaskId = "", sTaskIdx = "";
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_sample_fragment);
        checkPlayServices();

        mGoogleApiClient = new GoogleApiClient.Builder(RoutineNewSample_Activity.this)
                .addConnectionCallbacks(RoutineNewSample_Activity.this)
                .addOnConnectionFailedListener(RoutineNewSample_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        databaseHandler = new DatabaseHandler(RoutineNewSample_Activity.this);

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvAddress = findViewById(R.id.tvAddress);

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
        spResidualChlorine = findViewById(R.id.spResidualChlorine);
        spResidualChlorineTested = findViewById(R.id.spResidualChlorineTested);
        spSubSourceType = findViewById(R.id.spSubSourceType);
        spConditionOfSource = findViewById(R.id.spConditionOfSource);
        spChamberAvailable = findViewById(R.id.spChamberAvailable);

        tietZoneNumber = findViewById(R.id.tietZoneNumber);
        tietBigDiaTubeWellNumber = findViewById(R.id.tietBigDiaTubeWellNumber);
        tietLocationDescription = findViewById(R.id.tietLocationDescription);
        tietPipesTubeWell = findViewById(R.id.tietPipesTubeWell);
        tietSampleBottleNumber = findViewById(R.id.tietSampleBottleNumber);
        tietResidualChlorine = findViewById(R.id.tietResidualChlorine);
        tietSchemeName = findViewById(R.id.tietSchemeName);
        tietSubSourceTypeOther = findViewById(R.id.tietSubSourceTypeOther);
        tietWaterLevel = findViewById(R.id.tietWaterLevel);
        tietPinCode = findViewById(R.id.tietPinCode);

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
        llResidualChlorine = findViewById(R.id.llResidualChlorine);
        llResidualChlorineTested = findViewById(R.id.llResidualChlorineTested);
        llSubSourceType = findViewById(R.id.llSubSourceType);
        llSchemeName = findViewById(R.id.llSchemeName);
        llSubSourceTypeOther = findViewById(R.id.llSubSourceTypeOther);
        llConditionOfSource = findViewById(R.id.llConditionOfSource);
        llChamberAvailable = findViewById(R.id.llChamberAvailable);
        llWaterLevel = findViewById(R.id.llWaterLevel);

        init();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(RoutineNewSample_Activity.this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(RoutineNewSample_Activity.this, result,
                        101).show();
            }

            return false;
        }

        return true;
    }

    TextView tvDate, tvTime, tvDistrict, tvLatitude, tvLongitude, tvAddress;

    Spinner spSelectSourceSite, spIsItSpecialDrive, spNameSpecialDrive, spTypeOfLocality, spWaterSourceType,
            spBlock, spPanchayat, spVillageName, spHabitation, spNameTown, spWardNumber, spHandPumpCategory,
            spDescriptionLocation, spHealthFacility, spIsItNewLocation, spSelectScheme, spSelectSource,
            spZoneNumber, spBigDiaTubeWellNumber, spCollectingSample, spResidualChlorine, spResidualChlorineTested,
            spSubSourceType, spConditionOfSource, spChamberAvailable;

    TextInputEditText tietZoneNumber, tietBigDiaTubeWellNumber, tietLocationDescription, tietPipesTubeWell, tietSampleBottleNumber,
            tietResidualChlorine, tietSchemeName, tietSubSourceTypeOther, tietWaterLevel, tietPinCode;

    ImageView ivPicture, ivPictureSampleBottle;

    Button btnTakeImage, btnDeviceLocation, btnNext, btnTakeImageSampleBottle;

    LinearLayout llNameSpecialDrive, llNameTown, llWardNumber, llBlock, llPanchayat, llVillageName, llHabitation,
            llIsItNewLocation, llHealthFacility, llDescriptionLocation, llHandPumpCategory, llSelectScheme, llSelectSource,
            llZoneNumber, llBigDiaTubeWellNumber, llLocationDescription, llPipesTubeWell, llResidualChlorine, llResidualChlorineTested,
            llSubSourceType, llSchemeName, llSubSourceTypeOther, llConditionOfSource, llChamberAvailable, llWaterLevel;

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
    ArrayList<CommonModel> cmaScheme;
    ArrayList<CommonModel> cmaZone = new ArrayList<>();
    ArrayList<CommonModel> cmaBigDiaTubeWellNumber = new ArrayList<>();
    ArrayList<SampleModel> sampleModelsDataCollection;

    private static final int CAMERA_REQUEST = 11;
    private static final int CAMERA_REQUEST_1 = 12;
    private String mCurrentPhotoPath, mCurrentPhotoPath_1;

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(RoutineNewSample_Activity.this, mGoogleApiClient);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        String formattedTime = df1.format(c.getTime());
        tvDate.setText(formattedDate);
        tvTime.setText(formattedTime);
        String sDistrict = databaseHandler.getDistrict();
        tvDistrict.setText(sDistrict);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        startLocationUpdates();
    }

    private void init() {

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
        llResidualChlorine.setVisibility(View.GONE);
        llResidualChlorineTested.setVisibility(View.GONE);
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
                        Uri photoURI = FileProvider.getUriForFile(RoutineNewSample_Activity.this,
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
                        RoutineNewSample_Activity.this,
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                    String[] PERMISSIONS = {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                    };
                    ActivityCompat.requestPermissions(
                            RoutineNewSample_Activity.this, PERMISSIONS, 0
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
                                        RoutineNewSample_Activity.this,
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
                            Toast.makeText(RoutineNewSample_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnDeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DashBoard_Facilitator_Activity.mCurrentLocation == null) {
                    Toast.makeText(RoutineNewSample_Activity.this, "Please wait for Location...", Toast.LENGTH_LONG).show();
                } else {
                    tvLatitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
                    tvLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));
                }
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoutineNewSample_Activity.this, date, myCalendar
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
                new TimePickerDialog(RoutineNewSample_Activity.this, time_listener, hour,
                        minute, false).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandler databaseHandler = new DatabaseHandler(RoutineNewSample_Activity.this);
                sampleModelsDataCollection = new ArrayList<>();
                /*try {
                    sTaskIdx = databaseHandler.getTaskIdOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                String sFCID = CGlobal.getInstance().getPersistentPreference(RoutineNewSample_Activity.this)
                        .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
                sampleModelsDataCollection = databaseHandler.getSampleCollection(sFCID, "Routine");
                int noOfForm = CGlobal.getInstance().getPersistentPreference(RoutineNewSample_Activity.this)
                        .getInt(Constants.PREFS_NO_OF_ROUTINE_FORM, 100);
                if (sampleModelsDataCollection.size() < noOfForm) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RoutineNewSample_Activity.this);
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
                } else {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Sync before Collecting new.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RoutineNewSample_Activity.this, DataUploadRoutine_Activity.class);
                                    startActivity(intent);
                                    RoutineNewSample_Activity.this.finish();
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });


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

        getSelectSourceSite();

        getIsItSpecialDrive();

        getTypeOfLocality();

        getSourceType();

        getCollectingSample();
    }

    String sSourceSite = "", sIsItSpecialDrive = "", sSpecialDrive = "", sTypeOfLocality = "", sSourceType = "", sSourceTypeId = "",
            sBlockName = "", sBlockCode = "", sPanchayatName = "", sPanchayatCode = "", sVillageName = "", sVillageCode = "",
            sHabitationName = "", sHabitationCode = "", sSourceLocalityName = "", sTown = "", sWard_no = "",
            sHealth_facility_name = "", sHandPumpCategory = "", sIsItNewLocation = "", sScheme = "", sSchemeCode = "",
            sSelectSource = "", sZone = "", sBig_dia_tube_well_no = "", sBig_dia_tube_well_Code = "", sCollectingSample = "",
            sResidualChlorine = "", sResidualChlorineTested = "", sResidualChlorineTestedValue = "", sSubSourceType = "",
            sSubSourceTypeId = "", sSubSchemeName = "", sConditionOfSource = "", bigDiaTuble = "", sArsenic_id = "",
            sChamberAvailable = "", sWaterLevel = "";

    private void getSelectSourceSite() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSourceSite = databaseHandler.getSourceSite("R");
        cmaSourceSite.add(0, commonModel);
        Master_Adapter sourceSiteMaster_adapter = new Master_Adapter(RoutineNewSample_Activity.this, cmaSourceSite);
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
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("Yes");
        stringArrayList.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
                        getTypeOfLocality();
                        getSourceType();
                        getBlock();
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
        Master_Adapter specialDriveMaster_adapter = new Master_Adapter(RoutineNewSample_Activity.this, cmaSpecialDrive);
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
                getTypeOfLocality();
                getSourceType();
                getBlock();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getChamberAvailable() {
        ArrayList<String> stringArrayListChamberAvailable = new ArrayList<>();
        stringArrayListChamberAvailable.add("Choose");
        stringArrayListChamberAvailable.add("Yes");
        stringArrayListChamberAvailable.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayListChamberAvailable);
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
    }

    private void getTypeOfLocality() {
        ArrayList<String> stringArrayListLocality = new ArrayList<>();
        stringArrayListLocality.add("Choose");
        stringArrayListLocality.add("RURAL");
        stringArrayListLocality.add("URBAN");

        ArrayAdapter<String> adapterLocality = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayListLocality);
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
                        getSource();
                        getHandPumpCategory();

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                                || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                        } else {
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }

                        break;
                    case "URBAN":
                        sBlockName = "";
                        sPanchayatName = "";
                        sVillageName = "";
                        sHabitationName = "";
                        sSourceLocalityName = "";
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
                        getSource();
                        getHandPumpCategory();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSourceType() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSourceType = databaseHandler.getSourceType();
        cmaSourceType.add(0, commonModel);
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(RoutineNewSample_Activity.this, cmaSourceType);
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
                CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sSourceType).commit();

                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        sHandPumpCategory = "";
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
                        llHandPumpCategory.setVisibility(View.GONE);
                        llPipesTubeWell.setVisibility(View.GONE);
                        llDescriptionLocation.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.GONE);
                        llResidualChlorine.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llSubSourceTypeOther.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        sHandPumpCategory = "";
                        sIsItNewLocation = "";
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
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llResidualChlorineTested.setVisibility(View.VISIBLE);
                        getResidualChlorineTested();
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {

                        sHandPumpCategory = "";
                        sIsItNewLocation = "";
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
                        sIsItNewLocation = "";
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
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
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
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                                || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
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
                        llResidualChlorine.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.VISIBLE);
                        getConditionOfSource();
                    }
                } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                        sHandPumpCategory = "";
                        sIsItNewLocation = "";
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
                        llResidualChlorine.setVisibility(View.GONE);
                        llSubSourceType.setVisibility(View.GONE);
                        llSchemeName.setVisibility(View.GONE);
                        llSubSourceTypeOther.setVisibility(View.GONE);
                        llConditionOfSource.setVisibility(View.GONE);
                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                        sHandPumpCategory = "";
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
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
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
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
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.VISIBLE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
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
                        sIsItNewLocation = "";
                        llSelectScheme.setVisibility(View.GONE);
                        llSelectSource.setVisibility(View.GONE);
                        llZoneNumber.setVisibility(View.GONE);
                        llBigDiaTubeWellNumber.setVisibility(View.GONE);
                        llIsItNewLocation.setVisibility(View.GONE);
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
                        sIsItNewLocation = "";
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
                        llResidualChlorine.setVisibility(View.GONE);
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
    }

    private void getSubSourceType(String sourceTypeId) {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        cmaSubSourceType = databaseHandler.getChildSourceType(sourceTypeId);
        cmaSubSourceType.add(0, commonModel);
        Master_Adapter sourceTypeMaster_adapter = new Master_Adapter(RoutineNewSample_Activity.this, cmaSubSourceType);
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
                CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sSubSourceTypeId).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                        .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sSubSourceType).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        Block_Adapter block_adapter = new Block_Adapter(RoutineNewSample_Activity.this, cmaBlock);
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
        Panchayat_Adapter panchayat_adapter = new Panchayat_Adapter(RoutineNewSample_Activity.this, cmaPanchayat);
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
                getVillageName(sBlockCode, sPanchayatCode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        VillageName_Adapter villageName_adapter = new VillageName_Adapter(RoutineNewSample_Activity.this, cmaVillageName);
        spVillageName.setAdapter(villageName_adapter);

        spVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sVillageName = cmaVillageName.get(position).getVillagename();
                if (sVillageName.equalsIgnoreCase("Choose")) {
                    sVillageName = "";
                    sVillageCode = "";
                    spHabitation.setVisibility(View.GONE);
                    return;
                }
                spHabitation.setVisibility(View.VISIBLE);
                sVillageCode = cmaVillageName.get(position).getVillagecode();
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    getHabitationName(blockCode, panchayatCode, sVillageName);
                } else {
                    getHabitationName(blockCode, panchayatCode, sVillageCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        HabitationName_Adapter habitationName_adapter = new HabitationName_Adapter(RoutineNewSample_Activity.this, cmaHabitationName);
        spHabitation.setAdapter(habitationName_adapter);

        spHabitation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHabitationName = cmaHabitationName.get(position).getHabitationname();
                if (sHabitationName.equalsIgnoreCase("Choose")) {
                    sHabitationName = "";
                    sHabitationCode = "";
                    return;
                }
                sHabitationCode = cmaHabitationName.get(position).getHabecode();

                if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
                    if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                            || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, sVillageName, sHabitationName);
                        }

                    } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {

                        getSelectScheme(sBlockName);
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, sVillageName, sHabitationName);
                        }

                    } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        getSelectScheme(sBlockName);
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        getSelectScheme(sBlockName);
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        getSelectScheme(sBlockName);
                    } else {
                        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, sVillageName, sHabitationName);
                        } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                            sIsItNewLocation = "";
                            llDescriptionLocation.setVisibility(View.VISIBLE);
                            llLocationDescription.setVisibility(View.GONE);
                            sIsItNewLocation = "No";
                            getDescriptionLocation(blockCode, panchayatCode, villageNameCode, sHabitationCode);
                        } else {
                            sIsItNewLocation = "Yes";
                            llDescriptionLocation.setVisibility(View.GONE);
                            llLocationDescription.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDescriptionLocation(String sBlockCode, String sPanchayatCode, String sVillageCode, String SHabitationCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setSourcelocalityname("Choose");

        if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
            cmaDescriptionLocation = databaseHandler.getRoster(sBlockCode, sPanchayatCode, sVillageCode, SHabitationCode, sSpecialDrive);
        } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
            cmaDescriptionLocation = databaseHandler.getArsenic(sBlockCode, sPanchayatCode, sVillageCode, SHabitationCode);
        }

        cmaDescriptionLocation.add(0, commonModel);
        DescriptionLocation_Adapter descriptionLocation_adapter = new DescriptionLocation_Adapter(RoutineNewSample_Activity.this, cmaDescriptionLocation);
        spDescriptionLocation.setAdapter(descriptionLocation_adapter);

        spDescriptionLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sSourceLocalityName = cmaDescriptionLocation.get(position).getSourcelocalityname();
                if (sSourceLocalityName.equalsIgnoreCase("Choose")) {
                    sSourceLocalityName = "";
                    sArsenic_id = "";
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
    }

    private void getTown() {
        CommonModel commonModel = new CommonModel();
        commonModel.setTown_name("Choose");
        cmaTown = databaseHandler.getTown();
        cmaTown.add(0, commonModel);
        Town_Adapter town_adapter = new Town_Adapter(RoutineNewSample_Activity.this, cmaTown);
        spNameTown.setAdapter(town_adapter);

        spNameTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTown = cmaTown.get(position).getTown_name();
                if (sTown.equalsIgnoreCase("Choose")) {
                    sTown = "";
                    spWardNumber.setVisibility(View.GONE);
                    return;
                }
                spWardNumber.setVisibility(View.VISIBLE);
                getWardName(sTown);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getWardName(String sTown) {
        CommonModel commonModel = new CommonModel();
        commonModel.setWard_no("Choose");
        cmaWord = databaseHandler.getWard(sTown);
        cmaWord.add(0, commonModel);
        Ward_Adapter ward_adapter = new Ward_Adapter(RoutineNewSample_Activity.this, cmaWord);
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
                    if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                        getSelectScheme("");
                    } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                        getSelectScheme("");
                    } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                        getSelectScheme("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getHealthFacility(String blockCode) {
        CommonModel commonModel = new CommonModel();
        commonModel.setHealth_facility_name("Choose");
        cmaHealthFacility = databaseHandler.getHealthFacility(blockCode);
        cmaHealthFacility.add(0, commonModel);
        HealthFacility_Adapter ward_adapter = new HealthFacility_Adapter(RoutineNewSample_Activity.this, cmaHealthFacility);
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
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
    }

    private void getIsItNewLocation() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("Yes");
        stringArrayList.add("No");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
                    sLocationDescription = "";
                    getDescriptionLocation(sBlockCode, sPanchayatCode, sVillageCode, sHabitationCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        Scheme_Adapter scheme_adapter = new Scheme_Adapter(RoutineNewSample_Activity.this, cmaScheme);
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
                getSource();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSource() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("DISTRIBUTION SYSTEM");
        stringArrayList.add("PUMP HOUSE");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
    }

    private void getZoneNumber(final String sSchemeCode) {
        if (TextUtils.isEmpty(sSchemeCode)) {
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            Zone_Adapter zone_adapter = new Zone_Adapter(RoutineNewSample_Activity.this, cmaZone);
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
    }

    private void getBigDiaTubeWellNumber(String sSchemeCode, String sZone) {
        CommonModel commonModel = new CommonModel();
        commonModel.setBig_dia_tube_well_no("Choose");
        cmaBigDiaTubeWellNumber = databaseHandler.getBigDiaTubeWellNo(sSchemeCode, sZone);
        if (!TextUtils.isEmpty(cmaBigDiaTubeWellNumber.get(0).getBig_dia_tube_well_no())) {
            tietBigDiaTubeWellNumber.setVisibility(View.GONE);
            spBigDiaTubeWellNumber.setVisibility(View.VISIBLE);
            cmaBigDiaTubeWellNumber.add(0, commonModel);
            BigDiaTubeWellNumber_Adapter bigDiaTubeWellNumber_adapter = new BigDiaTubeWellNumber_Adapter(RoutineNewSample_Activity.this, cmaBigDiaTubeWellNumber);
            spBigDiaTubeWellNumber.setAdapter(bigDiaTubeWellNumber_adapter);
            spBigDiaTubeWellNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sBig_dia_tube_well_no = cmaBigDiaTubeWellNumber.get(position).getBig_dia_tube_well_no();
                    if (sBig_dia_tube_well_no.equalsIgnoreCase("Choose")) {
                        sBig_dia_tube_well_no = "";
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
    }

    private void getConditionOfSource() {
        ArrayList<String> stringConditionOfSource = new ArrayList<>();
        stringConditionOfSource.add("Choose");
        stringConditionOfSource.add("FUNCTIONAL");
        stringConditionOfSource.add("DEFUNCT");

        ArrayAdapter<String> adapterConditionOfSource = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringConditionOfSource);
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
    }

    private void getCollectingSample() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("FACILITATOR");
        stringArrayList.add("LABORATORY STAFF");
        stringArrayList.add("SAMPLING ASSISTANT");
        stringArrayList.add("HEALTH PERSONNEL");
        stringArrayList.add("MLV");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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

    private void getResidualChlorineTested() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("Yes");
        stringArrayList.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
                        sResidualChlorineTestedValue = "1";
                        llResidualChlorine.setVisibility(View.VISIBLE);
                        getResidualChlorine();
                        break;
                    case "No":
                        sResidualChlorineTestedValue = "0";
                        llResidualChlorine.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getResidualChlorine() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("VISUAL COMPARATOMETRIC METHOD");

        //"DPD1 TABLET WITH MANUAL COLOR COMPARISON"

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                RoutineNewSample_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    RoutineNewSample_Activity.this, new GeocoderHandler());
        }
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(RoutineNewSample_Activity.this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("", "All location settings are satisfied.");
                        updateLocationUI();
                    }
                })
                .addOnFailureListener(RoutineNewSample_Activity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i("", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(RoutineNewSample_Activity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("", errorMessage);

                                Toast.makeText(RoutineNewSample_Activity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
           /* tvLocation.setText(
                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
                            "Lng: " + mCurrentLocation.getLongitude()
            );*/

            /*Toast.makeText(RoutineNewSample_Activity.this, "Lat: " + mCurrentLocation.getLatitude()
                    + ", " + "Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();*/

            tvLatitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));
            sAccuracy = String.valueOf(mCurrentLocation.getAccuracy());

            double latitude = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    RoutineNewSample_Activity.this, new GeocoderHandler());

            /*// giving a blink animation on TextView
            tvLocation.setAlpha(0);
            tvLocation.animate().alpha(1).setDuration(300);

            // location last updated time
            tvLocation.setText("Last updated on: " + mLastUpdateTime);*/
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e("", "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e("", "User chose not to make required location settings changes.");
                        break;
                }
                break;

            case CAMERA_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        handleBigCameraPhoto();
                        break;
                    case Activity.RESULT_CANCELED:
                        mCurrentPhotoPath = null;
                        ivPicture.setImageBitmap(null);
                        break;
                }
                break;

            case CAMERA_REQUEST_1:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        handleBigCameraPhoto_1();
                        break;
                    case Activity.RESULT_CANCELED:
                        mCurrentPhotoPath_1 = null;
                        ivPictureSampleBottle.setImageBitmap(null);
                        break;
                }
                break;
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
            } else {
                actualHeight = options.outHeight;
                actualWidth = options.outWidth;
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
        Cursor cursor = RoutineNewSample_Activity.this.getContentResolver().query(contentUri, null, null, null, null);
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
        compressImage_1(mCurrentPhotoPath_1);
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
        Cursor cursor = RoutineNewSample_Activity.this.getContentResolver().query(contentUri, null, null, null, null);
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
            sResidualChlorineValue = "", sMobileIMEI = "", sMobileSerialNo = "", sMobileModelNo = "", sPinCode = "";
    int iTotalDepth;

    private void nextPageWithValidation() {
        if (ActivityCompat.checkSelfPermission(RoutineNewSample_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        long unixTime = System.currentTimeMillis() / 1000L;

        long first14 = (long) (Math.random() * 100000000000000L);

        String uTime = String.valueOf(unixTime) + "_" + String.valueOf(first14);

        String sSampleCollectorId = CGlobal.getInstance().getPersistentPreference(RoutineNewSample_Activity.this)
                .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

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
        sResidualChlorineValue = tietResidualChlorine.getText().toString();
        sWaterLevel = tietWaterLevel.getText().toString();
        sPinCode = tietPinCode.getText().toString();

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

        if (TextUtils.isEmpty(sSourceSite) || sSourceSite.equalsIgnoreCase("Choose")) {
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
                        .setMessage("Please select Water Source Type")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sBlockCode) || sBlockName.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sVillageName) || sVillageName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationName) || sHabitationName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sVillageCode) || sVillageName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Village Name")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                if (TextUtils.isEmpty(sHabitationCode) || sHabitationName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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

            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    if (sIsItNewLocation.equalsIgnoreCase("No")) {
                        if (TextUtils.isEmpty(sSourceLocalityName) || sSourceLocalityName.equalsIgnoreCase("Choose")) {
                            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                } else if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                                sBig_dia_tube_well_no = sBigDiaTubeWellValue;
                            }
                        }
                    } else {
                        if (TextUtils.isEmpty(sZoneValue) || TextUtils.isEmpty(sBigDiaTubeWellValue)) {
                            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                            sBig_dia_tube_well_no = sBigDiaTubeWellValue;
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
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
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

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    if (sIsItNewLocation.equalsIgnoreCase("No")) {
                        if (TextUtils.isEmpty(sSourceLocalityName) || sSourceLocalityName.equalsIgnoreCase("Choose")) {
                            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                } else if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select/Enter Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select/Enter Scheme")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else {
                if (sIsItNewLocation.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    if (TextUtils.isEmpty(sSourceLocalityName) || sSourceLocalityName.equalsIgnoreCase("Choose")) {
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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

            if (TextUtils.isEmpty(sTown) || sTown.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }

                /*if (sSelectSource.equalsIgnoreCase("Choose")) {a
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            } else if (sSourceType.equals("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (TextUtils.isEmpty(sLocationDescription.trim())) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please Enter Location Description")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (TextUtils.isEmpty(sSubSourceType) || sSubSourceType.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("Please select Water Sub Source Type")
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
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
            PackageInfo packageInfo = getPackageManager().getPackageInfo(RoutineNewSample_Activity.this.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sTaskId = databaseHandler.getTaskId(sBlockCode, sPanchayatCode, sVillageCode, sHabitationCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sFCID = CGlobal.getInstance().getPersistentPreference(RoutineNewSample_Activity.this).getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        if (sTypeOfLocality.equalsIgnoreCase("RURAL")) {
            if (sSourceType.equalsIgnoreCase("DUG WELL")
                    || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "", sSourceLocalityName,
                            "", "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId, "", "",
                            sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Dug well/Spring/water treatment unit.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "",
                            "", sPinCode, "0");
                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
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

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, sSchemeCode,
                            "", sZone, sSelectSource,
                            "", sSourceLocalityName,
                            "", "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()),
                            sCollectingSample, sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId, "",
                            "", sFCID, "", "", "", "",
                            sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Piped water supply")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, sSchemeCode,
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "",
                            sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "", sSourceLocalityName,
                            "", "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");
                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);

                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of OTHERS")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            "", "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", sSourceLocalityName,
                            "", "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of MUNICIPAL Piped water supply")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", sSourceLocalityName,
                            "", "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Arsenic trend station in case of Own Piped water supply")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")) {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "No", sSourceLocalityName,
                            "", sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", sConditionOfSource, sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId, "", "",
                            sFCID, sArsenic_id, "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
                                .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RoutineNewSample_Activity.this, DashBoard_Facilitator_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }
                } else if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "No", sSourceLocalityName,
                            "", sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", sConditionOfSource, sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId, "", "",
                            sFCID, sArsenic_id, sChamberAvailable, sWaterLevel, "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
                                .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RoutineNewSample_Activity.this, DashBoard_Facilitator_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), sBlockCode, sPanchayatCode,
                            sVillageName, sHabitationName, "",
                            "", sHealth_facility_name, "", "",
                            "", "", "",
                            "Yes", "",
                            sLocationDescription, sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", sConditionOfSource, sVillageCode,
                            sHabitationCode, sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
                                .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RoutineNewSample_Activity.this, DashBoard_Facilitator_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }
                }
            }
        } else if (sTypeOfLocality.equalsIgnoreCase("URBAN")) {
            if (sSourceType.equalsIgnoreCase("DUG WELL") || sSourceType.equalsIgnoreCase("SPRING")
                    || sSourceType.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {

                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, "", "",
                            "", "", "",
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", "",
                            "", sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("PIPED WATER SUPPLY")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme, sSchemeCode,
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", "", "",
                            "", sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("OTHERS")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, "", "",
                            "", "", "",
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            "", "", "",
                            "", sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId,
                            "", "", sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {

                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", "",
                            "", sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId, "", "",
                            sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else if (sSourceType.equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, sScheme, "",
                            "", sZone, sSelectSource,
                            "", "",
                            sLocationDescription, "", sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            sBig_dia_tube_well_Code, "", String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, sSubSourceType,
                            sSubSchemeName, "", "",
                            "", sCollectingSample, sMobileModelNo, uTime, sResidualChlorineTestedValue, sResidualChlorine,
                            sResidualChlorineValue, "", "", sTaskId, "", "",
                            sFCID, "", "", "", "", sPinCode, "0");


                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                }
            } else {
                if (sSpecialDrive.equalsIgnoreCase("ROSTER") || sSpecialDrive.equalsIgnoreCase("JE/AES ROSTER")
                        || sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
                    new AlertDialog.Builder(RoutineNewSample_Activity.this)
                            .setMessage("You can't select Roster in case of Urban.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    databaseHandler.addSampleCollection("Routine", uTime, sSourceSite, sIsItSpecialDrive, sSpecialDrive,
                            sData, sTime, sTypeOfLocality, sSourceType,
                            databaseHandler.getDistrictCode(), "", "",
                            "", "", sTown,
                            sWard_no, sHealth_facility_name, "", "",
                            "", "", "",
                            "Yes", "",
                            sLocationDescription, sHandPumpCategory, sSampleBottleNumber,
                            mCurrentPhotoPath, sLatitude, sLongitude,
                            String.valueOf(DashBoard_Facilitator_Activity.mCurrentLocation.getAccuracy()), sCollectingSample,
                            "", sPipesTubeWell, String.valueOf(iTotalDepth),
                            sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath, sSampleCollectorId, "",
                            "", sConditionOfSource, "",
                            "", sCollectingSample, sMobileModelNo, uTime, "", "",
                            "", "", "", sTaskId, "", "",
                            sFCID, "", "", "", "", sPinCode, "0");

                    int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

                    CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                            .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();
                    if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                        new AlertDialog.Builder(RoutineNewSample_Activity.this)
                                .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RoutineNewSample_Activity.this, DashBoard_Facilitator_Activity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                        dialog.dismiss();
                                    }
                                }).show();
                    } else {
                        Intent intent = new Intent(RoutineNewSample_Activity.this, SanitarySurveyQuesAns_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                    }
                }
            }
        }
        mCurrentPhotoPath = null;
        mCurrentPhotoPath_1 = null;

        CGlobal.getInstance().getPersistentPreferenceEditor(RoutineNewSample_Activity.this)
                .putBoolean(Constants.PREFS_RESTART_FRAGMENT, true).commit();

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            tvAddress.setText(locationAddress);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(RoutineNewSample_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RoutineNewSample_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, RoutineNewSample_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}
