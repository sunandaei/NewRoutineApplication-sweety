package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.AnganwadiData_Adapter;
import com.sunanda.newroutine.application.adapter.AutoText_Adapter;
import com.sunanda.newroutine.application.adapter.Master_Adapter;
import com.sunanda.newroutine.application.adapter.Scheme_Adapter;
import com.sunanda.newroutine.application.adapter.SchoolData_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.newactivity.RoutineNewSample_Activity;
import com.sunanda.newroutine.application.newactivity.SchoolInfo_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.LocationAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OldSampleCollection_Activity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_sample_collection_activity);
        mGoogleApiClient = new GoogleApiClient.Builder(OldSampleCollection_Activity.this)
                .addConnectionCallbacks(OldSampleCollection_Activity.this)
                .addOnConnectionFailedListener(OldSampleCollection_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        StrictMode.VmPolicy.Builder builder1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder1.build());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        init();
    }

    LinearLayout llPipesTubeWell, llResidualChlorineTested, llResidualChlorineRoutine, llConditionOfSource,
            llSchoolNameAndAnganwadiName, llSharedSource, llSharedWith, llOthersType, llListShow, llResidualChlorineInputOMAS,
            llResidualChlorineResultOMAS, llResidualChlorineDescriptionOMAS, llSpecialDrive, llNameSpecialDrive, llAppType,
            llChamberAvailable, llWaterLevel, llSelectScheme, llSchemeName;

    AutoCompleteTextView actvResidualChlorineInputOMAS;

    TextInputEditText tietPipesTubeWell, tietSampleBottleNumber, tietResidualChlorineRoutine, tietOthersType,
            tietWaterLevel, tietPinCode, tietSchemeName;

    ImageView ivPicture;

    Button btnTakeImage, btnNext, btnDeviceLocation;

    Spinner spCollectingSample, spResidualChlorineTested, spResidualChlorineRoutine,
            spConditionOfSource, spListShow, spSharedWith, spSharedSource, spIsItSpecialDrive,
            spNameSpecialDrive, spAppType, spChamberAvailable, spSelectScheme;

    TextView tvSchoolNameAndAnganwadiName, tvListShow, tvLatitude, tvLongitude, tvResidualChlorineResultOMAS, tvResidualChlorineDescriptionOMAS;

    GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private SettingsClient mSettingsClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CHECK_SETTINGS = 101;
    String sAccuracy = "", sLatitude = "", sLongitude = "";
    private static final int CAMERA_REQUEST = 1015;
    private String mCurrentPhotoPath = "", sSharedSource = "", sSharedWith = "", sSchool_Anganwadi_Name_SharedWith = "",
            versionName = "";

    private void init() {

        llPipesTubeWell = findViewById(R.id.llPipesTubeWell);
        llResidualChlorineTested = findViewById(R.id.llResidualChlorineTested);
        llResidualChlorineRoutine = findViewById(R.id.llResidualChlorineRoutine);
        llConditionOfSource = findViewById(R.id.llConditionOfSource);
        llResidualChlorineInputOMAS = findViewById(R.id.llResidualChlorineInputOMAS);
        llResidualChlorineResultOMAS = findViewById(R.id.llResidualChlorineResultOMAS);
        llResidualChlorineDescriptionOMAS = findViewById(R.id.llResidualChlorineDescriptionOMAS);
        llSpecialDrive = findViewById(R.id.llSpecialDrive);
        llNameSpecialDrive = findViewById(R.id.llNameSpecialDrive);
        llAppType = findViewById(R.id.llAppType);
        llChamberAvailable = findViewById(R.id.llChamberAvailable);
        llWaterLevel = findViewById(R.id.llWaterLevel);
        llSelectScheme = findViewById(R.id.llSelectScheme);
        llSchemeName = findViewById(R.id.llSchemeName);

        actvResidualChlorineInputOMAS = findViewById(R.id.actvResidualChlorineInputOMAS);

        tietPipesTubeWell = findViewById(R.id.tietPipesTubeWell);
        tietSampleBottleNumber = findViewById(R.id.tietSampleBottleNumber);
        tietResidualChlorineRoutine = findViewById(R.id.tietResidualChlorineRoutine);
        tietWaterLevel = findViewById(R.id.tietWaterLevel);
        tietPinCode = findViewById(R.id.tietPinCode);
        tietSchemeName = findViewById(R.id.tietSchemeName);

        ivPicture = findViewById(R.id.ivPicture);

        btnTakeImage = findViewById(R.id.btnTakeImage);
        btnNext = findViewById(R.id.btnNext);
        btnDeviceLocation = findViewById(R.id.btnDeviceLocation);

        spCollectingSample = findViewById(R.id.spCollectingSample);
        spResidualChlorineTested = findViewById(R.id.spResidualChlorineTested);
        spResidualChlorineRoutine = findViewById(R.id.spResidualChlorineRoutine);
        spConditionOfSource = findViewById(R.id.spConditionOfSource);
        spAppType = findViewById(R.id.spAppType);
        spChamberAvailable = findViewById(R.id.spChamberAvailable);
        spSelectScheme = findViewById(R.id.spSelectScheme);

        //School
        llSchoolNameAndAnganwadiName = findViewById(R.id.llSchoolNameAndAnganwadiName);
        llSharedSource = findViewById(R.id.llSharedSource);
        llSharedWith = findViewById(R.id.llSharedWith);
        llOthersType = findViewById(R.id.llOthersType);
        llListShow = findViewById(R.id.llListShow);

        tietOthersType = findViewById(R.id.tietOthersType);

        spListShow = findViewById(R.id.spListShow);
        spSharedWith = findViewById(R.id.spSharedWith);
        spSharedSource = findViewById(R.id.spSharedSource);
        spIsItSpecialDrive = findViewById(R.id.spIsItSpecialDrive);
        spNameSpecialDrive = findViewById(R.id.spNameSpecialDrive);

        tvSchoolNameAndAnganwadiName = findViewById(R.id.tvSchoolNameAndAnganwadiName);
        tvListShow = findViewById(R.id.tvListShow);
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvResidualChlorineResultOMAS = findViewById(R.id.tvResidualChlorineResultOMAS);
        tvResidualChlorineDescriptionOMAS = findViewById(R.id.tvResidualChlorineDescriptionOMAS);


        llPipesTubeWell.setVisibility(View.GONE);
        llResidualChlorineTested.setVisibility(View.GONE);
        llResidualChlorineRoutine.setVisibility(View.GONE);
        llResidualChlorineInputOMAS.setVisibility(View.GONE);
        llResidualChlorineResultOMAS.setVisibility(View.GONE);
        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
        llChamberAvailable.setVisibility(View.GONE);
        llWaterLevel.setVisibility(View.GONE);
        llSelectScheme.setVisibility(View.GONE);
        llSchemeName.setVisibility(View.GONE);

        //School
        llSchoolNameAndAnganwadiName.setVisibility(View.GONE);
        llSharedSource.setVisibility(View.GONE);
        llSharedWith.setVisibility(View.GONE);
        llOthersType.setVisibility(View.GONE);
        llListShow.setVisibility(View.GONE);

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
                        Uri photoURI = FileProvider.getUriForFile(OldSampleCollection_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        getCollectingSample();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(OldSampleCollection_Activity.this);
                builder1.setTitle("Water Quality");
                builder1.setMessage("Are you sure you want to save it?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendExsitingSampleData();
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

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm");
        formattedDate = df.format(c.getTime());
        formattedTime = df1.format(c.getTime());

        String json = CGlobal.getInstance().getPersistentPreference(OldSampleCollection_Activity.this)
                .getString(Constants.PREFS_SOURCE_SAMPLE_DETAILS, "");

        Type listType = new TypeToken<CommonModel>() {
        }.getType();
        responseSourceData = new GsonBuilder().create().fromJson(json, listType);

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("PIPED WATER SUPPLY")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            getResidualChlorineTested();
            llResidualChlorineTested.setVisibility(View.VISIBLE);
            llPipesTubeWell.setVisibility(View.GONE);
            llConditionOfSource.setVisibility(View.GONE);
            llSelectScheme.setVisibility(View.VISIBLE);
            llSchemeName.setVisibility(View.GONE);
            getSelectScheme(responseSourceData.getBlockcode());
        } else if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
            llPipesTubeWell.setVisibility(View.VISIBLE);
            llResidualChlorineTested.setVisibility(View.GONE);
            llConditionOfSource.setVisibility(View.VISIBLE);
            getConditionOfSource();
            llSelectScheme.setVisibility(View.GONE);
            llSchemeName.setVisibility(View.GONE);
        } else {
            llResidualChlorineTested.setVisibility(View.GONE);
            llPipesTubeWell.setVisibility(View.GONE);
            llConditionOfSource.setVisibility(View.GONE);
            llSelectScheme.setVisibility(View.GONE);
            llSchemeName.setVisibility(View.GONE);
        }

        if (responseSourceData.getApp_name().equalsIgnoreCase("School")) {
            DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
            String schoolName = databaseHandler.getSchoolName(responseSourceData.getIsnewlocation_School_UdiseCode());
            tvSchoolNameAndAnganwadiName.setText(schoolName);
            llSchoolNameAndAnganwadiName.setVisibility(View.VISIBLE);
            llSharedSource.setVisibility(View.VISIBLE);
            llSharedWith.setVisibility(View.VISIBLE);
            llOthersType.setVisibility(View.VISIBLE);
            llListShow.setVisibility(View.VISIBLE);
            llAppType.setVisibility(View.GONE);
            llChamberAvailable.setVisibility(View.GONE);
            llWaterLevel.setVisibility(View.GONE);
            getSharedSource();
        } else {
            llSchoolNameAndAnganwadiName.setVisibility(View.GONE);
            llSharedSource.setVisibility(View.GONE);
            llSharedWith.setVisibility(View.GONE);
            llOthersType.setVisibility(View.GONE);
            llListShow.setVisibility(View.GONE);
            llAppType.setVisibility(View.VISIBLE);
            llChamberAvailable.setVisibility(View.GONE);
            llWaterLevel.setVisibility(View.GONE);
            getAppType();
        }

        if (responseSourceData.getName_of_special_drive().equalsIgnoreCase("ARSENIC TREND STATION")) {
            llSpecialDrive.setVisibility(View.GONE);
            llChamberAvailable.setVisibility(View.VISIBLE);
            llWaterLevel.setVisibility(View.GONE);
            getChamberAvailable();
        } else {
            llSpecialDrive.setVisibility(View.VISIBLE);
            llChamberAvailable.setVisibility(View.GONE);
            llWaterLevel.setVisibility(View.GONE);
            getIsItSpecialDrive();
        }

        tietPinCode.setText(responseSourceData.getPin_code());

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

        btnDeviceLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentLocation == null) {
                    Toast.makeText(OldSampleCollection_Activity.this, "Please wait for Location...", Toast.LENGTH_LONG).show();
                } else {
                    tvLatitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
                    tvLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));

                    sAccuracy = String.valueOf(mCurrentLocation.getAccuracy());
                    sLatitude = String.valueOf(mCurrentLocation.getLatitude());
                    sLongitude = String.valueOf(mCurrentLocation.getLongitude());

                    distance = distance(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), responseSourceData.getLat(), responseSourceData.getLng());

                    if (distance > 30) {
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
                                .setMessage("The Current GPS is too far from the previous GPS of the same source, Are you sure you want to take this!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }
            }
        });
    }

    CommonModel responseSourceData;
    String formattedDate, formattedTime;

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(OldSampleCollection_Activity.this, mGoogleApiClient);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        startLocationUpdates();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(OldSampleCollection_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(OldSampleCollection_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, OldSampleCollection_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new OldSampleCollection_Activity.GeocoderHandler());

            /*tvLatitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));

            sAccuracy = String.valueOf(mCurrentLocation.getAccuracy());
            sLatitude = String.valueOf(mCurrentLocation.getLatitude());
            sLongitude = String.valueOf(mCurrentLocation.getLongitude());*/
        }
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("", "All location settings are satisfied.");

                        //Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        /*mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());*/

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
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
                                    rae.startResolutionForResult(OldSampleCollection_Activity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("", errorMessage);

                                Toast.makeText(OldSampleCollection_Activity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            //sAccuracy = String.valueOf(mCurrentLocation.getAccuracy());
            //sLatitude = String.valueOf(mCurrentLocation.getLatitude());
            //sLongitude = String.valueOf(mCurrentLocation.getLongitude());

            /*tvLatitude.setText(String.valueOf(mCurrentLocation.getLatitude()));
            tvLongitude.setText(String.valueOf(mCurrentLocation.getLongitude()));

            double latitude = mCurrentLocation.getLatitude();
            double longitude = mCurrentLocation.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new OldSampleCollection_Activity.GeocoderHandler());*/

        }
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
            //tvAddress.setText(locationAddress);
        }
    }

    private static final String JPEG_FILE_PREFIX = "img_source_new_";

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        }
    }

    String sCollectingSample = "", sResidualChlorine = "", sResidualChlorineTested = "", sResidualChlorineTestedValue = "",
            sConditionOfSource = "", sResidualChlorineValue = "", sResidualChlorineResultOMAS = "", sResidualChlorineDescriptionOMAS = "",
            sIsItSpecialDrive = "", sSpecialDrive = "", sAppType = "", sChamberAvailable = "", sWaterLevel = "";

    ArrayList<CommonModel> cmaSpecialDrive = new ArrayList<>();

    private void getAppType() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Routine");
        stringArrayList.add("OMAS");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                OldSampleCollection_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
        adapter_IsItSpecialDrive.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAppType.setAdapter(adapter_IsItSpecialDrive);

        spAppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAppType = spAppType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(responseSourceData.getApp_name())) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                String ssAppType = stringArrayList.get(i);
                if (!TextUtils.isEmpty(ssAppType)) {
                    if (ssAppType.equalsIgnoreCase(responseSourceData.getApp_name())) {
                        spAppType.setSelection(i);
                        sAppType = responseSourceData.getApp_name();
                    }
                }
            }
        }
    }

    private void getIsItSpecialDrive() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("Yes");
        stringArrayList.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                OldSampleCollection_Activity.this, android.R.layout.simple_spinner_item, stringArrayList);
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

        if (!TextUtils.isEmpty(responseSourceData.getSpecial_drive())) {
            for (int i = 0; i < stringArrayList.size(); i++) {
                String isItSpecialDrive = stringArrayList.get(i);
                if (!TextUtils.isEmpty(isItSpecialDrive)) {
                    if (isItSpecialDrive.equalsIgnoreCase(responseSourceData.getSpecial_drive())) {
                        spIsItSpecialDrive.setSelection(i);
                        sIsItSpecialDrive = responseSourceData.getSpecial_drive();
                    }
                }
            }
        }
    }

    private void getNameSpecialDrive() {
        CommonModel commonModel = new CommonModel();
        commonModel.setName("Choose");
        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        cmaSpecialDrive = databaseHandler.getSpecialDrive("ARSENIC TREND STATION");
        cmaSpecialDrive.add(0, commonModel);
        Master_Adapter specialDriveMaster_adapter = new Master_Adapter(OldSampleCollection_Activity.this, cmaSpecialDrive);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(responseSourceData.getName_of_special_drive())) {
            for (int i = 0; i < cmaSpecialDrive.size(); i++) {
                String specialdrive_id = cmaSpecialDrive.get(i).getName();
                if (!TextUtils.isEmpty(specialdrive_id)) {
                    if (specialdrive_id.equalsIgnoreCase(responseSourceData.getName_of_special_drive())) {
                        spNameSpecialDrive.setSelection(i);
                        sSpecialDrive = cmaSpecialDrive.get(i).getName();
                        if (responseSourceData.getName_of_special_drive().equalsIgnoreCase("ARSENIC TREND STATION")) {
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
                OldSampleCollection_Activity.this, android.R.layout.simple_spinner_item, stringArrayListChamberAvailable);
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

    private void getConditionOfSource() {
        ArrayList<String> stringConditionOfSource = new ArrayList<>();
        stringConditionOfSource.add("Choose");
        stringConditionOfSource.add("FUNCTIONAL");
        stringConditionOfSource.add("DEFUNCT");

        ArrayAdapter<String> adapterConditionOfSource = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringConditionOfSource);
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
        stringArrayList.add("FACILITATOR");
        stringArrayList.add("LABORATORY STAFF");
        stringArrayList.add("SAMPLING ASSISTANT");
        stringArrayList.add("HEALTH PERSONNEL");
        stringArrayList.add("MLV");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCollectingSample.setAdapter(adapter);

        spCollectingSample.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCollectingSample = spCollectingSample.getSelectedItem().toString();
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
                this, android.R.layout.simple_spinner_item, stringArrayList);
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
                        sResidualChlorineTestedValue = "1";
                        /*if (responseSourceData.getApp_name().equalsIgnoreCase("OMAS")) {
                            llResidualChlorineRoutine.setVisibility(View.GONE);
                            llResidualChlorineInputOMAS.setVisibility(View.VISIBLE);
                            llResidualChlorineResultOMAS.setVisibility(View.VISIBLE);
                            llResidualChlorineDescriptionOMAS.setVisibility(View.VISIBLE);
                            autoText();
                        } else if (responseSourceData.getApp_name().equalsIgnoreCase("Routine")) {*/
                        llResidualChlorineRoutine.setVisibility(View.VISIBLE);
                        llResidualChlorineInputOMAS.setVisibility(View.GONE);
                        llResidualChlorineResultOMAS.setVisibility(View.GONE);
                        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
                        getResidualChlorine();
                        //}
                        break;
                    case "No":
                        sResidualChlorineTestedValue = "0";
                        llResidualChlorineRoutine.setVisibility(View.GONE);
                        llResidualChlorineInputOMAS.setVisibility(View.GONE);
                        llResidualChlorineResultOMAS.setVisibility(View.GONE);
                        llResidualChlorineDescriptionOMAS.setVisibility(View.GONE);
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
                this, android.R.layout.simple_spinner_item, stringArrayList);
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
    }

    ArrayList<CommonModel> cmaScheme;
    DatabaseHandler databaseHandler;
    String sScheme = "", sSchemeCode = "", sSubSchemeName = "";

    private void getSelectScheme(String sBlock) {
        databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        String blockName = databaseHandler.getBlockSingle(sBlock);
        cmaScheme = new ArrayList<>();
        if (responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
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
            cmaScheme = databaseHandler.getScheme(blockName);
            CommonModel commonModel = new CommonModel();
            commonModel.setPwssname("Choose");
            cmaScheme.add(0, commonModel);
        }
        Scheme_Adapter scheme_adapter = new Scheme_Adapter(OldSampleCollection_Activity.this, cmaScheme);
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
                } else if (sScheme.equalsIgnoreCase("PROVIDE SCHEME NAME")) {
                    llSchemeName.setVisibility(View.VISIBLE);
                } else {
                    llSchemeName.setVisibility(View.GONE);
                    sSchemeCode = cmaScheme.get(position).getSmcode();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (!TextUtils.isEmpty(responseSourceData.getSub_scheme_name())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String subschemename = cmaScheme.get(i).getPwssname();
                    if (!TextUtils.isEmpty(subschemename)) {
                        if (subschemename.equalsIgnoreCase(responseSourceData.getSub_scheme_name())) {
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
            if (!TextUtils.isEmpty(responseSourceData.getScheme_code())) {
                for (int i = 0; i < cmaScheme.size(); i++) {
                    String schemeid = cmaScheme.get(i).getSmcode();
                    if (!TextUtils.isEmpty(schemeid)) {
                        if (schemeid.equalsIgnoreCase(responseSourceData.getScheme_code())) {
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

    ArrayList<CommonModel> commonModelArrayListAutoText;

    private void autoText() {
        commonModelArrayListAutoText = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        commonModelArrayListAutoText = databaseHandler.getResidualChlorineResult();
        AutoText_Adapter block_adapter = new AutoText_Adapter(OldSampleCollection_Activity.this, R.layout.old_sample_collection_activity,
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

    //SCHOOL

    private void getSharedSource() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        stringArrayList.add("Yes");
        stringArrayList.add("No");

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
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

    private void getSharedWith() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Choose");
        if (responseSourceData.getSource_site().equalsIgnoreCase("SCHOOL")) {
            stringArrayList.add("ANGANWADI");
            stringArrayList.add("OTHERS");
        } else if (responseSourceData.getSource_site().equalsIgnoreCase("ANGANWADI")) {
            stringArrayList.add("SCHOOL");
            stringArrayList.add("OTHERS");
        }

        ArrayAdapter<String> adapter_IsItSpecialDrive = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, stringArrayList);
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

                        if (responseSourceData.getType_of_locality().equalsIgnoreCase("RURAL")) {
                            getAwsDataSourceRural_SharedWith(responseSourceData.getBlockcode(), responseSourceData.getPancode());
                        } else {
                            getAwsDataSourceUrban_SharedWith(responseSourceData.getTown_name());
                        }

                        break;
                    case "SCHOOL":
                        tvListShow.setText("Select School Name");

                        tietOthersType.setText("");
                        llOthersType.setVisibility(View.GONE);
                        tietOthersType.setVisibility(View.GONE);
                        llListShow.setVisibility(View.VISIBLE);

                        getSchoolData_SharedWith(responseSourceData.getBlockcode(), responseSourceData.getPancode());

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
    }

    ArrayList<CommonModel> cmaSchoolData_SharedWith = new ArrayList<>();
    ArrayList<CommonModel> cmaAnganwadiData_SharedWith = new ArrayList<>();

    private void getSchoolData_SharedWith(String sBlockCode, String sPanCode) {
        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        CommonModel commonModel = new CommonModel();
        commonModel.setSchool_name("Choose");
        cmaSchoolData_SharedWith = databaseHandler.getSchoolDataSheet(sBlockCode, sPanCode);
        cmaSchoolData_SharedWith.add(0, commonModel);
        SchoolData_Adapter schoolData_Adapter = new SchoolData_Adapter(OldSampleCollection_Activity.this, cmaSchoolData_SharedWith);
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
    }

    private void getAwsDataSourceRural_SharedWith(String sBlockCode, String sPanCode) {
        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData_SharedWith = databaseHandler.getAwsDataSourceMasterRural(sBlockCode, sPanCode);
        cmaAnganwadiData_SharedWith.add(0, commonModel);
        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(OldSampleCollection_Activity.this,
                cmaAnganwadiData_SharedWith);
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
    }

    private void getAwsDataSourceUrban_SharedWith(String townName) {
        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        CommonModel commonModel = new CommonModel();
        commonModel.setAwcname("Choose");
        cmaAnganwadiData_SharedWith = databaseHandler.getAwsDataSourceMasterUrban(townName);
        cmaAnganwadiData_SharedWith.add(0, commonModel);
        AnganwadiData_Adapter anganwadiData_Adapter = new AnganwadiData_Adapter(OldSampleCollection_Activity.this,
                cmaAnganwadiData_SharedWith);
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
    }

    int total_depth_q_22;
    double distance = 0.0;
    String sMobileIMEI = "", sMobileSerialNo = "", sMobileModelNo = "", lat = "", lng = "", sPinCode = "",
            oldLocation = "";

    private void sendExsitingSampleData() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        long unixTime = System.currentTimeMillis() / 1000L;

        long first14 = (long) (Math.random() * 100000000000000L);

        String uTime = String.valueOf(unixTime) + "_" + String.valueOf(first14);

        String sample_bottle_number_q_16 = tietSampleBottleNumber.getText().toString();
        String how_many_pipes_q_21 = tietPipesTubeWell.getText().toString();

        lat = tvLatitude.getText().toString();
        lng = tvLongitude.getText().toString();
        sWaterLevel = tietWaterLevel.getText().toString();
        sWaterLevel = tietWaterLevel.getText().toString();
        sPinCode = tietPinCode.getText().toString();

        //if (responseSourceData.getApp_name().equalsIgnoreCase("OMAS")) {

        //sResidualChlorineValue = tietResidualChlorineRoutine.getText().toString();
        sResidualChlorineDescriptionOMAS = "";
        sResidualChlorineResultOMAS = "";

        //} else if (responseSourceData.getApp_name().equalsIgnoreCase("Routine")) {

        sResidualChlorineValue = tietResidualChlorineRoutine.getText().toString();

        //}

        String sSampleCollectorId = CGlobal.getInstance().getPersistentPreference(OldSampleCollection_Activity.this)
                .getString(Constants.PREFS_USER_USRUNIQUE_ID, "");

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

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (sScheme.equalsIgnoreCase("PROVIDE SCHEME NAME")) {
                sSubSchemeName = tietSchemeName.getText().toString();
            }
        }

        if (sSharedWith.equalsIgnoreCase("OTHERS")) {
            sSchool_Anganwadi_Name_SharedWith = tietOthersType.getText().toString();
        }

        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            sMobileModelNo = manufacturer + model;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(how_many_pipes_q_21)) {
            total_depth_q_22 = Integer.parseInt(how_many_pipes_q_21) * 20;
        }

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (responseSourceData.getName_of_special_drive().equalsIgnoreCase("ARSENIC TREND STATION")) {
            sIsItSpecialDrive = responseSourceData.getSpecial_drive();
            sSpecialDrive = responseSourceData.getName_of_special_drive();
            if (sAppType.equalsIgnoreCase("Routine") || sAppType.equalsIgnoreCase("OMAS")) {
                if (sChamberAvailable.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sChamberAvailable)) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
        }

        if (TextUtils.isEmpty(mCurrentPhotoPath)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
                    .setMessage("Please Capture Image")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sSpecialDrive.equalsIgnoreCase("ARSENIC TREND STATION")) {
            if (sAppType.equalsIgnoreCase("Routine") || sAppType.equalsIgnoreCase("OMAS")) {
                if (sChamberAvailable.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sChamberAvailable)) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
        }

        if (TextUtils.isEmpty(sample_bottle_number_q_16)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
                    .setMessage("Please Enter Sample Bottle Number")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (sIsItSpecialDrive.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sIsItSpecialDrive)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("PIPED WATER SUPPLY")) {
            if (TextUtils.isEmpty(sScheme) || sScheme.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
                        .setMessage("Please select Scheme")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        }

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
            if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            if (TextUtils.isEmpty(sSubSchemeName) || sSubSchemeName.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

        if (responseSourceData.getApp_name().equalsIgnoreCase("School")) {
            if (TextUtils.isEmpty(sSharedSource) || sSharedSource.equalsIgnoreCase("Choose")) {
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
        }

        if (sCollectingSample.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sCollectingSample)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
                    .setMessage("Please Select Collecting Sample")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
            if (sConditionOfSource.equalsIgnoreCase("Choose") || TextUtils.isEmpty(sConditionOfSource)) {
                new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

        if (responseSourceData.getWater_source_type().equalsIgnoreCase("PIPED WATER SUPPLY")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")
                || responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {

            if (responseSourceData.getApp_name().equalsIgnoreCase("OMAS")) {

                if (TextUtils.isEmpty(sResidualChlorineTested)) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

            } else if (responseSourceData.getApp_name().equalsIgnoreCase("Routine")) {

                if (TextUtils.isEmpty(sResidualChlorineTested) || sResidualChlorineTested.equalsIgnoreCase("Choose")) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
                        new AlertDialog.Builder(OldSampleCollection_Activity.this)
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

        if (TextUtils.isEmpty(lat)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
                    .setMessage("Please get Latitude")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (TextUtils.isEmpty(lng)) {
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
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
            new AlertDialog.Builder(OldSampleCollection_Activity.this)
                    .setMessage("Please enter valid PinCode")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        }

        if (!TextUtils.isEmpty(responseSourceData.getLocationDescription())) {
            oldLocation = responseSourceData.getLocationDescription();
        } else {
            oldLocation = responseSourceData.getDescriptionofthelocation();
        }

        DatabaseHandler databaseHandler = new DatabaseHandler(OldSampleCollection_Activity.this);
        if (responseSourceData.getWater_source_type().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT") ||
                responseSourceData.getWater_source_type().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME") ||
                responseSourceData.getWater_source_type().equalsIgnoreCase("OTHERS")) {
            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, responseSourceData.getSub_source_type()).commit();
        } else {
            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, responseSourceData.getWater_source_type()).commit();
        }
        String sTaskId = databaseHandler.getTaskId(responseSourceData.getBlockcode(), responseSourceData.getPancode(),
                responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code());

        String sFCID = CGlobal.getInstance().getPersistentPreference(OldSampleCollection_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");

        if (responseSourceData.getApp_name().equalsIgnoreCase("School")) {

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_SOURCE_SITE_SCHOOL, responseSourceData.getSource_site()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_UDISE_CODE_SCHOOL, responseSourceData.getIsnewlocation_School_UdiseCode()).commit();

            databaseHandler.addSchoolAppDataCollection(responseSourceData.getApp_name(), uTime, responseSourceData.getSource_site(), sIsItSpecialDrive,
                    sSpecialDrive, formattedDate, formattedTime,
                    responseSourceData.getType_of_locality(), responseSourceData.getWater_source_type(), responseSourceData.getDistrictcode(),
                    responseSourceData.getBlockcode(),
                    responseSourceData.getPancode(), responseSourceData.getVillagename(), responseSourceData.getHabitationname(),
                    responseSourceData.getIsnewlocation_School_UdiseCode(),
                    responseSourceData.getAnganwadi_name_q_12b(), responseSourceData.getAnganwadi_code_q_12c(), responseSourceData.getAnganwadi_sectorcode_q_12d(),
                    responseSourceData.getTown_name(), responseSourceData.getWard_no(), sSchemeCode,
                    responseSourceData.getZoneCategory(),
                    responseSourceData.getZone(), responseSourceData.getSourceselect(), responseSourceData.getStandpostsituated_q_13e(),
                    responseSourceData.getLocationDescription(), responseSourceData.getHandPumpCategory(), sample_bottle_number_q_16,
                    mCurrentPhotoPath, lat, lng, sAccuracy,
                    sCollectingSample, responseSourceData.getBig_dia_tube_well_no(), how_many_pipes_q_21,
                    String.valueOf(total_depth_q_22), mCurrentPhotoPath, uTime, versionName,
                    sMobileSerialNo, sMobileModelNo, sMobileIMEI, sResidualChlorineTestedValue,
                    sResidualChlorine, sResidualChlorineValue, sSharedSource, sSharedWith,
                    sSchool_Anganwadi_Name_SharedWith, sSampleCollectorId, responseSourceData.getSub_source_type(),
                    sSubSchemeName, sTaskId, responseSourceData.getMiD(), "", sFCID,
                    responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code(), sConditionOfSource,
                    responseSourceData.getApp_name(), sPinCode, responseSourceData.getOtherSchoolName(),
                    responseSourceData.getOtherAnganwadiName(), "0");

            databaseHandler.updateSourceForFacilitator("1", responseSourceData.getMiD());

            int lastIndexSampleCollectionId = databaseHandler.getLastSchoolAppDataCollectionId();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putInt(Constants.PREFS_LAST_INDEX_SCHOOL_SAMPLE_COLLECTION_ID_SCHOOL, lastIndexSampleCollectionId).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_MID, responseSourceData.getMiD()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putBoolean(Constants.PREFS_RESTART_FRAGMENT, true).commit();

            if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                    responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
                            .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(OldSampleCollection_Activity.this, SchoolInfo_Activity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAnsSchool_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            } else {
                Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAnsSchool_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }

        } else if (sAppType.equalsIgnoreCase("OMAS")) {

            if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                    responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                databaseHandler.addSampleCollection(sAppType, uTime, responseSourceData.getSource_site(), sIsItSpecialDrive,
                        sSpecialDrive, formattedDate, formattedTime, responseSourceData.getType_of_locality(),
                        responseSourceData.getWater_source_type(),
                        responseSourceData.getDistrictcode(), responseSourceData.getBlockcode(), responseSourceData.getPancode(),
                        responseSourceData.getVillagename(), responseSourceData.getHabitationname(), responseSourceData.getTown_name(),
                        responseSourceData.getWard_no(), responseSourceData.getHealth_facility_name(), sScheme, sSchemeCode,
                        responseSourceData.getZoneCategory(), responseSourceData.getZone(), responseSourceData.getSourceselect(),
                        "No", oldLocation,
                        "", responseSourceData.getHandPumpCategory(), sample_bottle_number_q_16,
                        mCurrentPhotoPath, lat, lng, sAccuracy, sCollectingSample, responseSourceData.getBig_dia_tube_well_no(),
                        how_many_pipes_q_21, String.valueOf(total_depth_q_22), sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                        sSampleCollectorId, responseSourceData.getSub_source_type(), sSubSchemeName, sConditionOfSource,
                        responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code(), sCollectingSample, sMobileModelNo, uTime,
                        sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS,
                        sTaskId, responseSourceData.getMiD(), "", sFCID, responseSourceData.getArsenic_mid(),
                        sChamberAvailable, sWaterLevel, responseSourceData.getApp_name(), sPinCode, "0");
            } else {

                databaseHandler.addSampleCollection(sAppType, uTime, responseSourceData.getSource_site(), sIsItSpecialDrive,
                        sSpecialDrive, formattedDate, formattedTime, responseSourceData.getType_of_locality(),
                        responseSourceData.getWater_source_type(),
                        responseSourceData.getDistrictcode(), responseSourceData.getBlockcode(), responseSourceData.getPancode(),
                        responseSourceData.getVillagename(), responseSourceData.getHabitationname(), responseSourceData.getTown_name(),
                        responseSourceData.getWard_no(), responseSourceData.getHealth_facility_name(), sScheme, sSchemeCode,
                        responseSourceData.getZoneCategory(), responseSourceData.getZone(), responseSourceData.getSourceselect(),
                        "", responseSourceData.getDescriptionofthelocation(),
                        responseSourceData.getLocationDescription(), responseSourceData.getHandPumpCategory(), sample_bottle_number_q_16,
                        mCurrentPhotoPath, lat, lng, sAccuracy, sCollectingSample, responseSourceData.getBig_dia_tube_well_no(),
                        how_many_pipes_q_21, String.valueOf(total_depth_q_22), sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                        sSampleCollectorId, responseSourceData.getSub_source_type(), sSubSchemeName, sConditionOfSource,
                        responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code(), sCollectingSample, sMobileModelNo, uTime,
                        sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, sResidualChlorineResultOMAS, sResidualChlorineDescriptionOMAS,
                        sTaskId, responseSourceData.getMiD(), "", sFCID, responseSourceData.getArsenic_mid(),
                        sChamberAvailable, sWaterLevel, responseSourceData.getApp_name(), sPinCode, "0");
            }
            databaseHandler.updateSourceForFacilitator("1", responseSourceData.getMiD());

            int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_MID, responseSourceData.getMiD()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putBoolean(Constants.PREFS_RESTART_FRAGMENT, true).commit();

            if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                    responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
                            .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(OldSampleCollection_Activity.this, DashBoard_Facilitator_Activity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            } else {
                Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAns_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }

        } else if (sAppType.equalsIgnoreCase("Routine")) {

            if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                    responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                databaseHandler.addSampleCollection(sAppType, uTime, responseSourceData.getSource_site(), sIsItSpecialDrive,
                        sSpecialDrive, formattedDate, formattedTime, responseSourceData.getType_of_locality(),
                        responseSourceData.getWater_source_type(),
                        responseSourceData.getDistrictcode(), responseSourceData.getBlockcode(), responseSourceData.getPancode(),
                        responseSourceData.getVillagename(), responseSourceData.getHabitationname(), responseSourceData.getTown_name(),
                        responseSourceData.getWard_no(), responseSourceData.getHealth_facility_name(), sScheme, sSchemeCode,
                        responseSourceData.getZoneCategory(), responseSourceData.getZone(), responseSourceData.getSourceselect(),
                        "No", oldLocation,
                        "", responseSourceData.getHandPumpCategory(), sample_bottle_number_q_16,
                        mCurrentPhotoPath, lat, lng, sAccuracy, sCollectingSample, responseSourceData.getBig_dia_tube_well_no(),
                        how_many_pipes_q_21, String.valueOf(total_depth_q_22), sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                        sSampleCollectorId, responseSourceData.getSub_source_type(), sSubSchemeName, sConditionOfSource,
                        responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code(), sCollectingSample, sMobileModelNo, uTime,
                        sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, "", "",
                        sTaskId, responseSourceData.getMiD(), "", sFCID, responseSourceData.getArsenic_mid(),
                        sChamberAvailable, sWaterLevel, responseSourceData.getApp_name(), sPinCode, "0");
            } else {

                databaseHandler.addSampleCollection(sAppType, uTime, responseSourceData.getSource_site(), sIsItSpecialDrive,
                        sSpecialDrive, formattedDate, formattedTime, responseSourceData.getType_of_locality(),
                        responseSourceData.getWater_source_type(),
                        responseSourceData.getDistrictcode(), responseSourceData.getBlockcode(), responseSourceData.getPancode(),
                        responseSourceData.getVillagename(), responseSourceData.getHabitationname(), responseSourceData.getTown_name(),
                        responseSourceData.getWard_no(), responseSourceData.getHealth_facility_name(), sScheme, sSchemeCode,
                        responseSourceData.getZoneCategory(), responseSourceData.getZone(), responseSourceData.getSourceselect(),
                        "", responseSourceData.getDescriptionofthelocation(),
                        responseSourceData.getLocationDescription(), responseSourceData.getHandPumpCategory(), sample_bottle_number_q_16,
                        mCurrentPhotoPath, lat, lng, sAccuracy, sCollectingSample, responseSourceData.getBig_dia_tube_well_no(),
                        how_many_pipes_q_21, String.valueOf(total_depth_q_22), sMobileIMEI, sMobileSerialNo, versionName, mCurrentPhotoPath,
                        sSampleCollectorId, responseSourceData.getSub_source_type(), sSubSchemeName, sConditionOfSource,
                        responseSourceData.getVillagecode(), responseSourceData.getHabitation_Code(), sCollectingSample, sMobileModelNo, uTime,
                        sResidualChlorineTestedValue, sResidualChlorine, sResidualChlorineValue, "", "",
                        sTaskId, responseSourceData.getMiD(), "", sFCID, responseSourceData.getArsenic_mid(),
                        sChamberAvailable, sWaterLevel, responseSourceData.getApp_name(), sPinCode, "0");
            }

            databaseHandler.updateSourceForFacilitator("1", responseSourceData.getMiD());

            int lastIndexSampleCollectionId = databaseHandler.getLastSampleCollectionId();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putInt(Constants.PREFS_LAST_INDEX_SAMPLE_COLLECTION_ID, lastIndexSampleCollectionId).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putString(Constants.PREFS_MID, responseSourceData.getMiD()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(OldSampleCollection_Activity.this)
                    .putBoolean(Constants.PREFS_RESTART_FRAGMENT, true).commit();

            if (responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL MARK II") ||
                    responseSourceData.getWater_source_type().equalsIgnoreCase("TUBE WELL ORDINARY")) {
                if (sConditionOfSource.equalsIgnoreCase("DEFUNCT")) {
                    new AlertDialog.Builder(OldSampleCollection_Activity.this)
                            .setMessage("You have selected the Source as 'DEFUNCT', you can't proceed further!!\n\nSuccessfully Saved")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(OldSampleCollection_Activity.this, DashBoard_Facilitator_Activity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    dialog.dismiss();
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAns_Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
            } else {
                Intent intent = new Intent(OldSampleCollection_Activity.this, ExsitingSanitarySurveyQuesAns_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }

        }
    }

    private double distance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(startLatitude);
        startPoint.setLongitude(startLongitude);
        Location endPoint = new Location("locationA");
        endPoint.setLatitude(endLatitude);
        endPoint.setLongitude(endLongitude);

        return startPoint.distanceTo(endPoint);
    }

}