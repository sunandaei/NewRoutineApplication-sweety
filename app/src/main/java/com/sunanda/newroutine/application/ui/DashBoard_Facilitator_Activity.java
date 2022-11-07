package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.sunanda.newroutine.application.BuildConfig;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.MenuFacilitrator_Adapter;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.fragmnet.EditSample_Fragment;
import com.sunanda.newroutine.application.fragmnet.ExistingSample_Fragment;
import com.sunanda.newroutine.application.fragmnet.NewSample_Fragment;
import com.sunanda.newroutine.application.fragmnet.UploadSample_Fragment;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

public class DashBoard_Facilitator_Activity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar mtoolbar;
    TextView nav_header_appversion;
    RecyclerView recyclerView1;
    String[] names;
    Integer[] imageId1;
    boolean third_fragemt_selected = true;
    BottomNavigationView bottomNavigationView;
    FragmentManager mFragmentManager;
    Fragment mFragment, newSampleFragment, existingSampleFragment, editSampleFragment, uploadSampleFragment;
    ProgressDialog progressdialog;
    String jsonresponse = "";
    GoogleApiClient mGoogleApiClient;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    private LocationSettingsRequest mLocationSettingsRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_facilitator_activity);
        //getFacilitatorList();
        checkPlayServices();
        mGoogleApiClient = new GoogleApiClient.Builder(DashBoard_Facilitator_Activity.this)
                .addConnectionCallbacks(DashBoard_Facilitator_Activity.this)
                .addOnConnectionFailedListener(DashBoard_Facilitator_Activity.this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        init();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(DashBoard_Facilitator_Activity.this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(DashBoard_Facilitator_Activity.this, result,
                        101).show();
            }

            return false;
        }

        return true;
    }

    private void init() {

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        String name1 = CGlobal.getInstance().getPersistentPreference(DashBoard_Facilitator_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_NAME, "");
        if (!TextUtils.isEmpty(name1)) {
            setTitle(name1);
        } else {
            setTitle("Dashboard For Facilitator");
        }
        mtoolbar.setTitleTextColor(Color.WHITE);

        ActionBar actionBar = getSupportActionBar();
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //Expandable list in Navigation drawer
        setUpDrawer();

        TextView nav_header_main_tv_profile_name = findViewById(R.id.nav_header_main_tv_profile_name);
        TextView nav_header_appversion = (TextView) findViewById(R.id.nav_header_appversion);
        //nav_header_main_tv_profile_pic = (ImageView) findViewById(R.id.imageView);

        String versionName = BuildConfig.VERSION_NAME;
        nav_header_appversion.setText("V- " + versionName);

        bottomNavigationView = findViewById(R.id.navigation);

        mFragmentManager = getSupportFragmentManager();
        existingSampleFragment = ExistingSample_Fragment.newInstance();
        newSampleFragment = NewSample_Fragment.newInstance();
        uploadSampleFragment = UploadSample_Fragment.newInstance();
        editSampleFragment = EditSample_Fragment.newInstance();

        //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        //TextView textView = (TextView) bottomNavigationView.findViewById(R.id.navigation).findViewById(R.id.largeLabel);
        //textView.setTextSize(8);

        createFragments();

        // attaching bottom sheet behaviour - hide / show on scroll
        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            /*case R.id.one:
                                if (!(mFragment instanceof NewSample_Fragment)) {
                                    hideShowFragment(mFragment, frag1);
                                    mFragment = frag1;
                                }
                                third_fragemt_selected = false;
                                break;*/
                            case R.id.newSample:
                                if (!(mFragment instanceof NewSample_Fragment)) {
                                    hideShowFragment(mFragment, newSampleFragment);
                                    mFragment = newSampleFragment;
                                }
                                third_fragemt_selected = false;
                                break;
                            case R.id.existingSample:
                                if (!(mFragment instanceof ExistingSample_Fragment)) {
                                    hideShowFragment(mFragment, existingSampleFragment);
                                    mFragment = existingSampleFragment;
                                }
                                third_fragemt_selected = true;
                                break;
                            case R.id.editSample:
                                if (!(mFragment instanceof EditSample_Fragment)) {
                                    hideShowFragment(mFragment, editSampleFragment);
                                    mFragment = editSampleFragment;
                                }
                                third_fragemt_selected = false;
                                break;
                            case R.id.sampleUpload:
                                if (!(mFragment instanceof UploadSample_Fragment)) {
                                    hideShowFragment(mFragment, uploadSampleFragment);
                                    mFragment = uploadSampleFragment;
                                }
                                third_fragemt_selected = false;
                                break;
                        }
                        return true;
                    }
                });

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        hideShowFragment(mFragment, existingSampleFragment);
        mFragment = existingSampleFragment;

        LinearLayout llLogoutFacilitator = findViewById(R.id.llLogoutFacilitator);
        llLogoutFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Facilitator_Activity.this)
                        .putBoolean(Constants.PREFS_LOGIN_FLAG, false).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Facilitator_Activity.this)
                        .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, false).commit();
                Intent intent = new Intent(DashBoard_Facilitator_Activity.this, Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                drawer.closeDrawers();
                DatabaseHandler databaseHandler = new DatabaseHandler(DashBoard_Facilitator_Activity.this);
                databaseHandler.deleteAssignHabitationList();
                databaseHandler.deleteSourceForFacilitator();
                databaseHandler.deleteSourceSite();
                databaseHandler.deleteSourceType();
                databaseHandler.deleteChildSourceType();
                databaseHandler.deleteSpecialDrive();
                databaseHandler.deleteLab();
                databaseHandler.deletePipedWaterSupplyScheme();
                databaseHandler.deleteHealthFacility();
                databaseHandler.deleteTown();
                databaseHandler.deleteSurveyQuestion();
                databaseHandler.deleteRoster();
                databaseHandler.deleteArsenic();
                finish();
            }
        });

        LinearLayout llHomeFacilitator = findViewById(R.id.llHomeFacilitator);
        llHomeFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRefersh();
                drawer.closeDrawers();
            }
        });

        String name = CGlobal.getInstance().getPersistentPreference(DashBoard_Facilitator_Activity.this)
                .getString(Constants.PREFS_FACILITATOR_NAME, "");
        nav_header_main_tv_profile_name.setText(name);
    }

    private void setUpDrawer() {

        recyclerView1 = findViewById(R.id.recycler);

        names = new String[]{
                "Sync",
                "Recycle"
        };
        imageId1 = new Integer[]{
                R.drawable.ic_sync_new,
                R.drawable.ic_recycle,
        };

        RecyclerView.LayoutManager firstLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(firstLayoutManager);
        MenuFacilitrator_Adapter firstAdapter = new MenuFacilitrator_Adapter(this, names, imageId1, drawer);
        recyclerView1.setAdapter(firstAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CGlobal.getInstance().turnGPSOn1(DashBoard_Facilitator_Activity.this, mGoogleApiClient);

        boolean isFragment = CGlobal.getInstance().getPersistentPreference(DashBoard_Facilitator_Activity.this)
                .getBoolean(Constants.PREFS_RESTART_FRAGMENT, false);
        if (isFragment) {
            CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Facilitator_Activity.this)
                    .putBoolean(Constants.PREFS_RESTART_FRAGMENT, false).commit();
            mFragmentManager = getSupportFragmentManager();
            existingSampleFragment = ExistingSample_Fragment.newInstance();
            newSampleFragment = NewSample_Fragment.newInstance();
            uploadSampleFragment = UploadSample_Fragment.newInstance();
            editSampleFragment = EditSample_Fragment.newInstance();
            createFragments();
        }

        boolean isFragment_Edit = CGlobal.getInstance().getPersistentPreference(DashBoard_Facilitator_Activity.this)
                .getBoolean(Constants.PREFS_RESTART_FRAGMENT_EDIT, false);
        if (isFragment_Edit) {
            CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Facilitator_Activity.this)
                    .putBoolean(Constants.PREFS_RESTART_FRAGMENT_EDIT, false).commit();
            mFragmentManager = getSupportFragmentManager();
            existingSampleFragment = ExistingSample_Fragment.newInstance();
            newSampleFragment = NewSample_Fragment.newInstance();
            uploadSampleFragment = UploadSample_Fragment.newInstance();
            editSampleFragment = EditSample_Fragment.newInstance();

            addHideFragment(existingSampleFragment);
            addHideFragment(uploadSampleFragment);
            addHideFragment(newSampleFragment);
            mFragmentManager.beginTransaction().add(R.id.frame_layout, editSampleFragment).commit();
            mFragment = editSampleFragment;
        }

    }

    public void setRefersh() {
        mFragmentManager = getSupportFragmentManager();
        existingSampleFragment = ExistingSample_Fragment.newInstance();
        newSampleFragment = NewSample_Fragment.newInstance();
        uploadSampleFragment = UploadSample_Fragment.newInstance();
        editSampleFragment = EditSample_Fragment.newInstance();
        createFragments();
    }

    public void setRefersh2() {
        mFragmentManager = getSupportFragmentManager();
        existingSampleFragment = ExistingSample_Fragment.newInstance();
        newSampleFragment = NewSample_Fragment.newInstance();
        uploadSampleFragment = UploadSample_Fragment.newInstance();
        editSampleFragment = EditSample_Fragment.newInstance();

        addHideFragment(existingSampleFragment);
        addHideFragment(editSampleFragment);
        addHideFragment(uploadSampleFragment);
        mFragmentManager.beginTransaction().add(R.id.frame_layout, newSampleFragment).commit();
        mFragment = newSampleFragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void addHideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.frame_layout, fragment).hide(fragment).commit();
    }

    //Method to hide and show the fragment you need. It is called in the BottomBar click listener.
    private void hideShowFragment(Fragment hide, Fragment show) {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit();
    }

    //Add all the fragments that need to be added and hidden. Also, add the one that is supposed to be the starting one, that one is not hidden.
    private void createFragments() {
        //addHideFragment(frag1);
        addHideFragment(newSampleFragment);
        addHideFragment(editSampleFragment);
        addHideFragment(uploadSampleFragment);
        mFragmentManager.beginTransaction().add(R.id.frame_layout, existingSampleFragment).commit();
        mFragment = existingSampleFragment;
    }

    @Override
    public void onBackPressed() {
        if (third_fragemt_selected) {
            new AlertDialog.Builder(this)
                    //.setIcon(R.drawable.ice)
                    .setMessage("Are you sure to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        // do something when the button is clicked
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                        }
                    }).show();
        } else {
            third_fragemt_selected = true;
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            hideShowFragment(mFragment, existingSampleFragment);
            mFragment = existingSampleFragment;
        }
    }

    /*private void getFacilitatorList() {

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        boolean isConnected = CGlobal.getInstance().isConnected(this);
        if (isConnected) {
            try {
                progressdialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .build();

            String sLabCode = CGlobal.getInstance().getPersistentPreference(this)
                    .getString(Constants.PREFS_USER_LAB_CODE, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getFacilitatorListOnLab(sLabCode);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    Log.i("Responsestring", response.body());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.i("onSuccess", response.body());
                            jsonresponse = response.body();
                            //getFacilitatorListResponse(jsonresponse);
                            *//* ############# Initialize Fragments  ############*//*

                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(DashBoard_Facilitator_Activity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(DashBoard_Facilitator_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Please check your Internet connection. Please try again")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }*/

   /* @Override
    public void addNewOne(Fragment frag) {
        hideShowFragment(uploadSampleFragment, editSampleFragment);
        mFragment = editSampleFragment;
        third_fragemt_selected = false;
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        //Toast.makeText(getApplicationContext(), "dgsd", Toast.LENGTH_SHORT).show();
    }*/

    private LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(DashBoard_Facilitator_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashBoard_Facilitator_Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DashBoard_Facilitator_Activity.this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public static Location mCurrentLocation;
    boolean isZoom = false;

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        if (mCurrentLocation != null) {
            if (!isZoom) {
                isZoom = true;
                mFragmentManager = getSupportFragmentManager();
                existingSampleFragment = ExistingSample_Fragment.newInstance();
                newSampleFragment = NewSample_Fragment.newInstance();
                uploadSampleFragment = UploadSample_Fragment.newInstance();
                editSampleFragment = EditSample_Fragment.newInstance();

                //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
                //TextView textView = (TextView) bottomNavigationView.findViewById(R.id.navigation).findViewById(R.id.largeLabel);
                //textView.setTextSize(8);

                createFragments();

                // attaching bottom sheet behaviour - hide / show on scroll
                //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
                //layoutParams.setBehavior(new BottomNavigationBehavior());

                bottomNavigationView.setOnNavigationItemSelectedListener
                        (new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                switch (item.getItemId()) {
                            /*case R.id.one:
                                if (!(mFragment instanceof NewSample_Fragment)) {
                                    hideShowFragment(mFragment, frag1);
                                    mFragment = frag1;
                                }
                                third_fragemt_selected = false;
                                break;*/
                                    case R.id.newSample:
                                        if (!(mFragment instanceof NewSample_Fragment)) {
                                            hideShowFragment(mFragment, newSampleFragment);
                                            mFragment = newSampleFragment;
                                        }
                                        third_fragemt_selected = false;
                                        break;
                                    case R.id.existingSample:
                                        if (!(mFragment instanceof ExistingSample_Fragment)) {
                                            hideShowFragment(mFragment, existingSampleFragment);
                                            mFragment = existingSampleFragment;
                                        }
                                        third_fragemt_selected = true;
                                        break;
                                    case R.id.editSample:
                                        if (!(mFragment instanceof EditSample_Fragment)) {
                                            hideShowFragment(mFragment, editSampleFragment);
                                            mFragment = editSampleFragment;
                                        }
                                        third_fragemt_selected = false;
                                        break;
                                    case R.id.sampleUpload:
                                        if (!(mFragment instanceof UploadSample_Fragment)) {
                                            hideShowFragment(mFragment, uploadSampleFragment);
                                            mFragment = uploadSampleFragment;
                                        }
                                        third_fragemt_selected = false;
                                        break;
                                }
                                return true;
                            }
                        });

                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                hideShowFragment(mFragment, existingSampleFragment);
                mFragment = existingSampleFragment;
            }
        }
    }

    private static final int REQ_CODE_VERSION_UPDATE = 33;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != RESULT_OK) {
                unregisterInstallStateUpdListener();
            }
        }
    }

    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(DashBoard_Facilitator_Activity.this);
        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    private void popupSnackbarForCompleteUpdateAndUnregister() {
        /*Snackbar snackbar = Snackbar.make(findViewById(R.id.ll1), "Your Application update is available!", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", view -> {
            if (appUpdateManager != null){
                appUpdateManager.completeUpdate();
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();*/

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashBoard_Facilitator_Activity.this);
        alertDialogBuilder.setTitle(getString(R.string.app_name));
        alertDialogBuilder.setMessage("Your Application update is available");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("UPDATE NOW", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                appUpdateManager.completeUpdate();
            }
        });
        alertDialogBuilder.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }
}