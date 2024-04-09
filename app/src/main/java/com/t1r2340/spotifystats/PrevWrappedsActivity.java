package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.t1r2340.spotifystats.adapters.WrappedRecyclerViewAdapter;
import com.t1r2340.spotifystats.databinding.AllWrappedsBinding;
import com.t1r2340.spotifystats.helpers.EmptyAdapter;
import com.t1r2340.spotifystats.helpers.FirestoreHelper;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.ArrayList;
import java.util.List;

public class PrevWrappedsActivity extends AppCompatActivity {

    private String accessToken;
    private FirestoreHelper firestore;
    private AllWrappedsBinding binding;
    private WrappedRecyclerViewAdapter adapter;
    private List<Wrapped> wrappeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_wrappeds);

        binding = AllWrappedsBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accessToken");
        firestore = new FirestoreHelper();

        getWrappeds();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.wrappedRecyclerView);
        adapter = new WrappedRecyclerViewAdapter(this, wrappeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getWrappeds() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) finish();

        Log.d("WRAPPED", user.getUid());
        firestore.getWrappeds(user.getUid(), ws -> {
            Log.d("PREV_WRAPPED", "" + ws.size());
            wrappeds = ws;
            setRecyclerView();
        });

    }
}