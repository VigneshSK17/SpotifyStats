package com.t1r2340.unwrappd.helpers;

import android.content.Context;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

/** Helper class to allow for relevant App Remote actions */
public class SpotifyAppRemoteHelper {

  private SpotifyAppRemote spotifyAppRemote;

  private String clientId;
  private String redirectUri;
  private boolean isConnected = false;

  public SpotifyAppRemoteHelper(String clientId, String redirectUri, Context context) {
    this.clientId = clientId;
    this.redirectUri = redirectUri;

    connect(context);
  }

  private void connect(Context context) {
    ConnectionParams connectionParams =
        new ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build();

    SpotifyAppRemote.connect(
        context,
        connectionParams,
        new Connector.ConnectionListener() {
          @Override
          public void onConnected(SpotifyAppRemote sar) {
            spotifyAppRemote = sar;
            Log.d("APP_REMOTE", "Connected to App Remote");
          }

          @Override
          public void onFailure(Throwable throwable) {
            Log.e("APP_REMOTE", throwable.getMessage(), throwable);
          }
        });
  }

  public void play(String uri) {
    spotifyAppRemote.getPlayerApi().play(uri);
  }

  public void pause() {
    spotifyAppRemote.getPlayerApi().pause();
  }

  public void disconnect() {
    SpotifyAppRemote.disconnect(spotifyAppRemote);
  }

  public boolean isConnected(Context ctx) {
      return SpotifyAppRemote.isSpotifyInstalled(ctx);
  }
}
