package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.RecyclerViewAdapter;
import com.sunanda.newroutine.application.fragmnet.AssignTask_NA;
import com.sunanda.newroutine.application.fragmnet.CreateFacilitator;
import com.sunanda.newroutine.application.fragmnet.FCWOrkAreaFragment;
import com.sunanda.newroutine.application.fragmnet.FacilitatorLists;
import com.sunanda.newroutine.application.fragmnet.PendingTasks;
import com.sunanda.newroutine.application.fragmnet.WorkStatusFragment;
import com.sunanda.newroutine.application.ui.Login_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;
import com.sunanda.newroutine.application.util.PostInterface;
import com.sunanda.newroutine.application.util.SessionManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DashBoard_Activity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, AssignTask_NA.AddFacilatorEvent {

    NavigationView navigationView;
    DrawerLayout drawer;
    private Toolbar mtoolbar;
    private RecyclerView recyclerView1;
    String[] names;
    Integer[] imageId1;

    boolean third_fragemt_selected = true;
    private BottomNavigationView bottomNavigationView;
    FragmentManager mFragmentManager;
    Fragment mFragment;
    Fragment frag1;
    Fragment frag2;
    Fragment frag5;
    Fragment frag3;
    Fragment frag4;

    ProgressDialog progressdialog;
    String jsonresponse = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        checkPlayServices();
        getFacilitatorList();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        getFacilitatorList();
    }*/

    // The below code is required when facilitator's record is edited
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            String message = data.getStringExtra("MESSAGE");
            if (message.equalsIgnoreCase("1"))
                getFacilitatorList();
        }
    }

    private void checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(DashBoard_Activity.this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(DashBoard_Activity.this, result,
                        101).show();
            }
        }
    }

    private void init() {

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        setTitle("Dashboard For Laboratory Personnel");
        mtoolbar.setTitleTextColor(Color.WHITE);

        /*ActionBar actionBar = getSupportActionBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //actionBar.setDisplayHomeAsUpEnabled(true);


        // Expandable list in Navigation drawer
        setUpDrawer();

        TextView nav_header_main_tv_profile_name = (TextView) findViewById(R.id.nav_header_main_tv_profile_name);
        //TextView nav_header_main_tv_profile_email = (TextView) findViewById(R.id.nav_header_main_tv_profile_email);
        //nav_header_main_tv_profile_pic = (ImageView) findViewById(R.id.imageView);*/


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        mFragmentManager = getSupportFragmentManager();
        frag1 = FCWOrkAreaFragment.newInstance();
        frag2 = CreateFacilitator.newInstance();
        frag3 = FacilitatorLists.newInstance(jsonresponse);
        frag4 = PendingTasks.newInstance();
        frag5 = WorkStatusFragment.newInstance();

        //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        //TextView textView = (TextView) bottomNavigationView.findViewById(R.id.navigation).findViewById(R.id.largeLabel);
        //textView.setTextSize(8);

        try {
            createFragments();
        } catch (Exception ignore) {
        }

        // attaching bottom sheet behaviour - hide / show on scroll
        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                if (!(mFragment instanceof FCWOrkAreaFragment)) {
                                    hideShowFragment(mFragment, frag1);
                                    mFragment = frag1;
                                }
                                third_fragemt_selected = false;
                                break;
                            case R.id.two:
                                if (!(mFragment instanceof CreateFacilitator)) {
                                    hideShowFragment(mFragment, frag2);
                                    mFragment = frag2;
                                }
                                third_fragemt_selected = false;
                                break;
                            case R.id.three:
                                if (!(mFragment instanceof FacilitatorLists)) {
                                    hideShowFragment(mFragment, frag3);
                                    mFragment = frag3;
                                }
                                third_fragemt_selected = true;
                                break;
                            case R.id.four:
                                if (!(mFragment instanceof PendingTasks)) {
                                    hideShowFragment(mFragment, frag4);
                                    mFragment = frag4;
                                }
                                third_fragemt_selected = false;
                                break;
                            case R.id.five:
                                if (!(mFragment instanceof WorkStatusFragment)) {
                                    hideShowFragment(mFragment, frag5);
                                    mFragment = frag5;
                                }
                                third_fragemt_selected = false;
                                break;
                        }
                        return true;
                    }
                });

        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        hideShowFragment(mFragment, frag3);
        mFragment = frag3;


        /*LinearLayout llLogout = findViewById(R.id.llLogout);
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Activity.this)
                        .putBoolean(Constants.PREFS_LOGIN_FLAG, false).commit();
                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Activity.this)
                        .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, false).commit();
                Intent intent = new Intent(DashBoard_Activity.this, Login_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
            }
        });

        LinearLayout llHome = findViewById(R.id.llHome);
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard_Activity.this, DashBoard_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });*/

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DashBoard_Activity.this)
                        //.setIcon(R.drawable.ice)
                        .setMessage("Are you sure to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Activity.this)
                                        .putBoolean(Constants.PREFS_LOGIN_FLAG, false).commit();
                                CGlobal.getInstance().getPersistentPreferenceEditor(DashBoard_Activity.this)
                                        .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, false).commit();

                                new SessionManager(DashBoard_Activity.this).destroySession();
                                Intent intent = new Intent(DashBoard_Activity.this, Login_Activity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        }).show();
            }
        });

        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoard_Activity.this, "Hi", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(DashBoard_Activity.this);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                View mView = inflater.inflate(R.layout.dialog_lab_details, null);
                final TextView labname = (TextView) mView.findViewById(R.id.labname);
                final TextView labcode = (TextView) mView.findViewById(R.id.labcode);
                final TextView labid = (TextView) mView.findViewById(R.id.labid);
                labname.setText("• Lab Name : " + CGlobal.getInstance().getPersistentPreference(DashBoard_Activity.this)
                        .getString(Constants.PREFS_USER_LAB_NAME, ""));
                labcode.setText("• Lab Code : " + CGlobal.getInstance().getPersistentPreference(DashBoard_Activity.this)
                        .getString(Constants.PREFS_USER_LAB_CODE, ""));
                labid.setText("• Lab Id : " + CGlobal.getInstance().getPersistentPreference(DashBoard_Activity.this)
                        .getString(Constants.PREFS_USER_LAB_ID, ""));

                /*builder.setView(mView)
                        // Add action buttons
                        .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (TextUtils.isEmpty(username.getText().toString())) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Please Enter User Name", Toast.LENGTH_SHORT).show();
                                } else if (TextUtils.isEmpty(newmobile.getText().toString())) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                                } else if (newmobile.getText().toString().length() != 10) {
                                    Toast.makeText(FacilitatorAssignedList_Activity.this,
                                            "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                                } else {
                                    dialog.dismiss();
                                    EditMyFacilator(username.getText().toString().trim(), newmobile.getText().toString());
                                }
                            }
                        })
                        *//*.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })*//*
                        .setNeutralButton("DISCARD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });*/
                builder.setView(mView);
                builder.create();
                builder.show();
                builder.setCancelable(true);
            }
        });
    }

    private void setUpDrawer() {

        recyclerView1 = findViewById(R.id.recycler);

        names = new String[]{
                "Test1", "Test2", "Syanch", "Search", "Upload", "Settings", "Sign-In"
                /*getResources().getString(R.string.cart),
                getResources().getString(R.string.wishlist),
               *//* getResources().getString(R.string.orders),
                getResources().getString(R.string.account),
                getResources().getString(R.string.store),
                getResources().getString(R.string.notification),
                getResources().getString(R.string.logout),
                getResources().getString(R.string.login)*/
        };
        imageId1 = new Integer[]{
                R.drawable.one,
                R.drawable.one,
                R.drawable.synch,
                R.drawable.search2,
                R.drawable.upload,
                R.drawable.gears,
                R.drawable.signin,
        };

        RecyclerView.LayoutManager firstLayoutManager = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(firstLayoutManager);
        RecyclerViewAdapter firstAdapter = new RecyclerViewAdapter(this, names, imageId1, drawer);
        recyclerView1.setAdapter(firstAdapter);
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
        addHideFragment(frag1);
        addHideFragment(frag2);
        addHideFragment(frag4);
        addHideFragment(frag5);
        mFragmentManager.beginTransaction().add(R.id.frame_layout, frag3).commit();
        mFragment = frag3;
    }

    public void Test() {
        Toast.makeText(this, "fffff", Toast.LENGTH_SHORT).show();
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
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            hideShowFragment(mFragment, frag3);
            mFragment = frag3;
        }
    }

    private void getFacilitatorList() {

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
                    .getString(Constants.PREFS_USER_LAB_ID, "");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(PostInterface.JSONURL)
                    .client(httpClient)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            PostInterface api = retrofit.create(PostInterface.class);

            Call<String> call = api.getFacilitatorListOnLab(sLabCode , "Idgz1PE3zO9iNc0E3oeH3CHDPX9MzZe3");

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    //Log.i("Responsestring", response.body());
                    progressdialog.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //Log.i("onSuccess", response.body());
                            jsonresponse = response.body();
                            //getFacilitatorListResponse(jsonresponse);
                            /* ############# Initialize Fragments  ############*/
                            init();
                        } else {
                            //Log.i("onEmptyResponse", "Returned empty response");
                            Toast.makeText(DashBoard_Activity.this, "Unable to get data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Log.i("onEmptyResponse", "Returned empty response");
                    progressdialog.dismiss();
                    Toast.makeText(DashBoard_Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void addNewOne(Fragment frag) {
        hideShowFragment(frag4, frag2);
        mFragment = frag2;
        third_fragemt_selected = false;
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        //Toast.makeText(getApplicationContext(), "dgsd", Toast.LENGTH_SHORT).show();
    }
}