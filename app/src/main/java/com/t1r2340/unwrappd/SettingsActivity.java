package com.t1r2340.unwrappd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.t1r2340.unwrappd.databinding.ActivitySettingsBinding;
import com.t1r2340.unwrappd.dialog.ChangePasswordDialogFragment;
import com.t1r2340.unwrappd.dialog.ChangeUsernameDialogFragment;
import com.t1r2340.unwrappd.helpers.AuthHelper;

public class SettingsActivity extends AppCompatActivity {

  private ActivitySettingsBinding binding;

  private FirebaseUser user;
  private AuthHelper authHelper;

  private static boolean isSpotifySignOut;

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
    authHelper = new AuthHelper(this);

    isSpotifySignOut = false;

    setupButtons();
  }

  /** Sets up the buttons in the activity */
  private void setupButtons() {
    setupExitButton();
    setupChangeUsernameButton();
    setupChangePasswordButton();
    setupSignOutButton();
    setupDeleteButton();
  }

  private void setupChangeUsernameButton() {
    binding.btnChangeUsername.setOnClickListener(
        v -> {
          new ChangeUsernameDialogFragment()
              .show(getSupportFragmentManager(), "ChangeUsernameDialogFragment");
        });
  }

  private void setupChangePasswordButton() {
    binding.btnChangePassword.setOnClickListener(
        v -> {
          new ChangePasswordDialogFragment()
              .show(getSupportFragmentManager(), "ChangePasswordDialogFragment");
        });
  }

  private void setupSignOutButton() {
    binding.btnLogout.setOnClickListener(
        v -> {
          signOutAccount();
          signOutSpotifyAccount();
        });
  }

  /** Sets up the delete account button */
  private void setupDeleteButton() {
    binding.btnDeleteAccount.setOnClickListener(
        v -> {
          deleteAccount();
          signOutSpotifyAccount();
        });
  }

  /** Sets up the exit button to finish the activity */
  private void setupExitButton() {
    binding.toolbar.setNavigationOnClickListener(v -> finish());
  }

  private void signOutAccount() {
    AuthUI.getInstance()
        .signOut(this)
        .addOnCompleteListener(
            task -> {
              finish();
            });
  }

  private void signOutSpotifyAccount() {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.spotify.com/logout/"));
    Toast.makeText(SettingsActivity.this, "Sign Out of Spotify", Toast.LENGTH_SHORT).show();
    isSpotifySignOut = true;
    startActivity(intent);
  }

  /** Deletes the user's account from Firebase */
  private void deleteAccount() {
    AuthUI.getInstance()
        .delete(this)
        .addOnCompleteListener(
            task -> {
              Toast.makeText(SettingsActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
              startActivity(new Intent(this, HomePageActivity.class));
              finish();
            });
  }


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        if (isSpotifySignOut) {
            isSpotifySignOut = false;
            authHelper.getToken();
            startActivity(new Intent(this, HomePageActivity.class));
        }
    }
}
