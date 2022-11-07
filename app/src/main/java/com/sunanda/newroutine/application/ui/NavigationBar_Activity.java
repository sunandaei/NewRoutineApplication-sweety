package com.sunanda.newroutine.application.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

public abstract class NavigationBar_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private NavigationView navigationView;
    private DrawerLayout drawer;
    View navHeader;
    private Toolbar toolbar;
    TextView tvVersionCode;
    String versionName = "";

    public void create(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        tvVersionCode = navHeader.findViewById(R.id.tvVersionCode);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvVersionCode.setText("V-" + versionName);
        setUpNavigationView();
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_sample_collection:
                        /*Intent intentSampleCollection = new Intent(NavigationBar_Activity.this, SampleCollection_Activity.class);
                        startActivity(intentSampleCollection);*/
                        break;
                    case R.id.nav_search:
                        Intent intentSearch = new Intent(NavigationBar_Activity.this, Search_Activity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.nav_data_upload:
                        Intent intentDataUpload = new Intent(NavigationBar_Activity.this, DataUploadRoutine_Activity.class);
                        startActivity(intentDataUpload);
                        break;
                    case R.id.nav_Sync:
                        Intent intentSyncOnlineData = new Intent(NavigationBar_Activity.this, SyncOnlineData_Facilitator_Activity.class);
                        startActivity(intentSyncOnlineData);
                        break;
                    case R.id.nav_logout:
                        CGlobal.getInstance().getPersistentPreferenceEditor(NavigationBar_Activity.this)
                                .putBoolean(Constants.PREFS_LOGIN_FLAG, false).commit();
                        CGlobal.getInstance().getPersistentPreferenceEditor(NavigationBar_Activity.this)
                                .putBoolean(Constants.PREFS_SYNC_ONLINE_DATA_FLAG, false).commit();
                        Intent intent = new Intent(NavigationBar_Activity.this, Login_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                        finish();
                        break;
                    default:
                }
                menuItem.setVisible(false);
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
