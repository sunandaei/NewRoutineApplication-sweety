package com.sunanda.newroutine.application.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.util.LoadingDialog;
import com.sunanda.newroutine.application.util.SessionManager;

public class ChangePasswordActivity extends BaseActivity {

    private static final String TAG = "ChangePasswordActivity";
    private Toolbar toolbar;
    private TextView phone_number, email_address;
    Button cancel_btn,submit_btn;
    private EditText input_old_password, input_new_password, input_retype_password;
    private LoadingDialog loadingDialog;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loadingDialog = new LoadingDialog(this);
        sessionManager = new SessionManager(this);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);

        phone_number = (TextView) findViewById(R.id.phone_number);
        email_address = (TextView) findViewById(R.id.email_address);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        submit_btn = (Button) findViewById(R.id.submit_btn);
        input_new_password = (EditText) findViewById(R.id.input_new_password);
        input_old_password = (EditText) findViewById(R.id.input_old_password);
        input_retype_password = (EditText) findViewById(R.id.input_retype_password);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ChangePasswordActivity.this, EditAccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChangePass();
            }
        });
    }

    private void ChangePass() {

        /*loadingDialog.showDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Webservice api = retrofit.create(Webservice.class);
        Call<ResponseBody> call = api.ChangePassword(sessionManager.getLoggedId(),input_old_password.getText().toString(),
                                                    input_new_password.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body()!=null)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String str = jsonObject.getString("success");
                        if (str.equalsIgnoreCase("1"))
                        {
                            Intent intent;
                            intent = new Intent(ChangePasswordActivity.this, EditAccountActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
                            Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                    "Invalid user.....", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.RED);
                            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            snackbar.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                loadingDialog.hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                loadingDialog.hideDialog();
            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent;
                intent = new Intent(this, EditAccountActivity.class);
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
        intent = new Intent(this, EditAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_right_out);
        finish();
    }

}
