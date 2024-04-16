package com.t1r2340.spotifystats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.t1r2340.spotifystats.helpers.FailureCallback;

import java.util.Arrays;
import java.util.List;


public class FireBaseActivity extends AppCompatActivity implements FailureCallback {

  private String mAccessToken;

  private final ActivityResultLauncher<Intent> signInLauncher =
      registerForActivityResult(
          new FirebaseAuthUIActivityResultContract(), result -> onSignInResult(result));

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fire_base);
    createSignInIntent();
  }

  protected void onStart() {
    super.onStart();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    if (currentUser == null) {
      createSignInIntent();
    }
  }

  public void createSignInIntent() {
    List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

    Intent signInIntent =
        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setLogo(R.drawable.unwrappd_white).build();
    signInLauncher.launch(signInIntent);
    Log.d("Firebase", "Sign in intent launched");
  }

  private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
    IdpResponse response = result.getIdpResponse();
    if (result.getResultCode() == RESULT_OK) {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      Toast.makeText(this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
      getToken();

    } else {
      Log.d("Firebase", "Login failed");
    }
  }

  /**
   * Get token from Spotify This method will open the Spotify login activity and get the token What
   * is token? https://developer.spotify.com/documentation/general/guides/authorization-guide/
   */
  public void getToken() {
    final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
    AuthorizationClient.openLoginActivity(FireBaseActivity.this, 0, request);
  }

  /**
   * When the app leaves this activity to momentarily get a token/code, this function fetches the
   * result of that external activity to get the response from Spotify
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

    // Check which request code is present (if any)
    if (0 == requestCode) {
      mAccessToken = response.getAccessToken();
      Log.d("Firebase", "Spotify token received: " + mAccessToken);

      HomePageFragment fragment = new HomePageFragment();
      Bundle bundle = new Bundle();
      bundle.putString("accessToken", mAccessToken);
      fragment.setArguments(bundle);
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, fragment).commit();

    }
  }

  // TODO: Implement into wrapped generation
  /**
   * Creates a UI thread to update a TextView in the background Reduces UI latency and makes the
   * system perform more consistently
   *
   * @param text the text to set
   * @param textView TextView object to update
   */
  private void setTextAsync(final String text, TextView textView) {
    runOnUiThread(() -> textView.setText(text));
  }

  /**
   * Get authentication request
   *
   * @param type the type of the request
   * @return the authentication request
   */
  private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
    String clientId = getString(R.string.client_id);
    String redirectUri = getString(R.string.redirect_uri);
    return new AuthorizationRequest.Builder(clientId, type, Uri.parse(redirectUri).toString())
        .setShowDialog(true)
        .setScopes(
            new String[] {
              "user-read-email", "user-follow-read", "user-top-read"
            }) // <--- Change the scope of your requested token here
        .setCampaign("your-campaign-token")
        .build();
  }

  @Override
  public void onFailure(Exception e) {
    Log.d("HTTP", "Failed to fetch data: " + e);
    Toast.makeText(FireBaseActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
  }

  // TODO: INclude in wrapped generation
  //    private void cancelCall() {
  //        if (mCall != null) {
  //            mCall.cancel();
  //        }
  //    }

  // TODO: include in wrapped generation
  //    @Override
  //    protected void onDestroy() {
  //        cancelCall();
  //        spotifyAppRemoteHelper.disconnect(); // TODO: Add this for app remote use cases
  //        super.onDestroy();
  //    }
}
