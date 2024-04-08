package com.t1r2340.spotifystats;

import android.content.Intent;
import android.net.Uri;
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
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
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
        setupSignOutButton();
    }

    private void setupSignOutButton() {
        binding.btnLogout.setOnClickListener(v -> {
            signOutAccount();
            signOutSpotifyAccount();
        });
    }

    /**
     * Sets up the delete account button
     */
    private void setupDeleteButton() {
        binding.btnDeleteAccount.setOnClickListener(v -> {
            deleteAccount();
            signOutSpotifyAccount();
        });
    }

    /**
     * Sets up the exit button to finish the activity
     */
    private void setupExitButton() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void signOutAccount() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    finish();
                });
    }

    private void signOutSpotifyAccount() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.spotify.com/logout/"));
        Toast.makeText(SettingsActivity.this, "Sign Out of the Spotify App", Toast.LENGTH_SHORT).show();
        startActivity(intent);
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