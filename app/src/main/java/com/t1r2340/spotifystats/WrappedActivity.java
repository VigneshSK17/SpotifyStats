package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.t1r2340.spotifystats.helpers.FailureCallback;
import com.t1r2340.spotifystats.helpers.FirestoreHelper;
import com.t1r2340.spotifystats.helpers.SpotifyApiHelper;
import com.t1r2340.spotifystats.models.api.SpotifyProfile;
import com.t1r2340.spotifystats.models.api.TopArtists;
import com.t1r2340.spotifystats.models.api.TopTracks;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class WrappedActivity extends AppCompatActivity implements FailureCallback {

    private String accessToken;
    private SpotifyApiHelper spotifyApi;
    private FirestoreHelper firestore;
    private SpotifyApiHelper.TimeRange timeRange;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapped);

        //TODO: homepagefragment should have intent with access token and pick time range dropdown
        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accessToken");
        timeRange = SpotifyApiHelper.TimeRange.valueOf(intent.getStringExtra("timeRange"));
        spotifyApi = new SpotifyApiHelper(this, accessToken, mOkHttpClient, mCall);
        firestore = new FirestoreHelper();

        genWrapped();

    }

    private void genWrapped() {
        spotifyApi.getProfile(
                (SpotifyProfile profile) -> {
                    spotifyApi.getTopArtists(
                            (TopArtists artists) -> {
                                spotifyApi.getTopTracks(
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
                                                            profile.getId(), timeRange);


                                            firestore
                                                    .storeWrapped(wrapped)
                                                    .addOnSuccessListener(a -> Log.d("FIRESTORE", "Stored wrapped"))
                                                    .addOnFailureListener(
                                                            a -> Log.d("FIRESTORE", "Failed to store wrapped" + a));

                                            WrappedDetailsFragment fragment = new WrappedDetailsFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("wrappedTitle", "Today's Wrapped");
                                            bundle.putSerializable("wrapped", wrapped);
                                            fragment.setArguments(bundle);
                                            getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.fragment_container_view, fragment)
                                                    .commit();


                                        },
                                        10,
                                        timeRange);
                            },
                            10,
                            timeRange);
                });
    }

    @Override
    public void onFailure(Exception e) {
        Log.d("HTTP", "Failed to fetch data: " + e);
        Toast.makeText(
                        WrappedActivity.this,
                        "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT)
                .show();
    }
}