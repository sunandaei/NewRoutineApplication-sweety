package com.sunanda.newroutine.application.ui;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.ui.schoolomas.RecycleBinSchoolOMAS_Activity;

public class RecycleViewApp_Activity extends AppCompatActivity {

    LinearLayout llRoutine, llSchool, llOMAS, llback, llSchoolOMAS;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_recycle_view_activity);

        llRoutine = findViewById(R.id.llRoutine);
        llSchool = findViewById(R.id.llSchool);
        llOMAS = findViewById(R.id.llOMAS);
        llback = findViewById(R.id.llback);
        llSchoolOMAS = findViewById(R.id.llSchoolOMAS);

        animationDrawable = (AnimationDrawable) llback.getBackground();

        // setting enter fade animation duration to 5 seconds
        animationDrawable.setEnterFadeDuration(2000);

        // setting exit fade animation duration to 2 seconds
        animationDrawable.setExitFadeDuration(2000);

        llRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewApp_Activity.this, RecycleBin_Activity.class);
                startActivity(intent);
            }
        });

        llSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewApp_Activity.this, RecycleBinSchool_Activity.class);
                startActivity(intent);
            }
        });

        llOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewApp_Activity.this, RecycleBinOmas_Activity.class);
                startActivity(intent);
            }
        });

        llSchoolOMAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecycleViewApp_Activity.this, RecycleBinSchoolOMAS_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }
}
