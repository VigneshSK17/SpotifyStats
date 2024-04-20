package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.t1r2340.spotifystats.models.api.Wrapped;

public class WrappedActivity extends FragmentActivity {

    private Wrapped wrapped;
    private String title;
    private boolean isPremium;

    private ViewPager2 viewPager;
    private WrappedPagerAdapter pagerAdapter;
    private final int NUM_PAGES = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapped);

        Intent intent = getIntent();
        wrapped = (Wrapped) intent.getSerializableExtra("wrapped");
        title = intent.getStringExtra("wrappedTitle");
        isPremium = intent.getBooleanExtra("isPremium", false);

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new WrappedPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

    }

    private class WrappedPagerAdapter extends FragmentStateAdapter {
        public WrappedPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }

        // TODO: correspond for each page
        @Override
        public Fragment createFragment(int position) {
            Log.d("WRAPPED_ACTIVITY", "Position " + position);

            Bundle bundle = new Bundle();
            bundle.putString("wrappedTitle", title);
            bundle.putSerializable("wrapped", wrapped);
            bundle.putBoolean("isPremium", isPremium);

            Fragment fragment;

            if (position == 0) {
                fragment = new ArtistsDetailsFragment();
            } else if (position == 1) {
                fragment = new TracksDetailsFragment();
            } else if (position == 2) {
                fragment = new GenreDetailsFragment();
            } else {
                fragment = new WrappedDetailsFragment();
            }

            fragment.setArguments(bundle);
            return fragment;

        }
    }
}
