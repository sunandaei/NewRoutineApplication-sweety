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
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.adapter.EditSanitarySurveyQuesAnsSchool_Adapter;
import com.sunanda.newroutine.application.adapter.OnOptionSelected;
import com.sunanda.newroutine.application.database.DatabaseHandler;
import com.sunanda.newroutine.application.modal.CommonModel;
import com.sunanda.newroutine.application.modal.SampleModel;
import com.sunanda.newroutine.application.ui.NavigationBar_Activity;
import com.sunanda.newroutine.application.util.CGlobal;
import com.sunanda.newroutine.application.util.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditSanitarySurveyQuesAnsSchool_Activity extends NavigationBar_Activity implements OnOptionSelected {

    DatabaseHandler databaseHandler;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editsanitarysurveyquesans_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        databaseHandler = new DatabaseHandler(EditSanitarySurveyQuesAnsSchool_Activity.this);

        int iId1 = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        SampleModel sampleModel1 = databaseHandler.getSchoolAppDataCollectionEdit(iId1);
        //String sourceTypeName = databaseHandler.getSourceTypeSingle(sampleModel1.getSource_type_q_6());

        if (sampleModel1.getSource_type_q_6().equalsIgnoreCase("MUNICIPAL PIPED WATER SUPPLY SCHEME")) {
            String subSourceTypeId = databaseHandler.getSubSourceTypeSingleId(sampleModel1.getSub_source_type());

            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sampleModel1.getSub_source_type()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, subSourceTypeId).commit();
        } else if (sampleModel1.getSource_type_q_6().equalsIgnoreCase("OWN PIPED WATER SUPPLY ARRANGEMENT")) {
            String subSourceTypeId = databaseHandler.getSubSourceTypeSingleId(sampleModel1.getSub_source_type());

            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sampleModel1.getSub_source_type()).commit();

            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, subSourceTypeId).commit();
        } else if (sampleModel1.getSource_type_q_6().equalsIgnoreCase("OTHERS")) {
            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sampleModel1.getSource_type_q_6()).commit();

            /*CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sampleModel1.getSourceTypeId_q_6()).commit();*/
        } else {
            CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, sampleModel1.getSource_type_q_6()).commit();

            /*CGlobal.getInstance().getPersistentPreferenceEditor(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .putString(Constants.PREFS_WATER_SOURCE_TYPE_ID, sampleModel1.getSourceTypeId_q_6()).commit();*/
        }

        init();
    }

    SampleModel sampleModel;
    String sTaskIdx;

    @Override
    protected void onResume() {
        super.onResume();
        llSanitarySurveyImage.setVisibility(View.GONE);
        sampleModel = new SampleModel();
        int iId = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        DatabaseHandler databaseHandler = new DatabaseHandler(EditSanitarySurveyQuesAnsSchool_Activity.this);
        /*try {
            sTaskIdx = databaseHandler.getTaskIdOne();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String sFCID = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getString(Constants.PREFS_USER_FACILITATOR_ID, "");
        sampleModel = databaseHandler.getSanitarySurveyEditSchool(iId, sFCID);

        String sSourceTypeName = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, "");
        if (!TextUtils.isEmpty(sampleModel.getAns_W_S_Q_9())) {
            if (!sSourceTypeName.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
                if (!sSourceTypeName.equalsIgnoreCase("OTHERS")) {
                    if (!sSourceTypeName.equalsIgnoreCase("TANKER WATER")) {
                        if (!sSourceTypeName.equalsIgnoreCase("WASH BASIN")) {
                            if (!sSourceTypeName.equalsIgnoreCase("WASH  BASIN")) {
                                if (sampleModel.getAns_W_S_Q_9().equalsIgnoreCase("1")) {
                                    if (!TextUtils.isEmpty(sampleModel.getSanitary_W_S_Q_img())) {
                                        llSanitarySurveyImage.setVisibility(View.VISIBLE);
                                        mCurrentPhotoPath = sampleModel.getSanitary_W_S_Q_img();
                                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                        bmOptions.inJustDecodeBounds = true;
                                        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                                        bmOptions.inJustDecodeBounds = false;
                                        bmOptions.inPurgeable = true;
                                        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                                        ivPicture.setImageBitmap(bitmap);
                                    } else {
                                        llSanitarySurveyImage.setVisibility(View.GONE);
                                    }
                                } else {
                                    llSanitarySurveyImage.setVisibility(View.GONE);
                                }
                            } else {
                                llSanitarySurveyImage.setVisibility(View.GONE);
                            }
                        } else {
                            llSanitarySurveyImage.setVisibility(View.GONE);
                        }
                    } else {
                        llSanitarySurveyImage.setVisibility(View.GONE);
                    }
                } else {
                    llSanitarySurveyImage.setVisibility(View.GONE);
                }
            } else {
                llSanitarySurveyImage.setVisibility(View.GONE);
            }
        } else {
            llSanitarySurveyImage.setVisibility(View.GONE);
        }

        btnSaveAndExit.setText("SAVE");
    }

    RecyclerView rvQusAns;
    Button btnSaveAndExit, btnTakeImage;
    ArrayList<CommonModel> cmaSurveyQuesAns = new ArrayList<>();
    SampleModel sampleSurveyQuesAns;
    LinearLayout llSanitarySurveyImage;
    ImageView ivPicture;
    private static final int CAMERA_REQUEST = 119;
    private String mCurrentPhotoPath;

    private void init() {
        create("Sanitary Survey(Edit)");

        rvQusAns = findViewById(R.id.rvQusAns);

        btnSaveAndExit = findViewById(R.id.btnSaveAndExit);

        btnTakeImage = findViewById(R.id.btnTakeImage);

        llSanitarySurveyImage = findViewById(R.id.llSanitarySurveyImage);

        ivPicture = findViewById(R.id.ivPicture);

        btnTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (Exception ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(EditSanitarySurveyQuesAnsSchool_Activity.this,
                                "com.sunanda.newroutine.application.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
            }
        });

        btnSaveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this);
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
        String sSourceTypeName = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, "");

        if (sSourceTypeName.equalsIgnoreCase("TANKER WATER")) {

            new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .setMessage("Sanitary Survey not available")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                            dialog.dismiss();
                        }
                    }).show();

        } else if (sSourceTypeName.equalsIgnoreCase("OTHERS")) {

            new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .setMessage("Sanitary Survey not available")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            String sSourceTypeId = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .getString(Constants.PREFS_WATER_SOURCE_TYPE_ID, "");
            cmaSurveyQuesAns = databaseHandler.getSurveyQuestion(sSourceTypeId);

            questionAdapter = new EditSanitarySurveyQuesAnsSchool_Adapter();
            questionAdapter.setOnOptionSelected(EditSanitarySurveyQuesAnsSchool_Activity.this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            questionAdapter.setQuestionModels(EditSanitarySurveyQuesAnsSchool_Activity.this, cmaSurveyQuesAns);
            rvQusAns.setLayoutManager(layoutManager);
            rvQusAns.setAdapter(questionAdapter);
        }

    }

    private EditSanitarySurveyQuesAnsSchool_Adapter questionAdapter;
    String sAnswer = "";

    @Override
    public void onOptionSelected(int position, int itemSelected) {
        String sSourceTypeName = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, "");
        switch (itemSelected) {
            case 1:
                cmaSurveyQuesAns.get(position).setOp1Sel(true);
                sAnswer = "1";
                if (sSourceTypeName.equalsIgnoreCase("TUBE WELL MARK II") || sSourceTypeName.equalsIgnoreCase("TUBE WELL ORDINARY")) {
                    if (position == 8) {
                        llSanitarySurveyImage.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 2:
                cmaSurveyQuesAns.get(position).setOp2Sel(true);
                sAnswer = "0";
                if (sSourceTypeName.equalsIgnoreCase("TUBE WELL MARK II") || sSourceTypeName.equalsIgnoreCase("TUBE WELL ORDINARY")) {
                    if (position == 8) {
                        llSanitarySurveyImage.setVisibility(View.GONE);
                    }
                }
                break;
        }

        int iId = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);

        databaseHandler.updateQuestionSchoolAppDataCollection(iId, position, cmaSurveyQuesAns.get(position).getQuestionid(), sAnswer);

        questionAdapter.setQuestionModels(EditSanitarySurveyQuesAnsSchool_Activity.this, cmaSurveyQuesAns);
        questionAdapter.notifyDataSetChanged();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            handleBigCameraPhoto();
        } /*else {
            mCurrentPhotoPath = null;
        }*/
    }

    private static final String JPEG_FILE_PREFIX = "img_sanitary_";
    private static final String JPEG_FILE_SUFFIX = ".png";

    /*private File setUpPhotoFile() {
        File f = null;
        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }*/

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
        ivPicture.setImageBitmap(bitmap);

        int id = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        databaseHandler.updateImageFileSurveySchool(id, mCurrentPhotoPath);
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
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//      check the rotation of the image and display it properly
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
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

    private void checkSave() {
        int iId = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getInt(Constants.PREFS_SEARCH_CLICK_ID, 0);
        String sSourceTypeName = CGlobal.getInstance().getPersistentPreference(EditSanitarySurveyQuesAnsSchool_Activity.this)
                .getString(Constants.PREFS_WATER_SOURCE_TYPE_NAME, "");
        sampleSurveyQuesAns = new SampleModel();
        sampleSurveyQuesAns = databaseHandler.getSchoolAppDataCollectionQuestion(iId);
        if (sSourceTypeName.equalsIgnoreCase("WATER TREATMENT UNIT/FILTER")) {
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_1())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 1")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_2())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 2")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_3())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 3")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        } else if (sSourceTypeName.equalsIgnoreCase("WASH BASIN") || sSourceTypeName.equalsIgnoreCase("WASH  BASIN")) {
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_1())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 1")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_2())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 2")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_3())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 3")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_4())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 4")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        } else if (sSourceTypeName.equalsIgnoreCase("RCC TANK/PLASTIC TANK")) {
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_1())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 1")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_2())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 2")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_3())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 3")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_4())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 4")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_5())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 5")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_6())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 6")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_7())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 7")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_8())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 8")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_9())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 9")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        } else if (sSourceTypeName.equalsIgnoreCase("TANKER WATER")) {

            new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .setMessage("Sanitary Survey not available")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else if (sSourceTypeName.equalsIgnoreCase("OTHERS")) {

            new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                    .setMessage("Sanitary Survey not available")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

        } else {
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_1())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 1")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_2())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 2")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_3())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 3")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_4())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 4")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }

            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_5())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 5")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_6())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 6")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_7())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 7")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_8())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 8")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_9())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 9")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_10())) {
                new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                        .setMessage("Please Answer Question no 10")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
            if (sSourceTypeName.equalsIgnoreCase("DUG WELL")) {
                if (TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_11())) {
                    new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                            .setMessage("Please Answer Question no 11")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
            }
        }
        if (sSourceTypeName.equalsIgnoreCase("TUBE WELL MARK II") || sSourceTypeName.equalsIgnoreCase("TUBE WELL ORDINARY")) {
            if (!TextUtils.isEmpty(sampleSurveyQuesAns.getQuestionsid_9())) {
                if (sampleSurveyQuesAns.getAns_W_S_Q_9().equalsIgnoreCase("1")) {
                    if (TextUtils.isEmpty(sampleSurveyQuesAns.getSanitary_W_S_Q_img())) {
                        new AlertDialog.Builder(EditSanitarySurveyQuesAnsSchool_Activity.this)
                                .setMessage("Please Capture Image")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                        return;
                    }
                }
            }
        }

        finish();
    }
}
