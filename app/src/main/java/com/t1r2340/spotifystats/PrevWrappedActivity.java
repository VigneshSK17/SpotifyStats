package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.t1r2340.spotifystats.models.api.Wrapped;

public class PrevWrappedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_wrapped);

        Intent intent = getIntent();
        String wrappedTitle = intent.getStringExtra("wrappedTitle");
        Wrapped wrapped = (Wrapped) intent.getSerializableExtra("wrapped");

        WrappedDetailsFragment fragment = new WrappedDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("wrappedTitle", wrappedTitle);
        bundle.putSerializable("wrapped", wrapped);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }
}