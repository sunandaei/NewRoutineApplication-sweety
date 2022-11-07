package com.sunanda.newroutine.application.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONObject;

import java.util.Map;

public class CGlobal {

    public static final String TAG = "CGlobal: ";
    private static CGlobal instance;
    private static RequestQueue mVolleyRequestQueue;
    private SharedPreferences mPrefsVersionPersistent = null;
    private SharedPreferences.Editor mEditorVersionPersistent = null;
    final private static int REQUEST_LOCATION_LIB = 1001;

    private CGlobal() {
    }

    public static synchronized CGlobal getInstance() {
        if (instance == null) {
            instance = new CGlobal();
        }
        return instance;
    }

    public void addVolleyRequest(StringRequest postRequest, boolean clearBeforeQuery, Context context) {
        try {
            postRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            if (clearBeforeQuery) {
                getRequestQueue(context).cancelAll(
                        new RequestQueue.RequestFilter() {
                            @Override
                            public boolean apply(Request<?> request) {
                                return true;
                            }
                        });
            }
            getRequestQueue(context).add(postRequest);
        } catch (Exception e) {
            //SSLog.e(TAG, "mRequestQueue may not be initialized - ", e);
        }
    }

    public RequestQueue getRequestQueue(Context context) {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mVolleyRequestQueue == null) {
            // Instantiate the cache
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB
            // cap
            // Set up the network to use HttpURLConnection as the HTTP client.
            BasicNetwork network = new BasicNetwork(new HurlStack());
            // Instantiate the RequestQueue with the cache and network.
            mVolleyRequestQueue = new RequestQueue(cache, network);
            mVolleyRequestQueue.start();
        }
        return mVolleyRequestQueue;
    }

    public Map<String, String> checkParams(Map<String, String> map) {
        for (Map.Entry<String, String> pairs : map.entrySet()) {
            if (pairs.getValue() == null) {
                map.put(pairs.getKey(), "");
                Log.d(TAG, pairs.getKey());
            }
        }
        return map;
    }

    public SharedPreferences.Editor getPersistentPreferenceEditor(Context context) {
        if (mEditorVersionPersistent == null) {
            mEditorVersionPersistent = getPersistentPreference(context).edit();
        }
        return mEditorVersionPersistent;
    }

    public SharedPreferences getPersistentPreference(Context context) {
        if (mPrefsVersionPersistent == null) {
            mPrefsVersionPersistent = context.getApplicationContext()
                    .getSharedPreferences(Constants.PREFS_VERSION_PERSISTENT, Context.MODE_PRIVATE);
        }
        return mPrefsVersionPersistent;
    }

    public boolean isNullNotDefined(JSONObject jo, String jkey) {
        if (!jo.has(jkey)) {
            return true;
        }
        return jo.isNull(jkey);
    }

    public void turnGPSOn1(final Activity activity, GoogleApiClient googleApiClient) {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    activity, REQUEST_LOCATION_LIB);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.isConnectedOrConnecting());
    }

    public boolean checkConnected(Context context) {
        for (int i = 0; i < 3; i++) {
            try {
                if (isConnected(context)) {
                    return true;
                }
                Thread.sleep(500);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage().toString());
            }
        }
        return false;
    }

    public void turnGPSOn(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (!provider.contains("gps")) { //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }

    // turn off gps
    public void turnGPSOff(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            context.sendBroadcast(poke);
        }
    }

    public <T> void addToRequestQueue(Request<T> req, Context context) {
        req.setTag(TAG);
        getRequestQueue1(context).add(req);
    }

    private RequestQueue mRequestQueue;

    public RequestQueue getRequestQueue1(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

    public boolean checkGooglePlayServicesAvailable(Activity activity) {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (status == ConnectionResult.SUCCESS) {
            return true;
        }
        if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
            final Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(status, activity, 1);
            if (errorDialog != null) {
                errorDialog.show();
            }
        }
        return false;
    }

    public double getDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        Location startPoint = new Location("locationA");
        startPoint.setLatitude(startLatitude);
        startPoint.setLongitude(startLongitude);
        Location endPoint = new Location("locationA");
        endPoint.setLatitude(endLatitude);
        endPoint.setLongitude(endLongitude);

        return startPoint.distanceTo(endPoint) / 1000;
    }

}
