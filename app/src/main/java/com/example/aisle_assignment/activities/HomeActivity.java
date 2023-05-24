package com.example.aisle_assignment.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aisle_assignment.R;
import com.example.aisle_assignment.databinding.ActivityProfileBinding;
import com.example.aisle_assignment.fragments.DiscoverFragment;
import com.example.aisle_assignment.fragments.MatchesFragment;
import com.example.aisle_assignment.fragments.NoteFragment;
import com.example.aisle_assignment.fragments.ProfileFragment;
import com.example.aisle_assignment.models.Profile;
import com.example.aisle_assignment.utils.Constant;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityProfileBinding binding;
    Intent intent;
    String token;
    Context context;
    List<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initvalue();
        setViewData();
        initClickListeners();
    }

    private void initvalue() {
        intent = getIntent();
        token = intent.getStringExtra("token");
        Constant.token = token;
        context = this;
        profileList = new ArrayList<>();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        binding.bottomNavigation.setSelectedItemId(R.id.note);
        BadgeDrawable noteBadge = binding.bottomNavigation.getOrCreateBadge(R.id.note);
        noteBadge.setNumber(5);
        noteBadge.setBackgroundColor(getResources().getColor(R.color.bagde_color));
        BadgeDrawable matchesBadge = binding.bottomNavigation.getOrCreateBadge(R.id.matches);
        matchesBadge.setNumber(50);
        matchesBadge.setBackgroundColor(getResources().getColor(R.color.bagde_color));

    }

    private void setViewData() {
    }

    private void initClickListeners() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.discover:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new DiscoverFragment())
                        .commit();
                return true;

            case R.id.note:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new NoteFragment())
                        .commit();
                return true;

            case R.id.matches:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new MatchesFragment())
                        .commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, new ProfileFragment())
                        .commit();
                return true;
        }
        return false;
    }
}