package com.sunanda.newroutine.application.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.util.DrawableImageView;

import java.io.FileOutputStream;

public class ImageMarking_Activity extends Activity implements OnClickListener {

    DrawableImageView choosenImageView;
    Button savePicture;

    Bitmap bmp;
    Bitmap alteredBitmap;
    Uri url;
    String url1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_marking_activity);

        Intent intent = getIntent();
        url1 = intent.getStringExtra("URL");
        url = Uri.parse(url1);

        choosenImageView = this.findViewById(R.id.ChoosenImageView);
        savePicture = this.findViewById(R.id.SavePictureButton);
        savePicture.setOnClickListener(this);
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(url1, bmOptions);

            alteredBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), bitmap.getConfig());

            choosenImageView.setNewImage(alteredBitmap, bitmap);
        } catch (Exception e) {
            Log.v("ERROR", e.toString());
        }
    }

    public void onClick(View v) {
        if (alteredBitmap != null) {
            ContentValues contentValues = new ContentValues(10);
            contentValues.put(Media.DISPLAY_NAME, "Draw On Me");
            try {
                FileOutputStream out = new FileOutputStream(url1);
                alteredBitmap.compress(CompressFormat.JPEG, 100, out);
                Toast t = Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT);
                t.show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", url1);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } catch (Exception e) {
                Log.v("EXCEPTION", e.getMessage());
            }
        }
    }
}