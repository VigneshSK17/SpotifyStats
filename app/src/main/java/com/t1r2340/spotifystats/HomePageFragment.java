package com.t1r2340.spotifystats;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.t1r2340.spotifystats.databinding.FragmentHomePageBinding;

public class HomePageFragment extends Fragment {

  private FragmentHomePageBinding binding;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentHomePageBinding.inflate(inflater, container, false);

    setupButtons();

    return binding.getRoot();
  }

  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private void setupButtons() {

    setupSettingsButton();
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
