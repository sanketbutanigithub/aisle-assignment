package com.example.aisle_assignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aisle_assignment.databinding.ActivityOtpVerificationBinding;
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

public class OtpVerificationActivity extends AppCompatActivity {
    ActivityOtpVerificationBinding binding;
    String phoneNumber;
    String countryCode;
    String otp;
    String token;
    Intent intent;
    Context context;
    HashMap<String,String> otpMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initvalue();
        setViewData();
        initClickListeners();
    }

    private void initvalue() {
        intent = getIntent();
        phoneNumber = intent.getStringExtra("number");
        countryCode = intent.getStringExtra("code");
        context = this;
        otpMap = new HashMap<>();
    }

    private void setViewData() {
        binding.txtPhonenumber.setText(countryCode+phoneNumber);
    }

    private void initClickListeners() {
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = binding.edtOtp.getText().toString();
                if(!otp.isEmpty()){
                    otpMap.put("number",countryCode+phoneNumber);
                    otpMap.put("otp",otp);
                    DialogUtils.showDialog(context);
                    Call<ResponseBody> call = ApiClient.api.optApi(otpMap);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                DialogUtils.dialogDismiss();
                                try {
                                    String res = response.body().string();
                                    JSONObject otpJson = new JSONObject(res);
                                    token = otpJson.getString("token");
                                    Log.e("token",token);
                                    if(token!="null"){
                                        Intent profileScreenIntent = new Intent(context, HomeActivity.class);
                                        profileScreenIntent.putExtra("token",token);
                                        startActivity(profileScreenIntent);
                                    }else {
                                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                    Log.e("response",res);
                                } catch (IOException | JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }else {
                                DialogUtils.dialogDismiss();
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("Error",t.getMessage());
                        }
                    });
                }else {
                    Toast.makeText(context, "Enter Otp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}