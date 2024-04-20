package com.t1r2340.spotifystats.helpers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;

import com.firebase.ui.auth.AuthUI;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.t1r2340.spotifystats.R;

import java.util.Arrays;
import java.util.List;

public class AuthHelper {

    private Activity ctx;

    public AuthHelper(Activity activity) {
        ctx = activity;
    }

    public void createSignInIntent(ActivityResultLauncher<Intent> signInLauncher) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());

        Intent signInIntent =
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setLogo(R.drawable.unwrappd_white).build();
        signInLauncher.launch(signInIntent);
        Log.d("Firebase", "Sign in intent launched");
    }

    /**
     * Get token from Spotify This method will open the Spotify login activity and get the token What
     * is token? https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(ctx, 0, request);
    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        String clientId = ctx.getString(R.string.client_id);
        String redirectUri = ctx.getString(R.string.redirect_uri);
        return new AuthorizationRequest.Builder(clientId, type, Uri.parse(redirectUri).toString())
                .setShowDialog(true)
                .setScopes(
                        new String[] {
                                "user-read-email", "user-follow-read", "user-top-read", "user-read-private"
                        }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

}
