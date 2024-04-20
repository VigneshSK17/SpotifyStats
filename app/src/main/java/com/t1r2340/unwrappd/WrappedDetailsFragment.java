package com.t1r2340.unwrappd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.t1r2340.unwrappd.adapters.HorizArtistRecyclerViewAdapter;
import com.t1r2340.unwrappd.adapters.TrackRecyclerViewAdapter;
import com.t1r2340.unwrappd.databinding.FragmentWrappedDetailsBinding;
import com.t1r2340.unwrappd.helpers.SpotifyAppRemoteHelper;
import com.t1r2340.unwrappd.models.api.ArtistObject;
import com.t1r2340.unwrappd.models.api.TrackObject;
import com.t1r2340.unwrappd.models.api.Wrapped;

import java.util.List;

/** A fragment representing a list of Items. */
public class WrappedDetailsFragment extends Fragment {

  private Wrapped wrapped;
  List<TrackObject> tracks;
  List<ArtistObject> artists;
  private SpotifyAppRemoteHelper appRemoteHelper;


  private FragmentWrappedDetailsBinding binding;
  private TrackRecyclerViewAdapter tracksAdapter;
  private HorizArtistRecyclerViewAdapter artistsAdapter;

  private int color;
  private boolean isPremium;


  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentWrappedDetailsBinding.inflate(inflater, container, false);

    Bundle bundle = getArguments();
    binding.screenTitleTextView.setText(bundle.getString("wrappedTitle"));
    wrapped = (Wrapped) bundle.getSerializable("wrapped");
    isPremium = bundle.getBoolean("isPremium");

    color = wrapped.getColor();
    Log.d("WrappedDetailsFragment", "Wrapped: " + wrapped);
    Log.d("WrappedDetailsFragment", "isPremium: " + isPremium);

    tracks = wrapped.getTopTracks().getItems();
    artists = wrapped.getTopArtists().getItems();

    setColor();
    loadGenres();
    setArtistRecyclerView();

    appRemoteHelper = new SpotifyAppRemoteHelper(
            getString(R.string.client_id),
            getString(R.string.redirect_uri),
            getContext()
    );

    setTrackRecyclerView();

    return binding.getRoot();
  }

  private void loadGenres() {
    TextView tv = binding.genreListTextView;

    StringBuilder list = new StringBuilder();
    int count = Math.min(5, wrapped.getTopGenres().size());
    for (int i = 0; i < count; i++) {
      list.append(i + 1).append(". ").append(wrapped.getTopGenres().get(i)).append("\n");
    }
    tv.setText(list.toString());
  }

  private void setArtistRecyclerView() {
    RecyclerView recyclerView = binding.artistRecyclerView;

    artistsAdapter = new HorizArtistRecyclerViewAdapter(artists);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    recyclerView.setAdapter(artistsAdapter);
  }

  private void setTrackRecyclerView() {
    RecyclerView recyclerView = binding.trackRecyclerView;

    tracksAdapter = new TrackRecyclerViewAdapter(getContext(), tracks, appRemoteHelper, color, isPremium, true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(tracksAdapter);
  }

  private void setColor() {
    binding.genreCardView.setCardBackgroundColor(getContext().getColor(color));
    binding.artistCardView.setCardBackgroundColor(getContext().getColor(color));
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    appRemoteHelper.disconnect();
    binding = null;
  }
}
