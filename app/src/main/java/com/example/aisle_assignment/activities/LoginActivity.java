package com.example.aisle_assignment.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.aisle_assignment.databinding.ActivityLoginBinding;
import com.example.aisle_assignment.interfaces.ApiClient;
import com.example.aisle_assignment.utils.DialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Context context;
    String phoneNumber;
    String countyCode;
    HashMap<String, String> loginMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initvalue();
        setViewData();
        initClickListeners();
    }

    private void initvalue() {
        context = this;
        loginMap = new HashMap<>();
    }

    private void setViewData() {
    }

    private void initClickListeners() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(context);
                phoneNumber = binding.edtPhonenumber.getText().toString();
                countyCode = binding.edtCode.getText().toString();
                if (!phoneNumber.isEmpty() && !countyCode.isEmpty()) {
                    loginMap.put("number", countyCode + phoneNumber);
                    Call<ResponseBody> call = ApiClient.api.loginApi(loginMap);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    DialogUtils.dialogDismiss();
                                    String res = response.body().string();
                                    Log.e("api", "" + res);
                                    JSONObject loginJson = new JSONObject(res);
                                    boolean status = loginJson.getBoolean("status");
                                    if (status) {
                                        Intent otpScreenIntent = new Intent(context, OtpVerificationActivity.class);
                                        otpScreenIntent.putExtra("number",phoneNumber);
                                        otpScreenIntent.putExtra("code",countyCode);
                                        startActivity(otpScreenIntent);
                                    } else {
                                        DialogUtils.dialogDismiss();
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (IOException | JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                DialogUtils.dialogDismiss();
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            DialogUtils.dialogDismiss();
                        }
                    });
//                    Intent otpScreenIntent = new Intent(context,OtpVerificationActivity.class);
//                    startActivity(otpScreenIntent);
                } else {
                    Toast.makeText(context, "Enter all filed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}