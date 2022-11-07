package com.sunanda.newroutine.application.somenath.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.somenath.myadapter.VillageAdapter_C_UN;
import com.sunanda.newroutine.application.somenath.pojo.VillageCovUnCovPojo;
import com.sunanda.newroutine.application.util.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VillageDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LoadingDialog loadingDialog;
    TextView title, tmpTxt;
    ArrayList<VillageCovUnCovPojo> villageCoverArrayList, villageUnCoverArrayList, villageAllArrayList;
    VillageAdapter_C_UN villageAdapter_c_un;
    EditText editTextSearch;
    ImageView info;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_details);

        this.recyclerView = findViewById(R.id.statusVillage);

        loadingDialog = new LoadingDialog(this);
        info = findViewById(R.id.info);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        editTextSearch = findViewById(R.id.editTextSearch);

        title = findViewById(R.id.title);
        title.setText("Panchayat Name : " + getIntent().getStringExtra("PANNAME"));
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

        villageCoverArrayList = new ArrayList<>();
        villageUnCoverArrayList = new ArrayList<>();
        villageAllArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("VILLAGE"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                VillageCovUnCovPojo villageCovUnCovPojo = new VillageCovUnCovPojo();
                villageCovUnCovPojo.setId(jsonObject.getString("_id"));
                villageCovUnCovPojo.setVillCode(jsonObject.getString("vill_code"));
                //villageCovUnCovPojo.setVillType();
                villageCovUnCovPojo.setVillName(jsonObject.getString("vill_name"));
                villageCovUnCovPojo.setHabitation(jsonObject.getString("habitation"));

                JSONArray jsonArrayHab = new JSONArray(jsonObject.getString("habitation"));
                int count_touched = 0;
                for (int k = 0; k < jsonArrayHab.length(); k++) {
                    if (jsonArrayHab.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                        count_touched++;
                }
                if (jsonArrayHab.length() == count_touched)
                    villageCoverArrayList.add(villageCovUnCovPojo);
                else
                    villageUnCoverArrayList.add(villageCovUnCovPojo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        villageAllArrayList.clear();
        villageAllArrayList.addAll(villageUnCoverArrayList);
        villageAllArrayList.addAll(villageCoverArrayList);
        villageAdapter_c_un = new VillageAdapter_C_UN(this, villageAllArrayList);
        recyclerView.setAdapter(villageAdapter_c_un);

        final ImageView filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(VillageDetails.this, filter);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_vill, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.header1:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int val1 = 0, val2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            val1 = jsonArray1.length();
                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
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
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
                                break;
                            case R.id.header2:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int val1 = 0, val2 = 0;
                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            val1 = jsonArray1.length();
                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
                                            val2 = jsonArray2.length();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(val2).compareTo(val1);
                                    }
                                });
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
                                break;
                            case R.id.header3:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int count_touched1 = 0, count_touched2 = 0;

                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            for (int k = 0; k < jsonArray1.length(); k++) {
                                                if (jsonArray1.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                    count_touched1++;
                                            }

                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
                                            for (int k = 0; k < jsonArray2.length(); k++) {
                                                if (jsonArray2.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                    count_touched2++;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        // ## Ascending order
                                        return Integer.valueOf(count_touched1).compareTo(count_touched2);
                                    }
                                });
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
                                break;
                            case R.id.header4:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int count_touched1 = 0, count_touched2 = 0;

                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            for (int k = 0; k < jsonArray1.length(); k++) {
                                                if (jsonArray1.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                    count_touched1++;
                                            }

                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
                                            for (int k = 0; k < jsonArray2.length(); k++) {
                                                if (jsonArray2.getJSONObject(k).getString("touched").equalsIgnoreCase("1"))
                                                    count_touched2++;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_touched2).compareTo(count_touched1);
                                    }
                                });
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
                                break;
                            case R.id.header5:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int count_untouched1 = 0, count_untouched2 = 0;

                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            for (int k = 0; k < jsonArray1.length(); k++) {
                                                if (jsonArray1.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                    count_untouched1++;
                                            }

                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
                                            for (int k = 0; k < jsonArray2.length(); k++) {
                                                if (jsonArray2.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                    count_untouched2++;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        // ## Ascending order
                                        return Integer.valueOf(count_untouched1).compareTo(count_untouched2);
                                    }
                                });
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
                                break;
                            case R.id.header6:
                                Collections.sort(villageAllArrayList, new Comparator<VillageCovUnCovPojo>() {
                                    public int compare(VillageCovUnCovPojo obj1, VillageCovUnCovPojo obj2) {

                                        int count_untouched1 = 0, count_untouched2 = 0;

                                        try {
                                            JSONArray jsonArray1 = new JSONArray(obj1.getHabitation());
                                            for (int k = 0; k < jsonArray1.length(); k++) {
                                                if (jsonArray1.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                    count_untouched1++;
                                            }

                                            JSONArray jsonArray2 = new JSONArray(obj2.getHabitation());
                                            for (int k = 0; k < jsonArray2.length(); k++) {
                                                if (jsonArray2.getJSONObject(k).getString("touched").equalsIgnoreCase("0"))
                                                    count_untouched2++;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // ## Ascending order
                                        return Integer.valueOf(count_untouched2).compareTo(count_untouched1);
                                    }
                                });
                                villageAdapter_c_un = new VillageAdapter_C_UN(VillageDetails.this, villageAllArrayList);
                                recyclerView.setAdapter(villageAdapter_c_un);
                                villageAdapter_c_un.notifyDataSetChanged();
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

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });
    }

    private void showMyDialog() {
        final Dialog dialog = new Dialog(this, R.style.AppTheme_NoActionBar);

        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.CENTER);

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        dialog.setTitle(null);
        dialog.setContentView(R.layout.mydialog3);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    private void filter(String text) {
        try {
            //new array list that will hold the filtered data
            ArrayList<VillageCovUnCovPojo> filterdNames = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0; i < villageAllArrayList.size(); i++) {
                names.add(villageAllArrayList.get(i).getVillName());
            }

            //looping through existing elements
            for (String s : names) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(villageAllArrayList.get(names.indexOf(s)));
                }
            }
            //calling a method of the adapter class and passing the filtered list
            villageAdapter_c_un.updateList(filterdNames);
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
