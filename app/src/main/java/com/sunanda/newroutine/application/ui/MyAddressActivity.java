package com.sunanda.newroutine.application.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sunanda.newroutine.application.modal.ContactModel;
import com.sunanda.newroutine.application.adapter.MyAddressAdapter;
import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MyAddressActivity extends AppCompatActivity {

    private static final String TAG ="MyAddressActivity" ;
    SessionManager sessionManager;
    private RecyclerView recycler_view;
    private List<ContactModel> getcontent = new ArrayList<>();
     MyAddressAdapter myAddressAdapter;
     private CardView card_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        sessionManager = new SessionManager(this);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Address");
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        card_view = (CardView)findViewById(R.id.card_view);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        recycler_view.hasFixedSize();

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(MyAddressActivity.this, AddNewAdressActivity.class);
                //startActivity(intent);
            }
        });


        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyAddressActivity.this, "Deliver here", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        displayAllRecords();
    }

    private void displayAllRecords() {

        ArrayList<ContactModel> contacts = new ArrayList<>();
        getcontent = new ArrayList<>();

        if (contacts.size() > 0) {
             ContactModel contactModel;
            for (int i = 0; i < contacts.size(); i++) {
                contactModel = contacts.get(i);
                getcontent.add(contactModel);

                String city_name = contactModel.getCity();
                String area_name = contactModel.getArea();
                String flat_no = contactModel.getFlatno();
                String name = contactModel.getFirstName();
                String phone = contactModel.getPhonenumber();

                String personName = city_name + " " + area_name + " " +flat_no + " " +name + " " + phone ;

                Log.d(TAG, "displayAllRecords: "+personName);

            }

            myAddressAdapter = new MyAddressAdapter(MyAddressActivity.this,getcontent);
            recycler_view.setAdapter(myAddressAdapter);

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent=new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                setResult(sessionManager.getKeyAddressAct(),intent);
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
        Intent intent=new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(sessionManager.getKeyAddressAct(),intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
        finish();
    }
}
