package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sunanda.newroutine.application.somenath.helper.CustomListViewDialog;
import com.sunanda.newroutine.application.somenath.myadapter.DataAdapter;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.SlotAdapter;
import com.sunanda.newroutine.application.util.LatitudeLongitude;
import com.sunanda.newroutine.application.util.TaskLoadedCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        /*LocationListener,*/ GoogleMap.OnMarkerClickListener, TaskLoadedCallback,
        DataAdapter.RecyclerViewItemClickListener {

    private static final String TAG = "!!MapsActivity";
    private GoogleMap gm;
    ArrayList<LatitudeLongitude> latlong, newLatlong, tempLatlong;
    private MarkerOptions place1, place2;
    private Polyline currentPolyline;

    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> slot = new ArrayList<>();
    private TextView selecttime;

    private String baseLat = "", baseLong = "", TITLE = "";
    private int POSITION;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((TextView) findViewById(R.id.cat_title)).setText("MAP Wise Allocation");

        selecttime = findViewById(R.id.selecttime);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (!checkPermission()) {
            requestPermission();
        } /*else {
            Snackbar.make(findViewById(android.R.id.content), "Permission already granted.", Snackbar.LENGTH_LONG).show();
        }*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1, 10, locationListener);*/

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER

        slot = new ArrayList<>();
        slot.add("ALL");
        slot.add("1 KM");
        slot.add("2 KMS");
        slot.add("3 KMS");
        slot.add("4 KMS");
        slot.add("5 KMS");

        SlotAdapter slotAdapter = new SlotAdapter(slot, this, selecttime, new BtnSelect() {
            @Override
            public void onBtnSelectValue(int position) {
                //Toast.makeText(MapsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
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
                newLatlong = new ArrayList<LatitudeLongitude>();
                for (int i = 0; i < tempLatlong.size() - 1; i++) {
                    if (getDistance(tempLatlong.get(i).getLatitude(), tempLatlong.get(i).getLongitude()) < value) {
                        newLatlong.add(tempLatlong.get(i));
                    }
                }
                newLatlong.add(tempLatlong.get(tempLatlong.size() - 1));
                if (position == 0)
                    latlong = tempLatlong;
                else /*if(position==1 to ...)*/
                    latlong = newLatlong;

                gm.clear();
                getListAPIRequest();
            }
        });
        recyclerView.setAdapter(slotAdapter);

        // Fetching initial values
        latlong = new ArrayList<>();
        tempLatlong = new ArrayList<>();
        LatitudeLongitude latitudeLongitude;
        JSONArray aFacilitator;
        try {
            aFacilitator = new JSONArray(getIntent().getStringExtra("LISTS"));
            items = new String[aFacilitator.length()];
            for (int i = 0; i < aFacilitator.length(); i++) {
                JSONObject oFacilitator = aFacilitator.getJSONObject(i);
                items[i] = oFacilitator.getString("source_details");
                latitudeLongitude = new LatitudeLongitude();
                latitudeLongitude.setLatitude(oFacilitator.getString("latitude"));
                latitudeLongitude.setLongitude(oFacilitator.getString("longitude"));
                latitudeLongitude.setTitle(oFacilitator.getString("lab_Name"));
                latitudeLongitude.setLocation(oFacilitator.getString("source_details"));
                latlong.add(latitudeLongitude);
            }
            tempLatlong = latlong;
            baseLat = latlong.get(latlong.size() - 1).getLatitude();
            baseLong = latlong.get(latlong.size() - 1).getLongitude();
            TITLE = latlong.get(latlong.size() - 1).getTitle();
            POSITION = latlong.size() - 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface BtnSelect {
        void onBtnSelectValue(int position);
    }


    CustomListViewDialog customDialog;

    public void clickHere(View view) {

        //DataAdapter dataAdapter = new DataAdapter(items, this);
        //customDialog = new CustomListViewDialog(MapsActivity.this, dataAdapter);

        //customDialog.show();
        //customDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void clickOnItem(String data) {

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        /*if (customDialog != null) {
            customDialog.dismiss();
        }*/
    }

    private void getListAPIRequest() {

        for (int i = 0; i < latlong.size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(latlong.get(i).getLatitude()),
                            Double.parseDouble(latlong.get(i).getLongitude())))
                    .anchor(0.5f, 0.5f)
                    .title(latlong.get(i).getTitle())
                    .snippet(latlong.get(i).getLocation())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder));
            Marker marker = gm.addMarker(markerOptions);
            marker.setTag(latlong.get(i));

            place1 = markerOptions;
        }

        moveCameraToBase();

        //gm.setOnMarkerClickListener(MapsActivity.this);
        //place2 = markerOptions;
    }

    private void moveCameraToBase() {

        gm.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(Double.parseDouble(baseLat),
                        Double.parseDouble(baseLong)), 14.0f));
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(Double.parseDouble(baseLat),
                        Double.parseDouble(baseLong)))
                .anchor(0.5f, 0.5f)
                .title(TITLE)
                .snippet(TITLE)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        Marker marker = gm.addMarker(markerOptions);
        marker.setTag(POSITION);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            gm = googleMap;
        }
        // Add a marker in Sydney and move the camera
       /* LatLng l = new LatLng(Double.parseDouble(latlong.get(0).getLatitude()),
                Double.parseDouble(latlong.get(0).getLongitude()));
        gm.addMarker(new MarkerOptions().position(l).title("Marker in Sydney"));
        gm.moveCamera(CameraUpdateFactory.newLatLng(l));*/
        getListAPIRequest();
        gm.setOnMarkerClickListener(MapsActivity.this);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        return String.format("https://maps.googleapis.com/maps/api/directions/%s?%s&key=%s", output, parameters, getString(R.string.google_maps_key));
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = gm.addPolyline((PolylineOptions) values[0]);
    }


    /*@Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        gm.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .anchor(0.5f, 0.5f)
                .title("Current Location")
                //.snippet(latlong.get(i).getLocation())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
        Marker marker = gm.addMarker(markerOptions);
        //marker.setTag();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
*/

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            Toast.makeText(getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v(TAG, latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: " + cityName;
            Toast.makeText(MapsActivity.this, s, Toast.LENGTH_SHORT).show();

            gm.moveCamera(CameraUpdateFactory.newLatLngZoom
                    (new LatLng(loc.getLatitude(), loc.getLongitude()), 12.0f));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                    .anchor(0.5f, 0.5f)
                    .title(cityName)
                    .snippet("")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            Marker marker = gm.addMarker(markerOptions);
            //marker.setTag(latlong.get(0));

            //gm.setOnMarkerClickListener(MapsActivity.this);
            place2 = markerOptions;

            /*new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(),
                    place2.getPosition(), "cycling"), "cycling");*/
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            Toast.makeText(this, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(android.R.id.content), "Permission Granted, Now you can access location data.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Permission Denied, You cannot access location data.", Snackbar.LENGTH_LONG).show();
            }
        }
    }


    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    public float distance(float lat_a, float lng_a, float lat_b, float lng_b) {

        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return (float) ((distance * meterConversion) / 1000);
    }

    private double getDistance(String lat1, String lon1) {

        Location startPoint = new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(lat1));
        startPoint.setLongitude(Double.parseDouble(lon1));
        Location endPoint = new Location("locationB");
        endPoint.setLatitude(Double.parseDouble(tempLatlong.get(latlong.size() - 1).getLatitude()));
        endPoint.setLongitude(Double.parseDouble(tempLatlong.get(latlong.size() - 1).getLongitude()));

        //Log.d("DISTANCE", "" + startPoint.distanceTo(endPoint) / 1000);

        return startPoint.distanceTo(endPoint) / 1000;
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        //ShowDialog(marker.getTitle(), marker.getSnippet());

        new AlertDialog.Builder(this)
                .setTitle("Modify Starting Point?")
                .setMessage("Are you sure you want to modify the starting point?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        baseLat = String.valueOf(marker.getPosition().latitude);
                        baseLong = String.valueOf(marker.getPosition().longitude);
                        TITLE = marker.getTitle();
                        LatitudeLongitude x = new LatitudeLongitude();
                        x.setLatitude(baseLat);
                        x.setLongitude(baseLong);
                        POSITION = tempLatlong.indexOf(x);
                        gm.clear();
                        getListAPIRequest();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getListAPIRequest();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();

        return false;
    }

    @SuppressLint("SetTextI18n")
    public void ShowDialog(final String title, String snippet) {

        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_layout);

        //dialog.setTitle("Custom Dialog");

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER/* | Gravity.TOP*/);

        //lp.x = 100; // The new position of the X coordinates
        ///lp.y = 100; // The new position of the Y coordinates
        //lp.width = 500; // Width
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Height
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.alpha = 0.9f; // Transparency

        TextView text;
        Button proceed, cancel;
        text = dialog.findViewById(R.id.text);
        proceed = dialog.findViewById(R.id.proceed);
        cancel = dialog.findViewById(R.id.cancel);

        text.setText(snippet + " (" + title + ")");

        // The system will call this function when the Window Attributes when the change, can be called directly by application of above the window parameters change, also can use setAttributes
        // dialog.onWindowAttributesChanged(lp);
        dialogWindow.setAttributes(lp);
        dialog.show();

        proceed.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //Toast.makeText(MapActivity.this, dataCategoryOffer.getCategory_name() + "", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MapsActivity.this, "Working", Toast.LENGTH_SHORT).show();
                float f = distance(Float.parseFloat(latlong.get(0).getLatitude()), Float.parseFloat(latlong.get(0).getLongitude()),
                        Float.parseFloat(latlong.get(1).getLatitude()), Float.parseFloat(latlong.get(1).getLongitude()));
                Toast.makeText(MapsActivity.this, "Distance : " + String.format("%.2f", f) + " KM",
                        Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
            finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}