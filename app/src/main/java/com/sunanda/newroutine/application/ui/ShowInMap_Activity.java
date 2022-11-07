package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.SlotAdapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowInMap_Activity extends NavigationBar_Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    @SuppressLint("RestrictedApi")
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.INTERVAL);
        mLocationRequest.setFastestInterval(Constants.FIRST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private GoogleMap googleMap;
    private LatLng mCenterLatLong;
    Location mLastLocation;
    boolean isZoom = false;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    DatabaseHandler databaseHandler;
    private ArrayList<String> slot = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_in_map_activity);
        create("Map");
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(ShowInMap_Activity.this)
                .addConnectionCallbacks(ShowInMap_Activity.this)
                .addOnConnectionFailedListener(ShowInMap_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        databaseHandler = new DatabaseHandler(ShowInMap_Activity.this);

        init();

        boolean isConnected = CGlobal.getInstance().isConnected(ShowInMap_Activity.this);
        if (!isConnected) {
            new AlertDialog.Builder(ShowInMap_Activity.this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    ProgressBar loading_indicator;
    ArrayList<CommonModel> modelArrayList;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<CommonModel> newLatlong, latlong;
    private TextView selecttime;
    private String baseLat = "", baseLong = "", TITLE = "";
    private int POSITION;
    MarkerOptions place1;

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loading_indicator = findViewById(R.id.loading_indicator);
        selecttime = findViewById(R.id.selecttime);

        latlong = new ArrayList<>();

        slot = new ArrayList<>();
        slot.add("ALL");
        slot.add("1 KM");
        slot.add("2 KMS");
        slot.add("3 KMS");
        slot.add("4 KMS");
        slot.add("5 KMS");

        SlotAdapter slotAdapter = new SlotAdapter(slot, this, selecttime, new MapsActivity.BtnSelect() {
            @Override
            public void onBtnSelectValue(int position) {
                double value = 0.0;
                switch (position) {
                    case 0:
                        value = 0.0f;
                        break;
                    case 1:
                        value = 1.0f;
                        break;
                    case 2:
                        value = 30.0f;
                        break;
                    case 3:
                        value = 33.0f;
                        break;
                    case 4:
                        value = 36.0f;
                        break;
                    case 5:
                        value = 38.0f;
                        break;
                }

                /*newLatlong = new ArrayList<>();
                for (int i = 0; i < modelArrayList.size() - 1; i++) {
                    if (getDistance(modelArrayList.get(i).getLat(), modelArrayList.get(i).getLng()) < value) {
                        newLatlong.add(modelArrayList.get(i));
                    }
                }
                newLatlong.add(modelArrayList.get(modelArrayList.size() - 1));
                if (position == 0)
                    latlong = modelArrayList;
                else *//*if(position==1 to ...)*//*
                    latlong = newLatlong;


                googleMap.clear();
                getListAPIRequest();*/

            }
        });
        recyclerView.setAdapter(slotAdapter);
    }

    /*private void getListAPIRequest() {

        for (int i = 0; i < latlong.size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latlong.get(i).getLat(),
                            latlong.get(i).getLng()))
                    .anchor(0.5f, 0.5f)
                    .title(latlong.get(i).getLab_Name())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
            Marker marker = googleMap.addMarker(markerOptions);
            marker.setTag(latlong.get(i));

            place1 = markerOptions;
        }

        moveCameraToBase();

        //gm.setOnMarkerClickListener(MapsActivity.this);
        //place2 = markerOptions;
    }

    private void moveCameraToBase() {

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(Double.parseDouble(baseLat),
                        Double.parseDouble(baseLong)), 14.0f));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(Double.parseDouble(baseLat),
                        Double.parseDouble(baseLong)))
                .anchor(0.5f, 0.5f)
                .title(TITLE)
                .snippet(TITLE)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(POSITION);
    }


    private double getDistance(double lat1, double lon1) {

        Location startPoint = new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(lon1);
        Location endPoint = new Location("locationB");
        endPoint.setLatitude(modelArrayList.get(latlong.size() - 1).getLat());
        endPoint.setLongitude(modelArrayList.get(latlong.size() - 1).getLng());

        //Log.d("DISTANCE", "" + startPoint.distanceTo(endPoint) / 1000);

        return startPoint.distanceTo(endPoint) / 1000;
    }*/

    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(ShowInMap_Activity.this, mGoogleApiClient);
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(ShowInMap_Activity.this);
        }

        loading_indicator.setVisibility(View.GONE);

        /*latlong = new ArrayList<>();
        modelArrayList = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(ShowInMap_Activity.this);
        modelArrayList = databaseHandler.getExistingSource();
        latlong = modelArrayList;
        baseLat = String.valueOf(latlong.get(latlong.size() - 1).getLat());
        baseLong = String.valueOf(latlong.get(latlong.size() - 1).getLng());
        TITLE = latlong.get(latlong.size() - 1).getLab_Name();
        POSITION = latlong.size() - 1;*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mLastLocation == null) {
            new AlertDialog.Builder(ShowInMap_Activity.this)
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
        if (!isZoom) {
            LatLng latLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(14f).tilt(70).build();

            if (ActivityCompat.checkSelfPermission(ShowInMap_Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(ShowInMap_Activity.this, Manifest.permission.
                            ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            MarkerOptions markerOptions1 = new MarkerOptions()
                    .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                    .anchor(0.5f, 0.5f)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            googleMap.addMarker(markerOptions1);

            googleMap.setMyLocationEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
            isZoom = true;

            modelArrayList = new ArrayList<>();
            DatabaseHandler databaseHandler = new DatabaseHandler(ShowInMap_Activity.this);
            //modelArrayList = databaseHandler.getSourceForFacilitator(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            for (int i = 0; i < modelArrayList.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(modelArrayList.get(i).getLat(),
                                modelArrayList.get(i).getLng()))
                        .anchor(0.5f, 0.5f)
                        .title(modelArrayList.get(i).getExisting_loc())
                        .snippet(modelArrayList.get(i).getLab_Name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
                googleMap.addMarker(markerOptions);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(ShowInMap_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowInMap_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, ShowInMap_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void takeScreenshot() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + timeStamp + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Exception e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
