package com.sunanda.newroutine.application.newactivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditWash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wash_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        init();
    }

    SampleModel sampleModel;
    DatabaseHandler databaseHandler1;

    @Override
    protected void onResume() {
        super.onResume();
        btnSave.setText("SAVE");
        DatabaseHandler databaseHandler = new DatabaseHandler(EditWash_Activity.this);
        sampleModel = new SampleModel();
        int iId = CGlobal.getInstance().getPersistentPreference(EditWash_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        sampleModel = databaseHandler.getSchoolAppDataCollectionEdit(iId);

        if (!TextUtils.isEmpty(sampleModel.getNumberoftoiletforboys_q_w_2b_a())) {
            llNumberToiletsBoys.setVisibility(View.VISIBLE);
            etNumberToiletsBoys.setText(sampleModel.getNumberoftoiletforboys_q_w_2b_a());
        } else {
            llNumberToiletsBoys.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getNumberoftoiletforgirl_q_w_2b_b())) {
            llNumberToiletsGirls.setVisibility(View.VISIBLE);
            etNumberToiletsGirls.setText(sampleModel.getNumberoftoiletforgirl_q_w_2b_b());
        } else {
            llNumberToiletsGirls.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getNumberofgeneraltoilet_q_w_2b_c())) {
            llGeneralToilets.setVisibility(View.VISIBLE);
            etGeneralToilets.setText(sampleModel.getNumberofgeneraltoilet_q_w_2b_c());
        } else {
            llGeneralToilets.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getNumberoftoiletforteachers_q_w_2c_a())) {
            llNumberToiletsTeachers.setVisibility(View.VISIBLE);
            etNumberToiletsTeachers.setText(sampleModel.getNumberoftoiletforteachers_q_w_2c_a());
        } else {
            llNumberToiletsTeachers.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getImageoftoilet_q_w_2d())) {
            llSanitationFacility.setVisibility(View.VISIBLE);
            mCurrentPhotoPath = sampleModel.getImageoftoilet_q_w_2d();
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            ivPictureSanitationFacility.setImageBitmap(bitmap);
        } else {
            llSanitationFacility.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getImageofwashbasin_q_w_3c())) {
            llWashBasinPicture.setVisibility(View.VISIBLE);
            mCurrentPhotoPath_1 = sampleModel.getImageofwashbasin_q_w_3c();
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
            ivPictureWashBasin.setImageBitmap(bitmap);
        } else {
            llWashBasinPicture.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getWatersourcebeentestedbefore_q_w_1())) {
            if (sampleModel.getWatersourcebeentestedbefore_q_w_1().equalsIgnoreCase("1")) {
                rbTestedBeforeYes.setChecked(true);
                rbTestedBeforeNo.setChecked(false);
            } else {
                rbTestedBeforeYes.setChecked(false);
                rbTestedBeforeNo.setChecked(true);
            }
            sTestedBefore = sampleModel.getWatersourcebeentestedbefore_q_w_1();
            llTestedBefore.setVisibility(View.VISIBLE);
        } else {
            llTestedBefore.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(sampleModel.getWhenwaterlasttested_q_w_1a())) {
            if (sampleModel.getWhenwaterlasttested_q_w_1a().equalsIgnoreCase("1")) {
                rbMoreThanYearAgo.setChecked(true);
                rbWithinTheLastMonths.setChecked(false);
            } else {
                rbMoreThanYearAgo.setChecked(false);
                rbWithinTheLastMonths.setChecked(true);
            }
            sLastTested = sampleModel.getWhenwaterlasttested_q_w_1a();
            llLastTested.setVisibility(View.VISIBLE);
        } else {
            llLastTested.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIstestreportsharedschoolauthority_q_w_1b())) {
            if (sampleModel.getIstestreportsharedschoolauthority_q_w_1b().equalsIgnoreCase("1")) {
                rbSchoolAuthorityYes.setChecked(true);
                rbSchoolAuthorityNo.setChecked(false);
            } else {
                rbSchoolAuthorityYes.setChecked(false);
                rbSchoolAuthorityNo.setChecked(true);
            }
            sSchoolAuthority = sampleModel.getIstestreportsharedschoolauthority_q_w_1b();
            llSchoolAuthority.setVisibility(View.VISIBLE);
        } else {
            llSchoolAuthority.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getFoundtobebacteriologically_q_w_1c())) {
            if (sampleModel.getFoundtobebacteriologically_q_w_1c().equalsIgnoreCase("4")) {
                rbWithinWeek.setChecked(true);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(false);
            } else if (sampleModel.getFoundtobebacteriologically_q_w_1c().equalsIgnoreCase("5")) {
                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(true);
                rbNotFixed.setChecked(false);
            } else {
                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(true);
            }
            sBacteriologicallyContaminated = sampleModel.getFoundtobebacteriologically_q_w_1c();
            llDisinfected.setVisibility(View.VISIBLE);
        } else {
            llDisinfected.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIstoiletfacilityavailable_q_w_2())) {
            if (sampleModel.getIstoiletfacilityavailable_q_w_2().equalsIgnoreCase("1")) {
                rbToiletSchoolYes.setChecked(true);
                rbToiletSchoolNo.setChecked(false);
            } else {
                rbToiletSchoolYes.setChecked(false);
                rbToiletSchoolNo.setChecked(true);
            }
            sToiletSchool = sampleModel.getIstoiletfacilityavailable_q_w_2();
            llToiletSchool.setVisibility(View.VISIBLE);
        } else {
            llToiletSchool.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIsrunningwateravailable_q_w_2a())) {
            if (sampleModel.getIsrunningwateravailable_q_w_2a().equalsIgnoreCase("1")) {
                rbRunningWaterYes.setChecked(true);
                rbRunningWaterNo.setChecked(false);
            } else {
                rbRunningWaterNo.setChecked(true);
                rbRunningWaterYes.setChecked(false);
            }
            sRunningWater = sampleModel.getIsrunningwateravailable_q_w_2a();
            llRunningWater.setVisibility(View.VISIBLE);
        } else {
            llRunningWater.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getSeparatetoiletsforboysandgirls_q_w_2b())) {
            if (sampleModel.getSeparatetoiletsforboysandgirls_q_w_2b().equalsIgnoreCase("1")) {
                rbSeparateToiletsYes.setChecked(true);
                rbSeparateToiletsNo.setChecked(false);
            } else {
                rbSeparateToiletsYes.setChecked(false);
                rbSeparateToiletsNo.setChecked(true);
            }
            sSeparateToilets = sampleModel.getSeparatetoiletsforboysandgirls_q_w_2b();
            llSeparateToilets.setVisibility(View.VISIBLE);
        } else {
            llSeparateToilets.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIsseparatetoiletforteachers_q_w_2c())) {
            if (sampleModel.getIsseparatetoiletforteachers_q_w_2c().equalsIgnoreCase("1")) {
                rbTeacherToiletsYes.setChecked(true);
                rbTeacherToiletsNo.setChecked(false);
            } else {
                rbTeacherToiletsYes.setChecked(false);
                rbTeacherToiletsNo.setChecked(true);
            }
            sTeacherToilets = sampleModel.getIsseparatetoiletforteachers_q_w_2c();
            llTeacherToilets.setVisibility(View.VISIBLE);
        } else {
            llTeacherToilets.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIshandwashingfacility_q_w_3())) {
            if (sampleModel.getIshandwashingfacility_q_w_3().equalsIgnoreCase("1")) {
                rbWashingFacilityYes.setChecked(true);
                rbWashingFacilityNo.setChecked(false);
            } else {
                rbWashingFacilityYes.setChecked(false);
                rbWashingFacilityNo.setChecked(true);
            }
            sWashingFacility = sampleModel.getIshandwashingfacility_q_w_3();
            llWashingFacility.setVisibility(View.VISIBLE);
        } else {
            llWashingFacility.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIsrunningwateravailable_q_w_3a())) {
            if (sampleModel.getIsrunningwateravailable_q_w_3a().equalsIgnoreCase("1")) {
                rbHandWashingYes.setChecked(true);
                rbHandWashingNo.setChecked(false);
            } else {
                rbHandWashingYes.setChecked(false);
                rbHandWashingNo.setChecked(true);
            }
            sHandWashing = sampleModel.getIsrunningwateravailable_q_w_3a();
            llHandWashing.setVisibility(View.VISIBLE);
        } else {
            llHandWashing.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIsthewashbasinwithin_q_w_3b())) {
            if (sampleModel.getIsthewashbasinwithin_q_w_3b().equalsIgnoreCase("1")) {
                rbWashBasinYes.setChecked(true);
                rbWashBasinNo.setChecked(false);
            } else {
                rbWashBasinYes.setChecked(false);
                rbWashBasinNo.setChecked(true);
            }
            sWashBasin = sampleModel.getIsthewashbasinwithin_q_w_3b();
            llWashBasin.setVisibility(View.VISIBLE);
        } else {
            llWashBasin.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sampleModel.getIswaterinkitchen_q_w_4())) {
            if (sampleModel.getIswaterinkitchen_q_w_4().equalsIgnoreCase("1")) {
                rbWaterKitchenYes.setChecked(true);
                rbWaterKitchenNo.setChecked(false);
            } else {
                rbWaterKitchenYes.setChecked(false);
                rbWaterKitchenNo.setChecked(true);
            }
            sWaterKitchen = sampleModel.getIswaterinkitchen_q_w_4();
            llWaterKitchen.setVisibility(View.VISIBLE);
        } else {
            llWaterKitchen.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(sampleModel.getSource_site_q_1())) {
            if (sampleModel.getSource_site_q_1().equalsIgnoreCase("ANGANWADI")) {
                tvEdit5.setText(getString(R.string.is_the_water_quality_test_report_shared_with_the_anganwadi_authority));
                tvEdit6.setText(getString(R.string.is_toilet_facility_available_in_the_anganwadi));
                tvEdit7.setText(getString(R.string.number_of_general_toilets_anganwadi));
                tvEdit8.setText(getString(R.string.is_hand_washing_facility_available_in_the_anganwadi));
            } else {
                tvEdit5.setText(getString(R.string.is_the_water_quality_test_report_shared_with_the_school_authority));
                tvEdit6.setText(getString(R.string.is_toilet_facility_available_in_the_school));
                tvEdit7.setText(getString(R.string.number_of_general_toilets_school));
                tvEdit8.setText(getString(R.string.is_hand_washing_facility_available_in_the_school));
            }
        }

    }

    TextView tvEdit5, tvEdit6, tvEdit7, tvEdit8;
    Toolbar toolbar;
    LinearLayout llTestedBefore, llLastTested, llSchoolAuthority, llDisinfected, llToiletSchool,
            llRunningWater, llSeparateToilets, llNumberToiletsBoys, llNumberToiletsGirls, llGeneralToilets,
            llTeacherToilets, llNumberToiletsTeachers, llSanitationFacility, llWashingFacility,
            llHandWashing, llWashBasin, llWashBasinPicture, llWaterKitchen;

    RadioButton rbTestedBeforeYes, rbTestedBeforeNo, rbMoreThanYearAgo, rbWithinTheLastMonths,
            rbSchoolAuthorityYes, rbSchoolAuthorityNo, rbWithinWeek, rbWithinMonth, rbNotFixed,
            rbToiletSchoolYes, rbToiletSchoolNo, rbRunningWaterYes, rbRunningWaterNo, rbSeparateToiletsYes,
            rbSeparateToiletsNo, rbTeacherToiletsYes, rbTeacherToiletsNo, rbWashingFacilityYes, rbWashingFacilityNo,
            rbHandWashingYes, rbHandWashingNo, rbWashBasinYes, rbWashBasinNo, rbWaterKitchenYes, rbWaterKitchenNo;

    EditText etNumberToiletsBoys, etNumberToiletsGirls, etGeneralToilets, etNumberToiletsTeachers;

    ImageView ivPictureSanitationFacility, ivPictureWashBasin;

    Button btnTakeImageSanitationFacility, btnTakeImageWashBasin, btnSave;

    private static final int CAMERA_REQUEST = 1112;
    private String mCurrentPhotoPath;

    private static final int CAMERA_REQUEST_1 = 1113;
    private String mCurrentPhotoPath_1;

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private static final String JPEG_FILE_PREFIX = "sch_img_sanitation_";
    private static final String JPEG_FILE_PREFIX_1 = "sch_img_wash_";
    private static final String JPEG_FILE_SUFFIX = ".png";

    String sTestedBefore = "", sLastTested = "", sSchoolAuthority = "", sBacteriologicallyContaminated = "",
            sToiletSchool = "", sRunningWater = "", sSeparateToilets = "", sTeacherToilets = "", sWashingFacility = "",
            sHandWashing = "", sWashBasin = "", sWaterKitchen = "";

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("W.A.S.H");

        tvEdit5 = findViewById(R.id.tvEdit5);
        tvEdit6 = findViewById(R.id.tvEdit6);
        tvEdit7 = findViewById(R.id.tvEdit7);
        tvEdit8 = findViewById(R.id.tvEdit8);

        llTestedBefore = findViewById(R.id.llTestedBefore);
        llLastTested = findViewById(R.id.llLastTested);
        llSchoolAuthority = findViewById(R.id.llSchoolAuthority);
        llDisinfected = findViewById(R.id.llDisinfected);
        llToiletSchool = findViewById(R.id.llToiletSchool);
        llRunningWater = findViewById(R.id.llRunningWater);
        llSeparateToilets = findViewById(R.id.llSeparateToilets);
        llNumberToiletsBoys = findViewById(R.id.llNumberToiletsBoys);
        llNumberToiletsGirls = findViewById(R.id.llNumberToiletsGirls);
        llGeneralToilets = findViewById(R.id.llGeneralToilets);
        llTeacherToilets = findViewById(R.id.llTeacherToilets);
        llNumberToiletsTeachers = findViewById(R.id.llNumberToiletsTeachers);
        llSanitationFacility = findViewById(R.id.llSanitationFacility);
        llWashingFacility = findViewById(R.id.llWashingFacility);
        llHandWashing = findViewById(R.id.llHandWashing);
        llWashBasin = findViewById(R.id.llWashBasin);
        llWashBasinPicture = findViewById(R.id.llWashBasinPicture);
        llWaterKitchen = findViewById(R.id.llWaterKitchen);

        rbTestedBeforeYes = findViewById(R.id.rbTestedBeforeYes);
        rbTestedBeforeNo = findViewById(R.id.rbTestedBeforeNo);

        rbMoreThanYearAgo = findViewById(R.id.rbMoreThanYearAgo);
        rbWithinTheLastMonths = findViewById(R.id.rbWithinTheLastMonths);

        rbSchoolAuthorityYes = findViewById(R.id.rbSchoolAuthorityYes);
        rbSchoolAuthorityNo = findViewById(R.id.rbSchoolAuthorityNo);

        rbWithinWeek = findViewById(R.id.rbWithinWeek);
        rbWithinMonth = findViewById(R.id.rbWithinMonth);
        rbNotFixed = findViewById(R.id.rbNotFixed);

        rbToiletSchoolYes = findViewById(R.id.rbToiletSchoolYes);
        rbToiletSchoolNo = findViewById(R.id.rbToiletSchoolNo);

        rbRunningWaterYes = findViewById(R.id.rbRunningWaterYes);
        rbRunningWaterNo = findViewById(R.id.rbRunningWaterNo);

        rbSeparateToiletsYes = findViewById(R.id.rbSeparateToiletsYes);
        rbSeparateToiletsNo = findViewById(R.id.rbSeparateToiletsNo);

        rbTeacherToiletsYes = findViewById(R.id.rbTeacherToiletsYes);
        rbTeacherToiletsNo = findViewById(R.id.rbTeacherToiletsNo);

        rbWashingFacilityYes = findViewById(R.id.rbWashingFacilityYes);
        rbWashingFacilityNo = findViewById(R.id.rbWashingFacilityNo);

        rbHandWashingYes = findViewById(R.id.rbHandWashingYes);
        rbHandWashingNo = findViewById(R.id.rbHandWashingNo);

        rbWashBasinYes = findViewById(R.id.rbWashBasinYes);
        rbWashBasinNo = findViewById(R.id.rbWashBasinNo);

        rbWaterKitchenYes = findViewById(R.id.rbWaterKitchenYes);
        rbWaterKitchenNo = findViewById(R.id.rbWaterKitchenNo);

        etNumberToiletsBoys = findViewById(R.id.etNumberToiletsBoys);
        etNumberToiletsGirls = findViewById(R.id.etNumberToiletsGirls);
        etGeneralToilets = findViewById(R.id.etGeneralToilets);
        etNumberToiletsTeachers = findViewById(R.id.etNumberToiletsTeachers);

        ivPictureSanitationFacility = findViewById(R.id.ivPictureSanitationFacility);
        ivPictureWashBasin = findViewById(R.id.ivPictureWashBasin);

        btnTakeImageSanitationFacility = findViewById(R.id.btnTakeImageSanitationFacility);
        btnTakeImageWashBasin = findViewById(R.id.btnTakeImageWashBasin);
        btnSave = findViewById(R.id.btnSave);

        llTestedBefore.setVisibility(View.VISIBLE);
        llToiletSchool.setVisibility(View.VISIBLE);
        llWashingFacility.setVisibility(View.VISIBLE);
        llWaterKitchen.setVisibility(View.VISIBLE);

        llLastTested.setVisibility(View.GONE);
        llSchoolAuthority.setVisibility(View.GONE);
        llDisinfected.setVisibility(View.GONE);

        llRunningWater.setVisibility(View.GONE);
        llSeparateToilets.setVisibility(View.GONE);
        llNumberToiletsBoys.setVisibility(View.GONE);
        llNumberToiletsGirls.setVisibility(View.GONE);
        llGeneralToilets.setVisibility(View.GONE);
        llTeacherToilets.setVisibility(View.GONE);
        llNumberToiletsTeachers.setVisibility(View.GONE);
        llSanitationFacility.setVisibility(View.GONE);

        llHandWashing.setVisibility(View.GONE);
        llWashBasin.setVisibility(View.GONE);
        llWashBasinPicture.setVisibility(View.GONE);

        //-----------------111------------------------//
        rbTestedBeforeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTestedBefore = "1";

                rbTestedBeforeYes.setChecked(true);
                rbTestedBeforeNo.setChecked(false);

                llLastTested.setVisibility(View.VISIBLE);
                llSchoolAuthority.setVisibility(View.VISIBLE);
                llDisinfected.setVisibility(View.VISIBLE);
            }
        });

        rbTestedBeforeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTestedBefore = "0";

                rbTestedBeforeYes.setChecked(false);
                rbTestedBeforeNo.setChecked(true);

                llLastTested.setVisibility(View.GONE);
                llSchoolAuthority.setVisibility(View.GONE);
                llDisinfected.setVisibility(View.GONE);
            }
        });

        rbMoreThanYearAgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sLastTested = "1";

                rbMoreThanYearAgo.setChecked(true);
                rbWithinTheLastMonths.setChecked(false);
            }
        });

        rbWithinTheLastMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sLastTested = "2";

                rbMoreThanYearAgo.setChecked(false);
                rbWithinTheLastMonths.setChecked(true);
            }
        });

        rbSchoolAuthorityYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSchoolAuthority = "1";

                rbSchoolAuthorityYes.setChecked(true);
                rbSchoolAuthorityNo.setChecked(false);
            }
        });

        rbSchoolAuthorityNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSchoolAuthority = "0";

                rbSchoolAuthorityYes.setChecked(false);
                rbSchoolAuthorityNo.setChecked(true);
            }
        });

        rbWithinWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "4";

                rbWithinWeek.setChecked(true);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(false);
            }
        });

        rbWithinMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "5";

                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(true);
                rbNotFixed.setChecked(false);
            }
        });

        rbNotFixed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sBacteriologicallyContaminated = "6";

                rbWithinWeek.setChecked(false);
                rbWithinMonth.setChecked(false);
                rbNotFixed.setChecked(true);
            }
        });
        //-----------------111------------------------//

        //-----------------222------------------------//

        databaseHandler1 = new DatabaseHandler(EditWash_Activity.this);
        sampleModel = new SampleModel();
        int iId = CGlobal.getInstance().getPersistentPreference(EditWash_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        sampleModel = databaseHandler1.getSchoolAppDataCollectionEdit(iId);

        rbToiletSchoolYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sToiletSchool = "1";

                rbToiletSchoolYes.setChecked(true);
                rbToiletSchoolNo.setChecked(false);

                if (!TextUtils.isEmpty(sampleModel.getSource_site_q_1())) {
                    if (sampleModel.getSource_site_q_1().equalsIgnoreCase("ANGANWADI")) {
                        llRunningWater.setVisibility(View.VISIBLE);
                        llSeparateToilets.setVisibility(View.GONE);
                        llNumberToiletsBoys.setVisibility(View.GONE);
                        llNumberToiletsGirls.setVisibility(View.GONE);
                        llGeneralToilets.setVisibility(View.VISIBLE);
                        llTeacherToilets.setVisibility(View.GONE);
                        llNumberToiletsTeachers.setVisibility(View.GONE);
                        llSanitationFacility.setVisibility(View.VISIBLE);
                    } else {
                        llRunningWater.setVisibility(View.VISIBLE);
                        llSeparateToilets.setVisibility(View.VISIBLE);
                        llNumberToiletsBoys.setVisibility(View.GONE);
                        llNumberToiletsGirls.setVisibility(View.GONE);
                        llGeneralToilets.setVisibility(View.GONE);
                        llTeacherToilets.setVisibility(View.VISIBLE);
                        llNumberToiletsTeachers.setVisibility(View.GONE);
                        llSanitationFacility.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        rbToiletSchoolNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sToiletSchool = "0";

                rbToiletSchoolYes.setChecked(false);
                rbToiletSchoolNo.setChecked(true);

                llRunningWater.setVisibility(View.GONE);
                llSeparateToilets.setVisibility(View.GONE);
                llNumberToiletsBoys.setVisibility(View.GONE);
                llNumberToiletsGirls.setVisibility(View.GONE);
                llGeneralToilets.setVisibility(View.GONE);
                llTeacherToilets.setVisibility(View.GONE);
                llNumberToiletsTeachers.setVisibility(View.GONE);
                llSanitationFacility.setVisibility(View.GONE);
            }
        });

        rbRunningWaterYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRunningWater = "1";

                rbRunningWaterYes.setChecked(true);
                rbRunningWaterNo.setChecked(false);
            }
        });

        rbRunningWaterNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sRunningWater = "0";

                rbRunningWaterYes.setChecked(false);
                rbRunningWaterNo.setChecked(true);
            }
        });

        rbSeparateToiletsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSeparateToilets = "1";

                rbSeparateToiletsYes.setChecked(true);
                rbSeparateToiletsNo.setChecked(false);

                llNumberToiletsBoys.setVisibility(View.VISIBLE);
                llNumberToiletsGirls.setVisibility(View.VISIBLE);
                llGeneralToilets.setVisibility(View.GONE);
            }
        });

        rbSeparateToiletsNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sSeparateToilets = "0";

                rbSeparateToiletsYes.setChecked(false);
                rbSeparateToiletsNo.setChecked(true);

                llNumberToiletsBoys.setVisibility(View.GONE);
                llNumberToiletsGirls.setVisibility(View.GONE);
                llGeneralToilets.setVisibility(View.VISIBLE);
            }
        });

        rbTeacherToiletsYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTeacherToilets = "1";

                rbTeacherToiletsYes.setChecked(true);
                rbTeacherToiletsNo.setChecked(false);

                llNumberToiletsTeachers.setVisibility(View.VISIBLE);
            }
        });

        rbTeacherToiletsNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sTeacherToilets = "0";

                rbTeacherToiletsYes.setChecked(false);
                rbTeacherToiletsNo.setChecked(true);

                llNumberToiletsTeachers.setVisibility(View.GONE);

            }
        });
        //-----------------222------------------------//

        //-----------------333------------------------//
        rbWashingFacilityYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashingFacility = "1";

                rbWashingFacilityYes.setChecked(true);
                rbWashingFacilityNo.setChecked(false);

                llHandWashing.setVisibility(View.VISIBLE);
                llWashBasin.setVisibility(View.VISIBLE);
                llWashBasinPicture.setVisibility(View.VISIBLE);
            }
        });

        rbWashingFacilityNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashingFacility = "0";

                rbWashingFacilityYes.setChecked(false);
                rbWashingFacilityNo.setChecked(true);

                llHandWashing.setVisibility(View.GONE);
                llWashBasin.setVisibility(View.GONE);
                llWashBasinPicture.setVisibility(View.GONE);
            }
        });

        rbHandWashingYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sHandWashing = "1";

                rbHandWashingYes.setChecked(true);
                rbHandWashingNo.setChecked(false);
            }
        });

        rbHandWashingNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sHandWashing = "0";

                rbHandWashingYes.setChecked(false);
                rbHandWashingNo.setChecked(true);
            }
        });

        rbWashBasinYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashBasin = "1";

                rbWashBasinYes.setChecked(true);
                rbWashBasinNo.setChecked(false);
            }
        });

        rbWashBasinNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWashBasin = "0";

                rbWashBasinYes.setChecked(false);
                rbWashBasinNo.setChecked(true);
            }
        });

        //-----------------333------------------------//

        //-----------------444------------------------//
        rbWaterKitchenYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWaterKitchen = "1";

                rbWaterKitchenYes.setChecked(true);
                rbWaterKitchenNo.setChecked(false);
            }
        });

        rbWaterKitchenNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sWaterKitchen = "0";

                rbWaterKitchenYes.setChecked(false);
                rbWaterKitchenNo.setChecked(true);
            }
        });
        //-----------------444------------------------//

        btnTakeImageSanitationFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;
                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (Exception e) {
                    f = null;
                    mCurrentPhotoPath = null;
                    e.printStackTrace();
                }
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);*/

                if (ContextCompat.checkSelfPermission(
                        EditWash_Activity.this,
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                    String[] PERMISSIONS = {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                    };
                    ActivityCompat.requestPermissions(
                            EditWash_Activity.this, PERMISSIONS, 0
                    );
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            File photoFile = setUpPhotoFile();
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(
                                        EditWash_Activity.this,
                                        "com.sunanda.newroutine.application.fileprovider",
                                        photoFile
                                );
                                //mCurrentPhotoPath = photoFile.getAbsolutePath();
                                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                            }
                        } catch (Exception ex) {
                            mCurrentPhotoPath = null;
                            // Error occurred while creating the File
                            Toast.makeText(EditWash_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnTakeImageWashBasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;
                try {
                    f = setUpPhotoFile_1();
                    mCurrentPhotoPath_1 = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (Exception e) {
                    f = null;
                    mCurrentPhotoPath_1 = null;
                    e.printStackTrace();
                }
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_1);*/

                if (ContextCompat.checkSelfPermission(
                        EditWash_Activity.this,
                        Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
                ) {
                    String[] PERMISSIONS = {
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA
                    };
                    ActivityCompat.requestPermissions(
                            EditWash_Activity.this, PERMISSIONS, 0
                    );
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        try {
                            File photoFile = setUpPhotoFile_1();
                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(
                                        EditWash_Activity.this,
                                        "com.sunanda.newroutine.application.fileprovider",
                                        photoFile
                                );
                                //mCurrentPhotoPath = photoFile.getAbsolutePath();
                                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                startActivityForResult(takePictureIntent, CAMERA_REQUEST_1);
                            }
                        } catch (Exception ex) {
                            mCurrentPhotoPath = null;
                            // Error occurred while creating the File
                            Toast.makeText(EditWash_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditWash_Activity.this);
                builder1.setTitle("Water Quality");
                builder1.setMessage("Are you sure you want to save it?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        checkSave();
                        dialog.cancel();
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    private void checkSave(){
        int id = CGlobal.getInstance().getPersistentPreference(EditWash_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);

        String sNumberToiletsBoys = etNumberToiletsBoys.getText().toString();
        String sNumberToiletsGirls = etNumberToiletsGirls.getText().toString();
        String sGeneralToilets = etGeneralToilets.getText().toString();
        String sNumberToiletsTeachers = etNumberToiletsTeachers.getText().toString();

        DatabaseHandler databaseHandler = new DatabaseHandler(EditWash_Activity.this);

        if (TextUtils.isEmpty(mCurrentPhotoPath) && TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, "", sWashingFacility,
                    sHandWashing, sWashBasin, "", sWaterKitchen, "", "");
        } else if (!TextUtils.isEmpty(mCurrentPhotoPath) && TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, mCurrentPhotoPath, sWashingFacility,
                    sHandWashing, sWashBasin, "", sWaterKitchen,
                    mCurrentPhotoPath, "");
        } else if (TextUtils.isEmpty(mCurrentPhotoPath) && !TextUtils.isEmpty(mCurrentPhotoPath_1)) {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, "", sWashingFacility,
                    sHandWashing, sWashBasin, mCurrentPhotoPath_1, sWaterKitchen,
                    "", mCurrentPhotoPath_1);
        } else {
            databaseHandler.updateWASHSchoolAppDataCollection(id, sTestedBefore, sLastTested, sSchoolAuthority,
                    sBacteriologicallyContaminated, sToiletSchool, sRunningWater, sSeparateToilets,
                    sNumberToiletsBoys, sNumberToiletsGirls, sGeneralToilets, sTeacherToilets,
                    sNumberToiletsTeachers, mCurrentPhotoPath, sWashingFacility,
                    sHandWashing, sWashBasin, mCurrentPhotoPath_1, sWaterKitchen,
                    mCurrentPhotoPath, mCurrentPhotoPath_1);
        }

        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto();
        } else if (requestCode == CAMERA_REQUEST_1 && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto_1();
        } else {
            mCurrentPhotoPath = null;
            ivPictureSanitationFacility.setImageBitmap(null);
            ivPictureWashBasin.setImageBitmap(null);
        }
    }

    private File setUpPhotoFile() {
        File f = null;
        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    private File createImageFile() {
        // Create an image file name
        File imageF = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
            File albumF = getAlbumDir();
            imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;
        try {
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {

                storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

                if (storageDir != null) {
                    if (!storageDir.mkdirs()) {
                        if (!storageDir.exists()) {
                            Log.d("CameraSample", "failed to create directory");
                            return null;
                        }
                    }
                }

            } else {
                Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storageDir;
    }

    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private void handleBigCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            setPic();
        }
    }

    private void setPic() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        compressImage(mCurrentPhotoPath);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        ivPictureSanitationFacility.setImageBitmap(bitmap);
    }

    public String compressImage(String imageUri) {
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        try {
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename(imageUri);
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename(String imageUri) {
        File file = new File(imageUri);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath());
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private File setUpPhotoFile_1() {
        File f = null;
        try {
            f = createImageFile_1();
            mCurrentPhotoPath_1 = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    private File createImageFile_1() {
        // Create an image file name
        File imageF = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = JPEG_FILE_PREFIX_1 + timeStamp + "_";
            File albumF = getAlbumDir_1();
            imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageF;
    }

    private File getAlbumDir_1() {
        File storageDir = null;
        try {
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState())) {

                storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName_1());

                if (storageDir != null) {
                    if (!storageDir.mkdirs()) {
                        if (!storageDir.exists()) {
                            Log.d("CameraSample", "failed to create directory");
                            return null;
                        }
                    }
                }

            } else {
                Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storageDir;
    }

    private String getAlbumName_1() {
        return getString(R.string.album_name);
    }

    private void handleBigCameraPhoto_1() {
        if (mCurrentPhotoPath_1 != null) {
            setPic_1();
        }
    }

    private void setPic_1() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        compressImage_1(mCurrentPhotoPath_1);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath_1, bmOptions);
        ivPictureWashBasin.setImageBitmap(bitmap);
    }

    public String compressImage_1(String imageUri) {
        String filePath = getRealPathFromURI_1(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        try {
            float maxHeight = 816.0f;
            float maxWidth = 612.0f;
            float imgRatio = actualWidth / actualHeight;
            float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight;
                    actualWidth = (int) (imgRatio * actualWidth);
                    actualHeight = (int) maxHeight;
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth;
                    actualHeight = (int) (imgRatio * actualHeight);
                    actualWidth = (int) maxWidth;
                } else {
                    actualHeight = (int) maxHeight;
                    actualWidth = (int) maxWidth;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            actualHeight = options.outHeight;
            actualWidth = options.outWidth;
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize_1(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            float ratioX = actualWidth / (float) options.outWidth;
            float ratioY = actualHeight / (float) options.outHeight;
            float middleX = actualWidth / 2.0f;
            float middleY = actualHeight / 2.0f;
            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename_1(imageUri);
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String getFilename_1(String imageUri) {
        File file = new File(imageUri);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath());
        return uriSting;

    }

    private String getRealPathFromURI_1(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize_1(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditWash_Activity.this)
                .setMessage("Filled data will lost permanently")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
