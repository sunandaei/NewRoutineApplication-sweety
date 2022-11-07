package com.sunanda.newroutine.application.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.view.DashBoard_Activity;
import com.sunanda.newroutine.application.util.SessionManager;


public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView viewmyaddress, viewmyorder;
    private RelativeLayout setting_layout, logout_layout;
    private ImageView edit;
    private Toolbar toolbar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        sessionManager = new SessionManager(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Account");
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        viewmyaddress = (TextView) findViewById(R.id.viewmyaddress);
        viewmyorder = (TextView) findViewById(R.id.viewmyorder);
        setting_layout = (RelativeLayout) findViewById(R.id.setting_layout);
        logout_layout = (RelativeLayout) findViewById(R.id.logout_layout);
        edit = (ImageView) findViewById(R.id.edit);

        viewmyaddress.setOnClickListener(this);
        viewmyorder.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        edit.setOnClickListener(this);

        if (sessionManager.isLoggedIn()) {
            ((TextView) findViewById(R.id.name)).setText(sessionManager.getName());
            ((TextView) findViewById(R.id.email)).setText(sessionManager.getEmail());
            ((TextView) findViewById(R.id.phonenumber)).setText(sessionManager.getPhone());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewmyaddress:
                //Intent intent = new Intent(MyAccountActivity.this, MyAddressActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(getApplicationContext(), MyAddressActivity.class);
                sessionManager.setKEY_ADDRESS_ACT(2000);
                startActivityForResult(intent, sessionManager.getKeyAddressAct());
                break;
            case R.id.viewmyorder:
                break;
            case R.id.setting_layout:
                Intent intent2 = new Intent(MyAccountActivity.this, EditAccountActivity.class);
                startActivity(intent2);
                break;
            case R.id.logout_layout:
                break;
            case R.id.edit:
                Intent intent1 = new Intent(MyAccountActivity.this, EditAccountActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent;
                intent = new Intent(this, DashBoard_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        intent = new Intent(this, DashBoard_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==40)
        {
            startActivity(getIntent());
        }*/
    }
}
