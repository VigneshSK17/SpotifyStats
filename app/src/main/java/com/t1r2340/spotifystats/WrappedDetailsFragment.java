package com.t1r2340.spotifystats;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t1r2340.spotifystats.databinding.FragmentWrappedDetailsBinding;
import com.t1r2340.spotifystats.models.api.Wrapped;
import com.t1r2340.spotifystats.placeholder.PlaceholderContent;

/** A fragment representing a list of Items. */
public class WrappedDetailsFragment extends Fragment {

  // TODO: Customize parameter argument names
  private static final String ARG_COLUMN_COUNT = "column-count";
  // TODO: Customize parameters
  private int mColumnCount = 1;

  private FragmentWrappedDetailsBinding binding;
  private Wrapped wrapped;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentWrappedDetailsBinding.inflate(inflater, container, false);

    Bundle bundle = getArguments();
    binding.screenTitleTextView.setText(bundle.getString("wrappedTitle"));
    wrapped = (Wrapped) bundle.getSerializable("wrapped");

    Log.d("WrappedDetailsFragment", "Wrapped: " + wrapped);

    return binding.getRoot();
  }
}
