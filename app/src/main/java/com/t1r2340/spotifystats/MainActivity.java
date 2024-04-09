package com.t1r2340.spotifystats;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.t1r2340.spotifystats.helpers.FailureCallback;
import com.t1r2340.spotifystats.helpers.FirestoreHelper;
import com.t1r2340.spotifystats.helpers.SpotifyApiHelper;
import com.t1r2340.spotifystats.helpers.SpotifyAppRemoteHelper;
import com.t1r2340.spotifystats.models.api.SpotifyProfile;
import com.t1r2340.spotifystats.models.api.TopArtists;
import com.t1r2340.spotifystats.models.api.TopTracks;
import com.t1r2340.spotifystats.models.api.TrackObject;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements FailureCallback {

  private final String CLIENT_ID = getString(R.string.client_id);
  private final String REDIRECT_URI = getString(R.string.redirect_uri);

  private final OkHttpClient mOkHttpClient = new OkHttpClient();
  private String mAccessToken, mAccessCode;
  private Call mCall;

  private TextView tokenTextView, codeTextView, profileTextView;

  private SpotifyApiHelper spotifyApiHelper;
  private SpotifyAppRemoteHelper spotifyAppRemoteHelper;
  private FirestoreHelper firestore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  /** Get user profile This method will get the user profile using the token */
  public void onGetUserProfileClicked() {
    if (mAccessToken == null) {
      Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
      return;
    }

    spotifyApiHelper.getTopArtists(
        (TopArtists jsonObject) -> {
          Log.d("JSON", jsonObject.toString());
        },
        5,
        SpotifyApiHelper.TimeRange.LONG_TERM);

    spotifyApiHelper.getTopTracks(
        (TopTracks jsonObject) -> {
          Log.d("JSON", jsonObject.toString());
          List<TrackObject> tracks = jsonObject.getItems();
          tracks.get(3).getAlbum();
        },
        5,
        SpotifyApiHelper.TimeRange.SHORT_TERM);

    spotifyApiHelper.getProfile(
        (SpotifyProfile jsonObject) -> {
          Log.d("JSON", jsonObject.toString());
        });
  }

  private void cancelCall() {
    if (mCall != null) {
      mCall.cancel();
    }
  }

  // TODO: Set firestore rules back to private
  private void testFirestoreSave() {
    spotifyApiHelper.getProfile(
        (SpotifyProfile profile) -> {
          spotifyApiHelper.getTopArtists(
              (TopArtists artists) -> {
                spotifyApiHelper.getTopTracks(
                    (TopTracks tracks) -> {
                      Set<String> genres =
                          artists.getItems().stream()
                              .flatMap(a -> a.getGenres().stream())
                              .collect(Collectors.toSet());
                      Wrapped wrapped =
                          new Wrapped(
                              artists,
                              tracks,
                              new ArrayList<>(genres),
                              Date.from(Instant.now()),
                              profile.getId(), SpotifyApiHelper.TimeRange.MEDIUM_TERM);
                      firestore
                          .storeWrapped(wrapped)
                          .addOnSuccessListener(a -> Log.d("FIRESTORE", "Stored wrapped"))
                          .addOnFailureListener(
                              a -> Log.d("FIRESTORE", "Failed to store wrapped" + a));
                    },
                    10,
                    SpotifyApiHelper.TimeRange.MEDIUM_TERM);
              },
              10,
              SpotifyApiHelper.TimeRange.MEDIUM_TERM);
        });
  }

  private void testFirestoreGet() {
    spotifyApiHelper.getProfile(
        (SpotifyProfile profile) -> {
          firestore.getWrappeds(
              profile.getId(),
              ds -> {
                Log.d("WRAPPED", ds.get(0).toString());
                Log.d("WRAPPED", ds.get(0).getTopTracks().getItems().get(0).getUri());
              });
        });
  }

  private void connectAppRemote() {
    spotifyAppRemoteHelper = new SpotifyAppRemoteHelper(CLIENT_ID, REDIRECT_URI, this);
  }

  // TODO: Clean this up, only use this when accessing music, make sure to disconnect when pause
  // pressed
//  private void testAppRemote() {
//    spotifyAppRemoteHelper.connectAndRun("spotify:track:6FGrBYBdIAS2asaP54AnZo");
//  }

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
        .setShowDialog(false)
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
    Toast.makeText(
            MainActivity.this,
            "Failed to fetch data, watch Logcat for more details",
            Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  protected void onDestroy() {
    cancelCall();
    spotifyAppRemoteHelper.disconnect();
    super.onDestroy();
  }
}
