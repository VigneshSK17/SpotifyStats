package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.t1r2340.spotifystats.databinding.FragmentHomePageBinding;

public class HomePageFragment extends Fragment {

  private FragmentHomePageBinding binding;
  private String accessToken;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentHomePageBinding.inflate(inflater, container, false);

    Bundle bundle = getArguments();
    accessToken = bundle.getString("accessToken");

    setupButtons();

    return binding.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private void setupButtons() {

    setupSettingsButton();
    setupCreateWrappedButton();
  }

  private void setupCreateWrappedButton() {

    Button createWrappedButton = binding.btnCreateWrapped;

    createWrappedButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(getActivity(), WrappedActivity.class);
          intent.putExtra("accessToken", accessToken);
          intent.putExtra("timeRange", "SHORT_TERM"); //TODO: get time range from dropdown
          startActivity(intent);
        });
  }

  private void setupSettingsButton() {

    ImageButton settingsButton = binding.btnSettings;

    settingsButton.setOnClickListener(
        v -> {
          Intent intent = new Intent(getActivity(), SettingsActivity.class);
          startActivity(intent);
        });
  }
}
