package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.t1r2340.spotifystats.databinding.ActivityHomePageBinding;
import com.t1r2340.spotifystats.dialog.TimeRangeDialog;
import com.t1r2340.spotifystats.helpers.AuthHelper;

public class HomePageActivity extends AppCompatActivity {

  private ActivityHomePageBinding binding;
  private AuthHelper authHelper;
  private String accessToken;

  private final ActivityResultLauncher<Intent> signInLauncher =
          registerForActivityResult(
                  new FirebaseAuthUIActivityResultContract(), result -> onSignInResult(result));

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityHomePageBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    setupButtons();

    authHelper = new AuthHelper(this);
    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
      Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
      authHelper.getToken();
    } else {
      authHelper.createSignInIntent(signInLauncher);
    }

  }

  private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
    if (result.getResultCode() == RESULT_OK) {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
      authHelper.getToken();
    } else {
      Log.d("Firebase", "Login failed");
      authHelper.createSignInIntent(signInLauncher);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

    // Check which request code is present (if any)
    if (0 == requestCode) {
      accessToken = response.getAccessToken();
      Log.d("Firebase", "Spotify token received: " + accessToken);
      setupButtons();
    }

  }


  private void setupButtons() {

    setupSettingsButton();
    setupCreateWrappedButton();
    setupPrevWrappedButton();
  }

  private void setupCreateWrappedButton() {

    Button createWrappedButton = binding.btnCreateWrapped;

    // TODO: Create dropdown dialog for time range

    createWrappedButton.setOnClickListener(
        v -> {
          TimeRangeDialog dialog = new TimeRangeDialog(accessToken);
          dialog.show(getSupportFragmentManager(), "TimeRangeDialog");
        });
  }

  private void setupPrevWrappedButton() {
    Button prevWrappedButton = binding.btnViewWrappeds;

    prevWrappedButton.setOnClickListener(
        v -> {
          // TODO: Make PrevWrappedFragment an activity
          Intent intent = new Intent(this, PrevWrappedsActivity.class);
          intent.putExtra("accessToken", accessToken);
          startActivity(intent);
        }
    );
  }

  private void setupSettingsButton() {

    ImageButton settingsButton = binding.btnSettings;

    settingsButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(this, SettingsActivity.class);
          startActivity(intent);
        });
  }

}
