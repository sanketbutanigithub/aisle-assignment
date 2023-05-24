package com.example.aisle_assignment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aisle_assignment.R;
import com.example.aisle_assignment.adapters.LikeAdapter;
import com.example.aisle_assignment.interfaces.ApiClient;
import com.example.aisle_assignment.models.Profile;
import com.example.aisle_assignment.utils.Constant;
import com.example.aisle_assignment.utils.DialogUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteFragment extends Fragment {

    Context context;
    List<Profile> profileList;
    RoundedImageView imgprofile;
    TextView txtnameage;
    RecyclerView rvLikes;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        initView(view);
        initvalue();
        setViewData();
        initClickListeners();
        return view;
    }

    private void initView(View view) {
        imgprofile = view.findViewById(R.id.imgprofile);
        txtnameage = view.findViewById(R.id.txtnameage);
        rvLikes = view.findViewById(R.id.rvLikes);

    }

    private void initvalue() {
        context = getContext();
        profileList = new ArrayList<>();
        DialogUtils.showDialog(getContext());
        Call<ResponseBody> call = ApiClient.api.getNotes(Constant.token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("profileArray", "" + response);
                    if (response.isSuccessful() && response.code() == 200) {
                        DialogUtils.dialogDismiss();
                        String res = response.body().string();
                        JSONObject profileJson = new JSONObject(res);
                        JSONObject invitesJson = profileJson.getJSONObject("invites");
                        JSONArray profileArray = invitesJson.getJSONArray("profiles");
                        JSONObject profileFirstObject = profileArray.getJSONObject(0);
                        JSONObject informationJson = profileFirstObject.getJSONObject("general_information");
                        JSONArray photoArray = profileFirstObject.getJSONArray("photos");
                        JSONObject photoObject = photoArray.getJSONObject(0);
                        Glide.with(context).load(photoObject.getString("photo")).into(imgprofile);

                        JSONObject likesJson = profileJson.getJSONObject("likes");
                        JSONArray profileJsonArray = likesJson.getJSONArray("profiles");
                        for (int i = 0; i < profileJsonArray.length(); i++) {
                            String fname = profileJsonArray.getJSONObject(i).getString("first_name");
                            String avatar = profileJsonArray.getJSONObject(i).getString("avatar");
                            profileList.add(new Profile(fname, avatar));
                        }
                        LikeAdapter likeAdapter = new LikeAdapter(context, profileList);
                        txtnameage.setText(informationJson.getString("first_name") + " , " + informationJson.getString("age"));
                        rvLikes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                        rvLikes.setAdapter(likeAdapter);

                        Log.e("res profile", "" + profileList);
                        Log.e("profileArray", "" + informationJson.getString("first_name"));
                    } else {
                        DialogUtils.dialogDismiss();
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                DialogUtils.dialogDismiss();
            }
        });
    }

    private void setViewData() {

    }

    private void initClickListeners() {

    }


}