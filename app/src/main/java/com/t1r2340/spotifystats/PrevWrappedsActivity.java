package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.t1r2340.spotifystats.adapters.WrappedRecyclerViewAdapter;
import com.t1r2340.spotifystats.databinding.AllWrappedsBinding;
import com.t1r2340.spotifystats.helpers.EmptyAdapter;
import com.t1r2340.spotifystats.helpers.FailureCallback;
import com.t1r2340.spotifystats.helpers.FirestoreHelper;
import com.t1r2340.spotifystats.helpers.SpotifyApiHelper;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class PrevWrappedsActivity extends AppCompatActivity implements FailureCallback {

    private String accessToken;
    private FirestoreHelper firestore;
    private AllWrappedsBinding binding;
    private WrappedRecyclerViewAdapter adapter;
    private List<Wrapped> wrappeds;

    private SpotifyApiHelper spotifyApi;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Call mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_wrappeds);

        binding = AllWrappedsBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accessToken");
        firestore = new FirestoreHelper();
        spotifyApi = new SpotifyApiHelper(this, accessToken, mOkHttpClient, mCall);

        getWrappeds();

    }

    private void setRecyclerView(boolean isPremium) {

        runOnUiThread(() -> {
            RecyclerView recyclerView = findViewById(R.id.wrappedRecyclerView);

            adapter = new WrappedRecyclerViewAdapter(this, wrappeds, isPremium);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        });
    }

    private void getWrappeds() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) finish();

        Log.d("WRAPPED", user.getUid());
        firestore.getWrappeds(user.getUid(), ws -> {
            Log.d("PREV_WRAPPED", "" + ws.size());
            wrappeds = ws;
            wrappeds.sort(Comparator.comparing(Wrapped::getGeneratedAt).reversed());

            spotifyApi.getProfile(profile -> setRecyclerView(profile.isPremium()));

        });

    }


    @Override
    public void onFailure(Exception e) {
        Log.d("HTTP", "Failed to fetch data: " + e);
        Toast.makeText(
                this,
                        "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT)
                .show();
    }
}