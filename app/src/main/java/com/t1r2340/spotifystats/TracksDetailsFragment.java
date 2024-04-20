package com.t1r2340.spotifystats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.t1r2340.spotifystats.adapters.TrackRecyclerViewAdapter;
import com.t1r2340.spotifystats.databinding.FragmentTracksDetailsBinding;
import com.t1r2340.spotifystats.helpers.SpotifyAppRemoteHelper;
import com.t1r2340.spotifystats.models.api.ArtistObject;
import com.t1r2340.spotifystats.models.api.ImageObject;
import com.t1r2340.spotifystats.models.api.TrackObject;
import com.t1r2340.spotifystats.models.api.Wrapped;

import java.util.List;

public class TracksDetailsFragment extends Fragment {


    private List<TrackObject> tracks;
    private SpotifyAppRemoteHelper appRemoteHelper;

    private FragmentTracksDetailsBinding binding;
    private TrackRecyclerViewAdapter adapter;
    private int color;
    private boolean isPremium;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTracksDetailsBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        Wrapped wrapped = (Wrapped) bundle.getSerializable("wrapped");
        isPremium = bundle.getBoolean("isPremium");

        color = wrapped.getColor();
        tracks = wrapped.getTopTracks().getItems();
        loadTopTrack(tracks.get(0));
        tracks = tracks.subList(1, tracks.size());

        appRemoteHelper = new SpotifyAppRemoteHelper(
                getString(R.string.client_id),
                getString(R.string.redirect_uri),
                getContext()
        );

        setTracksRecyclerView();

        return binding.getRoot();
    }

    private void loadTopTrack(TrackObject topTrack) {
        String title = "1. " + topTrack.getName();
        binding.topSongTitle.setText(title);

        ImageObject artistImage = topTrack.getAlbum().getImages().get(0);
        if (artistImage != null) {
            Picasso.get()
                    .load(artistImage.getUrl())
                    .resize(artistImage.getWidth(), artistImage.getHeight())
                    .into(binding.topSongImage);
        }
    }

    private void setTracksRecyclerView() {
        adapter = new TrackRecyclerViewAdapter(getContext(), tracks, appRemoteHelper, color, isPremium, false);
        binding.songRecyclerView.setAdapter(adapter);
        binding.songRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}