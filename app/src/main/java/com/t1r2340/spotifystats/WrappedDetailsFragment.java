package com.t1r2340.spotifystats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.t1r2340.spotifystats.adapters.TrackRecyclerViewAdapter;
import com.t1r2340.spotifystats.databinding.FragmentWrappedDetailsBinding;
import com.t1r2340.spotifystats.helpers.SpotifyAppRemoteHelper;
import com.t1r2340.spotifystats.models.api.TrackObject;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.List;

/** A fragment representing a list of Items. */
public class WrappedDetailsFragment extends Fragment {

  private Wrapped wrapped;
  List<TrackObject> tracks;
  private SpotifyAppRemoteHelper appRemoteHelper;


  private FragmentWrappedDetailsBinding binding;
  public TrackRecyclerViewAdapter adapter;




  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentWrappedDetailsBinding.inflate(inflater, container, false);

    Bundle bundle = getArguments();
    binding.screenTitleTextView.setText(bundle.getString("wrappedTitle"));
    wrapped = (Wrapped) bundle.getSerializable("wrapped");
    Log.d("WrappedDetailsFragment", "Wrapped: " + wrapped);

    tracks = wrapped.getTopTracks().getItems();

    loadNumberedLists(true);
    loadNumberedLists(false);

    appRemoteHelper = new SpotifyAppRemoteHelper(
            getString(R.string.client_id),
            getString(R.string.redirect_uri),
            getContext()
    );

    setRecyclerView();

    return binding.getRoot();
  }

  public void loadNumberedLists(boolean isGenres) {
    TextView tv = isGenres ? binding.genreListTextView : binding.artistListTextView;

    StringBuilder list = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      list.append(i + 1).append(". ").append(isGenres ? wrapped.getTopGenres().get(i) : wrapped.getTopArtists().getItems().get(i).getName()).append("\n");
    }
    tv.setText(list.toString());
  }

  private void setRecyclerView() {
    RecyclerView recyclerView = binding.trackRecyclerView;

    adapter = new TrackRecyclerViewAdapter(getContext(), tracks, appRemoteHelper);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    appRemoteHelper.disconnect();
    binding = null;
  }
}
