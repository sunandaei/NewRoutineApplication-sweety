package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.CoverUncoverAdapter;
import com.sunanda.newroutine.application.somenath.pojo.LabWorkStatus;
import com.sunanda.newroutine.application.util.LoadingDialog;
import com.sunanda.newroutine.application.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Cover_UncoverDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LoadingDialog loadingDialog;
    TextView title, tmpTxt;
    CoverUncoverAdapter coverUncoverAdapter;
    ArrayList<LabWorkStatus> labWorkStatusArrayList;
    EditText editTextSearch;
    SessionManager sessionManager;

    @Override
    protected void onSaveInstanceState(Bundle oldInstanceState) {
        super.onSaveInstanceState(oldInstanceState);
        oldInstanceState.clear();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover__uncover_details);

        this.recyclerView = (RecyclerView) findViewById(R.id.statusRecy);
        editTextSearch = findViewById(R.id.editTextSearch);

        loadingDialog = new LoadingDialog(this);
        sessionManager = new SessionManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        title = findViewById(R.id.title);
        tmpTxt = findViewById(R.id.tmpTxt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.leftarrow);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.leftarrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        labWorkStatusArrayList = sessionManager.getArrayList("LAB_STAT");
        coverUncoverAdapter = new CoverUncoverAdapter(this, labWorkStatusArrayList);
        recyclerView.setAdapter(coverUncoverAdapter);

        title.setText("Lab : " + labWorkStatusArrayList.get(0).getLabName()
                + "\nDistrict : " + labWorkStatusArrayList.get(0).getDistName()
                /*+ "\n" + "Block : " + labWorkStatusArrayList.get(0).getBlockName()*/);

        /*if (getIntent().getStringExtra("TYPE").equalsIgnoreCase("0")) {
            tmpTxt.setText("UNTOUCHED PANCHAYAT DETAILS");
            tmpTxt.setTextColor(Color.parseColor("#FF0000"));
        } else {
            tmpTxt.setText("COVERAGE PANCHAYAT DETAILS");
            tmpTxt.setTextColor(Color.parseColor("#228B22"));
        }*/

        final ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Cover_UncoverDetails.this, filter);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_pan, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.header1:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int val1 = 0, val2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            val1 = jsonArray1.length();
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            val2 = jsonArray2.length();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(val1).compareTo(val2);

                                        // ## Ascending order
                                        //return obj1.firstName.compareToIgnoreCase(obj2.firstName); // To compare string values
                                        // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values

                                        // ## Descending order
                                        // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                                        // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                            case R.id.header2:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int val1 = 0, val2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            val1 = jsonArray1.length();
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            val2 = jsonArray2.length();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(val2).compareTo(val1);
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                            case R.id.header3:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int count_touched1 = 0, count_touched2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray1.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                        count_touched1++;
                                                }
                                            }
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            for (int j = 0; j < jsonArray2.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray2.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                        count_touched2++;
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_touched1).compareTo(count_touched2);
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                            case R.id.header4:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int count_touched1 = 0, count_touched2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray1.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                        count_touched1++;
                                                }
                                            }
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            for (int j = 0; j < jsonArray2.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray2.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                        count_touched2++;
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_touched2).compareTo(count_touched1);
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                            case R.id.header5:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int count_untouched1 = 0, count_untouched2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray1.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                        count_untouched1++;
                                                }
                                            }
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            for (int j = 0; j < jsonArray2.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray2.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                        count_untouched2++;
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_untouched1).compareTo(count_untouched2);
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                            case R.id.header6:
                                Collections.sort(labWorkStatusArrayList, new Comparator<LabWorkStatus>() {
                                    public int compare(LabWorkStatus obj1, LabWorkStatus obj2) {

                                        int count_untouched1 = 0, count_untouched2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getVillageHab());
                                            for (int j = 0; j < jsonArray1.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray1.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                        count_untouched1++;
                                                }
                                            }
                                            JSONArray jsonArray2 = new JSONArray(obj2.getVillageHab());
                                            for (int j = 0; j < jsonArray2.length(); j++) {
                                                JSONArray habitation = new JSONArray(jsonArray2.getJSONObject(j).getJSONArray("habitation").toString());
                                                for (int k = 0; k < habitation.length(); k++) {
                                                    if (habitation.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                        count_untouched2++;
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_untouched2).compareTo(count_untouched1);
                                    }
                                });
                                coverUncoverAdapter = new CoverUncoverAdapter(Cover_UncoverDetails.this, labWorkStatusArrayList);
                                recyclerView.setAdapter(coverUncoverAdapter);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            ArrayList<LabWorkStatus> filterdNames = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < labWorkStatusArrayList.size(); i++) {
                names.add(labWorkStatusArrayList.get(i).getPanchayatName());
            }

            //looping through existing elements
            for (String s : names) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(labWorkStatusArrayList.get(names.indexOf(s)));
                }
            }
            //calling a method of the adapter class and passing the filtered list
            coverUncoverAdapter.updateList(filterdNames);
        } catch (Exception ignored) {
        }
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