package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.t1r2340.spotifystats.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            finish();
        }

        setupButtons();
    }

    /**
     * Sets up the buttons in the activity
     */
    private void setupButtons() {
        setupExitButton();
        setupDeleteButton();
    }

    /**
     * Sets up the delete account button
     */
    private void setupDeleteButton() {
        binding.btnDeleteAccount.setOnClickListener(v -> deleteAccount());
    }

    /**
     * Sets up the exit button to finish the activity
     */
    private void setupExitButton() {
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, FireBaseActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Deletes the user's account from Firebase
     */
    private void deleteAccount() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(task -> {
                    Toast.makeText(SettingsActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

}