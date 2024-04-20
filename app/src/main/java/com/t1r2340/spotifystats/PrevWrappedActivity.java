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
        Bundle bundle = intent.getExtras();

        WrappedDetailsFragment fragment = new WrappedDetailsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }
}