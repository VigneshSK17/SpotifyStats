package com.t1r2340.spotifystats;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.t1r2340.spotifystats.helpers.FailureCallback;
import com.t1r2340.spotifystats.helpers.FirestoreHelper;
import com.t1r2340.spotifystats.helpers.SpotifyApi;
import com.t1r2340.spotifystats.models.api.TopArtists;
import com.t1r2340.spotifystats.models.api.TopTracks;
import com.t1r2340.spotifystats.models.api.TrackObject;
import com.t1r2340.spotifystats.models.api.Wrapped;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements FailureCallback {

  public static final String CLIENT_ID = "1d7e65ad5ac447908a52e0b5de50ca92";
  public static final String REDIRECT_URI = "spotifystats://auth";

  public static final int AUTH_TOKEN_REQUEST_CODE = 0;
  public static final int AUTH_CODE_REQUEST_CODE = 1;

  private final OkHttpClient mOkHttpClient = new OkHttpClient();
  private String mAccessToken, mAccessCode;
  private Call mCall;

  private TextView tokenTextView, codeTextView, profileTextView;

  private SpotifyApi spotifyApi;
  private FirestoreHelper firestore;
  private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
          new FirebaseAuthUIActivityResultContract(),
          new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
            @Override
            public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
              onSignInResult(result);
            }
          }
  );

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Initialize the views
    //tokenTextView = (TextView) findViewById(R.id.token_text_view);

    //profileTextView = (TextView) findViewById(R.id.response_text_view);

    // Initialize the buttons

    //Button profileBtn = (Button) findViewById(R.id.profile_btn);

    // Set the click listeners for the buttons
//
//    tokenBtn.setOnClickListener((v) -> {
//      getToken();
//    });
//
//    codeBtn.setOnClickListener((v) -> {
//      getCode();
//    });

//    profileBtn.setOnClickListener((v) -> {
//      //onGetUserProfileClicked();
//      getToken();
//    });
    getToken();

  }
  public void createSignInIntent() {
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build());

    Intent signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build();
    signInLauncher.launch(signInIntent);
  }
  private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
    IdpResponse response = result.getIdpResponse();
    if (result.getResultCode() == RESULT_OK) {
      // Successfully signed in
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      getCode();
    } else {
      Toast.makeText(this, "Sign In Failed!", Toast.LENGTH_SHORT).show();
    }
  }
  public void signOut() {
    AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                // ...
              }
            });
  }



  /**
   * Get token from Spotify
   * This method will open the Spotify login activity and get the token
   * What is token?
   * https://developer.spotify.com/documentation/general/guides/authorization-guide/
   */
  public void getToken() {
    final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
    AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_TOKEN_REQUEST_CODE, request);

  }

  /**
   * Get code from Spotify
   * This method will open the Spotify login activity and get the code
   * What is code?
   * https://developer.spotify.com/documentation/general/guides/authorization-guide/
   */
  public void getCode() {
    final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
    AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_CODE_REQUEST_CODE, request);
  }


  /**
   * When the app leaves this activity to momentarily get a token/code, this function
   * fetches the result of that external activity to get the response from Spotify
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

    // Check which request code is present (if any)
    if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
      mAccessToken = response.getAccessToken();
      //setTextAsync(mAccessToken,tokenTextView);

      spotifyApi = new SpotifyApi(this, mAccessToken, mOkHttpClient, mCall);
      firestore = new FirestoreHelper();
      onGetUserProfileClicked();
//      testFirestoreSave();
      testFirestoreGet();


    }
  }

  /**
   * Get user profile
   * This method will get the user profile using the token
   */
  public void onGetUserProfileClicked() {
    if (mAccessToken == null) {
      Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
      return;
    }

    // Create a request to get the user profile
//    spotifyApi.getProfile((SpotifyProfile jsonObject) -> {
//      Log.d("JSON", jsonObject.toString());
//    });

    spotifyApi.getTopArtists((TopArtists jsonObject) -> {
      Log.d("JSON", jsonObject.toString());
    }, 5, SpotifyApi.TimeRange.LONG_TERM);

    spotifyApi.getTopTracks((TopTracks jsonObject) -> {
      Log.d("JSON", jsonObject.toString());
      List<TrackObject> tracks = jsonObject.getItems();
      tracks.get(3).getAlbum();
    }, 5, SpotifyApi.TimeRange.SHORT_TERM);

    spotifyApi.getProfile((SpotifyProfile jsonObject) -> {
      Log.d("JSON", jsonObject.toString());
    });

  }

  /**
   * Creates a UI thread to update a TextView in the background
   * Reduces UI latency and makes the system perform more consistently
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
    return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
            .setShowDialog(false)
            .setScopes(new String[] { "user-read-email", "user-follow-read", "user-top-read" }) // <--- Change the scope of your requested token here
            .setCampaign("your-campaign-token")
            .build();
  }

  /**
   * Gets the redirect Uri for Spotify
   *
   * @return redirect Uri object
   */
  private Uri getRedirectUri() {
    return Uri.parse(REDIRECT_URI);
  }

  private void cancelCall() {
    if (mCall != null) {
      mCall.cancel();
    }
  }

  // TODO: Set firestore rules back to private
  private void testFirestoreSave() {
    spotifyApi.getProfile((SpotifyProfile profile) -> {
      spotifyApi.getTopArtists((TopArtists artists) -> {
        spotifyApi.getTopTracks((TopTracks tracks) -> {

          Set<String> genres = artists.getItems().stream().flatMap(a -> a.getGenres().stream()).collect(Collectors.toSet());
          Wrapped wrapped = new Wrapped(artists, tracks, new ArrayList<>(genres), Date.from(Instant.now()), "2MsvIukb7LTkA2d79eiWoo3ygTm1");
          firestore.storeWrapped(wrapped)
                  .addOnSuccessListener(a -> Log.d("FIRESTORE", "Stored wrapped"))
                  .addOnFailureListener(a -> Log.d("FIRESTORE", "Failed to store wrapped" + a));

        }, 10, SpotifyApi.TimeRange.MEDIUM_TERM);
      }, 10, SpotifyApi.TimeRange.MEDIUM_TERM);
    });
  }

  private void testFirestoreGet() {
    firestore.getWrappeds("2MsvIukb7LTkA2d79eiWoo3ygTm1", ds -> {
      Log.d("WRAPPED", ds.get(0).toString());
      Log.d("WRAPPED", ds.get(1).toString());
    });
  }

  @Override
  public void onFailure(Exception e) {
    Log.d("HTTP", "Failed to fetch data: " + e);
    Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
            Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onDestroy() {
    cancelCall();
    super.onDestroy();
  }
}
