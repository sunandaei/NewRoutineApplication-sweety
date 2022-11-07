package com.sunanda.newroutine.application.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class EditAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView image_first;
    private ImageView edit_imageview;
    private EditText input_name, input_last_name;
    private TextView phone_number, email_address, changepassword, deactivateaccount;
    private Button submit_btn;
    private Toolbar toolbar;
    private Dialog dialog;

    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private static final String IMAGE_FOLDER = "DealMart";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    boolean allgranted = false;
    boolean isCropped = false;
    private TextView camera, gallery, cancel;
    private boolean isFromGallery = false;
    private String path = "", FILE_NAME = "";

    private static final int GALLERY_IMAGE_LOAD_REQUEST = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    private static final int PIC_CROP = 3;
    private static final int PIC_CROP2 = 4;

    private Bitmap bitmap = null;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Account");
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        RuntimePermission();

        image_first = (CircleImageView) findViewById(R.id.image_first);
        edit_imageview = (ImageView) findViewById(R.id.edit_imageview);
        input_name = (EditText) findViewById(R.id.input_name);
        input_last_name = (EditText) findViewById(R.id.input_last_name);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        phone_number = (TextView) findViewById(R.id.phone_number);
        email_address = (TextView) findViewById(R.id.email_address);
        changepassword = (TextView) findViewById(R.id.changepassword);
        deactivateaccount = (TextView) findViewById(R.id.deactivateaccount);

        edit_imageview.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        changepassword.setOnClickListener(this);
        deactivateaccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_imageview:
                showDialog();
                break;
            case R.id.submit_btn:
                if (isCropped && bitmap != null)
                    if (input_name.getText().toString().length() == 0) {
                        input_name.setError("Enter First Name");
                        input_name.requestFocus();
                    } else if (input_last_name.getText().toString().length() == 0) {
                        input_last_name.setError("Enter Last Name");
                        input_last_name.requestFocus();
                    } else {
                        uploadBitmap(bitmap);
                    }
                else {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Please Select New Picture....", Snackbar.LENGTH_LONG);
                    /*View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);*/
                    snackbar.show();
                }
                break;
            case R.id.changepassword:
                Intent intent = new Intent(EditAccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.deactivateaccount:
                break;
            case R.id.selectCamera:
                captureImage();
                isFromGallery = false;
                dialog.dismiss();
                break;
            case R.id.select_gallary:
                isFromGallery = true;
                dialog.dismiss();
                Intent gallayIntent = new Intent(Intent.ACTION_PICK);
                gallayIntent.setType("image/*");
                startActivityForResult(gallayIntent, GALLERY_IMAGE_LOAD_REQUEST);
            case R.id.cancel:
                dismissDialog();
                break;
        }
    }

    public void showDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);
        camera = (TextView) dialog.findViewById(R.id.selectCamera);
        gallery = (TextView) dialog.findViewById(R.id.select_gallary);
        cancel = (TextView) dialog.findViewById(R.id.cancel);
        camera.setOnClickListener(this);
        gallery.setOnClickListener(this);
        cancel.setOnClickListener(this);

        dialog.show();
    }

    public void dismissDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public void RuntimePermission() {

        if (ActivityCompat.checkSelfPermission(EditAccountActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(EditAccountActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(EditAccountActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[2])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(EditAccountActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(EditAccountActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void captureImage() {

        if (Build.VERSION.SDK_INT >= 23 /*Build.VERSION_CODES.M*/) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //Log.d("Request for", "Camera Permission");
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            } else {// Already have permissions for CAMERA
                cameraIntent();
            }
        } else {// Build.VERSION.SDK_INT < 23
            cameraIntent();
        }
    }

    private void cameraIntent() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, IMAGE_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }
        Date d = new Date();
        CharSequence s = android.text.format.DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
        String imagename = "Image_" + System.currentTimeMillis() + s + ".jpeg";
        File file2 = new File(new File(file.getPath()), imagename);
        /*if (file2.exists()) {
            file2.delete();
        }*/
        path = Environment.getExternalStorageDirectory() + "/" + IMAGE_FOLDER + "/" + imagename;

        FILE_NAME = imagename;

        Uri imageUri = Uri.fromFile(file2);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                performCrop(Uri.fromFile(new File(path)));

            } else if (requestCode == GALLERY_IMAGE_LOAD_REQUEST) {

                if (data.getData() != null) {
                    selectedImageUri = data.getData();

                    FILE_NAME = data.getData().getPath();

                    performCropGalImage(selectedImageUri);
                }
            } else if (requestCode == PIC_CROP) {
                try {
                    if (data != null) {

                        bitmap = (Bitmap) data.getExtras().get("data");

                        isCropped = true;

                        String root = Environment.getExternalStorageDirectory().getPath();
                        File myDir = new File(root + "/" + IMAGE_FOLDER);
                        if (!myDir.exists()) {
                            myDir.mkdirs();
                        }
                        Date d = new Date();
                        CharSequence s = android.text.format.DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
                        String fname = "Image_" + System.currentTimeMillis() + s + ".jpeg";
                        File file = new File(myDir, fname);

                        FILE_NAME = fname;

                        //Log.d("TEST", file.getPath());
                        //if (file.exists ()) file.delete ();
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();

                            image_first.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Log.d("TEST!!", e.toString());
                        }
                    }
                } catch (Exception e) {
                    isCropped = true;

                    //Toast.makeText(this, "Feature is not supporting", Toast.LENGTH_SHORT).show();
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //Log.d("TEMP", path);
                    bitmap = BitmapFactory.decodeFile(path, bmOptions);
                    image_first.setImageBitmap(bitmap);
                }
            } else if (requestCode == PIC_CROP2) {
                try {
                    if (data != null) {

                        bitmap = (Bitmap) data.getExtras().get("data");

                        isCropped = true;

                        String root = Environment.getExternalStorageDirectory().getPath();
                        File myDir = new File(root + "/" + IMAGE_FOLDER);
                        if (!myDir.exists()) {
                            myDir.mkdirs();
                        }
                        Date d = new Date();
                        CharSequence s = android.text.format.DateFormat.format("MM-dd-yy hh-mm-ss", d.getTime());
                        String fname = "Image_" + System.currentTimeMillis() + s + ".jpeg";
                        File file = new File(myDir, fname);

                        FILE_NAME = fname;

                        //Log.d("TEST", file.getPath());
                        //if (file.exists ()) file.delete ();
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.flush();
                            out.close();

                            image_first.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Log.d("TEST!!", e.toString());
                        }
                    }
                } catch (Exception e) {
                    isCropped = true;

                    try {
                        //Toast.makeText(this, "Feature is not supporting", Toast.LENGTH_SHORT).show();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        image_first.setImageBitmap(bitmap);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        //Log.d("TEST!!", e1.toString());
                    }
                }
            }
            //dialog.dismiss();
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {
        Toast.makeText(this, "Uploading......", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(EditAccountActivity.this, permissionsRequired[2])) {
                Toast.makeText(getBaseContext(), "Permissions Required", Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(EditAccountActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(EditAccountActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void proceedAfterPermission() {
        //Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
        allgranted = true;
    }

    private void performCropGalImage(Uri picUri) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setData(picUri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PIC_CROP2);
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 250);
            cropIntent.putExtra("outputY", 250);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent;
                intent = new Intent(this, MyAccountActivity.class);
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
        intent = new Intent(this, MyAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
        finish();
    }
}
