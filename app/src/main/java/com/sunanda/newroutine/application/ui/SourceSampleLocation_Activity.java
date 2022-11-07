package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.lang.reflect.Type;

public class SourceSampleLocation_Activity extends NavigationBar_Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap googleMap;
    private LatLng mCenterLatLong;
    Location mLastLocation;
    boolean isZoom = false;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    @SuppressLint("RestrictedApi")
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.INTERVAL);
        mLocationRequest.setFastestInterval(Constants.FIRST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_sample_location_activity);
        create("Map");
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(SourceSampleLocation_Activity.this)
                .addConnectionCallbacks(SourceSampleLocation_Activity.this)
                .addOnConnectionFailedListener(SourceSampleLocation_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        boolean isConnected = CGlobal.getInstance().isConnected(SourceSampleLocation_Activity.this);
        if (!isConnected) {
            new AlertDialog.Builder(SourceSampleLocation_Activity.this)
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

    CommonModel commonModel;
    private Polyline currentPolyline;

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        CGlobal.getInstance().turnGPSOn1(SourceSampleLocation_Activity.this, mGoogleApiClient);
        if (googleMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(SourceSampleLocation_Activity.this);
        }
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
        if (!isZoom) {
            LatLng latLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(14f).tilt(70).build();

            if (ActivityCompat.checkSelfPermission(SourceSampleLocation_Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(SourceSampleLocation_Activity.this, Manifest.permission.
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

            String json = CGlobal.getInstance().getPersistentPreference(SourceSampleLocation_Activity.this)
                    .getString(Constants.PREFS_SOURCE_SAMPLE_DETAILS, "");
            commonModel = new CommonModel();
            Type listType = new TypeToken<CommonModel>() {
            }.getType();
            commonModel = new GsonBuilder().create().fromJson(json, listType);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(commonModel.getLat(),
                            commonModel.getLng()))
                    .anchor(0.5f, 0.5f)
                    .title(commonModel.getExisting_loc())
                    .snippet(commonModel.getLab_Name())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
            googleMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(SourceSampleLocation_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SourceSampleLocation_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, SourceSampleLocation_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
